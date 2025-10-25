
package controlador;
import dao.OrdenDAO;
import modelo.Orden;
import modelo.Producto;
import java.util.List;

/**
 * Controlador para la lógica de órdenes.
 */
public class OrdenControlador {

    private OrdenDAO ordenDAO;

    public OrdenControlador() {
        this.ordenDAO = new OrdenDAO();
    }

    /**
     * Obtiene las órdenes abiertas del mesero.
     * @param mesero Nombre del mesero
     * @return lista de órdenes abiertas
     */
    public List<Orden> obtenerOrdenesAbiertas(String mesero) {
        return ordenDAO.obtenerOrdenesPorMesero(mesero);
    }

    /**
     * Crea una nueva orden para una mesa y mesero.
     * @param mesa Número de mesa
     * @param mesero Nombre mesero
     * @return true si se creó correctamente
     */
    public boolean crearOrden(int mesa, String mesero) {
        Orden orden = new Orden();
        orden.setMesa(mesa);
        orden.setMesero(mesero);
        orden.setEstado("Abierta");
        orden.setTotal(0.0);
        return ordenDAO.crearOrden(orden);
    }

    /**
     * Agrega un producto a la orden.
     * Actualiza el total de la orden.
     * @param idOrden id de la orden
     * @param producto producto a agregar
     * @return true si se agregó correctamente
     */
    public boolean agregarProductoAOrden(int idOrden, Producto producto) {
        boolean agregado = ordenDAO.agregarProductoAOrden(idOrden, producto.getId());
        if (agregado) {
            // Actualizar total sumando precio del producto
            Orden orden = ordenDAO.obtenerOrdenAbiertaPorMesa(producto.getId());
            if (orden != null) {
                double nuevoTotal = orden.getTotal() + producto.getPrecio();
                orden.setTotal(nuevoTotal);
                return ordenDAO.actualizarOrden(orden);
            }
        }
        return false;
    }

    /**
     * Elimina un producto de la orden.
     * Actualiza el total.
     */
    public boolean eliminarProductoDeOrden(int idOrden, Producto producto) {
        boolean eliminado = ordenDAO.eliminarProductoDeOrden(idOrden, producto.getId());
        if (eliminado) {
            Orden orden = ordenDAO.obtenerOrdenAbiertaPorMesa(producto.getId());
            if (orden != null) {
                double nuevoTotal = orden.getTotal() - producto.getPrecio();
                if (nuevoTotal < 0) nuevoTotal = 0;
                orden.setTotal(nuevoTotal);
                return ordenDAO.actualizarOrden(orden);
            }
        }
        return false;
    }

    /**
     * Cierra la orden.
     */
    public boolean cerrarOrden(Orden orden) {
        orden.setEstado("Cerrada");
        return ordenDAO.actualizarOrden(orden);
    }
}


