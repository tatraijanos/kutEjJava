package primSzalJava;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim5 extends Prim {
    private final byte AZ;
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim5(int n, byte szalDb, byte az) {
        super(n, szalDb);
        this.AZ = az;
    }
    
    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int pasOsszeg = 0;
        double a = 1, b = 0;
        
        int[] pasc = pascalHaromszog();
        for (byte i = 0; i < szalDb; i++) {
            pasOsszeg += pasc[i];
        }
        
        double bSeged = (double)n / pasOsszeg;
        byte pascAzon = (byte)(szalDb - 1), id = 1;
        
        for (; b <= n; b++) {
            b = (id != szalDb)? (a + bSeged * pasc[pascAzon]) - 1 : n;
            
            executor.execute(new PrimSzal((int)a, (int)b, id));
            
            a += bSeged * pasc[pascAzon];
            id++;
            pascAzon--;
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } 
        catch (InterruptedException e) {
            System.out.println("Időtúllépés miatt felfüggesztve!");
        }
        
        vegeIdo = System.currentTimeMillis();
        keszitCSV(OSZT_NEV + AZ);
        return eredmeny();       
    }
    
    private int[] pascalHaromszog() {
        /* Ha az
         *  0: akkor sima Pascal-háromszög
         *  1: akkor optimálisabb Pascal-háromszög
         *  2: akkor Pascal-háromszög páratlan oszlopait vesszük
        */
        
        byte sorosz = 0;
        if(AZ == 0 || AZ == 1)
            sorosz = szalDb;
        else if(AZ == 2)
            sorosz = (byte)(szalDb * 2 - 1);
        
        int[][] pasc = new int[sorosz][sorosz];

        for (byte s = 0; s < sorosz; s++) {
            for (byte o = 0; o < s + 1; o++) {
                if(s == 0 || o == 0 || o == s)
                    pasc[s][o] = 1;
                else
                    pasc[s][o] = pasc[s - 1][o] + pasc[s - 1][o - 1];
            }
        }
        
        if(AZ == 1)
            Arrays.sort(pasc[sorosz - 1]);
        return pasc[sorosz - 1];
    }
}