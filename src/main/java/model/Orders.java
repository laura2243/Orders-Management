package model;
/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class Orders {
    private int id_order;
    private int id_client;
    private int id_produs;
    private int quantity;

    /**
     * class used for creating an order
     * it contains constructors, getters, setters a method for display
     * @return
     */
    public Orders() {

    }

    public Orders(int id_order, int id_client, int id_produs, int quantity) {
        this.id_order = id_order;
        this.id_client = id_client;
        this.id_produs = id_produs;
        this.quantity = quantity;
    }

    public Orders(int id_client, int id_produs, int quantity) {
        this.id_client = id_client;
        this.id_produs = id_produs;
        this.quantity = quantity;
    }

    public int getId_order() {
        return id_order;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId_produs() {
        return id_produs;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setId_order(int id_order) {
        this.id_order = id_order;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId_produs(int id_produs) {
        this.id_produs = id_produs;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
