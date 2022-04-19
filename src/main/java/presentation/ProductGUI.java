package presentation;

import bll.ClientBLL;
import bll.ProductBLL;
import model.Client;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ProductGUI {
    private JPanel RootProd;
    private JTable table1;

    public ProductGUI() {
        createProductTable();
    }
    public JPanel getProductRootPanel() {
        return RootProd;
    }

    private void createProductTable() {

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
}


