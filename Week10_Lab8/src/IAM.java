import java.sql.SQLOutput;
import java.util.Scanner;
public class IAM {
    private static final Scanner keyboard = new Scanner(System.in);
    public static String allQualites = "The qualities are ";

    public static void main(String[] args) {

        System.out.println("WEEK 10 - LAB 8");

        System.out.println("Please enter sentences, . to end.");
       String sentence = keyboard.nextLine();

        while(!sentence.equals(".")){

            if( sentence.startsWith("I am")) {
                String quality = sentence.substring(5);
                allQualites = allQualites + quality + ", ";
            }
            sentence = keyboard.nextLine();
        }

        System.out.println(allQualites);
    }// end of the Main Method


}// end of the IAM class
