import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class Ventana extends JFrame {
    Panel panel;

    public Ventana() {
        panel = new Panel();
        setTitle("Acomodo de datos");
        setSize(panel.width, panel.height);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        add(panel);
        setVisible(true);
    }
}
