import javax.swing.SwingUtilities;

// Método principal da aplicação, para iniciar a aplicação
public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main(); 
            }
        });
    }
}
