package needleman.wunsch.master;

import java.util.ArrayList;
import java.util.List;

public class ParallelService {

	private final String fullSeq1;

	private final String fullSeq2;

	private final Integer numThreads;

	public ParallelService(String fullSeq1, String fullSeq2, Integer numThreads) {

		this.fullSeq1 = fullSeq1;
		this.fullSeq2 = fullSeq2;
		this.numThreads = numThreads;
	}

	public List<NeedlemanWunschThread> initialize() {

		StringBuilder name;
		List<NeedlemanWunschThread> threads = new ArrayList<>();
		int length = fullSeq1.length();
		double threadSeqSize = length / numThreads;
		int threadSeqRemainder = length % numThreads;
		int counterSeqPre = 0;
		int counterSeqPos = 0;
		String subSeq1;
		String subSeq2;

		System.out.println("resto da divisao: " + threadSeqRemainder);
		for (int i = 0; i <= numThreads; i++) {
			name = new StringBuilder();
			counterSeqPos += threadSeqSize;
			if (counterSeqPos >= length) {
				subSeq1 = (String) fullSeq1.subSequence(counterSeqPre, length);
				subSeq2 = (String) fullSeq2.subSequence(counterSeqPre, length);
				threads.add(new NeedlemanWunschThread(
						name.append("thread_").append(i + 1).append("_index:_").append(counterSeqPre).toString(), subSeq1,
						subSeq2));
				break;
			}
			subSeq1 = (String) fullSeq1.subSequence(counterSeqPre, counterSeqPos);
			subSeq2 = (String) fullSeq2.subSequence(counterSeqPre, counterSeqPos);
			threads.add(new NeedlemanWunschThread(
					name.append("thread_").append(i + 1).append("_index:_").append(counterSeqPre).toString(), subSeq1,
					subSeq2));
			counterSeqPre += threadSeqSize;
		}

		return threads;
	}
}
//https://github.com/wdouglascosta/needleman-wunsch-parallel/blob/master/Main.java