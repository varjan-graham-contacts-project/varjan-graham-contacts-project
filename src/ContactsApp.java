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
///
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

//    private static List<String> getContacts() {
//        Path datafile = Paths.get("data", "contacts.txt");
//        try {
//            List<String> contacts = Files.readAllLines(datafile);
//            System.out.println("Name           | Phone number");
//            System.out.println("-----------------------------");
//            for (String contact : contacts) {
//                System.out.println(contact);
////                String contactArray = Arrays.toString(contact.split(","));
//                //TODO need to save name and number to a variable
////                System.out.println(contactArray);
////                String[] ary = contactArray.split(",");
////                System.out.println(ary);
////                System.out.println(ary[0]);
////                System.out.println(ary[1]);
//            }
//            return contacts;
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//
//    }
private static List<String> getContacts() {
    Path datafile = Paths.get("data", "contacts.txt");
    try {
        List<String> contacts = Files.readAllLines(datafile);
        System.out.println("Name           | Phone number");
        System.out.println("-----------------------------");
        for (String contact : contacts) {
            String[] contactInfo = contact.split(",");
            if (contactInfo.length == 2) {
                String name = contactInfo[0].trim();
                String phoneNumber = contactInfo[1].trim();
                String formattedPhoneNumber = formatPhoneNumber(phoneNumber);
                System.out.println(name + " | " + formattedPhoneNumber);
            }
        }
        return contacts;
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}

    private static String formatPhoneNumber(String phoneNumber) {
        // Remove any non-digit characters
        String digitsOnly = phoneNumber.replaceAll("\\D+", "");

        // Determine the appropriate format based on the number of digits
        if (digitsOnly.length() == 10) {
            // Format for 10-digit phone numbers: XXX-XXX-XXXX
            return digitsOnly.substring(0, 3) + "-" + digitsOnly.substring(3, 6) + "-" + digitsOnly.substring(6);
        } else if (digitsOnly.length() == 7) {
            // Format for 7-digit phone numbers: XXX-XXXX
            return digitsOnly.substring(0, 3) + "-" + digitsOnly.substring(3);
        } else {
            // Return the original phone number if it doesn't match the expected lengths
            return phoneNumber;
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
                searchContact();
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

//    private static void addContacts() {
//        try {
//            System.out.println("Enter your name and phone number seperated by a comma");
//            String input = sc.nextLine();
//            Files.write(contactsPath, Arrays.asList(input), StandardOpenOption.APPEND);
//            List<String> contactsList = Files.readAllLines(contactsPath);
////            System.out.println(contactsList);
//            for (int i = 0; i < contactsList.size(); i += 1) {
//                System.out.println((i + 1) + ": " + contactsList.get(i));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException(e);
//        }
//    }
private static void addContacts() {
    try {
        System.out.println("Enter your name and phone number separated by a comma");
        Path contactsPath = Paths.get("data", "contacts.txt");

        String input = sc.nextLine();
        String[] contactInfo = input.split(",");
        if (contactInfo.length == 2) {
            String name = contactInfo[0].trim();
            String phoneNumber = contactInfo[1].trim();

            List<String> contactsList = Files.readAllLines(contactsPath);

            // Check if a contact with the same name already exists
            for (int i = 0; i < contactsList.size(); i++) {
                String existingContact = contactsList.get(i);
                String existingName = existingContact.split(",")[0].trim();
                if (existingName.equalsIgnoreCase(name)) {
                    System.out.println("There's already a contact named " + name + ".");
                    System.out.print("Do you want to overwrite it? (Yes/No): ");
                    String overwriteChoice = sc.nextLine().trim().toLowerCase();
                    if (overwriteChoice.equals("yes")) {
                        // Overwrite the existing contact
                        contactsList.set(i, input);
                        Files.write(contactsPath, contactsList);
                        System.out.println("Contact updated successfully.");
                    } else {
                        System.out.println("Contact not overwritten.");
                    }
                    return;
                }
            }

            // Add the new contact if no existing contact with the same name was found
            Files.write(contactsPath, Arrays.asList(input), StandardOpenOption.APPEND);
            System.out.println("Contact added successfully.");
        } else {
            System.out.println("Invalid input format. Please provide name and phone number separated by a comma.");
        }
    } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException(e);
    }
}

    private static void searchContact() {
        System.out.println("Enter the name to search:");
        String searchName = sc.nextLine();

        List<String> contactsList = getContacts();

        boolean found = false;
        for (String contact : contactsList) {
            String[] contactInfo = contact.split(",");
            if (contactInfo.length == 2) {
                String name = contactInfo[0].trim();
                if (name.equalsIgnoreCase(searchName)) {
                    String phoneNumber = contactInfo[1].trim();
                    String formattedPhoneNumber = formatPhoneNumber(phoneNumber);
                    System.out.println("Contact found:");
                    System.out.println("Name: " + name);
                    System.out.println("Phone number: " + formattedPhoneNumber);
                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("Contact not found.");
        }
    }

}
