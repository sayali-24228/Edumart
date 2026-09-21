import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;

/**
 * Central visual design system for EduMart.
 * All frames use this class so the application keeps one consistent look.
 */
public final class UIUtils {

    private UIUtils() { }

    // ---------- Modern EduMart palette ----------
    public static final Color PRIMARY = new Color(91, 70, 214);       // indigo-purple
    public static final Color PRIMARY_DARK = new Color(73, 56, 180);
    public static final Color PRIMARY_LIGHT = new Color(238, 234, 255);
    public static final Color BACKGROUND = new Color(247, 248, 252);
    public static final Color WHITE = Color.WHITE;
    public static final Color TEXT = new Color(24, 28, 38);
    public static final Color MUTED = new Color(107, 114, 128);
    public static final Color BORDER = new Color(226, 228, 238);
    public static final Color SIDEBAR = new Color(28, 25, 54);
    public static final Color SIDEBAR_HOVER = new Color(50, 45, 84);
    public static final Color SUCCESS = new Color(22, 163, 74);
    public static final Color DANGER = new Color(220, 38, 38);

    public static final int RADIUS = 14;

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

    /** Apply modern Swing defaults after the Look & Feel has been selected. */
    public static void installModernDefaults() {
        UIManager.put("Button.font", buttonFont());
        UIManager.put("Label.font", normalFont());
        UIManager.put("TextField.font", normalFont());
        UIManager.put("PasswordField.font", normalFont());
        UIManager.put("ComboBox.font", normalFont());
        UIManager.put("Table.font", normalFont());
        UIManager.put("TableHeader.font", new Font("Segoe UI", Font.BOLD, 13));
        UIManager.put("OptionPane.messageFont", normalFont());
        UIManager.put("OptionPane.buttonFont", buttonFont());
        UIManager.put("ToolTip.font", new Font("Segoe UI", Font.PLAIN, 12));
        UIManager.put("ScrollBar.width", 10);
    }

    public static JButton createButton(String text) {
        RoundedButton button = new RoundedButton(text, PRIMARY, Color.WHITE);
        button.setFont(buttonFont());
        button.setPreferredSize(new Dimension(120, 42));
        return button;
    }

    public static JButton createSidebarButton(String text) {
        RoundedButton button = new RoundedButton(text, SIDEBAR, new Color(224, 221, 242));
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setBorder(new EmptyBorder(11, 16, 11, 12));
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 44));
        button.setPreferredSize(new Dimension(190, 44));
        button.setHoverBackground(SIDEBAR_HOVER);
        return button;
    }

    public static JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(normalFont());
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setCaretColor(PRIMARY);
        field.setOpaque(true);
        field.setBorder(new RoundedLineBorder(BORDER, 12, 1));
        field.setMargin(new Insets(9, 12, 9, 12));
        return field;
    }

    public static JPasswordField createPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(normalFont());
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setCaretColor(PRIMARY);
        field.setOpaque(true);
        field.setBorder(new RoundedLineBorder(BORDER, 12, 1));
        field.setMargin(new Insets(9, 12, 9, 12));
        return field;
    }

    public static JLabel label(String text) {
        JLabel label = new JLabel(text);
        label.setFont(normalFont());
        label.setForeground(TEXT);
        return label;
    }

    /** Rounded border for cards and panels. */
    public static javax.swing.border.Border roundedBorder() {
        return new RoundedLineBorder(BORDER, RADIUS, 1);
    }

    /** Card border with a subtle outline and padding. */
    public static javax.swing.border.Border cardBorder(int padding) {
        return javax.swing.BorderFactory.createCompoundBorder(
                roundedBorder(),
                new EmptyBorder(padding, padding, padding, padding)
        );
    }

    /** A small secondary/outline button. */
    public static JButton createOutlineButton(String text) {
        RoundedButton button = new RoundedButton(text, WHITE, PRIMARY);
        button.setBorder(new RoundedLineBorder(PRIMARY, 12, 1));
        button.setHoverBackground(PRIMARY_LIGHT);
        return button;
    }

    private static final class RoundedButton extends JButton {
        private Color normalBackground;
        private Color hoverBackground;
        private final Color foregroundColor;

        RoundedButton(String text, Color background, Color foreground) {
            super(text);
            this.normalBackground = background;
            this.hoverBackground = background;
            this.foregroundColor = foreground;

            setForeground(foreground);
            setBackground(background);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
            setMargin(new Insets(10, 16, 10, 16));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    setBackground(hoverBackground);
                    repaint();
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setBackground(normalBackground);
                    repaint();
                }
            });
        }

        void setHoverBackground(Color color) {
            this.hoverBackground = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), RADIUS, RADIUS);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    private static final class RoundedLineBorder extends AbstractBorder {
        private final Color color;
        private final int radius;
        private final int thickness;

        RoundedLineBorder(Color color, int radius, int thickness) {
            this.color = color;
            this.radius = radius;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.setStroke(new BasicStroke(thickness));
            int inset = thickness / 2;
            g2.drawRoundRect(x + inset, y + inset, width - thickness, height - thickness, radius, radius);
            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(10, 12, 10, 12);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.set(10, 12, 10, 12);
            return insets;
        }
    }
}
