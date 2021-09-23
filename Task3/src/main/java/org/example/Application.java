package org.example;

import org.example.controllers.Controller;
import org.example.domain.Building;
import org.example.domain.Elevator;
import org.example.domain.Floor;
import org.example.threads.ElevatorMovementTask;
import org.example.threads.PassengerTransportationTask;
import org.example.util.PassengerDistributor;
import org.example.util.PropertiesReader;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class Application {

    public static void main(String[] args) {

        //get properties
        int floorsNumber = PropertiesReader.getFloorsNumber();
        int passengersNumber = PropertiesReader.getPassengersNumber();
        int elevatorCapacity = PropertiesReader.getElevatorCapacity();

        //create building withe floors and elevator
        Building building = new Building(
                Stream.generate(Floor::new).limit(floorsNumber).toList(),
                new Elevator(floorsNumber, elevatorCapacity),
                passengersNumber);

        //add passenger in floors
        PassengerDistributor.distribute(building, passengersNumber, floorsNumber);

        //start Thread for passenger and elevator
        Controller controller = new Controller(building);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(passengersNumber + 1, passengersNumber + 1,
                1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());

        building.getFloors()
                .forEach(n -> n.getDispatchContainer()
                        .forEach(a -> threadPoolExecutor.submit(new PassengerTransportationTask(a, controller))));
        threadPoolExecutor.submit(new ElevatorMovementTask(building, controller));

        threadPoolExecutor.shutdown();
    }
}
