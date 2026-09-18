import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckoutFrame extends JFrame {

    private User currentUser;

    private JPanel itemsPanel;

    private JLabel totalLabel;

    private JRadioButton codButton;
    private JRadioButton upiButton;
    private JRadioButton cardButton;
    private JRadioButton netBankingButton;

    private double totalAmount = 0;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public CheckoutFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - Checkout"
        );


        setSize(
                900,
                700
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        setResizable(false);


        createUI();


        loadCartItems();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(
                        new BorderLayout()
                );


        mainPanel.setBackground(
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
                UIUtils.WHITE
        );


        header.setBorder(
                new EmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "Checkout"
                );


        title.setFont(
                UIUtils.titleFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        header.add(
                title,
                BorderLayout.WEST
        );


        mainPanel.add(
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


        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
                )
        );


        content.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        // ======================================
        // DELIVERY
        // ======================================

        JPanel delivery =
                createSection();


        JLabel deliveryTitle =
                createSectionTitle(
                        "Delivery Information"
                );


        delivery.add(
                deliveryTitle
        );


        delivery.add(
                Box.createVerticalStrut(
                        10
                )
        );


        JLabel nameLabel =
                new JLabel(
                        "Customer: "
                        + currentUser.getName()
                );


        nameLabel.setFont(
                UIUtils.normalFont()
        );


        nameLabel.setForeground(
                UIUtils.TEXT
        );


        JLabel emailLabel =
                new JLabel(
                        "Email: "
                        + currentUser.getEmail()
                );


        emailLabel.setFont(
                UIUtils.normalFont()
        );


        emailLabel.setForeground(
                UIUtils.MUTED
        );


        delivery.add(
                nameLabel
        );


        delivery.add(
                Box.createVerticalStrut(
                        5
                )
        );


        delivery.add(
                emailLabel
        );


        content.add(
                delivery
        );


        content.add(
                Box.createVerticalStrut(
                        15
                )
        );


        // ======================================
        // ITEMS
        // ======================================

        JPanel itemsSection =
                createSection();


        JLabel itemsTitle =
                createSectionTitle(
                        "Order Items"
                );


        itemsSection.add(
                itemsTitle
        );


        itemsSection.add(
                Box.createVerticalStrut(
                        10
                )
        );


        itemsPanel =
                new JPanel();


        itemsPanel.setBackground(
                UIUtils.WHITE
        );


        itemsPanel.setLayout(
                new BoxLayout(
                        itemsPanel,
                        BoxLayout.Y_AXIS
                )
        );


        itemsSection.add(
                itemsPanel
        );


        content.add(
                itemsSection
        );


        content.add(
                Box.createVerticalStrut(
                        15
                )
        );


        // ======================================
        // PAYMENT
        // ======================================

        JPanel payment =
                createSection();


        JLabel paymentTitle =
                createSectionTitle(
                        "Payment Method"
                );


        payment.add(
                paymentTitle
        );


        payment.add(
                Box.createVerticalStrut(
                        8
                )
        );


        codButton =
                new JRadioButton(
                        "Cash on Delivery"
                );


        upiButton =
                new JRadioButton(
                        "UPI"
                );


        cardButton =
                new JRadioButton(
                        "Debit / Credit Card"
                );


        netBankingButton =
                new JRadioButton(
                        "Net Banking"
                );


        codButton.setSelected(
                true
        );


        ButtonGroup group =
                new ButtonGroup();


        group.add(codButton);

        group.add(upiButton);

        group.add(cardButton);

        group.add(netBankingButton);


        JRadioButton[] buttons = {
                codButton,
                upiButton,
                cardButton,
                netBankingButton
        };


        for (
                JRadioButton button :
                buttons
        ) {

            button.setBackground(
                    UIUtils.WHITE
            );


            button.setFont(
                    UIUtils.normalFont()
            );


            payment.add(
                    button
            );
        }


        content.add(
                payment
        );


        mainPanel.add(
                new JScrollPane(
                        content
                ),
                BorderLayout.CENTER
        );


        // ======================================
        // BOTTOM
        // ======================================

        JPanel bottom =
                new JPanel(
                        new BorderLayout()
                );


        bottom.setBackground(
                UIUtils.WHITE
        );


        bottom.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );


        totalLabel =
                new JLabel(
                        "Total Amount: ₹0.00"
                );


        totalLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );


        totalLabel.setForeground(
                UIUtils.TEXT
        );


        JButton placeOrderButton =
                UIUtils.createButton(
                        "Place Order"
                );


        placeOrderButton.setPreferredSize(
                new Dimension(
                        170,
                        45
                )
        );


        placeOrderButton.addActionListener(
                e -> placeOrder()
        );


        bottom.add(
                totalLabel,
                BorderLayout.WEST
        );


        bottom.add(
                placeOrderButton,
                BorderLayout.EAST
        );


        mainPanel.add(
                bottom,
                BorderLayout.SOUTH
        );


        setContentPane(
                mainPanel
        );
    }


    // ==========================================
    // CREATE SECTION
    // ==========================================

    private JPanel createSection() {

        JPanel panel =
                new JPanel();


        panel.setBackground(
                UIUtils.WHITE
        );


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );


        panel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        return panel;
    }


    // ==========================================
    // SECTION TITLE
    // ==========================================

    private JLabel createSectionTitle(
            String text) {

        JLabel label =
                new JLabel(
                        text
                );


        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        label.setForeground(
                UIUtils.TEXT
        );


        return label;
    }


    // ==========================================
    // LOAD CART ITEMS
    // ==========================================

    private void loadCartItems() {

        itemsPanel.removeAll();


        totalAmount = 0;


        String query =
                "SELECT "
                + "p.name, "
                + "c.quantity, "
                + "p.price "
                + "FROM cart c "
                + "INNER JOIN products p "
                + "ON c.product_id = p.id "
                + "WHERE c.user_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {
                return;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(query)
            ) {

                ps.setInt(
                        1,
                        currentUser.getId()
                );


                try (
                        ResultSet rs =
                                ps.executeQuery()
                ) {

                    while (rs.next()) {

                        String name =
                                rs.getString(
                                        "name"
                                );


                        int quantity =
                                rs.getInt(
                                        "quantity"
                                );


                        double price =
                                rs.getDouble(
                                        "price"
                                );


                        double subtotal =
                                quantity * price;


                        totalAmount +=
                                subtotal;


                        JPanel row =
                                new JPanel(
                                        new BorderLayout()
                                );


                        row.setBackground(
                                UIUtils.WHITE
                        );


                        row.setMaximumSize(
                                new Dimension(
                                        Integer.MAX_VALUE,
                                        45
                                )
                        );


                        JLabel nameLabel =
                                new JLabel(
                                        name
                                        + " × "
                                        + quantity
                                );


                        nameLabel.setFont(
                                UIUtils.normalFont()
                        );


                        nameLabel.setForeground(
                                UIUtils.TEXT
                        );


                        JLabel priceLabel =
                                new JLabel(
                                        "₹"
                                        + String.format(
                                                "%.2f",
                                                subtotal
                                        )
                                );


                        priceLabel.setFont(
                                new Font(
                                        "Segoe UI",
                                        Font.BOLD,
                                        14
                                )
                        );


                        priceLabel.setForeground(
                                UIUtils.PRIMARY
                        );


                        row.add(
                                nameLabel,
                                BorderLayout.WEST
                        );


                        row.add(
                                priceLabel,
                                BorderLayout.EAST
                        );


                        itemsPanel.add(
                                row
                        );


                        itemsPanel.add(
                                Box.createVerticalStrut(
                                        5
                                )
                        );
                    }
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();
        }


        totalLabel.setText(
                "Total Amount: ₹"
                + String.format(
                        "%.2f",
                        totalAmount
                )
        );


        itemsPanel.revalidate();

        itemsPanel.repaint();
    }


    // ==========================================
    // PLACE ORDER
    // ==========================================

    private void placeOrder() {

        if (totalAmount <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your cart is empty.",
                    "EduMart",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        String paymentMethod =
                getSelectedPaymentMethod();


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Place your order for ₹"
                        + String.format(
                                "%.2f",
                                totalAmount
                        )
                        + " using "
                        + paymentMethod
                        + "?",
                        "Confirm Order",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result !=
                JOptionPane.YES_OPTION
        ) {

            return;
        }


        OrderDAO dao =
                new OrderDAO();


        boolean success =
                dao.placeOrderFromCart(
                        currentUser.getId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Order placed successfully!\n"
                    + "Payment: "
                    + paymentMethod,
                    "Order Placed",
                    JOptionPane.INFORMATION_MESSAGE
            );


            dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to place the order.",
                    "EduMart",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // PAYMENT METHOD
    // ==========================================

    private String getSelectedPaymentMethod() {

        if (
                codButton.isSelected()
        ) {

            return "Cash on Delivery";
        }


        if (
                upiButton.isSelected()
        ) {

            return "UPI";
        }


        if (
                cardButton.isSelected()
        ) {

            return "Debit / Credit Card";
        }


        return "Net Banking";
    }
}