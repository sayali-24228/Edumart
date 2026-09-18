import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductDetailsFrame extends JFrame {

    private Product product;
    private User currentUser;

    private JLabel imageLabel;
    private JLabel sellerLabel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public ProductDetailsFrame(
            Product product,
            User currentUser) {

        this.product = product;
        this.currentUser = currentUser;

        setTitle(
                "EduMart - Product Details"
        );

        setSize(
                900,
                650
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);

        createUI();
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
                        "Product Details"
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
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                30,
                                0
                        )
                );

        content.setBackground(
                UIUtils.BACKGROUND
        );

        content.setBorder(
                new EmptyBorder(
                        30,
                        30,
                        30,
                        30
                )
        );


        // ======================================
        // LEFT - IMAGE
        // ======================================

        JPanel imagePanel =
                new JPanel(
                        new BorderLayout()
                );

        imagePanel.setBackground(
                UIUtils.WHITE
        );

        imagePanel.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );


        imageLabel =
                new JLabel(
                        "No Image"
                );

        imageLabel.setHorizontalAlignment(
                SwingConstants.CENTER
        );

        imageLabel.setVerticalAlignment(
                SwingConstants.CENTER
        );

        imageLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        imageLabel.setForeground(
                UIUtils.MUTED
        );

        imageLabel.setOpaque(true);

        imageLabel.setBackground(
                new Color(
                        239,
                        246,
                        255
                )
        );


        loadProductImage();


        imagePanel.add(
                imageLabel,
                BorderLayout.CENTER
        );


        content.add(
                imagePanel
        );


        // ======================================
        // RIGHT - INFORMATION
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

        infoPanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
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
                        27
                )
        );

        nameLabel.setForeground(
                UIUtils.TEXT
        );


        infoPanel.add(
                nameLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(8)
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
                        25
                )
        );

        priceLabel.setForeground(
                UIUtils.PRIMARY
        );


        infoPanel.add(
                priceLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(15)
        );


        // ======================================
        // CATEGORY
        // ======================================

        JLabel categoryLabel =
                createInfoLabel(
                        "Category",
                        product.getCategory()
                );


        infoPanel.add(
                categoryLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(8)
        );


        // ======================================
        // CONDITION
        // ======================================

        String condition =
                product.getConditionType();


        if (
                condition == null
                ||
                condition.trim().isEmpty()
        ) {

            condition = "Not specified";
        }


        JLabel conditionLabel =
                createInfoLabel(
                        "Condition",
                        condition
                );


        infoPanel.add(
                conditionLabel
        );


        infoPanel.add(
                Box.createVerticalStrut(8)
        );


        // ======================================
        // SELLER
        // ======================================

        sellerLabel =
                createInfoLabel(
                        "Seller",
                        "Loading..."
                );


        infoPanel.add(
                sellerLabel
        );


        loadSellerName();


        infoPanel.add(
                Box.createVerticalStrut(18)
        );


        // ======================================
        // DESCRIPTION TITLE
        // ======================================

        JLabel descriptionTitle =
                new JLabel(
                        "Description"
                );


        descriptionTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );


        descriptionTitle.setForeground(
                UIUtils.TEXT
        );


        infoPanel.add(
                descriptionTitle
        );


        infoPanel.add(
                Box.createVerticalStrut(6)
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
                    "No description provided.";
        }


        JLabel descriptionLabel =
                new JLabel(
                        "<html>"
                        + description
                        + "</html>"
                );


        descriptionLabel.setFont(
                UIUtils.normalFont()
        );


        descriptionLabel.setForeground(
                UIUtils.MUTED
        );


        infoPanel.add(
                descriptionLabel
        );


        infoPanel.add(
                Box.createVerticalGlue()
        );


        // ======================================
        // BUTTONS
        // ======================================

        JPanel buttonPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );


        buttonPanel.setBackground(
                UIUtils.WHITE
        );


        JButton cartButton =
                UIUtils.createButton(
                        "Add to Cart"
                );


        JButton favoriteButton =
                new JButton(
                        "♡ Favorite"
                );


        favoriteButton.setFont(
                UIUtils.buttonFont()
        );

        favoriteButton.setFocusPainted(
                false
        );


        cartButton.addActionListener(
                e -> addToCart()
        );


        favoriteButton.addActionListener(
                e -> toggleFavorite(
                        favoriteButton
                )
        );


        updateFavoriteButton(
                favoriteButton
        );


        buttonPanel.add(
                cartButton
        );


        buttonPanel.add(
                favoriteButton
        );


        infoPanel.add(
                buttonPanel
        );


        content.add(
                infoPanel
        );


        mainPanel.add(
                content,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // ==========================================
    // INFO LABEL
    // ==========================================

    private JLabel createInfoLabel(
            String title,
            String value) {

        JLabel label =
                new JLabel(
                        "<html><b>"
                        + title
                        + ":</b> "
                        + value
                        + "</html>"
                );


        label.setFont(
                UIUtils.normalFont()
        );


        label.setForeground(
                UIUtils.TEXT
        );


        return label;
    }


    // ==========================================
    // LOAD IMAGE
    // ==========================================

    private void loadProductImage() {

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
                        380,
                        420,
                        Image.SCALE_SMOOTH
                );


        imageLabel.setText("");

        imageLabel.setIcon(
                new ImageIcon(
                        scaled
                )
        );
    }


    // ==========================================
    // LOAD SELLER NAME
    // ==========================================

    private void loadSellerName() {

        String query =
                "SELECT name FROM users WHERE id = ?";


        try (Connection con =
                    DBConnection.getConnection()) {

            if (con == null) {

                sellerLabel.setText(
                        "<html><b>Seller:</b> Unknown</html>"
                );

                return;
            }


            try (PreparedStatement ps =
                        con.prepareStatement(query)) {

                ps.setInt(
                        1,
                        product.getSellerId()
                );


                try (ResultSet rs =
                            ps.executeQuery()) {

                    if (rs.next()) {

                        String sellerName =
                                rs.getString("name");


                        if (
                                sellerName == null
                                ||
                                sellerName.trim().isEmpty()
                        ) {

                            sellerName = "Unknown";
                        }


                        sellerLabel.setText(
                                "<html><b>Seller:</b> "
                                + sellerName
                                + "</html>"
                        );

                    } else {

                        sellerLabel.setText(
                                "<html><b>Seller:</b> Unknown</html>"
                        );
                    }
                }
            }

        } catch (SQLException e) {

            e.printStackTrace();

            sellerLabel.setText(
                    "<html><b>Seller:</b> Unknown</html>"
            );
        }
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
                    "Product added to cart.",
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
    // UPDATE FAVORITE BUTTON
    // ==========================================

    private void updateFavoriteButton(
            JButton button) {

        if (currentUser == null) {
            return;
        }


        FavoriteDAO dao =
                new FavoriteDAO();


        if (
                dao.isFavorite(
                        currentUser.getId(),
                        product.getId()
                )
        ) {

            button.setText(
                    "♥ Remove Favorite"
            );

            button.setForeground(
                    new Color(
                            220,
                            38,
                            38
                    )
            );

        } else {

            button.setText(
                    "♡ Add to Favorites"
            );

            button.setForeground(
                    UIUtils.TEXT
            );
        }
    }


    // ==========================================
    // TOGGLE FAVORITE
    // ==========================================

    private void toggleFavorite(
            JButton button) {

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


        updateFavoriteButton(button);
    }
}