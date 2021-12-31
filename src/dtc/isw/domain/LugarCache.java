package dtc.isw.domain;

import java.util.Hashtable;

public class LugarCache {
    private static Hashtable<String, Lugar> shareMap = new Hashtable<String, Lugar>();

    public static Lugar getLugar(String lugarID)
    {
        Lugar cachedLugar = shareMap.get(lugarID);
        return (Lugar) cachedLugar.clone();
    }

    public static void loadCache(){
        Biblioteca biblioteca = new Biblioteca();
        biblioteca.setID("1");
        shareMap.put(biblioteca.getID(),biblioteca);

        Planta planta = new Planta();
        planta.setID("2");
        shareMap.put(planta.getID(),planta);

        Mesa mesa = new Mesa();
        mesa.setID("3");
        shareMap.put(mesa.getID(),mesa);
    }


}
