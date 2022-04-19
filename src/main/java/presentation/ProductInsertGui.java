package presentation;

import javax.swing.*;

public class ProductInsertGui {
    private JTextField name;
    private JTextField price;
    private JPanel root;
    private JTextField stock;
    private JButton EXECUTEButton;

    public JPanel getRoot() {
        return root;
    }

    public JTextField getName() {
        return name;
    }

    public JTextField getPrice() {
        return price;
    }

    public JButton getEXECUTEButton() {
        return EXECUTEButton;
    }

    public JTextField getStock() {
        return stock;
    }
}

