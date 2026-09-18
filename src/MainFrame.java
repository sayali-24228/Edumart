import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private User currentUser;

    private JPanel contentPanel;
    private JTextField searchField;
    private JLabel welcomeLabel;
    private JPanel featuredPanel;

    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public MainFrame(User user) {

        currentUser = user;

        setTitle("EduMart - Student Marketplace");

        setSize(1200, 750);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        createUI();

        showHome();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void createUI() {

        JPanel mainPanel =
                new JPanel(new BorderLayout());

        mainPanel.setBackground(
                UIUtils.BACKGROUND
        );


        // ======================================
        // SIDEBAR
        // ======================================

        JPanel sidebar =
                new JPanel(new BorderLayout());

        sidebar.setPreferredSize(
                new Dimension(220, 750)
        );

        sidebar.setBackground(
                UIUtils.SIDEBAR
        );


        // ======================================
        // LOGO
        // ======================================

        JPanel logoPanel =
                new JPanel();

        logoPanel.setBackground(
                UIUtils.SIDEBAR
        );

        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );

        logoPanel.setBorder(
                new EmptyBorder(
                        25,
                        20,
                        20,
                        20
                )
        );


        JLabel logo =
                new JLabel("EduMart");

        logo.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        logo.setForeground(Color.WHITE);


        JLabel subtitle =
                new JLabel(
                        "Student Marketplace"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        subtitle.setForeground(
                new Color(
                        156,
                        163,
                        175
                )
        );


        logoPanel.add(logo);

        logoPanel.add(
                Box.createVerticalStrut(4)
        );

        logoPanel.add(subtitle);


        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // ======================================
        // MENU
        // ======================================

        JPanel menuPanel =
                new JPanel();

        menuPanel.setBackground(
                UIUtils.SIDEBAR
        );

        menuPanel.setLayout(
                new BoxLayout(
                        menuPanel,
                        BoxLayout.Y_AXIS
                )
        );


        // ======================================
        // SIDEBAR BUTTONS
        // ======================================

        JButton homeButton =
                UIUtils.createSidebarButton(
                        "  Home"
                );


        JButton browseButton =
                UIUtils.createSidebarButton(
                        "  Browse Products"
                );


        JButton sellButton =
                UIUtils.createSidebarButton(
                        "  Sell an Item"
                );


        JButton cartButton =
                UIUtils.createSidebarButton(
                        "  My Cart"
                );


        JButton ordersButton =
                UIUtils.createSidebarButton(
                        "  My Orders"
                );


        JButton productsButton =
                UIUtils.createSidebarButton(
                        "  My Products"
                );


        JButton favoritesButton =
                UIUtils.createSidebarButton(
                        "  Favorites"
                );


        JButton sellerOrdersButton =
                UIUtils.createSidebarButton(
                        "  Seller Orders"
                );


        JButton profileButton =
                UIUtils.createSidebarButton(
                        "  Seller Profile"
                );


        JButton dashboardButton =
                UIUtils.createSidebarButton(
                        "  Dashboard"
                );


        JButton donateButton =
                UIUtils.createSidebarButton(
                        "  Donate"
                );


        JButton exchangeButton =
                UIUtils.createSidebarButton(
                        "  Exchange"
                );


        JButton exchangeRequestsButton =
                UIUtils.createSidebarButton(
                        "  Exchange Requests"
                );


        // ======================================
        // ADD BUTTONS TO MENU
        // ======================================

        menuPanel.add(homeButton);

        menuPanel.add(browseButton);

        menuPanel.add(sellButton);

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(cartButton);

        menuPanel.add(ordersButton);

        menuPanel.add(productsButton);

        menuPanel.add(favoritesButton);

        menuPanel.add(sellerOrdersButton);

        menuPanel.add(profileButton);

        menuPanel.add(
                Box.createVerticalStrut(8)
        );

        menuPanel.add(dashboardButton);

        menuPanel.add(donateButton);

        menuPanel.add(exchangeButton);

        menuPanel.add(exchangeRequestsButton);


        // ======================================
        // SCROLLABLE SIDEBAR MENU
        // ======================================

        JScrollPane menuScroll =
                new JScrollPane(
                        menuPanel
                );

        menuScroll.setBorder(null);

        menuScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        menuScroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );

        menuScroll.getVerticalScrollBar()
                .setUnitIncrement(12);

        menuScroll.setBackground(
                UIUtils.SIDEBAR
        );


        sidebar.add(
                menuScroll,
                BorderLayout.CENTER
        );


        // ======================================
        // USER SECTION
        // ======================================

        JPanel bottomPanel =
                new JPanel();

        bottomPanel.setBackground(
                UIUtils.SIDEBAR
        );

        bottomPanel.setLayout(
                new BoxLayout(
                        bottomPanel,
                        BoxLayout.Y_AXIS
                )
        );

        bottomPanel.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        20,
                        20
                )
        );


        JLabel loggedUser =
                new JLabel(
                        currentUser.getName()
                );

        loggedUser.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        loggedUser.setForeground(
                Color.WHITE
        );


        JLabel email =
                new JLabel(
                        currentUser.getEmail()
                );

        email.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        email.setForeground(
                new Color(
                        156,
                        163,
                        175
                )
        );


        JButton logoutButton =
                UIUtils.createSidebarButton(
                        "  Logout"
                );


        bottomPanel.add(loggedUser);

        bottomPanel.add(
                Box.createVerticalStrut(3)
        );

        bottomPanel.add(email);

        bottomPanel.add(
                Box.createVerticalStrut(12)
        );

        bottomPanel.add(logoutButton);


        sidebar.add(
                bottomPanel,
                BorderLayout.SOUTH
        );


        // ======================================
        // RIGHT PANEL
        // ======================================

        JPanel rightPanel =
                new JPanel(
                        new BorderLayout()
                );

        rightPanel.setBackground(
                UIUtils.BACKGROUND
        );


        // ======================================
        // TOP BAR
        // ======================================

        JPanel topBar =
                new JPanel(
                        new BorderLayout(
                                15,
                                0
                        )
                );

        topBar.setBackground(
                UIUtils.WHITE
        );

        topBar.setBorder(
                new EmptyBorder(
                        15,
                        25,
                        15,
                        25
                )
        );


        JLabel pageTitle =
                new JLabel(
                        "Marketplace"
                );

        pageTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        22
                )
        );

        pageTitle.setForeground(
                UIUtils.TEXT
        );


        // ======================================
        // SEARCH
        // ======================================

        JPanel searchPanel =
                new JPanel(
                        new BorderLayout(
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
                        300,
                        40
                )
        );


        JButton searchButton =
                UIUtils.createButton(
                        "Search"
                );

        searchButton.setPreferredSize(
                new Dimension(
                        95,
                        40
                )
        );


        searchPanel.add(
                searchField,
                BorderLayout.CENTER
        );

        searchPanel.add(
                searchButton,
                BorderLayout.EAST
        );


        JLabel userLabel =
                new JLabel(
                        "Hi, "
                        + currentUser.getName()
                );

        userLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        14
                )
        );

        userLabel.setForeground(
                UIUtils.TEXT
        );


        topBar.add(
                pageTitle,
                BorderLayout.WEST
        );

        topBar.add(
                searchPanel,
                BorderLayout.CENTER
        );

        topBar.add(
                userLabel,
                BorderLayout.EAST
        );


        rightPanel.add(
                topBar,
                BorderLayout.NORTH
        );


        // ======================================
        // CONTENT
        // ======================================

        contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setBackground(
                UIUtils.BACKGROUND
        );


        rightPanel.add(
                contentPanel,
                BorderLayout.CENTER
        );


        // ======================================
        // MAIN PANEL
        // ======================================

        mainPanel.add(
                sidebar,
                BorderLayout.WEST
        );

        mainPanel.add(
                rightPanel,
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );


        // ======================================
        // BUTTON EVENTS
        // ======================================

        homeButton.addActionListener(
                e -> showHome()
        );


        browseButton.addActionListener(
                e -> openBrowseProducts("")
        );


        sellButton.addActionListener(
                e -> {

                    new SellProductFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        cartButton.addActionListener(
                e -> {

                    new MyCartFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        ordersButton.addActionListener(
                e -> {

                    new MyOrdersFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        productsButton.addActionListener(
                e -> {

                    new MyProductsFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        favoritesButton.addActionListener(
                e -> {

                    new FavoritesFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        sellerOrdersButton.addActionListener(
                e -> {

                    new SellerOrdersFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        // ======================================
        // SELLER PROFILE
        // ======================================

        profileButton.addActionListener(
                e -> {

                    new SellerProfileFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        // ======================================
        // DASHBOARD
        // ======================================

        dashboardButton.addActionListener(
                e -> {

                    new DashboardFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        // ======================================
        // DONATE
        // ======================================

        donateButton.addActionListener(
                e -> {

                    new DonateFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        // ======================================
        // EXCHANGE
        // ======================================

        exchangeButton.addActionListener(
                e -> {

                    new ExchangeFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        // ======================================
        // EXCHANGE REQUESTS
        // ======================================

        exchangeRequestsButton.addActionListener(
                e -> {

                    new ExchangeRequestsFrame(
                            currentUser
                    ).setVisible(true);

                }
        );


        // ======================================
        // SEARCH
        // ======================================

        searchButton.addActionListener(
                e -> openBrowseProducts(
                        searchField.getText().trim()
                )
        );


        searchField.addActionListener(
                e -> openBrowseProducts(
                        searchField.getText().trim()
                )
        );


        // ======================================
        // LOGOUT
        // ======================================

        logoutButton.addActionListener(
                e -> logout()
        );
    }


    // ==========================================
    // HOME
    // ==========================================

    private void showHome() {

        contentPanel.removeAll();


        JPanel homePanel =
                new JPanel();

        homePanel.setBackground(
                UIUtils.BACKGROUND
        );

        homePanel.setBorder(
                new EmptyBorder(
                        25,
                        25,
                        25,
                        25
                )
        );

        homePanel.setLayout(
                new BoxLayout(
                        homePanel,
                        BoxLayout.Y_AXIS
                )
        );


        // ======================================
        // WELCOME CARD
        // ======================================

        JPanel welcomeCard =
                new JPanel(
                        new BorderLayout()
                );

        welcomeCard.setBackground(
                UIUtils.PRIMARY
        );

        welcomeCard.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );

        welcomeCard.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        130
                )
        );


        JPanel welcomeText =
                new JPanel();

        welcomeText.setBackground(
                UIUtils.PRIMARY
        );

        welcomeText.setLayout(
                new BoxLayout(
                        welcomeText,
                        BoxLayout.Y_AXIS
                )
        );


        welcomeLabel =
                new JLabel(
                        "Welcome back, "
                        + currentUser.getName()
                        + "!"
                );

        welcomeLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        welcomeLabel.setForeground(
                Color.WHITE
        );


        JLabel description =
                new JLabel(
                        "Buy, sell and discover useful items "
                        + "from the EduMart community."
                );

        description.setFont(
                UIUtils.normalFont()
        );

        description.setForeground(
                new Color(
                        219,
                        234,
                        254
                )
        );


        welcomeText.add(
                welcomeLabel
        );

        welcomeText.add(
                Box.createVerticalStrut(8)
        );

        welcomeText.add(
                description
        );


        JButton browseNow =
                UIUtils.createButton(
                        "Browse Products"
                );

        browseNow.setPreferredSize(
                new Dimension(
                        160,
                        42
                )
        );


        browseNow.addActionListener(
                e -> openBrowseProducts("")
        );


        welcomeCard.add(
                welcomeText,
                BorderLayout.CENTER
        );

        welcomeCard.add(
                browseNow,
                BorderLayout.EAST
        );


        homePanel.add(
                welcomeCard
        );


        homePanel.add(
                Box.createVerticalStrut(25)
        );


        // ======================================
        // QUICK ACTIONS
        // ======================================

        JLabel quickTitle =
                new JLabel(
                        "Quick Actions"
                );

        quickTitle.setFont(
                UIUtils.headingFont()
        );

        quickTitle.setForeground(
                UIUtils.TEXT
        );


        homePanel.add(
                quickTitle
        );


        homePanel.add(
                Box.createVerticalStrut(12)
        );


        JPanel quickPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                3,
                                15,
                                0
                        )
                );

        quickPanel.setBackground(
                UIUtils.BACKGROUND
        );

        quickPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );


        JPanel browseCard =
                createQuickCard(
                        "Browse Products",
                        "Find books, electronics, bags and more."
                );


        JPanel sellCard =
                createQuickCard(
                        "Sell an Item",
                        "List your unused items for other students."
                );


        JPanel cartCard =
                createQuickCard(
                        "My Cart",
                        "View the items you want to purchase."
                );


        browseCard.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        openBrowseProducts("");

                    }
                }
        );


        sellCard.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        new SellProductFrame(
                                currentUser
                        ).setVisible(true);

                    }
                }
        );


        cartCard.addMouseListener(
                new java.awt.event.MouseAdapter() {

                    @Override
                    public void mouseClicked(
                            java.awt.event.MouseEvent e) {

                        new MyCartFrame(
                                currentUser
                        ).setVisible(true);

                    }
                }
        );


        quickPanel.add(
                browseCard
        );

        quickPanel.add(
                sellCard
        );

        quickPanel.add(
                cartCard
        );


        homePanel.add(
                quickPanel
        );


        homePanel.add(
                Box.createVerticalStrut(25)
        );


        // ======================================
        // LATEST PRODUCTS
        // ======================================

        JLabel latestTitle =
                new JLabel(
                        "Latest Products"
                );

        latestTitle.setFont(
                UIUtils.headingFont()
        );

        latestTitle.setForeground(
                UIUtils.TEXT
        );


        homePanel.add(
                latestTitle
        );


        homePanel.add(
                Box.createVerticalStrut(12)
        );


        featuredPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT,
                                15,
                                5
                        )
                );

        featuredPanel.setBackground(
                UIUtils.BACKGROUND
        );


        loadFeaturedProducts();


        homePanel.add(
                featuredPanel
        );


        // ======================================
        // HOME SCROLL
        // ======================================

        JScrollPane scrollPane =
                new JScrollPane(
                        homePanel
                );

        scrollPane.setBorder(null);

        scrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // ==========================================
    // QUICK CARD
    // ==========================================

    private JPanel createQuickCard(
            String title,
            String description) {

        JPanel card =
                new JPanel();

        card.setBackground(
                UIUtils.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(
                                15,
                                18,
                                15,
                                18
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        16
                )
        );

        titleLabel.setForeground(
                UIUtils.TEXT
        );


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
                        12
                )
        );

        descriptionLabel.setForeground(
                UIUtils.MUTED
        );


        card.add(
                titleLabel
        );

        card.add(
                Box.createVerticalStrut(8)
        );

        card.add(
                descriptionLabel
        );


        return card;
    }


    // ==========================================
    // FEATURED PRODUCTS
    // ==========================================

    private void loadFeaturedProducts() {

        featuredPanel.removeAll();


        ProductDAO dao =
                new ProductDAO();


        List<Product> products =
                dao.getAllProducts();


        int count =
                Math.min(
                        products.size(),
                        3
                );


        if (count == 0) {

            JLabel empty =
                    new JLabel(
                            "No products listed yet. "
                            + "Be the first to sell an item!"
                    );

            empty.setFont(
                    UIUtils.normalFont()
            );

            empty.setForeground(
                    UIUtils.MUTED
            );


            featuredPanel.add(
                    empty
            );

        } else {

            for (
                    int i = 0;
                    i < count;
                    i++
            ) {

                Product product =
                        products.get(i);


                ProductCardPanel card =
                        new ProductCardPanel(
                                product,
                                currentUser
                        );


                featuredPanel.add(
                        card
                );
            }
        }


        featuredPanel.revalidate();

        featuredPanel.repaint();
    }


    // ==========================================
    // BROWSE PRODUCTS
    // ==========================================

    private void openBrowseProducts(
            String searchText) {

        BrowseProductsFrame frame;


        if (
                searchText == null
                ||
                searchText.trim().isEmpty()
        ) {

            frame =
                    new BrowseProductsFrame(
                            currentUser
                    );

        } else {

            frame =
                    new BrowseProductsFrame(
                            currentUser,
                            searchText
                    );
        }


        frame.setVisible(true);
    }


    // ==========================================
    // LOGOUT
    // ==========================================

    private void logout() {

        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to logout?",
                        "Logout",
                        JOptionPane.YES_NO_OPTION
                );


        if (
                result ==
                JOptionPane.YES_OPTION
        ) {

            dispose();

            new LoginFrame()
                    .setVisible(true);
        }
    }
}