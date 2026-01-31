package ui;

import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public final class MainForm extends JFrame {

    JTable table;
    DefaultTableModel model;
    JTextField txtSearch;

    public MainForm() {
        setTitle("Quản lý cửa hàng tiện lợi");
        setSize(750, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JButton btnAdd = new JButton("Thêm");
        btnAdd.setBounds(20, 20, 90, 30);
        add(btnAdd);

        JButton btnUpdate = new JButton("Cập nhật");
        btnUpdate.setBounds(120, 20, 100, 30);
        add(btnUpdate);

        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBounds(230, 20, 90, 30);
        add(btnDelete);

        JButton btnMax = new JButton("Giá cao nhất");
        btnMax.setBounds(330, 20, 120, 30);
        add(btnMax);

        JButton btnTotal = new JButton("Tổng tồn kho");
        btnTotal.setBounds(460, 20, 120, 30);
        add(btnTotal);

        JButton btnPay = new JButton("Thanh toán");
        btnPay.setBounds(590, 20, 120, 30);
        add(btnPay);

        txtSearch = new JTextField();
        txtSearch.setBounds(20, 70, 200, 25);
        add(txtSearch);

        JButton btnSearch = new JButton("Tìm");
        btnSearch.setBounds(230, 70, 80, 25);
        add(btnSearch);

        model = new DefaultTableModel(new String[]{"Mã", "Tên", "Giá", "Số lượng"}, 0);
        table = new JTable(model);
        JScrollPane sp = new JScrollPane(table);
        sp.setBounds(20, 110, 700, 240);
        add(sp);

        btnAdd.addActionListener(e -> new AddUpdateProductForm(this).setVisible(true));
        btnUpdate.addActionListener(e -> update());
        btnDelete.addActionListener(e -> delete());
        btnSearch.addActionListener(e -> search());
        btnMax.addActionListener(e -> maxPrice());
        btnTotal.addActionListener(e -> totalQuantity());
        btnPay.addActionListener(e -> payment());

        refreshTable();
    }

    public void refreshTable() {
        model.setRowCount(0);
        for (Product p : ProductDAO.list) {
            model.addRow(new Object[]{
                p.getId(), p.getName(), p.getPrice(), p.getQuantity()
            });
        }
    }

    private void update() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần cập nhật!");
            return;
        }
        String id = model.getValueAt(row, 0).toString();
        Product p = ProductDAO.findById(id);
        new AddUpdateProductForm(this, p).setVisible(true);
    }

    private void delete() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Chọn sản phẩm cần xóa!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa sản phẩm này không?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            String id = model.getValueAt(row, 0).toString();
            ProductDAO.delete(id);
            refreshTable();
            JOptionPane.showMessageDialog(this, "Đã xóa thành công!");
        }
    }

    private void search() {
        String key = txtSearch.getText();
        Product p = ProductDAO.findById(key);
        if (p != null) {
            JOptionPane.showMessageDialog(this,
                    "Tên: " + p.getName()
                    + "\nGiá: " + p.getPrice()
                    + "\nSố lượng: " + p.getQuantity());
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy!");
        }
    }

    private void maxPrice() {
        Product p = ProductDAO.maxPrice();
        if (p != null) {
            JOptionPane.showMessageDialog(this,
                    "Sản phẩm giá cao nhất:\n"
                    + p.getName() + " - " + p.getPrice());
        }
    }

    private void totalQuantity() {
        JOptionPane.showMessageDialog(this,
                "Tổng số lượng tồn kho: " + ProductDAO.totalQuantity());
    }

    private void payment() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            double price = Double.parseDouble(model.getValueAt(row, 2).toString());
            int qty = Integer.parseInt(model.getValueAt(row, 3).toString());
            JOptionPane.showMessageDialog(this,
                    "Tổng tiền hóa đơn: " + (price * qty));
        }
    }
}
