/**
 *
 * @author Kiori McPhee
 */
import java.util.Scanner;
import java.util.Scanner;

public class DentalRecords {
    private static final Scanner keyboard = new Scanner(System.in);

    public static final  int MAX_FAMILY = 6;
    public static final int MAX_TEETH = 8;

    static String[] family;
    static char[][][] teethData;


    public static void main(String[] args) {
        Scanner scanner = new Scanner (System.in) ;

        System.out.println("Welcome to the Floridian Tooth Records");
        System.out.println("---------------------------------------");



        family = new String[MAX_FAMILY];
        teethData = new char[MAX_FAMILY][2][MAX_TEETH];
        int familySize = getFamilySize(scanner);

        for(int index = 0; index < familySize; index++){
        family[index] = getName(scanner, index);
        teethData[index][0] = getTeeth(scanner,"uppers", family[index]);
        teethData[index][1] = getTeeth(scanner, "lowers", family[index]);
        }

        char menuChoices;
        do{
            menuChoices  = getMenuChoices(scanner);
            switch (menuChoices) {
                case 'P':
                    printFamilyTeeth(family, teethData, familySize);
                    break;
                case 'E':
                    extractTooth(scanner, teethData, family);
                    break;
                case 'R':
                    reportRootCanal(teethData, familySize);
                    break;
                case 'X':
                    System.out.println("Exiting the Floridian Tooth Records :-)");
                default:
                    System.out.println("Invalid menu option, try again");
            }
        }while (menuChoices != 'X');


    }// End of Main Method


    static int getFamilySize (Scanner scanner){

        int size = 0;
        while(size < 1 || size > MAX_FAMILY) {
            System.out.println("Please enter the number of people in vour family: ");
            size = scanner.nextInt();
            if (size < 1 || size > MAX_FAMILY){
                System.out.println("Invalid number of people. Please try again.");
            }
        }// while loop end
        return size;
    }// end of getFamilySize method



    static String getName(Scanner scanner, int memberIndex) {

            System.out.println("Please enter the name for family member" + (memberIndex + 1) + ": ");
            return scanner.next();
        }// end of getName method


    static char [] getTeeth(Scanner scanner, String layer, String getName){//

        char[] teeth = new char [MAX_TEETH];
        boolean validInput = false;
        while (!validInput){
            System.out.print("Please enter the " + layer + " for " + getName + ": ");
            String input = scanner.next().toUpperCase();
            if(input.length() <= MAX_TEETH) {
                validInput = true;
                for (int index = 0; index < input.length(); index++) {
                    char toothType = input.charAt(index);
                    if (toothType != 'I' && toothType != 'B' && toothType != 'M') {
                        validInput = false;
                        break;
                    }
                    teeth[index] = toothType;
                }
                if (validInput) {
                    return teeth;
                }
            }
            else{
                System.out.println("Too many teeth, try again ");
            }
            System.out.println("Invalid teeth types, try again ");
        }// end of the while loop
        return teeth;
    }// end of the getTeeth method

    static char getMenuChoices (Scanner scanner){

        System.out.print(" (P)rint, (E)xtract, (R)oot, e(X)it: ");
        return scanner.next().toUpperCase().charAt (0);
}//end of the getMenu0fChoices method


    private static void printFamilyTeeth(String[] family, char[][][] teethData, int familySize) {

        for (int index = 0; index < familySize; index++) {
            System.out.println(family[index]);
            System.out.print("  Uppers: ");
            for (int upperToothIndex = 0; upperToothIndex < MAX_TEETH; upperToothIndex++) {
                char tooth = teethData[index][0][upperToothIndex];
                if (tooth != '\u0000') {
                    System.out.print((upperToothIndex + 1) + ":" + tooth + "  ");
                }
            }//end of if loop
            System.out.println();
            System.out.print("  Lowers: ");
            for (int lowerToothIndex = 0; lowerToothIndex < MAX_TEETH; lowerToothIndex++) {
                char tooth = teethData[index][1][lowerToothIndex];
                if (tooth != '\u0000') {
                    System.out.print((lowerToothIndex + 1) + ":" + tooth + "  ");
                }

            }// end of second for loop
            System.out.println();
        }// end of first for loop
    }//end of printTeethRecords method

    private static void extractTooth(Scanner scanner,char[][][] teethData, String[] family) {

        System.out.print("Which family member                         : ");
        String memberName = scanner.next();
        int memberIndex = findFamilyMember(family, memberName);

        if (memberIndex == -1) {//check if valid family member was found
            System.out.println("Invalid family member, try again");
            return;
        }//end of if statement

        System.out.print("Which tooth layer (U)pper or (L)ower        : ");
        char layer = scanner.next().toUpperCase().charAt(0);

        int row = (layer == 'U') ? 0 : (layer == 'L') ? 1 : -1;
        if (row == -1) {
            System.out.println("Invalid layer, try again");
            return;
        }

        System.out.print("Which tooth number                          : ");
        int toothNum = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (toothNum < 1 || toothNum > MAX_TEETH) {
            System.out.println("Invalid tooth number, try again");
            return;
        }

        if (teethData[memberIndex][row][toothNum - 1] == 'M') {
            System.out.println("Missing tooth, try again");
        } else {
            teethData[memberIndex][row][toothNum - 1] = 'M';
            System.out.println("Tooth extracted successfully.");
        }
    }//end of the extractTooth method



    private static int findFamilyMember(String[] family, String memberName) {

        for (int index = 0; index < family.length; index++) {
            if (family[index] != null && family[index].equalsIgnoreCase(memberName)) {
                return index;
            }
        }// end of for loop
        return -1;
    }//end of findFamilyMember method

    private static void reportRootCanal(char[][][] teethData, int familySize) {

        int iCount = 0, bCount = 0, mCount = 0;
        for (char[][] personTeeth : teethData) {  // corrected
            for (int row = 0; row < 2; row++) {
                for (int tooth = 0; tooth < MAX_TEETH; tooth++) {
                    if (personTeeth[row][tooth] == 'I') iCount++;
                    if (personTeeth[row][tooth] == 'B') bCount++;
                    if (personTeeth[row][tooth] == 'M') mCount++;
                    }// end of second for loop
                }//end of first for loop
            }//end of reportRootCanal method

            double discriminant = bCount * bCount + 4 * iCount * mCount;
            if (discriminant < 0) {
                System.out.println("No real roots for root canal indices.");
            } else {
                double root1 = (-bCount + Math.sqrt(discriminant)) / (2 * iCount);
                double root2 = (-bCount - Math.sqrt(discriminant)) / (2 * iCount);
                System.out.printf("One root canal at     %.2f\n", root1);
                System.out.printf("Another root canal at %.2f\n", root2);
            }//end of if-else
        }//end of reportRootCanal method


}// End of Public class