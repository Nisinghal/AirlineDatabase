import java.util.random;

public class Transaction {

    void Transaction(){
        type = random.nextInt(5);
        flight f = f; //random
        int pass_id = i; //random
    }
    // reserve a seat for passenger with id i on flight F, where i > 0
    void reserve(flight f, int id) {

    } //or id i or int i

    // cancel reservation for passenger with id i from flight F.
    void cancel(flight f, int id) {
    }  //or id i or int i

    //returns the set of flights on which passenger i has a reservation.
    flight[] my_flights(int id) {
    }  //or id i or int i

    //returns the sum total of all reservations on all flights.
    total_reservations() {
    }  //or id i or int i

    //transfer passenger i from flight F1 to F2. This transaction should have
// no impact if the passenger is not found in F1 or there is no room in F2
    Transfer(flight f1, flight f2, int id) {
    }
}