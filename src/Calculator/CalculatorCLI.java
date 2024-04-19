/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Calculator;

/**
 *
 * @author Gertrude
 */
import java.util.Scanner;

class Calculator {
    public int addition(int a, int b) {
        return a + b;
    }

    public int subtraction(int a, int b) {
        return a - b;
    }

    public int multiplication(int a, int b) {
        return a * b;
    }

    public int division(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Division by zero!");
        }
        return a / b;
    }
}

public class CalculatorCLI {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        System.out.print(" Calcualtor that do any operations of 2 numbers \n");
        System.out.print("================================================ \n\n");
        System.out.print("Enter value for First Number: ");
        int a = scanner.nextInt();
        System.out.print("Enter value for Second Number: ");
        int b = scanner.nextInt();

        while (!exit) {            
            int[] results = new int[4]; // Array to store results

            // Perform calculations
            results[0] = calculator.addition(a, b);
            results[1] = calculator.subtraction(a, b);
            results[2] = calculator.multiplication(a, b);
            results[3] = calculator.division(a, b);

            // Display results
            System.out.println("\nChoose operation:");
            System.out.println("1. Addition");
            System.out.println("2. Subtraction");
            System.out.println("3. Multiplication");
            System.out.println("4. Division");
            System.out.println("5. Change values of a and b");
            System.out.println("6. Exit");
            System.out.print("Enter operation number: ");
            int operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    System.out.println("\nAddition = " + results[0]);
                    break;
                case 2:
                    System.out.println("\nSubtraction = " + results[1]);
                    break;
                case 3:
                    System.out.println("\nMultiplication = " + results[2]);
                    break;
                case 4:
                    try {
                        System.out.println("\nDivision = " + results[3]);
                    } catch (ArithmeticException e) {
                        System.out.println("Error: " + e.getMessage());
                        results[3] = Integer.MIN_VALUE; // Mark error result
                    }
                    break;
                case 5:
                    // Change values of a and b
                    System.out.print("Enter new value for First Number: ");
                    a = scanner.nextInt();
                    System.out.print("Enter new value for Second Number: ");
                    b = scanner.nextInt();
                    break;
                case 6:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid operation!");
            }
        }

        scanner.close();
    }
}
