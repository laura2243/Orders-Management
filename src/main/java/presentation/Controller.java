package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import bll.validators.ClientAgeValidator;
import bll.validators.EmailValidator;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import model.Client;
import model.Orders;
import model.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.UIManager.*;

import com.itextpdf.text.pdf.PdfWriter;

public class Controller {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
    }

    /**
     * class used for displaying the interface with its components
     * it accesses each button using an action listener
     *
     * @return
     */
    public static void createGUI() {

        MainFrame mainFrame = new MainFrame();
        JPanel root = mainFrame.getMainRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    UIManager.put("nimbusBlueGrey", new Color(115, 164, 209));
                    break;
                }
            }
        } catch (Exception e) {
        }
        mainFrame.getComboBoxSelect().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getComboBoxSelect().getSelectedItem().toString().equals("Client")) {

                    new ClientGUI();
                    Controller.createClient();
                } else if (mainFrame.getComboBoxSelect().getSelectedItem().toString().equals("Product")) {

                    new ProductGUI();
                    Controller.createProduct();
                } else if (mainFrame.getComboBoxSelect().getSelectedItem().toString().equals("Orders")) {

                    new SelectOrderGUI();
                    Controller.createGUIOrderSelect();
                }
            }
        });

        mainFrame.getComboBoxInsert().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getComboBoxInsert().getSelectedItem().toString().equals("Clients")) {

                    ClientInsertGUI clientInsertGUI1 = Controller.createGUIClientInsert();

                    clientInsertGUI1.getBtnInsert().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ClientBLL clientBLL = new ClientBLL();

                            String name = clientInsertGUI1.getNAMETextField().getText();
                            String address = clientInsertGUI1.getADDRESSTextField().getText();
                            String email = clientInsertGUI1.getEMAILTextField().getText();
                            Integer age = Integer.valueOf(clientInsertGUI1.getAge().getText());
                            Client client = new Client(name, address, email, age);
                            EmailValidator emailValidator = new EmailValidator();
                            ClientAgeValidator clientAgeValidator = new ClientAgeValidator();
                            if (emailValidator.validate(client) && clientAgeValidator.validate(client)) {
                                clientBLL.insertClient(client);
                            } else if (!emailValidator.validate(client)) {
                                JOptionPane.showMessageDialog(null, "Incorrect email");
                            } else if (!clientAgeValidator.validate(client)) {
                                JOptionPane.showMessageDialog(null, "Incorrect age");
                            }


                            System.out.println(clientInsertGUI1.getADDRESSTextField().getText());
                        }
                    });

                } else if (mainFrame.getComboBoxInsert().getSelectedItem().toString().equals("Product")) {

                    ProductInsertGui productInsertGui = Controller.createGUIProductInsert();

                    productInsertGui.getEXECUTEButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            ProductBLL productBLL = new ProductBLL();
                            String name = productInsertGui.getName().getText();
                            Integer price = Integer.valueOf(productInsertGui.getPrice().getText());
                            Integer stock = Integer.valueOf(productInsertGui.getStock().getText());


                            if (price > 0 && stock > 0) {
                                Product product = new Product(name, price, stock);
                                productBLL.insertProduct(product);
                            } else if(price <= 0) {
                                JOptionPane.showMessageDialog(null, "Incorrect price");
                            }
                            else if(stock <= 0) {
                                JOptionPane.showMessageDialog(null, "Incorrect stock");
                            }

                        }
                    });
                }
            }
        });


        mainFrame.getComboBoxUpdate().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainFrame.getComboBoxUpdate().getSelectedItem().toString().equals("Client")) {

                    UpdateClientGUI updateClientGUI = Controller.updateClientGUI();

                    ClientBLL clientBLL = new ClientBLL();
                    List<Client> clients = clientBLL.findAllClients();

                    updateClientGUI.getTable1().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            updateClientGUI.getTextFieldName().setText(String.valueOf(updateClientGUI.getTable1().getValueAt(updateClientGUI.getTable1().getSelectedRow(), 0)));
                            updateClientGUI.getTextFieldAddress().setText(String.valueOf(updateClientGUI.getTable1().getValueAt(updateClientGUI.getTable1().getSelectedRow(), 1)));
                            updateClientGUI.getTextFieldEmail().setText(String.valueOf(updateClientGUI.getTable1().getValueAt(updateClientGUI.getTable1().getSelectedRow(), 2)));
                            updateClientGUI.getTextFieldAge().setText(String.valueOf(updateClientGUI.getTable1().getValueAt(updateClientGUI.getTable1().getSelectedRow(), 3)));
                        }
                    });

                    updateClientGUI.getUPDATEButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            int id = clients.get(updateClientGUI.getTable1().getSelectedRow()).getId();
                            String name = updateClientGUI.getTextFieldName().getText();
                            String address = updateClientGUI.getTextFieldAddress().getText();
                            String email = updateClientGUI.getTextFieldEmail().getText();
                            Integer age = Integer.valueOf(updateClientGUI.getTextFieldAge().getText());
                            Client client = new Client(id, name, address, email, age);

                            EmailValidator emailValidator = new EmailValidator();
                            ClientAgeValidator clientAgeValidator = new ClientAgeValidator();
                            if (emailValidator.validate(client) && clientAgeValidator.validate(client)) {
                                clientBLL.updateClient(client);
                                updateClientGUI.createClientTable();
                            } else if (!emailValidator.validate(client)) {
                                JOptionPane.showMessageDialog(null, "Incorrect email!");
                            } else if (!clientAgeValidator.validate(client)) {
                                JOptionPane.showMessageDialog(null, "Incorrect age");
                            }
                        }
                    });

                } else if (mainFrame.getComboBoxUpdate().getSelectedItem().toString().equals("Product")) {

                    UpdateProductsGUI updateProductsGUI = Controller.updateProductsGUI();
                    ProductBLL productBLL = new ProductBLL();
                    List<Product> products = productBLL.findAllProducts();

                    updateProductsGUI.getTable1().addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            super.mouseClicked(e);
                            updateProductsGUI.getTextFieldName().setText(String.valueOf(updateProductsGUI.getTable1().getValueAt(updateProductsGUI.getTable1().getSelectedRow(), 0)));
                            updateProductsGUI.getTextFieldPrice().setText(String.valueOf(updateProductsGUI.getTable1().getValueAt(updateProductsGUI.getTable1().getSelectedRow(), 1)));
                            updateProductsGUI.getTextFieldStock().setText(String.valueOf(updateProductsGUI.getTable1().getValueAt(updateProductsGUI.getTable1().getSelectedRow(), 2)));


                        }
                    });

                    updateProductsGUI.getUPDATEButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            int id = products.get(updateProductsGUI.getTable1().getSelectedRow()).getId_product();
                            String name = updateProductsGUI.getTextFieldName().getText();
                            Integer price = Integer.valueOf(updateProductsGUI.getTextFieldPrice().getText());
                            Integer stock = Integer.valueOf(updateProductsGUI.getTextFieldStock().getText());
                            Product product = new Product(id, name, price, stock);


                            if (price > 0 && stock > 0) {
                                productBLL.updateProduct(product);
                                updateProductsGUI.createProductTable();
                            } else if(price <= 0) {
                                JOptionPane.showMessageDialog(null, "Incorrect price");
                            }
                            else if(stock <= 0) {
                                JOptionPane.showMessageDialog(null, "Incorrect stock");
                            }

                        }
                    });
                }
            }
        });

        mainFrame.getCreateOrderButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                MakeOrderGui makeOrderGui = Controller.createMakeOrderGUI();
                makeOrderGui.getMakeOrderBtn().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        ClientBLL clientBLL = new ClientBLL();
                        List<Client> clients = clientBLL.findAllClients();

                        ProductBLL productBLL = new ProductBLL();
                        List<Product> products = productBLL.findAllProducts();

                        int rowClient = clients.get(makeOrderGui.getClientsTable().getSelectedRow()).getId();
                        int rowProduct = products.get(makeOrderGui.getProductsTable().getSelectedRow()).getId_product();
                        OrderBLL orderBLL = new OrderBLL();
                        int quantity = Integer.parseInt(makeOrderGui.getTextFieldQuantity().getText());

                        //verify quantity condition
                        if (quantity > productBLL.findProductById(rowProduct).getStock()) {
                            JOptionPane.showMessageDialog(null, "Under-stock");
                        } else if (quantity < 0)

                            JOptionPane.showMessageDialog(null, "Incorrect quantity");
                        else {
                            Product product = new Product(rowProduct, products.get(makeOrderGui.getProductsTable().getSelectedRow()).getProduct_name(), products.get(makeOrderGui.getProductsTable().getSelectedRow()).getPrice(), productBLL.findProductById(rowProduct).getStock() - quantity);
                            productBLL.updateProduct(product);
                            makeOrderGui.createTable();
                            Orders orders = new Orders(rowClient, rowProduct, quantity);
                            orderBLL.insertOrder(orders);
                        }


                    }
                });
            }
        });


        mainFrame.getComboBoxDelete().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (mainFrame.getComboBoxDelete().getSelectedItem().toString().equals("Client")) {

                    DeleteClientGUI deleteClientGUI = Controller.deleteClientGUI();
                    deleteClientGUI.getDELETEButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            ClientBLL clientBLL = new ClientBLL();
                            List<Client> clients = clientBLL.findAllClients();
                            int id = clients.get(deleteClientGUI.getTable1().getSelectedRow()).getId();
                            clientBLL.deleteClient(id);
                            deleteClientGUI.createClientTable();


                        }
                    });
                } else if (mainFrame.getComboBoxDelete().getSelectedItem().toString().equals("Product")) {


                    DeleteProductsGUI deleteProductsGUI = Controller.deleteProductsGUI();

                    deleteProductsGUI.getDELETEButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {

                            ProductBLL productBLL = new ProductBLL();
                            List<Product> products = productBLL.findAllProducts();
                            int id = products.get(deleteProductsGUI.getTable1().getSelectedRow()).getId_product();
                            productBLL.deleteProduct(id);
                            deleteProductsGUI.createProductTable();

                        }
                    });

                }
                if (mainFrame.getComboBoxDelete().getSelectedItem().toString().equals("Orders")) {


                    DeleteOrdersGUI deleteOrdersGUI = Controller.deleteOrdersGUI();
                    deleteOrdersGUI.getDELETEButton().addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {


                            OrderBLL orderBLL = new OrderBLL();
                            List<Orders> orders = orderBLL.findAllOrdersWithID();
                            int id = orders.get(deleteOrdersGUI.getTable1().getSelectedRow()).getId_order();
                            orderBLL.deleteOrder(id);
                            deleteOrdersGUI.createOrderTable();
                        }
                    });
                }
            }
        });


        mainFrame.getCreateBillButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                OrderBLL orderBLL = new OrderBLL();
                List<String> orders = orderBLL.findAllOrdersDetails();
                System.out.println(orders.toString());


                int contor = 0;
                for (int i = 0; i < orders.size() / 8; i++) {
                    Document document = new Document();
                    try {
                        String s = "bill" + i;
                        PdfWriter.getInstance(document, new FileOutputStream(s + ".pdf"));
                    } catch (DocumentException ex) {
                        ex.printStackTrace();
                    } catch (FileNotFoundException ex) {
                        ex.printStackTrace();
                    }
                    document.open();

                    Chunk chunk = null;
                    Font font1 = FontFactory.getFont(FontFactory.TIMES_ITALIC, 30, BaseColor.BLACK);
                    chunk = new Chunk("BILL \n ", font1);
                    try {
                        document.add(chunk);
                    } catch (DocumentException ex) {
                        ex.printStackTrace();
                    }
                    try {
                        document.add(new Paragraph("\n"));
                    } catch (DocumentException ex) {
                        ex.printStackTrace();
                    }
                    Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
                    chunk = new Chunk(" Client:  ", font);
                    try {
                        document.add(chunk);
                    } catch (DocumentException ex) {
                        ex.printStackTrace();
                    }
                    contor = 0;
                    for (int j = i * 8; j < 8 * (i + 1); j++) {
                        chunk = new Chunk(orders.get(j), font);
                        contor++;
                        System.out.println(chunk + "\n");
                        try {
                            document.add(chunk);
                        } catch (DocumentException ex) {
                            ex.printStackTrace();
                        }
                        chunk = new Chunk(" ", font);
                        try {
                            document.add(chunk);
                        } catch (DocumentException ex) {
                            ex.printStackTrace();
                        }
                        if (contor % 5 == 0 && contor != 0) {
                            try {
                                document.add(new Paragraph("\n"));
                                chunk = new Chunk(" Produs:  ", font);
                                try {
                                    document.add(chunk);
                                } catch (DocumentException ex) {
                                    ex.printStackTrace();
                                }
                            } catch (DocumentException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    document.close();

                }

            }
        });

    }

    public static void createClient() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUIClient();
            }
        });
    }

    public static void createProduct() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUIProduct();
            }
        });
    }

    public static void createGUIProduct() {


        ProductGUI uiP = new ProductGUI();
        JPanel root = uiP.getProductRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static void createGUIClient() {
        ClientGUI ui = new ClientGUI();
        JPanel root = ui.getClientRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public static ClientInsertGUI createGUIClientInsert() {
        ClientInsertGUI ui = new ClientInsertGUI();
        JPanel root = ui.getRootInsertCl();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static ProductInsertGui createGUIProductInsert() {
        ProductInsertGui ui = new ProductInsertGui();
        JPanel root = ui.getRoot();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static SelectOrderGUI createGUIOrderSelect() {
        SelectOrderGUI ui = new SelectOrderGUI();
        JPanel root = ui.getOrderRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }


    public static MakeOrderGui createMakeOrderGUI() {
        MakeOrderGui ui = new MakeOrderGui();
        JPanel root = ui.getProductRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static DeleteClientGUI deleteClientGUI() {
        DeleteClientGUI ui = new DeleteClientGUI();
        JPanel root = ui.getClientRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }


    public static DeleteProductsGUI deleteProductsGUI() {
        DeleteProductsGUI ui = new DeleteProductsGUI();
        JPanel root = ui.getProductRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static DeleteOrdersGUI deleteOrdersGUI() {
        DeleteOrdersGUI ui = new DeleteOrdersGUI();
        JPanel root = ui.getOrderRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static UpdateClientGUI updateClientGUI() {
        UpdateClientGUI ui = new UpdateClientGUI();
        JPanel root = ui.getClientRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static UpdateProductsGUI updateProductsGUI() {
        UpdateProductsGUI ui = new UpdateProductsGUI();
        JPanel root = ui.getProductRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

    public static UpdateOrdersGUI updateOrdersGUI() {
        UpdateOrdersGUI ui = new UpdateOrdersGUI();
        JPanel root = ui.getProductRootPanel();
        JFrame frame = new JFrame();
        frame.setContentPane(root);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
        return ui;
    }

}
