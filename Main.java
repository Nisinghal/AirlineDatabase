import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	public static void main(String[] args) throws InterruptedException {
		Scanner sc=new Scanner(System.in);
		
		AirlineDB airline= new AirlineDB();
		
		System.out.println("Enter 0 for Serial and 1 for 2PL");
		int x=sc.nextInt();
		System.out.println("Enter number of Threads to be used");
		int sz=sc.nextInt();
		System.out.println("Enter number of Transactions:");
		int N=sc.nextInt();
		long start = System.currentTimeMillis();
		ExecutorService exec = Executors.newFixedThreadPool(sz);
		boolean two_PL=false;
		if(x==1){
			two_PL=true;
		}
		for (int i = 0; i < N; i++) {
			
			Runnable task = new TransactionThreads(i,airline,two_PL);
			exec.execute(task);
		}
		if (!exec.isTerminated()) {
			exec.shutdown();
			exec.awaitTermination(10L, TimeUnit.SECONDS);
		}
		
		long end = System.currentTimeMillis();
		System.out.println(end - start + " milliseconds");
		
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