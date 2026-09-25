import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.List;

public class DonateFrame extends JFrame {

    private final User currentUser;

    private JTextField itemField;
    private JTextArea descriptionArea;
    private JComboBox<String> categoryBox;
    private JComboBox<String> conditionBox;
    private JTextField imageField;
    private JPanel donationPanel;

    public DonateFrame(User user) {

        currentUser = user;

        setTitle("EduMart - Donate");
        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
        loadDonations();
    }

    private void createUI() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UIUtils.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIUtils.PRIMARY);
        header.setBorder(new EmptyBorder(20, 25, 20, 25));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(
                titlePanel,
                BoxLayout.Y_AXIS
        ));

        JLabel title = new JLabel("🎁 Donate an Item");
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Give useful items to students who need them."
        );
        subtitle.setFont(UIUtils.normalFont());
        subtitle.setForeground(new Color(230, 225, 255));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        JButton requests = UIUtils.createOutlineButton(
                "Donation Requests"
        );
        requests.addActionListener(
                e -> new DonationRequestsFrame(
                        currentUser
                ).setVisible(true)
        );

        header.add(titlePanel, BorderLayout.WEST);
        header.add(requests, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(
                1, 2, 20, 0
        ));
        content.setBackground(UIUtils.BACKGROUND);
        content.setBorder(new EmptyBorder(20, 25, 20, 25));

        content.add(createForm());
        content.add(createAvailableDonations());

        main.add(content, BorderLayout.CENTER);
        setContentPane(main);
    }

    private JPanel createForm() {

        JPanel form = new JPanel();
        form.setBackground(UIUtils.WHITE);
        form.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER),
                        new EmptyBorder(22, 24, 22, 24)
                )
        );
        form.setLayout(new BoxLayout(
                form,
                BoxLayout.Y_AXIS
        ));

        JLabel heading = new JLabel("Create Donation");
        heading.setFont(UIUtils.headingFont());
        heading.setForeground(UIUtils.TEXT);

        form.add(heading);
        form.add(Box.createVerticalStrut(16));

        form.add(UIUtils.label("Item Name"));
        itemField = UIUtils.createTextField();
        setHeight(itemField, 42);
        form.add(itemField);
        form.add(Box.createVerticalStrut(12));

        form.add(UIUtils.label("Category"));
        categoryBox = new JComboBox<>(new String[]{
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
        });
        categoryBox.setFont(UIUtils.normalFont());
        setHeight(categoryBox, 40);
        form.add(categoryBox);
        form.add(Box.createVerticalStrut(12));

        form.add(UIUtils.label("Condition"));
        conditionBox = new JComboBox<>(new String[]{
                "New",
                "Like New",
                "Good",
                "Fair"
        });
        conditionBox.setFont(UIUtils.normalFont());
        setHeight(conditionBox, 40);
        form.add(conditionBox);
        form.add(Box.createVerticalStrut(12));

        form.add(UIUtils.label("Description"));

        descriptionArea = new JTextArea(5, 20);
        descriptionArea.setFont(UIUtils.normalFont());
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(new EmptyBorder(8, 10, 8, 10));

        JScrollPane descriptionScroll =
                new JScrollPane(descriptionArea);
        descriptionScroll.setBorder(
                BorderFactory.createLineBorder(UIUtils.BORDER)
        );
        setHeight(descriptionScroll, 120);

        form.add(descriptionScroll);
        form.add(Box.createVerticalStrut(12));

        form.add(UIUtils.label("Item Image"));

        JPanel imagePanel = new JPanel(new BorderLayout(8, 0));
        imagePanel.setBackground(UIUtils.WHITE);

        imageField = UIUtils.createTextField();
        imageField.setEditable(false);

        JButton choose = UIUtils.createOutlineButton("Choose");
        choose.setPreferredSize(new Dimension(100, 40));
        choose.addActionListener(e -> chooseImage());

        imagePanel.add(imageField, BorderLayout.CENTER);
        imagePanel.add(choose, BorderLayout.EAST);
        setHeight(imagePanel, 42);

        form.add(imagePanel);
        form.add(Box.createVerticalStrut(20));

        JButton donate = UIUtils.createButton(
                "Publish Donation"
        );
        donate.setAlignmentX(Component.LEFT_ALIGNMENT);
        donate.addActionListener(e -> saveDonation());

        form.add(donate);

        return form;
    }

    private JPanel createAvailableDonations() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(UIUtils.BACKGROUND);

        JLabel title = new JLabel(
                "Available Donations"
        );
        title.setFont(UIUtils.headingFont());
        title.setForeground(UIUtils.TEXT);

        wrapper.add(title, BorderLayout.NORTH);

        donationPanel = new JPanel();
        donationPanel.setBackground(UIUtils.BACKGROUND);
        donationPanel.setLayout(new BoxLayout(
                donationPanel,
                BoxLayout.Y_AXIS
        ));

        JScrollPane scroll = new JScrollPane(
                donationPanel
        );
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        wrapper.add(scroll, BorderLayout.CENTER);

        return wrapper;
    }

    private void chooseImage() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Donation Image");

        if (chooser.showOpenDialog(this)
                == JFileChooser.APPROVE_OPTION) {

            File file = chooser.getSelectedFile();
            String name = file.getName().toLowerCase();

            if (!name.endsWith(".jpg")
                    && !name.endsWith(".jpeg")
                    && !name.endsWith(".png")
                    && !name.endsWith(".webp")) {

                JOptionPane.showMessageDialog(
                        this,
                        "Please select JPG, JPEG, PNG or WEBP.",
                        "Invalid Image",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            imageField.setText(
                    file.getAbsolutePath()
            );
        }
    }

    private void saveDonation() {

        String item = itemField.getText().trim();
        String description =
                descriptionArea.getText().trim();

        String category =
                String.valueOf(categoryBox.getSelectedItem());

        String condition =
                String.valueOf(conditionBox.getSelectedItem());

        String image =
                imageField.getText().trim();

        if (item.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please enter the item name.",
                    "EduMart",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (image.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select an image so students can identify the item.",
                    "Image Required",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean success = new DonationDAO().addDonation(
                currentUser.getId(),
                item,
                description,
                category,
                condition,
                image
        );

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Donation published successfully."
                        : "Unable to create donation.",
                "EduMart",
                success
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );

        if (success) {
            itemField.setText("");
            descriptionArea.setText("");
            imageField.setText("");
            loadDonations();
        }
    }

    private void loadDonations() {

        List<DonationDAO.DonationData> list =
                new DonationDAO().getAvailableDonations(
                        currentUser.getId()
                );

        donationPanel.removeAll();

        if (list.isEmpty()) {

            JLabel empty = new JLabel(
                    "No donation items available right now."
            );
            empty.setFont(UIUtils.normalFont());
            empty.setForeground(UIUtils.MUTED);
            empty.setBorder(new EmptyBorder(20, 5, 5, 5));

            donationPanel.add(empty);

        } else {

            for (DonationDAO.DonationData data : list) {
                donationPanel.add(
                        createDonationCard(data)
                );
                donationPanel.add(
                        Box.createVerticalStrut(12)
                );
            }
        }

        donationPanel.revalidate();
        donationPanel.repaint();
    }

    private JPanel createDonationCard(
            DonationDAO.DonationData data) {

        JPanel card = new JPanel(new BorderLayout(12, 0));
        card.setBackground(UIUtils.WHITE);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER),
                        new EmptyBorder(12, 12, 12, 12)
                )
        );
        card.setMaximumSize(
                new Dimension(Integer.MAX_VALUE, 170)
        );

        JLabel image = createImage(data.imagePath);

        JPanel info = new JPanel();
        info.setBackground(UIUtils.WHITE);
        info.setLayout(new BoxLayout(
                info,
                BoxLayout.Y_AXIS
        ));

        JLabel name = new JLabel(
                data.itemName
        );
        name.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                17
        ));
        name.setForeground(UIUtils.TEXT);

        JLabel details = new JLabel(
                "<html>"
                        + "<b>Category:</b> "
                        + safe(data.category)
                        + "<br><b>Condition:</b> "
                        + safe(data.condition)
                        + "<br><b>Donated by:</b> "
                        + safe(data.donorName)
                        + "</html>"
        );
        details.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));
        details.setForeground(UIUtils.MUTED);

        JButton request = UIUtils.createButton(
                "Request Donation"
        );
        request.addActionListener(
                e -> sendDonationRequest(data)
        );

        info.add(name);
        info.add(Box.createVerticalStrut(6));
        info.add(details);
        info.add(Box.createVerticalStrut(10));
        info.add(request);

        card.add(image, BorderLayout.WEST);
        card.add(info, BorderLayout.CENTER);

        return card;
    }

    private void sendDonationRequest(
            DonationDAO.DonationData data) {

        JTextArea message = new JTextArea(
                "I am interested in receiving this item."
        );
        message.setRows(5);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setFont(UIUtils.normalFont());

        int result = JOptionPane.showConfirmDialog(
                this,
                new JScrollPane(message),
                "Request Donation",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) {
            return;
        }

        String text = message.getText().trim();

        if (text.isEmpty()) {
            text = "I am interested in receiving this item.";
        }

        boolean success =
                new DonationRequestDAO().sendRequest(
                        data.id,
                        currentUser.getId(),
                        text
                );

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Request sent to " + data.donorName + "."
                        : "A pending request already exists or the request failed.",
                "EduMart",
                success
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.WARNING_MESSAGE
        );
    }

    private JLabel createImage(String path) {

        JLabel label = new JLabel(
                "No Image",
                SwingConstants.CENTER
        );
        label.setPreferredSize(
                new Dimension(120, 130)
        );
        label.setOpaque(true);
        label.setBackground(new Color(243, 246, 250));
        label.setForeground(UIUtils.MUTED);

        if (path != null && !path.trim().isEmpty()) {

            File file = new File(path);

            if (file.exists()) {

                ImageIcon icon = new ImageIcon(path);

                if (icon.getIconWidth() > 0) {

                    Image scaled =
                            icon.getImage().getScaledInstance(
                                    120,
                                    130,
                                    Image.SCALE_SMOOTH
                            );

                    label.setText("");
                    label.setIcon(
                            new ImageIcon(scaled)
                    );
                }
            }
        }

        return label;
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

    private String safe(String value) {
        return value == null || value.trim().isEmpty()
                ? "Not specified"
                : value;
    }
}
