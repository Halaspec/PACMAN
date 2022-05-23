package fr.upec.pacman;

import fr.upec.pacman.EntityState.NormalGhost;

import java.awt.*;

public class Ghost extends Entity {
    private final NormalGhost normalState;

    public Ghost(int x, int y, Color color) {
        super(x, y, Direction.random(), color, new NormalGhost());
        this.normalState = new NormalGhost(color, this);
        setState(this.normalState);
    }

    public NormalGhost getNormalState() {
        return normalState;
    }
}
