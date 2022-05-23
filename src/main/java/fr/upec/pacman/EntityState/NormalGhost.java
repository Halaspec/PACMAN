package fr.upec.pacman.EntityState;

import fr.upec.pacman.Ghost;

import java.awt.*;

public class NormalGhost implements EntityState {
    private Color color;
    private Ghost ghost;

    public NormalGhost() {
    }

    public NormalGhost(Color color, Ghost ghost) {
        this.color = color;
        this.ghost = ghost;
    }

    @Override
    public void colorState() {
        ghost.setColor(color);
    }

    @Override
    public void state() {
        colorState();
    }
}
