import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library myLibrary = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("=====================================");
        System.out.println(" Welcome to the Java Library System! ");
        System.out.println("=====================================");

        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Add a new book");
            System.out.println("2. View all books");
            System.out.println("3. Search for a book");
            System.out.println("4. Borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Add a new user");
            System.out.println("7. View all users");
            System.out.println("8. View a user's borrowed books");
            System.out.println("9. Save & Exit");
            System.out.print("Your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    myLibrary.addBook(new Book(title, author, isbn));
                    break;
                case "2":
                    myLibrary.displayAllBooks();
                    break;
                case "3":
                    System.out.print("Enter title or author to search: ");
                    String keyword = scanner.nextLine();
                    myLibrary.searchBooks(keyword);
                    break;
                case "4":
                    System.out.print("Enter the ISBN of the book to borrow: ");
                    String borrowIsbn = scanner.nextLine();
                    System.out.print("Enter your User ID: ");
                    String borrowerId = scanner.nextLine();
                    myLibrary.borrowBook(borrowIsbn, borrowerId);
                    break;
                case "5":
                    System.out.print("Enter the ISBN of the book to return: ");
                    String returnIsbn = scanner.nextLine();
                    myLibrary.returnBook(returnIsbn);
                    break;
                case "6":
                    System.out.print("Enter new user name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter a unique User ID (e.g., U101): ");
                    String userId = scanner.nextLine();
                    myLibrary.addUser(new User(name, userId));
                    break;
                case "7":
                    myLibrary.displayAllUsers();
                    break;
                case "8":
                    System.out.print("Enter the User ID to view their books: ");
                    String searchUserId = scanner.nextLine();
                    myLibrary.displayBooksByUser(searchUserId);
                    break;
                case "9":
                    myLibrary.saveAllData(); // Saves both books and users
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
        }
        scanner.close();
    }
}