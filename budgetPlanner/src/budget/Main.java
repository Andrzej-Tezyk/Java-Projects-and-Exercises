package budget;

import java.io.File;

import java.io.FileNotFoundException; // obsluga wyjatkow

import java.io.PrintWriter;
//drukuje sformatowane reprezentacje obiektow w strumieniu tekstowym, https://pl.ichlese.at/what-is-printwriter-java
import java.util.*;

public class Main {
	
    static double balance = 0;
    static ArrayList<String> list = new ArrayList<>();
    static PurchaseType<String> food, clothes, entertainment, other, all;
    static File file = new File("C:/Users/andre/OneDrive/Desktop/Projekt_java/zakupy.txt");
    public static void main(String[] args) {
    	
        Scanner scanner = new Scanner(System.in);
        
        String menu = "Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Status\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit";
        String purchaseType = "\nChoose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) Back";
        String showPurhaseList = "\nChoose the type of purchases\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n" +
                "5) All\n" +
                "6) Back";
        String sortOptions = "How do you want to sort?\n" +
                "1) Sort all purchases\n" +
                "2) Sort by type\n" +
                "3) Sort certain type\n" +
                "4) Back";
        String statusOptions = "What do you want to show?\n" +
                "1) Balance\n" +
                "2) Money left after purchases\n" +
                "3) Back";
        
        boolean exit = false;
        while (!exit) {
        	
        	System.out.println(menu);
            int choice;

            while (true) {
              System.out.print("Enter an integer: ");
              if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                break;
              } else {
                System.out.println("Invalid input. Try again.");
                scanner.nextLine();
              }
            }      
            
            switch (choice) {
                case 1:
                    addIncome(scanner);
                    break;
                    
                case 2:
                    boolean back = false;
                    while (!back) {
                        System.out.println(purchaseType); //menu
                        int purchaseTypeNumber = scanner.nextInt();
                        System.out.println();

                        switch (purchaseTypeNumber) {
                            case 1:
                                if (food == null) {
                                    food = new PurchaseType<>();
                                }
                                food.readProductnPrice();
                                break;
                                
                            case 2:
                                if (clothes == null) {
                                    clothes = new PurchaseType<>();
                                }

                                clothes.readProductnPrice();
                                break;
                                
                            case 3:
                                if (entertainment == null) {
                                    entertainment = new PurchaseType<>();
                                }
                                entertainment.readProductnPrice();
                                break;
                                
                            case 4:
                                if (other == null) {
                                    other = new PurchaseType<>();
                                }
                                other.readProductnPrice();
                                break;
                                
                            case 5:
                                back = true;
                                break;
                        }
                    }

                    break;
                    
                case 3:
                    boolean quit = false;
                    while (!quit) {

                        System.out.println(showPurhaseList);
                        int showPurchaseListNumber = scanner.nextInt();
                        System.out.println();
                        switch (showPurchaseListNumber) {
                            case 1:
                                showList("Food:", food);
                                break;
                                
                            case 2:
                                showList("Clothes:", clothes);
                                break;
                                
                            case 3:
                                showList("Entertainment:", entertainment);
                                break;
                                
                            case 4:
                                showList("Other:", other);
                                break;
                                
                            case 5:

                                System.out.println("All:");
                                System.out.println("+------------------+------------+");
                                System.out.println("| Name             | Price      |");
                                System.out.println("+------------------+------------+");
                                PurchaseType.allList.forEach((name, price) -> {
                                    System.out.printf("| %-16s | $%.2f \t|\n", name.trim(), price);
                                });
                                System.out.println("+------------------+------------+");
                                System.out.printf("| Total sum        | $%.2f \t|\n", PurchaseType.allProductsTotal);
                                System.out.println("+------------------+------------+");
                                break;                                
                                
                            case 6:
                                quit = true;
                                break;
                        }
                    }

                    break;
                    
                case 4:
                	boolean backCase4 = false;
                    while (!backCase4) {
	                    System.out.println(statusOptions);
	                    int statusOptionsCase = scanner.nextInt();
	                    System.out.println();
	                      
	                    switch (statusOptionsCase) {
	                    	case 1: 
	                          System.out.printf("balance: $%.2f\n\n", balance);
	                          break;
	                          // balance bez dzialan
	                          
	                    	case 2:
	                          double balanceAfterPurchases = balance - PurchaseType.allProductsTotal;
	                          System.out.printf("balance after purchases: $%.2f\n\n", balanceAfterPurchases);
	                          break;
	                          // odejmujemy wszystkie zakupy
	                          
	                        case 3:
	                          backCase4 = true;
	                          break;
	                      }
                    }
                    break;
     
                case 5:
                    System.out.println("Saving");
                    
                    try (PrintWriter printWriter = new PrintWriter(file)) {
                        printWriter.print("balance\n" + (balance) + "\n");
                        saveList("Food", food, printWriter);
                        saveList("Clothes", clothes, printWriter);
                        saveList("Entertainment", entertainment, printWriter);
                        saveList("Other", other, printWriter);
                        System.out.println("Purchases were saved!");
                        printWriter.write("");
                        printWriter.flush(); //przeplukanie strumienia
                    } catch (FileNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                    // wykonuje sie jezeli bez blendu, w przeciwnym przypadku wyjatek


                    break;

                case 6:
                    File fileRead = new File("C:/Users/andre/OneDrive/Desktop/Projekt_java/zakupy.txt");
                    loadPurchases(fileRead);
                    System.out.println("Purchases were loaded!\n");
                    break;
                    
                case 7:
                    boolean quitFromSort = false;
                    while (!quitFromSort) {
                        System.out.println(sortOptions);

                        int sortOption = scanner.nextInt();

                        switch (sortOption) {
                            case 1:
                                System.out.println();
                                if (isNull(PurchaseType.allList.isEmpty())) break;
                                SortedMap<String, Double> map = new TreeMap<>(PurchaseType.getAllList());
                                System.out.println("All:");
                                sort(map);
                                System.out.printf("Total: $%.2f\n", PurchaseType.allProductsTotal);
                                System.out.println();
                                break;
                                
                            case 2:

                                System.out.println();
                                System.out.println("Types:");
                                SortedMap<String, Double> categoryMap = new TreeMap<>();
                                if (food != null) {
                                    categoryMap.put("Food", food.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Food", 0.0);
                                if (clothes != null) {
                                    categoryMap.put("Clothes", clothes.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Clothes", 0.0);
                                if (entertainment != null) {
                                    categoryMap.put("Entertainment", entertainment.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Entertainment", 0.0);
                                if (other != null) {
                                    categoryMap.put("Other", other.getTotalPrice());
                                }
                                categoryMap.putIfAbsent("Other", 0.0);
                                ValueComparator bvc = new ValueComparator(categoryMap);
                                TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);

                                sorted_map.putAll(categoryMap);
                                sorted_map.forEach((key, value) -> System.out.printf("%s - $%.2f\n", key, value));

                                System.out.printf("Total sum: $%.2f\n\n", PurchaseType.allProductsTotal);
                                break;
                                
                            case 3:

                                System.out.println("\nChoose the type of purchase\n" +
                                        "1) Food\n" +
                                        "2) Clothes\n" +
                                        "3) Entertainment\n" +
                                        "4) Other");
                                int sortCatType = scanner.nextInt();
                                System.out.println();
                                switch (sortCatType) {
                                    case 1:
                                        if (isNull(food == null)) break;
                                        System.out.println("Food:");
                                        sort(new TreeMap<>(food.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", food.getTotalPrice());
                                        break;
                                        
                                    case 2:
                                        if (isNull(clothes == null)) break;
                                        System.out.println("Clothes:");
                                        sort(new TreeMap<>(clothes.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", clothes.getTotalPrice());
                                        break;
                                        
                                    case 3:
                                        if (isNull(entertainment == null)) break;
                                        System.out.println("Entertainment:");
                                        sort(new TreeMap<>(entertainment.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", entertainment.getTotalPrice());
                                        break;
                                        
                                    case 4:
                                        if (isNull(other == null)) break;
                                        System.out.println("Other:");
                                        sort(new TreeMap<>(other.purchaseList));
                                        System.out.printf("Total: $%.2f\n\n", other.getTotalPrice());
                                        break;
                                }

                                break;
                                
                            case 4:
                                System.out.println();
                                quitFromSort = true;
                                break;
                                
                        }
                    }
                    break;

                case 0:
                    exit = true;
                    System.out.println("Bye!");
                    break;


            }
        }

    }

    private static boolean isNull(boolean b) {
        if (b) {
            System.out.println("Purchase list is empty!\n");
            return true;
        }
        return false;
    }

    private static void sort(SortedMap<String, Double> map) {
        ValueComparator bvc = new ValueComparator(map);
        TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);

        sorted_map.putAll(map); // wrzuca zakupy


        sorted_map.forEach((key, value) -> System.out.printf("%s$%.2f\n", key, value));

    }

    private static void loadPurchases(File fileRead) {
        try {
            Scanner read = new Scanner(fileRead);

            while (read.hasNext()) { //sprawdzamy symbole na nastepnej linijce

                String firstWord = read.nextLine();

                if (firstWord.equals("balance")) {

                    balance = (Double.parseDouble(read.nextLine()));

                    continue;
                }
                String nextString = read.nextLine();
                int indexOf$ = nextString.lastIndexOf('$'); //gdzie jest $


                String s = nextString.substring(0, indexOf$);
                double p = Double.parseDouble(nextString.substring(indexOf$ + 1));

                switch (firstWord) { // tworzy rekord
                    case "Food":
                        if (food == null) {
                            food = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, food);
                        break;
                    case "Clothes":
                        if (clothes == null) {
                            clothes = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, clothes);
                        break;
                    case "Entertainment":
                        if (entertainment == null) {
                            entertainment = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, entertainment);
                        break;
                    case "Other":
                        if (other == null) {
                            other = new PurchaseType<>();
                        }
                        readFromFiletoCategory(p, s, other);
                        break;

                }

            }

            read.close(); //zamyka strumien

        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
    }

    private static void readFromFiletoCategory(double p, String s, PurchaseType<String> food) {

        food.setTotalPrice(p);
        food.setAllList(s, p);
        food.setAllProductsTotal(p);
        food.purchaseList.put(s, p);
    }

    private static void saveList(String s2, PurchaseType<String> productType, PrintWriter printWriter) {

        if (!Objects.equals(productType, null)) {

            if (!productType.getPurchaseList().isEmpty()) {
                productType.getPurchaseList().forEach((name, price) -> {
                    printWriter.print(s2 + "\n");
                    printWriter.write(name + " $" + price + "\n");
                    System.out.println();
                });
            }
        }
    }

    private static void showList(String s2, PurchaseType<String> productType) {
        System.out.println(s2);
        if (!productType.getPurchaseList().isEmpty()) {
          System.out.println("+------------------+------------+");
            System.out.println("| Name             | Price      |");
            System.out.println("+------------------+------------+");
            productType.getPurchaseList().forEach((name, price) -> {
                System.out.printf("| %-16s | $%.2f \t|\n", name, price);
            });
            System.out.println("+------------------+------------+");
            System.out.printf("| Total sum        | $%.2f \t|\n", PurchaseType.allProductsTotal);
            System.out.println("+------------------+------------+");
        } else
            System.out.println("Purchase list is empty!");
    }

    private static void addIncome(Scanner scanner) {
        System.out.println("Enter income:");
        double income = scanner.nextDouble();
        scanner.nextLine();
        balance += income; //zmieniamy balance
        System.out.println("Income was added\n");
    }
}
