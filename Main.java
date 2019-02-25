public class Main {

	public static void main(String[] args) {
		AirlineDB airline= new AirlineDB();
		for (int i = 0; i < 20; i++) {
			new Thread(new TransactionThreads(airline)).start();
		}
	}

	public static void printSysData() {
		for (Flight f : AirlineDB.getFlights().values()) {
			System.out.print(f.getAirlineName() + " " + f.getPassengers().size() + " ---->");
			for (Passenger p : f.getPassengers().values()) {
				System.out.print(p.getName() + " flying on " + p.getFlights().size() + " flight(s). ");
			}
			System.out.println();
		}
		System.out.println();
		for (Passenger p : AirlineDB.getPassengers().values()) {
			System.out.print(p.getName() + " " + p.getFlights().size() + " ---->");
			for (Flight f : p.getFlights().values()) {
				System.out.print(f.getAirlineName() + " having " + f.getPassengers().size() + " passenger(s). ");
			}
			System.out.println();
		}
	}
}