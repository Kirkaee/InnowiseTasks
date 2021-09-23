package org.example.domain;

import org.example.enums.MovementDirection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;

public class Elevator {

    private int topFloor;
    private int capacity;
    private final List<Passenger> container;
    private int currentFloor;
    private MovementDirection movementDirection;
    private boolean door;

    public Elevator(int topFloor, int capacity) {
        this.topFloor = topFloor;
        this.capacity = capacity;
        this.container = new ArrayList<>();
        this.currentFloor = 1;
        this.movementDirection = MovementDirection.UP;
        this.door = false;
    }

    public int getTopFloor() {
        return topFloor;
    }

    public void setTopFloor(int topFloor) {
        this.topFloor = topFloor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Passenger> getContainer() {
        return container;
    }

    public void setContainer(Passenger passenger) {
        container.add(passenger);
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public MovementDirection getMovementDirection() {
        return movementDirection;
    }

    public void setMovementDirection(MovementDirection movementDirection) {
        this.movementDirection = movementDirection;
    }

    public boolean isDoor() {
        return door;
    }

    public void setDoor(boolean door) {
        this.door = door;
    }

    public void openDoor(Condition elevatorCondition, Condition enterCondition, Condition exitCondition, List<Passenger> floor) {
        door = true;

        if (container.stream().anyMatch(n -> n.getDestinationFloor() == currentFloor)) {
            exitCondition.signalAll();
            sleepElevator(elevatorCondition);
        }
        if (getContainer().size() < capacity & !floor.isEmpty()) {
            enterCondition.signalAll();
            sleepElevator(elevatorCondition);
        }
}

    public void closeDoor() {
        door = false;
    }

    public void sleepElevator(Condition elevatorCondition){
        try {
            elevatorCondition.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
