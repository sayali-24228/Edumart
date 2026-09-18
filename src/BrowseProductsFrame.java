import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BrowseProductsFrame extends JFrame {

    private User currentUser;

    private JTextField searchField;

    private JComboBox<String> categoryBox;

    private JComboBox<String> sortBox;

    private JPanel productsPanel;

    private JLabel resultLabel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public BrowseProductsFrame(User user) {

        this.currentUser = user;

        setTitle(
                "EduMart - Browse Products"
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
    // COMPATIBILITY CONSTRUCTOR
    // ==========================================

    public BrowseProductsFrame(
            User user,
            String searchText) {

        this.currentUser = user;

        setTitle(
                "EduMart - Browse Products"
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


        if (searchText != null) {

            searchField.setText(
                    searchText
            );
        }


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
                        "Browse Products"
                );


        title.setFont(
                UIUtils.titleFont()
        );


        title.setForeground(
                UIUtils.TEXT
        );


        resultLabel =
                new JLabel(
                        "Loading products..."
                );


        resultLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        resultLabel.setForeground(
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
                resultLabel
        );


        header.add(
                titlePanel,
                BorderLayout.WEST
        );


        // ======================================
        // SEARCH AREA
        // ======================================

        JPanel searchPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                8,
                                0
                        )
                );


        searchPanel.setBackground(
                UIUtils.WHITE
        );


        searchField =
                UIUtils.createTextField();


        searchField.setPreferredSize(
                new Dimension(
                        230,
                        40
                )
        );


        searchField.setToolTipText(
                "Search products..."
        );


        JButton searchButton =
                UIUtils.createButton(
                        "Search"
                );


        searchButton.setPreferredSize(
                new Dimension(
                        90,
                        40
                )
        );


        JButton refreshButton =
                new JButton(
                        "Refresh"
                );


        refreshButton.setFont(
                UIUtils.buttonFont()
        );


        refreshButton.setPreferredSize(
                new Dimension(
                        85,
                        40
                )
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


        searchButton.addActionListener(
                e -> loadProducts()
        );


        searchField.addActionListener(
                e -> loadProducts()
        );


        refreshButton.addActionListener(
                e -> {

                    searchField.setText("");

                    categoryBox.setSelectedIndex(0);

                    sortBox.setSelectedIndex(0);

                    loadProducts();
                }
        );


        searchPanel.add(
                searchField
        );


        searchPanel.add(
                searchButton
        );


        searchPanel.add(
                refreshButton
        );


        header.add(
                searchPanel,
                BorderLayout.EAST
        );


        mainPanel.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // CENTER PANEL
        // ======================================

        JPanel centerPanel =
                new JPanel(
                        new BorderLayout()
                );


        centerPanel.setBackground(
                UIUtils.BACKGROUND
        );


        // ======================================
        // FILTER BAR
        // ======================================

        JPanel filterBar =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                12,
                                10
                        )
                );


        filterBar.setBackground(
                UIUtils.BACKGROUND
        );


        filterBar.setBorder(
                new EmptyBorder(
                        5,
                        25,
                        5,
                        25
                )
        );


        JLabel categoryLabel =
                new JLabel(
                        "Category:"
                );


        categoryLabel.setFont(
                UIUtils.normalFont()
        );


        categoryLabel.setForeground(
                UIUtils.TEXT
        );


        categoryBox =
                new JComboBox<>(
                        new String[]{
                                "All Categories",
                                "Books",
                                "Electronics",
                                "Stationery",
                                "Bags",
                                "Clothing",
                                "Hostel Essentials",
                                "Sports",
                                "Engineering Instruments",
                                "Notes",
                                "Other"
                        }
                );


        categoryBox.setFont(
                UIUtils.normalFont()
        );


        categoryBox.setPreferredSize(
                new Dimension(
                        190,
                        36
                )
        );


        JLabel sortLabel =
                new JLabel(
                        "Sort by:"
                );


        sortLabel.setFont(
                UIUtils.normalFont()
        );


        sortLabel.setForeground(
                UIUtils.TEXT
        );


        sortBox =
                new JComboBox<>(
                        new String[]{
                                "Newest",
                                "Price: Low to High",
                                "Price: High to Low",
                                "Name: A to Z"
                        }
                );


        sortBox.setFont(
                UIUtils.normalFont()
        );


        sortBox.setPreferredSize(
                new Dimension(
                        190,
                        36
                )
        );


        categoryBox.addActionListener(
                e -> loadProducts()
        );


        sortBox.addActionListener(
                e -> loadProducts()
        );


        filterBar.add(
                categoryLabel
        );


        filterBar.add(
                categoryBox
        );


        filterBar.add(
                sortLabel
        );


        filterBar.add(
                sortBox
        );


        centerPanel.add(
                filterBar,
                BorderLayout.NORTH
        );


        // ======================================
        // PRODUCTS PANEL
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
                        15,
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


        centerPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        mainPanel.add(
                centerPanel,
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

        if (
                searchField == null ||
                categoryBox == null ||
                sortBox == null
        ) {

            return;
        }


        String searchText =
                searchField
                        .getText()
                        .trim();


        String category =
                (String)
                categoryBox
                        .getSelectedItem();


        String sortOption =
                (String)
                sortBox
                        .getSelectedItem();


        ProductDAO dao =
                new ProductDAO();


        List<Product> products =
                dao.searchProducts(
                        currentUser.getId(),
                        searchText,
                        category,
                        sortOption
                );


        productsPanel.removeAll();


        resultLabel.setText(
                products.size()
                +
                (
                        products.size() == 1
                                ? " product found"
                                : " products found"
                )
        );


        if (products.isEmpty()) {

            showNoProducts();

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

                ProductCardPanel card =
                        new ProductCardPanel(
                                product,
                                currentUser
                        );


                productsPanel.add(
                        card
                );
            }
        }


        productsPanel.revalidate();

        productsPanel.repaint();
    }


    // ==========================================
    // NO PRODUCTS
    // ==========================================

    private void showNoProducts() {

        productsPanel.setLayout(
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


        JLabel title =
                new JLabel(
                        "No products found"
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
                        "Try a different search or category."
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
                        120
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


        productsPanel.add(
                emptyPanel,
                BorderLayout.CENTER
        );
    }
}