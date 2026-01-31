package dao;

import java.util.ArrayList;
import model.Product;

public class ProductDAO {
    public static ArrayList<Product> list = new ArrayList<>();

    public static void add(Product p) {
        list.add(p);
    }

    public static void delete(String id) {
        list.removeIf(p -> p.getId().equals(id));
    }

    public static Product findById(String id) {
        for (Product p : list) {
            if (p.getId().equals(id)) return p;
        }
        return null;
    }

    public static Product maxPrice() {
        if (list.isEmpty()) return null;
        Product max = list.get(0);
        for (Product p : list) {
            if (p.getPrice() > max.getPrice()) max = p;
        }
        return max;
    }

    public static int totalQuantity() {
        int sum = 0;
        for (Product p : list) sum += p.getQuantity();
        return sum;
    }
}
