package views;

import controller.ProductMng;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Product Manage:");
            System.out.println("1. Add Product");
            System.out.println("2. Show Product List");
            System.out.println("3. Search Product");
            System.out.println("4. Exit");

            switch (getChoice(scanner)) {
                case 1:
                    System.out.println("Adding product");
                    ProductMng.addProductToFile("data/product.txt");
                    break;
                case 2:
                    System.out.println("Products list:");
                    ProductMng.showProductList("data/product.txt");
                    break;
                case 3:
                    System.out.println("Searching product");
                    ProductMng.searchProduct("data/product.txt");
                    break;
                case 4:
                    System.out.println("Exit program");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static int getChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Enter your choice: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine();
            }
        }
        return choice;
    }
}
