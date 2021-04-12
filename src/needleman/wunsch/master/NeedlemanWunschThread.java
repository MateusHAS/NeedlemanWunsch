package needleman.wunsch.master;

public class NeedlemanWunschThread implements Runnable {
	private String name;
	private String seq1;
	private String seq2;

	public NeedlemanWunschThread(String name, String seq1, String seq2) {

		this.name = name;
		this.seq1 = seq1;
		this.seq2 = seq2;
	}

	@Override
	public void run() {
		NeedlemanWunsch alinhamento = new NeedlemanWunsch(seq1, seq2 );
		System.out.println("Thread: " + this.name );
		alinhamento.printStrandInfo();

	}
}
