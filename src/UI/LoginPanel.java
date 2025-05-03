package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private CustomButton loginButton;

    public LoginPanel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);
        createUI();
    }

    private void createUI() {
        // Tiêu đề
        JLabel titleLabel = new JLabel("ĐĂNG NHẬP HỆ THỐNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Panel chính chứa các trường nhập
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tên đăng nhập
        JLabel usernameLabel = new JLabel("Mã nhân viên:");
        usernameLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(usernameField, gbc);

        // Mật khẩu
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordLabel.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        gbc.gridx = 1;
        inputPanel.add(passwordField, gbc);

        // Nút đăng nhập
        loginButton = new CustomButton("Đăng nhập", new Color(70, 130, 180), Color.WHITE, 10);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        inputPanel.add(loginButton, gbc);

        add(inputPanel, BorderLayout.CENTER);
        
    }

    public void addLoginListener(ActionListener listener) {
        if (loginButton != null) {
            loginButton.addActionListener(listener);
        }
    }

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        usernameField.requestFocus();
        
    }
}
