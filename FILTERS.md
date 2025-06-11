# 🧩 Filtrowanie użytkowników w MongoDB przy użyciu wzorca Strategii (dynamiczne zapytania)

Ten dokument przedstawia implementację filtrowania użytkowników w MongoDB z użyciem wzorca projektowego **Strategy**, przy czym każda strategia buduje dynamicznie zapytanie `Bson`, które jest następnie wykonywane po stronie bazy.

## 🎯 Cel

Zbudować strategię filtrowania, która generuje odpowiedni obiekt `Bson`, przekazywany bezpośrednio do zapytania MongoDB (`collection.find(...)`).


## 🧩 Interfejs strategii

```java
package strategy;

import org.bson.conversions.Bson;

public interface UserFilterStrategy {
    Bson toBson();
}
```

## 🔍 Implementacje strategii

### 1️⃣ Filtrowanie po imieniu

```java
package strategy;

import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class FilterByName implements UserFilterStrategy {
    private String name;

    public FilterByName(String name) {
        this.name = name;
    }

    public Bson toBson() {
        return Filters.eq("name", name);
    }
}
```

### 2️⃣ Filtrowanie po wieku

```java
package strategy;

import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class FilterByAge implements UserFilterStrategy {
    private int age;

    public FilterByAge(int age) {
        this.age = age;
    }

    public Bson toBson() {
        return Filters.eq("age", age);
    }
}
```

### 3️⃣ Filtrowanie po zakresie wieku

```java
package strategy;

import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class FilterByAgeRange implements UserFilterStrategy {
    private int minAge;
    private int maxAge;

    public FilterByAgeRange(int minAge, int maxAge) {
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public Bson toBson() {
        return Filters.and(
            Filters.gte("age", minAge),
            Filters.lte("age", maxAge)
        );
    }
}
```

### ♻️ Strategia złożona (Composite)

```java
package strategy;

import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import com.mongodb.client.model.Filters;

public class CompositeFilterStrategy implements UserFilterStrategy {
    private List<UserFilterStrategy> strategies;

    public CompositeFilterStrategy() {
        this.strategies = new ArrayList<UserFilterStrategy>();
    }

    public void addStrategy(UserFilterStrategy strategy) {
        this.strategies.add(strategy);
    }

    public Bson toBson() {
        List<Bson> bsonList = new ArrayList<Bson>();
        for (UserFilterStrategy strategy : strategies) {
            bsonList.add(strategy.toBson());
        }
        return Filters.and(bsonList);
    }
}
```

## 🛠️ Modyfikacja `UserDao`

Dodajemy nową metodę do interfejsu i implementacji DAO:

### Interfejs:

```java
List<User> findByFilter(UserFilterStrategy filterStrategy);
```

### Implementacja:

```java
public List<User> findByFilter(UserFilterStrategy filterStrategy) {
    List<User> users = new ArrayList<User>();
    for (Document doc : collection.find(filterStrategy.toBson())) {
        User user = new User(
            doc.getObjectId("_id").toHexString(),
            doc.getString("name"),
            doc.getInteger("age")
        );
        users.add(user);
    }
    return users;
}
```

## ✅ Przykład użycia

```java
UserDao dao = new UserDaoImpl();

FilterByAgeRange ageFilter = new FilterByAgeRange(18, 30);
FilterByName nameFilter = new FilterByName("Anna");

CompositeFilterStrategy composite = new CompositeFilterStrategy();
composite.addStrategy(ageFilter);
composite.addStrategy(nameFilter);

List<User> result = dao.findByFilter(composite);

for (User u : result) {
    System.out.println(u.getName());
}
```

## 📌 Zalety

* Wydajne filtrowanie danych — bez pobierania całej kolekcji
* Łatwość rozszerzania (nowa klasa strategii = nowy filtr)
* Możliwość łączenia wielu strategii
* Separacja logiki zapytań od DAO i UI

---

