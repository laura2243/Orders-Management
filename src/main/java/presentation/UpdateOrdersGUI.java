package presentation;

import bll.OrderBLL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class UpdateOrdersGUI {
    private JPanel rootPanel;
    private JTable table1;
    private JTextField textFieldNameCl;
    private JTextField textFieldNameProd;
    private JTextField textFieldQuantity;
    private JButton UPDATEButton;


    public UpdateOrdersGUI() {
        createOrderTable();

    }
    public JPanel getProductRootPanel() {
        return rootPanel;
    }

    protected void createOrderTable() {

        OrderBLL clientBLL = new OrderBLL();
        List<String> orders= clientBLL.findAllOrders();
        String[][] matrix = new String[orders.size()/4][4];

        int j = 1;
        for (int i = 0; i < orders.size()/4; i++) {

            matrix[i][0] = orders.get(j++);
            matrix[i][1] = orders.get(j++);
            matrix[i][2] = orders.get(j++);
            j++;

        }
        table1.setModel(new DefaultTableModel(
                matrix, new String[]{"Client Name", "Product Name", "Quantity"}));
    }

    public UpdateOrdersGUI(JButton UPDATEButton) {
        this.UPDATEButton = UPDATEButton;
    }

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTable getTable1() {
        return table1;
    }

    public JTextField getTextFieldNameCl() {
        return textFieldNameCl;
    }

    public JTextField getTextFieldNameProd() {
        return textFieldNameProd;
    }

    public JTextField getTextFieldQuantity() {
        return textFieldQuantity;
    }

    public JButton getUPDATEButton() {
        return UPDATEButton;
    }
}
