package start;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class Start {
    protected static final Logger LOGGER = Logger.getLogger(Start.class.getName());

    public static void main(String[] args) throws SQLException {

        ClientBLL clientBll = new ClientBLL();

        Client client1 = null;
        List<Client> allClients = new ArrayList<>();
//
//		try {
//			client1 = clientBll.findClientById(1);
//			ReflectionExample.retrieveProperties(client1);
//
//
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//		try {
//
//			allClients = clientBll.findAllClients();
//			for(Client client : allClients){
//				ReflectionExample.retrieveProperties(client);
//				System.out.println("\n");
//			}
//
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
        Client client = new Client(2, "ce", "ma", "enervezi", 12);
//		try {
//
//			clientBll.insertClient(client);
//
//
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
        try {

            OrderBLL orderBLL = new OrderBLL();
            System.out.println(orderBLL.findAllOrdersWithID());

        } catch (Exception ex) {
            LOGGER.log(Level.INFO, ex.getMessage());
        }
//		try {
//			product1 = productBll.findProductById(1);
//			ReflectionExample.retrieveProperties(product1);
//
//
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//		try {
//
//			allProducts = productBll.findAllProducts();
//			for(Product product: allProducts){
//				ReflectionExample.retrieveProperties(product);
//				System.out.println("\n");
//			}
//
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}
//		try {
//
//			product = productBll.updateProduct(product);
//
//
//		} catch (Exception ex) {
//			LOGGER.log(Level.INFO, ex.getMessage());
//		}


    }

}
