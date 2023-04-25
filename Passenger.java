public class Passenger{
    private int passengerID;
    private Plane plane;

    public Passenger(int passengerID) {
        this.passengerID = passengerID;
    }

    public int getPassengerID() {
        return passengerID;
    }

    public void setPassengerID(int passengerID) {
        this.passengerID = passengerID;
    }

    public Plane getPlane() {
        return plane;
    }

    public void setPlane(Plane plane) {
        this.plane = plane;
    }

}

