package needleman.wunsch.master;

import java.util.Arrays;

/**
 *
 * @author mateus
 */
public class NeedlemanWunschMain {

    
    public static void main(String[] args) {

        NeedlemanWunsch alinhamento = new NeedlemanWunsch("AATACT", "ATTCT", 4, -2, -1, true);

        alinhamento.printStrandInfo();         
        
        alinhamento.printMatrizScore();
        System.out.println(" \n");
                
        System.out.println(Arrays.deepToString(alinhamento.getSolution()));

    }
    
}
