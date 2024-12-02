
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * The BoatRecords class manages a fleet of boats, allowing users to perform operations
 * like adding, removing, and printing boat inventory, as well as requesting expense permissions.
 * @author Kiori McPhee
 */
public class BoatRecords {

    // maximum values for the inputs
    public static final double MAX_PURCHASE_PRICE = 1000000.0;
    public static final double MAX_LENGTH_IN_FEET = 100.0;
    public static final String DATA_FILE_NAME = "FleetData";

    private static final Scanner keyboard = new Scanner(System.in);

    /**
     * @param args Passed in from the command line
     * The main method runs the Fleet Management System.
     */

    public static void main(String[] args) {
        ArrayList<Boat> boatList;
        boatList = new ArrayList<>();
if (args.length >0) {
    String fleetData = args[0];

    readCSV(fleetData, boatList);
}else {
    loadDBFileSavedData(boatList);
}// end of if else statement


        System.out.println("Welcome to the Fleet Management System"); // Welcome message
        System.out.println("--------------------------------------");

        char menuChoices;

        do {
            menuChoices = getMenuChoices(keyboard);
            switch (menuChoices) {
                case 'P': // Print the inventory
                    printBoatInventory(boatList);
                    break;
                case 'A': // Add a new boat
                    addBoat(boatList);
                    break;
                case 'R': // Remove boat from inventory
                    removeBoat(boatList);
                    break;
                case 'E': // Request permission to spend money on a boat
                    requestPermission( boatList);
                    break;
                case 'X': // Exit the program
                    exitProgram( boatList);
                    break;
                default: // Handle other inputs
                    if (menuChoices !='X'){
                        System.out.println("Invalid menu option, try again");
                    }
                    break;
            }
        } while (menuChoices != 'X'); // Allow loop to continue until X is selected
    }//end of the main method

    /**
     * Reads data from a CSV file and populates the boat inventory.
     *
     * @param fleetData The path to the CSV file.
     * @param boatList  The list to which boats will be added and read from.
     */

    public static void readCSV(String fleetData, ArrayList<Boat> boatList) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fleetData))) {
            String currentLine;
            currentLine = reader.readLine();

            while (currentLine != null) {
                if (!currentLine.contains(",")) {
                    System.out.println("Invalid line in file (not CSV format): " + currentLine);
                    currentLine = reader.readLine();
                    continue;
                }

                String[] userInputArray = currentLine.split(",");
                try {
                    Boat boat = new Boat(
                            Boat.BoatType.valueOf(userInputArray[0].toUpperCase()),
                            userInputArray[1],
                            Integer.parseInt(userInputArray[2]),
                            userInputArray[3],
                            Double.parseDouble(userInputArray[4]),
                            Double.parseDouble(userInputArray[5])
                    );
                    boatList.add(boat);
                } catch (NumberFormatException e) {
                    System.out.println("Number error in line: " + currentLine);
                } catch (IllegalArgumentException e) {
                    System.out.println("Boat type error in line: " + currentLine);
                }

                currentLine = reader.readLine();
            }

            System.out.println("File closed");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

}// end of readCSV method

    /**
     * Prompts the user for a menu choice and returns it as an uppercase character
     * no matter if the input was lower or upper case.
     *
     * @param scanner The Scanner object for user input
     * @return The user's menu choice as a char
     */

    private static char getMenuChoices(Scanner scanner) {
        System.out.print(" (P)rint, (A)dd, (R)emove, (E)xpense, e(X)it: ");
        return scanner.next().toUpperCase().charAt(0);
    }//end of the getMenuChoices method

    /**
     * @ param boatList The list to which the boat's information will be printed from
     * Used if the user inputs a letter P into the menu options
     * Prints a report of all boats in the inventory, including their total costs.
     */

    private static void printBoatInventory(ArrayList<Boat> boatList) {
        System.out.println("\nFleet report:");
        double totalPaid = 0;
        double totalSpent = 0;
        for (Boat boat : boatList) {
            System.out.println(boat);
            totalPaid += boat.getPurchasePrice();
            totalSpent += boat.getExpenses();
        }
        System.out.printf("    Total                                             : Paid $%.2f | Spent $%.2f\n",
                totalPaid, totalSpent);
    }// end of the printBoatInventory

    /**
     * Loads saved boat inventory data from a serialized database file.
     *
     * @param boatList The list to which loaded boats will be added.
     */

    private static void loadDBFileSavedData(ArrayList<Boat> boatList){
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(DATA_FILE_NAME))){
            boatList.clear();
            List<Boat> loadedFleet = (List<Boat>) objectInputStream.readObject();
            boatList.addAll(loadedFleet);
        }catch (IOException | ClassNotFoundException e){
            //print error message if needed
            System.out.println("Error loading database file: " + e.getMessage());

        }
    }//end of loadDBFileSavedData

    /**
     * Saves the current boat inventory to a database file.
     *
     * @param boatList The list of boats to be saved.
     */
    private static void saveFleetDataToDB(ArrayList<Boat> boatList){
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(DATA_FILE_NAME))) {
            objectOutputStream.writeObject(boatList);

        }catch (IOException e){
            System.out.println("Error saving to data base file: " + e.getMessage());
        }

    }//end of saveFleetDataDB

    /**
     * @param boatList The list of boats which will be referenced for spending allowances
     * if the user inputs E into the menu choices
     * Allows the user to request permission to spend money on a specific boat, but no more than original cost
     */

    private static void requestPermission(ArrayList<Boat> boatList) {
        System.out.print("Which boat do you want to spend on?         : ");
        keyboard.nextLine(); // Clear the newline character
        String boatName = keyboard.nextLine();
        for (Boat boat : boatList) {
            if (boat.getName().equalsIgnoreCase(boatName)) {
                System.out.print("How much do you want to spend?              : ");
                double amount = keyboard.nextDouble();
                keyboard.nextLine(); // Consume the newline character
                if (boat.canSpend(amount)) {
                    boat.spend(amount);
                    System.out.printf("Expense authorized, $%.2f spent.%n", amount);
                } else {
                    System.out.printf("Expense not permitted, only $%.2f left to spend.%n", boat.getRemainingBudget());
                }
                return;
            }
        }
        System.out.println("Cannot find boat " + boatName);
    }// end of the requestPermission method

    /**
     *@param boatList The list of boats that the new boat will be added to.
     *  if the user inputs A into the menu choices
     * Adds a new boat to the inventory based on user input in CSV format.
     */

    private static void addBoat(ArrayList<Boat> boatList) {
        System.out.print("Please enter the new boat CSV data          : ");
        keyboard.nextLine(); // Consume the newline character
        String newCsv = keyboard.nextLine();
        try {
            String[] data = newCsv.split(",");
            Boat.BoatType type = Boat.BoatType.valueOf(data[0].toUpperCase());
            String name = data[1];
            int yearOfManufacture = Integer.parseInt(data[2]);
            String makeModel = data[3];
            double length = Double.parseDouble(data[4]);
            double price = Double.parseDouble(data[5]);

            if (length > MAX_LENGTH_IN_FEET || price > MAX_PURCHASE_PRICE) {
                System.out.println("Invalid input: Boat exceeds maximum allowed length or price.");
                return;
            }

            boatList.add(new Boat(type, name, yearOfManufacture, makeModel, length, price));
            System.out.println("Boat added successfully.");
        } catch (Exception e) {
            System.out.println("Error: Invalid input format. Please follow the correct CSV format.");
        }// end of the try catch
    }// end of the addBoat method

    /**
     * @param boatList The list of boats to be removed from
     * If the user inputs R into the meny choices
     * Removes a boat from the inventory based on its name.
     */

    private static void removeBoat(ArrayList<Boat> boatList) {
        System.out.print("Which boat do you want to remove?           : ");
        keyboard.nextLine(); // Consume the newline character
        String boatName = keyboard.nextLine();
        boolean removedBoat = false;

        for (int index = 0; index < boatList.size(); index++) {
            if (boatList.get(index).getName().equalsIgnoreCase(boatName)) {
                boatList.remove(index);
                removedBoat = true;
                break;
            }
        }// end of the for loop

        if (removedBoat) {
            System.out.println("Boat removed.");
        } else {
            System.out.println("Cannot find boat " + boatName);
        }
    }// end of the removeBoat method

    /**
     * Exits the program by saving the current boat inventory and displaying an exit message.
     *
     * @param boatList The list of boats to be saved before exiting.
     */

    private static void exitProgram (ArrayList<Boat> boatList){
        saveFleetDataToDB(boatList);
        System.out.println(" Exiting the Fleet Management System");
    }
}//end of the Boat records class
