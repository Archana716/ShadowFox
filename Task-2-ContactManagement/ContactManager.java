import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class ContactManager {

    private static ArrayList<Contact> contacts = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;
        do {
            System.out.println("\n**** Welcome to Contact Management System ****");
            System.out.println("1. Add a new Contact");
            System.out.println("2. List all Contacts");
            System.out.println("3. Search for Contact");
            System.out.println("4. Edit a Contact");
            System.out.println("5. Delete a Contact");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addContact();
                case 2 -> listContacts();
                case 3 -> searchContact();
                case 4 -> editContact();
                case 5 -> deleteContact();
                case 0 -> System.out.println("Exiting... Thank you!");
                default -> System.out.println("Invalid choice!");
            }
        } while (choice != 0);
    }

    // ADD
    private static void addContact() {
        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Phone: ");
        String phone = sc.nextLine();

        if (!isValidPhone(phone)) {
            System.out.println("Invalid phone number!");
            return;
        }

        if (isDuplicatePhone(phone)) {
            System.out.println("Contact with this phone already exists!");
            return;
        }

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format!");
            return;
        }

        contacts.add(new Contact(name, phone, email));
        System.out.println("Contact added successfully!");
    }

    // LIST
    private static void listContacts() {
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
            return;
        }

        for (Contact c : contacts) {
            System.out.println(c);
        }
    }

    // SEARCH (case-insensitive)
    private static void searchContact() {
        System.out.print("Enter name to search: ");
        String keyword = sc.nextLine().toLowerCase();

        boolean found = false;
        for (Contact c : contacts) {
            if (c.getName().toLowerCase().contains(keyword)) {
                System.out.println(c);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Contact not found.");
        }
    }

    // EDIT
    private static void editContact() {
        System.out.print("Enter phone number of contact to edit: ");
        String phone = sc.nextLine();

        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {

                System.out.print("Enter new name: ");
                c.setName(sc.nextLine());

                System.out.print("Enter new email: ");
                String email = sc.nextLine();

                if (!isValidEmail(email)) {
                    System.out.println("Invalid email!");
                    return;
                }

                c.setEmail(email);
                System.out.println("Contact updated successfully!");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    // DELETE
    private static void deleteContact() {
        System.out.print("Enter phone number to delete: ");
        String phone = sc.nextLine();

        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {
                contacts.remove(c);
                System.out.println("Contact deleted successfully!");
                return;
            }
        }
        System.out.println("Contact not found.");
    }

    // VALIDATIONS
    private static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    private static boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email);
    }

    private static boolean isDuplicatePhone(String phone) {
        for (Contact c : contacts) {
            if (c.getPhone().equals(phone)) {
                return true;
            }
        }
        return false;
    }
}
