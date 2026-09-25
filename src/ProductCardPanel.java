import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ProductCardPanel extends JPanel {

    private Product product;

    private User currentUser;

    private JButton favoriteButton;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public ProductCardPanel(
            Product product,
            User currentUser) {

        this.product = product;

        this.currentUser = currentUser;


        setPreferredSize(
                new Dimension(
                        255,
                        390
                )
        );


        setBackground(
                UIUtils.WHITE
        );


        setLayout(
                new BorderLayout()
        );


        setBorder(
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

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    openDetails();
                }
            }
        });


        createUI();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void createUI() {

        JPanel content =
                new JPanel();


        content.setBackground(
                UIUtils.WHITE
        );


        content.setLayout(
                new BoxLayout(
                        content,
                        BoxLayout.Y_AXIS
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


        imageLabel.setMaximumSize(
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


        imageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );


        loadImage(
                imageLabel
        );

        imageLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        imageLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    openDetails();
                }
            }
        });


        content.add(
                imageLabel
        );


        content.add(
                Box.createVerticalStrut(
                        10
                )
        );


        // ======================================
        // PRODUCT NAME
        // ======================================

        String productName =
                product.getName();


        if (
                productName == null
        ) {

            productName =
                    "Unnamed Product";
        }


        JLabel nameLabel =
                new JLabel(
                        productName
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

        nameLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    openDetails();
                }
            }
        });


        content.add(
                nameLabel
        );


        content.add(
                Box.createVerticalStrut(
                        4
                )
        );


        // ======================================
        // CATEGORY
        // ======================================

        String category =
                product.getCategory();


        if (
                category == null
        ) {

            category =
                    "Other";
        }


        String condition =
                product.getConditionType();


        if (
                condition == null
                ||
                condition.trim().isEmpty()
        ) {

            condition =
                    "Not specified";
        }


        JLabel categoryLabel =
                new JLabel(
                        category
                        + " • "
                        + condition
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


        content.add(
                categoryLabel
        );


        content.add(
                Box.createVerticalStrut(
                        7
                )
        );


        // ======================================
        // DESCRIPTION
        // ======================================

        String description =
                product.getDescription();


        if (
                description == null
                ||
                description.trim().isEmpty()
        ) {

            description =
                    "No description available.";
        }


        if (
                description.length() > 60
        ) {

            description =
                    description.substring(
                            0,
                            60
                    )
                    + "...";
        }


        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                        + description
                        + "</html>"
                );


        descriptionLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );


        descriptionLabel.setForeground(
                UIUtils.MUTED
        );


        content.add(
                descriptionLabel
        );


        content.add(
                Box.createVerticalStrut(
                        7
                )
        );


        // ======================================
        // PRICE
        // ======================================

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


        content.add(
                priceLabel
        );


        add(
                content,
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


        // ======================================
        // DETAILS
        // ======================================

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
                e -> openDetails()
        );


        buttons.add(
                detailsButton
        );


        // ======================================
        // CART
        // ======================================

        JButton cartButton =
                UIUtils.createButton(
                        "Add to Cart"
                );


        cartButton.addActionListener(
                e -> addToCart()
        );


        buttons.add(
                cartButton
        );


        // ======================================
        // FAVORITE
        // ======================================

        favoriteButton =
                new JButton(
                        "♡ Add to Favorites"
                );


        favoriteButton.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );


        favoriteButton.setFocusPainted(
                false
        );


        favoriteButton.setBackground(
                UIUtils.WHITE
        );


        favoriteButton.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );


        favoriteButton.addActionListener(
                e -> toggleFavorite()
        );


        buttons.add(
                favoriteButton
        );


        add(
                buttons,
                BorderLayout.SOUTH
        );


        updateFavoriteButton();
    }


    // ==========================================
    // OPEN DETAILS
    // ==========================================

    private void openDetails() {

        new ProductDetailsFrame(
                product,
                currentUser
        ).setVisible(true);
    }


    // ==========================================
    // LOAD IMAGE
    // ==========================================

    private void loadImage(
            JLabel imageLabel) {

        String path =
                product.getImagePath();


        if (
                path == null
                ||
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

            imageLabel.setText(
                    "Image unavailable"
            );

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


        imageLabel.setText(
                ""
        );


        imageLabel.setIcon(
                new ImageIcon(
                        scaled
                )
        );
    }


    // ==========================================
    // ADD TO CART
    // ==========================================

    private void addToCart() {

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
    // FAVORITE BUTTON
    // ==========================================

    private void updateFavoriteButton() {

        if (currentUser == null) {

            return;
        }


        FavoriteDAO dao =
                new FavoriteDAO();


        boolean favorite =
                dao.isFavorite(
                        currentUser.getId(),
                        product.getId()
                );


        if (favorite) {

            favoriteButton.setText(
                    "♥ Remove Favorite"
            );


            favoriteButton.setForeground(
                    new Color(
                            220,
                            38,
                            38
                    )
            );

        } else {

            favoriteButton.setText(
                    "♡ Add to Favorites"
            );


            favoriteButton.setForeground(
                    UIUtils.TEXT
            );
        }
    }


    // ==========================================
    // TOGGLE FAVORITE
    // ==========================================

    private void toggleFavorite() {

        if (currentUser == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please login first.",
                    "EduMart",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        FavoriteDAO dao =
                new FavoriteDAO();


        boolean favorite =
                dao.isFavorite(
                        currentUser.getId(),
                        product.getId()
                );


        if (favorite) {

            dao.removeFavorite(
                    currentUser.getId(),
                    product.getId()
            );

        } else {

            dao.addFavorite(
                    currentUser.getId(),
                    product.getId()
            );
        }


        updateFavoriteButton();
    }
}