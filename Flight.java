import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class Flight {
    //add seats
    private int id;
    private String airlineName;
    private int capacity;
    private HashMap<Integer, Passenger> passengers;
    private int reservationsCount;
    private Lock lock;

    public int getId() {
        return id;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public Flight(int i, String an, int c) {
        this.id = i;
        this.airlineName = an;
        this.capacity = c;
        this.passengers = new HashMap<>();
        this.reservationsCount = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public HashMap<Integer, Passenger> getPassengers() {
        return passengers;
    }

    public int getReservationsCount() {
        return reservationsCount;
    }

    public void addPassenger(int id, Passenger p) {
        if(passengers.size()<capacity) {
            passengers.putIfAbsent(id, p);
            reservationsCount++;
        }
    }

    public void removePassenger(int id) {
        if(passengers.containsKey(id)) {
            passengers.remove(id);
            reservationsCount--;
        }
    }
    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}