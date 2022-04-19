package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MakeOrderGui {
    private JPanel rootPanel;
    private JTable ClientsTable;
    private JTable ProductsTable;
    private JButton makeOrderBtn;
    private JTextField textFieldQuantity;

    public MakeOrderGui() {
        createTable();

    }
    public JPanel getProductRootPanel() {
        return rootPanel;
    }

    protected void createTable() {

        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.findAllProducts();
        String[][] matrix = new String[products.size()][3];

        for (int i = 0; i < products.size(); i++) {
            matrix[i][0] = products.get(i).getProduct_name();
            matrix[i][1] = String.valueOf(products.get(i).getPrice());
            matrix[i][2] = String.valueOf(products.get(i).getStock());
        }
        ProductsTable.setModel(new DefaultTableModel(
                matrix, new String[]{"Name", "Price","Stock"}));

        ClientBLL clientBLL = new ClientBLL();
        List<Client> clients = clientBLL.findAllClients();
        String[][] matrix1 = new String[clients.size()][4];

        for (int i = 0; i < clients.size(); i++) {
            matrix1[i][0] = clients.get(i).getName();
            matrix1[i][1] = clients.get(i).getAddress();
            matrix1[i][2] = clients.get(i).getEmail();
            matrix1[i][3] = String.valueOf(clients.get(i).getAge());

        }
        ClientsTable.setModel(new DefaultTableModel(
                matrix1, new String[]{"Name", "Address", "Email", "Age"}));
    }

    public JButton getMakeOrderBtn() {
        return makeOrderBtn;
    }

    public JTable getClientsTable() {
        return ClientsTable;
    }

    public JTable getProductsTable() {
        return ProductsTable;
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }
}
