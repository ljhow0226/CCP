import java.util.concurrent.locks.ReentrantLock;

public class Gate extends ReentrantLock{
    private int gateID;
    private Plane currentAssignedPlane;

    public Gate(int gateID) {
        this.gateID = gateID;
    }

    public int getGateID() {
        return gateID;
    }

    public void setGateID(int gateID) {
        this.gateID = gateID;
    }

    public Plane getCurrentAssignedPlane() {
        return currentAssignedPlane;
    }

    public void setCurrentAssignedPlane(Plane currentAssignedPlane) {
        this.currentAssignedPlane = currentAssignedPlane;
    }

    public synchronized void dockPlane(Plane plane) {
        currentAssignedPlane = plane;
    }

    public synchronized void undockPlane() {
        currentAssignedPlane = null;
    }

    public synchronized boolean isAvailable() {
        return currentAssignedPlane == null;
    }

}

