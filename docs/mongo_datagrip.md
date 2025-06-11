# 💡 Konfiguracja DataGrip do połączenia z MongoDB w Dockerze

Ten przewodnik pokaże Ci, jak skonfigurować JetBrains DataGrip, aby połączyć się z instancją MongoDB uruchomioną w kontenerze Docker na Windows.

---

## 📦 Wymagania

- Zainstalowany [DataGrip](https://www.jetbrains.com/datagrip/)
- Uruchomiony kontener MongoDB (zgodnie z [tym przewodnikiem](./mongo_docker.md))
- MongoDB słucha na porcie `27017` i ma skonfigurowanego użytkownika `admin` z hasłem `haslo123`

---

## ⚙️ Kroki konfiguracji

### 1. Uruchom DataGrip i dodaj nowe połączenie
- Otwórz DataGrip
- Kliknij `+` w panelu `Database` → `Data Source` → `MongoDB`

### 2. Wprowadź dane połączenia

| Pole | Wartość |
|------|---------|
| Host | `localhost` |
| Port | `27017` |
| Auth Database | `admin` |
| User | `admin` |
| Password | `haslo123` |

> 💡 Jeśli kontener działa lokalnie, `localhost` powinien działać bez problemu.

### 3. Zainstaluj sterowniki (jeśli wymagane)
- Po wprowadzeniu danych kliknij `Test Connection`
- DataGrip może poprosić o pobranie sterownika – kliknij `Download`

### 4. Zapisz połączenie
- Kliknij `OK`, aby zapisać konfigurację
- Połączenie powinno być teraz widoczne w panelu po lewej stronie i gotowe do użytku

---

## ✅ Gotowe!

Masz teraz skonfigurowane połączenie DataGrip z MongoDB działającym w kontenerze Docker. Możesz wykonywać zapytania, przeglądać kolekcje i zarządzać danymi z poziomu GUI.

---

