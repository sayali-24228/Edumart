import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;

public class SellProductFrame extends JFrame {

    private User currentUser;

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryBox;
    private JTextField priceField;
    private JComboBox<String> conditionBox;
    private JTextField imageField;

    public SellProductFrame(User user) {

        this.currentUser = user;

        setTitle("EduMart - Sell an Item");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        createUI();
    }

    private void createUI() {

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(UIUtils.BACKGROUND);

        // =========================
        // TOP BAR
        // =========================

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UIUtils.WHITE);
        topBar.setBorder(
                new EmptyBorder(18, 30, 18, 30)
        );

        JLabel title = new JLabel("Sell an Item");
        title.setFont(UIUtils.titleFont());
        title.setForeground(UIUtils.TEXT);

        JLabel userLabel = new JLabel(
                "Seller: " + currentUser.getName()
        );

        userLabel.setFont(UIUtils.normalFont());
        userLabel.setForeground(UIUtils.MUTED);

        topBar.add(title, BorderLayout.WEST);
        topBar.add(userLabel, BorderLayout.EAST);

        mainPanel.add(topBar, BorderLayout.NORTH);

        // =========================
        // FORM
        // =========================

        JPanel formWrapper = new JPanel(new BorderLayout());
        formWrapper.setBackground(UIUtils.BACKGROUND);
        formWrapper.setBorder(
                new EmptyBorder(25, 80, 25, 80)
        );

        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIUtils.WHITE);
        formPanel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(30, 35, 30, 35)
                )
        );

        formPanel.setLayout(
                new BoxLayout(formPanel, BoxLayout.Y_AXIS)
        );

        // =========================
        // PRODUCT NAME
        // =========================

        formPanel.add(
                createLabel("Product Name")
        );

        nameField = UIUtils.createTextField();
        nameField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );

        formPanel.add(nameField);

        formPanel.add(Box.createVerticalStrut(15));

        // =========================
        // DESCRIPTION
        // =========================

        formPanel.add(
                createLabel("Description")
        );

        descriptionArea = new JTextArea(4, 20);

        descriptionArea.setFont(
                UIUtils.normalFont()
        );

        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);

        descriptionArea.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        JScrollPane descriptionScroll =
                new JScrollPane(descriptionArea);

        descriptionScroll.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 100)
        );

        formPanel.add(descriptionScroll);

        formPanel.add(Box.createVerticalStrut(15));

        // =========================
        // CATEGORY
        // =========================

        formPanel.add(
                createLabel("Category")
        );

        String[] categories = {
                "Books",
                "Electronics",
                "Stationery",
                "Bags",
                "Hostel Essentials",
                "Engineering Instruments",
                "Notes",
                "Sports",
                "Bicycles",
                "Other"
        };

        categoryBox =
                new JComboBox<>(categories);

        categoryBox.setFont(
                UIUtils.normalFont()
        );

        categoryBox.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        formPanel.add(categoryBox);

        formPanel.add(Box.createVerticalStrut(15));

        // =========================
        // PRICE
        // =========================

        formPanel.add(
                createLabel("Price (₹)")
        );

        priceField =
                UIUtils.createTextField();

        priceField.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );

        formPanel.add(priceField);

        formPanel.add(Box.createVerticalStrut(15));

        // =========================
        // CONDITION
        // =========================

        formPanel.add(
                createLabel("Condition")
        );

        String[] conditions = {
                "New",
                "Like New",
                "Good",
                "Fair",
                "Used"
        };

        conditionBox =
                new JComboBox<>(conditions);

        conditionBox.setFont(
                UIUtils.normalFont()
        );

        conditionBox.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 40)
        );

        formPanel.add(conditionBox);

        formPanel.add(Box.createVerticalStrut(15));

        // =========================
        // IMAGE
        // =========================

        formPanel.add(
                createLabel("Product Image")
        );

        JPanel imagePanel =
                new JPanel(new BorderLayout(10, 0));

        imagePanel.setBackground(
                UIUtils.WHITE
        );

        imageField =
                UIUtils.createTextField();

        imageField.setEditable(false);

        JButton browseButton =
                UIUtils.createButton("Browse");

        browseButton.setPreferredSize(
                new Dimension(100, 40)
        );

        browseButton.addActionListener(
                e -> chooseImage()
        );

        imagePanel.add(
                imageField,
                BorderLayout.CENTER
        );

        imagePanel.add(
                browseButton,
                BorderLayout.EAST
        );

        imagePanel.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 42)
        );

        formPanel.add(imagePanel);

        formPanel.add(Box.createVerticalStrut(25));

        // =========================
        // BUTTONS
        // =========================

        JPanel buttonPanel =
                new JPanel(new FlowLayout(
                        FlowLayout.RIGHT,
                        10,
                        0
                ));

        buttonPanel.setBackground(
                UIUtils.WHITE
        );

        JButton cancelButton =
                new JButton("Cancel");

        cancelButton.setFont(
                UIUtils.buttonFont()
        );

        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(
                e -> dispose()
        );

        JButton publishButton =
                UIUtils.createButton(
                        "Publish Item"
                );

        publishButton.setPreferredSize(
                new Dimension(150, 42)
        );

        publishButton.addActionListener(
                e -> publishProduct()
        );

        buttonPanel.add(cancelButton);
        buttonPanel.add(publishButton);

        formPanel.add(buttonPanel);

        formWrapper.add(
                formPanel,
                BorderLayout.CENTER
        );

        mainPanel.add(
                formWrapper,
                BorderLayout.CENTER
        );

        setContentPane(mainPanel);
    }

    // =========================
    // LABEL CREATOR
    // =========================

    private JLabel createLabel(String text) {

        JLabel label =
                new JLabel(text);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        13
                )
        );

        label.setForeground(
                UIUtils.TEXT
        );

        label.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        return label;
    }

    // =========================
    // IMAGE SELECTOR
    // =========================

    private void chooseImage() {

        JFileChooser fileChooser =
                new JFileChooser();

        fileChooser.setDialogTitle(
                "Select Product Image"
        );

        int result =
                fileChooser.showOpenDialog(this);

        if (result ==
                JFileChooser.APPROVE_OPTION) {

            File file =
                    fileChooser.getSelectedFile();

            imageField.setText(
                    file.getAbsolutePath()
            );
        }
    }

    // =========================
    // PUBLISH PRODUCT
    // =========================

    private void publishProduct() {

        String name =
                nameField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        String category =
                categoryBox
                        .getSelectedItem()
                        .toString();

        String priceText =
                priceField.getText().trim();

        String condition =
                conditionBox
                        .getSelectedItem()
                        .toString();

        String imagePath =
                imageField.getText().trim();

        // =========================
        // VALIDATION
        // =========================

        if (name.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the product name.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            nameField.requestFocus();

            return;
        }

        if (description.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a product description.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            descriptionArea.requestFocus();

            return;
        }

        if (priceText.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the price.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            priceField.requestFocus();

            return;
        }

        double price;

        try {

            price =
                    Double.parseDouble(priceText);

            if (price <= 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Price must be greater than 0.",
                        "Validation Error",
                        JOptionPane.WARNING_MESSAGE
                );

                return;
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid price.",
                    "Validation Error",
                    JOptionPane.WARNING_MESSAGE
            );

            priceField.requestFocus();

            return;
        }

        // =========================
        // CREATE PRODUCT
        // =========================

        Product product =
                new Product(
                        currentUser.getId(),
                        name,
                        description,
                        category,
                        price,
                        condition,
                        imagePath
                );

        // =========================
        // SAVE TO DATABASE
        // =========================

        ProductDAO productDAO =
                new ProductDAO();

        boolean success =
                productDAO.addProduct(product);

        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Item published successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            clearForm();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to publish the item.\n" +
                    "Please check your database connection.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    // =========================
    // CLEAR FORM
    // =========================

    private void clearForm() {

        nameField.setText("");

        descriptionArea.setText("");

        categoryBox.setSelectedIndex(0);

        priceField.setText("");

        conditionBox.setSelectedIndex(0);

        imageField.setText("");

        nameField.requestFocus();
    }
}