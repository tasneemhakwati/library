It is a library management system which contain Book and Patron and BorrowingRecords
You can do the following:
- Add Book: by calling the api: localhost:8080/api/books with POST method, sending the Book details as this : 
{
	"title": "bookTitle",
	"author": "bookAuthor"
} 
- Update Book : localhost:8080/api/books/{bookId} with PUT method, and the request body:
{
	"title": "bookTitle",
	"author": "bookAuthor"
} 
- Delete Book: localhost:8080/api/books/{bookID} with DELETE method.
- Get Book by ID: localhost:8080/api/books/{bookID} with GET method.
- Get all books: localhost:8080/api/books with GET method.
- You can do the same for the patron add, delete, update, getById, getAll. Its request body for adding and updating:
 {
	"name" : "patronName",
	"contactInfo": "patronEmail"
 }
For BorrowingRecord you can:
-  Add Record with: localhost:8080/api/borrow/{bookID}/patron/{patronId} with POST method
-  Update Record: localhost:8080/api/return/{bookID}/patron/{patronId} with PUT method
If You failed to run the api, you will be directed to the missing fields by the error handling and validation proccess.
Unit Testing also exist for service classes to make sure our service work correctly.
 
