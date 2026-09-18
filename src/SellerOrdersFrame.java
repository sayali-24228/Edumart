import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.List;

public class SellerOrdersFrame extends JFrame {

    private User currentUser;

    private JPanel ordersPanel;

    private JLabel countLabel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public SellerOrdersFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - Seller Orders"
        );


        setSize(
                1150,
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
                        "Seller Orders"
                );


        title.setFont(
                UIUtils.titleFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        JPanel headerRight =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                0
                        )
                );


        headerRight.setBackground(
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


        headerRight.add(
                countLabel
        );


        headerRight.add(
                refreshButton
        );


        header.add(
                title,
                BorderLayout.WEST
        );


        header.add(
                headerRight,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // ORDERS PANEL
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


        JScrollPane scrollPane =
                new JScrollPane(
                        ordersPanel
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


        setContentPane(
                mainPanel
        );
    }


    // ==========================================
    // LOAD ORDERS
    // ==========================================

    private void loadOrders() {

        ordersPanel.removeAll();


        SellerOrderDAO dao =
                new SellerOrderDAO();


        List<SellerOrderData> orders =
                dao.getSellerOrders(
                        currentUser.getId()
                );


        int orderCount =
                orders.size();


        countLabel.setText(
                orderCount
                + (
                        orderCount == 1
                                ? " order"
                                : " orders"
                )
        );


        if (orders.isEmpty()) {

            showEmptyOrders();

        } else {

            for (
                    SellerOrderData order :
                    orders
            ) {

                JPanel card =
                        createOrderCard(
                                order
                        );


                ordersPanel.add(
                        card
                );


                ordersPanel.add(
                        Box.createVerticalStrut(
                                12
                        )
                );
            }
        }


        ordersPanel.revalidate();

        ordersPanel.repaint();
    }


    // ==========================================
    // CREATE ORDER CARD
    // ==========================================

    private JPanel createOrderCard(
            SellerOrderData order) {


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
                        170
                )
        );


        // ======================================
        // ORDER INFORMATION
        // ======================================

        JPanel infoPanel =
                new JPanel();


        infoPanel.setBackground(
                UIUtils.WHITE
        );


        infoPanel.setLayout(
                new BoxLayout(
                        infoPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel productLabel =
                new JLabel(
                        safeText(
                                order.getProductName(),
                                "Unknown Product"
                        )
                );


        productLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );


        productLabel.setForeground(
                UIUtils.TEXT
        );


        JLabel orderLabel =
                new JLabel(
                        "Order #"
                        + order.getOrderId()
                );


        orderLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        orderLabel.setForeground(
                UIUtils.MUTED
        );


        JLabel buyerLabel =
                new JLabel(
                        "Buyer: "
                        + safeText(
                                order.getBuyerName(),
                                "Unknown"
                        )
                );


        buyerLabel.setFont(
                UIUtils.normalFont()
        );


        buyerLabel.setForeground(
                UIUtils.TEXT
        );


        JLabel emailLabel =
                new JLabel(
                        safeText(
                                order.getBuyerEmail(),
                                "No email"
                        )
                );


        emailLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        emailLabel.setForeground(
                UIUtils.MUTED
        );


        JLabel quantityLabel =
                new JLabel(
                        "Quantity: "
                        + order.getQuantity()
                );


        quantityLabel.setFont(
                UIUtils.normalFont()
        );


        quantityLabel.setForeground(
                UIUtils.TEXT
        );


        infoPanel.add(
                productLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(5)
        );


        infoPanel.add(
                orderLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(8)
        );


        infoPanel.add(
                buyerLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(3)
        );


        infoPanel.add(
                emailLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(6)
        );


        infoPanel.add(
                quantityLabel
        );


        card.add(
                infoPanel,
                BorderLayout.CENTER
        );


        // ======================================
        // RIGHT SIDE
        // ======================================

        JPanel rightPanel =
                new JPanel();


        rightPanel.setBackground(
                UIUtils.WHITE
        );


        rightPanel.setLayout(
                new BoxLayout(
                        rightPanel,
                        BoxLayout.Y_AXIS
                )
        );


        // ======================================
        // TOTAL PRICE
        // ======================================

        JLabel priceLabel =
                new JLabel(
                        "₹"
                        + String.format(
                                "%.2f",
                                order.getTotalPrice()
                        )
                );


        priceLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        19
                )
        );


        priceLabel.setForeground(
                UIUtils.PRIMARY
        );


        rightPanel.add(
                priceLabel
        );


        // ======================================
        // ORDER DATE
        // ======================================

        String dateText =
                "Date unavailable";


        if (
                order.getOrderDate() != null
        ) {

            dateText =
                    new SimpleDateFormat(
                            "dd MMM yyyy, hh:mm a"
                    ).format(
                            order.getOrderDate()
                    );
        }


        JLabel dateLabel =
                new JLabel(
                        dateText
                );


        dateLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );


        dateLabel.setForeground(
                UIUtils.MUTED
        );


        rightPanel.add(
                Box.createVerticalStrut(4)
        );


        rightPanel.add(
                dateLabel
        );


        // ======================================
        // STATUS
        // ======================================

        rightPanel.add(
                Box.createVerticalStrut(10)
        );


        JComboBox<String> statusBox =
                new JComboBox<>(
                        new String[]{
                                "Pending",
                                "Confirmed",
                                "Shipped",
                                "Delivered",
                                "Cancelled"
                        }
                );


        statusBox.setPreferredSize(
                new Dimension(
                        145,
                        32
                )
        );


        statusBox.setMaximumSize(
                new Dimension(
                        145,
                        32
                )
        );


        String currentStatus =
                order.getStatus();


        if (
                currentStatus != null
                &&
                !currentStatus.trim().isEmpty()
        ) {

            statusBox.setSelectedItem(
                    currentStatus
            );
        }


        rightPanel.add(
                statusBox
        );


        // ======================================
        // UPDATE BUTTON
        // ======================================

        rightPanel.add(
                Box.createVerticalStrut(7)
        );


        JButton updateButton =
                UIUtils.createButton(
                        "Update Status"
                );


        updateButton.setPreferredSize(
                new Dimension(
                        145,
                        35
                )
        );


        updateButton.setMaximumSize(
                new Dimension(
                        145,
                        35
                )
        );


        updateButton.addActionListener(
                e -> {

                    String newStatus =
                            (String)
                            statusBox.getSelectedItem();


                    if (
                            newStatus == null
                    ) {

                        return;
                    }


                    SellerOrderDAO dao =
                            new SellerOrderDAO();


                    boolean success =
                            dao.updateOrderStatus(
                                    order.getOrderId(),
                                    currentUser.getId(),
                                    newStatus
                            );


                    if (success) {

                        JOptionPane.showMessageDialog(
                                this,
                                "Order #"
                                + order.getOrderId()
                                + " status updated to "
                                + newStatus
                                + ".",
                                "EduMart",
                                JOptionPane.INFORMATION_MESSAGE
                        );


                        loadOrders();

                    } else {

                        JOptionPane.showMessageDialog(
                                this,
                                "Unable to update order status.",
                                "EduMart",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
        );


        rightPanel.add(
                updateButton
        );


        card.add(
                rightPanel,
                BorderLayout.EAST
        );


        return card;
    }


    // ==========================================
    // EMPTY ORDERS
    // ==========================================

    private void showEmptyOrders() {

        JPanel emptyPanel =
                new JPanel();


        emptyPanel.setBackground(
                UIUtils.BACKGROUND
        );


        emptyPanel.setLayout(
                new BoxLayout(
                        emptyPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(
                        "No orders yet"
                );


        titleLabel.setFont(
                UIUtils.headingFont()
        );


        titleLabel.setForeground(
                UIUtils.TEXT
        );


        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel messageLabel =
                new JLabel(
                        "Orders for your products will appear here."
                );


        messageLabel.setFont(
                UIUtils.normalFont()
        );


        messageLabel.setForeground(
                UIUtils.MUTED
        );


        messageLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        emptyPanel.add(
                Box.createVerticalStrut(
                        130
                )
        );


        emptyPanel.add(
                titleLabel
        );


        emptyPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );


        emptyPanel.add(
                messageLabel
        );


        ordersPanel.add(
                emptyPanel
        );
    }


    // ==========================================
    // SAFE TEXT
    // ==========================================

    private String safeText(
            String value,
            String fallback) {

        if (
                value == null
                ||
                value.trim().isEmpty()
        ) {

            return fallback;
        }

        return value;
    }
}