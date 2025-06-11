# 📂 Projekt Java + MongoDB

## 📊 Opis projektu

Ten projekt to przykładowa aplikacja konsolowa napisana w czystej Javie (bez Springa), która pozwala na wykonywanie operacji CRUD (Create, Read, Update, Delete) na bazie danych MongoDB. W aplikacji zastosowano podstawowe wzorce projektowe: **DAO**, **Factory**, oraz **Strategy** (opcjonalnie).

## 🚀 Wymagania

* Java 17+
* Maven
* MongoDB (lokalnie lub w chmurze - np. MongoDB Atlas)

## 📝 Konfiguracja projektu

### Konfiguracja `pom.xml`

```xml
<dependencies>
    <dependency>
        <groupId>org.mongodb</groupId>
        <artifactId>mongodb-driver-sync</artifactId>
        <version>4.10.2</version>
    </dependency>
</dependencies>
```

## 🧰 Wzorce projektowe

### 🔢 DAO (Data Access Object)

Interfejs i implementacja operacji CRUD:

```java
public interface UserDao {
    void create(User user);
    User read(String id);
    void update(User user);
    void delete(String id);
    List<User> findAll();
}
```

```java
public class UserDaoImpl implements UserDao {
    private final MongoCollection<Document> collection;

    public UserDaoImpl() {
        collection = MongoDBConnector.getDatabase().getCollection("users");
    }
    // Implementacja metod...
}
```

### 🏠 Factory

```java
public class DaoFactory {
    public static UserDao createUserDao() {
        return new UserDaoImpl();
    }
}
```

## 🛠️ Połączenie z MongoDB (MongoDBConnector.java)

```java
public class MongoDBConnector {
    private static final String USER = "mongoUser";
    private static final String PASSWORD = "mongoPass";
    private static final String HOST = "localhost"; // lub adres MongoDB Atlas
    private static final String DATABASE = "test_db";

    public static MongoDatabase getDatabase() {
        String uri = String.format("mongodb://%s:%s@%s:27017", USER, PASSWORD, HOST);
        MongoClient mongoClient = MongoClients.create(uri);
        return mongoClient.getDatabase(DATABASE);
    }
}
```

## ⚖️ Klasa modelowa

```java
public class User {
    private String id;
    private String name;
    private int age;
    // gettery i settery
}
```

## 💻 Uruchomienie aplikacji

```bash
mvn compile
mvn exec:java -Dexec.mainClass="app.Main"
```

## 🔮 Przykładowy interfejs użytkownika (Main.java)

```java
public class Main {
    public static void main(String[] args) {
        UserDao userDao = DaoFactory.createUserDao();
        Scanner scanner = new Scanner(System.in);
        // menu konsolowe CRUD
    }
}
```

## 🔧 Przykładowe dane testowe

```java
User testUser = new User();
testUser.setName("John Doe");
testUser.setAge(30);
userDao.create(testUser);
```

## 📅 TODO

* [ ] Obsługa błędów
* [ ] Walidacja danych
* [ ] Strategie filtrowania użytkowników

