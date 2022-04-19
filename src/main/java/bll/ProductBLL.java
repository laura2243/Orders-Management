package bll;

import dao.ProductDAO;
import model.Product;

import java.util.List;
import java.util.NoSuchElementException;
/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */

public class ProductBLL {

    private ProductDAO productDAO;


    public ProductBLL() {
        productDAO = new ProductDAO();
    }

    /**
     * method used for searching a product based on its id
     *
     * @return
     */
    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * methos that returns a list with details of products
     * used for displaying in table
     *
     * @return
     */
    public List<Product> findAllProducts() {
        List<Product> st = productDAO.findAll();
        return st;
    }

    /**
     * method used for inserting a product in the database
     * used for displaying in table
     *
     * @return
     */
    public void insertProduct(Product product) {
        Product st = productDAO.insert(product);
    }

    /**
     * method used for updating a product in the database
     * used for displaying in table
     *
     * @return
     */
    public Product updateProduct(Product product) {
        Product st = productDAO.update(product, "id_product");
        return st;
    }

    /**
     * method used for deleting a product in the database
     * used for displaying in table
     *
     * @return
     */
    public void deleteProduct(int id) {
        productDAO.delete(id, "id_product");


    }
}


