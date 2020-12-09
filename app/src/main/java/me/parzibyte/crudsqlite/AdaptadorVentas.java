package me.parzibyte.crudsqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import me.parzibyte.crudsqlite.modelos.Venta;

public class AdaptadorVentas extends RecyclerView.Adapter<AdaptadorVentas.MyViewHolder> {

    private List<Venta> listaDeVentas;

    public void setListaDeMascotas(List<Venta> listaDeVentas) {
        this.listaDeVentas = listaDeVentas;
    }

    public AdaptadorVentas(List<Venta> ventas) {
        this.listaDeVentas = ventas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View filaVenta = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fila_mascota, viewGroup, false);
        return new MyViewHolder(filaVenta);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        // Obtener la mascota de nuestra lista gracias al índice i
        Venta venta = listaDeVentas.get(i);

        // Obtener los datos de la lista
        String productoVenta = venta.getProducto();
        int cantidadVenta = venta.getCantidad();
        String descripcionVenta = venta.getDescripcion();

        // Y poner a los TextView los datos con setText
        myViewHolder.producto.setText(productoVenta);
        myViewHolder.cantidad.setText(String.valueOf(cantidadVenta));
        myViewHolder.descripcion.setText(descripcionVenta);
    }

    @Override
    public int getItemCount() {
        return listaDeVentas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView producto, cantidad, descripcion;

        MyViewHolder(View itemView) {
            super(itemView);
            this.producto = itemView.findViewById(R.id.tvProducto);
            this.cantidad = itemView.findViewById(R.id.tvCantidad);
            this.descripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
}
