package controlador;
import dao.MesaDAO;
import modelo.Mesa;
import java.util.List;

/**
 * Controlador para mesas. Controla estados y lista las mesas.
 */
public class MesaControlador{
    private MesaDAO dao;

    public MesaControlador() {
        dao = new MesaDAO();
    }
    public List<Mesa> listarMesas() {
        return dao.listarMesas();
    }
    public boolean cambiarEstadoMesa(int id, String estado) {
        return dao.actualizarEstado(id, estado);
    }

    public List<Mesa> obtenerMesas() {
        return List.of();
    }

    public boolean asignarOrdenAMesa(int numero, String mesero) {
        return false;
    }
}

