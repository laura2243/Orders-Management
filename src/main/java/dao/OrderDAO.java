package dao;

import connection.ConnectionFactory;
import model.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class OrderDAO extends AbstractDAO<Orders> {

    /**
     * method for order that creates a list with orders from result set of the SELECT query
     * used for displaying the details of the order in the table
     * @return
     */
    private List<String> createObject(ResultSet resultSet) {
        List<String> list = new ArrayList<>();

        while (true) {
            try {
                if (!resultSet.next()) break;
                for(int i =0;i<4;i++)
                    list.add(resultSet.getString(i+1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        return list;
    }

    /**
     * method for order that creates a list with orders from result set of the SELECT query
     * used for displaying the all the details about products and clients of the order in bill
     * @return
     */
    private List<String> createObjectDetails(ResultSet resultSet) {
        List<String> list = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                for(int i =0;i<8;i++)
                    list.add(resultSet.getString(i+1));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * method used for creating the SELECT query that uses a join between tables
     * return a list with the id_order, product and client name and the quantity of the specified order
     *
     * @return
     */
    public List<String> selectAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT orders.id_order, client.name, product.product_name,orders.quantity FROM orders join client join product where id_client=client.id and id_produs=product.id_product";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObject(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:select all " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * method used for creating the SELECT query that uses a join between tables
     * return a list with all attributes of the clients and products for the specified order
     *
     * @return
     */
    public List<String> selectAllDetails() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT orders.id_order, client.name,client.address,client.email,client.age, product.product_name,product.price,orders.quantity FROM orders join client join product where id_client=client.id and id_produs=product.id_product";
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjectDetails(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:select all " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    /**
     * abstract method used for creating INSERT query for order without its id
     *
     * @return
     */
    private String createInsertQuery( ArrayList<String> values)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO orders (`id_client`, `id_produs`, `quantity`)  ");
        sb.append(" VALUES (");
        for(String v:values)
        {
            sb.append("'").append(v).append("'").append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * method for order that creates a list with orders from result set of the INSERT query
     * the insert query doesn't need the id of the order
     * @return
     */
    public Orders insert(Orders t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> vals = new ArrayList<>();
        vals.add(String.valueOf(t.getId_client()));
        vals.add(String.valueOf(t.getId_produs()));
        vals.add(String.valueOf(t.getQuantity()));

        String query = createInsertQuery(vals);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,  "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

}
