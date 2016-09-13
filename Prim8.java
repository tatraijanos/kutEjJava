package primSzalJava;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Prim8 extends Prim {
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim8(int n, byte szalDb) {
        super(n, szalDb);
    }
    
    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        
        int fPrimOsszeg = 0;
        double a = 1, b;
        
        Integer[] felPrim = ujFelPrimLista();
        for (int szam : felPrim) {
            fPrimOsszeg += szam;
        }

        double bSeged = (double)n / fPrimOsszeg;
        
        for (byte id = szalDb; id > 0; id--) {
            b = (id != 1)? a + bSeged * felPrim[id - 1] - 1 : n;

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
    
    private Integer[] ujFelPrimLista() {
    //private List<Integer> UjFelPrimLista(){
        ArrayList<Short> prim = new ArrayList<>();
        //ArrayList<Integer> felPr = new ArrayList<>();
        Set<Integer> felPr = new HashSet<>();
        short szam = 2;
        
        while(prim.size() != szalDb * 2){
            if(isPrimOpt(szam)){
                prim.add(szam);
                for (int prszamok : prim){
                    felPr.add(prszamok * szam);
                } 
            }
            szam++;
        }
        
        //Set<Integer> uniqSe = new HashSet<>(felPr);
        //ArrayList<Integer> lista = new ArrayList<>(uniqSe);
        ArrayList<Integer> lista = new ArrayList<>(felPr);
        Collections.sort(lista);
        
        //return lista.subList(0, szalDb);
        return lista.subList(0, szalDb).toArray(new Integer[szalDb]);
    }
}