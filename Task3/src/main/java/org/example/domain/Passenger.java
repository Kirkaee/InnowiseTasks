package org.example.domain;

import org.example.enums.TransportationState;

public class Passenger {

    static int count = 0;

    private int id;
    private int sourceFloor;
    private int destinationFloor;
    private TransportationState transportationState;

    public Passenger(int sourceFloor, int destinationFloor) {
        this.id = ++count;
        this.sourceFloor = sourceFloor;
        this.destinationFloor = destinationFloor;
        this.transportationState = TransportationState.NOT_STARTED;
    }

    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        Passenger.count = count;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSourceFloor() {
        return sourceFloor;
    }

    public void setSourceFloor(int sourceFloor) {
        this.sourceFloor = sourceFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    public void setDestinationFloor(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public TransportationState getTransportationState() {
        return transportationState;
    }

    public void setTransportationState(TransportationState transportationState) {
        this.transportationState = transportationState;
    }

}
