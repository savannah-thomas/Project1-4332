
    package LibraryManagementSystem;

    public class Book {
        // Fields that were directly specified from UML
        private String Name;
        private String Author;
        private int Year;
        private String ISBN;
        private String BookID;
        private boolean IsAvailable;
        private String Genre;

        // Basic constructor... this is not shown in the uml diagram
        public Book(String Name, String Author, int Year, String ISBN, String BookID, boolean IsAvailable, String Genre) {
            this.Name = Name;
            this.Author = Author;
            this.Year = Year;
            this.ISBN = ISBN;
            this.BookID = BookID;
            this.IsAvailable = IsAvailable;
            this.Genre = Genre;
        }


        public boolean checkAvailability() {
            return IsAvailable;
        }


        public void updateBookInfo(String Name, String Author, int Year, String ISBN, String Genre) {
            this.Name = Name;
            this.Author = Author;
            this.Year = Year;
            this.ISBN = ISBN;
            this.Genre = Genre;
        }

        public String getBookInfo() {
            return "BookID: " + BookID +
                    ", Name: " + Name +
                    ", Author: " + Author +
                    ", Year: " + Year +
                    ", ISBN: " + ISBN +
                    ", Genre: " + Genre +
                    ", IsAvailable: " + IsAvailable;
        }

        public String getName() {
            return Name;
        }

        public String getBookID() {
            return BookID;
        }

    }



