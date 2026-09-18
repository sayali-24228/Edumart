import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class MyOrdersFrame extends JFrame {

    private User currentUser;

    private JPanel ordersPanel;

    private JLabel countLabel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public MyOrdersFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - My Orders"
        );


        setSize(
                1050,
                720
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        setResizable(false);


        createUI();


        loadOrders();
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
                        "My Orders"
                );


        title.setFont(
                UIUtils.titleFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        JPanel right =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );


        right.setBackground(
                UIUtils.WHITE
        );


        countLabel =
                new JLabel(
                        "0 orders"
                );


        countLabel.setFont(
                UIUtils.normalFont()
        );


        countLabel.setForeground(
                UIUtils.MUTED
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
                e -> loadOrders()
        );


        right.add(
                countLabel
        );


        right.add(
                refreshButton
        );


        header.add(
                title,
                BorderLayout.WEST
        );


        header.add(
                right,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // ORDERS
        // ======================================

        ordersPanel =
                new JPanel();


        ordersPanel.setBackground(
                UIUtils.BACKGROUND
        );


        ordersPanel.setLayout(
                new BoxLayout(
                        ordersPanel,
                        BoxLayout.Y_AXIS
                )
        );


        ordersPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        25,
                        25
                )
        );


        JScrollPane scroll =
                new JScrollPane(
                        ordersPanel
                );


        scroll.setBorder(null);


        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );


        scroll.getVerticalScrollBar()
                .setUnitIncrement(16);


        mainPanel.add(
                scroll,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // ==========================================
    // LOAD ORDERS
    // ==========================================

    private void loadOrders() {

        ordersPanel.removeAll();


        String query =
                "SELECT "
                + "o.id AS order_id, "
                + "o.quantity, "
                + "o.total_price, "
                + "o.order_status, "
                + "o.order_date, "
                + "p.name AS product_name, "
                + "p.category "
                + "FROM orders o "
                + "INNER JOIN products p "
                + "ON o.product_id = p.id "
                + "WHERE o.buyer_id = ? "
                + "ORDER BY o.order_date DESC";


        int count = 0;


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

                        count++;


                        int orderId =
                                rs.getInt(
                                        "order_id"
                                );


                        String productName =
                                rs.getString(
                                        "product_name"
                                );


                        String category =
                                rs.getString(
                                        "category"
                                );


                        int quantity =
                                rs.getInt(
                                        "quantity"
                                );


                        double totalPrice =
                                rs.getDouble(
                                        "total_price"
                                );


                        String status =
                                rs.getString(
                                        "order_status"
                                );


                        java.sql.Timestamp date =
                                rs.getTimestamp(
                                        "order_date"
                                );


                        ordersPanel.add(
                                createOrderCard(
                                        orderId,
                                        productName,
                                        category,
                                        quantity,
                                        totalPrice,
                                        status,
                                        date
                                )
                        );


                        ordersPanel.add(
                                Box.createVerticalStrut(
                                        12
                                )
                        );
                    }
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Unable to load orders.",
                    "EduMart",
                    JOptionPane.ERROR_MESSAGE
            );
        }


        countLabel.setText(
                count
                + (
                        count == 1
                                ? " order"
                                : " orders"
                )
        );


        if (count == 0) {

            showEmptyOrders();
        }


        ordersPanel.revalidate();

        ordersPanel.repaint();
    }


    // ==========================================
    // CREATE ORDER CARD
    // ==========================================

    private JPanel createOrderCard(
            int orderId,
            String productName,
            String category,
            int quantity,
            double totalPrice,
            String status,
            java.sql.Timestamp date) {


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
                                18,
                                20,
                                18,
                                20
                        )
                )
        );


        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        135
                )
        );


        // ======================================
        // LEFT
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


        JLabel product =
                new JLabel(
                        productName
                );


        product.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );


        product.setForeground(
                UIUtils.TEXT
        );


        JLabel order =
                new JLabel(
                        "Order #"
                        + orderId
                );


        order.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        order.setForeground(
                UIUtils.MUTED
        );


        JLabel categoryLabel =
                new JLabel(
                        category
                        + " • Quantity: "
                        + quantity
                );


        categoryLabel.setFont(
                UIUtils.normalFont()
        );


        categoryLabel.setForeground(
                UIUtils.TEXT
        );


        String dateText =
                "Date unavailable";


        if (date != null) {

            dateText =
                    new SimpleDateFormat(
                            "dd MMM yyyy, hh:mm a"
                    ).format(date);
        }


        JLabel dateLabel =
                new JLabel(
                        dateText
                );


        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        dateLabel.setForeground(
                UIUtils.MUTED
        );


        info.add(
                product
        );


        info.add(
                Box.createVerticalStrut(
                        5
                )
        );


        info.add(
                order
        );


        info.add(
                Box.createVerticalStrut(
                        7
                )
        );


        info.add(
                categoryLabel
        );


        info.add(
                Box.createVerticalStrut(
                        5
                )
        );


        info.add(
                dateLabel
        );


        card.add(
                info,
                BorderLayout.CENTER
        );


        // ======================================
        // RIGHT
        // ======================================

        JPanel right =
                new JPanel();


        right.setBackground(
                UIUtils.WHITE
        );


        right.setLayout(
                new BoxLayout(
                        right,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel price =
                new JLabel(
                        "₹"
                        + String.format(
                                "%.2f",
                                totalPrice
                        )
                );


        price.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        price.setForeground(
                UIUtils.PRIMARY
        );


        JLabel statusLabel =
                new JLabel(
                        status
                );


        statusLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        statusLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );


        statusLabel.setOpaque(true);


        statusLabel.setBorder(
                new EmptyBorder(
                        6,
                        12,
                        6,
                        12
                )
        );


        setStatusStyle(
                statusLabel,
                status
        );


        right.add(
                price
        );


        right.add(
                Box.createVerticalStrut(
                        10
                )
        );


        right.add(
                statusLabel
        );


        card.add(
                right,
                BorderLayout.EAST
        );


        return card;
    }


    // ==========================================
    // STATUS STYLE
    // ==========================================

    private void setStatusStyle(
            JLabel label,
            String status) {

        if (status == null) {

            status = "Pending";
        }


        if (
                status.equalsIgnoreCase(
                        "Delivered"
                )
        ) {

            label.setForeground(
                    new Color(
                            22,
                            163,
                            74
                    )
            );


            label.setBackground(
                    new Color(
                            220,
                            252,
                            231
                    )
            );


        } else if (
                status.equalsIgnoreCase(
                        "Cancelled"
                )
        ) {

            label.setForeground(
                    new Color(
                            220,
                            38,
                            38
                    )
            );


            label.setBackground(
                    new Color(
                            254,
                            226,
                            226
                    )
            );


        } else if (
                status.equalsIgnoreCase(
                        "Shipped"
                )
        ) {

            label.setForeground(
                    UIUtils.PRIMARY
            );


            label.setBackground(
                    new Color(
                            219,
                            234,
                            254
                    )
            );


        } else {

            label.setForeground(
                    new Color(
                            180,
                            83,
                            9
                    )
            );


            label.setBackground(
                    new Color(
                            254,
                            243,
                            199
                    )
            );
        }
    }


    // ==========================================
    // EMPTY
    // ==========================================

    private void showEmptyOrders() {

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
                        "No orders yet"
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
                        "Your purchased products will appear here."
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


        ordersPanel.add(
                panel
        );
    }
}