package fr.upec.pacman;

import javax.swing.*;
import java.awt.*;

public class GameView extends JComponent {
    public final static int size = 36;
    private final int footerX;
    private final int footerY;
    private boolean start; //Pour commencer la partie
    private Game game;
    private Timer timerGhost; //Ajouter la classe de Action listener
    private Timer timerPacman;
    private Frame frame;

    //Constructeur
    public GameView(Frame f, Game game) {
        super();
        setStart(false);
        setOpaque(true);
        this.timerGhost = new Timer(20, new EnemyAction(game.getGhosts(), game, this));
        this.timerPacman = new Timer(20, new PacAction(game, this, game.getPacman()));
        this.frame = f;
        this.game = game;
        this.footerX = game.getMap().getMap()[0].length * size;
        this.footerY = game.getMap().getMap().length * size;
    }

    public Frame getFrame() {
        return frame;
    }

    public Timer getTimerGhost() {
        return timerGhost;
    }

    public boolean isStart() {
        return start;
    }

    public void setStart(boolean start) {
        this.start = start;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.black);
        if (isStart()) {
            timerGhost.start();
            timerPacman.start();
            if (!game.getPacman().isAlive()) {
                loseScreen(g);
            } else {
                if (game.win()) {
                    winScreen(g);
                } else {
                    drawTerrain(g);
                    drawPacman(g);
                    drawEnemy(g);
                    drawFooter(g);
                }
            }
        } else {
            startScreen(g);
        }
    }

    // Terrain
    private void drawTerrain(Graphics g) {
        g.fill3DRect(0, 0, game.getMap().getMap()[0].length * size, game.getMap().getMap().length * size, start);

        int x;
        int y = 0;
        // x = width y = height  // size taille du bloc

        for (Type[] i : game.getMap().getMap()) {
            x = 0;
            for (Type j : i) {
                switch (j) {
                    case W:
                        g.setColor(Color.decode("#2E20BD"));
                        g.fillRect(x, y, size, size);
                        g.setColor(Color.black);
                        g.fillRect(x + 3, y + 3, size - 6, size - 6);
                        break;
                    case C:
                        g.setColor(Color.decode("#EDF033"));
                        g.fillOval(x + 12, y + 12, size - 14 * 2, size - 14 * 2);
                        break;
                    case I:
                        g.setColor(Color.decode("#9f40ff"));
                        g.fillOval(x + 10, y + 10, size - 10 * 2, size - 10 * 2);
                        break;
                    case S:
                        g.setColor(Color.decode("#FFA500"));
                        g.fillOval(x + 10, y + 10, size - 10 * 2, size - 10 * 2);
                        break;
                    case M:
                        g.setColor(Color.decode("#2ed12e"));
                        g.fillOval(x + 10, y + 10, size - 10 * 2, size - 10 * 2);
                        break;
                    case N:
                        g.setColor(Color.black);
                        g.fillRect(x, y, size, size);
                        break;
                }
                x += size;
            }
            y += size;
        }
    }

    private void drawFooter(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, footerY, 610, 370);
        int x = 0;
        for (int i = 0; i < game.getPacman().getLife(); i++) {
            g.setColor(game.getPacman().getColor());
            g.fillOval(x, footerY, size, size);
            g.setColor(Color.GRAY);
            g.fillOval(x + 22, footerY + 3, size + 8, size - 8);
            x += size;
        }
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + game.getScore(), footerX / 3, footerY + 22);
        if (game.getPacman().isInvisible()) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.PLAIN, 17));
            g.drawString("Invisible: " + (10 - (game.getPacman().getInvisibleTimer() / 1000)), (footerX / 3) * 2, footerY + 15);
        }
        if (game.getPacman().isSuperPow()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.PLAIN, 17));
            g.drawString("Super Power: " + (10 - (game.getPacman().getSuperPowTimer() / 1000)), (footerX / 3) * 2, footerY + 30);
        }
    }

    private void drawPacman(Graphics g) {
        g.setColor(game.getPacman().getColor());
        g.fillOval(game.getPacman().getX(), game.getPacman().getY(), size, size);
        g.setColor(Color.black);
    }

    private void drawEnemy(Graphics g) {
        for (Ghost ghost : game.getGhosts()) {
            g.setColor(ghost.getColor());
            g.fillOval(ghost.getX(), ghost.getY(), size, size);
        }
    }

    private void startScreen(Graphics g) {
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Press -SPACE- to start the game !", 90, 250);
        g.setColor(game.getPacman().getColor());
        g.fillOval(180, 270, size, size);
        g.setColor(Color.black);
        g.fillOval(180 + 22, 270 + 3, size + 8, size - 8);
        int i = 0;
        for (Ghost ghost : game.getGhosts()) {
            g.setColor(ghost.getColor());
            g.fillOval(230 + i, 270, size, size);
            i += 46;
        }
    }

    private void winScreen(Graphics g) {
        this.timerGhost.stop();
        this.timerPacman.stop();
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("YOU WIN !", 220, 250);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press -SPACE- to restart the game.", 130, 270);
    }

    private void loseScreen(Graphics g) {
        this.timerGhost.stop();
        this.timerPacman.stop();
        g.fillRect(0, 0, frame.getWidth(), frame.getHeight());
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("YOU LOSE !", 220, 250);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Press -SPACE- to restart the game.", 130, 270);
    }
}
