# BeerNest

This project is an assignment for 3-6th weeks of Neobis Club.
An online beer store application.
BeerNest is a sample online beer store that allows customers to browse, order,
and manage beer inventory. Key features include product catalog and order management.

### Table of Contents

- [About](#about)
- [Getting Started](#getting-started)
- [Installation](#installation)
- [Docker](#docker)
- [Usage](#usage)
- [Built With](#built-with)
- [Authors](#authors)
- [Contact](#contact)


### About
This is a sample Java Spring Boot application for an online beer store. Customers can browse 
beer products, add them to a shopping cart (left the task for front-end dev), and checkout. 
The application manages product inventory and order fulfillment.
Key Features:
* Browse beer catalog
* Add/remove products to cart
* Checkout and complete purchase
* Order history and status
* Manage product inventory
* Authentication and authorization
* Admin and user roles

### Getting Started

These instructions will get you a copy of the project up and running on your local machine for 
development and testing purposes.

#### Prerequisites

Requirements to run this project:
* Java 21
* Maven 3.5+
* Spring Boot 3.2+
* PostgreSQL 15+

### Installation

To install the application you need:
1. before running installation you need to generate .pem RSA key pair using these commands:
    1) generate key pair: `openssl genrsa -out keypair.pem 2048`
    2) extract public key: `openssl rsa -in keypair.pem -pubout -out public.pem`
    3) transform private key to java readable format: `openssl pkcs8 -topk8 -inform PEM -outform PEM -nocrypt -in keypair.pem -out private.pem`
2. run `git clone https://github.com/yerokha312/beernest-spring.git`
3. place "public.pem" and "private.pem" files into `project/src/main/resources/certs/` directory
4. `cd beernest-spring`
5. run maven command `mvn clean install`, if you have maven installed locally. Or `./mvnw clean install` if you have not maven installed.

### Docker

If you are using Docker, run sql-script saved in sql-scripts directory of Github repository, then open Terminal, type 
command and run: `docker run --rm -e DATABASE_URL=your-database-url(omit 'jdbc:' part of URL)
-e USERNAME=your-database-username -e PASSWORD=your-database-password -p {your-port-to-run-app}:8080 yerokha/beernest-backend`

#### Testing

To run tests just open terminal in root directory and type `mvn clean test`. All test cases could be found in test directory of the project.

#### How To Run

After successful installation:
- type into terminal `cd target/`
- `java -jar beernest-spring-0.0.1-SNAPSHOT.jar --DATABASE_URL=your-database-url(omit 'jdbc:' part of URL) 
--USERNAME=your-database-username --PASSWORD=your-database-password`

#### How To Use

You can sign in under predefined admin user using username "admin@test.ru" and password "password" and test the app via Postman.

Or go to [registration](http://localhost:8080/v1/register)  and sign up as a new customer.

<img src="/screenshots/registration_scr.jpeg" width=50%>

Then go to [token](http://localhost:8080/v1/token) and sign in. Obtain returned token.

<img src="/screenshots/token_scr.jpeg" width=50%>

Then you can visit protected endpoints after the token was saved under 'Authorization' -> 'Bearer Token' field.
### Usage

* Users can browse products without logging in.
* Add products to cart to checkout.
* Registered users can checkout and view order history.
* Admin users can manage inventory and view reports.

### Built With

[Spring Boot](https://spring.io/projects/spring-boot/) - Server framework

[Maven](https://maven.apache.org) - Build and dependency management

[PostgreSQL](https://www.postgresql.org) - Database

### Authors

[Yerbolat Yergaliyev](https://github.com/yerokha312)

### Contact
For support, questions or suggestions please contact:

[linkedIn](https://lnkd.in/ddpDGKY2) - Yerbolat Yergaliyev

erbolatt@live.com

`date` Creation date: 04 January 2024
