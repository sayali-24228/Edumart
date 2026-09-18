import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FavoritesFrame extends JFrame {

    private User currentUser;

    private JPanel favoritesPanel;

    private JLabel countLabel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public FavoritesFrame(User user) {

        this.currentUser = user;


        setTitle(
                "EduMart - My Favorites"
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


        loadFavorites();
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
                        "♥ My Favorites"
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
        // REFRESH
        // ======================================

        JButton refreshButton =
                UIUtils.createButton(
                        "Refresh"
                );


        refreshButton.setPreferredSize(
                new Dimension(
                        100,
                        40
                )
        );


        refreshButton.addActionListener(
                e -> loadFavorites()
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
        // FAVORITES PANEL
        // ======================================

        favoritesPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                4,
                                15,
                                15
                        )
                );


        favoritesPanel.setBackground(
                UIUtils.BACKGROUND
        );


        favoritesPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        25,
                        25
                )
        );


        JScrollPane scrollPane =
                new JScrollPane(
                        favoritesPanel
                );


        scrollPane.setBorder(null);


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
    // LOAD FAVORITES
    // ==========================================

    private void loadFavorites() {

        FavoriteDAO dao =
                new FavoriteDAO();


        List<Product> products =
                dao.getFavoriteProducts(
                        currentUser.getId()
                );


        favoritesPanel.removeAll();


        countLabel.setText(
                products.size()
                +
                (
                        products.size() == 1
                                ? " saved product"
                                : " saved products"
                )
        );


        if (products.isEmpty()) {

            showEmptyFavorites();

        } else {

            favoritesPanel.setLayout(
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

                favoritesPanel.add(
                        createFavoriteCard(
                                product
                        )
                );
            }
        }


        favoritesPanel.revalidate();

        favoritesPanel.repaint();
    }


    // ==========================================
    // CREATE FAVORITE CARD
    // ==========================================

    private JPanel createFavoriteCard(
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

        JLabel imageLabel =
                new JLabel(
                        "No Image"
                );


        imageLabel.setPreferredSize(
                new Dimension(
                        230,
                        125
                )
        );


        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );


        imageLabel.setVerticalAlignment(
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


        imageLabel.setForeground(
                UIUtils.MUTED
        );


        loadImage(
                imageLabel,
                product.getImagePath()
        );


        card.add(
                imageLabel,
                BorderLayout.NORTH
        );


        // ======================================
        // INFORMATION
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


        JLabel nameLabel =
                new JLabel(
                        product.getName()
                );


        nameLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );


        nameLabel.setForeground(
                UIUtils.TEXT
        );


        JLabel categoryLabel =
                new JLabel(
                        safeText(
                                product.getCategory(),
                                "Other"
                        )
                );


        categoryLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
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
                                product.getPrice()
                        )
                );


        priceLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        20
                )
        );


        priceLabel.setForeground(
                UIUtils.PRIMARY
        );


        infoPanel.add(
                nameLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(
                        5
                )
        );


        infoPanel.add(
                categoryLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );


        infoPanel.add(
                priceLabel
        );


        card.add(
                infoPanel,
                BorderLayout.CENTER
        );


        // ======================================
        // BUTTONS
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


        // VIEW DETAILS

        JButton detailsButton =
                new JButton(
                        "View Details"
                );


        detailsButton.setFont(
                UIUtils.buttonFont()
        );


        detailsButton.setFocusPainted(
                false
        );


        detailsButton.addActionListener(
                e -> {

                    new ProductDetailsFrame(
                            product,
                            currentUser
                    ).setVisible(true);
                }
        );


        buttons.add(
                detailsButton
        );


        // ADD TO CART

        JButton cartButton =
                UIUtils.createButton(
                        "Add to Cart"
                );


        cartButton.addActionListener(
                e -> addToCart(product)
        );


        buttons.add(
                cartButton
        );


        // REMOVE

        JButton removeButton =
                new JButton(
                        "♥ Remove Favorite"
                );


        removeButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );


        removeButton.setForeground(
                new Color(
                        220,
                        38,
                        38
                )
        );


        removeButton.setBackground(
                UIUtils.WHITE
        );


        removeButton.setFocusPainted(
                false
        );


        removeButton.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );


        removeButton.addActionListener(
                e -> removeFavorite(product)
        );


        buttons.add(
                removeButton
        );


        card.add(
                buttons,
                BorderLayout.SOUTH
        );


        return card;
    }


    // ==========================================
    // ADD TO CART
    // ==========================================

    private void addToCart(
            Product product) {

        if (currentUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please login first.",
                    "EduMart",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (
                product.getSellerId()
                ==
                currentUser.getId()
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "You cannot add your own product to cart.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );

            return;
        }


        CartDAO dao =
                new CartDAO();


        boolean success =
                dao.addToCart(
                        currentUser.getId(),
                        product.getId()
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    product.getName()
                    + " added to cart.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to add product to cart.",
                    "EduMart",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // REMOVE FAVORITE
    // ==========================================

    private void removeFavorite(
            Product product) {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Remove "
                        + product.getName()
                        + " from favorites?",
                        "EduMart",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result !=
                JOptionPane.YES_OPTION
        ) {

            return;
        }


        FavoriteDAO dao =
                new FavoriteDAO();


        boolean success =
                dao.removeFavorite(
                        currentUser.getId(),
                        product.getId()
                );


        if (success) {

            loadFavorites();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to remove favorite.",
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


        Image image =
                icon.getImage();


        Image scaled =
                image.getScaledInstance(
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
    // EMPTY FAVORITES
    // ==========================================

    private void showEmptyFavorites() {

        favoritesPanel.setLayout(
                new BorderLayout()
        );


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


        JLabel icon =
                new JLabel(
                        "♡"
                );


        icon.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        55
                )
        );


        icon.setForeground(
                UIUtils.MUTED
        );


        icon.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel title =
                new JLabel(
                        "No favorites yet"
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
                        "Save products you like and find them here later."
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


        emptyPanel.add(
                Box.createVerticalStrut(
                        100
                )
        );


        emptyPanel.add(
                icon
        );


        emptyPanel.add(
                Box.createVerticalStrut(
                        10
                )
        );


        emptyPanel.add(
                title
        );


        emptyPanel.add(
                Box.createVerticalStrut(
                        8
                )
        );


        emptyPanel.add(
                message
        );


        favoritesPanel.add(
                emptyPanel,
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