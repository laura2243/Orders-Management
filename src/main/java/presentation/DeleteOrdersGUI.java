package presentation;

import bll.OrderBLL;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DeleteOrdersGUI {
    private JPanel rootPanel;
    private JTable table1;
    private JButton DELETEButton;


    public DeleteOrdersGUI() {
        createOrderTable();

    }

    public JPanel getOrderRootPanel() {
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

    public JButton getDELETEButton() {
        return DELETEButton;
    }

    public JTable getTable1() {
        return table1;
    }
}
