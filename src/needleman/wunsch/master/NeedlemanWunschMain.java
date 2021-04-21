package needleman.wunsch.master;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import needleman.wunsch.utils.BarrierUpdater;
import needleman.wunsch.utils.FileLoader;

/**
 * @author mateus
 */
public class NeedlemanWunschMain {

    private final static int MATCH = 4; // caracteres iguais

    private final static int MISMATCH = -2; // caracteres diferentes

    private final static int GAP = -1; // penalidade por lacuna

    public static CyclicBarrier barrier;

    public static void main(String[] args) throws IOException {

        FileLoader fileLoader = new FileLoader();

        String fileName1 = "seq1_SARS-COV-2";
        String fileName2 = "seq2_MERS";
        String firstSeq;
        String secondSeq;
        Integer limit = 1000;
        boolean isParallel = true;
        int numThreads = 5;

        if (args.length > 0) {
            try {
                fileName1 = args[0];
                fileName2 = args[1];
                limit = Integer.getInteger(args[2]);
            } catch (Exception e) {
                System.out.println("Erro de parâmetros!\n [arquivo1] [arquivo2] [limite de tamanho](opcional)");
            }
        }

        if (limit != null && limit > 0) {
            firstSeq = fileLoader.getSequence(fileName1).substring(0, limit);
            secondSeq = fileLoader.getSequence(fileName2).substring(0, limit);
        } else {
            firstSeq = fileLoader.getSequence(fileName1);
            secondSeq = fileLoader.getSequence(fileName2);
        }

        if (!isParallel) {

            long currentTimeStart = System.nanoTime();
            NeedlemanWunsch alinhamento = new NeedlemanWunsch(firstSeq, secondSeq, MATCH, MISMATCH, GAP, true);
            long currentTimeEnd = System.nanoTime();

            BigDecimal finalTime = BigDecimal.valueOf((currentTimeEnd - currentTimeStart) / 1000000.);
            System.out.println("tempo de execução (ms): " + finalTime);
            alinhamento.printStrandInfo();

            System.out.println(" \n");

        } else {
            barrier = new CyclicBarrier(numThreads, new BarrierUpdater());
            validateInput(limit, numThreads);
            Data data = new Data(firstSeq, secondSeq, MATCH, MISMATCH, GAP);
            ParallelService parallelService = new ParallelService(numThreads, data);
            long currentTimeStart = System.nanoTime();
            int score = parallelService.runParallel();
            long currentTimeEnd = System.nanoTime();

            BigDecimal finalTime = BigDecimal.valueOf((currentTimeEnd - currentTimeStart) / 1000000.);
            System.out.println("tempo de execução (ms): " + finalTime);
            System.out.println("A pontuação para este alinhamento (paralelo) é: " + score);

        }

    }

    private static void validateInput(Integer limit, int numThreads) {

        int resto = limit % numThreads;
        int sugestao = numThreads * (limit / numThreads);
        if (resto != 0) {

            throw new IllegalArgumentException(
                    "Entrada inválida, o tamanho da string de entrada deve ser um múltiplo do numero de threads, sugestão: "
                            + sugestao);
        }
    }

    public static void reachBarrier(){

        try {
            barrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

}
