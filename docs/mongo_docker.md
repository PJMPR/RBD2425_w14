# MongoDB w Dockerze (Windows / CMD)

Poniżej znajduje się przewodnik instalacji i uruchomienia MongoDB w kontenerze Docker na systemie Windows, z wykorzystaniem `cmd`.

---

## 📦 Wymagania wstępne

- Zainstalowany [Docker Desktop for Windows](https://www.docker.com/products/docker-desktop/)
- Uruchamianie poleceń w `cmd` lub PowerShell

---

## 🛠️ Instalacja i uruchomienie

### 1. Uruchom MongoDB z użyciem Dockera:

```cmd
docker run -d --name mongodb -p 27017:27017 -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=haslo123 mongo
```

📌 **Parametry wyjaśnienie:**
- `-d` – uruchamia kontener w tle
- `--name` – nazwa kontenera
- `-p` – mapowanie portów (lokalny:kontenerowy)
- `-e` – zmienne środowiskowe dla loginu i hasła

---

## ✅ Sprawdzenie działania

### 1. Zobacz logi kontenera:
```cmd
docker logs mongodb
```

### 2. Połącz się do MongoDB z klienta (np. MongoDB Compass):
- Host: `localhost`
- Port: `27017`
- Username: `admin`
- Password: `haslo123`
- Auth DB: `admin`

📌 Jeśli chcesz korzystać z klienta `mongo` w Windows, musisz go zainstalować ręcznie lub użyć obrazu Docker:
```cmd
docker exec -it mongodb mongosh -u admin -p haslo123 --authenticationDatabase admin
```

---

## 🧼 Zatrzymanie i usunięcie kontenera (opcjonalnie)

```cmd
docker stop mongodb

docker rm mongodb
```

---

## 📚 Dokumentacja
- [Oficjalny obraz MongoDB na DockerHub](https://hub.docker.com/_/mongo)

---
