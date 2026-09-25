import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

/**
 * Full e-commerce style product details page.
 * Keeps the existing EduMart theme and existing Cart/Favorites actions.
 */
public class ProductDetailsFrame extends JFrame {

    private final Product product;
    private final User currentUser;
    private JPanel relatedPanel;
    private JButton favoriteButton;

    public ProductDetailsFrame(Product product, User currentUser) {
        this.product = product;
        this.currentUser = currentUser;

        setTitle("EduMart - " + safe(product.getName(), "Product Details"));
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
        loadRelatedProducts();
    }

    private void createUI() {
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UIUtils.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIUtils.WHITE);
        header.setBorder(new EmptyBorder(15, 25, 15, 25));

        JButton back = UIUtils.createOutlineButton("← Back");
        back.setPreferredSize(new Dimension(100, 40));
        back.addActionListener(e -> dispose());

        JLabel title = new JLabel("Product Details");
        title.setFont(UIUtils.titleFont());
        title.setForeground(UIUtils.TEXT);

        header.add(back, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        JPanel page = new JPanel();
        page.setBackground(UIUtils.BACKGROUND);
        page.setBorder(new EmptyBorder(25, 30, 35, 30));
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        page.add(createProductDetailsCard());
        page.add(Box.createVerticalStrut(30));

        JLabel relatedTitle = new JLabel("Related Products");
        relatedTitle.setFont(UIUtils.headingFont());
        relatedTitle.setForeground(UIUtils.TEXT);
        relatedTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(relatedTitle);
        page.add(Box.createVerticalStrut(12));

        relatedPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 5));
        relatedPanel.setBackground(UIUtils.BACKGROUND);
        relatedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(relatedPanel);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createProductDetailsCard() {
        JPanel card = new JPanel(new BorderLayout(30, 0));
        card.setBackground(UIUtils.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER),
                new EmptyBorder(25, 25, 25, 25)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 560));

        JLabel image = createLargeImage(product.getImagePath());
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(UIUtils.WHITE);
        imagePanel.setPreferredSize(new Dimension(430, 360));
        imagePanel.add(image, BorderLayout.CENTER);
        card.add(imagePanel, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setBackground(UIUtils.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel category = new JLabel(
                safe(product.getCategory(), "Other") +
                "  •  " + safe(product.getConditionType(), "Condition not specified")
        );
        category.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        category.setForeground(UIUtils.MUTED);

        JLabel name = new JLabel(safe(product.getName(), "Unnamed Product"));
        name.setFont(new Font("Segoe UI", Font.BOLD, 30));
        name.setForeground(UIUtils.TEXT);

        JLabel price = new JLabel("₹" + String.format("%.2f", product.getPrice()));
        price.setFont(new Font("Segoe UI", Font.BOLD, 27));
        price.setForeground(UIUtils.PRIMARY);

        JLabel seller = new JLabel("Seller ID: " + product.getSellerId());
        seller.setFont(UIUtils.normalFont());
        seller.setForeground(UIUtils.MUTED);

        JLabel descTitle = new JLabel("Description");
        descTitle.setFont(UIUtils.headingFont());
        descTitle.setForeground(UIUtils.TEXT);

        JTextArea description = new JTextArea(safe(product.getDescription(), "No description available."));
        description.setFont(UIUtils.normalFont());
        description.setForeground(UIUtils.TEXT);
        description.setBackground(UIUtils.WHITE);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        description.setEditable(false);
        description.setBorder(null);
        description.setRows(5);

        info.add(category);
        info.add(Box.createVerticalStrut(10));
        info.add(name);
        info.add(Box.createVerticalStrut(12));
        info.add(price);
        info.add(Box.createVerticalStrut(8));
        info.add(seller);
        info.add(Box.createVerticalStrut(22));
        info.add(descTitle);
        info.add(Box.createVerticalStrut(8));
        info.add(description);
        info.add(Box.createVerticalStrut(20));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setBackground(UIUtils.WHITE);

        if (currentUser != null && product.getSellerId() != currentUser.getId()) {
            JButton cart = UIUtils.createButton("Add to Cart");
            cart.setPreferredSize(new Dimension(145, 42));
            cart.addActionListener(e -> addToCart());
            actions.add(cart);

            favoriteButton = UIUtils.createOutlineButton("♡ Add to Favorites");
            favoriteButton.setPreferredSize(new Dimension(175, 42));
            favoriteButton.addActionListener(e -> toggleFavorite());
            actions.add(favoriteButton);
            updateFavoriteButton();
        } else if (currentUser != null) {
            JLabel own = new JLabel("This is your product");
            own.setFont(UIUtils.buttonFont());
            own.setForeground(UIUtils.MUTED);
            actions.add(own);
        }

        info.add(actions);
        card.add(info, BorderLayout.CENTER);

        return card;
    }

    private JLabel createLargeImage(String path) {
        JLabel label = new JLabel("No Image Available", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(243, 246, 250));
        label.setForeground(UIUtils.MUTED);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setPreferredSize(new Dimension(430, 350));

        if (path != null && !path.trim().isEmpty()) {
            File file = new File(path);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(path);
                if (icon.getIconWidth() > 0) {
                    Image scaled = scaleToFit(icon.getImage(), 420, 340);
                    label.setText("");
                    label.setIcon(new ImageIcon(scaled));
                }
            }
        }
        return label;
    }

    private Image scaleToFit(Image image, int maxWidth, int maxHeight) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        if (width <= 0 || height <= 0) return image;

        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
        int newWidth = Math.max(1, (int) (width * scale));
        int newHeight = Math.max(1, (int) (height * scale));
        return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }

    private void loadRelatedProducts() {
        relatedPanel.removeAll();

        List<Product> products = new ProductDAO().getRelatedProducts(
                product.getId(),
                product.getCategory(),
                4
        );

        if (products.isEmpty()) {
            JLabel empty = new JLabel("No related products available yet.");
            empty.setFont(UIUtils.normalFont());
            empty.setForeground(UIUtils.MUTED);
            relatedPanel.add(empty);
        } else {
            for (Product related : products) {
                relatedPanel.add(new ProductCardPanel(related, currentUser));
            }
        }

        relatedPanel.revalidate();
        relatedPanel.repaint();
    }

    private void addToCart() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.", "EduMart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (product.getSellerId() == currentUser.getId()) {
            JOptionPane.showMessageDialog(this, "You cannot add your own product to cart.", "EduMart", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        boolean success = new CartDAO().addToCart(currentUser.getId(), product.getId());
        JOptionPane.showMessageDialog(
                this,
                success ? product.getName() + " added to cart." : "Unable to add product to cart.",
                "EduMart",
                success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE
        );
    }

    private void updateFavoriteButton() {
        if (favoriteButton == null || currentUser == null) return;

        boolean favorite = new FavoriteDAO().isFavorite(
                currentUser.getId(), product.getId()
        );

        if (favorite) {
            favoriteButton.setText("♥ Remove from Favorites");
            favoriteButton.setForeground(UIUtils.DANGER);
        } else {
            favoriteButton.setText("♡ Add to Favorites");
            favoriteButton.setForeground(UIUtils.PRIMARY);
        }
    }

    private void toggleFavorite() {
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please login first.", "EduMart", JOptionPane.WARNING_MESSAGE);
            return;
        }

        FavoriteDAO dao = new FavoriteDAO();
        if (dao.isFavorite(currentUser.getId(), product.getId())) {
            dao.removeFavorite(currentUser.getId(), product.getId());
        } else {
            dao.addFavorite(currentUser.getId(), product.getId());
        }
        updateFavoriteButton();
    }

    private String safe(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }
}
