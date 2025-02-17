package caso;

public class Main {
    public static void main (String[] args) {
        int numOperarios = 3;
        int numProductos = 20;
        int capacidadBuzon = 5;
        int maxFallos = (int) Math.floor(numProductos * 0.1);
        
        Buzon buzonRevision = new Buzon(capacidadBuzon);
        Buzon buzonReproceso = new Buzon(Integer.MAX_VALUE);
        Buzon deposito = new Buzon(Integer.MAX_VALUE);
        
        Productor[] productores = new Productor[numOperarios];
        EquipoCalidad[] equipoCalidad = new EquipoCalidad[numOperarios];
        
        for (int i = 0; i < numOperarios; i++) {
            productores[i] = new Productor(i, buzonRevision, buzonReproceso);
            equipoCalidad[i] = new EquipoCalidad(i, buzonRevision, buzonReproceso, deposito, maxFallos);
        }
        
        for (Productor p : productores) p.start();
        for (EquipoCalidad e : equipoCalidad) e.start();
        
        for (int i = 0; i < numProductos; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
        try {
            buzonReproceso.depositar("FIN");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        for (Productor p : productores) p.interrupt();
        for (EquipoCalidad e : equipoCalidad) e.interrupt();
    }
}