package ui;

import javax.swing.*;

public class LoginForm extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(300, 180);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lbl1 = new JLabel("Username:");
        lbl1.setBounds(20, 20, 80, 25);
        add(lbl1);

        txtUser = new JTextField();
        txtUser.setBounds(110, 20, 150, 25);
        add(txtUser);

        JLabel lbl2 = new JLabel("Password:");
        lbl2.setBounds(20, 60, 80, 25);
        add(lbl2);

        txtPass = new JPasswordField();
        txtPass.setBounds(110, 60, 150, 25);
        add(txtPass);

        btnLogin = new JButton("Login");
        btnLogin.setBounds(90, 100, 100, 30);
        add(btnLogin);

        btnLogin.addActionListener(e -> login());
    }

    private void login() {
        if (txtUser.getText().equals("admin") &&
                new String(txtPass.getPassword()).equals("123")) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!");
            new MainForm().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tài khoản hoặc mật khẩu!");
        }
    }
}
