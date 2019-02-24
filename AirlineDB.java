import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.locks.Lock;

public class AirlineDB {

    static private HashMap<Integer, Passenger> passengers = new HashMap<>();
    static private HashMap<Integer, Flight> flights = new HashMap<>();
    private Lock lock;

    public AirlineDB() {
        flights = new HashMap<>();
        passengers = new HashMap<>();
        setFlights();
        setPassengers();
    }

    static public HashMap<Integer, Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers() {
        String fileName = "passengers.txt";
        BufferedReader fileReader;
        try {

            fileReader = new BufferedReader(new FileReader(fileName));
            String line ;
            String[] lineElements;
            while ((line = fileReader.readLine())!= null) {
                lineElements=line.split(",");
//                for(String i: lineElements){
//                System.out.print(i);
//            }
//                System.out.println();
                passengers.put( Integer.parseInt(lineElements[0]), new Passenger( Integer.parseInt(lineElements[0]),(String)lineElements[1], Integer.parseInt(lineElements[2]),lineElements[3].charAt(0)));
            }
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static public HashMap<Integer, Flight> getFlights() {
        return flights;
    }

    public void setFlights() {
        String fileName = "flights.txt";
        BufferedReader fileReader;
        try {
            fileReader = new BufferedReader(new FileReader(fileName));
            String line;// = fileReader.readLine().split(",");
            String[] lineElements;
            while ((line = fileReader.readLine())!= null){
                lineElements=line.split(",");
//                for(String i: lineElements){
//                    System.out.print(i);
//                }
//                System.out.println();
                flights.put( Integer.parseInt(lineElements[0]), new Flight( Integer.parseInt(lineElements[0]), (String) lineElements[1], Integer.parseInt(lineElements[2])));
            };
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