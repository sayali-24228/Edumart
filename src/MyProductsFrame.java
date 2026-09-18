import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MyProductsFrame extends JFrame {

    private User currentUser;

    private JPanel productsPanel;

    private JLabel countLabel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public MyProductsFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - My Products"
        );


        setSize(
                1200,
                750
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        setResizable(false);


        createUI();


        loadProducts();
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


        // ======================================
        // TITLE
        // ======================================

        JPanel titlePanel =
                new JPanel();


        titlePanel.setBackground(
                UIUtils.WHITE
        );


        titlePanel.setLayout(
                new BoxLayout(
                        titlePanel,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "My Products"
                );


        title.setFont(
                UIUtils.titleFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        countLabel =
                new JLabel(
                        "Loading..."
                );


        countLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        countLabel.setForeground(
                UIUtils.MUTED
        );


        titlePanel.add(
                title
        );


        titlePanel.add(
                Box.createVerticalStrut(
                        3
                )
        );


        titlePanel.add(
                countLabel
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );


        // ======================================
        // BUTTONS
        // ======================================

        JPanel headerButtons =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );


        headerButtons.setBackground(
                UIUtils.WHITE
        );


        JButton sellButton =
                UIUtils.createButton(
                        "+ Sell New Item"
                );


        sellButton.setPreferredSize(
                new Dimension(
                        135,
                        40
                )
        );


        sellButton.addActionListener(
                e -> {

                    new SellProductFrame(
                            currentUser
                    ).setVisible(true);
                }
        );


        JButton refreshButton =
                new JButton(
                        "Refresh"
                );


        refreshButton.setPreferredSize(
                new Dimension(
                        90,
                        40
                )
        );


        refreshButton.setFont(
                UIUtils.buttonFont()
        );


        refreshButton.setFocusPainted(
                false
        );


        refreshButton.setBackground(
                UIUtils.WHITE
        );


        refreshButton.setForeground(
                UIUtils.TEXT
        );


        refreshButton.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );


        refreshButton.addActionListener(
                e -> loadProducts()
        );


        headerButtons.add(
                sellButton
        );


        headerButtons.add(
                refreshButton
        );


        header.add(
                headerButtons,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // PRODUCTS
        // ======================================

        productsPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                4,
                                15,
                                15
                        )
                );


        productsPanel.setBackground(
                UIUtils.BACKGROUND
        );


        productsPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        25,
                        25
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        productsPanel
                );


        scrollPane.setBorder(
                null
        );


        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );


        mainPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // ==========================================
    // LOAD PRODUCTS
    // ==========================================

    private void loadProducts() {

        ProductDAO dao =
                new ProductDAO();


        List<Product> products =
                dao.getProductsBySeller(
                        currentUser.getId()
                );


        productsPanel.removeAll();


        countLabel.setText(
                products.size()
                +
                (
                        products.size() == 1
                                ? " product listed"
                                : " products listed"
                )
        );


        if (products.isEmpty()) {

            showEmptyState();

        } else {

            productsPanel.setLayout(
                    new GridLayout(
                            0,
                            4,
                            15,
                            15
                    )
            );


            for (
                    Product product :
                    products
            ) {

                productsPanel.add(
                        createProductCard(
                                product
                        )
                );
            }
        }


        productsPanel.revalidate();

        productsPanel.repaint();
    }


    // ==========================================
    // CREATE PRODUCT CARD
    // ==========================================

    private JPanel createProductCard(
            Product product) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
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
                                10,
                                10,
                                10,
                                10
                        )
                )
        );


        // ======================================
        // IMAGE
        // ======================================

        JLabel image =
                new JLabel(
                        "No Image"
                );


        image.setPreferredSize(
                new Dimension(
                        230,
                        125
                )
        );


        image.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        image.setVerticalAlignment(
                SwingConstants.CENTER
        );


        image.setOpaque(true);


        image.setBackground(
                new Color(
                        239,
                        246,
                        255
                )
        );


        image.setForeground(
                UIUtils.MUTED
        );


        loadImage(
                image,
                product.getImagePath()
        );


        card.add(
                image,
                BorderLayout.NORTH
        );


        // ======================================
        // INFORMATION
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


        JLabel name =
                new JLabel(
                        safeText(
                                product.getName(),
                                "Unnamed Product"
                        )
                );


        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        name.setForeground(
                UIUtils.TEXT
        );


        JLabel category =
                new JLabel(
                        safeText(
                                product.getCategory(),
                                "Other"
                        )
                );


        category.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );


        category.setForeground(
                UIUtils.MUTED
        );


        JLabel price =
                new JLabel(
                        "₹"
                        + String.format(
                                "%.2f",
                                product.getPrice()
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


        info.add(
                name
        );


        info.add(
                Box.createVerticalStrut(
                        5
                )
        );


        info.add(
                category
        );


        info.add(
                Box.createVerticalStrut(
                        8
                )
        );


        info.add(
                price
        );


        card.add(
                info,
                BorderLayout.CENTER
        );


        // ======================================
        // ACTION BUTTONS
        // ======================================

        JPanel buttons =
                new JPanel(
                        new GridLayout(
                                3,
                                1,
                                0,
                                5
                        )
                );


        buttons.setBackground(
                UIUtils.WHITE
        );


        JButton details =
                new JButton(
                        "View Details"
                );


        details.setFont(
                UIUtils.buttonFont()
        );


        details.setFocusPainted(
                false
        );


        details.addActionListener(
                e -> {

                    new ProductDetailsFrame(
                            product,
                            currentUser
                    ).setVisible(true);
                }
        );


        buttons.add(
                details
        );


        JButton edit =
                UIUtils.createButton(
                        "Edit Product"
                );


        edit.addActionListener(
                e -> {

                    new EditProductFrame(
                            product,
                            currentUser
                    ).setVisible(true);

                }
        );


        buttons.add(
                edit
        );


        JButton delete =
                new JButton(
                        "Delete Product"
                );


        delete.setFont(
                UIUtils.buttonFont()
        );


        delete.setForeground(
                new Color(
                        220,
                        38,
                        38
                )
        );


        delete.setBackground(
                UIUtils.WHITE
        );


        delete.setFocusPainted(
                false
        );


        delete.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );


        delete.addActionListener(
                e -> deleteProduct(product)
        );


        buttons.add(
                delete
        );


        card.add(
                buttons,
                BorderLayout.SOUTH
        );


        return card;
    }


    // ==========================================
    // DELETE PRODUCT
    // ==========================================

    private void deleteProduct(
            Product product) {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to delete\n"
                        + product.getName()
                        + "?",
                        "Delete Product",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );


        if (
                result !=
                JOptionPane.YES_OPTION
        ) {

            return;
        }


        ProductDAO dao =
                new ProductDAO();


        boolean success =
                dao.deleteProduct(
                        product.getId(),
                        currentUser.getId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Product deleted successfully.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );


            loadProducts();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to delete product.",
                    "EduMart",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // LOAD IMAGE
    // ==========================================

    private void loadImage(
            JLabel label,
            String path) {

        if (
                path == null ||
                path.trim().isEmpty()
        ) {

            return;
        }


        ImageIcon icon =
                new ImageIcon(
                        path
                );


        if (
                icon.getIconWidth() <= 0
        ) {

            return;
        }


        Image scaled =
                icon.getImage()
                        .getScaledInstance(
                                230,
                                125,
                                Image.SCALE_SMOOTH
                        );


        label.setText(
                ""
        );


        label.setIcon(
                new ImageIcon(
                        scaled
                )
        );
    }


    // ==========================================
    // EMPTY STATE
    // ==========================================

    private void showEmptyState() {

        productsPanel.setLayout(
                new BorderLayout()
        );


        JPanel empty =
                new JPanel();


        empty.setBackground(
                UIUtils.BACKGROUND
        );


        empty.setLayout(
                new BoxLayout(
                        empty,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel icon =
                new JLabel(
                        "📦"
                );


        icon.setFont(
                new Font(
                        "Segoe UI Emoji",
                        Font.PLAIN,
                        45
                )
        );


        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel title =
                new JLabel(
                        "You haven't listed any products"
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
                        "Start selling your unused items to other students."
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


        JButton sell =
                UIUtils.createButton(
                        "Sell Your First Item"
                );


        sell.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        sell.addActionListener(
                e -> {

                    new SellProductFrame(
                            currentUser
                    ).setVisible(true);
                }
        );


        empty.add(
                Box.createVerticalStrut(
                        90
                )
        );


        empty.add(
                icon
        );


        empty.add(
                Box.createVerticalStrut(
                        10
                )
        );


        empty.add(
                title
        );


        empty.add(
                Box.createVerticalStrut(
                        8
                )
        );


        empty.add(
                message
        );


        empty.add(
                Box.createVerticalStrut(
                        18
                )
        );


        empty.add(
                sell
        );


        productsPanel.add(
                empty,
                BorderLayout.CENTER
        );
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