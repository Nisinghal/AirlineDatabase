import java.util.ArrayList;

class Node {
	TransactionThreads thread;
	int N;
	boolean granted = false;
	int lock_type;

	public Node(TransactionThreads t, int N) {
		thread = t;
		this.N = N;
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
			System.out.println(node.N);
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void two_PL_scheduling(Node node) {
		if (type >= 1 && type < 3) {
			while (!lock(node, airline.getPassengers().get(thread.passengerID).waiting_for_locks))
				;
			while (!lock(node, thread.f1.waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node, airline.getPassengers().get(thread.passengerID).waiting_for_locks);
			unlock(node, thread.f1.waiting_for_locks);
		} else if (type == 3) {
			while (!lock(node, airline.getPassengers().get(thread.passengerID).waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node, airline.getPassengers().get(thread.passengerID).waiting_for_locks);
		} else if (type == 4) {
			while (!lock(node, airline.waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node, airline.waiting_for_locks);
		} else {
			while (!lock(node, airline.getPassengers().get(thread.passengerID).waiting_for_locks))
				;
			while (!lock(node, thread.f1.waiting_for_locks))
				;
			while (!lock(node, thread.f2.waiting_for_locks))
				;
			thread.execute();
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			unlock(node, airline.getPassengers().get(thread.passengerID).waiting_for_locks);
			unlock(node, thread.f1.waiting_for_locks);
			unlock(node, thread.f2.waiting_for_locks);
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
		return node.granted;
	}

	public void unlock(Node node, ArrayList<Node> waiting_for_locks) {
		waiting_for_locks.remove(node);
		for (int i = 0; i < waiting_for_locks.size(); i++) {
			Node n1 = waiting_for_locks.get(i);
			if (n1.granted && n1 != node) {
				return;
			}
		}
		for (int i = 0; i < waiting_for_locks.size(); i++) {
			Node n1 = waiting_for_locks.get(i);
			if (n1 != node) {
				n1.granted = true;
				return;
			}
		}
	}
}