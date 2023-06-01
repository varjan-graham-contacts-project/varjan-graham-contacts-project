import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.nio.file.Files;



public class ContactsApp {

    public static Scanner sc = new Scanner(System.in);

    public static int choice;
    public static void main(String[] args) {
        consoleUI();
        handleChoice(choice);

    }



    private static int consoleUI() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("Enter an option (1, 2, 3, 4 or 5):");
        choice = Integer.parseInt(sc.nextLine());
        return choice;
    }

    private static List<String> getContacts() {
        Path datafile = Paths.get("data", "contacts.txt");
        try {
            List<String> contacts = Files.readAllLines(datafile);
//            System.out.println(contacts);
            System.out.println("Name           | Phone number");
            System.out.println("-----------------------------");
            for (String contact : contacts) {
                System.out.println(contact);
            }
            return contacts;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1:
                getContacts();
                break;
            case 2:
                addContacts();
                break;
            case 3:

                break;
            case 4:
                break;
            case 5:
                break;
            default:
                System.out.println("That is not an option");

        }
    }

    private static void addContacts() {
        try {
            System.out.println("Enter your name and phone number seperated by a comma");
            Path contactsPath = Paths.get("data", "contacts.txt");

            String input = sc.nextLine();
            Files.write(contactsPath, Arrays.asList(input), StandardOpenOption.APPEND);
            List<String> contactsList = Files.readAllLines(contactsPath);

            for (int i = 0; i < contactsList.size(); i += 1) {
                System.out.println((i + 1) + ": " + contactsList.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
