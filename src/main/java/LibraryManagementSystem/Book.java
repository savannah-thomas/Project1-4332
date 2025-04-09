
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

        /**
         * Checks whether book is available.
         * @return true if IsAvailable and false if loaned
         */
        public boolean checkAvailability() {
            return IsAvailable;
        }

        /**
         * Updates book info.
         * @param Name - new name of book
         * @param Author - new author name
         * @param Year - new year that the book was published
         * @param ISBN - new ISBN
         * @param Genre - new genre of book
         */
        public void updateBookInfo(String Name, String Author, int Year, String ISBN, String Genre) {
            this.Name = Name;
            this.Author = Author;
            this.Year = Year;
            this.ISBN = ISBN;
            this.Genre = Genre;
        }

        /**
         * @return string of book details
         */
        public String getBookInfo() {
            return "BookID: " + BookID +
                    ", Name: " + Name +
                    ", Author: " + Author +
                    ", Year: " + Year +
                    ", ISBN: " + ISBN +
                    ", Genre: " + Genre +
                    ", IsAvailable: " + IsAvailable;
        }

        /**
         * getter for getName
         * @return name of book
         */
        public String getName() {
            return Name;
        }

        /**
         * getter for getBookID
         * @return ID of book
         */
        public String getBookID() {
            return BookID;
        }

    }



