package me.parzibyte.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.parzibyte.crudsqlite.controllers.VentasController;
import me.parzibyte.crudsqlite.modelos.Venta;

public class AgregarMascotaActivity extends AppCompatActivity {
    private Button btnAgregarVenta, btnCancelarNuevaVenta;
    private EditText etProducto, etCantidad, etDescripcion;
    private VentasController ventasController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_mascota);

        // Instanciar vistas
        etProducto = findViewById(R.id.etProducto);
        etCantidad = findViewById(R.id.etCantidad);
        etDescripcion = findViewById(R.id.etDescripcion);
        btnAgregarVenta = findViewById(R.id.btnAgregarVenta);
        btnCancelarNuevaVenta = findViewById(R.id.btnCancelarNuevaVenta);
        // Crear el controlador
        ventasController = new VentasController(AgregarMascotaActivity.this);

        // Agregar listener del botón de guardar
        btnAgregarVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Resetear errores a ambos
                etProducto.setError(null);
                etCantidad.setError(null);
                etDescripcion.setError(null);
                String producto = etProducto.getText().toString(),
                        CantidadComoCadena = etCantidad.getText().toString(),
                        descripcion = etDescripcion.getText().toString();
                if ("".equals(producto)) {
                    etProducto.setError("Escribe el producto a comprar");
                    etProducto.requestFocus();
                    return;
                }
                if ("".equals(CantidadComoCadena)) {
                    etCantidad.setError("Escribe la cantidad de la venta");
                    etCantidad.requestFocus();
                    return;
                }
                if ("".equals(descripcion)) {
                    etDescripcion.setError("Escribe la descripción de la venta");
                    etDescripcion.requestFocus();
                    return;
                }

                // Ver si es un entero
                int cantidad;
                try {
                    cantidad = Integer.parseInt(etCantidad.getText().toString());
                } catch (NumberFormatException e) {
                    etCantidad.setError("Escribe la cantidad");
                    etCantidad.requestFocus();
                    return;
                }
                // Ya pasó la validación
                Venta nuevaVenta = new Venta(producto,cantidad,descripcion);
                long id = ventasController.nuevaVenta(nuevaVenta);
                if (id == -1) {
                    // De alguna manera ocurrió un error
                    Toast.makeText(AgregarMascotaActivity.this, "Error al guardar. Intenta de nuevo", Toast.LENGTH_SHORT).show();
                } else {
                    // Terminar
                    finish();
                }
            }
        });

        // El de cancelar simplemente cierra la actividad
        btnCancelarNuevaVenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
