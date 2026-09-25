import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class ExchangeFrame extends JFrame {

    private User currentUser;

    private JTextField itemNameField;
    private JTextArea descriptionArea;

    private JTextField wantedItemField;
    private JTextArea requirementsArea;

    private JComboBox<String> categoryCombo;
    private JComboBox<String> conditionCombo;
    private JComboBox<String> wantedCategoryCombo;

    private JLabel imagePreview;

    private String selectedImagePath = "";

    private JPanel listingsPanel;

    private ExchangeDAO exchangeDAO;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public ExchangeFrame(User user) {

        currentUser = user;

        exchangeDAO =
                new ExchangeDAO();

        setTitle(
                "EduMart - Exchange Items"
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

        loadListings();
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
                new JPanel();

        header.setLayout(
                new BoxLayout(
                        header,
                        BoxLayout.Y_AXIS
                )
        );

        header.setBackground(
                UIUtils.PRIMARY
        );

        header.setBorder(
                new EmptyBorder(
                        22,
                        28,
                        22,
                        28
                )
        );


        JLabel title =
                new JLabel(
                        "Exchange Items"
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
                        "Trade something you have "
                        + "for something you need."
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


        header.add(title);

        header.add(
                Box.createVerticalStrut(5)
        );

        header.add(subtitle);


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
                                20,
                                0
                        )
                );

        content.setBackground(
                UIUtils.BACKGROUND
        );

        content.setBorder(
                new EmptyBorder(
                        22,
                        28,
                        22,
                        28
                )
        );


        content.add(
                createFormCard()
        );

        content.add(
                createListingsCard()
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
    // FORM CARD
    // ==========================================

    private JPanel createFormCard() {

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
                                18,
                                22,
                                18,
                                22
                        )
                )
        );


        JLabel title =
                new JLabel(
                        "Create Exchange Listing"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        title.setForeground(
                UIUtils.TEXT
        );


        card.add(
                title,
                BorderLayout.NORTH
        );


        JPanel form =
                new JPanel();

        form.setBackground(
                UIUtils.WHITE
        );

        form.setLayout(
                new BoxLayout(
                        form,
                        BoxLayout.Y_AXIS
                )
        );


        // IMAGE

        form.add(
                fieldLabel(
                        "Item Photo *"
                )
        );


        JPanel imagePanel =
                new JPanel(
                        new BorderLayout()
                );

        imagePanel.setBackground(
                UIUtils.WHITE
        );

        imagePanel.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        imagePanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        105
                )
        );


        imagePreview =
                new JLabel(
                        "No Image",
                        SwingConstants.CENTER
                );

        imagePreview.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        imagePreview.setForeground(
                UIUtils.MUTED
        );

        imagePreview.setBackground(
                new Color(
                        243,
                        246,
                        250
                )
        );

        imagePreview.setOpaque(true);

        imagePreview.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );

        imagePreview.setPreferredSize(
                new Dimension(
                        130,
                        100
                )
        );


        JButton chooseImage =
                UIUtils.createButton(
                        "Choose Image"
                );

        chooseImage.setPreferredSize(
                new Dimension(
                        125,
                        40
                )
        );


        chooseImage.addActionListener(
                e -> chooseImage()
        );


        imagePanel.add(
                imagePreview,
                BorderLayout.WEST
        );

        imagePanel.add(
                chooseImage,
                BorderLayout.EAST
        );


        form.add(imagePanel);


        form.add(
                Box.createVerticalStrut(10)
        );


        // ITEM NAME

        form.add(
                fieldLabel(
                        "Item Name *"
                )
        );

        itemNameField =
                UIUtils.createTextField();

        setHeight(
                itemNameField,
                38
        );

        form.add(
                itemNameField
        );


        form.add(
                Box.createVerticalStrut(9)
        );


        // CATEGORY + CONDITION

        JPanel categoryRow =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                10,
                                0
                        )
                );

        categoryRow.setBackground(
                UIUtils.WHITE
        );

        categoryRow.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        65
                )
        );


        JPanel categoryPanel =
                new JPanel();

        categoryPanel.setBackground(
                UIUtils.WHITE
        );

        categoryPanel.setLayout(
                new BorderLayout(
                        0,
                        5
                )
        );


        categoryPanel.add(
                fieldLabel(
                        "Category *"
                ),
                BorderLayout.NORTH
        );


        categoryCombo =
                new JComboBox<>(
                        new String[]{
                                "Books",
                                "Electronics",
                                "Stationery",
                                "Engineering Instruments",
                                "Bags",
                                "Lab Equipment",
                                "Hostel Essentials",
                                "Notes",
                                "Others"
                        }
                );

        categoryCombo.setFont(
                UIUtils.normalFont()
        );


        categoryPanel.add(
                categoryCombo,
                BorderLayout.CENTER
        );


        JPanel conditionPanel =
                new JPanel();

        conditionPanel.setBackground(
                UIUtils.WHITE
        );

        conditionPanel.setLayout(
                new BorderLayout(
                        0,
                        5
                )
        );


        conditionPanel.add(
                fieldLabel(
                        "Condition *"
                ),
                BorderLayout.NORTH
        );


        conditionCombo =
                new JComboBox<>(
                        new String[]{
                                "New",
                                "Like New",
                                "Good",
                                "Fair",
                                "Used"
                        }
                );

        conditionCombo.setFont(
                UIUtils.normalFont()
        );


        conditionPanel.add(
                conditionCombo,
                BorderLayout.CENTER
        );


        categoryRow.add(
                categoryPanel
        );

        categoryRow.add(
                conditionPanel
        );


        form.add(
                categoryRow
        );


        form.add(
                Box.createVerticalStrut(9)
        );


        // DESCRIPTION

        form.add(
                fieldLabel(
                        "Description"
                )
        );


        descriptionArea =
                createArea(
                        "Describe your item..."
                );


        JScrollPane descriptionScroll =
                createScrollPane(
                        descriptionArea,
                        62
                );


        form.add(
                descriptionScroll
        );


        form.add(
                Box.createVerticalStrut(12)
        );


        // ======================================
        // WHAT YOU WANT
        // ======================================

        JLabel exchangeTitle =
                new JLabel(
                        "What Do You Want in Exchange?"
                );

        exchangeTitle.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        15
                )
        );

        exchangeTitle.setForeground(
                UIUtils.PRIMARY
        );


        form.add(
                exchangeTitle
        );


        form.add(
                Box.createVerticalStrut(7)
        );


        // WANTED ITEM

        form.add(
                fieldLabel(
                        "Desired Item *"
                )
        );


        wantedItemField =
                UIUtils.createTextField();

        setHeight(
                wantedItemField,
                38
        );


        form.add(
                wantedItemField
        );


        form.add(
                Box.createVerticalStrut(8)
        );


        // WANTED CATEGORY

        form.add(
                fieldLabel(
                        "Preferred Category"
                )
        );


        wantedCategoryCombo =
                new JComboBox<>(
                        new String[]{
                                "Books",
                                "Electronics",
                                "Stationery",
                                "Engineering Instruments",
                                "Bags",
                                "Lab Equipment",
                                "Hostel Essentials",
                                "Notes",
                                "Others",
                                "Any Category"
                        }
                );

        wantedCategoryCombo.setFont(
                UIUtils.normalFont()
        );

        wantedCategoryCombo.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        38
                )
        );


        form.add(
                wantedCategoryCombo
        );


        form.add(
                Box.createVerticalStrut(8)
        );


        // REQUIREMENTS

        form.add(
                fieldLabel(
                        "Additional Requirements"
                )
        );


        requirementsArea =
                createArea(
                        "Example: Recent edition, good condition..."
                );


        JScrollPane requirementsScroll =
                createScrollPane(
                        requirementsArea,
                        55
                );


        form.add(
                requirementsScroll
        );


        form.add(
                Box.createVerticalStrut(12)
        );


        JButton createButton =
                UIUtils.createButton(
                        "Create Exchange Listing"
                );

        createButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        42
                )
        );


        createButton.addActionListener(
                e -> createListing()
        );


        form.add(
                createButton
        );


        JScrollPane formScroll =
                new JScrollPane(
                        form
                );

        formScroll.setBorder(null);

        formScroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        formScroll.getVerticalScrollBar()
                .setUnitIncrement(12);


        card.add(
                formScroll,
                BorderLayout.CENTER
        );


        return card;
    }


    // ==========================================
    // LISTINGS CARD
    // ==========================================

    private JPanel createListingsCard() {

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
                                18,
                                18,
                                18,
                                18
                        )
                )
        );


        JLabel title =
                new JLabel(
                        "Available Exchanges"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        21
                )
        );

        title.setForeground(
                UIUtils.TEXT
        );


        card.add(
                title,
                BorderLayout.NORTH
        );


        listingsPanel =
                new JPanel();

        listingsPanel.setBackground(
                UIUtils.WHITE
        );

        listingsPanel.setLayout(
                new BoxLayout(
                        listingsPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JScrollPane scroll =
                new JScrollPane(
                        listingsPanel
                );

        scroll.setBorder(null);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        scroll.getVerticalScrollBar()
                .setUnitIncrement(15);


        card.add(
                scroll,
                BorderLayout.CENTER
        );


        return card;
    }


    // ==========================================
    // CREATE LISTING
    // ==========================================

    private void createListing() {

        String itemName =
                itemNameField
                        .getText()
                        .trim();


        String description =
                descriptionArea
                        .getText()
                        .trim();


        String wantedItem =
                wantedItemField
                        .getText()
                        .trim();


        String requirements =
                requirementsArea
                        .getText()
                        .trim();


        String category =
                categoryCombo
                        .getSelectedItem()
                        .toString();


        String condition =
                conditionCombo
                        .getSelectedItem()
                        .toString();


        String wantedCategory =
                wantedCategoryCombo
                        .getSelectedItem()
                        .toString();


        // ======================================
        // VALIDATION
        // ======================================

        if (itemName.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the item you are offering.",
                    "Missing Item",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (wantedItem.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the item you want in exchange.",
                    "Missing Desired Item",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        if (selectedImagePath.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select an image of the item.",
                    "Image Required",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        // ======================================
        // COPY IMAGE
        // ======================================

        String savedImage =
                saveImage(
                        selectedImagePath
                );


        if (savedImage == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to save the selected image.",
                    "Image Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        // ======================================
        // DATABASE
        // ======================================

        boolean success =
                exchangeDAO.addExchange(
                        currentUser.getId(),
                        itemName,
                        description,
                        category,
                        savedImage,
                        condition,
                        wantedItem,
                        wantedCategory,
                        "",
                        requirements
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your exchange listing has been created!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );


            clearForm();

            loadListings();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to create exchange listing.",
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // CHOOSE IMAGE
    // ==========================================

    private void chooseImage() {

        JFileChooser chooser =
                new JFileChooser();

        chooser.setDialogTitle(
                "Select Item Image"
        );


        int result =
                chooser.showOpenDialog(this);


        if (
                result ==
                JFileChooser.APPROVE_OPTION
        ) {

            File file =
                    chooser.getSelectedFile();


            String name =
                    file.getName().toLowerCase();


            if (
                    !name.endsWith(".jpg")
                    &&
                    !name.endsWith(".jpeg")
                    &&
                    !name.endsWith(".png")
                    &&
                    !name.endsWith(".webp")
            ) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select JPG, JPEG, PNG or WEBP image.",
                        "Invalid Image",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }


            selectedImagePath =
                    file.getAbsolutePath();


            ImageIcon icon =
                    new ImageIcon(
                            selectedImagePath
                    );


            Image scaled =
                    icon.getImage()
                            .getScaledInstance(
                                    130,
                                    100,
                                    Image.SCALE_SMOOTH
                            );


            imagePreview.setText("");

            imagePreview.setIcon(
                    new ImageIcon(scaled)
            );
        }
    }


    // ==========================================
    // SAVE IMAGE
    // ==========================================

    private String saveImage(
            String originalPath) {

        try {

            Path folder =
                    Path.of(
                            "uploads",
                            "exchanges"
                    );


            Files.createDirectories(
                    folder
            );


            File original =
                    new File(
                            originalPath
                    );


            String extension =
                    getExtension(
                            original.getName()
                    );


            String fileName =
                    "exchange_"
                    + System.currentTimeMillis()
                    + extension;


            Path destination =
                    folder.resolve(
                            fileName
                    );


            Files.copy(
                    original.toPath(),
                    destination,
                    StandardCopyOption.REPLACE_EXISTING
            );


            return destination
                    .toString()
                    .replace(
                            "\\",
                            "/"
                    );

        } catch (IOException e) {

            e.printStackTrace();

            return null;
        }
    }


    // ==========================================
    // GET EXTENSION
    // ==========================================

    private String getExtension(
            String fileName) {

        int index =
                fileName.lastIndexOf('.');


        if (index == -1) {
            return ".jpg";
        }


        return fileName.substring(
                index
        );
    }


    // ==========================================
    // LOAD LISTINGS
    // ==========================================

    private void loadListings() {

        listingsPanel.removeAll();


        List<ExchangeDAO.ExchangeData> list =
                exchangeDAO.getAvailableExchanges(
                        currentUser.getId()
                );


        if (
                list == null
                ||
                list.isEmpty()
        ) {

            JLabel empty =
                    new JLabel(
                            "No exchange listings available yet."
                    );

            empty.setFont(
                    UIUtils.normalFont()
            );

            empty.setForeground(
                    UIUtils.MUTED
            );

            empty.setBorder(
                    new EmptyBorder(
                            30,
                            10,
                            10,
                            10
                    )
            );


            listingsPanel.add(empty);

        } else {

            for (
                    ExchangeDAO.ExchangeData exchange
                    : list
            ) {

                listingsPanel.add(
                        createExchangeCard(
                                exchange
                        )
                );


                listingsPanel.add(
                        Box.createVerticalStrut(12)
                );
            }
        }


        listingsPanel.revalidate();

        listingsPanel.repaint();
    }


    // ==========================================
    // EXCHANGE CARD
    // ==========================================

    private JPanel createExchangeCard(
            ExchangeDAO.ExchangeData exchange) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                12,
                                0
                        )
                );

        card.setBackground(
                new Color(
                        249,
                        250,
                        251
                )
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(
                                12,
                                12,
                                12,
                                12
                        )
                )
        );

        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        235
                )
        );


        // ======================================
        // IMAGE
        // ======================================

        JLabel image =
                createProductImage(
                        exchange.imagePath
                );


        card.add(
                image,
                BorderLayout.WEST
        );


        // ======================================
        // DETAILS
        // ======================================

        JPanel details =
                new JPanel();

        details.setBackground(
                new Color(
                        249,
                        250,
                        251
                )
        );

        details.setLayout(
                new BoxLayout(
                        details,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel name =
                new JLabel(
                        exchange.offeredItem
                );

        name.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        17
                )
        );

        name.setForeground(
                UIUtils.TEXT
        );


        details.add(name);


        details.add(
                Box.createVerticalStrut(4)
        );


        JLabel category =
                new JLabel(
                        exchange.category
                        + "  •  "
                        + safe(
                                exchange.conditionType,
                                "Used"
                        )
                );

        category.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        category.setForeground(
                UIUtils.MUTED
        );


        details.add(category);


        details.add(
                Box.createVerticalStrut(10)
        );


        JLabel offer =
                new JLabel(
                        "<html><b>Offering:</b> "
                        + safe(
                                exchange.offeredDescription,
                                "No description"
                        )
                        + "</html>"
                );

        offer.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        details.add(offer);


        details.add(
                Box.createVerticalStrut(7)
        );


        JLabel wanted =
                new JLabel(
                        "<html><b>Wants:</b> "
                        + safe(
                                exchange.wantedItem,
                                ""
                        )
                        + "</html>"
                );

        wanted.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        details.add(wanted);


        details.add(
                Box.createVerticalStrut(5)
        );


        JLabel wantedCategory =
                new JLabel(
                        "Preferred category: "
                        + safe(
                                exchange.wantedCategory,
                                "Any"
                        )
                );

        wantedCategory.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        11
                )
        );

        wantedCategory.setForeground(
                UIUtils.MUTED
        );


        details.add(
                wantedCategory
        );


        details.add(
                Box.createVerticalStrut(7)
        );


        JLabel owner =
                new JLabel(
                        "Listed by: "
                        + exchange.ownerName
                );

        owner.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        11
                )
        );

        owner.setForeground(
                UIUtils.MUTED
        );


        details.add(owner);


        details.add(
                Box.createVerticalStrut(9)
        );


        JButton requestButton =
                UIUtils.createButton(
                        "Request Exchange"
                );

        requestButton.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        36
                )
        );


        requestButton.addActionListener(
                e -> sendExchangeRequest(
                        exchange
                )
        );


        details.add(
                requestButton
        );


        card.add(
                details,
                BorderLayout.CENTER
        );


        return card;
    }


    // ==========================================
    // IMAGE LABEL
    // ==========================================

    private JLabel createProductImage(
            String path) {

        JLabel label =
                new JLabel(
                        "No Image",
                        SwingConstants.CENTER
                );

        label.setPreferredSize(
                new Dimension(
                        125,
                        150
                )
        );

        label.setBackground(
                new Color(
                        239,
                        246,
                        255
                )
        );

        label.setOpaque(true);

        label.setForeground(
                UIUtils.MUTED
        );


        if (
                path != null
                &&
                !path.trim().isEmpty()
        ) {

            File file =
                    new File(path);


            if (file.exists()) {

                ImageIcon icon =
                        new ImageIcon(
                                path
                        );


                Image image =
                        icon.getImage()
                                .getScaledInstance(
                                        125,
                                        150,
                                        Image.SCALE_SMOOTH
                                );


                label.setText("");

                label.setIcon(
                        new ImageIcon(image)
                );
            }
        }


        return label;
    }


    // ==========================================
    // REQUEST EXCHANGE
    // ==========================================

    private void sendExchangeRequest(
            ExchangeDAO.ExchangeData exchange) {

        JTextArea messageArea =
                new JTextArea();

        messageArea.setRows(5);

        messageArea.setLineWrap(true);

        messageArea.setWrapStyleWord(true);

        messageArea.setFont(
                UIUtils.normalFont()
        );


        JScrollPane scroll =
                new JScrollPane(
                        messageArea
                );


        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                0,
                                8
                        )
                );

        panel.setPreferredSize(
                new Dimension(
                        400,
                        150
                )
        );


        panel.add(
                new JLabel(
                        "Write a message to "
                        + exchange.ownerName
                        + ":"
                ),
                BorderLayout.NORTH
        );


        panel.add(
                scroll,
                BorderLayout.CENTER
        );


        int result =
                JOptionPane.showConfirmDialog(
                        this,
                        panel,
                        "Request Exchange",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );


        if (
                result !=
                JOptionPane.OK_OPTION
        ) {
            return;
        }


        String message =
                messageArea
                        .getText()
                        .trim();


        if (message.isEmpty()) {

            message =
                    "I am interested in exchanging "
                    + "with your item.";
        }


        ExchangeRequestDAO requestDAO =
                new ExchangeRequestDAO();


        boolean success =
                requestDAO.sendRequest(
                        exchange.id,
                        currentUser.getId(),
                        message
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Exchange request sent successfully!",
                    "Request Sent",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to send exchange request.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // CLEAR FORM
    // ==========================================

    private void clearForm() {

        itemNameField.setText("");

        descriptionArea.setText("");

        wantedItemField.setText("");

        requirementsArea.setText("");

        categoryCombo.setSelectedIndex(0);

        conditionCombo.setSelectedIndex(0);

        wantedCategoryCombo.setSelectedIndex(0);

        selectedImagePath = "";

        imagePreview.setIcon(null);

        imagePreview.setText("No Image");
    }


    // ==========================================
    // HELPERS
    // ==========================================

    private JLabel fieldLabel(
            String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        label.setForeground(
                UIUtils.TEXT
        );

        label.setBorder(
                new EmptyBorder(
                        0,
                        0,
                        5,
                        0
                )
        );


        return label;
    }


    private JTextArea createArea(
            String tooltip) {

        JTextArea area =
                new JTextArea();

        area.setFont(
                UIUtils.normalFont()
        );

        area.setLineWrap(true);

        area.setWrapStyleWord(true);

        area.setToolTipText(
                tooltip
        );

        area.setBorder(
                new EmptyBorder(
                        7,
                        8,
                        7,
                        8
                )
        );


        return area;
    }


    private JScrollPane createScrollPane(
            JTextArea area,
            int height) {

        JScrollPane scroll =
                new JScrollPane(
                        area
                );

        scroll.setBorder(
                BorderFactory.createLineBorder(
                        UIUtils.BORDER
                )
        );

        scroll.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        height
                )
        );

        scroll.setPreferredSize(
                new Dimension(
                        400,
                        height
                )
        );


        return scroll;
    }


    private void setHeight(
            JComponent component,
            int height) {

        component.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        height
                )
        );
    }


    private String safe(
            String value,
            String fallback) {

        if (
                value == null
                ||
                value.trim().isEmpty()
        ) {

            return fallback;
        }

        return value;
    }
}