import javafx.util.converter.LocalTimeStringConverter;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Plane implements Runnable{
    private int planeID;
    private int capacity;
    private List<Passenger> passengers;
    private Runway runway;
    private boolean isDocking = false;
    private ReentrantLock refuelingTruck;
    private LocalTime startTime = LocalTime.now();
    private LocalTime endTime;
    private LocalTime startWaitingTime;
    private LocalTime endWaitingTime;
    private Gate gate1;
    private Gate gate2;
    private boolean EMERGENCY = false;

    public Plane(int planeID, int capacity, Runway runway, Gate gate1, Gate gate2, RefuelingTruck refuelingTruck) {
        this.planeID = planeID;
        this.capacity = capacity;
        this.passengers = new ArrayList<>();
        this.runway = runway;
        this.gate1 = gate1;
        this.gate2 = gate2;
        this.refuelingTruck = refuelingTruck;
        if (this.planeID == 5) {
            this.EMERGENCY = true;
        }
    }

    public LocalTime getStartWaitingTime() {
        return startWaitingTime;
    }

    public void setStartWaitingTime(LocalTime startWaitingTime) {
        this.startWaitingTime = startWaitingTime;
    }

    public LocalTime getEndWaitingTime() {
        return endWaitingTime;
    }

    public void setEndWaitingTime(LocalTime endWaitingTime) {
        this.endWaitingTime = endWaitingTime;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Gate returnDockGate(Gate gate) {
        return gate;
    }

    public boolean isDocking() {
        return isDocking;
    }

    public void setDocking(boolean docking) {
        isDocking = docking;
    }

    public int getPlaneID() {
        return planeID;
    }

    public void setPlaneID(int planeID) {
        this.planeID = planeID;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public Runway getRunway() {
        return runway;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
    }

    public void sendLandingRequest() throws InterruptedException {
        if (this.EMERGENCY) {
            System.out.println("Plane " + this.planeID + ": Request for EMERGENCY LANDING");
            boolean successLanding = runway.offerFirst(this);
            if (!successLanding) {
                runway.putFirst(this);
            }
            return;
        }
        System.out.println("Plane " + this.planeID + ": Request for landing");
        Thread.sleep(100);
        boolean successLanding = runway.offer(this);
        if (!successLanding) {
            runway.put(this);
        }
    }

    public void sendDepartureRequest() throws InterruptedException {
        System.out.println("Plane " + this.planeID + ": Request for departure");
        Thread.sleep(100);
        boolean successDeparture = runway.offer(this);
        if (!successDeparture) {
            runway.put(this);
        }
    }

    public void unloadPassenger() throws InterruptedException {
        System.out.println("Plane " + this.planeID + ": Start Unload Passenger");
        Thread.sleep(100);
        while (!passengers.isEmpty()) {
            Passenger passenger = passengers.remove(0);
            System.out.println("Plane " + this.planeID + ": Unload Passenger " + passenger.getPassengerID());
            Thread.sleep(20);
        }
        System.out.println("Plane " + this.planeID + ": Unload Passenger complete");
        Thread.sleep(100);
    }

    public void performRoutine() throws InterruptedException {
        System.out.println("Plane " + this.planeID + ": Unload baggage");
        Thread.sleep(50);
        System.out.println("Plane " + this.planeID + ": Unload baggage - Finished");
        Thread.sleep(50);
        if (refuelingTruck.tryLock()) {
            System.out.println("Plane " + this.planeID + ": Refueling");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Refueling - Finished");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Doing maintenance and cleaning");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Doing maintenance and cleaning - Finished");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Restocking catering supplies");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Restocking catering supplies - Finished");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Reloading baggage");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Reloading baggage - Finished");
            refuelingTruck.unlock();
        } else {
            System.out.println("Plane " + this.planeID + ": Refueling Truck is in used, maintenance and cleaning first");
            System.out.println("Plane " + this.planeID + ": Doing maintenance and cleaning");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Doing maintenance and cleaning - Finished");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Restocking catering supplies");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Restocking catering supplies - Finished");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Refueling");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Refueling - Finished");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Reloading baggage");
            Thread.sleep(200);
            System.out.println("Plane " + this.planeID + ": Reloading baggage - Finished");
        }

    }

    public void loadPassenger() throws InterruptedException {
        System.out.println("Plane " + this.planeID + ": Start Load new Passenger");
        for (int i = 301+(50*(this.planeID-1)); i <= (300+(50*this.planeID)); i++) {
            Passenger passenger = new Passenger(i);
            this.getPassengers().add(passenger);
            System.out.println("Plane " + this.planeID + ": Load new Passenger " + passenger.getPassengerID());
            Thread.sleep(20);
        }
        System.out.println("Plane " + this.planeID + ": Load new Passenger complete");
    }

    @Override
    public void run() {
        //send landing request
        if (runway.getLandingLock().tryLock()) {
            try {
                //Plane send request and signal ATC
                this.sendLandingRequest();
                runway.getWaitPlaneSendLandingRequest().signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                runway.getLandingLock().unlock();
            }
        } else {
            try {
                while (!runway.getLandingLock().tryLock()) {
                    Thread.sleep(10);
                }
                try {
                    this.sendLandingRequest();
                    runway.getWaitPlaneSendLandingRequest().signal();
                } finally {
                    runway.getLandingLock().unlock();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        //while isDocking is false, wait
        while (!isDocking) {
            try {
                runway.getLandingLock().lock();
                //Plane await for docking process to be completed
                runway.getWaitPlaneDocking().await();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                runway.getLandingLock().unlock();
            }
        }

        //Plane wake up and unload passenger and then perform routine before departure and then load new passenger
        try {
            unloadPassenger();
            performRoutine();
            loadPassenger();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (runway.getDepartingLock().tryLock()) {
            try {
                //Plane send departure request and signal ATC
                this.sendDepartureRequest();
                runway.getWaitPlaneSendDepartingRequest().signal();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                runway.getDepartingLock().unlock();
            }
        } else {
            try {
                while (!runway.getDepartingLock().tryLock()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                try {
                    this.sendDepartureRequest();
                    runway.getWaitPlaneSendDepartingRequest().signal();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    runway.getDepartingLock().unlock();
                }
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            runway.getDepartingLock().lock();
            runway.getWaitCircleQueue().signal();
            if (gate1.getCurrentAssignedPlane() == this) {
                gate1.setCurrentAssignedPlane(null);
            } else if (gate2.getCurrentAssignedPlane() == this) {
                gate2.setCurrentAssignedPlane(null);
            }
        } finally {
            runway.getDepartingLock().unlock();
        }

        //this if-else code block is used to corporate with daemon ATC thread
        //a bit of seconds needed to make sure ATC print out statistics at the end
        if (this.planeID == 6 && gate1.isAvailable() && gate2.isAvailable()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //sometimes plane 5 goes last
        } else if (this.planeID == 5 && gate1.isAvailable() && gate2.isAvailable()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
