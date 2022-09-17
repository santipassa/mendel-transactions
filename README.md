# mendel-transactions

Service to handle Mendel Transactions

## This project is using:

![java 11](https://img.shields.io/badge/java-11-orange.svg)
![springboot 2.6.3](https://img.shields.io/badge/springboot-2.5.6-272822.svg)

## Documentation

[![spects](https://img.shields.io/badge/specs-%E2%9D%A4-green.svg)](http://localhost:8080/swagger-ui.html)

## Running it with Docker locally:

1. Build fat jar file

```bash 
$ mvn clean compile install 
```

2. Build image:

```bash 
$ docker build -t "mendel-transactions" . 
```

3. Check the image:

> This is an optional step, you can skip this if you want.

``` bash 
$ docker images 
```

4. Run the container:

``` bash 
$ docker run --name mendel-transactions -p 8080:8080 mendel-transactions:latest 
```

## Running it without Docker:

```bash 
$ mvn spring-boot:run 
```

## Testing:

```bash 
$ mvn test 
```

## How to use:

### Create new Transaction:
``` bash
$ curl --location --request PUT 'localhost:8080/transactions/12' \
--header 'Content-Type: application/json' \
--data-raw '{
    "amount":5000,
    "type":"shopping",
    "parent_id":null
}'
```

### Get related transactions sum amount:
``` bash
$ curl --location --request GET 'localhost:8080/transactions/sum/12'
```
### Get transactions ids by type :
``` bash
$ curl --location --request GET 'localhost:8080/transactions/types/shopping'
```
