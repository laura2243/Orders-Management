package presentation;

import bll.ClientBLL;
import model.Client;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

public class MainFrame {
    private JPanel rootMain;
    private JComboBox comboBoxSelect;
    private JComboBox comboBoxInsert;
    private JComboBox comboBoxUpdate;
    private JButton createOrderButton;
    private JComboBox comboBoxDelete;
    private JButton createBillButton;

    public MainFrame() {



    }

    public JPanel getMainRootPanel() {
        return rootMain;
    }

    private void createClientTable() {




    }

    public JButton getCreateBillButton() {
        return createBillButton;
    }

    public JComboBox getComboBoxSelect() {
        return comboBoxSelect;
    }

    public JComboBox getComboBoxInsert() {
        return comboBoxInsert;
    }

    public JComboBox getComboBoxUpdate() {
        return comboBoxUpdate;
    }

    public JButton getCreateOrderButton() {
        return createOrderButton;
    }

    public JComboBox getComboBoxDelete() {
        return comboBoxDelete;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
