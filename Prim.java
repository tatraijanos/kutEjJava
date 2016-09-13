package primSzalJava;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

public class Prim {
    public byte szalDb;
    public int n;
    public long indulIdo, vegeIdo;
    public static CopyOnWriteArrayList<Integer> megtalaltPrimLista = new CopyOnWriteArrayList<>();
    protected static CopyOnWriteArrayList<String> csvBeLista = new CopyOnWriteArrayList<>();
    private static boolean optimalis;           //0810
    
    public Prim() {
        szalDb = 1;
        n = 10000;
    }
    
    public Prim(boolean optimalis) {            //0810
        Prim.optimalis = optimalis;
    }
    
    public Prim(int n, byte szalDb) {
        this.n = n;
        this.szalDb = szalDb;
        megtalaltPrimLista.clear();
    }
    
    private boolean isPrim(int n) {
        int osztoDb = 0;
        
        for (int i = 1; i <= n; i++) {
            if (n % i == 0)
                osztoDb++;
        }
        
        return (osztoDb == 2);
    }
    
    public boolean isPrimOpt(int n){
        byte osztoDb = 0;
        
        if(n > 2){
           if(n % 2 == 0)
               return false;
        }
        
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (n % i == 0){
                osztoDb++;
                if(osztoDb >= 2)
                    return false;
            }
        }
        
        if(n == 1)
            return false;
        
        return (osztoDb == 1);
    }
    
    public CopyOnWriteArrayList<Integer> primLista(int a, int b) {          //0810
        CopyOnWriteArrayList<Integer> lista = new CopyOnWriteArrayList<>();
        for (int i = a; i <= b; i++){
            if (optimalis){
                if(isPrimOpt(i))
                    lista.add((int)i);
            }
            else{
                if (isPrim(i))
                    lista.add((int)i);
            }
        }
        return lista;
    }

    public void keszitCSV(String mod){
        csvBeLista.add("\"" + szalDb + "\";\"1\";\"" + n + "\";\"" + megtalaltPrimLista.size() + "\";\"" + indulIdo + "\";\"" + (vegeIdo - indulIdo) + "\";\"true\";\"" + mod + "\"");
    }
    
    public String eredmeny() {
        return " módszer " + n + "-ig " + szalDb + " szálon futott: " + (vegeIdo - indulIdo) + " ms, megtalált " + megtalaltPrimLista.size() + " db prímszámot.";
    }
    
    public String ujMentCSV(String fajlNev){
        String celurl = "C:/";
        String ujsort = "\r\n";
        try {
            celurl = Prim.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String jarNev = new java.io.File(Prim.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getName();
            celurl = celurl.substring(0, celurl.length() - jarNev.length());
        }
        catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        
        File fajlCSV = new File(celurl + fajlNev + ".csv");
        File fajlTXT = new File(celurl + "primSzamok.txt");
        try{
            FileWriter iroCSV = new FileWriter(fajlCSV, true);
            if (fajlCSV.length() == 0){
                iroCSV.write("\"Szál\";\"IntervallumTól\";\"IntervallumIg\";\"Megtalált prímek száma\";\"Indulási idő\";\"Idő\";\"Összefoglaló\";\"Módszer\"");
                iroCSV.write(ujsort);
            }
            for(String fajlba : csvBeLista){
                iroCSV.write(fajlba);
                iroCSV.write(ujsort);
            }
            iroCSV.close();
            
            FileWriter iroTXT = new FileWriter(fajlTXT);
            int sorDb = 1;
            Collections.sort(megtalaltPrimLista);
            for (Integer primek : megtalaltPrimLista){
                iroTXT.write(primek + "\t");
                if(sorDb % 10 == 0)
                    iroTXT.write(ujsort);
                sorDb++;
            }
            iroTXT.close();
            
            return "Az eredmények mentése sikeresen megtörtént.";
        }
        catch(IOException e){
            return "Az eredmények mentése nem történt meg, mert egy másik folyamat használja a fájlt.";
        }
    }
    
    public void csvBeListaTorlse(){
        csvBeLista.clear();
    }
}