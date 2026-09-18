import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardFrame extends JFrame {

    private User currentUser;

    private JLabel productsValue;
    private JLabel ordersValue;
    private JLabel favoritesValue;
    private JLabel cartValue;
    private JLabel donationsValue;
    private JLabel exchangeValue;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public DashboardFrame(User user) {

        this.currentUser = user;

        setTitle(
                "EduMart - Dashboard"
        );

        setSize(
                1050,
                700
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);

        createUI();

        loadStatistics();
    }


    // ==========================================
    // CREATE UI
    // ==========================================

    private void createUI() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(
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
                UIUtils.PRIMARY
        );

        header.setBorder(
                new EmptyBorder(
                        25,
                        30,
                        25,
                        30
                )
        );


        JPanel headerText =
                new JPanel();

        headerText.setBackground(
                UIUtils.PRIMARY
        );

        headerText.setLayout(
                new BoxLayout(
                        headerText,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel title =
                new JLabel(
                        "Dashboard"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        28
                )
        );

        title.setForeground(
                Color.WHITE
        );


        JLabel subtitle =
                new JLabel(
                        "Welcome, "
                        + currentUser.getName()
                        + "!"
                );

        subtitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        subtitle.setForeground(
                new Color(
                        219,
                        234,
                        254
                )
        );


        headerText.add(
                title
        );

        headerText.add(
                Box.createVerticalStrut(
                        5
                )
        );

        headerText.add(
                subtitle
        );


        JButton refresh =
                UIUtils.createButton(
                        "Refresh"
                );

        refresh.setPreferredSize(
                new Dimension(
                        100,
                        40
                )
        );

        refresh.addActionListener(
                e -> loadStatistics()
        );


        header.add(
                headerText,
                BorderLayout.WEST
        );

        header.add(
                refresh,
                BorderLayout.EAST
        );


        main.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // CONTENT
        // ======================================

        JPanel content =
                new JPanel();

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

        content.setLayout(
                new BorderLayout(
                        0,
                        25
                )
        );


        JLabel overview =
                new JLabel(
                        "Your Activity Overview"
                );

        overview.setFont(
                UIUtils.headingFont()
        );

        overview.setForeground(
                UIUtils.TEXT
        );


        content.add(
                overview,
                BorderLayout.NORTH
        );


        // ======================================
        // STAT GRID
        // ======================================

        JPanel stats =
                new JPanel(
                        new GridLayout(
                                2,
                                3,
                                18,
                                18
                        )
                );

        stats.setBackground(
                UIUtils.BACKGROUND
        );


        JPanel productsCard =
                createStatCard(
                        "Products Listed",
                        "0"
                );

        productsValue =
                getValueLabel(
                        productsCard
                );


        JPanel ordersCard =
                createStatCard(
                        "Orders Received",
                        "0"
                );

        ordersValue =
                getValueLabel(
                        ordersCard
                );


        JPanel favoritesCard =
                createStatCard(
                        "Favorites",
                        "0"
                );

        favoritesValue =
                getValueLabel(
                        favoritesCard
                );


        JPanel cartCard =
                createStatCard(
                        "Cart Items",
                        "0"
                );

        cartValue =
                getValueLabel(
                        cartCard
                );


        JPanel donationsCard =
                createStatCard(
                        "My Donations",
                        "0"
                );

        donationsValue =
                getValueLabel(
                        donationsCard
                );


        JPanel exchangeCard =
                createStatCard(
                        "Exchange Listings",
                        "0"
                );

        exchangeValue =
                getValueLabel(
                        exchangeCard
                );


        stats.add(
                productsCard
        );

        stats.add(
                ordersCard
        );

        stats.add(
                favoritesCard
        );

        stats.add(
                cartCard
        );

        stats.add(
                donationsCard
        );

        stats.add(
                exchangeCard
        );


        content.add(
                stats,
                BorderLayout.CENTER
        );


        main.add(
                content,
                BorderLayout.CENTER
        );


        setContentPane(
                main
        );
    }


    // ==========================================
    // STAT CARD
    // ==========================================

    private JPanel createStatCard(
            String title,
            String value) {

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
                                25,
                                20,
                                25,
                                20
                        )
                )
        );

        card.setLayout(
                new BoxLayout(
                        card,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel valueLabel =
                new JLabel(
                        value
                );

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        32
                )
        );

        valueLabel.setForeground(
                UIUtils.PRIMARY
        );

        valueLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                UIUtils.normalFont()
        );

        titleLabel.setForeground(
                UIUtils.MUTED
        );

        titleLabel.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        card.add(
                valueLabel
        );

        card.add(
                Box.createVerticalStrut(
                        8
                )
        );

        card.add(
                titleLabel
        );


        card.putClientProperty(
                "valueLabel",
                valueLabel
        );


        return card;
    }


    // ==========================================
    // GET VALUE LABEL
    // ==========================================

    private JLabel getValueLabel(
            JPanel panel) {

        return (JLabel)
                panel.getClientProperty(
                        "valueLabel"
                );
    }


    // ==========================================
    // LOAD STATISTICS
    // ==========================================

    private void loadStatistics() {

        DashboardDAO dao =
                new DashboardDAO();


        productsValue.setText(
                String.valueOf(
                        dao.getProductsListed(
                                currentUser.getId()
                        )
                )
        );


        ordersValue.setText(
                String.valueOf(
                        dao.getOrdersReceived(
                                currentUser.getId()
                        )
                )
        );


        favoritesValue.setText(
                String.valueOf(
                        dao.getFavorites(
                                currentUser.getId()
                        )
                )
        );


        cartValue.setText(
                String.valueOf(
                        dao.getCartItems(
                                currentUser.getId()
                        )
                )
        );


        donationsValue.setText(
                String.valueOf(
                        dao.getDonations(
                                currentUser.getId()
                        )
                )
        );


        exchangeValue.setText(
                String.valueOf(
                        dao.getExchangeListings(
                                currentUser.getId()
                        )
                )
        );
    }
}