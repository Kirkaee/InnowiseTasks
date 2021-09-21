package org.example;

import org.example.controllers.Controller;
import org.example.domain.Building;
import org.example.domain.Elevator;
import org.example.domain.Floor;
import org.example.domain.Passenger;
import org.example.threads.ElevatorMovementTask;
import org.example.threads.PassengerTransportationTask;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Application {

    public static void main(String[] args) {

        //get properties
        File file = new File("src/main/resources/config.properties");
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        int passengersNumber = Integer.parseInt(properties.getProperty("passengersNumber"));
        int elevatorCapacity = Integer.parseInt(properties.getProperty("elevatorCapacity"));
        int floorsNumber = Integer.parseInt(properties.getProperty("floorsNumber"));

        //create building withe floors and elevator
        Building building = new Building();
        List<Floor> floors = new ArrayList<>();
        for (int i = 1; i <= floorsNumber; i++) {
            floors.add(new Floor());
        }
        building.setPassengerNumber(passengersNumber);
        building.setFloors(floors);
        building.setElevator(new Elevator(floorsNumber, elevatorCapacity));

        //add passenger in floors
        for (int i = 1; i <= passengersNumber; i++) {
            int sourceFloor = (int) (Math.random() * floorsNumber + 1);
            int destinationFloor = 0;
            while (destinationFloor == sourceFloor | destinationFloor == 0) {
                destinationFloor = (int) (Math.random() * floorsNumber + 1);
            }
            building.getFloors().get(sourceFloor - 1)
                    .setDispatchContainer(new Passenger(sourceFloor, destinationFloor));
        }

        //start Thread for passenger and elevator
        Controller controller = new Controller(building);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(passengersNumber + 1, passengersNumber + 1,
                1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        threadPoolExecutor.submit(new ElevatorMovementTask(building, controller));

        building.getFloors()
                .forEach(n -> n.getDispatchContainer()
                        .forEach(a -> threadPoolExecutor.submit(new PassengerTransportationTask(a, controller))));

        threadPoolExecutor.shutdown();


    }
}
