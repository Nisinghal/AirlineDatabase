import java.util.Random;

public class TransactionThreads implements Runnable {
	private AirlineDB airline;
	private Random random = new Random();
	private int type = random.nextInt(5) + 1;
	private Flight f1;
	private Flight f2;
	private int passengerID = random.nextInt(10) + 1;
	private Transactions transaction = new Transactions();

	public TransactionThreads(AirlineDB airline) {
		this.airline = airline;
		f1 = airline.getFlights().get(random.nextInt(5) + 1);
	}

	@Override
	public void run() {
		airline.lock();
		execute();
		airline.unlock();
	}

	public void execute() {
		if (type == 1) {
			print_details();
			System.out.println("Reservation");
			transaction.reserve(f1, passengerID);
		} else if (type == 2) {
			print_details();
			System.out.println("Cancellation");
			transaction.cancel(f1, passengerID);
		} else if (type == 3) {
			print_details();
			System.out.println("Show My Flights");
			transaction.myFlights(passengerID);
		} else if (type == 4) {
			System.out.println("\n ----------------------------starting------------------------------------");
			System.out.println(" - TT type: Show Total Reservations");
			transaction.totalReservations();
		} else if (type == 5) {
			do {
				f2 = airline.getFlights().get(random.nextInt(5) + 1);
			} while (f1 == f2);
			System.out.println("\n ----------------------------starting------------------------------------");
			System.out.println("F1: " + f1.getAirlineName() + " - F2: " + f2.getAirlineName() + " - Passenger: "
					+ airline.getPassengers().get(passengerID).getName() + " - TT type: Transfer");
			transaction.transfer(f1, f2, passengerID);
		}
	}

	public void print_details() {
		System.out.println("\n ----------------------------starting------------------------------------");
		System.out.print("F1: " + f1.getAirlineName() + " - Passenger: "
				+ airline.getPassengers().get(passengerID).getName() + " - TT type: ");
	}
}
