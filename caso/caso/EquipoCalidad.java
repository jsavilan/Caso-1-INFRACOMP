package caso;

import java.util.Random;

public class EquipoCalidad extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private final Buzon deposito;
    private final Random random = new Random();
    private final int maxFallos;
    private int fallos = 0;
    private boolean finGenerado = false;
    private int id;

    public EquipoCalidad(int id, Buzon buzonRevision, Buzon buzonReproceso, Buzon deposito, int maxFallos) {
        this.id = id;
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
        this.deposito = deposito;
        this.maxFallos = maxFallos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String producto = buzonRevision.retirarSemi();


                if (producto == null) {
                    if (finGenerado && buzonRevision.estaVacio()) {
                        break;
                    }
                    Thread.sleep(10);
                    continue;
                }

                if ("FIN".equals(producto)) {
                    finGenerado = true;
                    buzonReproceso.depositar("FIN"); 
                    continue;
                }

                if (fallos < maxFallos && random.nextInt(100) % 7 == 0) {
                    buzonReproceso.depositar(producto);
                    fallos++;
                    System.out.println(this.id + " rechaza: " + producto);
                } else {
                    deposito.depositar(producto);
                    System.out.println(this.id + " aprueba: " + producto);
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this.id + " finaliza (interrumpido).");
        }
    }
}
