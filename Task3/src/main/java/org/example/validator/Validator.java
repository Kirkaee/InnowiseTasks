package org.example.validator;

import org.apache.log4j.Logger;
import org.example.domain.Building;
import org.example.enums.TransportationState;

import java.util.stream.Stream;

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
        Stream.iterate(0, i -> i < building.getElevator().getTopFloor(), i -> i + 1)
                .forEach(integer -> {
                    if (building.getFloors().get(integer).getArrivalContainer()
                            .stream().allMatch(passenger ->
                                    passenger.getTransportationState().equals(TransportationState.COMPLETED))) {
                        logger.info("Passengers from the floor number " + (integer + 1) + " completed.");
                    }
                });
    }

    public void isDestinationFloor(Building building) {
        Stream.iterate(0, i -> i < building.getElevator().getTopFloor(), i -> i + 1)
                .forEach(integer -> {
                    if (building.getFloors().get(integer).getArrivalContainer()
                            .stream().allMatch(passenger ->
                                    passenger.getDestinationFloor() == integer + 1)) {
                        logger.info("Passengers on their floor " + (integer + 1) + ".");
                    }
                });
    }

    public void differenceBetweenInitialAndTransported(Building building) {
        long count = Stream.iterate(0, i -> i < building.getElevator().getTopFloor(), i -> i + 1)
                .mapToLong(integer -> building.getFloors().get(integer).getArrivalContainer().size()).sum();
        if (count == building.getPassengerNumber()) {
            logger.info("Passengers number = passenger in arrival containers.");
        }
    }
}
