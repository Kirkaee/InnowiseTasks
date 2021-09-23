package org.example.enums;

public enum MovementDirection {
    UP(1),
    DOWN(-1);

    public final int floorIncrement;

    MovementDirection(int floorIncrement) {
        this.floorIncrement = floorIncrement;
    }
}
