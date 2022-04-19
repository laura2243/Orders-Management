package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 * Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 * @Source http://www.java-blog.com/mapping-javaobjects-database-reflection-generics
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * abstract method used creating SELECT query based on ID
     *
     * @return
     */
    private String createSelectQuery(String field, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " = '").append(id).append(" ' ");
        return sb.toString();
    }


    /**
     * abstract method used for searching after ID
     *
     * @return
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id", id);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * abstract method used creating SELECT query
     *
     * @return
     */
    private String createSelectQueryAll() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT");
        sb.append(" * ");
        sb.append("FROM ");
        sb.append(type.getSimpleName());
        return sb.toString();
    }

    /**
     * abstract method that returns a list with all the specified objects from table
     *
     * @return
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQueryAll();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();

            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:find " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * abstract method that creates a list with the specified objects from result of the query
     *
     * @return
     */
    protected List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T) ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * abstract method used creating INSERT query
     *
     * @return
     */
    private String createInsertQuery(String table, ArrayList<String> values) {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(table);
        sb.append(" VALUES (");
        for (String v : values) {
            sb.append("'").append(v).append("'").append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        return sb.toString();
    }

    /**
     * abstract method used to insert a client in the table
     * abstract method that returns a list with all the specified objects from table
     * including the object inserted
     * @return
     */
    public T insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> vals = new ArrayList<>();

        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                vals.add(f.get(t).toString());

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        String query = createInsertQuery(type.getSimpleName(), vals);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * abstract method used creating UPDATE query
     *
     * @return
     */
    private String createUpdateQuery(T t, String field) {

        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET ");
        ArrayList<String> fields = new ArrayList<>();
        for (Field f : type.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                fields.add(String.valueOf(f.get(t)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                sb.append(f.getName()).append("='").append(f.get(t));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sb.append("',");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" WHERE `").append(field).append("` = ").append(Integer.valueOf(fields.get(0)));
        return sb.toString();
    }


    /**
     * abstract method used to update a client from the table
     * abstract method that returns a list with all the specified objects from table
     * including the object that was updated
     * @return
     */
    public T update(T t, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ArrayList<String> vals = new ArrayList<>();
        String query = createUpdateQuery(t, field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * abstract method used creating DELETE query
     *
     * @return
     */
    private String createDeleteQuery(String table, int id, String field) {

        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(table);
        sb.append(" WHERE `").append(field).append("` = '");
        sb.append(id).append("'");
        return sb.toString();
    }


    /**
     * abstract method used to delete a client from the table
     * abstract method that returns a list with all the specified objects from table
     * without the object that was deteled
     * @return
     */
    public T delete(int id, String field) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createDeleteQuery(type.getSimpleName(), id, field);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }
}
