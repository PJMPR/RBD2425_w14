# ❗ Obsługa wyjątków MongoDB w aplikacji Java

Ten dokument przedstawia najczęściej występujące wyjątki pochodzące z biblioteki `mongodb-driver-sync` oraz sposoby ich obsługi w aplikacji Java. Pozwala to na zwiększenie niezawodności oraz czytelność komunikatów błędów.

---

## 🎯 Cel

Zidentyfikować i odpowiednio obsłużyć wyjątki rzucane przez MongoDB podczas operacji CRUD.

## 📦 Typowe wyjątki MongoDB

### 1️⃣ `MongoWriteException`

* Dotyczy błędów przy zapisie dokumentów.
* Często związany z duplikatem `_id`, błędnym indeksem lub strukturą danych.

```java
try {
    collection.insertOne(doc);
} catch (MongoWriteException e) {
    System.err.println("Błąd zapisu do MongoDB: " + e.getError().getMessage());
}
```

### 2️⃣ `MongoCommandException`

* Występuje, gdy MongoDB nie może wykonać polecenia (np. `update`, `delete`, `aggregate`).

```java
try {
    collection.updateOne(filter, update);
} catch (MongoCommandException e) {
    System.err.println("Błąd komendy MongoDB: " + e.getErrorMessage());
}
```

### 3️⃣ `MongoTimeoutException`

* Występuje, gdy przekroczono limit czasu połączenia z serwerem MongoDB.
* Może oznaczać, że baza danych jest niedostępna lub adres jest błędny.

```java
try {
    MongoClient client = MongoClients.create(uri);
    MongoDatabase db = client.getDatabase("mydb");
} catch (MongoTimeoutException e) {
    System.err.println("Nie można połączyć się z MongoDB: timeout.");
}
```

### 4️⃣ `MongoSocketException`

* Błąd po stronie gniazda TCP — np. MongoDB nie działa, błąd DNS, odłączony kabel sieciowy.

```java
try {
    collection.find().first();
} catch (MongoSocketException e) {
    System.err.println("Błąd połączenia sieciowego z MongoDB: " + e.getMessage());
}
```

### 5️⃣ `MongoException` (ogólny)

* Klasa bazowa dla wszystkich wyjątków Mongo.
* Można jej użyć do zabezpieczenia każdej operacji.

```java
try {
    collection.deleteOne(filter);
} catch (MongoException e) {
    System.err.println("Ogólny wyjątek MongoDB: " + e.getMessage());
}
```

---

## 🔁 Rekomendowana struktura w DAO

```java
try {
    // operacja MongoDB
} catch (MongoWriteException e) {
    // logika dla błędów zapisu
} catch (MongoCommandException e) {
    // logika dla błędów komend
} catch (MongoTimeoutException | MongoSocketException e) {
    // logika dla problemów z połączeniem
} catch (MongoException e) {
    // fallback: ogólna obsługa
}
```

---

## ✅ Dobre praktyki

* Loguj błędy w sposób przyjazny dla użytkownika
* Dla krytycznych błędów używaj loggera, nie tylko `System.err`
* Unikaj przechwytywania `Exception` bez potrzeby — używaj konkretnych klas
* Możesz utworzyć własne wyjątki aplikacyjne (`AppDatabaseException`), które będą opakowywać Mongo wyjątki

---
