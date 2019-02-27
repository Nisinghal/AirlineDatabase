import java.util.ArrayList;

class Node {
	TransactionThreads thread;
	int N;
	volatile boolean granted = false;
	int lock_type;

	public Node(TransactionThreads t, int N) {
		thread = t;
		this.N = N;
	}

	public Node(Node node) {
		thread = node.thread;
		N = node.N;
		granted = node.granted;
		lock_type = node.lock_type;
	}
}

public class CCM {
	TransactionThreads thread;
	boolean two_PL;
	int type;
	AirlineDB airline;
	int S = 0;
	int X = 1;

	public CCM(TransactionThreads t, boolean two_PL) {
		thread = t;
		airline = thread.airline;
		type = thread.type;
		this.two_PL = two_PL;
		Node node = new Node(t, t.N);
		if (type == 3 || type == 4) {
			node.lock_type = S;
		} else {
			node.lock_type = X;
		}
		if (!two_PL) {
			serial_schedule_locking(node);
		} else {
			two_PL_scheduling(node);
		}
	}

	public void serial_schedule_locking(Node node) {
		Object obj1 = airline;
		synchronized (obj1) {
			thread.execute();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void two_PL_scheduling(Node node) {
		if (type >= 1 && type < 3) {
			Node node1 = new Node(node);
			Node node2 = new Node(node);
			while (!lock(node1, airline.getPassengers().get(thread.passengerID).waiting_for_locks))
				;
			while (!lock(node2, thread.f1.waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			unlock(node1, airline.getPassengers().get(thread.passengerID).waiting_for_locks);
			unlock(node2, thread.f1.waiting_for_locks);

		} else if (type == 3) {
			Node node1 = new Node(node);
			Node node2 = new Node(node);
			while (!lock(node1, airline.waiting_for_locks))
				;
			while (!lock(node2, airline.getPassengers().get(thread.passengerID).waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node1, airline.waiting_for_locks);
			unlock(node2, airline.getPassengers().get(thread.passengerID).waiting_for_locks);

		} else if (type == 4) {
			Node node1 = new Node(node);
			while (!lock(node1, airline.waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node1, airline.waiting_for_locks);
		} else {
			Node node1 = new Node(node);
			Node node2 = new Node(node);
			Node node3 = new Node(node);
			Flight f1 = thread.f1;
			Flight f2 = thread.f2;
			if (f1.getId() > f2.getId()) {
				f1 = thread.f2;
				f2 = thread.f1;
			}
			while (!lock(node1, airline.getPassengers().get(thread.passengerID).waiting_for_locks))
				;
			while (!lock(node2, f1.waiting_for_locks))
				;
			while (!lock(node3, f2.waiting_for_locks))
				;

			thread.execute();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node1, airline.getPassengers().get(thread.passengerID).waiting_for_locks);

			unlock(node2, f1.waiting_for_locks);

			unlock(node3, f2.waiting_for_locks);
		}
	}

	public boolean lock(Node node, ArrayList<Node> waiting_for_locks) {
		if (!waiting_for_locks.contains(node)) {
			waiting_for_locks.add(node);
		}
		if (waiting_for_locks.size() == 1) {
			node.granted = true;
		} else if (node.lock_type == S) {
			for (int i = 0; i < waiting_for_locks.size(); i++) {
				Node n1 = waiting_for_locks.get(i);
				if (n1 != null && n1.granted && n1.lock_type == S) {
					node.granted = true;
					break;
				}
			}
		}
		if (node.granted)
			node.granted = check(node);

		return node.granted;
	}

	public void unlock(Node node, ArrayList<Node> waiting_for_locks) {
		waiting_for_locks.remove(node);
		node.granted = false;
		for (int i = 0; i < waiting_for_locks.size(); i++) {
			Node n1 = waiting_for_locks.get(i);
			if (n1 != null && n1.granted) {
				return;
			}
		}
		for (int i = 0; i < waiting_for_locks.size(); i++) {
			Node n1 = waiting_for_locks.get(i);
			if (n1 != null) {
				n1.granted = true;
				return;
			}
		}
	}

	public boolean check(Node node) {
		if (node.lock_type == S) {
			for (Flight flight : airline.getFlights().values()) {
				ArrayList<Node> arr = flight.waiting_for_locks;
				for (int i = 0; i < arr.size(); i++) {
					Node n1 = arr.get(i);
					if (n1 != null && n1.granted && n1.lock_type == X) {
						return false;
					}
				}
			}
			for (Passenger p : airline.getPassengers().values()) {
				ArrayList<Node> arr = p.waiting_for_locks;
				for (int i = 0; i < arr.size(); i++) {
					Node n1 = arr.get(i);
					if (n1 != null && n1.granted && n1.lock_type == X) {
						return false;
					}
				}
			}
		} else {
			ArrayList<Node> arr = airline.waiting_for_locks;
			for (int i = 0; i < arr.size(); i++) {
				Node n1 = arr.get(i);
				if (n1 != null && n1.granted) {
					return false;
				}
			}
		}
		return true;
	}
}
