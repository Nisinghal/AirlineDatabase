import java.util.concurrent.locks.Lock;
import java.util.HashMap;

public class AirlineDB {

    private HashMap<int, Passenger> passengers = new HashMap<>();
    private HashMap<int, Flight> flights = new HashMap<>();
    private Lock lock;

    public AirlineDB() {
        flights = new HashMap<>();
        passengers = new HashMap<>();
        setFlights();
        setPassengers();
    }

    public HashMap<int, Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(HashMap<int, Passenger> passengers) {
        String fileName = "passengers.txt";
        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
            Object[] line;
            do {
                line = fileReader.readLine().split(",");
                passengers.add(new Passenger((int) line[0],(String)line[1],(int) line[2],(char)line[3]));
            }while (line != null)
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<int, Flight> getFlights() {
        return flights;
    }

    public void setFlights(HashMap<int, Flight> flights) {
        String fileName = "flights.txt";
        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
            Object[] line;
            do {
                line = fileReader.readLine().split(",");
                flights.add(new Flight((int) line[0], (String) line[1], (int) line[2]));
            }while (line != null)
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void lock() {
        lock.lock();
    }

    public void unlock() {
        lock.unlock();
    }
}