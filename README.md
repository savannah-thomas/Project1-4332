# Project 1, 4332
Project 1 for 4332 requires us to develop a Library Management System with an Interface and a Library, Member, and Book class. Additionally, the project requires us to write a test suite with Specification, Structural, and Property Based tests. 

This project is a console-based Library Managment System in Java, structured to help librarians manage their books as well as it's members through a command-line interface. These features include: 
1. Adding and Removing Books
2. Adding and Removing Members
3. Checking Out Books to Members
4. Returning Books Back to the Library
5. Tracking Books Availabilty
6. Managaging Member's Borrowing History

The Library Managment System is Structured Below:
1. Book.java - Which represents a book's data and availability status
2. Member. java - Which represents a library member and which book they've borrowed
3. Library.java - Manages books, members, check out process, and the returns
4. Interface.java - Defines the operations that a librarian will invoke from command line
5. ConsoleInterface. java - Represents the command line interface for library

Exception Handling
-   Classes perform internal validation and throws:
  - IllegalArgumentException for invalid inputs
  - NullPointerException for null references
