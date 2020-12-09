package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.VentasController;
import me.parzibyte.crudsqlite.modelos.Venta;

public class EditarMascotaActivity extends AppCompatActivity {
    private EditText etProducto, etCantidad, etDescripcion;
    private Button btnGuardarCambios, btnCancelarEdicion;
    private Venta venta;
    private VentasController ventasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_mascota);

        // Recuperar datos que enviaron
        Bundle extras = getIntent().getExtras();
        // Si no hay datos (cosa rara) salimos
        if (extras == null) {
            finish();
            return;
        }
        // Instanciar el controlador de las mascotas
        ventasController = new VentasController(EditarMascotaActivity.this);

        long idVenta = extras.getLong("idVenta");
        String productoVenta = extras.getString("productoVenta");
        int cantidadVenta = extras.getInt("CantidadVenta");
        String descripcionVenta = extras.getString("DescripcionVenta");
        venta = new Venta (productoVenta, cantidadVenta, descripcionVenta, idVenta);


        // Ahora declaramos las vistas
        etProducto = findViewById(R.id.etProducto);
        etCantidad = findViewById(R.id.etCantidad);
        etDescripcion = findViewById(R.id.etDescripcion);

        btnCancelarEdicion = findViewById(R.id.btnCancelarEdicionVenta);
        btnGuardarCambios = findViewById(R.id.btnGuardarCambiosVenta);

        // Rellenar los EditText con los datos de la mascota
        etProducto.setText(venta.getProducto());
        etCantidad.setText(String.valueOf(cantidadVenta));
        etDescripcion.setText(venta.getDescripcion());

        // Listener del click del botón para salir, simplemente cierra la actividad
        btnCancelarEdicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener del click del botón que guarda cambios
        btnGuardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Remover previos errores si existen
                etProducto.setError(null);
                etCantidad.setError(null);
                etDescripcion.setError(null);
                // Crear la mascota con los nuevos cambios pero ponerle
                // el id de la anterior
                String nuevoProducto = etProducto.getText().toString();
                String nuevaCantidad = etCantidad.getText().toString();
                String nuevaDescripcion = etDescripcion.getText().toString();
                if (nuevoProducto.isEmpty()) {
                    etProducto.setError("Escribe el producto");
                    etProducto.requestFocus();
                    return;
                }
                if (nuevaCantidad.isEmpty()) {
                    etCantidad.setError("Escribe la cantidad");
                    etCantidad.requestFocus();
                    return;
                }
                if (nuevaDescripcion.isEmpty()) {
                    etDescripcion.setError("Escribe la descripción");
                    etDescripcion.requestFocus();
                    return;
                }

                int nueva_Cantidad;
                try {
                    nueva_Cantidad = Integer.parseInt(nuevaCantidad);
                } catch (NumberFormatException e) {
                    etCantidad.setError("Escribe una cantidad");
                    etCantidad.requestFocus();
                    return;
                }
                // Si llegamos hasta aquí es porque los datos ya están validados
                Venta ventaModificada = new Venta(nuevoProducto, Integer.parseInt(nuevaCantidad), nuevaDescripcion, venta.getId());
                int filasModificadas = ventasController.guardarCambios(ventaModificada);
                if (filasModificadas != 1) {
                    // De alguna forma ocurrió un error porque se debió modificar únicamente una fila
                    Toast.makeText(EditarMascotaActivity.this, "Error guardando cambios. Intente de nuevo.", Toast.LENGTH_SHORT).show();
                } else {
                    // Si las cosas van bien, volvemos a la principal
                    // cerrando esta actividad
                    finish();
                }
            }
        });
    }
}
