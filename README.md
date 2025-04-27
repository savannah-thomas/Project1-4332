# Project 2, 4332
Project 2 for 4332 requires us to develop a Library Management System with an Interface and a Library, Member, Book, Library Accounts, Purchasing and Librarians class. Additionally, the project requires us to write a test suite with Specification, Structural, and Property Based tests. 

new funtionality for Proj2 is denoted w/ **

This project is a console-based Library Managment System in Java, structured to help librarians manage their books as well as it's members through a command-line interface. These features include: 
1. Adding and Removing Books
2. Adding and Removing Members
3. Checking Out Books to Members
4. Returning Books Back to the Library
5. Tracking Books Availabilty
6. Managaging Member's Borrowing History
7. Handling Library Fianances and Librarian (Full-Time & Part-Time Roles)**

The Library Managment System is Structured Below:
1. Book.java - Which represents a book's data and availability status
2. Member. java - Which represents a library member and which book they've borrowed
3. Library.java - Manages books, members, check out process, and the returns
4. Interface.java - Defines the operations that a librarian will invoke from command line
5. ConsoleInterface.java - Represents the command line interface for library and interacts with LibraryAccounts and Librarians**
6. Librarians.java - Manages full-time librarian authentication, and records librarian salary withdrawals and purchases**
7. LibraryAccounts.java - Manages library's operating cash balance and allows librarians to add donations, withdrawal librarian book salaries, and purchase books**
8. Purchasing.java - Simulates random book pricing for purchases made by full-time librarians**

Exception Handling
-   Classes perform internal validation and throws:
  - IllegalArgumentException for invalid inputs
  - NullPointerException for null references
  - SecurityException for unauthorized action attempts**
  - IllegalStateException for insufficient operating cash balance while purchasing**
