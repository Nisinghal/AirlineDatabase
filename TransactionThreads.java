import java.util.Random;

public class TransactionThreads implements Runnable {
	private AirlineDB airline;
	private Random random = new Random();
	private int type = random.nextInt(5) + 1;
	private Flight f1;
	private Flight f2;
	private int passengerID;
	private Transactions transaction = new Transactions();
	private boolean two_PL;

	public TransactionThreads(AirlineDB airline, boolean two_PL) {
		this.airline = airline;
		this.two_PL = two_PL;
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
		if (!two_PL) {
			airline.lock();
			execute();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			airline.unlock();
		} else {
			if (type >= 1 && type < 3) {
				airline.getPassengers().get(passengerID).lock();
				f1.lock();
				execute();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				airline.getPassengers().get(passengerID).unlock();
				f1.unlock();
			} else if (type == 3) {
				airline.getPassengers().get(passengerID).lock();
				execute();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				airline.getPassengers().get(passengerID).unlock();
			} else if (type == 4) {
				airline.lock();
				execute();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				airline.unlock();
			} else {
				airline.getPassengers().get(passengerID).lock();
				f1.lock();
				f2.lock();
				execute();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				airline.getPassengers().get(passengerID).unlock();
				f1.unlock();
				f2.unlock();
			}
		}
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
			System.out.println("Passenger: " + airline.getPassengers().get(passengerID).getName()
					+ " - TT type: Show My Flights");
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
