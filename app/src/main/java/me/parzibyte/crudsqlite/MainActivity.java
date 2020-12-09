package me.parzibyte.crudsqlite;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import me.parzibyte.crudsqlite.controllers.VentasController;
import me.parzibyte.crudsqlite.modelos.Venta;

public class MainActivity extends AppCompatActivity {
    private List<Venta> listaDeVentas;
    private RecyclerView recyclerView;
    private AdaptadorVentas adaptadorVentas;
    private VentasController ventasController;
    private FloatingActionButton fabAgregarVenta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Ojo: este código es generado automáticamente, pone la vista y ya, pero
        // no tiene nada que ver con el código que vamos a escribir
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Lo siguiente sí es nuestro ;)
        // Definir nuestro controlador
        ventasController = new VentasController(MainActivity.this);

        // Instanciar vistas
        recyclerView = findViewById(R.id.recyclerViewVentas);
        fabAgregarVenta = findViewById(R.id.fabAgregarVenta);


        // Por defecto es una lista vacía,
        // se la ponemos al adaptador y configuramos el recyclerView
        listaDeVentas = new ArrayList<>();
        adaptadorVentas = new AdaptadorVentas(listaDeVentas);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptadorVentas);

        // Una vez que ya configuramos el RecyclerView le ponemos los datos de la BD
        refrescarListaDeVentas();

        // Listener de los clicks en la lista, o sea el RecyclerView
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override // Un toque sencillo
            public void onClick(View view, int position) {
                // Pasar a la actividad EditarMascotaActivity.java
                Venta ventaSeleccionada = listaDeVentas.get(position);
                Intent intent = new Intent(MainActivity.this, EditarMascotaActivity.class);
                intent.putExtra("idVenta", ventaSeleccionada.getId());
                intent.putExtra("productoVenta", ventaSeleccionada.getProducto());
                intent.putExtra("CantidadVenta", ventaSeleccionada.getCantidad());
                intent.putExtra("DescripcionVenta", ventaSeleccionada.getDescripcion());
                startActivity(intent);
            }

            @Override // Un toque largo
            public void onLongClick(View view, int position) {
                final Venta VentaParaEliminar = listaDeVentas.get(position);
                AlertDialog dialog = new AlertDialog
                        .Builder(MainActivity.this)
                        .setPositiveButton("Sí, eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ventasController.eliminarVentas(VentaParaEliminar);
                                refrescarListaDeVentas();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setTitle("Confirmar")
                        .setMessage("¿Eliminar el producto " + VentaParaEliminar.getProducto() + "?")
                        .create();
                dialog.show();

            }
        }));

        // Listener del FAB
        fabAgregarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Simplemente cambiamos de actividad
                Intent intent = new Intent(MainActivity.this, AgregarMascotaActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        refrescarListaDeVentas();
    }

    public void refrescarListaDeVentas() {

        if (adaptadorVentas == null) return;
        listaDeVentas = ventasController.obtenerVentas();
        adaptadorVentas.setListaDeMascotas(listaDeVentas);
        adaptadorVentas.notifyDataSetChanged();
    }
}
