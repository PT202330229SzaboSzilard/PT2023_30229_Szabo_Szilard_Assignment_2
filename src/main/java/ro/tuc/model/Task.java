package ro.tuc.model;

import java.util.concurrent.atomic.AtomicInteger;

public class Task {
    private int ID;
    private Integer arrivalTime;
    private int serviceTime;

    public Task(int ID, Integer arrivalTime, int serviceTime) {
        this.ID = ID;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
    }

    public Integer getArrivalTime() {
        return arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "ID=" + ID +
                ", arrivalTime=" + arrivalTime +
                ", serviceTime=" + serviceTime +
                '}';
    }
}
