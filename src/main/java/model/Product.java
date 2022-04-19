package model;
/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class Product {

    private int id_product;
    private String product_name;
    private int price;
    private int stock;

    /**
     * class used for creating a product
     * it contains constructors, getters, setters a method for display
     * @return
     */
    public Product(int id_product, String product_name, int price, int stock) {
        this.id_product = id_product;
        this.product_name = product_name;
        this.price = price;
        this.stock = stock;
    }

    public Product(String product_name, int price, int stock) {
        this.product_name = product_name;
        this.price = price;
        this.stock = stock;
    }

    public Product() {
    }

    public int getId_product() {
        return id_product;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public void setId_product(int id_product) {
        this.id_product = id_product;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id_product=" + id_product +
                ", product_name='" + product_name + '\'' +
                ", price=" + price +
                '}';
    }
}
