package org.example.graduationProject;

import java.util.Objects;

public class Product {
    private Integer id;    /**Порядковый номер*/
    private String name;   /**Имя*/
    private Double price;  /**Цена*/
    private Integer quantity; /*Переменная, которая хранит его количество в магазине*/
    /** При создании продукта в конструкторе должен указать все,
    объявленные выше, поля*/
    public Product(Integer id, String name, Double price, Integer quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }
    /**Пустой конструктор в Java это специальный метод, который не принимает никаких аргументов и
    не выполняет никаких действий. Он используется в случаях, когда нужно создать экземпляр класса,
    но не требуется инициализировать его какими-либо значениями.*/
    public Product() {
    }

    // Геттеры (public методы, чтобы получения значений полей)
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    // Cеттеры (public методы, чтобы установить значения полей)
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }


    // Служебные методы для сравнения продуктов между собой
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        hash = 29 * hash + Objects.hashCode(this.name);
        hash = 29 * hash + Objects.hashCode(this.price);
        hash = 29 * hash + Objects.hashCode(this.quantity);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Product other = (Product) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        return Objects.equals(this.quantity, other.quantity);
    }
}
