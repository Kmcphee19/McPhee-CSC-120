/**
 * to catalog a boats specific attributes and provide methods to edit that catalog based on information inputed
 * @author Kiori McPhee
 */

import java.io.Serializable;

/**
 * The Boat class represents a boat with specific attributes and provides methods
 * to manage its expenses while adhering to budget constraints. It implements the
 * Serializable interface to allow saving and loading of boat objects.
 */

public class Boat implements Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    public enum BoatType {SAILING, POWER}

    private BoatType type;
    private String name;
    private int yearOfManufacture;
    private String makeModel;
    private double length;
    private double purchasePrice;
    private double expenses;

    /**
     * Constructs a new Boat object with the specified attributes and initializes
     * its expenses to zero.
     *
     * @param type            The type of the boat (SAILING or POWER).
     * @param name            The name of the boat.
     * @param yearOfManufacture The year the boat was manufactured.
     * @param makeModel       The make or model of the boat.
     * @param length          The length of the boat in feet.
     * @param purchasePrice   The purchase price of the boat.
     */

    public Boat(BoatType type, String name, int yearOfManufacture, String makeModel, double length, double purchasePrice){
        this.type = type;
        this.name = name;
        this.yearOfManufacture = yearOfManufacture;
        this.makeModel = makeModel;
        this.length = length;
        this.purchasePrice = purchasePrice;
        this.expenses = 0.0;

    }// end of Boat method

    /**
     * Gets the name of the boat.
     *
     * @return The name of the boat.
     */

    public String getName(){
        return name;
    }// end of getName method

    /**
     * Gets the purchase price of the boat.
     *
     * @return The purchase price of the boat.
     */

    public double getPurchasePrice(){
        return purchasePrice;
    }// end of getPurchasePrice method

    /**
     * Determines if the specified amount can be spent on the boat without exceeding
     * its purchase price.
     *
     * @param amount The amount to spend.
     * @return True if the amount can be spent, otherwise false.
     */

    public boolean canSpend(double amount){
        return (expenses + amount) <= purchasePrice;
    }// end of canSpend

    /**
     * Adds the specified amount to the boat's total expenses.
     *
     * @param amount The amount to spend on the boat.
     */

    public void spend(double amount){
        this.expenses += amount;
    }// end of spend method

    /**
     * Gets the remaining budget for the boat based on its purchase price and current expenses.
     *
     * @return The remaining budget.
     */

    public double getRemainingBudget(){
        return purchasePrice - expenses;
    } // end of getRemainingBudget method

    /**
     * Gets the total expenses incurred for the boat.
     *
     * @return The total expenses.
     */

    public double getExpenses(){
        return expenses;
    }// end of getExpenses
    /**
     * Adds old expenses to new expenses
     *
     */
public void updateExpenses( double newExpenses){
        expenses = expenses + newExpenses;
}// end of the updateExpenses
    /**
     * Returns a formatted string representation of the boat's details.
     *
     * @return A string containing the boat's attributes and financial details.
     */

    @Override
    public String toString() {
        return String.format("%-8s %-20s %4d %-10s %3.0f' : Paid $%10.2f : Spent $%10.2f",
                type, name, yearOfManufacture, makeModel, length, purchasePrice, expenses);
    }
}// end of the Boat class
