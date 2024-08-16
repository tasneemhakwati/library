This documentation provides details on how to run the application, interact with the API endpoints, and use any authentication if implemented. 
1. Running the Application:
   - Ensure you have Java 17 installed.
   - Clone the repository to your local machine.
   - Navigate to the project directory.
   - Use the following Maven command to build and run the application:
     mvn spring-boot:run 
   - The application will start on http://localhost:8080.
     
 2. API Endpoints:
    - Books:
      - GET /books: Retrieve a list of all books.
      - GET /books/{id}: Retrieve details of a specific book by ID.
      - POST /books: Add a new book. (Request Body: JSON)
      - PUT /books/{id}: Update details of a specific book by ID. (Request Body: JSON)
      - DELETE /books/{id}: Delete a book by ID.
    - Patrons:
      - GET /patrons: Retrieve a list of all patrons.
      - GET /patrons/{id}: Retrieve details of a specific patron by ID.
      - POST /patrons: Add a new patron. (Request Body: JSON)
      - PUT /patrons/{id}: Update details of a specific patron by ID. (Request Body: JSON)
      - DELETE /patrons/{id}: Delete a patron by ID.
    - Borrowing Records:
      - POST /borrow: Add a new borrowing record when a patron borrows a book. (Request Body: JSON)
      - PUT /borrow/{id}: Update a borrowing record when a patron returns a book. (Request Body: JSON)
  3. Authentication: - No authentication mechanisms are implemented in this application.
  4. Transactional Methods:
     - In the services, methods like updateBook, deleteBook, addPatron, updatePatron, deletePatron, addBorrowingRecord, and updateBorrowingRecord have been annotated with @Transactional to ensure data integrity.
  5. Design Patterns Used:
     - Service Layer Pattern: The application uses a Service Layer pattern where business logic is separated from the controllers, promoting a clean architecture.
     - Repository Pattern: The repository interfaces abstract the data access logic, making it easier to manage data and perform unit testing.
  6. Dependencies: - Spring Boot - Spring Data JPA - H2 Database - Spring Boot Starter Web - Spring Boot Starter Data Redis - Mockito (for testing)
  7. There's a Postman collection provided with name "LibraryManagement.postman_collection.json", this collection includes all the endpoints, along with samples and responses which can help you during testing.
