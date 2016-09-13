package primSzalJava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim3 extends Prim {
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim3(int n, byte szalDb) {
        super(n, szalDb);
    }
    
    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int a = 1, b, hatvany = (int)Math.pow(2, szalDb), arany = hatvany / 2, nevezo = hatvany - 1, hossz;

        for (int k = arany; k >= 1; k /= 2) {
            hossz = (int)((double)k / nevezo * n);
            b = (k > 1)? a + hossz - 1 : n;
            double id = Math.abs((Math.log(k) / Math.log(2)) - szalDb);
            if(id > 0.5 && id < 1.1)
                id = 1;
            
            executor.execute(new PrimSzal(a, b, (byte)id));
            
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