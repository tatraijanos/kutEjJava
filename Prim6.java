package primSzalJava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim6 extends Prim {
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim6(int n, byte szalDb) {
        super(n, szalDb);
    }
    
    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int osszeg = 0;
        double a = 1, b;
        
        for(byte i = 0; i <= szalDb; i++){
            osszeg += i; 
        }

        double bSeged = (double)n / osszeg;

        for (byte id = szalDb; id > 0; id--) {
            b = (id != 1)? id * bSeged + (a - 1) : n;
            
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