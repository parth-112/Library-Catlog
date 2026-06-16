import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Library {
    private List<Book> collection;
    private List<User> users;

    private final String BOOKS_FILE = "library_data.csv";
    private final String USERS_FILE = "users_data.csv";

    public Library() {
        this.collection = new ArrayList<>();
        this.users = new ArrayList<>();

        // Auto-load data when the program starts
        loadBooksFromFile();
        loadUsersFromFile();
    }

    // ==========================================
    //               BOOK METHODS
    // ==========================================

    public void addBook(Book book) {
        collection.add(book);
        System.out.println("Success! '" + book.getTitle() + "' has been added.");
    }

    public void displayAllBooks() {
        if (collection.isEmpty()) {
            System.out.println("The library is currently empty.");
            return;
        }
        System.out.println("\n--- Library Catalog ---");
        for (Book book : collection) {
            System.out.println(book.toString());
        }
        System.out.println("-----------------------");
    }

    public void searchBooks(String keyword) {
        boolean found = false;
        keyword = keyword.toLowerCase();
        System.out.println("\n--- Search Results ---");
        for (Book book : collection) {
            if (book.getTitle().toLowerCase().contains(keyword) ||
                    book.getAuthor().toLowerCase().contains(keyword)) {
                System.out.println(book.toString());
                found = true;
            }
        }
        if (!found) {
            System.out.println("No books found matching: " + keyword);
        }
    }

    // ==========================================
    //               USER METHODS
    // ==========================================

    public void addUser(User user) {
        users.add(user);
        System.out.println("Success! User '" + user.getName() + "' registered.");
    }

    public void displayAllUsers() {
        if (users.isEmpty()) {
            System.out.println("No users registered yet.");
            return;
        }
        System.out.println("\n--- Registered Users ---");
        for (User user : users) {
            System.out.println(user.toString());
        }
        System.out.println("------------------------");
    }

    private boolean userExists(String userId) {
        for (User user : users) {
            if (user.getUserId().equals(userId)) return true;
        }
        return false;
    }

    public void displayBooksByUser(String userId) {
        if (!userExists(userId)) {
            System.out.println("User ID not found. Please check the ID and try again.");
            return;
        }

        boolean hasBooks = false;
        System.out.println("\n--- Books checked out to: " + userId + " ---");
        for (Book book : collection) {
            if (book.getBorrowerId().equals(userId)) {
                System.out.println("- " + book.getTitle() + " (ISBN: " + book.getIsbn() + ")");
                hasBooks = true;
            }
        }

        if (!hasBooks) {
            System.out.println("This user currently has no books checked out.");
        }
        System.out.println("---------------------------------------------");
    }

    // ==========================================
    //           TRANSACTION METHODS
    // ==========================================

    public void borrowBook(String isbn, String userId) {
        if (!userExists(userId)) {
            System.out.println("User ID not found. Please register first.");
            return;
        }

        for (Book book : collection) {
            if (book.getIsbn().equals(isbn)) {
                if (book.isAvailable()) {
                    book.setBorrowerId(userId);
                    System.out.println("Success! Book checked out to User ID: " + userId);
                } else {
                    System.out.println("Sorry, that book is currently checked out.");
                }
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    public void returnBook(String isbn) {
        for (Book book : collection) {
            if (book.getIsbn().equals(isbn)) {
                if (!book.isAvailable()) {
                    book.setBorrowerId("none");
                    System.out.println("Thank you for returning: " + book.getTitle());
                } else {
                    System.out.println("That book was already in the library.");
                }
                return;
            }
        }
        System.out.println("Book with ISBN " + isbn + " not found.");
    }

    // ==========================================
    //             FILE I/O METHODS
    // ==========================================

    public void saveAllData() {
        // Save Books
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : collection) {
                writer.println(book.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving book data: " + e.getMessage());
        }

        // Save Users
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User user : users) {
                writer.println(user.toCSV());
            }
        } catch (IOException e) {
            System.out.println("Error saving user data: " + e.getMessage());
        }

        System.out.println("All library data saved successfully.");
    }

    private void loadBooksFromFile() {
        File file = new File(BOOKS_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                if (data.length == 4) {
                    Book loadedBook = new Book(data[0], data[1], data[2]);
                    loadedBook.setBorrowerId(data[3]); // Load the linked User ID or "none"
                    collection.add(loadedBook);
                }
            }
            System.out.println("Loaded " + collection.size() + " books from file.");
        } catch (IOException e) {
            System.out.println("Error loading library data: " + e.getMessage());
        }
    }

    private void loadUsersFromFile() {
        File file = new File(USERS_FILE);
        if (!file.exists()) return;

        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                if (data.length == 2) {
                    users.add(new User(data[0], data[1]));
                }
            }
            System.out.println("Loaded " + users.size() + " users from file.");
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }
}