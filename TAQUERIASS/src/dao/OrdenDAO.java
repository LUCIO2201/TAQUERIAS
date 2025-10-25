package dao;
import modelo.Orden;
import modelo.Producto;
import util.ConexionDB;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para acceso a datos de órdenes.
 */
public class OrdenDAO {

    private Connection conn = ConexionDB.getConexion();

    /**
     * Crea una orden sin productos inicialmente.
     */
    public boolean crearOrden(Orden orden) {
        String sql = "INSERT INTO ordenes (mesa, mesero, estado, total) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, orden.getMesa());
            ps.setString(2, orden.getMesero());
            ps.setString(3, orden.getEstado());
            ps.setDouble(4, 0.0); // total inicial 0
            int filas = ps.executeUpdate();

            if (filas > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idGenerado = rs.getInt(1);
                        orden.setId(idGenerado);
                        return true;
                    }
                }
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene las órdenes abiertas de un mesero.
     */
    public List<Orden> obtenerOrdenesPorMesero(String mesero) {
        List<Orden> ordenes = new ArrayList<>();
        String sql = "SELECT * FROM ordenes WHERE mesero = ? AND estado = 'Abierta' ORDER BY id";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mesero);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Orden orden = new Orden();
                    orden.setId(rs.getInt("id"));
                    orden.setMesa(rs.getInt("mesa"));
                    orden.setMesero(rs.getString("mesero"));
                    orden.setEstado(rs.getString("estado"));
                    orden.setTotal(rs.getDouble("total"));
                    orden.setProductos(obtenerProductosDeOrden(orden.getId()));
                    ordenes.add(orden);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ordenes;
    }

    /**
     * Obtiene productos asignados a una orden.
     */
    private List<Producto> obtenerProductosDeOrden(int idOrden) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.id, p.nombre, p.precio, p.stock " +
                "FROM productos p " +
                "JOIN orden_productos op ON p.id = op.producto_id " +
                "WHERE op.orden_id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Producto p = new Producto(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getDouble("precio"),
                            rs.getInt("stock")
                    );
                    productos.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    /**
     * Actualiza el total y estado de una orden.
     */
    public boolean actualizarOrden(Orden orden) {
        String sql = "UPDATE ordenes SET total = ?, estado = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, orden.getTotal());
            ps.setString(2, orden.getEstado());
            ps.setInt(3, orden.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene la orden abierta vinculada a una mesa (o null si no hay).
     */
    public Orden obtenerOrdenAbiertaPorMesa(int numeroMesa) {
        String sql = "SELECT * FROM ordenes WHERE mesa = ? AND estado = 'Abierta' LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numeroMesa);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Orden orden = new Orden();
                    orden.setId(rs.getInt("id"));
                    orden.setMesa(rs.getInt("mesa"));
                    orden.setMesero(rs.getString("mesero"));
                    orden.setEstado(rs.getString("estado"));
                    orden.setTotal(rs.getDouble("total"));
                    orden.setProductos(obtenerProductosDeOrden(orden.getId()));
                    return orden;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Agrega un producto a una orden (en tabla orden_productos).
     */
    public boolean agregarProductoAOrden(int idOrden, int idProducto) {
        String sql = "INSERT INTO orden_productos (orden_id, producto_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Elimina un producto de una orden.
     */
    public boolean eliminarProductoDeOrden(int idOrden, int idProducto) {
        String sql = "DELETE FROM orden_productos WHERE orden_id = ? AND producto_id = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOrden);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
