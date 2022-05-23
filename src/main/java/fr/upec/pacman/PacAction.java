package fr.upec.pacman;

import fr.upec.pacman.EntityState.InvisiblePacman;
import fr.upec.pacman.EntityState.SuperPovPacman;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacAction implements ActionListener {
    private Game game;
    private GameView view;
    private Pacman pacman;

    public PacAction(Game game, GameView view, Pacman pacman) {
        this.game = game;
        this.view = view;
        this.pacman = pacman;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Type[][] map = game.getMap().getMap();
        int pacXMove = pacman.getX() + pacman.getDirection().getDx();
        int pacYMove = pacman.getY() + pacman.getDirection().getDy();

        if (pacXMove == 0 && pacYMove == 252) {
            pacXMove = 572;
        }
        if (pacXMove == 576 && pacYMove == 252) {
            pacXMove = 4;
        }

        if (map[(pacYMove) / 36][(pacXMove) / 36] != Type.W &&
                map[(pacYMove) / 34][(pacXMove + 34) / 36] != Type.W &&
                map[(pacYMove + 34) / 36][(pacXMove) / 36] != Type.W &&
                map[(pacYMove + 34) / 36][(pacXMove + 34) / 36] != Type.W) {
            switch (map[(pacYMove) / 36][(pacXMove) / 36]) {
                case C:
                    game.addScore(100);
                    break;
                case I:
                    game.addScore(300);
                    pacman.setState(new InvisiblePacman(pacman));
                    pacman.getState().state();
                    break;
                case S:
                    game.addScore(500);
                    pacman.setState(new SuperPovPacman(game, pacman));
                    pacman.getState().state();
                    break;
                case M:
                    game.addScore(1000);
                    game.getPacman().eatMix();
                    break;
                default:
                    break;
            }
            game.gainOneUp(); // Verifie s'il a 5000pts pour ajouter une vie en plus.
            game.getMap().replaceNothing(pacman.getY() / 36, pacman.getX() / 36);
            pacman.setX(pacXMove);
            pacman.setY(pacYMove);
        }

        view.repaint();
    }
}
