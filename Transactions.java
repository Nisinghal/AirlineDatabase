import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;

public class Transactions {

    public Transactions() {
        Random random = new Random();
        int type = random.nextInt(5)+1;

        Flight f1 = AirlineDB.getFlights().get(random.nextInt(5)+1);
        Flight f2;
        do {
            f2 = AirlineDB.getFlights().get(random.nextInt(5)+1) ;
        } while (f1 == f2);
        int passengerID = random.nextInt(10)+1;
        System.out.println(type+" "+f1.getAirlineName()+" "+f2.getAirlineName()+" "+AirlineDB.getPassengers().get(passengerID).getName());
        if (type == 1) {
            reserve(f1, passengerID);
        } else if (type == 2) {
            cancel(f1, passengerID);
        } else if (type == 3) {
            myFlights(passengerID);
        } else if (type == 4) {
            System.out.println(totalReservations());
        } else if (type == 5) {
            transfer(f1, f2, passengerID);
        }
    }

    //returns the sum total of all reservations on all flights.
    public int totalReservations() {
        int count = 0;
        for (int id : AirlineDB.getFlights().keySet()) {
            count = count + AirlineDB.getFlights().get(id).getReservationsCount();
        }
//        System.out.println(count+" Total Res");
        return count;
    }

    //transfer passenger i from flight F1 to F2. This transaction should have
    // no impact if the passenger is not found in F1 or there is no room in F2
    public void transfer(Flight f1, Flight f2, int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        if (f1.getPassengers().containsKey(id) && f2.getReservationsCount() < f2.getCapacity()) {
            f1.removePassenger(id);
            f2.addPassenger(id, p);
        }
        System.out.println(f1.getAirlineName()+" "+f2.getAirlineName()+" "+p.getName());
    }

    // reserve a seat for passenger with id i on flight F, where i > 0
    public void reserve(Flight f, int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        p.reserve(f);
        System.out.println(f.getAirlineName()+" "+p.getName());
    }

    // cancel reservation for passenger with id i from flight F.
    public void cancel(Flight f, int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        p.cancel(f);
        System.out.println(f.getAirlineName()+" "+p.getName());
    }

    //returns the set of flights on which passenger i has a reservation.
    public HashMap<Flight,Integer>  myFlights(int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        System.out.println(p.getName());
        return p.getFlights();
    }
}