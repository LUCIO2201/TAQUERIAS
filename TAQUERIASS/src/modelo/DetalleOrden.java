package modelo;

/**
 * Detalle de un producto en una orden específica.
 */
public class DetalleOrden {
    private int id;
    private int ordenId;
    private int productoId;
    private int cantidad;
    private double subtotal;

    public DetalleOrden() {}

    public DetalleOrden(int id, int ordenId, int productoId, int cantidad, double subtotal) {
        this.id = id;
        this.ordenId = ordenId;
        this.productoId = productoId;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrdenId() { return ordenId; }
    public void setOrdenId(int ordenId) { this.ordenId = ordenId; }

    public int getProductoId() { return productoId; }
    public void setProductoId(int productoId) { this.productoId = productoId; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}

