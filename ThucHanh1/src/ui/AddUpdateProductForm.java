package ui;

import dao.ProductDAO;
import java.awt.HeadlessException;
import model.Product;

import javax.swing.*;

public class AddUpdateProductForm extends JFrame {
    JTextField txtId, txtName, txtPrice, txtQty;
    JButton btnSave;
    boolean isUpdate = false;
    MainForm parent;

    public AddUpdateProductForm(MainForm parent) {
        this(parent, null);
    }

    public AddUpdateProductForm(MainForm parent, Product p) {
        this.parent = parent;
        setTitle(p == null ? "Thêm sản phẩm" : "Cập nhật sản phẩm");
        setSize(300, 250);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel l1 = new JLabel("Mã:");
        l1.setBounds(20, 20, 80, 25);
        add(l1);

        txtId = new JTextField();
        txtId.setBounds(110, 20, 150, 25);
        add(txtId);

        JLabel l2 = new JLabel("Tên:");
        l2.setBounds(20, 60, 80, 25);
        add(l2);

        txtName = new JTextField();
        txtName.setBounds(110, 60, 150, 25);
        add(txtName);

        JLabel l3 = new JLabel("Giá:");
        l3.setBounds(20, 100, 80, 25);
        add(l3);

        txtPrice = new JTextField();
        txtPrice.setBounds(110, 100, 150, 25);
        add(txtPrice);

        JLabel l4 = new JLabel("Số lượng:");
        l4.setBounds(20, 140, 80, 25);
        add(l4);

        txtQty = new JTextField();
        txtQty.setBounds(110, 140, 150, 25);
        add(txtQty);

        btnSave = new JButton(p == null ? "Thêm" : "Cập nhật");
        btnSave.setBounds(90, 180, 100, 30);
        add(btnSave);

        if (p != null) {
            isUpdate = true;
            txtId.setText(p.getId());
            txtId.setEnabled(false);
            txtName.setText(p.getName());
            txtPrice.setText(String.valueOf(p.getPrice()));
            txtQty.setText(String.valueOf(p.getQuantity()));
        }

        btnSave.addActionListener(e -> save());
    }

    private void save() {
        try {
            String id = txtId.getText();
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int qty = Integer.parseInt(txtQty.getText());

            if (isUpdate) {
                Product p = ProductDAO.findById(id);
                p.setName(name);
                p.setPrice(price);
                p.setQuantity(qty);
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            } else {
                ProductDAO.add(new Product(id, name, price, qty));
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
            }

            parent.refreshTable(); 
            dispose();
        } catch (HeadlessException | NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ!");
        }
    }
}
