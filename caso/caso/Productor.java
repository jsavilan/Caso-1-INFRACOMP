package caso;

public class Productor extends Thread {
    private final Buzon buzonRevision;
    private final Buzon buzonReproceso;
    private final int id;

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
                            System.out.println("Productor " + id + " recibe FIN y finaliza.");
                            return;
                        }
                        System.out.println("Productor " + id + " reprocesa: " + producto);
                    } else {
                        producto = "Producto de " + id;
                        System.out.println("Productor " + id + " genera: " + producto);
                    }
                }
                buzonRevision.depositar(producto);
            }
        } catch (InterruptedException e) {
            System.out.println("Productor " + id + " finaliza.");
        }
    }
}