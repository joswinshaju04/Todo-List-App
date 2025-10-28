package view;

import dao.UserDAO;
import view.RegisterView;
import app.TodoApp;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, signupButton;
    private JLabel messageLabel;

    public LoginView() {
        setTitle("Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Form panel with GridLayout (same as RegisterView)
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 10, 40));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        loginButton = new JButton("Login");
        formPanel.add(loginButton);

        signupButton = new JButton("Sign Up");
        formPanel.add(signupButton);

        messageLabel = new JLabel("");
        messageLabel.setForeground(Color.RED);
        formPanel.add(messageLabel);

        add(formPanel, BorderLayout.CENTER);

        // Login action
        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("Please enter both fields.");
                return;
            }

            UserDAO userDAO = new UserDAO();
            boolean success = userDAO.authenticate(username, password);

            if (success) {
                messageLabel.setText("");
                new TodoApp(username);
                dispose();
            } else {
                messageLabel.setText("Invalid credentials.");
            }
        });

        // Sign up action
        signupButton.addActionListener(e -> {
            new RegisterView();
            dispose();
        });

        setVisible(true);
    }
}