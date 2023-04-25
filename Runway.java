import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Runway extends LinkedBlockingDeque<Plane> {
    private ReentrantLock LandingLock = new ReentrantLock();
    private Condition waitPlaneSendLandingRequest = LandingLock.newCondition();
    private Condition waitPlaneDocking = LandingLock.newCondition();

    private ReentrantLock DepartingLock = new ReentrantLock();
    private Condition waitPlaneSendDepartingRequest = DepartingLock.newCondition();
    private Condition waitCircleQueue = DepartingLock.newCondition();

    public Runway(int capacity) {
        super(capacity);
    }

    public ReentrantLock getLandingLock() {
        return LandingLock;
    }

    public void setLandingLock(ReentrantLock landingLock) {
        LandingLock = landingLock;
    }

    public Condition getWaitCircleQueue() {
        return waitCircleQueue;
    }

    public void setWaitCircleQueue(Condition waitCircleQueue) {
        this.waitCircleQueue = waitCircleQueue;
    }

    public Condition getWaitPlaneSendDepartingRequest() {
        return waitPlaneSendDepartingRequest;
    }

    public void setWaitPlaneSendDepartingRequest(Condition waitPlaneSendDepartingRequest) {
        this.waitPlaneSendDepartingRequest = waitPlaneSendDepartingRequest;
    }

    public ReentrantLock getDepartingLock() {
        return DepartingLock;
    }

    public void setDepartingLock(ReentrantLock departingLock) {
        DepartingLock = departingLock;
    }

    public Condition getWaitPlaneDocking() {
        return waitPlaneDocking;
    }

    public void setWaitPlaneDocking(Condition waitPlaneDocking) {
        this.waitPlaneDocking = waitPlaneDocking;
    }

    public Condition getWaitPlaneSendLandingRequest() {
        return waitPlaneSendLandingRequest;
    }

    public void setWaitPlaneSendLandingRequest(Condition waitPlaneSendLandingRequest) {
        this.waitPlaneSendLandingRequest = waitPlaneSendLandingRequest;
    }

}
