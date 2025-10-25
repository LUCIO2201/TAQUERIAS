package dao;
import modelo.DetalleOrden;
import util.ConexionDB;
import java.sql.*;

/**
 * DAO para insertar detalles en una orden.
 */
public class DetalleOrdenDAO {

    public boolean insertarDetalle(DetalleOrden d) {
        String sql = "INSERT INTO detalle_orden(orden_id, producto_id, cantidad, subtotal) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexionDB.getConexion()) {
            assert conn != null;
            try (PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, d.getOrdenId());
                ps.setInt(2, d.getProductoId());
                ps.setInt(3, d.getCantidad());
                ps.setDouble(4, d.getSubtotal());

                return ps.executeUpdate() > 0;

            }
        } catch (SQLException e) {
            System.out.println("Error al insertar detalle de orden: " + e.getMessage());
            return false;
        }
    }
}

