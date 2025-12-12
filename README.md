# LIBRARY365 

## PROJECT THEME

LIBRARY365 is a backend app to handle a library.  
It's main focus is to be a complete, scalable and secure app  
capable of doing all the operation of a modern library :  

- **Book Handling**  
- **Book Tracking**  
- **Loan**
- **User handling** 

The app was developed using Spring Boot as the main backend framework. As database, PostgresSQL was used.

The app was made keeping in mind **best practices**, **API REST**, **security**, **Data validation** and interaction with **external services**

Library365 could be integrated easily with an ecommerce platform or with any frontend app. 

This project was made as a part of Backend Programming exam and the main scope is to show the acquired skill:  

- **Domain design**
- **Data persistence**
- **JWT Authentication**
- **Relationship**
- **Inheritance and Polymorphism**
- **External API**
- **Advanced Query**
- **Rest API**
- **Endpoint Handling**
- **Security**

## DOMAIN 

![Project Entity Relationship Diagram](img/Library365.png)
The image show the Entity Relationship diagram.
The project includes 8 entities and 2 junction tables.
1) **User**: This entity represents typical library's user with main attributes such as name, surname, mail, password, etc...
2) **Role**: This table represents the User's role
3) **Users_Roles**: This is the junction table that link User and Role. It is used to divide an N:N relationship into two  1:N relationships  

4) **Loan**: This is the table for books loaning, linked to User's and Book's table. There are field for loan date, return date and for information about user and book 
5) **Reservation**: This is the table used when there are no book's available and one user reserve one copy for him. There are field for the loan status, a date for the reservation and info about user and book
6) **Book**: Abstract Class. Alongside User, this is one of the major entity of the project. Has field for title,ISBN, description, publication year and cover
7) **Printed_Book**: Concrete Class,"child" of Book, implementation of Book. This is a physical book. Has the same field of his "father" and other important field such as position in the physical library, total copies and available copies
8) **E_Book**:Concrete Class, "child" of Book, implementation of Book. Has the same field of his "father" with other important field such as url and licenseType

### Logic
The logic of the project is based over these entities.
We can have 3 different type of User: Admin, Librarian and User.  
**Admin**  can handle **Librarian**, **User**, **User activity** and the **Library**.  
**Librarian** can handle **User**, **User activity** and **Library**.  
**User** can only open / delete  **account** and make request about **Loans**, **Reservation** and see **Books** in the **Library**  

## ENTITY
The project is built around 8 main entities:  
1)  **User**
2)   **Loan**  
3)   **Role**
4)   **Reservation**
5)   **Book**  
6)   **Printed_Book**  
7)   **E_Book**  
8)   **Authors**

In the ERD as visible, there are 2 junction table. These table could have been created physically 
using following sql code :
```sql
CREATE TABLE users_roles (
    user_id UUID NOT NULL,
    role_id UUID NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (role_id) REFERENCES roles(role_id)
);

CREATE TABLE books_authors (
    book_id UUID NOT NULL,
    author_id UUID NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES book(book_id),
    FOREIGN KEY (author_id) REFERENCES author(author_id)
);

```

Due to the simplicity of the project, I preferred to use JPA  Manytomany annotations. 
In this way I followed the project guidelines regarding relationship. 

In the ERD is also showed an Inheritance between three entities:  
1)  **Book**  
2)  **Printed_Book**  
3)  **E_Book**  

Essentially, **Book** is an abstract class extended by **Printed_Book** (physical book of the library)
and **E_Book** (digital copy). I've used a **JOINED** approach, where the children have some common field with father.
This approach avoid **Null** field in table.

Every entity has its own attribute and constraints in order to have a smooth, logic and meaningful workflow.

![PG ADMIN ERD](img/pgAdmin_ERD.png)  
In this file you can see how all the code I wrote about entities has translated in the following ERD created by pgAdmin.  
It was created at first glance (how lucky, without error ) and it is similar to the one I designed in the beginning  

### User
User was designed with some "fields".
This fields contributes on creating a full functionally profile. 

### Normalization : Entity, DTO, Services, Controller
Every Entity, DTO, Services and Controller was normalized following best practices.  
The best practices includes using of annotation like ```@NotBlank```, ```@Size``` and so on . This ensures no sensible information leaks outside and grant consistency 

## REST API's : CONTROLLER, REPOSITORY AND SERVICE 
This application exposes main functionality using CRUD operations (POST, GET,PATCH, PUT, DELETE ) taken from repositories and implementing it with other useful method.
Every entity, where possible, has its own Controller and its own Service. This fragmentation enhance modularity and code reusability, enhancing loose coupling