package org.example.graduationProject;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Shop shop = new Shop(getStoreItems());
        new TextUserInterface(shop);
    }

    /**Создаю набор продуктов для магазина*/
    public static List<Product> getStoreItems() {
        List<Product> products = new ArrayList<>();

    /**Три массива Названия, Цены, Общее количество продуктов */

        String[] productNames = {"Помидоры", "Огурцы", "Лук", "Картошка", "Капуста", "Морковка", "Яблока",
                "Банан", "Апельсин", "Мандарин", "Киви", "Мука"};
        Double[] productPrice = {100.00d, 70.00d, 50.00d, 40.00d, 35.00d, 15.00d, 90.00d,
                130.00d, 110.00d, 100.00d, 80.00d, 20.00d};
        Integer[] stock = {30, 30, 50, 60, 20, 10, 60,
                30, 10, 30, 10, 80};

        /**Последовательно наполняем список продуктами*/
        for (int i = 0; i < productNames.length; i++) {
            products.add(new Product(i + 1, productNames[i], productPrice[i], stock[i]));
        }

        return products;
    }
}
