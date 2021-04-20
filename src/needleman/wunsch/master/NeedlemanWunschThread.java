package needleman.wunsch.master;

import java.util.List;

public class NeedlemanWunschThread extends Thread {

	private String name;

	private Data data;

	private List<Integer> indexes;

	public NeedlemanWunschThread(String name, Data data) {

		this.name = name;
		this.data = data;
	}

	public void setI(List<Integer> indexes) {

		this.indexes = indexes;
	}

	@Override
	public void run() {

		for (Integer i : indexes) {

			System.out.println("Thread: " + Thread.currentThread().getName() + " - index: " + i);

			for (int j = 1; j < data.getFullSeq1().length() + 1; j++) {
				int matchValue;
				if (data.getFullSeq1().charAt(i - 1) == data.getFullSeq2().charAt(j - 1)) {
					matchValue = data.getMATCH();
				} else {
					matchValue = data.getMISMATCH();
				}
				if (data.solution[i - 1][j].getValue() == null) {
					waitingRoom(i - 1, j);
				}
				int max = max(data.solution[i][j - 1].getValue() + data.getGAP(),
						data.solution[i - 1][j].getValue() + data.getGAP(),
						data.solution[i - 1][j - 1].getValue() + matchValue);
				synchronized (data.solution[i][j]) {

					data.solution[i][j].setValue(max);
					data.solution[i][j].notifyAll();
				}
			}

		}

	}

	private void waitingRoom(int i, int j) {

		synchronized (data.solution[i][j]) {

			try {
				data.solution[i][j].wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	private int max(int a, int b, int c) {

		return Math.max(Math.max(a, b), c);
	}
}
