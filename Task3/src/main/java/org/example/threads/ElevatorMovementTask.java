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
    private final Validator validator;

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
        controller.getLock().lock();

        while (checkNeedToMoveElevator()) {

            elevator.openDoor(controller.getConditionElevator(),
                    controller.getConditionPassenger(),
                    controller.getConditionElevatorsPassenger(),
                    building.getFloors().get(elevator.getCurrentFloor() - 1).getDispatchContainer());
            elevator.closeDoor();

            int floorIncrement = elevator.getMovementDirection().floorIncrement;
            elevator.setCurrentFloor(elevator.getCurrentFloor() + floorIncrement);
            logger.info("MOVING_ELEVATOR (from floor-" + (elevator.getCurrentFloor() - floorIncrement)
                    + " to floor-" + elevator.getCurrentFloor() + ")");

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

    public boolean checkNeedToMoveElevator() {
        return !elevator.getContainer().isEmpty() | !building.getFloors().stream()
                .allMatch(n -> n.getDispatchContainer().isEmpty());
    }
}
