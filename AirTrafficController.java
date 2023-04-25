import java.time.LocalTime;

public class AirTrafficController implements Runnable {
    private Runway runway;
    private Gate gate1;
    private Gate gate2;
    private final String name = "ATC";
    private int plane3WaitingTime;
    private int plane4WaitingTime;
    private int plane5WaitingTime;
    private int plane6WaitingTime;

    public AirTrafficController(Runway runway, Gate gate1, Gate gate2) {
        this.runway = runway;
        this.gate1 = gate1;
        this.gate2 = gate2;
    }

    public Runway getRunway() {
        return runway;
    }

    public void setRunway(Runway runway) {
        this.runway = runway;
    }

    public Gate getGate1() {
        return gate1;
    }

    public void setGate1(Gate gate1) {
        this.gate1 = gate1;
    }

    public Gate getGate2() {
        return gate2;
    }

    public void setGate2(Gate gate2) {
        this.gate2 = gate2;
    }

    public String getName() {
        return name;
    }

    public synchronized void managePlaneLanding(Plane plane) throws InterruptedException {
        System.out.println("ATC: Plane " + plane.getPlaneID() + " You are cleared to land on runway");
        plane.setEndWaitingTime(LocalTime.now());
//        if (plane != null) {
//            if (plane.getPlaneID() == 3) {
//                plane3WaitingTime = plane.getEndWaitingTime().toSecondOfDay() - plane.getStartWaitingTime().toSecondOfDay();
//            } else if (plane.getPlaneID() == 4) {
//                plane4WaitingTime = plane.getEndWaitingTime().toSecondOfDay() - plane.getStartWaitingTime().toSecondOfDay();
//            } else if (plane.getPlaneID() == 5) {
//                plane5WaitingTime = plane.getEndWaitingTime().toSecondOfDay() - plane.getStartWaitingTime().toSecondOfDay();
//            } else if (plane.getPlaneID() == 6) {
//                plane6WaitingTime = plane.getEndWaitingTime().toSecondOfDay() - plane.getStartWaitingTime().toSecondOfDay();
//            }
//        }
        if (gate1.isAvailable()) {
            gate1.setCurrentAssignedPlane(plane);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Please proceed to the runway and prepare for landing");
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Please head to Gate " + gate1.getGateID());
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Is using the runway");
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Has left the runway");
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Has docked successfully at Gate " + gate1.getGateID());
            gate1.dockPlane(plane);
            plane.setDocking(true);


        } else if (gate2.isAvailable()) {
            gate2.setCurrentAssignedPlane(plane);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Please proceed to the runway and prepare for landing");
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Please head to Gate " + gate2.getGateID());
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Is using the runway");
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Has left the runway");
            Thread.sleep(100);
            System.out.println("ATC: Plane " + plane.getPlaneID() + " Has docked successfully at Gate " + gate2.getGateID());
            gate2.dockPlane(plane);
            plane.setDocking(true);
        }
    }

    public synchronized void managePlaneDeparting(Plane plane) throws InterruptedException {
        System.out.println("ATC: Plane " + plane.getPlaneID() + " You are cleared to land on runway to departure");
        System.out.println("ATC: Plane " + plane.getPlaneID() + " Is using the runway");
        Thread.sleep(100);
        System.out.println("ATC: Plane " + plane.getPlaneID() + " Has left the runway");
        plane.setEndTime(LocalTime.now());
        int lifeSpan = plane.getEndTime().toSecondOfDay() - plane.getStartTime().toSecondOfDay();
        System.out.println("(ATC: Plane " + plane.getPlaneID() + " From landing to departure used time: " + lifeSpan + "s)");
        plane.setDocking(false);
    }

    public void printStatistics(Plane plane) {
        if (plane.getPlaneID() == 6 && gate1.isAvailable() && gate2.isAvailable()) {
            //print statistics
            System.out.println();
            System.out.println("AIRPORT STATUS");
            System.out.println("-----------------------");
            if (gate1.isAvailable()) {
                System.out.println("GATE 1: CLEAR");
            } else {
                System.out.println("GATE 1 is busy");
            }
            if (gate2.isAvailable()) {
                System.out.println("GATE 2: CLEAR");
            } else {
                System.out.println("GATE 2 is busy");
            }
            System.out.println("-----------------------");
            System.out.println();
            System.out.println("STATISTICS");
            System.out.println("-----------------------");
            System.out.println("NO OF PLANES: 6");
            System.out.println("NO OF PASSENGERS DISEMBARKED: 300");
            System.out.println("NO OF PASSENGERS BOARDED: 300");
            int max = Math.max(Math.max(Math.max(plane3WaitingTime,plane4WaitingTime),plane5WaitingTime),plane6WaitingTime);
            int min = Math.min(Math.min(Math.min(plane3WaitingTime,plane4WaitingTime),plane5WaitingTime),plane6WaitingTime);
            int avg = (plane3WaitingTime+plane4WaitingTime+plane5WaitingTime+plane6WaitingTime)/4;
            System.out.println("MINIMUM WAITING TIME: " + min + "s");
            System.out.println("MAXIMUM WAITING TIME: " + max + "s");
            System.out.println("AVERAGE WAITING TIME: " + avg + "s");
            System.out.println("-----------------------");
            //sometimes plane 5 goes last
        } else if (plane.getPlaneID() == 5 && gate1.isAvailable() && gate2.isAvailable()) {
            //print statistics
            System.out.println();
            System.out.println("AIRPORT STATUS");
            System.out.println("-----------------------");
            if (gate1.isAvailable()) {
                System.out.println("GATE 1: CLEAR");
            } else {
                System.out.println("GATE 1 is busy");
            }
            if (gate2.isAvailable()) {
                System.out.println("GATE 2: CLEAR");
            } else {
                System.out.println("GATE 2 is busy");
            }
            System.out.println("-----------------------");
            System.out.println();
            System.out.println("STATISTICS");
            System.out.println("-----------------------");
            System.out.println("NO OF PLANES: 6");
            System.out.println("NO OF PASSENGERS DISEMBARKED: 300");
            System.out.println("NO OF PASSENGERS BOARDED: 300");
            int max = Math.max(Math.max(Math.max(plane3WaitingTime,plane4WaitingTime),plane5WaitingTime),plane6WaitingTime);
            int min = Math.min(Math.min(Math.min(plane3WaitingTime,plane4WaitingTime),plane5WaitingTime),plane6WaitingTime);
            int avg = (plane3WaitingTime+plane4WaitingTime+plane5WaitingTime+plane6WaitingTime)/4;
            System.out.println("MINIMUM WAITING TIME: " + min + "s");
            System.out.println("MAXIMUM WAITING TIME: " + max + "s");
            System.out.println("AVERAGE WAITING TIME: " + avg + "s");
            System.out.println("-----------------------");
        }
    }



    @Override
    public void run() {
        while (true) {
            try {
                runway.getLandingLock().lock();
                while (runway.isEmpty()) {
                    runway.getWaitPlaneSendLandingRequest().await();
                }
                //Although the plane sends a request to ATC
                //ATC cannot accept the request as the Gates are busy
                //need to wait until the gates are cleared
                while ((!gate1.isAvailable()) && (!gate2.isAvailable())) {
                    Plane plane = runway.take();
                    runway.getDepartingLock().lock();
                    try {
                        System.out.println("ATC: Plane " + plane.getPlaneID() + " Please join circle queue and wait for further instructions");
                        //start counting waiting time
                        plane.setStartWaitingTime(LocalTime.now());
                        runway.getWaitCircleQueue().await();
                    } finally {
                        runway.add(plane);
                        runway.getDepartingLock().unlock();
                    }
                }
                //ATC wakes up and manage plane landing
                Plane plane = runway.take();
                this.managePlaneLanding(plane); //plane ended up docking at dock gate and runway is cleared
                runway.getWaitPlaneDocking().signal(); //Signal plane to unload passenger
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                runway.getLandingLock().unlock();
            }

            try {
                runway.getDepartingLock().lock();
                while (runway.isEmpty()) {
                    runway.getWaitPlaneSendDepartingRequest().await();
                }
                //ATC wakes up and manage plane departing
                Plane plane = runway.take();
                this.managePlaneDeparting(plane);
                printStatistics(plane);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                runway.getDepartingLock().unlock();
            }
        }
    }
}
