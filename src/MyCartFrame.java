import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MyCartFrame extends JFrame {

    private User currentUser;

    private JPanel cartPanel;

    private JLabel totalLabel;

    private double cartTotal = 0;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public MyCartFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - My Cart"
        );


        setSize(
                1000,
                700
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        setResizable(false);


        createUI();


        loadCart();
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
                        20,
                        25,
                        20,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "My Cart"
                );


        title.setFont(
                UIUtils.titleFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        JButton refreshButton =
                UIUtils.createButton(
                        "Refresh"
                );


        refreshButton.setPreferredSize(
                new Dimension(
                        95,
                        38
                )
        );


        refreshButton.addActionListener(
                e -> loadCart()
        );


        header.add(
                title,
                BorderLayout.WEST
        );


        header.add(
                refreshButton,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // CART PANEL
        // ======================================

        cartPanel =
                new JPanel();


        cartPanel.setBackground(
                UIUtils.BACKGROUND
        );


        cartPanel.setLayout(
                new BoxLayout(
                        cartPanel,
                        BoxLayout.Y_AXIS
                )
        );


        cartPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        cartPanel
                );


        scrollPane.setBorder(null);


        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        mainPanel.add(
                scrollPane,
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
                        "Total: ₹0.00"
                );


        totalLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );


        totalLabel.setForeground(
                UIUtils.TEXT
        );


        JButton checkoutButton =
                UIUtils.createButton(
                        "Proceed to Checkout"
                );


        checkoutButton.setPreferredSize(
                new Dimension(
                        210,
                        45
                )
        );


        checkoutButton.addActionListener(
                e -> openCheckout()
        );


        bottom.add(
                totalLabel,
                BorderLayout.WEST
        );


        bottom.add(
                checkoutButton,
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
    // LOAD CART
    // ==========================================

    private void loadCart() {

        cartPanel.removeAll();


        cartTotal = 0;


        String query =
                "SELECT "
                + "c.id AS cart_id, "
                + "c.product_id, "
                + "c.quantity, "
                + "p.name, "
                + "p.category, "
                + "p.price, "
                + "p.image_path "
                + "FROM cart c "
                + "INNER JOIN products p "
                + "ON c.product_id = p.id "
                + "WHERE c.user_id = ? "
                + "ORDER BY c.id DESC";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {

                showError(
                        "Database connection failed."
                );

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

                    boolean hasItems = false;


                    while (rs.next()) {

                        hasItems = true;


                        int cartId =
                                rs.getInt(
                                        "cart_id"
                                );


                        int productId =
                                rs.getInt(
                                        "product_id"
                                );


                        int quantity =
                                rs.getInt(
                                        "quantity"
                                );


                        String name =
                                rs.getString(
                                        "name"
                                );


                        String category =
                                rs.getString(
                                        "category"
                                );


                        double price =
                                rs.getDouble(
                                        "price"
                                );


                        String imagePath =
                                rs.getString(
                                        "image_path"
                                );


                        cartTotal +=
                                price * quantity;


                        JPanel card =
                                createCartCard(
                                        cartId,
                                        productId,
                                        name,
                                        category,
                                        price,
                                        quantity,
                                        imagePath
                                );


                        cartPanel.add(
                                card
                        );


                        cartPanel.add(
                                Box.createVerticalStrut(
                                        12
                                )
                        );
                    }


                    if (!hasItems) {

                        showEmptyCart();
                    }
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();


            showError(
                    "Unable to load cart."
            );
        }


        totalLabel.setText(
                "Total: ₹"
                + String.format(
                        "%.2f",
                        cartTotal
                )
        );


        cartPanel.revalidate();

        cartPanel.repaint();
    }


    // ==========================================
    // CREATE CART CARD
    // ==========================================

    private JPanel createCartCard(
            int cartId,
            int productId,
            String name,
            String category,
            double price,
            int quantity,
            String imagePath) {


        JPanel card =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );


        card.setBackground(
                UIUtils.WHITE
        );


        card.setBorder(
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


        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        125
                )
        );


        // ======================================
        // IMAGE
        // ======================================

        JLabel imageLabel =
                new JLabel(
                        "No Image"
                );


        imageLabel.setPreferredSize(
                new Dimension(
                        100,
                        90
                )
        );


        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        imageLabel.setOpaque(true);


        imageLabel.setBackground(
                new Color(
                        239,
                        246,
                        255
                )
        );


        loadImage(
                imageLabel,
                imagePath
        );


        card.add(
                imageLabel,
                BorderLayout.WEST
        );


        // ======================================
        // INFO
        // ======================================

        JPanel info =
                new JPanel();


        info.setBackground(
                UIUtils.WHITE
        );


        info.setLayout(
                new BoxLayout(
                        info,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel nameLabel =
                new JLabel(
                        name
                );


        nameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );


        nameLabel.setForeground(
                UIUtils.TEXT
        );


        JLabel categoryLabel =
                new JLabel(
                        category
                );


        categoryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        categoryLabel.setForeground(
                UIUtils.MUTED
        );


        JLabel priceLabel =
                new JLabel(
                        "₹"
                        + String.format(
                                "%.2f",
                                price
                        )
                );


        priceLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );


        priceLabel.setForeground(
                UIUtils.PRIMARY
        );


        info.add(
                nameLabel
        );


        info.add(
                Box.createVerticalStrut(
                        5
                )
        );


        info.add(
                categoryLabel
        );


        info.add(
                Box.createVerticalStrut(
                        8
                )
        );


        info.add(
                priceLabel
        );


        card.add(
                info,
                BorderLayout.CENTER
        );


        // ======================================
        // QUANTITY + DELETE
        // ======================================

        JPanel actions =
                new JPanel();


        actions.setBackground(
                UIUtils.WHITE
        );


        actions.setLayout(
                new BoxLayout(
                        actions,
                        BoxLayout.Y_AXIS
                )
        );


        JPanel quantityPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.CENTER,
                                4,
                                0
                        )
                );


        quantityPanel.setBackground(
                UIUtils.WHITE
        );


        JButton minus =
                new JButton(
                        "-"
                );


        JButton plus =
                new JButton(
                        "+"
                );


        JLabel quantityLabel =
                new JLabel(
                        String.valueOf(
                                quantity
                        )
                );


        quantityLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );


        minus.setPreferredSize(
                new Dimension(
                        35,
                        30
                )
        );


        plus.setPreferredSize(
                new Dimension(
                        35,
                        30
                )
        );


        minus.setFocusPainted(false);

        plus.setFocusPainted(false);


        minus.addActionListener(
                e -> {

                    if (quantity > 1) {

                        updateQuantity(
                                cartId,
                                quantity - 1
                        );

                    } else {

                        removeCartItem(
                                cartId
                        );
                    }
                }
        );


        plus.addActionListener(
                e -> updateQuantity(
                        cartId,
                        quantity + 1
                )
        );


        quantityPanel.add(
                minus
        );


        quantityPanel.add(
                quantityLabel
        );


        quantityPanel.add(
                plus
        );


        actions.add(
                quantityPanel
        );


        actions.add(
                Box.createVerticalStrut(
                        10
                )
        );


        JButton removeButton =
                new JButton(
                        "Remove"
                );


        removeButton.setForeground(
                new Color(
                        220,
                        38,
                        38
                )
        );


        removeButton.setFocusPainted(
                false
        );


        removeButton.addActionListener(
                e -> removeCartItem(
                        cartId
                )
        );


        actions.add(
                removeButton
        );


        card.add(
                actions,
                BorderLayout.EAST
        );


        return card;
    }


    // ==========================================
    // UPDATE QUANTITY
    // ==========================================

    private void updateQuantity(
                int cartId,
                int quantity) {

        String query =
                "UPDATE cart "
                + "SET quantity = ? "
                + "WHERE id = ? "
                + "AND user_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

                if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed.",
                        "EduMart",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
                }


                try (
                        PreparedStatement ps =
                                con.prepareStatement(query)
                ) {

                ps.setInt(
                        1,
                        quantity
                );


                ps.setInt(
                        2,
                        cartId
                );


                ps.setInt(
                        3,
                        currentUser.getId()
                );


                ps.executeUpdate();
                }


                loadCart();


        } catch (SQLException e) {

                e.printStackTrace();


                JOptionPane.showMessageDialog(
                        this,
                        "Unable to update cart quantity.",
                        "EduMart",
                        JOptionPane.ERROR_MESSAGE
                );
        }
        }


    // ==========================================
    // REMOVE ITEM
    // ==========================================

    private void removeCartItem(
        int cartId) {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Remove this item from your cart?",
                        "EduMart",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result !=
                JOptionPane.YES_OPTION
        ) {

                return;
        }


        String query =
                "DELETE FROM cart "
                + "WHERE id = ? "
                + "AND user_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

                if (con == null) {

                JOptionPane.showMessageDialog(
                        this,
                        "Database connection failed.",
                        "EduMart",
                        JOptionPane.ERROR_MESSAGE
                );

                return;
                }


                try (
                        PreparedStatement ps =
                                con.prepareStatement(query)
                ) {

                ps.setInt(
                        1,
                        cartId
                );


                ps.setInt(
                        2,
                        currentUser.getId()
                );


                ps.executeUpdate();
                }


                loadCart();


        } catch (SQLException e) {

                e.printStackTrace();


                JOptionPane.showMessageDialog(
                        this,
                        "Unable to remove item.",
                        "EduMart",
                        JOptionPane.ERROR_MESSAGE
                );
        }
        }


    // ==========================================
    // OPEN CHECKOUT
    // ==========================================

    private void openCheckout() {

        if (cartTotal <= 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your cart is empty.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        new CheckoutFrame(
                currentUser
        ).setVisible(true);
    }


    // ==========================================
    // LOAD IMAGE
    // ==========================================

    private void loadImage(
            JLabel label,
            String path) {

        if (
                path == null
                ||
                path.trim().isEmpty()
        ) {

            return;
        }


        ImageIcon icon =
                new ImageIcon(path);


        if (
                icon.getIconWidth() <= 0
        ) {

            return;
        }


        Image image =
                icon.getImage();


        Image scaled =
                image.getScaledInstance(
                        100,
                        90,
                        Image.SCALE_SMOOTH
                );


        label.setText("");


        label.setIcon(
                new ImageIcon(
                        scaled
                )
        );
    }


    // ==========================================
    // EMPTY CART
    // ==========================================

    private void showEmptyCart() {

        JPanel panel =
                new JPanel();


        panel.setBackground(
                UIUtils.BACKGROUND
        );


        panel.setLayout(
                new BoxLayout(
                        panel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "Your cart is empty"
                );


        title.setFont(
                UIUtils.headingFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        title.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel message =
                new JLabel(
                        "Add products from the marketplace."
                );


        message.setFont(
                UIUtils.normalFont()
        );


        message.setForeground(
                UIUtils.MUTED
        );


        message.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        panel.add(
                Box.createVerticalStrut(
                        120
                )
        );


        panel.add(
                title
        );


        panel.add(
                Box.createVerticalStrut(
                        8
                )
        );


        panel.add(
                message
        );


        cartPanel.add(
                panel
        );
    }


    // ==========================================
    // ERROR
    // ==========================================

    private void showError(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "EduMart",
                JOptionPane.ERROR_MESSAGE
        );
    }
}