import java.util.concurrent.locks.Lock;
import java.util.HashMap;

public class AirlineDB {

    private HashMap<int, Passenger> passengers = new HashMap<>();
    private HashMap<int, Flight> flights = new HashMap<>();
    private Lock lock;

    public AirlineDB() {
    }

    public HashMap<int, Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(HashMap<int, Passenger> passengers) {

    }

    public HashMap<int, Flight> getFlights() {
        return flights;
    }

    public void setFlights(HashMap<int, Flight> flights) {

    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}