package org.example.controllers;

import org.example.domain.Building;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controller {

    private Building building;
    private final Lock lock = new ReentrantLock();
    private final Condition conditionElevator = lock.newCondition();
    private final Condition conditionPassenger = lock.newCondition();
    private final Condition conditionElevatorsPassenger = lock.newCondition();

    public Controller(Building building) {
        this.building = building;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Lock getLock() {
        return lock;
    }

    public Condition getConditionElevator() {
        return conditionElevator;
    }

    public Condition getConditionPassenger() {
        return conditionPassenger;
    }

    public Condition getConditionElevatorsPassenger() {
        return conditionElevatorsPassenger;
    }
}
