import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class UIUtils {

    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color PRIMARY_DARK = new Color(29, 78, 216);
    public static final Color BACKGROUND = new Color(245, 247, 251);
    public static final Color WHITE = Color.WHITE;
    public static final Color TEXT = new Color(31, 41, 55);
    public static final Color MUTED = new Color(107, 114, 128);
    public static final Color BORDER = new Color(229, 231, 235);
    public static final Color SIDEBAR = new Color(17, 24, 39);
    public static final Color SUCCESS = new Color(22, 163, 74);

    public static Font titleFont() {
        return new Font("Segoe UI", Font.BOLD, 28);
    }

    public static Font headingFont() {
        return new Font("Segoe UI", Font.BOLD, 20);
    }

    public static Font normalFont() {
        return new Font("Segoe UI", Font.PLAIN, 14);
    }

    public static Font buttonFont() {
        return new Font("Segoe UI", Font.BOLD, 14);
    }

    public static JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(buttonFont());
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        return button;
    }

    public static JButton createSidebarButton(String text) {

        JButton button = new JButton(text);

        button.setFont(
                new Font("Segoe UI", Font.PLAIN, 14)
        );

        button.setForeground(
                new Color(209, 213, 219)
        );

        button.setBackground(SIDEBAR);

        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );

        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        button.setBorder(
                new EmptyBorder(12, 20, 12, 10)
        );

        return button;
    }

    public static JTextField createTextField() {

        JTextField field = new JTextField();

        field.setFont(normalFont());
        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        return field;
    }

    public static JPasswordField createPasswordField() {

        JPasswordField field =
                new JPasswordField();

        field.setFont(normalFont());

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER),
                        new EmptyBorder(8, 10, 8, 10)
                )
        );

        return field;
    }

    public static JLabel label(String text) {

        JLabel label = new JLabel(text);

        label.setFont(normalFont());
        label.setForeground(TEXT);

        return label;
    }
}