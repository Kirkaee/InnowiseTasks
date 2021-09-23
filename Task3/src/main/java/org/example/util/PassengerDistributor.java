package org.example.util;

import org.example.domain.Building;
import org.example.domain.Passenger;

import java.util.Random;

public class PassengerDistributor {

    private static Random random = new Random();

    public static int getRandomFloor(int floorNumber){
        return random.nextInt(floorNumber) + 1;
    }

    public static void distribute(Building building, int passengersNumber, int floorsNumber) {

        for (int i = 1; i <= passengersNumber; i++) {
            int sourceFloor = getRandomFloor(floorsNumber);

            int destinationFloor = 0;
            while (destinationFloor == sourceFloor | destinationFloor == 0) {
                destinationFloor = getRandomFloor(floorsNumber);
            }

            building.getFloors().get(sourceFloor - 1)
                    .setDispatchContainer(new Passenger(sourceFloor, destinationFloor));
        }
    }
}
