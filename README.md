# Retail Store Discounts
## Requirements
On a retail website, the following discounts apply:
1. If the user is an employee of the store, he gets a 30% discount.
2. If the user is an affiliate of the store, he gets a 10% discount.
3. If the user has been a customer for over 2 years, he gets a 5% discount.
4. For every $100 on the bill, there would be a $ 5 discount (e.g. for $ 990, you get $ 45 as
a discount).
5. The percentage based discounts do not apply on groceries.
6. A user can get only one of the percentage based discounts on a bill.


### Prerequisites:
- Java 21
- Spring Boot 3.2.0
- MongoDB

## Table of Contents
- [Installation](#installation)
- [Usage](#usage)

## Installation
### Using docker-compose to build the project and run the app under port 8080
- mvn clean package
- docker-compose up --build

### App now is ready to receive calls on http://localhost:8080

## Usage

### First step is to register user to be able to access the app and calculate the discounts

#### Register API
- Request
```
curl --request POST \
  --url http://localhost:8080/api/v1/auth/register \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.1.1' \
  --cookie JSESSIONID=FC6BCD9E7B23BC792F1110C909623350 \
  --data '{
	"username":"employee1",
	"password":"password123",
	"userType":"EMPLOYEE"
}'
```

- Response
```
{
	"username": "employee1"
}
```

#### Login API
- Request
```
curl --request POST \
  --url http://localhost:8080/api/v1/auth/login \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.1.1' \
  --cookie JSESSIONID=FC6BCD9E7B23BC792F1110C909623350 \
  --data '{
	"username":"employee1",
	"password":"password123"
}'
```

- Response
```
{
	"jwt": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbXBsb3llZTEiLCJpYXQiOjE3MzExODYzNTIsImV4cCI6MTczMTE4OTk1Mn0.wNhUkAZqqm7BotCpi7C3R9joRrKNL1FbsnM65PtKcfo",
	"expiresIn": 3600000
}
```
#### Calculate Discount API
- Request
```
curl --request POST \
  --url http://localhost:8080/api/v1/bills/calculate-discount \
  --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbXBsb3llZTEiLCJpYXQiOjE3MzExODYzNTIsImV4cCI6MTczMTE4OTk1Mn0.wNhUkAZqqm7BotCpi7C3R9joRrKNL1FbsnM65PtKcfo' \
  --header 'Content-Type: application/json' \
  --header 'User-Agent: insomnia/10.1.1' \
  --cookie JSESSIONID=FC6BCD9E7B23BC792F1110C909623350 \
  --data '{
	"items": [
		{
			"price":"30",
			"isGrocery": true
		},
		{
			"price":"300",
			"isGrocery": false
		},
		{
			"price":"45",
			"isGrocery": true
		},
		{
			"price":"10",
			"isGrocery": true
		}
	]
}'
```
- Response
```
{
	"totalAmount": 385.0,
	"totalAmountAfterDiscount": 280.0
}
```

## UML Class Diagram
### uml draw.io is included in the repo /documents 
![Retail-Store-Discount drawio](https://github.com/user-attachments/assets/1e670283-1a79-40b1-ad09-78ca5a4fa91a)

