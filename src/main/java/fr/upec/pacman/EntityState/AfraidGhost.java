package fr.upec.pacman.EntityState;

import fr.upec.pacman.Ghost;

import java.awt.*;

public class AfraidGhost implements EntityState {
    private Ghost ghost;

    public AfraidGhost(Ghost ghost) {
        this.ghost = ghost;
    }

    @Override
    public void colorState() {
        ghost.setColor(Color.BLUE);
    }

    @Override
    public void state() {
        colorState();
    }
}
