package org.example.threads;

import org.apache.log4j.Logger;
import org.example.controllers.Controller;
import org.example.domain.Passenger;
import org.example.enums.TransportationState;

public class PassengerTransportationTask implements Runnable {

    private static final Logger logger = Logger.getLogger(PassengerTransportationTask.class);

    private Passenger passenger;
    private Controller controller;

    public PassengerTransportationTask(Passenger passenger, Controller controller) {
        this.passenger = passenger;
        this.controller = controller;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        controller.getLock().lock();
        enterTheElevator();
        getOutOfTheElevator();
        controller.getLock().unlock();
    }

    public void enterTheElevator() {
        while ((controller.getBuilding().getElevator().getCurrentFloor() != passenger.getSourceFloor()) |
                !controller.getBuilding().getElevator().isDoor() |
                !(controller.getBuilding().getElevator().getContainer().size() < controller.getBuilding().getElevator().getCapacity())) {
            try {
                controller.getConditionPassenger().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        passenger.setTransportationState(TransportationState.IN_PROGRESS);
        controller.getBuilding().getElevator().setContainer(passenger);
        controller.getBuilding().getFloors().get(passenger.getSourceFloor() - 1).getDispatchContainer().remove(passenger);
        logger.info("BOADING_OF_PASSENGER (" + passenger.getId() + " on floor-" + passenger.getSourceFloor() + ")");
    }

    public void getOutOfTheElevator() {
        while ((controller.getBuilding().getElevator().getCurrentFloor() != passenger.getDestinationFloor()) |
                !controller.getBuilding().getElevator().isDoor() |
                !controller.getBuilding().getElevator().getContainer().contains(passenger)) {
            try {
                controller.getConditionElevatorsPassenger().await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        passenger.setTransportationState(TransportationState.COMPLETED);
        controller.getBuilding().getFloors().get(passenger.getDestinationFloor() - 1).setArrivalContainer(passenger);
        controller.getBuilding().getElevator().getContainer().remove(passenger);
        logger.info("DEBOADING_OF_PASSENGER (" + passenger.getId() + " on floor-" + passenger.getSourceFloor() + ")");
    }
}
