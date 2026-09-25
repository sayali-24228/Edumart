import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BrowseProductsFrame extends JFrame {

    private final User currentUser;

    private JTextField searchField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> typeBox;
    private JComboBox<String> sortBox;
    private JPanel listingsPanel;
    private JLabel resultLabel;

    public BrowseProductsFrame(User user) {
        this.currentUser = user;

        setTitle("EduMart - Browse Marketplace");
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
        loadListings();
    }

    public BrowseProductsFrame(User user, String searchText) {
        this.currentUser = user;

        setTitle("EduMart - Browse Marketplace");
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();

        if (searchText != null) {
            searchField.setText(searchText);
        }

        loadListings();
    }

    private void createUI() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UIUtils.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout(20, 0));
        header.setBackground(UIUtils.WHITE);
        header.setBorder(new EmptyBorder(18, 25, 18, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(UIUtils.WHITE);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Browse Marketplace");
        title.setFont(UIUtils.titleFont());
        title.setForeground(UIUtils.TEXT);

        resultLabel = new JLabel("Loading listings...");
        resultLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        resultLabel.setForeground(UIUtils.MUTED);

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(3));
        titlePanel.add(resultLabel);

        JPanel searchPanel = new JPanel(
                new BorderLayout(8, 0)
        );
        searchPanel.setBackground(UIUtils.WHITE);

        searchField = UIUtils.createTextField();
        searchField.setPreferredSize(new Dimension(260, 40));

        JButton searchButton = UIUtils.createButton("Search");
        searchButton.setPreferredSize(new Dimension(90, 40));

        JButton refreshButton = UIUtils.createOutlineButton("Refresh");
        refreshButton.setPreferredSize(new Dimension(90, 40));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        JPanel right = new JPanel(new FlowLayout(
                FlowLayout.RIGHT, 8, 0
        ));
        right.setBackground(UIUtils.WHITE);
        right.add(searchPanel);
        right.add(refreshButton);

        header.add(titlePanel, BorderLayout.WEST);
        header.add(right, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UIUtils.BACKGROUND);

        JPanel filters = new JPanel(new FlowLayout(
                FlowLayout.LEFT, 10, 10
        ));
        filters.setBackground(UIUtils.BACKGROUND);
        filters.setBorder(new EmptyBorder(4, 25, 4, 25));

        filters.add(new JLabel("Category:"));

        categoryBox = new JComboBox<>(new String[]{
                "All Categories",
                "Books",
                "Electronics",
                "Stationery",
                "Bags",
                "Clothing",
                "Hostel Essentials",
                "Engineering Instruments",
                "Notes",
                "Sports",
                "Bicycles",
                "Other"
        });
        categoryBox.setFont(UIUtils.normalFont());
        filters.add(categoryBox);

        filters.add(new JLabel("Type:"));

        typeBox = new JComboBox<>(new String[]{
                "All Listings",
                "SELL",
                "DONATE",
                "EXCHANGE"
        });
        typeBox.setFont(UIUtils.normalFont());
        filters.add(typeBox);

        filters.add(new JLabel("Sort:"));

        sortBox = new JComboBox<>(new String[]{
                "Newest",
                "Price: Low to High",
                "Price: High to Low",
                "Name: A to Z"
        });
        sortBox.setFont(UIUtils.normalFont());
        filters.add(sortBox);

        center.add(filters, BorderLayout.NORTH);

        listingsPanel = new ViewportWidthGridPanel(
                new GridLayout(0, 4, 15, 15)
        );
        listingsPanel.setBackground(UIUtils.BACKGROUND);
        listingsPanel.setBorder(new EmptyBorder(15, 25, 25, 25));

        JScrollPane scroll = new JScrollPane(listingsPanel);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
        );
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        center.add(scroll, BorderLayout.CENTER);
        main.add(center, BorderLayout.CENTER);

        setContentPane(main);

        searchButton.addActionListener(e -> loadListings());
        searchField.addActionListener(e -> loadListings());
        categoryBox.addActionListener(e -> loadListings());
        typeBox.addActionListener(e -> loadListings());
        sortBox.addActionListener(e -> loadListings());

        refreshButton.addActionListener(e -> {
            searchField.setText("");
            categoryBox.setSelectedIndex(0);
            typeBox.setSelectedIndex(0);
            sortBox.setSelectedIndex(0);
            loadListings();
        });
    }

    private void loadListings() {

        String search = searchField.getText().trim();
        String category = String.valueOf(
                categoryBox.getSelectedItem()
        );
        String type = String.valueOf(
                typeBox.getSelectedItem()
        );
        String sort = String.valueOf(
                sortBox.getSelectedItem()
        );

        List<UnifiedListing> listings =
                new UnifiedListingDAO().searchListings(
                        currentUser.getId(),
                        search,
                        category,
                        type,
                        sort
                );

        listingsPanel.removeAll();

        resultLabel.setText(
                listings.size()
                        + (listings.size() == 1
                        ? " listing found"
                        : " listings found")
        );

        if (listings.isEmpty()) {
            showNoListings();
        } else {

            listingsPanel.setLayout(new GridLayout(
                    0, 4, 15, 15
            ));

            for (UnifiedListing listing : listings) {
                listingsPanel.add(
                        new UnifiedListingCardPanel(
                                listing,
                                currentUser
                        )
                );
            }
        }

        listingsPanel.revalidate();
        listingsPanel.repaint();
    }

    private void showNoListings() {

        listingsPanel.setLayout(new BorderLayout());

        JPanel empty = new JPanel();
        empty.setBackground(UIUtils.BACKGROUND);
        empty.setLayout(new BoxLayout(
                empty,
                BoxLayout.Y_AXIS
        ));

        JLabel title = new JLabel("No listings found");
        title.setFont(UIUtils.headingFont());
        title.setForeground(UIUtils.TEXT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel message = new JLabel(
                "Try another search, category or listing type."
        );
        message.setFont(UIUtils.normalFont());
        message.setForeground(UIUtils.MUTED);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        empty.add(Box.createVerticalStrut(120));
        empty.add(title);
        empty.add(Box.createVerticalStrut(8));
        empty.add(message);

        listingsPanel.add(empty, BorderLayout.CENTER);
    }

    /** Keeps the marketplace grid exactly as wide as the visible window. */
    private static class ViewportWidthGridPanel extends JPanel implements Scrollable {

        ViewportWidthGridPanel(LayoutManager layout) {
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
            return 16;
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
