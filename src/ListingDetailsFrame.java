import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

/** Full e-commerce-style details page for SELL, DONATE and EXCHANGE listings. */
public class ListingDetailsFrame extends JFrame {

    private final UnifiedListing listing;
    private final User currentUser;
    private JPanel relatedPanel;

    public ListingDetailsFrame(UnifiedListing listing, User currentUser) {
        this.listing = listing;
        this.currentUser = currentUser;

        setTitle("EduMart - " + safe(listing.getName(), "Listing Details"));
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
        loadRelatedListings();
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

        JLabel title = new JLabel("Listing Details");
        title.setFont(UIUtils.titleFont());
        title.setForeground(UIUtils.TEXT);

        header.add(back, BorderLayout.WEST);
        header.add(title, BorderLayout.CENTER);
        root.add(header, BorderLayout.NORTH);

        ViewportWidthPanel page = new ViewportWidthPanel();
        page.setBackground(UIUtils.BACKGROUND);
        page.setBorder(new EmptyBorder(25, 30, 35, 30));
        page.setLayout(new BoxLayout(page, BoxLayout.Y_AXIS));

        page.add(createDetailsCard());
        page.add(Box.createVerticalStrut(30));

        JLabel relatedTitle = new JLabel("Related Products & Listings");
        relatedTitle.setFont(UIUtils.headingFont());
        relatedTitle.setForeground(UIUtils.TEXT);
        relatedTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        page.add(relatedTitle);
        page.add(Box.createVerticalStrut(12));

        relatedPanel = new ViewportWidthPanel(new GridLayout(0, 4, 15, 15));
        relatedPanel.setBackground(UIUtils.BACKGROUND);
        relatedPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        relatedPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        page.add(relatedPanel);

        JScrollPane scroll = new JScrollPane(page);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.getVerticalScrollBar().setUnitIncrement(18);
        root.add(scroll, BorderLayout.CENTER);

        setContentPane(root);
    }

    private JPanel createDetailsCard() {
        JPanel card = new JPanel(new BorderLayout(30, 0));
        card.setBackground(UIUtils.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER),
                new EmptyBorder(25, 25, 25, 25)
        ));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel image = createLargeImage(listing.getImagePath());
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(UIUtils.WHITE);
        imagePanel.setPreferredSize(new Dimension(430, 360));
        imagePanel.add(image, BorderLayout.CENTER);
        card.add(imagePanel, BorderLayout.WEST);

        JPanel info = new JPanel();
        info.setBackground(UIUtils.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel type = new JLabel(safe(listing.getListingType(), "LISTING"));
        type.setFont(new Font("Segoe UI", Font.BOLD, 13));
        type.setForeground(typeColor(listing.getListingType()));

        JLabel name = new JLabel(safe(listing.getName(), "Unnamed Item"));
        name.setFont(new Font("Segoe UI", Font.BOLD, 30));
        name.setForeground(UIUtils.TEXT);

        JLabel category = new JLabel(
                safe(listing.getCategory(), "Other") + "  •  " +
                safe(listing.getConditionType(), "Condition not specified")
        );
        category.setFont(UIUtils.normalFont());
        category.setForeground(UIUtils.MUTED);

        info.add(type);
        info.add(Box.createVerticalStrut(8));
        info.add(name);
        info.add(Box.createVerticalStrut(10));
        info.add(category);
        info.add(Box.createVerticalStrut(14));

        if (UnifiedListing.SELL.equals(listing.getListingType())) {
            JLabel price = new JLabel("₹" + String.format("%.2f", listing.getPrice()));
            price.setFont(new Font("Segoe UI", Font.BOLD, 27));
            price.setForeground(UIUtils.PRIMARY);
            info.add(price);
            info.add(Box.createVerticalStrut(8));
        } else if (UnifiedListing.DONATE.equals(listing.getListingType())) {
            JLabel free = new JLabel("FREE • Donation");
            free.setFont(new Font("Segoe UI", Font.BOLD, 22));
            free.setForeground(UIUtils.SUCCESS);
            info.add(free);
            info.add(Box.createVerticalStrut(8));
        } else {
            JLabel wanted = new JLabel("Wants: " + safe(listing.getWantedItem(), "Any suitable item"));
            wanted.setFont(new Font("Segoe UI", Font.BOLD, 17));
            wanted.setForeground(UIUtils.PRIMARY);
            info.add(wanted);
            info.add(Box.createVerticalStrut(6));
            info.add(new JLabel("Wanted Category: " + safe(listing.getWantedCategory(), "Any")));
            info.add(Box.createVerticalStrut(6));
            info.add(new JLabel("Requirements: " + safe(listing.getAdditionalRequirements(), "None")));
            info.add(Box.createVerticalStrut(8));
        }

        JLabel seller = new JLabel("Listed by: " + safe(listing.getOwnerName(), "Student") +
                "  •  Seller ID: " + listing.getOwnerId());
        seller.setFont(UIUtils.normalFont());
        seller.setForeground(UIUtils.MUTED);
        info.add(seller);
        info.add(Box.createVerticalStrut(18));

        JLabel descTitle = new JLabel("Description");
        descTitle.setFont(UIUtils.headingFont());
        descTitle.setForeground(UIUtils.TEXT);
        info.add(descTitle);
        info.add(Box.createVerticalStrut(6));

        JTextArea desc = new JTextArea(safe(listing.getDescription(), "No description available."));
        desc.setFont(UIUtils.normalFont());
        desc.setForeground(UIUtils.TEXT);
        desc.setBackground(UIUtils.WHITE);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);
        desc.setEditable(false);
        desc.setBorder(null);
        desc.setRows(5);
        info.add(desc);
        info.add(Box.createVerticalStrut(18));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actions.setBackground(UIUtils.WHITE);
        addActionButtons(actions);
        info.add(actions);

        card.add(info, BorderLayout.CENTER);
        return card;
    }

    private void addActionButtons(JPanel actions) {
        if (currentUser == null) return;
        if (listing.getOwnerId() == currentUser.getId()) {
            JLabel own = new JLabel("This is your listing");
            own.setFont(UIUtils.buttonFont());
            own.setForeground(UIUtils.MUTED);
            actions.add(own);
            return;
        }

        if (UnifiedListing.SELL.equals(listing.getListingType())) {
            JButton cart = UIUtils.createButton("Add to Cart");
            cart.addActionListener(e -> addToCart());
            actions.add(cart);
        } else if (UnifiedListing.DONATE.equals(listing.getListingType())) {
            JButton request = UIUtils.createButton("Request Donation");
            request.addActionListener(e -> requestDonation());
            actions.add(request);
        } else {
            JButton request = UIUtils.createButton("Request Exchange");
            request.addActionListener(e -> requestExchange());
            actions.add(request);
        }
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

    private void loadRelatedListings() {
        relatedPanel.removeAll();

        List<UnifiedListing> listings = new UnifiedListingDAO().searchListings(
                currentUser == null ? -1 : currentUser.getId(),
                "",
                safe(listing.getCategory(), "All Categories"),
                "All Listings",
                "Newest"
        );

        int added = 0;
        for (UnifiedListing related : listings) {
            if (related.getListingType().equals(listing.getListingType()) && related.getId() == listing.getId()) {
                continue;
            }
            relatedPanel.add(new UnifiedListingCardPanel(related, currentUser));
            added++;
            if (added >= 4) break;
        }

        if (added == 0) {
            JLabel empty = new JLabel("No related products or listings available yet.");
            empty.setFont(UIUtils.normalFont());
            empty.setForeground(UIUtils.MUTED);
            relatedPanel.add(empty);
        }

        relatedPanel.revalidate();
        relatedPanel.repaint();
    }

    private void addToCart() {
        boolean success = new CartDAO().addToCart(currentUser.getId(), listing.getId());
        JOptionPane.showMessageDialog(this,
                success ? listing.getName() + " added to cart." : "Unable to add product to cart.",
                "EduMart",
                success ? JOptionPane.INFORMATION_MESSAGE : JOptionPane.ERROR_MESSAGE);
    }

    private void requestDonation() {
        String message = JOptionPane.showInputDialog(this, "Message to donor (optional):", "Request Donation", JOptionPane.PLAIN_MESSAGE);
        new DonationRequestDAO().sendRequest(listing.getId(), currentUser.getId(), message == null ? "" : message);
        JOptionPane.showMessageDialog(this, "Donation request submitted.", "EduMart", JOptionPane.INFORMATION_MESSAGE);
    }

    private void requestExchange() {
        String message = JOptionPane.showInputDialog(this, "Message to owner (optional):", "Request Exchange", JOptionPane.PLAIN_MESSAGE);
        new ExchangeRequestDAO().sendRequest(listing.getId(), currentUser.getId(), message == null ? "" : message);
        JOptionPane.showMessageDialog(this, "Exchange request submitted.", "EduMart", JOptionPane.INFORMATION_MESSAGE);
    }

    private Color typeColor(String type) {
        if (UnifiedListing.SELL.equals(type)) return UIUtils.PRIMARY;
        if (UnifiedListing.DONATE.equals(type)) return UIUtils.SUCCESS;
        return new Color(234, 88, 12);
    }

    private Image scaleToFit(Image image, int maxWidth, int maxHeight) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        if (width <= 0 || height <= 0) return image;
        double scale = Math.min((double) maxWidth / width, (double) maxHeight / height);
        return image.getScaledInstance(Math.max(1, (int)(width * scale)), Math.max(1, (int)(height * scale)), Image.SCALE_SMOOTH);
    }

    private String safe(String value, String fallback) {
        return value == null || value.trim().isEmpty() ? fallback : value;
    }

    /** Keeps the details page and related-products grid inside the visible
     * window while allowing the page to grow vertically. */
    private static class ViewportWidthPanel extends JPanel implements Scrollable {

        ViewportWidthPanel() {
            super();
        }

        ViewportWidthPanel(LayoutManager layout) {
            super(layout);
        }

        @Override
        public Dimension getPreferredScrollableViewportSize() {
            return getPreferredSize();
        }

        @Override
        public int getScrollableUnitIncrement(
                Rectangle visibleRect,
                int orientation,
                int direction) {
            return 18;
        }

        @Override
        public int getScrollableBlockIncrement(
                Rectangle visibleRect,
                int orientation,
                int direction) {
            return orientation == SwingConstants.VERTICAL
                    ? Math.max(visibleRect.height - 40, 40)
                    : Math.max(visibleRect.width - 40, 40);
        }

        @Override
        public boolean getScrollableTracksViewportWidth() {
            return true;
        }

        @Override
        public boolean getScrollableTracksViewportHeight() {
            return false;
        }
    }

}
