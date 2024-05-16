import javax.swing.*;



public class Main {
    private  static final int WIDTH = 800;
    private  static final int HEIGHT = 600;

    public static void main(String[] args) {

        System.out.println("Running.....");
        final JFrame frame = new JFrame("Juego de la Serpiente");
        frame.setSize(WIDTH, HEIGHT);
        SnakeGame game = new SnakeGame(WIDTH, HEIGHT);
        frame.add(game);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.pack();
        game.startGame();
    }
}