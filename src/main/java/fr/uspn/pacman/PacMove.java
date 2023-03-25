package fr.uspn.pacman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class PacMove implements KeyListener {
    private Game game;
    private GameView view;

    public PacMove(Game game, GameView view) {
        this.game = game;
        this.view = view;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        Type[][] map = game.getMap().getMap();
        int pacXMove = game.getPacman().getX() + game.getPacman().getDirection().getDx();
        int pacYMove = game.getPacman().getY() + game.getPacman().getDirection().getDy();
        Direction oldDirection = game.getPacman().getDirection();

        if (pacXMove == 0 && pacYMove == 252) {
            pacXMove = 572;
        }
        if (pacXMove == 576 && pacYMove == 252) {
            pacXMove = 4;
        }
        //In-game
        if (!game.win() || game.getPacman().isAlive()) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    game.getPacman().setDirection(Direction.LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    game.getPacman().setDirection(Direction.RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    oldDirection = game.getPacman().getDirection();
                    game.getPacman().setDirection(Direction.UP);
                    pacXMove = game.getPacman().getX() + game.getPacman().getDirection().getDx();
                    if(pacXMove % 36 < 22 && oldDirection == Direction.LEFT) { 
                        pacXMove -= pacXMove % 36;
                        if(map[(pacYMove - 34) / 36][(pacXMove) / 36] != Type.W){
                            game.getPacman().setX(pacXMove);
                        }
                        else {
                            game.getPacman().setDirection(oldDirection);
                        }
                    } 
                    else if (pacXMove % 36 > 14 && oldDirection == Direction.RIGHT){
                        pacXMove += 36 - (pacXMove % 36);
                        if(map[(pacYMove - 34) / 36][(pacXMove) / 36] != Type.W){
                            game.getPacman().setX(pacXMove);
                        }
                        else {
                            game.getPacman().setDirection(oldDirection);
                        }
                    }
                    else {
                        game.getPacman().setDirection(oldDirection);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    game.getPacman().setDirection(Direction.DOWN);
                    break;
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (game.getPacman().isAlive()) {
                view.setStart(true);
            } else {
                view.getFrame().dispose();
                App.main(null);
            }
            if (game.win()) {
                view.getFrame().dispose();
                App.main(null);
            }
        }

        view.repaint();
    }
}
