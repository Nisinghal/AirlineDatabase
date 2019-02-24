import java.util.concurrent.locks.Lock;
import java.util.ArrayList;

public class Passenger {

    private int id;
    private int name;
    private int age;
    private int gender;
    private Lock lock;
    private ArrayList<Flight> flights;

    public Passenger(int i, int n, int a, int g) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.flights = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    void reserve(flight f) {
        f.addPassenger(this.id, this);
        flights.add(f);
    }

    void cancel(flight f) {
        f.removePassenger(this.id);
        flights.remove(flights.lastIndexOf(f));
    }
    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
    //hasReservation? check in flights if flight f is present
}