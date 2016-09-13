package primSzalJava;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim9 extends Prim{
    private final boolean AZ;
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim9(int n, byte szalDb, boolean az) {
        super(n, szalDb);
        this.AZ = az;
    }

    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        double osszOsszeg = 0;
        double a = 1, b;

        ArrayList<Double> fraktal = new ArrayList<>();
        
        if (AZ){
            for (byte i = 1; i <= szalDb; i++){
                fraktal.add(3 * 1 * (Math.pow((4.0 / 3.0), i))); //koch-gorbe
            }
        }
        else{
            for (byte i = 1; i <= szalDb; i++){
                fraktal.add(Math.pow(i, 2)); //inverznégyzetes
            }
        }
        
        for(double szam: fraktal){
            osszOsszeg=szam+osszOsszeg;
        }
        
        double bSeged = (double)n / osszOsszeg;

        for (byte id = szalDb; id > 0; id--) {
            b = (id > 1)? a + bSeged * fraktal.get(id - 1) - 1 : n;
            
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
        keszitCSV(OSZT_NEV + (AZ? 1 : 0));
        return eredmeny();
    }
}
