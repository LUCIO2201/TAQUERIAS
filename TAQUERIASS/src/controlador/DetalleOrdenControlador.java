package controlador;
import dao.DetalleOrdenDAO;
import modelo.DetalleOrden;

/**
 * Controlador para detalles de orden.
 */
public class DetalleOrdenControlador {
    private DetalleOrdenDAO dao;

    public DetalleOrdenControlador() {
        dao = new DetalleOrdenDAO();
    }

    public boolean agregarDetalle(DetalleOrden d) {
        return dao.insertarDetalle(d);
    }
}

