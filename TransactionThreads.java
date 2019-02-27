import java.util.Random;

public class TransactionThreads implements Runnable {
	int N;
	AirlineDB airline;
	private Random random = new Random();
	int type = random.nextInt(5) + 1;
	Flight f1;
	Flight f2;
	int passengerID;
	Transactions transaction = new Transactions();
	private boolean two_PL;

	public TransactionThreads(int N, AirlineDB airline, boolean two_PL) {
		this.airline = airline;
		this.two_PL = two_PL;
		this.N = N;
		createTransaction();
	}

	public void createTransaction() {
		if (type >= 1 && type < 3) {
			passengerID = random.nextInt(10) + 1;
			f1 = airline.getFlights().get(random.nextInt(5) + 1);
		} else if (type == 3) {
			passengerID = random.nextInt(10) + 1;
		} else if (type == 5) {
			passengerID = random.nextInt(10) + 1;
			f1 = airline.getFlights().get(random.nextInt(5) + 1);
			do {
				f2 = airline.getFlights().get(random.nextInt(5) + 1);
			} while (f1 == f2);
		}
	}

	@Override
	public void run() {
		CCM ccm=new CCM(this, two_PL);
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
			System.out.println("\n ----------------------------starting------------------------------------");
			System.out.println(
					"Passenger: " + airline.getPassengers().get(passengerID).getName() + " - TT type: Show My Flights");
			transaction.myFlights(passengerID);
		} else if (type == 4) {
			System.out.println("\n ----------------------------starting------------------------------------");
			System.out.println("TT type: Show Total Reservations");
			transaction.totalReservations();
		} else if (type == 5) {
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
