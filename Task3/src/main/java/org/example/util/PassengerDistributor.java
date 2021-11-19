package org.example.util;

import org.example.domain.Building;
import org.example.domain.Passenger;

import java.util.Random;
import java.util.stream.Stream;

public class PassengerDistributor {

    private static Random random = new Random();

    public static int getRandomFloor(int floorNumber) {
        return random.nextInt(floorNumber) + 1;
    }

    public static int getDestinationFloor(int sourceFloor, int floorsNumber) {
        return Stream.iterate(1, i -> i <= floorsNumber, i -> i + 1)
                .filter(i -> i != sourceFloor).toList().get(getRandomFloor(floorsNumber - 2));
    }

    public static void distribute(Building building, int passengersNumber, int floorsNumber) {
        Stream.iterate(1, i -> i <= passengersNumber, i -> i + 1)
                .forEach(integer -> {
                    int sourceFloor = getRandomFloor(floorsNumber);
                    building.getFloors().get(sourceFloor - 1)
                            .setDispatchContainer(new Passenger(sourceFloor,
                                    getDestinationFloor(sourceFloor, floorsNumber)));
                });
    }
}
