# 🧩 Implementacja UserDao w Javie z MongoDB

Ten dokument przedstawia szczegółową implementację warstwy DAO dla klasy `User`, która korzysta z MongoDB jako źródła danych. DAO (Data Access Object) oddziela logikę dostępu do danych od logiki biznesowej aplikacji.


## 🔗 Interfejs `UserDao`

```java
package dao;

import model.User;
import java.util.List;

public interface UserDao {
    void create(User user);
    User read(String id);
    void update(User user);
    void delete(String id);
    List<User> findAll();
}
```

### ✍️ Opis metod

* `create(User user)` – dodaje nowego użytkownika do bazy.
* `read(String id)` – pobiera użytkownika po ID.
* `update(User user)` – aktualizuje dane użytkownika.
* `delete(String id)` – usuwa użytkownika po ID.
* `findAll()` – pobiera wszystkich użytkowników.

## 🛠️ Implementacja `UserDaoImpl`

```java
package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import model.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import util.MongoDBConnector;

import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {
    private final MongoCollection<Document> collection;

    public UserDaoImpl() {
        this.collection = MongoDBConnector.getDatabase().getCollection("users");
    }

    @Override
    public void create(User user) {
        Document doc = new Document("name", user.getName())
                .append("age", user.getAge());
        collection.insertOne(doc);
    }

    @Override
    public User read(String id) {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        return new User(doc.getObjectId("_id").toHexString(), doc.getString("name"), doc.getInteger("age"));
    }

    @Override
    public void update(User user) {
        collection.updateOne(
            Filters.eq("_id", new ObjectId(user.getId())),
            Updates.combine(
                Updates.set("name", user.getName()),
                Updates.set("age", user.getAge())
            )
        );
    }

    @Override
    public void delete(String id) {
        collection.deleteOne(Filters.eq("_id", new ObjectId(id)));
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        for (Document doc : collection.find()) {
            users.add(new User(
                doc.getObjectId("_id").toHexString(),
                doc.getString("name"),
                doc.getInteger("age")
            ));
        }
        return users;
    }
}
```

## 🔍 Uwagi implementacyjne

* Operacje CRUD wykorzystują klasę `Document` oraz filtry z pakietu `com.mongodb.client.model.Filters`.
* ID użytkownika to typ `ObjectId`, który jest konwertowany na/z formatu hex.
* Klasa `User` musi posiadać konstruktor z polami `id`, `name`, `age`.

## 🧪 Testowanie

Przykład użycia DAO:

```java
UserDao userDao = new UserDaoImpl();

User newUser = new User(null, "Anna", 25);
userDao.create(newUser);

List<User> users = userDao.findAll();
users.forEach(u -> System.out.println(u.getName()));
```

---

