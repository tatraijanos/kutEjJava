package primSzalJava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim4 extends Prim {
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim4(int n, byte szalDb) {
        super(n, szalDb);
    }

    public String futas() {
        indulIdo = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int fibOsszeg = 0;
        double a = 1, b = 0;

        int[] fibo = fibonacci();
        for (byte i = 0; i < szalDb; i++) {
            fibOsszeg += fibo[i];
        }
        
        double bSeged = (double)n / fibOsszeg;
        byte fiboAzon = (byte)(szalDb - 1), id = 1;
        
        for (; b <= n; b++) {
            b = (id != szalDb)? (a + bSeged * fibo[fiboAzon]) - 1 : n;
            
            executor.execute(new PrimSzal((int)a, (int)b, id));
            
            a += bSeged * fibo[fiboAzon];
            id ++;
            fiboAzon --;
        }
        executor.shutdown();
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } 
        catch (InterruptedException e) {
            System.out.println("Időtúllépés miatt felfüggesztve!");
        }
        
        vegeIdo = System.currentTimeMillis();
        keszitCSV(OSZT_NEV);
        return eredmeny();
    }

    private int[] fibonacci() {
        int[] fibo = new int[szalDb];
        int elozo = 1, kovetkezo;
        
        for (byte i = 0; i < szalDb ; ++i) {
            kovetkezo = fibo[(i == 0)? i : i - 1] + elozo;
            elozo = fibo[(i == 0)? i : i - 1];
            fibo[i] = kovetkezo;
        }
        return fibo;
    }
}