# API Developer Assignment

## Description

Write a client for the Fractal API Sandbox to read bank transactions. Categorise bank
transactions based on the transaction description for two categories (Ex: Travel, Coffee)

Write a service to implement endpoints for the following cases:

1. Get categorised transactions for a given category
2. Update category of an existing transaction
3. Add a new category


## 1. How to run
$ git clone https://github.com/dhramijo/api-fractal-labs.git
$ mvn spring-boot:run

************************************************

Endpoints
There are 3 endpoints:

### 1. Get categorised transactions for a given category

HTTP GET /api/v1/{categoryId}
categoryId as a6hg1

### 2. Update category of an existing transaction
````
HTTP PUT /api/v1/updatecategory
````
Request
````
{
	"companyId": 6,
	"categoryId": a6hg1,
	"transactionId": "string"
}
````
## 3. Add a new category
````
HTTP POST /api/v1/category
````
Request
````
{
	"id": "string",
	"name": "string"
}
````
## API Documentation
Swagger documentation can be accessible via
````
http://localhost:8080/swagger-ui.html
````

## Technologies/Frameworks:
Spring Boot 2 

## Data Model:
	Category
	Transaction
	TokenResponse
	UpdateCategoryTransaction
	CategorisedTransaction

## Controller:
	CategoryTransactionController

## Service:
	CategoryTransactionService