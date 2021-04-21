package needleman.wunsch.master;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import needleman.wunsch.utils.BarrierUpdater;

public class Data {

	private final String fullSeq1;

	private final String fullSeq2;

	private final int MATCH; // caracteres iguais

	private final int MISMATCH; // caracteres diferentes

	private final int GAP; // penalidade por lacuna

	private int numThreads;



	public Cell[][] solution;

	public CyclicBarrier barrier;

	public Data(String fullSeq1, String fullSeq2, int numThreads, int MATCH, int MISMATCH, int GAP) {

		this.fullSeq1 = fullSeq1;
		this.fullSeq2 = fullSeq2;
		this.MATCH = MATCH;
		this.MISMATCH = MISMATCH;
		this.GAP = GAP;
		this.solution = new Cell[fullSeq1.length() + 1][fullSeq2.length() + 1];
		this.numThreads = numThreads;

	}

	public int getNumThreads() {

		return numThreads;
	}

	public String getFullSeq1() {

		return fullSeq1;
	}

	public String getFullSeq2() {

		return fullSeq2;
	}

	public int getMATCH() {

		return MATCH;
	}

	public int getMISMATCH() {

		return MISMATCH;
	}

	public int getGAP() {

		return GAP;
	}
}
