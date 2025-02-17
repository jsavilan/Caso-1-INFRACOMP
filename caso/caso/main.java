package caso;

import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Ingrese número de operarios (productores y calidad): ");
        int numOperarios = sc.nextInt();

        System.out.print("Ingrese número total de productos: ");
        int numProductos = sc.nextInt();

        System.out.print("Ingrese límite del buzón de revisión: ");
        int capacidadBuzon = sc.nextInt();

        sc.close();

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

        for (int i = 0; i < numOperarios; i++) {
            productores[i].start();
        }
        for (int i = 0; i < numOperarios; i++) {
            equipoCalidad[i].start();
        }

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

        for (int i = 0; i < numOperarios; i++) {
            productores[i].interrupt();
            equipoCalidad[i].interrupt();
        }
    }
}
