package org.example.threads;

import org.apache.log4j.Logger;
import org.example.controllers.Controller;
import org.example.domain.Building;
import org.example.domain.Elevator;
import org.example.enums.MovementDirection;
import org.example.validator.Validator;

public class ElevatorMovementTask implements Runnable {

    private static final Logger logger = Logger.getLogger(ElevatorMovementTask.class);

    private final Building building;
    private Elevator elevator;
    private Controller controller;
    private Validator validator;

    public ElevatorMovementTask(Building building, Controller controller) {
        this.building = building;
        this.elevator = building.getElevator();
        this.controller = controller;
        this.validator = new Validator();
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        logger.info("STARTING_TRANSPORTATION");
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        controller.getLock().lock();

        while (!elevator.getContainer().isEmpty() || !building.getFloors().stream()
                .allMatch(n -> n.getDispatchContainer().isEmpty())) {

            if (elevator.getMovementDirection() == MovementDirection.UP) {
                elevator.openDoor(controller.getConditionElevator(), controller.getConditionPassenger(), controller.getConditionElevatorsPassenger());
                elevator.closeDoor();
                elevator.setCurrentFloor(elevator.getCurrentFloor() + 1);
                logger.info("MOVING_ELEVATOR (from floor-" + (elevator.getCurrentFloor() - 1)
                        + " to floor-" + elevator.getCurrentFloor() + ")");
            } else {
                elevator.openDoor(controller.getConditionElevator(), controller.getConditionPassenger(), controller.getConditionElevatorsPassenger());
                elevator.closeDoor();
                elevator.setCurrentFloor(elevator.getCurrentFloor() - 1);
                logger.info("MOVING_ELEVATOR (from floor-" + (elevator.getCurrentFloor() + 1)
                        + " to floor-" + elevator.getCurrentFloor() + ")");
            }

            if (elevator.getCurrentFloor() == elevator.getTopFloor()) {
                elevator.setMovementDirection(MovementDirection.DOWN);
            }
            if (elevator.getCurrentFloor() == 1) {
                elevator.setMovementDirection(MovementDirection.UP);
            }
        }
        logger.info("COMPLETION_TRANSPORTATION");
        controller.getLock().unlock();

        validator.checkAll(building);

    }
}
