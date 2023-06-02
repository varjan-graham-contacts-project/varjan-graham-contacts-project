import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.nio.file.Files;



public class ContactsApp {

    public static Path contactsPath = Paths.get("data", "contacts.txt");

    public static Scanner sc = new Scanner(System.in);
    public static int choice;
    public static void main(String[] args) {
        consoleUI();
    }

    private static void askAgain() {
        System.out.println("Would you like to make another selection? [y/n]");
        boolean keepGoing = sc.nextLine().equalsIgnoreCase("y");
        if(keepGoing) {
            consoleUI();
        } else {
            System.out.println("Closing Program");
        }

    }

//    private static void writeData() {
//        HashMap<String, String> contactInfo = new HashMap<>();
//        contactInfo.put("Ryan", "1234567");
//        System.out.println(contactInfo);
/
//    }


    private static void consoleUI() {
        System.out.println("1. View contacts.");
        System.out.println("2. Add a new contact.");
        System.out.println("3. Search a contact by name.");
        System.out.println("4. Delete an existing contact.");
        System.out.println("5. Exit.");
        System.out.println("Enter an option (1, 2, 3, 4 or 5):");
        choice = Integer.parseInt(sc.nextLine());
        if(choice > 5 || choice < 1) {
            System.out.println("That is not an option");
            consoleUI();
        }
        handleChoice(choice);

    }

    private static List<String> getContacts() {
        Path datafile = Paths.get("data", "contacts.txt");
        try {
            List<String> contacts = Files.readAllLines(datafile);
            System.out.println("Name           | Phone number");
            System.out.println("-----------------------------");
            for (String contact : contacts) {
                System.out.println(contact);
//                String contactArray = Arrays.toString(contact.split(","));
                //TODO need to save name and number to a variable
//                System.out.println(contactArray);
//                String[] ary = contactArray.split(",");
//                System.out.println(ary);
//                System.out.println(ary[0]);
//                System.out.println(ary[1]);
            }
            return contacts;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    private static void handleChoice(int choice) {
        switch (choice) {
            case 1 -> {
                getContacts();
                askAgain();
            }
            case 2 -> {
                addContacts();
                askAgain();
            }
            case 3 -> {
//                TODO need to implement search
                System.out.println("Sorry still in development");
                askAgain();
            }
            case 4 -> {
                deleteContact();
                askAgain();
            }
            case 5 -> System.out.println("Quitting Program");
        }
    }

    private static void deleteContact() {
        System.out.println("Enter a contact name and its number to delete");
        String contactName = sc.nextLine();
        List<String> lines = null;
        try {
            lines = Files.readAllLines(contactsPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        List<String> newList = new ArrayList<>();

        for (String line : lines) {
            if (line.equals(contactName)) {
                newList.remove(line);
                continue;
            }
            newList.add(line);
        }

        try {
            Files.write(contactsPath, newList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void addContacts() {
        try {
            System.out.println("Enter your name and phone number seperated by a comma");
            String input = sc.nextLine();
            Files.write(contactsPath, Arrays.asList(input), StandardOpenOption.APPEND);
            List<String> contactsList = Files.readAllLines(contactsPath);
//            System.out.println(contactsList);
            for (int i = 0; i < contactsList.size(); i += 1) {
                System.out.println((i + 1) + ": " + contactsList.get(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


}
