public class Book {
    private String title;
    private String author;
    private String isbn;
    private String borrowerId; // Replaces 'isAvailable'

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrowerId = "none"; // "none" means it is available
    }

    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }
    public String getBorrowerId() { return borrowerId; }

    // A helpful method to check availability
    public boolean isAvailable() { return borrowerId.equals("none"); }

    public void setBorrowerId(String borrowerId) { this.borrowerId = borrowerId; }

    @Override
    public String toString() {
        String status = isAvailable() ? "Available" : "Checked out to User: " + borrowerId;
        return String.format("Title: %s | Author: %s | ISBN: %s | Status: %s",
                title, author, isbn, status);
    }

    public String toCSV() {
        return title + "," + author + "," + isbn + "," + borrowerId;
    }
}