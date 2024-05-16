

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.util.ArrayList;
import java.util.List;

public class SnakeGame  extends JPanel implements ActionListener {
    private final int width;
    private final int height;
    private int cellSize;
    private static final int FRAME_RATE = 20;
    private boolean gameStarted = false;
    private boolean gameOver = false;
    private final List<GamePoint> snake = new ArrayList<>();

    public SnakeGame(int width, int height) {
        super();
        this.width = width;
        this.height = height;
        this.cellSize = width / (FRAME_RATE * 2);
        setPreferredSize(new Dimension(width, height));
        setBackground(Color.BLACK);
    }

    public void startGame() {
        resetGameData();
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        requestFocusInWindow();
        repaint();
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                handleKeyEvent(e);
            }
        });
        new Timer(1000/FRAME_RATE, this).start();
    }

    private void handleKeyEvent(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE) {
            gameStarted=true;
        }
    }

    private void resetGameData() {
        snake.clear();
        snake.add(new GamePoint(width/2, height/2));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(!gameStarted) {
            g.setColor(Color.WHITE);
            g.setFont(g.getFont().deriveFont(30f));
            int currentHeight = height/2;
            final var g2D = (Graphics2D) g;
            final var frc = g2D.getFontRenderContext();
            final String message = "Press Spacebar\nto start";
            g.drawString("My String", 0, 0);
            for(final var line : message.split("\n")){
                final var layout = new TextLayout(line, g.getFont(), frc);
                final var bounds = layout.getBounds();
                final var targetWidth = (float) (width - bounds.getWidth())/2;
                layout.draw(g2D, targetWidth, currentHeight);
                currentHeight += g.getFontMetrics().getHeight();
            }
        }else {
            g.setColor(Color.GREEN);
            for(final var point : snake){
                g.fillRect(point.x, point.y, cellSize, cellSize);
            }
        }
    }

    private void move(){
        final GamePoint currentHead = snake.getFirst();
        final GamePoint newHead = new GamePoint(currentHead.x + cellSize, currentHead.y);
        snake.addFirst(newHead);

        if(isCollision()){
            gameOver = true;
            snake.removeFirst();
        }else{
            snake.removeLast();
        }
    }


    private boolean isCollision() {
        final GamePoint head = snake.getFirst();
        final var invalidWidth = (head.x < 0) || (head.x >= width);
        final var invalidHeight = (head.y < 0) || (head.y >= height);
        return (invalidWidth || invalidHeight);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(gameStarted && !gameOver) {
            move();
        }

        repaint();
    }

    private record GamePoint(int x, int y){

    }
}
