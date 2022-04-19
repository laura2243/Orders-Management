package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class DeleteClientGUI {
    private JPanel rootPanel;
    private JTable table1;
    private JButton DELETEButton;


    public DeleteClientGUI() {
        createClientTable();
    }

    public JPanel getClientRootPanel() {
        return rootPanel;
    }

    protected void createClientTable() {

        ClientBLL clientBLL = new ClientBLL();
        List<Client> clients = clientBLL.findAllClients();
        String[][] matrix = new String[clients.size()][4];

        for (int i = 0; i < clients.size(); i++) {
            matrix[i][0] = clients.get(i).getName();
            matrix[i][1] = clients.get(i).getAddress();
            matrix[i][2] = clients.get(i).getEmail();
            matrix[i][3] = String.valueOf(clients.get(i).getAge());

        }
        table1.setModel(new DefaultTableModel(
                matrix, new String[]{"Name", "Address", "Email", "Age"}));
    }

    public JButton getDELETEButton() {
        return DELETEButton;
    }

    public JTable getTable1() {
        return table1;
    }
}
