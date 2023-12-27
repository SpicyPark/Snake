import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    int[] x = new int[Constants.NUM_UNITS];
    int[] y = new int[Constants.NUM_UNITS];
    Random rand = new Random();
    int foodX = rand.nextInt(Constants.WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
    int foodY = rand.nextInt(Constants.HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
    int length = 5;
    boolean alive = true;

    public GamePanel() {
        super();
        this.setPreferredSize(this.getPreferredSize());
        this.setBackground(Color.BLACK);
        
        Timer clock = new Timer(100, this);
        clock.start();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(Constants.WIDTH, Constants.HEIGHT);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);
        g.drawString("Made by SpicyPark", 30, 30);
        g.drawString("Score: " + (length - 5), 30, 50);

        g.setColor(Color.RED);
        g.fillOval(foodX, foodY, Constants.UNIT_SIZE, Constants.UNIT_SIZE);

        g.setColor(Color.GREEN);
        for (int i = 0; i < length; i++) g.fillRect(x[i], y[i], Constants.UNIT_SIZE, Constants.UNIT_SIZE);

        if (!alive) {
            g.setColor(Color.WHITE);
            g.drawString("Game Over", 30, 70);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (alive) {
            switch (Constants.DIRECTION) {
                case 'U': y[0] -= Constants.UNIT_SIZE; break;
                case 'D': y[0] += Constants.UNIT_SIZE; break;
                case 'L': x[0] -= Constants.UNIT_SIZE; break;
                case 'R': x[0] += Constants.UNIT_SIZE; break;
            }
            for (int i = length; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            for (int i = length; i > 1; i--) {
                if (x[0] == x[i] && y[0] == y[i]) {
                    alive = false;
                }
            }
            if (x[0] < 0 || x[0] > Constants.WIDTH || y[0] < 0 || y[0] > Constants.HEIGHT) alive = false;
            if (x[0] == foodX && y[0] == foodY) {
                length++;
                foodX = rand.nextInt(Constants.WIDTH / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
                foodY = rand.nextInt(Constants.HEIGHT / Constants.UNIT_SIZE) * Constants.UNIT_SIZE;
            }
        }
        this.repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: if (Constants.DIRECTION != 'D') Constants.DIRECTION = 'U'; break;
            case KeyEvent.VK_DOWN: if (Constants.DIRECTION != 'U') Constants.DIRECTION = 'D'; break;
            case KeyEvent.VK_LEFT: if (Constants.DIRECTION != 'R') Constants.DIRECTION = 'L'; break;
            case KeyEvent.VK_RIGHT: if (Constants.DIRECTION != 'L') Constants.DIRECTION = 'R'; break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {}
}