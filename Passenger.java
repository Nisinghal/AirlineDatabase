import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.HashMap;

public class Passenger {

    private int id;
    private String name;
    private int age;
    private int gender;
    private Lock lock = new ReentrantLock();
    private HashMap<Integer, Flight> flights;

    public Passenger(int i, String  n, int a, int g) {
        this.id = i;
        this.name = n;
        this.age = a;
        this.gender = g;
        this.flights = new HashMap<>();
    }

    public int getId() {
        return id;
    }

    public HashMap<Integer, Flight> getFlights() {
        return flights;
    }

    public String getName() {
        return name;
    }

    void reserve(Flight f) {
        int reserved=0;
        System.out.println("Reserving...");
        reserved = f.addPassenger(this.id, this);
        if(reserved==1) {
            flights.putIfAbsent(f.getId(), f);
            System.out.println(name + " got a reservation in " + f.getAirlineName()+".");
        }
    }


    void cancel(Flight f) {
        if(flights.size()>0 && flights.containsValue(f)==true){
            System.out.println("Cancelling...");
            f.removePassenger(this.id);
            flights.remove(f.getId());
            System.out.println(name+" cancelled reservation in "+f.getAirlineName());
        }
        else System.out.println("Passenger not found.");
    }
    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
    //hasReservation? check in flights if flight f is present
}