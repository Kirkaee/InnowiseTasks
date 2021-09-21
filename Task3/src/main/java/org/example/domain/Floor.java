package org.example.domain;

import java.util.ArrayList;
import java.util.List;

public class Floor {

    private List<Passenger> dispatchContainer;
    private List<Passenger> arrivalContainer;

    public Floor() {
        this.dispatchContainer = new ArrayList<>();
        this.arrivalContainer = new ArrayList<>();
    }

    public List<Passenger> getDispatchContainer() {
        return dispatchContainer;
    }

    public void setDispatchContainer(Passenger passenger) {
        dispatchContainer.add(passenger);
    }

    public List<Passenger> getArrivalContainer() {
        return arrivalContainer;
    }

    public void setArrivalContainer(Passenger passenger) {
        arrivalContainer.add(passenger);
    }
}
