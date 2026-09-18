import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class StartEduMart {

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName()
            );

        } catch (Exception e) {

            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {

            new LoginFrame().setVisible(true);

        });
    }
}