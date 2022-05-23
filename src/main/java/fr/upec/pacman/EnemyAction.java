package fr.upec.pacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnemyAction implements ActionListener {
    private Ghost[] ghosts;
    private Game game;
    private GameView p;

    public EnemyAction(Ghost[] ghosts, Game game, GameView p) {
        this.ghosts = ghosts;
        this.game = game;
        this.p = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (Ghost g : ghosts) {
            wall(g, game.getMap().getMap());
            p.repaint();
            checkLife(g); // Verifie si ca touche
        }
    }

    private void checkLife(Ghost ghost) {
        if (game.getPacman().checkCollision(ghost)) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

    //Choix de la trajectoire des fantomes dynamique :
    private void wall(Ghost g, Type[][] map) {
        int xMove = g.getX() + g.getDirection().getDx();
        int yMove = g.getY() + g.getDirection().getDy();

        while (map[(yMove) / 36][(xMove) / 36] == Type.W ||
                map[(yMove) / 36][(xMove + 34) / 36] == Type.W ||
                map[(yMove + 34) / 36][(xMove) / 36] == Type.W ||
                map[(yMove + 34) / 36][(xMove + 34) / 36] == Type.W) {
            g.setDirection(Direction.random());
            xMove = g.getX() + g.getDirection().getDx();
            yMove = g.getY() + g.getDirection().getDy();
        }

        g.setX(xMove);
        g.setY(yMove);
    }
}
