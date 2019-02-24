import java.util.random;

public class Transaction {

    public Transaction() {
        int type = random.nextInt(5);
        Flight f1 = AirlineDB.flights[random.nextInt(5)];
        do {
            Flight f2 = AirlineDB.flights[random.nextInt(5)];
        } while (f1 == f2)
        int passengerID = AirlineDB.passengers[random.nextInt(500)];
        if (type == 1) {
            reserve(f1, passengerID);
        } else (type == 2) {
            cancel(f1, passengerID);
        } else(type == 3) {
            myFlights(passengerID);
        } else(type == 4) {
            totalReservations(f1, passengerID);
        } else(type == 5) {
            transfer(f1, f2, passengerID);
        }
    }

    //returns the sum total of all reservations on all flights.
    public void totalReservations() {
        count = 0;
        for (int id : AirlineDB.flights.keySet()) {
            count = count + AirlineDB.flights[id].getReservationsCount();
        }
        return count;
    }

    //transfer passenger i from flight F1 to F2. This transaction should have
    // no impact if the passenger is not found in F1 or there is no room in F2
    public void transfer(flight f1, flight f2, int id) {
        passenger p = AirlineDB.passengers.get(id);
        if (f1.getPassengers().containsKey(id) && f2.getReservationsCount() < f2.getCapacity()) {
            f1.removePassenger(id);
            f2.addPassenger(id, p);
        }
    }

    // reserve a seat for passenger with id i on flight F, where i > 0
    public void reserve(flight f, int id) {
        passenger p = AirlineDB.passengers.get(id);
        p.reserve(f);
    }

    // cancel reservation for passenger with id i from flight F.
    public void cancel(flight f, int id) {
        passenger p = AirlineDB.passengers.get(id);
        p.cancel(f);
    }

    //returns the set of flights on which passenger i has a reservation.
    public ArrayList<Flight> myFlights(int id) {
        passenger p = AirlineDB.passengers.get(id);
        return p.getFlights();
    }
}