package primSzalJava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim10 extends Prim {
    private final Integer[] TARTOMANY;
    
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim10(int n, byte szalDb, Integer[] tartomany) {
        super(n, szalDb);
        this.TARTOMANY = tartomany;
    }
    
    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int egyediOsszeg = 0;
        double a = 1, b;
        
        for (byte i = 0; i < szalDb; i++) {
            egyediOsszeg += TARTOMANY[i];
        }

        double intervalHossz = (double)n / egyediOsszeg;
        
        for (byte id = szalDb; id > 0; id--) {
            b = (id != 1)? a + intervalHossz * TARTOMANY[id - 1] - 1 : n;
            
            executor.execute(new PrimSzal((int)a, (int)b, (byte)(szalDb - id + 1)));

            a = b + 1;
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
    
}
