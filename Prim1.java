package primSzalJava;

public class Prim1 extends Prim {
    private final String OSZT_NEV = this.getClass().getName().split("\\.")[1];
    
    public Prim1(int n, byte szalDb) {
        super(n, szalDb);
    }
    
    public String futas() {
        indulIdo = System.currentTimeMillis();
        megtalaltPrimLista.clear();
        megtalaltPrimLista.addAll(primLista(1, n));
        vegeIdo = System.currentTimeMillis();
        keszitCSV(OSZT_NEV);
        return eredmeny();
    }
    
}