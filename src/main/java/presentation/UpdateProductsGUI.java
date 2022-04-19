package presentation;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UpdateProductsGUI {
    private JPanel rootPanel;
    private JTable table1;
    private JTextField textFieldName;
    private JTextField textFieldPrice;
    private JButton UPDATEButton;
    private JTextField textFieldStock;

    public UpdateProductsGUI() {
        createProductTable();
    }
    public JPanel getProductRootPanel() {
        return rootPanel;
    }

    protected void createProductTable() {

        ProductBLL productBLL = new ProductBLL();
        List<Product> products = productBLL.findAllProducts();
        String[][] matrix = new String[products.size()][3];

        for (int i = 0; i < products.size(); i++) {
            matrix[i][0] = products.get(i).getProduct_name();
            matrix[i][1] = String.valueOf(products.get(i).getPrice());
            matrix[i][2] = String.valueOf(products.get(i).getStock());
        }
        table1.setModel(new DefaultTableModel(
                matrix, new String[]{"Name", "Price","Stock"}));
    }

    public JButton getUPDATEButton() {
        return UPDATEButton;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTextField getTextFieldName() {
        return textFieldName;
    }

    public JTextField getTextFieldPrice() {
        return textFieldPrice;
    }

    public JTextField getTextFieldStock() {
        return textFieldStock;
    }
}
