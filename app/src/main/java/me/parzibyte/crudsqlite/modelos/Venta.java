package me.parzibyte.crudsqlite.modelos;

public class Venta {

    private String producto;
    private int cantidad;
    private String descripcion;
    private long id; // El ID de la BD

    public Venta(String producto, int cantidad, String descripcion) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
    }

    public Venta(String producto, int cantidad, String descripcion, long id) {
        this.producto = producto;
        this.cantidad = cantidad;
        this.descripcion = descripcion;
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Venta{" +
                "producto='" + producto + '\'' +
                ", cantidad=" + cantidad +
                ", descripcion='" + descripcion + '\'' +
                ", id=" + id +
                '}';
    }
}
