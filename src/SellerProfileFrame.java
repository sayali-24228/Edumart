import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SellerProfileFrame extends JFrame {

    private User currentUser;

    private JLabel productsValue;

    private JLabel ordersValue;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SellerProfileFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - Seller Profile"
        );


        setSize(
                850,
                650
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        setResizable(false);


        createUI();


        loadStatistics();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void createUI() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );


        main.setBackground(
                UIUtils.BACKGROUND
        );


        // ======================================
        // HEADER
        // ======================================

        JPanel header =
                new JPanel(
                        new BorderLayout()
                );


        header.setBackground(
                UIUtils.PRIMARY
        );


        header.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );


        JLabel title =
                new JLabel(
                        "Seller Profile"
                );


        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );


        title.setForeground(
                Color.WHITE
        );


        header.add(
                title,
                BorderLayout.WEST
        );


        main.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // CONTENT
        // ======================================

        JPanel content =
                new JPanel();


        content.setBackground(
                UIUtils.BACKGROUND
        );


        content.setBorder(
                new EmptyBorder(
                        30,
                        50,
                        30,
                        50
                )
        );


        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );


        // ======================================
        // PROFILE CARD
        // ======================================

        JPanel profileCard =
                new JPanel();


        profileCard.setBackground(
                UIUtils.WHITE
        );


        profileCard.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(
                                25,
                                30,
                                25,
                                30
                        )
                )
        );


        profileCard.setLayout(
                new BoxLayout(
                        profileCard,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel avatar =
                new JLabel(
                        getInitials(
                                currentUser.getName()
                        )
                );


        avatar.setPreferredSize(
                new Dimension(
                        80,
                        80
                )
        );


        avatar.setMaximumSize(
                new Dimension(
                        80,
                        80
                )
        );


        avatar.setMinimumSize(
                new Dimension(
                        80,
                        80
                )
        );


        avatar.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        avatar.setVerticalAlignment(
                SwingConstants.CENTER
        );


        avatar.setOpaque(true);


        avatar.setBackground(
                UIUtils.PRIMARY
        );


        avatar.setForeground(
                Color.WHITE
        );


        avatar.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        25
                )
        );


        avatar.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        profileCard.add(
                avatar
        );


        profileCard.add(
                Box.createVerticalStrut(
                        15
                )
        );


        JLabel name =
                new JLabel(
                        safeText(
                                currentUser.getName(),
                                "User"
                        )
                );


        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );


        name.setForeground(
                UIUtils.TEXT
        );


        name.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        profileCard.add(
                name
        );


        profileCard.add(
                Box.createVerticalStrut(
                        5
                )
        );


        JLabel email =
                new JLabel(
                        safeText(
                                currentUser.getEmail(),
                                "No email"
                        )
                );


        email.setFont(
                UIUtils.normalFont()
        );


        email.setForeground(
                UIUtils.MUTED
        );


        email.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        profileCard.add(
                email
        );


        content.add(
                profileCard
        );


        content.add(
                Box.createVerticalStrut(
                        25
                )
        );


        // ======================================
        // STATISTICS
        // ======================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                20,
                                0
                        )
                );


        stats.setBackground(
                UIUtils.BACKGROUND
        );


        JPanel productsCard =
                createStatCard(
                        "Products Listed",
                        "0"
                );


        productsValue =
                (JLabel)
                productsCard.getClientProperty(
                        "valueLabel"
                );


        JPanel ordersCard =
                createStatCard(
                        "Orders Received",
                        "0"
                );


        ordersValue =
                (JLabel)
                ordersCard.getClientProperty(
                        "valueLabel"
                );


        stats.add(
                productsCard
        );


        stats.add(
                ordersCard
        );


        content.add(
                stats
        );


        content.add(
                Box.createVerticalStrut(
                        25
                )
        );


        // ======================================
        // ACTIONS
        // ======================================

        JPanel actions =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                10,
                                0
                        )
                );


        actions.setBackground(
                UIUtils.BACKGROUND
        );


        JButton productsButton =
                UIUtils.createButton(
                        "My Products"
                );


        productsButton.setPreferredSize(
                new Dimension(
                        140,
                        42
                )
        );


        productsButton.addActionListener(
                e -> {

                    new MyProductsFrame(
                            currentUser
                    ).setVisible(true);
                }
        );


        JButton ordersButton =
                UIUtils.createButton(
                        "Seller Orders"
                );


        ordersButton.setPreferredSize(
                new Dimension(
                        140,
                        42
                )
        );


        ordersButton.addActionListener(
                e -> {

                    new SellerOrdersFrame(
                            currentUser
                    ).setVisible(true);
                }
        );


        actions.add(
                productsButton
        );


        actions.add(
                ordersButton
        );


        content.add(
                actions
        );


        main.add(
                content,
                BorderLayout.CENTER
        );


        setContentPane(
                main
        );
    }


    // ==========================================
    // STAT CARD
    // ==========================================

    private JPanel createStatCard(
            String title,
            String value) {

        JPanel card =
                new JPanel();


        card.setBackground(
                UIUtils.WHITE
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );


        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel valueLabel =
                new JLabel(
                        value
                );


        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );


        valueLabel.setForeground(
                UIUtils.PRIMARY
        );


        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );


        titleLabel.setFont(
                UIUtils.normalFont()
        );


        titleLabel.setForeground(
                UIUtils.MUTED
        );


        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        card.add(
                valueLabel
        );


        card.add(
                Box.createVerticalStrut(
                        5
                )
        );


        card.add(
                titleLabel
        );


        card.putClientProperty(
                "valueLabel",
                valueLabel
        );


        return card;
    }


    // ==========================================
    // LOAD STATISTICS
    // ==========================================

    private void loadStatistics() {

        ProductDAO dao =
                new ProductDAO();


        int productCount =
                dao.getSellerProductCount(
                        currentUser.getId()
                );


        int orderCount =
                dao.getSellerOrderCount(
                        currentUser.getId()
                );


        productsValue.setText(
                String.valueOf(
                        productCount
                )
        );


        ordersValue.setText(
                String.valueOf(
                        orderCount
                )
        );
    }


    // ==========================================
    // GET INITIALS
    // ==========================================

    private String getInitials(
            String name) {

        if (
                name == null ||
                name.trim().isEmpty()
        ) {

            return "U";
        }


        String[] parts =
                name.trim().split(
                        "\\s+"
                );


        if (parts.length == 1) {

            return parts[0]
                    .substring(
                            0,
                            1
                    )
                    .toUpperCase();
        }


        return (
                parts[0].substring(
                        0,
                        1
                )
                +
                parts[
                        parts.length - 1
                ].substring(
                        0,
                        1
                )
        ).toUpperCase();
    }


    // ==========================================
    // SAFE TEXT
    // ==========================================

    private String safeText(
            String value,
            String fallback) {

        if (
                value == null ||
                value.trim().isEmpty()
        ) {

            return fallback;
        }


        return value;
    }
}