package org.example.graduationProject;

import java.util.List;

public class Shop {
    private final List<Product> productsShop;
    /**При создании класса Shop (магазина) нужно передать ему list products*/
    public Shop(List<Product> productsShop) {
        this.productsShop = productsShop;
    }

    /**Метод получения списка products*/
    public List<Product> getProductsShop() {
        return productsShop;
    }
}
