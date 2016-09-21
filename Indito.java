/*
 * Módosítási napló:
 *
 * 0921:
 *      Indito:
 *          - #6 - Kivenni a rossz módszereket
 *          - #7 - 1000-es tartomány megszüntetése
 *          - #5 - Átállás 15 szálra
 * 
 * 0913:
 *      Indito:
 *          - importból is törölhető a logger
 *          - parancs() catch ág
 *          - sor() és egyediIntvall() catch ág
 * 0810:
 *      Optimális függvény fusson vagy ne, argumentumból szerzi meg, ha hagyományos futást szeretnénk;
 *      Új tartományok felvétele
 * 
 *      Indito:
 *          - psvm()
 *          - szoveg()
 *      Prim:
 *          - primLista()
 *          - osztályszintű statikus változó
 * 
 */
package primSzalJava;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Indito{
    private static final String[] MODSZERES = new String[13];                   // 0921#6

    private static void feltolt() {
        MODSZERES[1] = "Szál nélküli";
        MODSZERES[2] = "Normál";
        MODSZERES[3] = "Hatvány";
        MODSZERES[4] = "Fibonacci";
        //ODSZERES[5] = "Pascal normál";                                        // 0921#6
        //MODSZERES[6] = "Pascal optimális";
        //MODSZERES[7] = "Pascal páratlan";
        MODSZERES[5] = "Arányos";
        MODSZERES[6] = "Összetett";
        MODSZERES[7] = "Prím";
        MODSZERES[8] = "Félprím";
        MODSZERES[9] = "Koch-görbe";
        MODSZERES[10] = "Inverz négyzetes";
        MODSZERES[11] = "Egyedi";
        MODSZERES[12] = "Kilépés";
    }

    public static void main(String[] args) {
        boolean optimalis = true;
        if(args.length == 1 && args[0].equals("-hagyomanyos"))
            optimalis = false;

        feltolt();
        Prim prOsztaly = new Prim(optimalis);
        
        byte modszer, intval, szal;
        byte szaltol = 1, m = 4;                                                // 0921#7
        
        byte[] funk = szoveg(optimalis);
        modszer = funk[0];
        intval = funk[1];
        szal = funk[2];

        if(funk.length > 4){
            m = funk[4];
            szaltol = funk[5];
        }
        
        Integer[] tartomanyok = new Integer[szal];
        if(modszer == 1){
            szal = 1;
            szaltol = 1;
        }
        else if(modszer == MODSZERES.length - 2){                               // 0921#6
            System.out.println("Kérem azokat a számokat (" + szal + " db), amelyekkel a tartományokat kívánjuk felosztani.");
            System.out.println("Formátum példa: 1;2;4;7;11");
            System.out.print("> ");
            tartomanyok = egyediIntvall(szal);
            /*
             * for (int i = 1; i <= szal; i++) {
             * System.out.print("Kérem a(z) " + i + " számot: ");
             * tartomanyok[i - 1] = (int)sor((byte)1, Byte.MAX_VALUE);
             * }
            */
        }

        System.out.println("");
        System.out.println("A futtatás indul...");
        
        for (byte szalDb = szaltol; szalDb <= szal; szalDb++) {
            System.gc();
            int n = (int)Math.pow(10, m);
            
            for (int i = 1; i <= intval; i++) {
                switch(modszer){
                    case 1:
                        System.out.println(MODSZERES[modszer] + new Prim1(n, szalDb).futas());
                        break;
                    case 2:
                        System.out.println(MODSZERES[modszer] + new Prim2(n, szalDb).futas());
                        break;
                    case 3:
                        System.out.println(MODSZERES[modszer] + new Prim3(n, szalDb).futas());
                        break;
                    case 4:
                        System.out.println(MODSZERES[modszer] + new Prim4(n, szalDb).futas());
                        break;
                    /*case 5:                                                   // 0921#6
                    case 6:
                    case 7:
                        System.out.println(MODSZERES[modszer] + new Prim5(n, szalDb, (byte)(modszer - 5)).futas());
                        break;*/
                    case 5:                                                     // 0921#6
                        System.out.println(MODSZERES[modszer] + new Prim6(n, szalDb).futas());
                        break;
                    case 6:                                                     // 0921#6
                    case 7:                                                     // 0921#6
                        System.out.println(MODSZERES[modszer] + new Prim7(n, szalDb, (modszer - 6 == 1)).futas());// 0921#6
                        break;
                    case 8:                                                     // 0921#6
                        System.out.println(MODSZERES[modszer] + new Prim8(n, szalDb).futas());
                        break;
                    case 9:                                                     // 0921#6
                    case 10:                                                    // 0921#6
                        System.out.println(MODSZERES[modszer] + new Prim9(n, szalDb, (modszer - 10 == 0)).futas());// 0921#6
                        break;
                    case 11:                                                    // 0921#6
                        System.out.println(MODSZERES[modszer] + new Prim10(n, szalDb, tartomanyok).futas());
                }
                n *= 10;
                System.out.println("");
            } 
        }

        if(funk[3] == 1)
            System.out.println(prOsztaly.ujMentCSV("primJava"));
        else if(funk[3] == 3)
            System.out.println(prOsztaly.ujMentCSV("primJavaAdmin"));
        
        System.out.println("");
        prOsztaly.csvBeListaTorlse();
        main(args);
    }
    
    private static byte[] szoveg(boolean optimalis){
        byte[] funk = new byte[4];
        try {
            System.setOut(new java.io.PrintStream(System.out, true, "cp852"));
        } catch (UnsupportedEncodingException ex) {
            System.out.println("Kódlap hiba!");
        }
        System.out.println("Kérem, válassza ki a Java futtatandó metódust:");
        if(!optimalis)
            System.out.println("--- isPrim() üzemmód ---");
        
        for (byte i = 1; i < MODSZERES.length; i++) {
            System.out.println(i + " -> " + MODSZERES[i]);
        }

        System.out.print("A futtatandó: ");
        
        funk[0] = sor((byte)1, (byte)MODSZERES.length);
        if(funk[0] == MODSZERES.length - 1){
            System.exit(0);
        }
        else if(funk[0] == MODSZERES.length){
            byte[] parancss = parancs();
            if (parancss  == null){
                System.out.println("Hibás admin!");
                szoveg(optimalis);
            }
            else
                return parancss;
        }    
        
        System.out.println("Kérem, válassza ki az intervallumot: ");            // 0921#7
        //System.out.println("1 -> 1 - 1000");
        System.out.println("1 -> 1 - 10000");
        System.out.println("2 -> 1 - 100000");
        System.out.println("3 -> 1 - 1000000");
        System.out.println("4 -> 1 - 10000000");
        System.out.println("5 -> 1 - 100000000");
        System.out.println("6 -> 1 - 1000000000");
        //System.out.println("TILTVA 4 -> 1 - 1000000 TILTVA");
        System.out.print("Az intervallum: ");
        funk[1] = sor((byte)1, (byte)6);
        
        funk[2] = 1;
        if(funk[0] != 1){                                                       // 0921#5
            System.out.print("Kérem, határozza meg a maximális szál számot (1 - 15 közé eshet): ");
            funk[2] = sor((byte)1, (byte)15);
        }
        
        System.out.println("Mentsük az eredményeket egy CSV fájlba?");
        System.out.println("1 -> Igen");
        System.out.println("2 -> Nem");
        System.out.print("Mentés: ");
        funk[3] = sor((byte)1, (byte)2);
        
        return funk;
    }
    
    private static byte sor(byte kezd, byte vege){
        BufferedReader konzol = new BufferedReader(new InputStreamReader(System.in));
        boolean ok = false;
        byte azon = 0;
        do{
            try{
                String parancs = konzol.readLine();
                azon = Byte.parseByte(parancs);
                ok = (kezd <= azon && azon <= vege);
                if(!ok)
                    System.out.print("Ismeretlen parancs! Újra! ");
            }
            catch(IOException | NumberFormatException e){
                System.out.print("Ismeretlen parancs! Újra! ");
            }
        }
        while(!ok);
        
        return azon;
    }
    
    private static byte[] parancs(){
        System.out.println("Kérem a parancsot: metódus száma;tízek száma;maximum szál;mentés;intervallumtól;száltól;jelszó");
        System.out.println("Pl.: 3;2;4;Y;1000;1;****");
        BufferedReader konzol = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("> ");
        try {
            String parancs = konzol.readLine().trim();
            String[] admin = parancs.split(";");
            admin[1] = String.valueOf(Byte.valueOf(admin[1]) + 1);
            admin[3] = ("Y".equals(admin[3].toUpperCase()))? "3" : "2";
            admin[4] = String.valueOf((int)(Math.log10(Integer.parseInt(admin[4]))));
            admin[6] = ("0224".equals(admin[6]))? "Ok" : "Rossz";
            
            if("Rossz".equals(admin[6]))
                return null;
            
            byte[] retadm = new byte[admin.length - 1];
            for (int i = 0; i < admin.length - 1; i++) {
                retadm[i] = Byte.valueOf(admin[i]);
            }
            return retadm;
        }
        catch(IOException | NumberFormatException ex){
            return null;
        }
    }
    
    private static Integer[] egyediIntvall(byte hossz){
        BufferedReader konzol = new BufferedReader(new InputStreamReader(System.in));
        boolean ok = false;
        ArrayList<Integer> intv = new ArrayList<>();
        do{
            try{
                String parancs = konzol.readLine().trim();
                String[] daraboltPar = parancs.split(";");
                for (String daraboltPar1 : daraboltPar) {
                    intv.add(Integer.parseInt(daraboltPar1));
                }
                ok = true;
                if(intv.size() < hossz){
                    System.out.println(hossz + " szám felsorolása szükséges, kérem bővítse a felsorolást!");
                    ok = false;
                }
                else if(daraboltPar.length > hossz)
                    System.out.println("Több számot adott meg a szükségesnél, a maradék eldobásra került.");
            }
            catch(IOException | NumberFormatException e){
                System.out.print("Nem egész! Újra!");
            }
        }
        while(!ok);
        
        return intv.toArray(new Integer[hossz]);
    }
    
}