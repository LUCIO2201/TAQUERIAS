package modelo;

/**
 * Representa un producto (entrada, plato, bebida, postre).
 */
public class Producto {
    private int id;
    private String nombre;
    private String categoria;
    private double precio;
    private int stock;
    private final String s;

    // public Producto() {}

    //public Producto(int id, String nombre, String categoria, double precio, int stock) {
        //this.id = id;
       // this.nombre = nombre;
       // this.categoria = categoria;
        //this.precio = precio;
       // this.stock = stock;
    //}

    public Producto(int id, String nombre, double precio, int stock) {
        this.id = id;
        this.nombre = nombre;
        //this.categoria = categoria;
        this.precio = precio;
        this.stock = stock;
        this.s = s;
    }

    public Producto() {

    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}

