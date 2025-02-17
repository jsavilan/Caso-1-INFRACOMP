package caso;

public class Productor extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private final int id;
    private static int productCounter = 0;

    public Productor(int id, Buzon buzonRevision, Buzon buzonReproceso) {
        this.id = id;
        this.buzonRevision = buzonRevision;
        this.buzonReproceso = buzonReproceso;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                String producto;

                synchronized (buzonReproceso) {
                    if (!buzonReproceso.estaVacio()) {
                        producto = buzonReproceso.retirar();
                        if ("FIN".equals(producto)) {
                            System.out.println(this.id + " recibe FIN y finaliza.");
                            return;
                        }
                        System.out.println(this.id + " reprocesa: " + producto);
                    } else {
                        int num = ++productCounter;
                        producto = "Producto de P" + id + "#" + num;
                        System.out.println(this.id + " genera: " + producto);
                    }
                }

                buzonRevision.depositar(producto);
            }
        } catch (InterruptedException e) {
            System.out.println(this.id + " finaliza (interrumpido).");
        }
    }
}
