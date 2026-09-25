import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class ExchangeRequestsFrame extends JFrame {

    private User currentUser;

    private JPanel requestsPanel;

    private ExchangeRequestDAO requestDAO;


    public ExchangeRequestsFrame(User user) {

        currentUser = user;

        requestDAO =
                new ExchangeRequestDAO();

        setTitle(
                "EduMart - Exchange Requests"
        );

        setMinimumSize(new Dimension(1000, 650));
        setExtendedState(JFrame.MAXIMIZED_BOTH);

                setDefaultCloseOperation(
                JFrame.DISPOSE_ON_CLOSE
        );

        createUI();

        loadRequests();
    }


    private void createUI() {

        JPanel main =
                new JPanel(
                        new BorderLayout()
                );

        main.setBackground(
                UIUtils.BACKGROUND
        );


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
                        "Exchange Requests"
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


        header.add(
                title,
                BorderLayout.WEST
        );


        main.add(
                header,
                BorderLayout.NORTH
        );


        requestsPanel =
                new JPanel();

        requestsPanel.setBackground(
                UIUtils.BACKGROUND
        );

        requestsPanel.setLayout(
                new BoxLayout(
                        requestsPanel,
                        BoxLayout.Y_AXIS
                )
        );


        JScrollPane scroll =
                new JScrollPane(
                        requestsPanel
                );

        scroll.setBorder(null);

        scroll.getVerticalScrollBar()
                .setUnitIncrement(15);


        main.add(
                scroll,
                BorderLayout.CENTER
        );


        setContentPane(main);
    }


    private void loadRequests() {

        requestsPanel.removeAll();


        List<ExchangeRequestDAO.RequestData> requests =
                requestDAO.getReceivedRequests(
                        currentUser.getId()
                );


        if (
                requests.isEmpty()
        ) {

            JLabel empty =
                    new JLabel(
                            "No exchange requests received."
                    );

            empty.setFont(
                    UIUtils.normalFont()
            );

            empty.setForeground(
                    UIUtils.MUTED
            );

            empty.setBorder(
                    new EmptyBorder(
                            40,
                            30,
                            30,
                            30
                    )
            );


            requestsPanel.add(empty);

        } else {

            for (
                    ExchangeRequestDAO.RequestData request
                    : requests
            ) {

                requestsPanel.add(
                        createRequestCard(
                                request
                        )
                );

                requestsPanel.add(
                        Box.createVerticalStrut(12)
                );
            }
        }


        requestsPanel.revalidate();

        requestsPanel.repaint();
    }


    private JPanel createRequestCard(
            ExchangeRequestDAO.RequestData request) {

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
                                18,
                                20,
                                18,
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

        card.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        JLabel requester =
                new JLabel(
                        "Request from "
                        + request.requesterName
                );

        requester.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        18
                )
        );

        requester.setForeground(
                UIUtils.TEXT
        );


        card.add(requester);


        card.add(
                Box.createVerticalStrut(4)
        );


        JLabel email =
                new JLabel(
                        request.requesterEmail
                );

        email.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        12
                )
        );

        email.setForeground(
                UIUtils.MUTED
        );


        card.add(email);


        card.add(
                Box.createVerticalStrut(12)
        );


        JLabel items =
                new JLabel(
                        "<html>"
                        + "<b>Your item:</b> "
                        + request.offeredItem
                        + "<br>"
                        + "<b>They want:</b> "
                        + request.wantedItem
                        + "</html>"
                );

        items.setFont(
                UIUtils.normalFont()
        );

        card.add(items);


        card.add(
                Box.createVerticalStrut(10)
        );


        JLabel message =
                new JLabel(
                        "<html><b>Message:</b> "
                        + safe(
                                request.message
                        )
                        + "</html>"
                );

        message.setFont(
                UIUtils.normalFont()
        );

        card.add(message);


        card.add(
                Box.createVerticalStrut(12)
        );


        JLabel status =
                new JLabel(
                        "Status: "
                        + request.status
                );

        status.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        status.setForeground(
                UIUtils.PRIMARY
        );


        card.add(status);


        if (
                "Pending".equalsIgnoreCase(
                        request.status
                )
        ) {

            card.add(
                    Box.createVerticalStrut(12)
            );


            JPanel buttons =
                    new JPanel(
                            new FlowLayout(
                                    FlowLayout.LEFT,
                                    8,
                                    0
                            )
                    );

            buttons.setBackground(
                    UIUtils.WHITE
            );


            JButton accept =
                    UIUtils.createButton(
                            "Accept"
                    );


            JButton reject =
                    new JButton(
                            "Reject"
                    );

            reject.setFont(
                    UIUtils.buttonFont()
            );

            reject.setFocusPainted(false);


            accept.addActionListener(
                    e -> updateStatus(
                            request.id,
                            "Accepted"
                    )
            );


            reject.addActionListener(
                    e -> updateStatus(
                            request.id,
                            "Rejected"
                    )
            );


            buttons.add(accept);

            buttons.add(reject);


            card.add(buttons);
        }


        return card;
    }


    private void updateStatus(
            int requestId,
            String status) {

        boolean success =
                requestDAO.updateRequestStatus(
                        requestId,
                        currentUser.getId(),
                        status
                );


        if (success) {

            JOptionPane.showMessageDialog(
                    this,
                    "Request marked as "
                    + status
                    + ".",
                    "Updated",
                    JOptionPane.INFORMATION_MESSAGE
            );


            loadRequests();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to update request.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    private String safe(
            String text) {

        if (
                text == null
                ||
                text.trim().isEmpty()
        ) {

            return "No message provided.";
        }

        return text;
    }
}