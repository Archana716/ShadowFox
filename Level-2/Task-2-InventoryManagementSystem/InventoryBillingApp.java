package com.inventory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class InventoryBillingApp extends JFrame {

    // Product prices (mock inventory)
    private final Map<String, Integer> priceMap = new HashMap<>();
    private final Map<String, JTextField> qtyFields = new HashMap<>();

    private final JTextArea billArea = new JTextArea();

    public InventoryBillingApp() {
        setTitle("Inventory Management System | Project Zone");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        initPrices();
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
    }

    // ---------------- TOP PANEL ----------------
    private JPanel createTopPanel() {
        JPanel top = new JPanel(new GridLayout(2, 4, 10, 5));
        top.setBorder(BorderFactory.createTitledBorder("Customer Details"));

        top.add(new JLabel("Customer Name"));
        top.add(new JTextField());

        top.add(new JLabel("Phone No"));
        top.add(new JTextField());

        top.add(new JLabel("Bill Number"));
        top.add(new JTextField());

        top.add(new JLabel("Barcode Search"));
        JTextField barcodeField = new JTextField();
        top.add(barcodeField);

        // 🔥 Barcode search (instant focus)
        barcodeField.addCaretListener(e -> {
            JTextField field = qtyFields.get(barcodeField.getText());
            if (field != null) {
                field.requestFocus();
                field.setBackground(Color.YELLOW);
            }
        });

        return top;
    }

    // ---------------- CENTER PANEL ----------------
    private JPanel createCenterPanel() {
        JPanel center = new JPanel(new GridLayout(1, 4, 10, 10));

        center.add(createCategoryPanel("Cosmetics",
                new String[]{"Bath Soap", "Face Cream", "Hair Gel"}));

        center.add(createCategoryPanel("Grocery",
                new String[]{"Rice", "Food Oil", "Sugar"}));

        center.add(createCategoryPanel("Cold Drinks",
                new String[]{"Coca Cola", "Sprite", "Frooti"}));

        // Billing Area
        billArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(billArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Customer Billing Area"));
        center.add(scroll);

        return center;
    }

    // ---------------- CATEGORY PANEL ----------------
    private JPanel createCategoryPanel(String title, String[] items) {
        JPanel panel = new JPanel(new GridLayout(items.length, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        for (String item : items) {
            panel.add(new JLabel(item));
            JTextField qty = new JTextField("0");
            panel.add(qty);
            qtyFields.put(item, qty); // barcode mock
        }
        return panel;
    }

    // ---------------- BOTTOM PANEL ----------------
    private JPanel createBottomPanel() {
        JPanel bottom = new JPanel();

        JButton totalBtn = new JButton("Total");
        JButton generateBtn = new JButton("Generate Bill");
        JButton clearBtn = new JButton("Clear");
        JButton exitBtn = new JButton("Exit");

        totalBtn.addActionListener(e -> calculateBill());
        generateBtn.addActionListener(e -> calculateBill());
        clearBtn.addActionListener(e -> clearAll());
        exitBtn.addActionListener(e -> System.exit(0));

        bottom.add(totalBtn);
        bottom.add(generateBtn);
        bottom.add(clearBtn);
        bottom.add(exitBtn);

        return bottom;
    }

    // ---------------- BILL LOGIC ----------------
    private void calculateBill() {
        billArea.setText("");
        int total = 0;

        billArea.append("Products\tQty\tPrice\n");
        billArea.append("--------------------------------\n");

        for (String item : qtyFields.keySet()) {
            int qty = Integer.parseInt(qtyFields.get(item).getText());
            if (qty > 0) {
                int price = priceMap.get(item) * qty;
                billArea.append(item + "\t" + qty + "\t" + price + "\n");
                total += price;
            }
        }

        billArea.append("--------------------------------\n");
        billArea.append("Total Bill : Rs. " + total);
    }

    private void clearAll() {
        billArea.setText("");
        for (JTextField f : qtyFields.values()) {
            f.setText("0");
            f.setBackground(Color.WHITE);
        }
    }

    // ---------------- MOCK DATA ----------------
    private void initPrices() {
        priceMap.put("Bath Soap", 40);
        priceMap.put("Face Cream", 120);
        priceMap.put("Hair Gel", 90);

        priceMap.put("Rice", 60);
        priceMap.put("Food Oil", 140);
        priceMap.put("Sugar", 45);

        priceMap.put("Coca Cola", 50);
        priceMap.put("Sprite", 45);
        priceMap.put("Frooti", 30);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() ->
                new InventoryBillingApp().setVisible(true));
    }
}
