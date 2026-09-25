import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class DonationRequestsFrame extends JFrame {

    private final User currentUser;
    private final DonationRequestDAO requestDAO;
    private JPanel requestsPanel;

    public DonationRequestsFrame(User user) {
        currentUser = user;
        requestDAO = new DonationRequestDAO();

        setTitle("EduMart - Donation Requests");
        setMinimumSize(new Dimension(900, 600));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        createUI();
        loadRequests();
    }

    private void createUI() {

        JPanel main = new JPanel(new BorderLayout());
        main.setBackground(UIUtils.BACKGROUND);

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(UIUtils.PRIMARY);
        header.setBorder(new EmptyBorder(20, 28, 20, 28));

        JPanel titlePanel = new JPanel();
        titlePanel.setOpaque(false);
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Donation Requests");
        title.setFont(new Font("Segoe UI", Font.BOLD, 27));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel(
                "Review requests for the items you have donated."
        );
        subtitle.setFont(UIUtils.normalFont());
        subtitle.setForeground(new Color(230, 225, 255));

        titlePanel.add(title);
        titlePanel.add(Box.createVerticalStrut(4));
        titlePanel.add(subtitle);

        JButton refresh = UIUtils.createOutlineButton("Refresh");
        refresh.addActionListener(e -> loadRequests());

        header.add(titlePanel, BorderLayout.WEST);
        header.add(refresh, BorderLayout.EAST);

        main.add(header, BorderLayout.NORTH);

        requestsPanel = new JPanel();
        requestsPanel.setBackground(UIUtils.BACKGROUND);
        requestsPanel.setLayout(new BoxLayout(requestsPanel, BoxLayout.Y_AXIS));
        requestsPanel.setBorder(new EmptyBorder(22, 28, 28, 28));

        JScrollPane scroll = new JScrollPane(requestsPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);

        main.add(scroll, BorderLayout.CENTER);
        setContentPane(main);
    }

    private void loadRequests() {

        requestsPanel.removeAll();

        List<DonationRequestDAO.RequestData> requests =
                requestDAO.getReceivedRequests(currentUser.getId());

        if (requests.isEmpty()) {

            JLabel empty = new JLabel(
                    "No donation requests received yet."
            );
            empty.setFont(UIUtils.headingFont());
            empty.setForeground(UIUtils.MUTED);
            empty.setAlignmentX(Component.LEFT_ALIGNMENT);

            requestsPanel.add(Box.createVerticalStrut(30));
            requestsPanel.add(empty);

        } else {

            for (DonationRequestDAO.RequestData request : requests) {
                requestsPanel.add(createRequestCard(request));
                requestsPanel.add(Box.createVerticalStrut(14));
            }
        }

        requestsPanel.revalidate();
        requestsPanel.repaint();
    }

    private JPanel createRequestCard(
            DonationRequestDAO.RequestData request) {

        JPanel card = new JPanel(new BorderLayout(18, 0));
        card.setBackground(UIUtils.WHITE);
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(UIUtils.BORDER),
                        new EmptyBorder(18, 20, 18, 20)
                )
        );
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 190));

        JPanel info = new JPanel();
        info.setBackground(UIUtils.WHITE);
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        JLabel item = new JLabel(
                safe(request.itemName, "Donation")
        );
        item.setFont(new Font("Segoe UI", Font.BOLD, 19));
        item.setForeground(UIUtils.TEXT);

        JLabel category = new JLabel(
                safe(request.category, "Other")
                        + " • "
                        + safe(request.condition, "Not specified")
        );
        category.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        category.setForeground(UIUtils.MUTED);

        JLabel requester = new JLabel(
                "Requested by: "
                        + safe(request.requesterName, "Student")
                        + "  •  "
                        + safe(request.requesterEmail, "")
        );
        requester.setFont(UIUtils.normalFont());
        requester.setForeground(UIUtils.TEXT);

        JLabel message = new JLabel(
                "<html><b>Message:</b> "
                        + safe(request.message, "No message.")
                        + "</html>"
        );
        message.setFont(UIUtils.normalFont());
        message.setForeground(UIUtils.TEXT);

        JLabel status = new JLabel(
                "Status: " + safe(request.status, "Pending")
        );
        status.setFont(new Font("Segoe UI", Font.BOLD, 12));
        status.setForeground(statusColor(request.status));

        info.add(item);
        info.add(Box.createVerticalStrut(5));
        info.add(category);
        info.add(Box.createVerticalStrut(10));
        info.add(requester);
        info.add(Box.createVerticalStrut(8));
        info.add(message);
        info.add(Box.createVerticalStrut(8));
        info.add(status);

        card.add(info, BorderLayout.CENTER);

        JPanel actions = new JPanel();
        actions.setBackground(UIUtils.WHITE);
        actions.setLayout(new BoxLayout(actions, BoxLayout.Y_AXIS));

        if ("Pending".equalsIgnoreCase(request.status)) {

            JButton accept = UIUtils.createButton("Accept");
            accept.setAlignmentX(Component.CENTER_ALIGNMENT);
            accept.addActionListener(
                    e -> updateStatus(request.id, "Accepted")
            );

            JButton reject = UIUtils.createOutlineButton("Reject");
            reject.setAlignmentX(Component.CENTER_ALIGNMENT);
            reject.addActionListener(
                    e -> updateStatus(request.id, "Rejected")
            );

            actions.add(accept);
            actions.add(Box.createVerticalStrut(8));
            actions.add(reject);

        } else {

            JLabel done = new JLabel(
                    request.status
            );
            done.setFont(new Font("Segoe UI", Font.BOLD, 13));
            done.setForeground(statusColor(request.status));
            done.setAlignmentX(Component.CENTER_ALIGNMENT);

            actions.add(done);
        }

        card.add(actions, BorderLayout.EAST);

        return card;
    }

    private void updateStatus(int requestId, String status) {

        String question = "Are you sure you want to "
                + status.toLowerCase()
                + " this donation request?";

        int result = JOptionPane.showConfirmDialog(
                this,
                question,
                status + " Donation Request",
                JOptionPane.YES_NO_OPTION
        );

        if (result != JOptionPane.YES_OPTION) {
            return;
        }

        boolean success = requestDAO.updateRequestStatus(
                requestId,
                currentUser.getId(),
                status
        );

        JOptionPane.showMessageDialog(
                this,
                success
                        ? "Request " + status.toLowerCase() + "."
                        : "Unable to update the request.",
                "EduMart",
                success
                        ? JOptionPane.INFORMATION_MESSAGE
                        : JOptionPane.ERROR_MESSAGE
        );

        if (success) {
            loadRequests();
        }
    }

    private Color statusColor(String status) {
        if ("Accepted".equalsIgnoreCase(status)) {
            return UIUtils.SUCCESS;
        }
        if ("Rejected".equalsIgnoreCase(status)) {
            return UIUtils.DANGER;
        }
        return UIUtils.PRIMARY;
    }

    private String safe(String value, String fallback) {
        return value == null || value.trim().isEmpty()
                ? fallback
                : value;
    }
}
