package org.example.validator;

import org.apache.log4j.Logger;
import org.example.domain.Building;
import org.example.enums.TransportationState;

public class Validator {

    private static final Logger logger = Logger.getLogger(Validator.class);

    public void checkAll(Building building) {
        isEmptyDispatchContainer(building);
        isEmptyElevatorContainer(building);
        isCompletedPassenger(building);
        isDestinationFloor(building);
        differenceBetweenInitialAndTransported(building);
    }

    public void isEmptyDispatchContainer(Building building) {
        if (building.getFloors().stream()
                .allMatch(a -> a.getDispatchContainer().isEmpty())) {
            logger.info("Dispatch container is empty");
        }
    }

    public void isEmptyElevatorContainer(Building building) {
        if (building.getElevator().getContainer().isEmpty()) {
            logger.info("Elevator container is empty");
        }
    }

    public void isCompletedPassenger(Building building) {
        for (int i = 0; i < building.getElevator().getTopFloor(); i++) {
            if (building.getFloors().get(i).getArrivalContainer().stream()
                    .allMatch(a -> a.getTransportationState() == TransportationState.COMPLETED)) {
                logger.info("Passengers from the floor number " + (i + 1) + " completed.");
            }
        }
    }

    public void isDestinationFloor(Building building) {
        for (int i = 0; i < building.getElevator().getTopFloor(); i++) {
            int finalI = i;
            if (building.getFloors().get(i).getArrivalContainer().stream().allMatch(n -> n.getDestinationFloor() == (finalI + 1))) {
                logger.info("Passengers on their floor " + (i + 1) + ".");
            }
        }
    }

    public void differenceBetweenInitialAndTransported(Building building) {
        long count = 0;
        for (int i = 0; i < building.getElevator().getTopFloor(); i++) {
            count += building.getFloors().get(i).getArrivalContainer().size();
        }
        if (count == building.getPassengerNumber()) {
            logger.info("Passengers number = passenger in arrival containers.");
        }
    }
}
