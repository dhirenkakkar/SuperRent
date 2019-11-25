import javax.swing.JButton;
import javax.swing.JFrame;

public class GenerateReports extends JFrame {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args) {
        new Main().setVisible(true);
    }

    public Main() {
        setSize(600,600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSe);

        JButton button = new JButton("Generate report");
    }
}