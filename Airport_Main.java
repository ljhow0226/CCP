import java.util.Random;

public class Airport_Main {
    public static void main(String[] args) {
        //new Runway
        Runway runway = new Runway(1);

        //new Gate
        Gate gate1 = new Gate(1);
        Gate gate2 = new Gate(2);

        //new ATC
        for (int i = 0; i <=5 ; i++) {
            AirTrafficController airTrafficController = new AirTrafficController(runway,gate1,gate2); //ATC monitors runway and gates
            Thread threadATC = new Thread(airTrafficController);
            threadATC.setDaemon(true);
            threadATC.start();
        }

        //new refueling truck
        RefuelingTruck refuelingTruck = new RefuelingTruck();

        for (int i = 1; i <= 6; i++) {
            Plane plane = new Plane(i,50,runway,gate1,gate2,refuelingTruck);
            int passengerFlagCount = (50*(i-1));
            for (int j = 1+(passengerFlagCount); j <= 50*i; j++) {
                Passenger passenger = new Passenger(j);
                plane.getPassengers().add(passenger);
            }
            //passenger added complete
            //thread Plane start
            Thread threadPlane = new Thread(plane);
            threadPlane.start();
            Random rand = new Random();
            try {
                Thread.sleep(rand.nextInt(3000));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
