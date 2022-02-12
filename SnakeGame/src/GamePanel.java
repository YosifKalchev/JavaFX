
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;


public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 75;
    final int[] snakeCoordinatesX = new int[GAME_UNITS];
    final int[] snakeCoordinatesY = new int[GAME_UNITS];
    int bodyParts = 6;
    int applesEaten;
    int appleCoordinatesX;
    int appleCoordinatesY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;

    GamePanel() {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new SnakeKeyAdapter());
        startGame();
    }

    public void startGame() {
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics) {

        if (running) {
            /* Draw the grid lines

            for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
                graphics.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                graphics.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
             */

            graphics.setColor(Color.red);
            graphics.fillOval(appleCoordinatesX, appleCoordinatesY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i==0) {
                    graphics.setColor(new Color(13, 138, 42));
                    graphics.fillRect(snakeCoordinatesX[i], snakeCoordinatesY[i],
                            UNIT_SIZE, UNIT_SIZE);
                } else {
                    graphics.setColor(new Color(51,212,88));
                    graphics.fillRect(snakeCoordinatesX[i], snakeCoordinatesY[i],
                            UNIT_SIZE, UNIT_SIZE);
                }
            }

            showScoreBoard(graphics);

        } else {

            gameOver(graphics);
        }

    }

    private void showScoreBoard(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Game Font", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: " + applesEaten,
                (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2,
                graphics.getFont().getSize());
    }

    public void newApple() {
        appleCoordinatesX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleCoordinatesY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            snakeCoordinatesX[i] = snakeCoordinatesX[i-1];
            snakeCoordinatesY[i] = snakeCoordinatesY[i-1];
        }

        switch (direction) {
            case 'U' -> snakeCoordinatesY[0] = snakeCoordinatesY[0] - UNIT_SIZE;
            case 'D' -> snakeCoordinatesY[0] = snakeCoordinatesY[0] + UNIT_SIZE;
            case 'L' -> snakeCoordinatesX[0] = snakeCoordinatesX[0] - UNIT_SIZE;
            case 'R' -> snakeCoordinatesX[0] = snakeCoordinatesX[0] + UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((snakeCoordinatesX[0] == appleCoordinatesX) &&
                (snakeCoordinatesY[0] == appleCoordinatesY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {

        //checks if the head collides with the body
        for (int i = bodyParts; i > 0; i--) {
            if ((snakeCoordinatesX[0] == snakeCoordinatesX[i]) &&
                    (snakeCoordinatesY[0] == snakeCoordinatesY[i])) {
                running = false;
                break;
            }
        }

        //check if the head collides with the borders
        if (snakeCoordinatesX[0] < 0 || snakeCoordinatesX[0] > SCREEN_WIDTH ||
        snakeCoordinatesY[0] < 0 || snakeCoordinatesY[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics) {

            showScoreBoard(graphics);

            showGameOver(graphics);
    }

    private void showGameOver(Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font("Game Font", Font.BOLD, 75));
        FontMetrics metrics = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (SCREEN_WIDTH - metrics.stringWidth("Game Over"))/2,
                SCREEN_HEIGHT/2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class SnakeKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent event) {
            switch(event.getKeyCode()) {
                case KeyEvent.VK_LEFT -> {
                    if (direction != 'R') {
                        direction = 'L';
                    }
                }


                case KeyEvent.VK_RIGHT -> {
                    if (direction != 'L') {
                        direction = 'R';
                    }
                }

                case KeyEvent.VK_UP -> {
                    if (direction != 'D') {
                        direction = 'U';
                    }
                }

                case KeyEvent.VK_DOWN -> {
                    if (direction != 'U') {
                        direction = 'D';
                    }
                }

            }
        }
    }
}
