public class Producto {

    private String nombreProducto;
    private String descripcion;
    private String categoria;
    private double precio;

    public Producto(String nombreProducto, String descripcion, String categoria, double precio) {
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.precio = precio;

    }

    public String getNombreProducto() {

        return nombreProducto;
    }

    public String getDescripcion() {

        return descripcion;
    }

    public String getCategoria() {
        return categoria;
    }


    public double getPrecio() {
        return precio;
    }

    public double getPrecioTotal(int cantidad) {
        return this.precio * cantidad;
    }


}
