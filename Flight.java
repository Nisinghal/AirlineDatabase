import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class Flight {
    //add seats
    private int id;
    private String airlineName;
    private int capacity;
    private HashMap<Passenger> passengers;
    private int reservationsCount;
    private Lock lock;

    public Flight(int i, int an, int c) {
        this.id = i;
        this.airlineName = an;
        this.capacity = c;
        this.passengers = new HashMap<>();
        this.reservationsCount = 0;
    }

    public int getCapacity() {
        return capacity;
    }

    public HashMap<Passenger> getPassengers() {
        return passengers;
    }

    public int getReservationsCount() {
        return reservationsCount;
    }

    public addPassenger(int id, passenger p) {
        if(passengers.size()<capacity) {
            passengers.putIfAbsent(id, p);
            reservationsCount++;
        }
    }

    public removePassenger(int id) {
        passengers.remove(id);
        reservationsCount--;
    }
    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}