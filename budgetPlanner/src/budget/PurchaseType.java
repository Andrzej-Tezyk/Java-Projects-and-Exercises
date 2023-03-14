
package budget;

import java.util.*;

public class PurchaseType<T> {
    SortedMap<String,Double> purchaseList = new TreeMap<>();
    static SortedMap<String,Double> allList = new TreeMap<>();
    static double allProductsTotal = 0;

    double totalPrice =0;
    String name;
    double price;
    Scanner scanner=new Scanner(System.in);


    public void readProductnPrice() {
        System.out.println("Enter purchase name:");
        this.name = scanner.nextLine(); //odniesienie do konkretnego obiektu

        System.out.println("Enter its price:");
        price = scanner.nextDouble();
        scanner.nextLine();
        name = name + " ";
        this.purchaseList.put(name,price);
        allList.put(name,price); //dodaj do allList
        allProductsTotal +=price;
        System.out.println("Purchase was added!");
        totalPrice +=price;
    }

    public SortedMap<String, Double> getPurchaseList() {
        return purchaseList;
    }

    public static SortedMap<String, Double> getAllList() {
        return allList;
    }

    public Set<String> getName(){
        return purchaseList.keySet();
    }
    public Collection<Double> getPrice(){
        return purchaseList.values();
    }

    public double getTotalPrice(){
        return totalPrice;
    }

    public  void setAllList(String a ,double p) {
        PurchaseType.allList.put(a,p);
    }

    public  void setAllProductsTotal(double allProductsTotal) {
        PurchaseType.allProductsTotal += allProductsTotal;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice += totalPrice;
    }
}