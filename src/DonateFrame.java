import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DonateFrame extends JFrame {

    private User currentUser;

    private JTextField itemField;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryBox;
    private JComboBox<String> conditionBox;
    private JTextField imageField;

    private JPanel donationPanel;


    // ==========================================
    // CONSTRUCTOR
    // ==========================================

    public DonateFrame(User user) {

        this.currentUser = user;

        setTitle(
                "EduMart - Donate"
        );

        setSize(
                1150,
                750
        );

        setLocationRelativeTo(null);

        setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        setResizable(false);

        createUI();

        loadDonations();
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
                        20,
                        25,
                        20,
                        25
                )
        );


        JLabel title =
                new JLabel(
                        "🎁 Donate"
                );

        title.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        26
                )
        );

        title.setForeground(
                Color.WHITE
        );


        JLabel subtitle =
                new JLabel(
                        "Give useful items to students who need them."
                );

        subtitle.setFont(
                UIUtils.normalFont()
        );

        subtitle.setForeground(
                new Color(
                        219,
                        234,
                        254
                )
        );


        JPanel heading =
                new JPanel();

        heading.setBackground(
                UIUtils.PRIMARY
        );

        heading.setLayout(
                new BoxLayout(
                        heading,
                        BoxLayout.Y_AXIS
                )
        );


        heading.add(
                title
        );

        heading.add(
                Box.createVerticalStrut(
                        4
                )
        );

        heading.add(
                subtitle
        );


        header.add(
                heading,
                BorderLayout.WEST
        );


        main.add(
                header,
                BorderLayout.NORTH
        );


        // ======================================
        // MAIN CONTENT
        // ======================================

        JPanel content =
                new JPanel(
                        new BorderLayout(
                                20,
                                0
                        )
                );

        content.setBackground(
                UIUtils.BACKGROUND
        );

        content.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        // ======================================
        // DONATION FORM
        // ======================================

        JPanel form =
                new JPanel();

        form.setPreferredSize(
                new Dimension(
                        350,
                        500
                )
        );

        form.setBackground(
                UIUtils.WHITE
        );

        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                UIUtils.BORDER
                        ),
                        new EmptyBorder(
                                20,
                                20,
                                20,
                                20
                        )
                )
        );

        form.setLayout(
                new BoxLayout(
                        form,
                        BoxLayout.Y_AXIS
                )
        );


        JLabel formTitle =
                new JLabel(
                        "Donate an Item"
                );

        formTitle.setFont(
                UIUtils.headingFont()
        );

        formTitle.setForeground(
                UIUtils.TEXT
        );


        form.add(
                formTitle
        );

        form.add(
                Box.createVerticalStrut(
                        15
                )
        );


        form.add(
                UIUtils.label(
                        "Item Name"
                )
        );


        itemField =
                UIUtils.createTextField();


        form.add(
                itemField
        );

        form.add(
                Box.createVerticalStrut(
                        10
                )
        );


        form.add(
                UIUtils.label(
                        "Category"
                )
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
                                "Notes",
                                "Engineering Instruments",
                                "Other"
                        }
                );


        categoryBox.setFont(
                UIUtils.normalFont()
        );


        form.add(
                categoryBox
        );

        form.add(
                Box.createVerticalStrut(
                        10
                )
        );


        form.add(
                UIUtils.label(
                        "Condition"
                )
        );


        conditionBox =
                new JComboBox<>(
                        new String[]{
                                "New",
                                "Like New",
                                "Good",
                                "Fair"
                        }
                );


        conditionBox.setFont(
                UIUtils.normalFont()
        );


        form.add(
                conditionBox
        );

        form.add(
                Box.createVerticalStrut(
                        10
                )
        );


        form.add(
                UIUtils.label(
                        "Description"
                )
        );


        descriptionArea =
                new JTextArea(
                        5,
                        20
                );


        descriptionArea.setFont(
                UIUtils.normalFont()
        );


        descriptionArea.setLineWrap(
                true
        );


        descriptionArea.setWrapStyleWord(
                true
        );


        JScrollPane descriptionScroll =
                new JScrollPane(
                        descriptionArea
                );


        form.add(
                descriptionScroll
        );

        form.add(
                Box.createVerticalStrut(
                        10
                )
        );


        form.add(
                UIUtils.label(
                        "Image Path (Optional)"
                )
        );


        imageField =
                UIUtils.createTextField();


        form.add(
                imageField
        );


        form.add(
                Box.createVerticalStrut(
                        15
                )
        );


        JButton donateButton =
                UIUtils.createButton(
                        "Donate Item"
                );


        donateButton.setAlignmentX(
                Component.CENTER_ALIGNMENT
        );


        donateButton.addActionListener(
                e -> saveDonation()
        );


        form.add(
                donateButton
        );


        content.add(
                form,
                BorderLayout.WEST
        );


        // ======================================
        // DONATION LIST
        // ======================================

        donationPanel =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                15,
                                15
                        )
                );


        donationPanel.setBackground(
                UIUtils.BACKGROUND
        );


        donationPanel.setBorder(
                new EmptyBorder(
                        5,
                        5,
                        5,
                        5
                )
        );


        JScrollPane scroll =
                new JScrollPane(
                        donationPanel
                );


        scroll.setBorder(
                null
        );


        scroll.getVerticalScrollBar()
                .setUnitIncrement(
                        16
                );


        content.add(
                scroll,
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
    // SAVE DONATION
    // ==========================================

    private void saveDonation() {

        String item =
                itemField.getText().trim();


        String description =
                descriptionArea
                        .getText()
                        .trim();


        String category =
                String.valueOf(
                        categoryBox
                                .getSelectedItem()
                );


        String condition =
                String.valueOf(
                        conditionBox
                                .getSelectedItem()
                );


        String image =
                imageField
                        .getText()
                        .trim();


        if (item.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the item name.",
                    "EduMart",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }


        DonationDAO dao =
                new DonationDAO();


        boolean success =
                dao.addDonation(
                        currentUser.getId(),
                        item,
                        description,
                        category,
                        condition,
                        image
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Your item has been added for donation.",
                    "Donation Successful",
                    JOptionPane.INFORMATION_MESSAGE
            );


            itemField.setText("");

            descriptionArea.setText("");

            imageField.setText("");

            loadDonations();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to create donation.",
                    "EduMart",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // ==========================================
    // LOAD DONATIONS
    // ==========================================

    private void loadDonations() {

        DonationDAO dao =
                new DonationDAO();


        List<DonationDAO.DonationData> list =
                dao.getAvailableDonations(
                        currentUser.getId()
                );


        donationPanel.removeAll();


        if (list.isEmpty()) {

            JLabel empty =
                    new JLabel(
                            "No donation items available right now."
                    );

            empty.setFont(
                    UIUtils.normalFont()
            );

            empty.setForeground(
                    UIUtils.MUTED
            );


            donationPanel.add(
                    empty
            );

        } else {

            for (
                    DonationDAO.DonationData data :
                    list
            ) {

                donationPanel.add(
                        createDonationCard(
                                data
                        )
                );
            }
        }


        donationPanel.revalidate();

        donationPanel.repaint();
    }


    // ==========================================
    // DONATION CARD
    // ==========================================

    private JPanel createDonationCard(
            DonationDAO.DonationData data) {

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
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        JLabel name =
                new JLabel(
                        data.itemName
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


        JLabel details =
                new JLabel(
                        "<html>"
                        + "Category: "
                        + data.category
                        + "<br>"
                        + "Condition: "
                        + data.condition
                        + "<br>"
                        + "Donated by: "
                        + data.donorName
                        + "</html>"
                );


        details.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        details.setForeground(
                UIUtils.MUTED
        );


        JTextArea description =
                new JTextArea(
                        data.description == null
                                ? ""
                                : data.description
                );


        description.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );


        description.setForeground(
                UIUtils.TEXT
        );


        description.setBackground(
                UIUtils.WHITE
        );


        description.setLineWrap(
                true
        );


        description.setWrapStyleWord(
                true
        );


        description.setEditable(
                false
        );


        JButton claim =
                UIUtils.createButton(
                        "Claim Donation"
                );


        claim.addActionListener(
                e -> {

                    int result =
                            JOptionPane.showConfirmDialog(
                                    this,
                                    "Claim "
                                    + data.itemName
                                    + "?",
                                    "EduMart",
                                    JOptionPane.YES_NO_OPTION
                            );


                    if (
                            result ==
                            JOptionPane.YES_OPTION
                    ) {

                        DonationDAO dao =
                                new DonationDAO();


                        if (
                                dao.claimDonation(
                                        data.id
                                )
                        ) {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "Donation claimed successfully.",
                                    "EduMart",
                                    JOptionPane.INFORMATION_MESSAGE
                            );


                            loadDonations();

                        } else {

                            JOptionPane.showMessageDialog(
                                    this,
                                    "This donation may already have been claimed.",
                                    "EduMart",
                                    JOptionPane.WARNING_MESSAGE
                            );
                        }
                    }
                }
        );


        JPanel top =
                new JPanel();

        top.setBackground(
                UIUtils.WHITE
        );

        top.setLayout(
                new BoxLayout(
                        top,
                        BoxLayout.Y_AXIS
                )
        );


        top.add(
                name
        );

        top.add(
                Box.createVerticalStrut(
                        6
                )
        );

        top.add(
                details
        );


        card.add(
                top,
                BorderLayout.NORTH
        );


        card.add(
                description,
                BorderLayout.CENTER
        );


        card.add(
                claim,
                BorderLayout.SOUTH
        );


        return card;
    }
}