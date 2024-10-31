package controller;

import model.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class ProductMng {
    static Scanner scanner = new Scanner(System.in);
    public static void addProductToFile(String filePath) {
        List<Product> productList = getProductsFromFile(filePath);
        try {
            enterProductInfo(productList);

            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }

            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(productList);

            oos.close();
            fos.close();

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        } catch (IOException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        } catch (InputMismatchException e) {
            System.err.println("Not a number");
        }
    }

    private static List<Product> getProductsFromFile(String filePath) {
        List<Product> productList = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (file.length() == 0) {
                System.out.println("The list is empty.");
                return productList;
            }

            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //noinspection unchecked
            productList = (List<Product>) ois.readObject();
            fis.close();
            ois.close();
        } catch (EOFException e) {
            System.out.println("No products found. The list is empty.");
        }
        catch (IOException | ClassNotFoundException e) {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        return productList;
    }

    private static void enterProductInfo(List<Product> productList) {
        int productId;
        boolean idFound = false;
        do {
            System.out.print("Enter product id: ");
            productId = scanner.nextInt();
            scanner.nextLine();
            for (Product product : productList) {
                if (product.getId() == productId) {
                    System.out.println("Product with Id " + productId + " already exists");
                    idFound = true;
                    break;
                } else {
                    idFound = false;
                }
            }
        } while (idFound);
        System.out.print("Enter name: ");
        String productName = scanner.nextLine();
        System.out.print("Enter price: ");
        double productPrice = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter manufacturer: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Enter description: ");
        String productDescription = scanner.nextLine();
        productList.add(new Product(productId, productName, productPrice, manufacturer, productDescription));
    }

    public static void showProductList(String filePath) {
        List<Product> productList = getProductsFromFile(filePath);
        if (productList.isEmpty()) {
            System.out.println("No products found.");
        } else {
            System.out.println("The list has '" + productList.size() + "' products.");
            for (Product product : productList) {
                System.out.println(product);
            }
        }
    }

    public static void searchProduct(String filePath) {
        List<Product> productList = getProductsFromFile(filePath);
        if (productList.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        boolean found = false;
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();
        for (Product product : productList) {
            if (product.getName().equals(productName)) {
                System.out.println("Searched Product: ");
                System.out.println(product);
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Product "+ productName + " is not in the list.");
        }
    }
}
