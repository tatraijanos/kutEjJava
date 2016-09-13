package primSzalJava;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim7 extends Prim {
    private final boolean AZ;
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim7(int n, byte szalDb, boolean az) {
        super(n, szalDb);
        this.AZ = az;
    }

    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int osszOsszeg = 0;
        double a = 1, b;

        ArrayList<Short> osszetett = osszetettLista();
        for(short szam : osszetett){
            osszOsszeg += szam;
        }
        
        double bSeged = (double)n / osszOsszeg;
        
        for (byte id = szalDb; id > 0; id--) {
            b = (id != 1)? a + bSeged * osszetett.get(id - 1) - 1 : n;
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
        keszitCSV(OSZT_NEV + ((AZ)? 1 : 0));
        return eredmeny();   
    }
    
    private ArrayList<Short> osszetettLista() {
        ArrayList<Short> lista = new ArrayList<>();
        short szam = 2;

        while(lista.size() != szalDb){
            if(isPrimOpt(szam) == AZ)
                lista.add(szam);

            szam++;
        }
        
        return lista;
    }
}