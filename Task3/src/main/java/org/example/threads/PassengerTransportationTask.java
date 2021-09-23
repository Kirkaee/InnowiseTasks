package org.example.threads;

import org.apache.log4j.Logger;
import org.example.controllers.Controller;
import org.example.domain.Passenger;
import org.example.enums.TransportationState;

import java.util.concurrent.locks.Condition;

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
        while (checkCannotEnter()) {
            sleepPassenger(controller.getConditionPassenger());
        }

        passenger.setTransportationState(TransportationState.IN_PROGRESS);
        controller.getBuilding().getElevator().setContainer(passenger);
        controller.getBuilding().getFloors().get(passenger.getSourceFloor() - 1).getDispatchContainer().remove(passenger);
        logger.info("BOADING_OF_PASSENGER (" + passenger.getId() + " on floor-" + passenger.getSourceFloor() + ")");

        if (checkDoesThePassengerNeedToEnter()) {
            controller.getConditionElevator().signalAll();
        }
    }

    public void getOutOfTheElevator() {
        while (checkCannotGetOut()) {
            sleepPassenger(controller.getConditionElevatorsPassenger());
        }
        passenger.setTransportationState(TransportationState.COMPLETED);
        controller.getBuilding().getFloors().get(passenger.getDestinationFloor() - 1).setArrivalContainer(passenger);
        controller.getBuilding().getElevator().getContainer().remove(passenger);
        logger.info("DEBOADING_OF_PASSENGER (" + passenger.getId() + " on floor-" + passenger.getSourceFloor() + ")");
        if (checkDoesThePassengerNeedToGetOut()){
            controller.getConditionElevator().signalAll();
        }
    }

    public boolean checkCannotEnter() {
        return (controller.getBuilding().getElevator().getCurrentFloor() != passenger.getSourceFloor()) |
                !controller.getBuilding().getElevator().isDoor() |
                !(controller.getBuilding().getElevator().getContainer().size() < controller.getBuilding().getElevator().getCapacity());
    }

    public boolean checkCannotGetOut() {
        return (controller.getBuilding().getElevator().getCurrentFloor() != passenger.getDestinationFloor()) |
                !controller.getBuilding().getElevator().isDoor() |
                !controller.getBuilding().getElevator().getContainer().contains(passenger);
    }

    public boolean checkDoesThePassengerNeedToGetOut(){
        return controller.getBuilding().getElevator().getContainer().stream()
                .noneMatch(n -> n.getDestinationFloor() == controller.getBuilding().getElevator().getCurrentFloor());
    }

    public boolean checkDoesThePassengerNeedToEnter(){
        return controller.getBuilding().getElevator().getContainer().size() == controller.getBuilding().getElevator().getCapacity() |
                controller.getBuilding().getFloors().get(controller.getBuilding().getElevator().getCurrentFloor()-1)
                        .getDispatchContainer().stream()
                        .noneMatch(n -> n.getSourceFloor() == controller.getBuilding().getElevator().getCurrentFloor());
    }

    public void sleepPassenger(Condition condition){
        try {
            condition.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
