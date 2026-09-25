import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Keeps EduMart secondary pages consistent with the laptop-sized main window.
 * The main MainFrame remains open in the background while the selected page
 * is brought to the foreground and fitted to the usable screen area.
 */
public final class EduMartWindowUtils {

    private EduMartWindowUtils() {
    }

    public static void showLaptopWindow(JFrame frame) {

        if (frame == null) {
            return;
        }

        // Allow the window to occupy the full usable laptop screen.
        frame.setResizable(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Use the actual usable screen bounds instead of a hard-coded
        // 1200 x 750 size. This works on different laptop resolutions.
        Rectangle screenBounds =
                GraphicsEnvironment
                        .getLocalGraphicsEnvironment()
                        .getMaximumWindowBounds();

        frame.setBounds(screenBounds);

        frame.setVisible(true);

        // Bring the selected page in front of MainFrame.
        SwingUtilities.invokeLater(() -> {
            frame.toFront();
            frame.requestFocus();
        });
    }
}
