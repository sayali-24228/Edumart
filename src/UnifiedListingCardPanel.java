import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class UnifiedListingCardPanel extends JPanel {

    private final UnifiedListing listing;
    private final User currentUser;

    public UnifiedListingCardPanel(
            UnifiedListing listing,
            User currentUser) {

        this.listing = listing;
        this.currentUser = currentUser;

        setBackground(UIUtils.WHITE);
        setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER),
                        new EmptyBorder(10, 10, 10, 10)
                )
        );
        setLayout(new BorderLayout(0, 8));
        setPreferredSize(new Dimension(270, 430));

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showDetails();
                }
            }
        });

        createUI();
    }

    private void createUI() {

        JPanel body = new JPanel();
        body.setBackground(UIUtils.WHITE);
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel image = createImageLabel(listing.getImagePath());
        image.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(image);
        addClickToDetails(image);
        body.add(Box.createVerticalStrut(10));

        JLabel type = new JLabel(
                listing.getListingType()
        );
        type.setFont(new Font("Segoe UI", Font.BOLD, 11));
        type.setForeground(typeColor(listing.getListingType()));
        type.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(type);
        body.add(Box.createVerticalStrut(4));

        JLabel name = new JLabel(
                "<html><b>" + safe(listing.getName(), "Unnamed Item")
                        + "</b></html>"
        );
        name.setFont(new Font("Segoe UI", Font.BOLD, 16));
        name.setForeground(UIUtils.TEXT);
        name.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(name);
        addClickToDetails(name);
        body.add(Box.createVerticalStrut(5));

        JLabel category = new JLabel(
                safe(listing.getCategory(), "Other")
                        + " • "
                        + safe(listing.getConditionType(), "Not specified")
        );
        category.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        category.setForeground(UIUtils.MUTED);
        category.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(category);
        body.add(Box.createVerticalStrut(7));

        String description = safe(
                listing.getDescription(),
                "No description available."
        );
        if (description.length() > 75) {
            description = description.substring(0, 75) + "...";
        }

        JLabel desc = new JLabel(
                "<html>" + description + "</html>"
        );
        desc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        desc.setForeground(UIUtils.MUTED);
        desc.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(desc);
        body.add(Box.createVerticalStrut(7));

        if (UnifiedListing.EXCHANGE.equals(listing.getListingType())) {
            JLabel wanted = new JLabel(
                    "<html><b>Wants:</b> "
                            + safe(listing.getWantedItem(), "Any suitable item")
                            + "</html>"
            );
            wanted.setFont(new Font("Segoe UI", Font.PLAIN, 11));
            wanted.setForeground(UIUtils.TEXT);
            wanted.setAlignmentX(Component.LEFT_ALIGNMENT);
            body.add(wanted);
            body.add(Box.createVerticalStrut(5));
        } else if (UnifiedListing.SELL.equals(listing.getListingType())) {
            JLabel price = new JLabel(
                    "₹" + String.format("%.2f", listing.getPrice())
            );
            price.setFont(new Font("Segoe UI", Font.BOLD, 19));
            price.setForeground(UIUtils.PRIMARY);
            price.setAlignmentX(Component.LEFT_ALIGNMENT);
            body.add(price);
            body.add(Box.createVerticalStrut(5));
        } else {
            JLabel free = new JLabel("FREE • Donation");
            free.setFont(new Font("Segoe UI", Font.BOLD, 17));
            free.setForeground(UIUtils.SUCCESS);
            free.setAlignmentX(Component.LEFT_ALIGNMENT);
            body.add(free);
            body.add(Box.createVerticalStrut(5));
        }

        JLabel owner = new JLabel(
                "Listed by " + safe(listing.getOwnerName(), "Student")
        );
        owner.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        owner.setForeground(UIUtils.MUTED);
        owner.setAlignmentX(Component.LEFT_ALIGNMENT);
        body.add(owner);

        add(body, BorderLayout.CENTER);

        JPanel buttons = new JPanel(new GridLayout(2, 1, 0, 5));
        buttons.setBackground(UIUtils.WHITE);

        JButton details = new JButton("View Details");
        details.setFont(UIUtils.buttonFont());
        details.setFocusPainted(false);
        details.addActionListener(e -> showDetails());
        buttons.add(details);

        if (listing.getOwnerId() == currentUser.getId()) {
            JButton own = UIUtils.createOutlineButton("Your Listing");
            own.setEnabled(false);
            buttons.add(own);

        } else if (UnifiedListing.SELL.equals(listing.getListingType())) {
            JButton cart = UIUtils.createButton("Add to Cart");
            cart.addActionListener(e -> addToCart());
            buttons.add(cart);

        } else if (UnifiedListing.DONATE.equals(listing.getListingType())) {
            JButton request = UIUtils.createButton("Request Donation");
            request.addActionListener(e -> requestDonation());
            buttons.add(request);

        } else {
            JButton request = UIUtils.createButton("Request Exchange");
            request.addActionListener(e -> requestExchange());
            buttons.add(request);
        }

        add(buttons, BorderLayout.SOUTH);
    }

    private void addClickToDetails(JComponent component) {
        component.setCursor(new Cursor(Cursor.HAND_CURSOR));
        component.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    showDetails();
                }
            }
        });
    }

    private JLabel createImageLabel(String path) {

        JLabel label = new JLabel("No Image", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(new Color(243, 246, 250));
        label.setForeground(UIUtils.MUTED);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setPreferredSize(new Dimension(248, 145));
        label.setMaximumSize(new Dimension(Integer.MAX_VALUE, 145));

        if (path != null && !path.trim().isEmpty()) {
            File file = new File(path);

            if (file.exists()) {
                ImageIcon icon = new ImageIcon(path);
                if (icon.getIconWidth() > 0) {
                    Image scaled = icon.getImage().getScaledInstance(
                            248, 145, Image.SCALE_SMOOTH
                    );
                    label.setText("");
                    label.setIcon(new ImageIcon(scaled));
                }
            }
        }

        return label;
    }

    private void showDetails() {

        new ListingDetailsFrame(
                listing,
                currentUser
        ).setVisible(true);
    }

    private void addToCart() {

        if (currentUser == null) {
            showLoginWarning();
            return;
        }

        if (listing.getOwnerId() == currentUser.getId()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot add your own product to cart.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        boolean success = new CartDAO().addToCart(
                currentUser.getId(),
                listing.getId()
        );

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Item added to cart."
                        : "Unable to add item to cart.",
                "EduMart",
                success
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );
    }

    private void requestDonation() {

        if (currentUser == null) {
            showLoginWarning();
            return;
        }

        if (listing.getOwnerId() == currentUser.getId()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot request your own donation.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JTextArea messageArea = new JTextArea(
                "I am interested in receiving this item."
        );
        messageArea.setRows(5);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(UIUtils.normalFont());

        int result = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(messageArea),
                "Request Donation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            message = "I am interested in receiving this item.";
        }

        boolean success = new DonationRequestDAO().sendRequest(
                listing.getId(),
                currentUser.getId(),
                message
        );

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Donation request sent to " + listing.getOwnerName() + "."
                        : "A pending request may already exist.",
                "EduMart",
                success
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.WARNING_MESSAGE
        );
    }

    private void requestExchange() {

        if (currentUser == null) {
            showLoginWarning();
            return;
        }

        if (listing.getOwnerId() == currentUser.getId()) {
            JOptionPane.showMessageDialog(
                    this,
                    "You cannot request your own exchange.",
                    "EduMart",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }

        JTextArea messageArea = new JTextArea(
                "I am interested in exchanging with your item."
        );
        messageArea.setRows(5);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(UIUtils.normalFont());

        int result = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(messageArea),
                "Request Exchange",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String message = messageArea.getText().trim();
        if (message.isEmpty()) {
            message = "I am interested in exchanging with your item.";
        }

        boolean success = new ExchangeRequestDAO().sendRequest(
                listing.getId(),
                currentUser.getId(),
                message
        );

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Exchange request sent successfully."
                        : "Unable to send exchange request.",
                "EduMart",
                success
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );
    }

    private void showLoginWarning() {
        JOptionPane.showMessageDialog(
                this,
                "Please login first.",
                "EduMart",
                JOptionPane.WARNING_MESSAGE
        );
    }

    private Color typeColor(String type) {
        if (UnifiedListing.SELL.equals(type)) {
            return UIUtils.PRIMARY;
        }
        if (UnifiedListing.DONATE.equals(type)) {
            return UIUtils.SUCCESS;
        }
        return new Color(234, 88, 12);
    }

    private String safe(String value, String fallback) {
        return value == null || value.trim().isEmpty()
                ? fallback
                : value;
    }
}
