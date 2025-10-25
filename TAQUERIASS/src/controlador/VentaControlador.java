package controlador;
import dao.VentaDAO;
import modelo.Venta;

/**
 * Controlador para ventas. Se encarga del registro final.
 */
public class VentaControlador {
    private VentaDAO dao;

    public VentaControlador() {
        dao = new VentaDAO();
    }

    public boolean registrarVenta(Venta venta) {
        return dao.registrarVenta(venta);
    }
}

