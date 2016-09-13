package primSzalJava;

//import java.util.ArrayList;
//import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
//import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class PrimSzal extends Prim implements Runnable {
    private final int a, b;
    private final byte id;
    
    public PrimSzal(int a, int b, byte id){
        this.a = a;
        this.b = b;
        this.id = id;
    }
    
    @Override
    public void run() {
        long s1 = System.currentTimeMillis();
        CopyOnWriteArrayList l = primLista(a, b);
        megtalaltPrimLista.addAll(l);
        long s2 = System.currentTimeMillis();
        System.out.println(id + ". szál futott: " + (s2-s1) + " ms, a: " + a + ", b: " + b + ", prímDb: " + l.size());
        csvBeLista.add("\"" + id + "\";\"" + a + "\";\"" + b + "\";\"" + l.size() + "\";\"" + s1 + "\";\"" + (s2 - s1) + "\";\"\";\"\"");
    }
}

/*class regiPrimSzal extends Prim implements Callable<CopyOnWriteArrayList> {
    private final int a, b;
    private final byte id;
    
    public regiPrimSzal(int a, int b, byte id) {
        this.a = a;
        this.b = b;
        this.id = id;
    }
    
    @Override
    public CopyOnWriteArrayList call() {
        long s1 = System.currentTimeMillis();
        CopyOnWriteArrayList l = primLista(a, b);
        long s2 = System.currentTimeMillis();
        System.out.println(id + ". szál futott: " + (s2-s1) + " ms, a: " + a + ", b: " + b + ", prímDb: " + l.size());
        csvBeLista.add("\"" + id + "\";\"" + a + "\";\"" + b + "\";\"" + l.size() + "\";\"" + s1 + "\";\"" + (s2 - s1) + "\";\"\";\"\"");
        return l;
    }
}*/

public class Prim2 extends Prim {
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim2(int n, byte szalDb) {
        super(n, szalDb);
    }

    public String futas() {
        indulIdo = System.currentTimeMillis();
        
        ExecutorService executor = Executors.newFixedThreadPool(szalDb);
        //ArrayList<Future<CopyOnWriteArrayList>> futures = new ArrayList<>();    //RÉGI
        
        int intervalHossz = n / szalDb, a, b;
        
        for (byte id = 1; id <= szalDb; id++) {
            a = (id - 1) * intervalHossz + 1;
            b = (id < szalDb)? a + intervalHossz - 1 : n;
            
            executor.execute(new PrimSzal(a, b, id));
            //futures.add(executor.submit(new regiPrimSzal(a, b, id)));           //RÉGI
        }
        executor.shutdown();
        //while (!executor.isTerminated()) {     } // Új, de lassabb, mint az alsó
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } 
        catch (InterruptedException e) {
            System.out.println("Időtúllépés miatt felfüggesztve!");
        }
        
        /*try {
            for (Future<CopyOnWriteArrayList> future : futures)
                megtalaltPrimLista.addAll(future.get());
        }
        catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }*/
        
        vegeIdo = System.currentTimeMillis();
        keszitCSV(OSZT_NEV);
        return eredmeny();
    }
}