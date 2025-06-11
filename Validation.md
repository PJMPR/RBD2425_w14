# 🛡️ Walidacja danych i obsługa błędów w aplikacji Java + MongoDB

Ten dokument opisuje sposoby implementacji podstawowej walidacji danych oraz obsługi błędów w aplikacji CRUD z MongoDB bez użycia frameworków typu Spring.

## 🎯 Cele

* Zapewnić, że dane są poprawne przed zapisaniem ich do bazy
* Zapobiec awariom programu przez przechwytywanie wyjątków

---

## ✅ Walidacja danych użytkownika

Walidacja może być wykonana w klasie `UserValidator`, która sprawdza poprawność pól obiektu `User`.


### ✏️ Implementacja

```java
package validation;

import model.User;

public class UserValidator {

    public static boolean isValid(User user) {
        return isNameValid(user.getName()) && isAgeValid(user.getAge());
    }

    public static boolean isNameValid(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public static boolean isAgeValid(int age) {
        return age >= 0 && age <= 120;
    }
}
```

### 📌 Użycie w DAO

```java
import validation.UserValidator;

if (!UserValidator.isValid(user)) {
    throw new IllegalArgumentException("Niepoprawne dane użytkownika.");
}
```

---

## 🧱 Obsługa wyjątków w `UserDaoImpl`

Zabezpieczenie operacji MongoDB przed błędami wykonania.

### 🧩 Przykład obsługi błędów

```java
public void create(User user) {
    try {
        if (!UserValidator.isValid(user)) {
            throw new IllegalArgumentException("Niepoprawne dane użytkownika.");
        }
        Document doc = new Document("name", user.getName())
                .append("age", user.getAge());
        collection.insertOne(doc);
    } catch (IllegalArgumentException e) {
        System.err.println("Błąd walidacji: " + e.getMessage());
    } catch (MongoException e) {
        System.err.println("Błąd MongoDB: " + e.getMessage());
    }
}
```

### 🛠 Obsługa błędnych ID

```java
public User read(String id) {
    try {
        Document doc = collection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (doc == null) return null;
        return new User(doc.getObjectId("_id").toHexString(), doc.getString("name"), doc.getInteger("age"));
    } catch (IllegalArgumentException e) {
        System.err.println("Nieprawidłowy format ID: " + id);
        return null;
    }
}
```

---

## 📌 Rekomendacje

* Unikaj wypisywania `stack trace` bez potrzeby – wyświetlaj przyjazne komunikaty
* Waliduj dane zarówno w UI, jak i w DAO (warstwa ochrony)
* Możesz stworzyć klasę `ValidationException` dla lepszej organizacji wyjątków

---

