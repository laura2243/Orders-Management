package bll;

import dao.OrderDAO;
import model.Orders;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */

public class OrderBLL {

    private OrderDAO orderDAO;

    /**
     * constructor for OrderBll
     *
     * @return
     */
    public OrderBLL() {
        orderDAO = new OrderDAO();
    }

    /**
     * method used for searching a client based on his id
     *
     * @return
     */
    public Orders findOrderById(int id) {
        Orders st = orderDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The student with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * methos that returns a list with details of clients and products for a certain order
     * used for displaying in table
     *
     * @return
     */
    public List<String> findAllOrders() {
        List<String> st = orderDAO.selectAll();
        return st;
    }

    /**
     * methos that returns a list with all the attributes of clients and products for a certain order
     * used for displaying in bill
     *
     * @return
     */
    public List<String> findAllOrdersDetails() {
        List<String> st = orderDAO.selectAllDetails();
        return st;
    }

    /**
     * methos that returns a list with of orders comtaining the id_client and id_product
     * used for the database
     *
     * @return
     */
    public List<Orders> findAllOrdersWithID() {
        List<Orders> st = orderDAO.findAll();
        return st;
    }

    public void insertOrder(Orders orders) {
        Orders st = orderDAO.insert(orders);
    }

    public void deleteOrder(int id) {
        orderDAO.delete(id, "id_order");
    }
}


