import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditProductFrame extends JFrame {

    private Product product;
    private User currentUser;

    private JTextField nameField;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryBox;
    private JTextField priceField;
    private JComboBox<String> conditionBox;
    private JTextField imagePathField;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public EditProductFrame(
            Product product,
            User currentUser) {

        this.product = product;
        this.currentUser = currentUser;


        setTitle(
                "EduMart - Edit Product"
        );


        setSize(
                700,
                650
        );


        setLocationRelativeTo(null);


        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );


        setResizable(false);


        createUI();


        loadProductData();
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
                        20,
                        25,
                        20,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "Edit Product"
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
        // FORM
        // ======================================

        JPanel form =
                new JPanel(
                        new GridBagLayout()
                );


        form.setBackground(
                UIUtils.WHITE
        );


        form.setBorder(
                new EmptyBorder(
                        25,
                        35,
                        25,
                        35
                )
        );


        GridBagConstraints gbc =
                new GridBagConstraints();


        gbc.insets =
                new Insets(
                        7,
                        7,
                        7,
                        7
                );


        gbc.fill =
                GridBagConstraints.HORIZONTAL;


        gbc.weightx = 1;


        int row = 0;


        // NAME

        addLabel(
                form,
                gbc,
                "Product Name",
                row
        );


        nameField =
                UIUtils.createTextField();


        addComponent(
                form,
                gbc,
                nameField,
                row++
        );


        // DESCRIPTION

        addLabel(
                form,
                gbc,
                "Description",
                row
        );


        descriptionArea =
                new JTextArea(
                        4,
                        30
                );


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
                        new EmptyBorder(
                                8,
                                10,
                                8,
                                10
                        )
                )
        );


        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );


        addComponent(
                form,
                gbc,
                descriptionScroll,
                row++
        );


        // CATEGORY

        addLabel(
                form,
                gbc,
                "Category",
                row
        );


        categoryBox =
                new JComboBox<>(
                        new String[]{
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


        addComponent(
                form,
                gbc,
                categoryBox,
                row++
        );


        // PRICE

        addLabel(
                form,
                gbc,
                "Price (₹)",
                row
        );


        priceField =
                UIUtils.createTextField();


        addComponent(
                form,
                gbc,
                priceField,
                row++
        );


        // CONDITION

        addLabel(
                form,
                gbc,
                "Condition",
                row
        );


        conditionBox =
                new JComboBox<>(
                        new String[]{
                                "New",
                                "Like New",
                                "Good",
                                "Fair",
                                "Used"
                        }
                );


        conditionBox.setFont(
                UIUtils.normalFont()
        );


        addComponent(
                form,
                gbc,
                conditionBox,
                row++
        );


        // IMAGE

        addLabel(
                form,
                gbc,
                "Product Image",
                row
        );


        JPanel imagePanel =
                new JPanel(
                        new BorderLayout(
                                8,
                                0
                        )
                );


        imagePanel.setBackground(
                UIUtils.WHITE
        );


        imagePathField =
                UIUtils.createTextField();


        JButton browseButton =
                new JButton(
                        "Choose Image"
                );


        browseButton.setFocusPainted(
                false
        );


        browseButton.addActionListener(
                e -> chooseImage()
        );


        imagePanel.add(
                imagePathField,
                BorderLayout.CENTER
        );


        imagePanel.add(
                browseButton,
                BorderLayout.EAST
        );


        addComponent(
                form,
                gbc,
                imagePanel,
                row++
        );


        // ======================================
        // BUTTONS
        // ======================================

        JPanel buttonPanel =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.RIGHT,
                                10,
                                15
                        )
                );


        buttonPanel.setBackground(
                UIUtils.WHITE
        );


        JButton cancelButton =
                new JButton(
                        "Cancel"
                );


        cancelButton.setFocusPainted(
                false
        );


        JButton updateButton =
                UIUtils.createButton(
                        "Update Product"
                );


        updateButton.setPreferredSize(
                new Dimension(
                        150,
                        40
                )
        );


        cancelButton.addActionListener(
                e -> dispose()
        );


        updateButton.addActionListener(
                e -> updateProduct()
        );


        buttonPanel.add(
                cancelButton
        );


        buttonPanel.add(
                updateButton
        );


        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;


        form.add(
                buttonPanel,
                gbc
        );


        mainPanel.add(
                new JScrollPane(form),
                BorderLayout.CENTER
        );


        setContentPane(
                mainPanel
        );
    }


    // ==========================================
    // ADD LABEL
    // ==========================================

    private void addLabel(
            JPanel panel,
            GridBagConstraints gbc,
            String text,
            int row) {

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;


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


        panel.add(
                label,
                gbc
        );
    }


    // ==========================================
    // ADD COMPONENT
    // ==========================================

    private void addComponent(
            JPanel panel,
            GridBagConstraints gbc,
            Component component,
            int row) {

        gbc.gridx = 1;
        gbc.gridy = row;
        gbc.gridwidth = 1;


        panel.add(
                component,
                gbc
        );
    }


    // ==========================================
    // LOAD PRODUCT DATA
    // ==========================================

    private void loadProductData() {

        nameField.setText(
                product.getName()
        );


        descriptionArea.setText(
                product.getDescription()
        );


        categoryBox.setSelectedItem(
                product.getCategory()
        );


        priceField.setText(
                String.valueOf(
                        product.getPrice()
                )
        );


        conditionBox.setSelectedItem(
                product.getConditionType()
        );


        String image =
                product.getImagePath();


        if (image != null) {

            imagePathField.setText(
                    image
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
                "Choose Product Image"
        );


        int result =
                chooser.showOpenDialog(this);


        if (
                result ==
                JFileChooser.APPROVE_OPTION
        ) {

            File file =
                    chooser.getSelectedFile();


            imagePathField.setText(
                    file.getAbsolutePath()
            );
        }
    }


    // ==========================================
    // UPDATE PRODUCT
    // ==========================================

    private void updateProduct() {

        String name =
                nameField.getText().trim();


        String description =
                descriptionArea.getText().trim();


        String category =
                (String)
                categoryBox.getSelectedItem();


        String priceText =
                priceField.getText().trim();


        String condition =
                (String)
                conditionBox.getSelectedItem();


        String imagePath =
                imagePathField.getText().trim();


        // ======================================
        // VALIDATION
        // ======================================

        if (name.isEmpty()) {

            showError(
                    "Please enter product name."
            );

            return;
        }


        if (category == null) {

            showError(
                    "Please select a category."
            );

            return;
        }


        if (priceText.isEmpty()) {

            showError(
                    "Please enter a price."
            );

            return;
        }


        double price;


        try {

            price =
                    Double.parseDouble(
                            priceText
                    );

        } catch (NumberFormatException e) {

            showError(
                    "Please enter a valid price."
            );

            return;
        }


        if (price <= 0) {

            showError(
                    "Price must be greater than 0."
            );

            return;
        }


        // ======================================
        // SQL
        // ======================================

        String query =
                "UPDATE products "
                + "SET name = ?, "
                + "description = ?, "
                + "category = ?, "
                + "price = ?, "
                + "condition_type = ?, "
                + "image_path = ? "
                + "WHERE id = ? "
                + "AND seller_id = ?";


        try (
                Connection con =
                        DBConnection.getConnection()
        ) {

            if (con == null) {

                showError(
                        "Database connection failed."
                );

                return;
            }


            try (
                    PreparedStatement ps =
                            con.prepareStatement(query)
            ) {

                ps.setString(
                        1,
                        name
                );


                ps.setString(
                        2,
                        description
                );


                ps.setString(
                        3,
                        category
                );


                ps.setDouble(
                        4,
                        price
                );


                ps.setString(
                        5,
                        condition
                );


                ps.setString(
                        6,
                        imagePath
                );


                ps.setInt(
                        7,
                        product.getId()
                );


                ps.setInt(
                        8,
                        currentUser.getId()
                );


                int rows =
                        ps.executeUpdate();


                if (rows > 0) {

                    JOptionPane.showMessageDialog(
                            this,
                            "Product updated successfully!",
                            "EduMart",
                            JOptionPane.INFORMATION_MESSAGE
                    );


                    dispose();

                } else {

                    showError(
                            "Product could not be updated."
                    );
                }
            }


        } catch (SQLException e) {

            e.printStackTrace();


            showError(
                    "Database error while updating product."
            );
        }
    }


    // ==========================================
    // ERROR MESSAGE
    // ==========================================

    private void showError(
            String message) {

        JOptionPane.showMessageDialog(
                this,
                message,
                "EduMart",
                JOptionPane.ERROR_MESSAGE
        );
    }
}