# 🔐 KV Store — In-Memory Key-Value Store (Spring Boot)

A lightweight, blazing-fast **in-memory key-value store** built using **Java 17 + Spring Boot 3**. Designed for learning and extending into distributed systems — this project is your foundation to explore **data storage, replication, persistence, and consistency models (e.g. Raft)**.

---

## 🚀 Features

- ✅ Create, Read, Delete key-value pairs via REST APIs
- ✅ In-memory store using `ConcurrentHashMap`
- ✅ Input validation with Jakarta Validation
- ✅ Centralized exception handling
- ✅ Lightweight logging (SLF4J/Logback)
- 🔜 Phase 2: Persistence, Replication, Raft, TTL & Expiry, Backup/Restore

---

## 📦 Tech Stack

| Layer           | Technology                     |
|----------------|--------------------------------|
| Language        | Java 17                        |
| Framework       | Spring Boot 3.2.5              |
| Concurrency     | ConcurrentHashMap              |
| Validation      | Jakarta Validation (JSR-380)   |
| Logging         | SLF4J + Logback                |
| Testing         | JUnit 5, Mockito (planned)     |
| Build Tool      | Maven                          |

---

## 🛠️ Getting Started

### 📄 Prerequisites

- Java 17+
- Maven 3.8+
- Git

### 🔧 Build & Run & Test

```bash
# Clone the repo
git clone git@github.com:nitish-pradhan/kv-store.git
cd kv-store

# Build the project
mvn clean install

# Run the app
mvn spring-boot:run

🧪 Test it out
Use curl, Postman, or any REST client:

# Create a key-value pair
curl -X POST http://localhost:8080/api/v1/store \
     -H "Content-Type: application/json" \
     -d '{"key": "greeting", "value": "hello"}'

# Retrieve the value
curl "http://localhost:8080/api/v1/store?key=greeting"

# Delete the key
curl -X DELETE "http://localhost:8080/api/v1/store?key=greeting"

# Get all keys
curl http://localhost:8080/api/v1/store/all


---

## 📌 Phase 2 Roadmap

We aim to evolve `kv-store` into a resilient, distributed KV engine:

- [ ] Persistence: File or H2-based storage
- [ ] TTL & Expiry: Auto cleanup of keys
- [ ] Replication: Primary-replica model
- [ ] Consensus: RAFT protocol integration
- [ ] Backup & Restore: Manual or scheduled snapshots
- [ ] Clustering: Peer-to-peer communication using REST or gRPC
- [ ] Monitoring: Metrics, health endpoints, Prometheus
- [ ] Kubernetes Deployment: Helm chart, readiness probes
- [ ] Docker Support: Containerization & orchestration

---

## 👨‍💻 Author

Made with ❤️ by **[Nitish Pradhan](https://github.com/nitish-pradhan)**

- Backend Architect | Java | Microservices | Distributed Systems | DevSecOps | Cloud Native  
- 📍 Based in India  
- 🛠 15+ years of experience  
- 📬 Always learning, always building  

---

## 🤝 Contributing

Pull requests are welcome! Open an issue first to discuss what you’d like to change or contribute in the Phase 2 roadmap.

---

## 📄 License

This project is licensed under the [MIT License](LICENSE).
