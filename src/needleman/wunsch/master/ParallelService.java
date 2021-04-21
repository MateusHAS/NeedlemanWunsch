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

		int qtdIndexes = data.solution.length / numThreads;
		List<List<Integer>> listOfIndexes = new ArrayList<>();

		for (int i = 0; i<numThreads; i++){
			List<Integer> indexes = new ArrayList<>();
			for (int j=i+1; j< data.solution.length; j+=numThreads){
				indexes.add(j);
			}
			listOfIndexes.add(indexes);
		}

		for (int i = 0; i < pool.size(); i++) {
			pool.get(i).setI(listOfIndexes.get(i));
		}

		for (NeedlemanWunschThread td : pool) {
			td.start();
		}

		synchronized (data.solution[data.solution.length - 1][data.solution[0].length - 1]) {

			if (data.solution[data.solution.length - 1][data.solution[0].length - 1].getValue() == null) {
				System.out.println("processando...");
				try {
					data.solution[data.solution.length - 1][data.solution[0].length - 1].wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return data.solution[data.solution.length - 1][data.solution[0].length - 1].getValue();
	}

}