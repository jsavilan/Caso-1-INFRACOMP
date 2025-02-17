package caso;
import java.util.LinkedList;
import java.util.Queue;

public class Buzon {
    private final int capacidad;
    private final Queue<String> productos = new LinkedList<>();

    public Buzon(int capacidad) {
        this.capacidad = capacidad;
    }

    public synchronized void depositar(String producto) throws InterruptedException {
        while (productos.size() >= capacidad) {
            wait();
        }
        productos.add(producto);
        notifyAll();
    }

    public synchronized String retirar() throws InterruptedException {
        while (productos.isEmpty()) {
            wait();
        }
        String producto = productos.poll();
        notifyAll();
        return producto;
    }

    public synchronized String retirarSemi() {
        return productos.poll();
    }

    public synchronized boolean estaVacio() {
        return productos.isEmpty();
    }
}