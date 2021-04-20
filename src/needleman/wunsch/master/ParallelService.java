package needleman.wunsch.master;

import java.util.ArrayList;
import java.util.List;

public class ParallelService {

	private final Integer numThreads;

	private final Data data;

	private List<NeedlemanWunschThread> pool = new ArrayList();

	public ParallelService(Integer numThreads, Data data) {

		this.numThreads = numThreads;
		this.data = data;
	}

	public int runParallel() {
//		for (int i = 0; i < numThreads; i++){
//			pool.add(new NeedlemanWunschThread());
//		}
		for (int i = 0; i < data.solution.length; i++) {
			for (int j = 0; j < data.solution.length; j++) {
				data.solution[i][j] = new Cell();
			}
		}

		data.solution[0][0] = new Cell(0);

		for (int i = 1; i < data.getFullSeq2().length() + 1; i++) {
			data.solution[0][i] = new Cell(data.solution[0][i - 1].getValue() + data.getGAP());
		}

		for (int i = 1; i < data.getFullSeq1().length() + 1; i++) {
			data.solution[i][0] = new Cell(data.solution[i - 1][0].getValue() + data.getGAP());
		}

		for (int i = 1; i <= numThreads; i++) {
			pool.add(new NeedlemanWunschThread(String.valueOf(i), data));
		}

		List<Integer> indexes1 = new ArrayList<>();
		indexes1.add(1);
		indexes1.add(3);
		indexes1.add(5);
		indexes1.add(7);
		indexes1.add(9);
		List<Integer> indexes2 = new ArrayList<>();
		indexes2.add(2);
		indexes2.add(4);
		indexes2.add(6);
		indexes2.add(8);
		indexes2.add(10);

		pool.get(0).setI(indexes1);
		pool.get(1).setI(indexes2);

		for (NeedlemanWunschThread td : pool) {
			td.start();
		}
		// aqui a bruxaria come solta
		System.out.println("fim das chamadas de start");

		while (data.solution[data.solution.length - 1][data.solution[0].length - 1].getValue() == null) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return data.solution[data.solution.length - 1][data.solution[0].length - 1].getValue();

	}

}