
package com.mycompany.mattresssalesapp;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class ProductForm extends JFrame {

    // Form components
    private final JTextField txtCustomer = new JTextField(22);

    private final JComboBox<String> cmbProduct =
            new JComboBox<>(new String[]{
                "Cozy Nights Monarch",
                "Restonic Barbados",
                "Sealy Phuket"
            });

    private final JTextField txtPrice = new JTextField(22);

    private final JButton btnProcess = new JButton("PROCESS");

    // Constructor
    public ProductForm() {

        setTitle("Mattress Sales Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(530, 330);
        setLocationRelativeTo(null);

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();

        JMenu menuFile = new JMenu("File");

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.addActionListener(e -> System.exit(0));

        menuFile.add(menuExit);

        JMenu menuTools = new JMenu("Tools");

        JMenuItem menuProcess = new JMenuItem("Process");
        JMenuItem menuClear = new JMenuItem("Clear");

        menuProcess.addActionListener(e -> processSale());
        menuClear.addActionListener(e -> clearForm());

        menuTools.add(menuProcess);
        menuTools.add(menuClear);

        menuBar.add(menuFile);
        menuBar.add(menuTools);

        setJMenuBar(menuBar);

        
        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(9, 12, 9, 12);
        c.fill = GridBagConstraints.HORIZONTAL;

       
        c.gridx = 0;
        c.gridy = 0;

        panel.add(new JLabel("Customer Name:"), c);

        c.gridx = 1;
        panel.add(txtCustomer, c);

       
        c.gridx = 0;
        c.gridy = 1;

        panel.add(new JLabel("Select Product:"), c);

        c.gridx = 1;
        panel.add(cmbProduct, c);

       
        c.gridx = 0;
        c.gridy = 2;

        panel.add(new JLabel("Enter Price:"), c);

        c.gridx = 1;
        panel.add(txtPrice, c);

       
        c.gridx = 1;
        c.gridy = 3;

        panel.add(btnProcess, c);

        btnProcess.addActionListener(e -> processSale());

        add(panel, BorderLayout.CENTER);
    }

    
    private void clearForm() {

        txtCustomer.setText("");
        txtPrice.setText("");

        cmbProduct.setSelectedIndex(0);

        txtCustomer.requestFocusInWindow();
    }

   
    private void processSale() {

        String customer = txtCustomer.getText().trim();

       
        if (customer.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the customer name.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtCustomer.requestFocusInWindow();
            return;
        }

        
        double price;

        try {

            price = Double.parseDouble(txtPrice.getText().trim());

        } catch (NumberFormatException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid price.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPrice.requestFocusInWindow();
            return;
        }

        if (!Double.isFinite(price) || price <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Price must be greater than zero.",
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE
            );

            txtPrice.requestFocusInWindow();
            return;
        }

        
        String product = (String) cmbProduct.getSelectedItem();

      
        ProductCalculations calc =
                new ProductCalculations(price);

        double vat = calc.GetVat();

        double commission = calc.GetCommission();

        double total = calc.GetTotal();

        // Format results
        String vatText = String.format(Locale.US, "%.2f", vat);
        String commissionText = String.format(Locale.US, "%.2f", commission);
        String totalText = String.format(Locale.US, "%.2f", total);

        // Display sales report
        String result =
                "MATTRESS SALES REPORT\n"
                + "******************************\n"
                + "Customer Name: " + customer + "\n"
                + "Product: " + product + "\n"
                + "Original Price: R " + String.format(Locale.US, "%.2f", price) + "\n"
                + "VAT (15%): R " + vatText + "\n"
                + "COMMISSION (5%): R " + commissionText + "\n"
                + "TOTAL: R " + totalText + "\n"
                + "******************************";

      
        try (PrintWriter writer =
                new PrintWriter(new FileWriter("data.txt", true))) {

            writer.println(result);
            writer.println();

            if (writer.checkError()) {
                throw new IOException("Could not finish writing data.txt");
            }

            JOptionPane.showMessageDialog(
                    this,
                    result,
                    "Sales Report",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error saving sales report: " + ex.getMessage(),
                    "File Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // Main method - runs the GUI
    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception ex) {
            
        }

        SwingUtilities.invokeLater(() -> {

            ProductForm form = new ProductForm();

            form.setVisible(true);

        });
    }
}
