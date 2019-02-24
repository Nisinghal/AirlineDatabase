import java.util.Random;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection;

public class Transactions {

    public Transactions() {
        System.out.println("\n ----------------------------starting------------------------------------");

        Random random = new Random();
//        int type=1;
        int type = random.nextInt(5)+1;
//        for (int type = 1; type < 6; type++) {
            Flight f1 = AirlineDB.getFlights().get(random.nextInt(5) + 1);
            Flight f2;
            do {
                f2 = AirlineDB.getFlights().get(random.nextInt(5) + 1);
            } while (f1 == f2);
            int passengerID = random.nextInt(10) + 1;
            System.out.print( "F1: " + f1.getAirlineName() + " - F2: " + f2.getAirlineName() + " - Passenger: " + AirlineDB.getPassengers().get(passengerID).getName()+" - TT type: ");
            if (type == 1) {
                System.out.println("Reservation");
                reserve(f1, passengerID);
            } else if (type == 2) {
                System.out.println("Cancellation");
                cancel(f1, passengerID);
            } else if (type == 3) {
                System.out.println("Show My Flights");
                myFlights(passengerID);
            } else if (type == 4) {
                System.out.println("Show Total Reservations");
                totalReservations();
            } else if (type == 5) {
                System.out.println("Transfer");
                transfer(f1, f2, passengerID);
            }
//        }
    }

    //returns the sum total of all reservations on all flights.
    public int totalReservations() {
        int count = 0;
        for (int id : AirlineDB.getFlights().keySet()) {
            count = count + AirlineDB.getFlights().get(id).getReservationsCount();
        }
        System.out.println( count+" total reservations made across the system.");
        return count;
    }

    //transfer passenger i from flight F1 to F2. This transaction should have
    // no impact if the passenger is not found in F1 or there is no room in F2
    public void transfer(Flight f1, Flight f2, int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        if (f1.getPassengers().containsKey(id) && f2.getReservationsCount() < f2.getCapacity() && f2.getPassengers().containsKey(id)==false) {
            p.cancel(f1);//.removePassenger(id);
            p.reserve(f2);//.addPassenger(id, p);
            System.out.println(p.getName()+" transferred from " + f1.getAirlineName()+" to "+f2.getAirlineName()+".");
        }
        else {
            if (f1.getPassengers().containsKey(id) == false)
                System.out.println(p.getName() + " doesn't have any reservation in " + f1.getAirlineName()+".");
            else if (f2.getPassengers().containsKey(id))
                System.out.println(p.getName() + " already has a reservation in " + f2.getAirlineName()+".");
            else if (f2.getReservationsCount() >= f2.getCapacity())
                System.out.println(f2.getAirlineName() + " is fully booked already.");
        }
    }

    // reserve a seat for passenger with id i on flight F, where i > 0
    public void reserve(Flight f, int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        if (f.getPassengers().containsKey(id)) System.out.println(p.getName() + " already has a reservation in " + f.getAirlineName()+".");
        else p.reserve(f);
    }

    // cancel reservation for passenger with id i from flight F.
    public void cancel(Flight f, int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        p.cancel(f);
//        System.out.println(f.getAirlineName()+" cancelled "+p.getName());
    }

    //returns the set of flights on which passenger i has a reservation.
    public Collection<Flight>  myFlights(int id) {
        Passenger p = AirlineDB.getPassengers().get(id);
        System.out.println(p.getName()+" has reservations on "+p.getFlights().size()+" flight(s).");
        for(Flight f:p.getFlights().values()){
            System.out.print(f.getAirlineName()+" ");
        }
        if(p.getFlights().size()!=0) System.out.println();
        return p.getFlights().values();
    }
}