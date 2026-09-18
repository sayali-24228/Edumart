import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    public RegisterFrame() {

        setTitle("EduMart - Create Account");

        setSize(900, 620);

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);

        setResizable(false);

        buildUI();
    }

    private void buildUI() {

        JPanel main =
                new JPanel(new GridLayout(1, 2));

        // =========================
        // LEFT
        // =========================

        JPanel left =
                new JPanel();

        left.setBackground(
                UIUtils.PRIMARY
        );

        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );

        left.setBorder(
                BorderFactory.createEmptyBorder(
                        90, 55, 60, 55
                )
        );

        JLabel logo =
                new JLabel("🛒");

        logo.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        55
                )
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel title =
                new JLabel("Join EduMart");

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        34
                )
        );

        title.setForeground(Color.WHITE);

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel text =
                new JLabel(
                        "<html>Create your account and<br>" +
                        "start buying or selling.</html>"
                );

        text.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        18
                )
        );

        text.setForeground(
                new Color(209, 213, 219)
        );

        text.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        left.add(logo);
        left.add(
                Box.createVerticalStrut(15)
        );
        left.add(title);
        left.add(
                Box.createVerticalStrut(15)
        );
        left.add(text);

        // =========================
        // RIGHT
        // =========================

        JPanel right =
                new JPanel(
                        new GridBagLayout()
                );

        right.setBackground(
                UIUtils.BACKGROUND
        );

        JPanel card =
                new JPanel();

        card.setBackground(Color.WHITE);

        card.setPreferredSize(
                new Dimension(390, 510)
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                28, 35, 28, 35
                        )
                )
        );

        JLabel heading =
                new JLabel("Create Account");

        heading.setFont(
                UIUtils.titleFont()
        );

        heading.setForeground(
                UIUtils.TEXT
        );

        heading.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        nameField =
                UIUtils.createTextField();

        emailField =
                UIUtils.createTextField();

        passwordField =
                UIUtils.createPasswordField();

        confirmPasswordField =
                UIUtils.createPasswordField();

        addField(
                card,
                "Full Name",
                nameField
        );

        addField(
                card,
                "Email",
                emailField
        );

        addField(
                card,
                "Password",
                passwordField
        );

        addField(
                card,
                "Confirm Password",
                confirmPasswordField
        );

        JButton registerButton =
                UIUtils.createButton(
                        "Create Account"
                );

        registerButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        registerButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JButton loginButton =
                new JButton(
                        "Already have an account? Login"
                );

        loginButton.setFont(
                UIUtils.normalFont()
        );

        loginButton.setForeground(
                UIUtils.PRIMARY
        );

        loginButton.setBackground(Color.WHITE);

        loginButton.setBorderPainted(false);

        loginButton.setFocusPainted(false);

        loginButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(heading);

        card.add(
                Box.createVerticalStrut(22)
        );

        card.add(registerButton);

        card.add(
                Box.createVerticalStrut(10)
        );

        card.add(loginButton);

        right.add(card);

        main.add(left);
        main.add(right);

        add(main);

        registerButton.addActionListener(
                e -> register()
        );

        loginButton.addActionListener(
                e -> {

                    dispose();

                    new LoginFrame()
                            .setVisible(true);
                }
        );
    }

    private void addField(
            JPanel panel,
            String labelText,
            JComponent field
    ) {

        JLabel label =
                UIUtils.label(labelText);

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        field.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        40
                )
        );

        panel.add(label);

        panel.add(
                Box.createVerticalStrut(5)
        );

        panel.add(field);

        panel.add(
                Box.createVerticalStrut(12)
        );
    }

    private void register() {

        String name =
                nameField.getText().trim();

        String email =
                emailField.getText().trim();

        String password =
                new String(
                        passwordField.getPassword()
                );

        String confirmPassword =
                new String(
                        confirmPasswordField
                                .getPassword()
                );

        if (name.isEmpty() ||
                email.isEmpty() ||
                password.isEmpty() ||
                confirmPassword.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please fill all fields.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!email.contains("@") ||
                !email.contains(".")) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid email address.",
                    "Invalid Email",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (password.length() < 6) {

            JOptionPane.showMessageDialog(
                    this,
                    "Password must contain at least 6 characters.",
                    "Weak Password",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        if (!password.equals(confirmPassword)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Passwords do not match.",
                    "Password Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        User user =
                new User(
                        name,
                        email,
                        password
                );

        UserDAO dao =
                new UserDAO();

        boolean success =
                dao.registerUser(user);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Account created successfully!",
                    "Welcome to EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new LoginFrame()
                    .setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "This email is already registered.",
                    "Registration Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}