package presentation;

import bll.ProductBLL;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DeleteProductsGUI {
    private JPanel root;
    private JTable table1;
    private JButton DELETEButton;


    public DeleteProductsGUI() {
        createProductTable();
    }
    public JPanel getProductRootPanel() {
        return root;
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

    public JButton getDELETEButton() {
        return DELETEButton;
    }

    public JTable getTable1() {
        return table1;
    }
}
