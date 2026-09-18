import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {

        setTitle("EduMart - Login");

        setSize(900, 600);

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
        // LEFT PANEL
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
                        80, 55, 60, 55
                )
        );

        JLabel logo =
                new JLabel("🛒");

        logo.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        60
                )
        );

        logo.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel brand =
                new JLabel("EduMart");

        brand.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        38
                )
        );

        brand.setForeground(Color.WHITE);

        brand.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel tagline =
                new JLabel(
                        "<html>Buy. Sell. Exchange.<br>" +
                        "Everything students need.</html>"
                );

        tagline.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        19
                )
        );

        tagline.setForeground(
                new Color(219, 234, 254)
        );

        tagline.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        left.add(logo);
        left.add(Box.createVerticalStrut(10));
        left.add(brand);
        left.add(Box.createVerticalStrut(15));
        left.add(tagline);

        // =========================
        // RIGHT PANEL
        // =========================

        JPanel right =
                new JPanel();

        right.setBackground(
                UIUtils.BACKGROUND
        );

        right.setLayout(
                new GridBagLayout()
        );

        JPanel card =
                new JPanel();

        card.setBackground(Color.WHITE);

        card.setPreferredSize(
                new Dimension(370, 420)
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
                                35, 35, 35, 35
                        )
                )
        );

        JLabel title =
                new JLabel("Welcome Back!");

        title.setFont(
                UIUtils.titleFont()
        );

        title.setForeground(
                UIUtils.TEXT
        );

        title.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel subtitle =
                new JLabel(
                        "Login to continue to EduMart"
                );

        subtitle.setFont(
                UIUtils.normalFont()
        );

        subtitle.setForeground(
                UIUtils.MUTED
        );

        subtitle.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        JLabel emailLabel =
                UIUtils.label("Email");

        emailLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        emailField =
                UIUtils.createTextField();

        emailField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        JLabel passwordLabel =
                UIUtils.label("Password");

        passwordLabel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        passwordField =
                UIUtils.createPasswordField();

        passwordField.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );

        JButton loginButton =
                UIUtils.createButton("Login");

        loginButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        loginButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );

        JButton registerButton =
                new JButton(
                        "Create a new account"
                );

        registerButton.setFont(
                UIUtils.normalFont()
        );

        registerButton.setForeground(
                UIUtils.PRIMARY
        );

        registerButton.setBackground(Color.WHITE);

        registerButton.setBorderPainted(false);

        registerButton.setFocusPainted(false);

        registerButton.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        // Add components

        card.add(title);

        card.add(
                Box.createVerticalStrut(5)
        );

        card.add(subtitle);

        card.add(
                Box.createVerticalStrut(30)
        );

        card.add(emailLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(emailField);

        card.add(
                Box.createVerticalStrut(18)
        );

        card.add(passwordLabel);

        card.add(
                Box.createVerticalStrut(7)
        );

        card.add(passwordField);

        card.add(
                Box.createVerticalStrut(25)
        );

        card.add(loginButton);

        card.add(
                Box.createVerticalStrut(12)
        );

        card.add(registerButton);

        right.add(card);

        main.add(left);
        main.add(right);

        add(main);

        // =========================
        // ACTIONS
        // =========================

        loginButton.addActionListener(
                e -> login()
        );

        registerButton.addActionListener(
                e -> {

                    dispose();

                    new RegisterFrame()
                            .setVisible(true);
                }
        );
    }

    private void login() {

        String email =
                emailField
                        .getText()
                        .trim();

        String password =
                new String(
                        passwordField
                                .getPassword()
                );

        if (email.isEmpty() ||
                password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter your email and password.",
                    "Missing Information",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        UserDAO dao =
                new UserDAO();

        User user =
                dao.loginUser(
                        email,
                        password
                );

        if (user != null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Welcome back, "
                            + user.getName()
                            + "!",
                    "Login Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            new MainFrame(user)
                    .setVisible(true);

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Invalid email or password.",
                    "Login Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}