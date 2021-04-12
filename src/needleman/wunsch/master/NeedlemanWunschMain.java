package needleman.wunsch.master;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import com.sun.xml.internal.ws.util.StringUtils;

import needleman.wunsch.utils.FileLoader;

/**
 *
 * @author mateus
 */
public class NeedlemanWunschMain {

    
    public static void main(String[] args) throws IOException {

    	FileLoader fileLoader = new FileLoader();
    	    	
    	String fileName1 = "seq1_SARS-COV-2";
    	String fileName2= "seq2_MERS";
		String firstSeq;
		String secondSeq;
		Integer limit = 1000;
		boolean isParallel = false;
		int numThreads = 20;
		
		if(args.length > 0) {
			try {
				fileName1 = args[0];
				fileName2 = args[1];
				limit = Integer.getInteger(args[2]);
			} catch (Exception e) {
				System.out.println("Erro de parâmetros!\n [arquivo1] [arquivo2] [limite de tamanho](opcional)" );
			}
		}
    			
    	if(limit != null && limit > 0) {
    		firstSeq = fileLoader.getSequence(fileName1).substring(0, limit);
    		secondSeq = fileLoader.getSequence(fileName2).substring(0, limit);
    	} else {
    		firstSeq = fileLoader.getSequence(fileName1);
    		secondSeq = fileLoader.getSequence(fileName2);
    	}
    			
		if(isParallel){

			long currentTimeStart = System.nanoTime();
			NeedlemanWunsch alinhamento = new NeedlemanWunsch(firstSeq, secondSeq, 4, -2, -1, true);
			long currentTimeEnd = System.nanoTime();

			BigDecimal finalTime = BigDecimal.valueOf((currentTimeEnd - currentTimeStart) / 1000000.);
			System.out.println("tempo de execução (ms): " + finalTime);
			alinhamento.printStrandInfo();

//        alinhamento.printMatrizScore();
			System.out.println(" \n");
//        System.out.println(Arrays.deepToString(alinhamento.getSolution()));

		} else {
			ParallelService parallelService = new ParallelService(firstSeq, secondSeq, numThreads);
			List<NeedlemanWunschThread> initializedThreads = parallelService.initialize();

			initializedThreads.stream().forEach(NeedlemanWunschThread::run);
		}


    }
    
}