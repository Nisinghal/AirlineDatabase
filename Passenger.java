import java.util.concurrent.locks.Lock;
import java.util.HashMap;

public class Passenger {

    private int id;
    private String name;
    private int age;
    private int gender;
    private Lock lock;
    private HashMap<Flight, Integer> flights;

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

    public HashMap<Flight,Integer> getFlights() {
        return flights;
    }

    public String getName() {
        return name;
    }

    void reserve(Flight f) {
        f.addPassenger(this.id, this);
        flights.putIfAbsent(f, f.getId());
    }

    void cancel(Flight f) {
        if(flights.size()>0 && flights.containsKey(f)==true){
            for(Flight air:flights.keySet()){
                System.out.println(air.getAirlineName());
            }
            f.removePassenger(this.id);
            flights.remove(f);
        }
    }
    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
    //hasReservation? check in flights if flight f is present
}