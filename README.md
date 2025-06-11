Quotation MCP Server

A modular, clean-coded backend application that implements the Model Context Protocol (MCP) to simulate quotation generation, pricing, and discount suggestion for a B2B sales use case.

This project is ideal for developers exploring MCP tooling, handler-based architecture, or looking to integrate AI/LLM-driven tool invocation in an enterprise context.

⸻

✨ Features
	•	✍️ Create new quotations with dynamic pricing rules
	•	📋 Retrieve quotation details by ID (UUID)
	•	💸 Calculate discounted price based on product quantity
	•	📉 Suggest promotional discounts per product
	•	⚙️ Tool Discovery endpoint for MCP clients like Claude
	•	🔐 PostgreSQL-backed persistent storage
	•	✨ Clean architecture with handler abstraction (Open/Closed Principle)
	•	📃 Extensive unit & edge-case testing using JUnit + Mockito

⸻

⚡ Tech Stack
	•	Java 17
	•	Spring Boot 3.x
	•	PostgreSQL 15+
	•	MCP (Model Context Protocol)
	•	JPA (Hibernate)
	•	JUnit 5, Mockito
	•	IntelliJ IDEA, Postman, DBeaver

⸻

📑 Tooling (MCP)

Tool Discovery Endpoint:

GET /mcp/tools

Returns JSON describing all supported MCP tools:

[
  {
    "name": "createQuotation",
    "description": "Yeni bir teklif oluşturur.",
    "params": {
      "customerName": "Müşteri adı",
      "currency": "Para birimi",
      "items": "Ürün ve adet listesi"
    }
  },
  ...
]


⸻

🚀 Getting Started

1. Clone & Build

git clone https://github.com/your-username/quotation-mcp-server.git
cd quotation-mcp-server
./mvnw clean install

2. Configure DB

Create PostgreSQL database:

createdb -U postgres quotationdb

Update src/main/resources/application.properties:

spring.datasource.url=jdbc:postgresql://localhost:5432/quotationdb
spring.datasource.username=postgres
spring.datasource.password=yourpassword

3. Run Locally

./mvnw spring-boot:run

4. Test via Postman

POST http://localhost:8080/mcp

{
  "jsonrpc": "2.0",
  "method": "createQuotation",
  "params": {
    "customerName": "ABC Ltd.",
    "currency": "TRY",
    "items": [
      {"product": "Premium Package", "quantity": 2},
      {"product": "Extra Module", "quantity": 3}
    ]
  },
  "id": "1"
}


⸻

📂 Code Structure

.
├── controller/             # MCPController, ToolDiscovery
├── handler/               # Clean handlers for each MCP tool
├── dto/                   # All Request/Response models
├── model/                 # JPA Entities
├── repository/            # Spring Data Repositories
├── service/               # Business logic layer
└── test/                  # Unit tests per handler/service


⸻

📝 License

MIT License

⸻

👍 Contributors
	• **M. Hakan Celik** – [LinkedIn Profile](https://www.linkedin.com/in/muhammedhakancelik/)

⸻

🚀 Coming Soon
	•	OAuth2 / JWT authentication
	•	Deployment via Docker
	•	Tool Registry publishing (Claude, etc.)
	•	Sample frontend client for testing (React)
	•	OpenAPI 3.0 YAML documentation

⸻

🚫 Disclaimer

This project is a learning implementation of MCP and does not simulate real-time financial pricing logic or actual airline quotation systems.

⸻

🧠 Learn More
	•	Model Context Protocol (MCP) Spec
	•	Anthropic Claude
	•	Language Server Protocol Inspiration
	•	PostgreSQL Documentation
	•	Spring Boot Reference Guide
