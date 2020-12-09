package me.parzibyte.crudsqlite.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import me.parzibyte.crudsqlite.AyudanteBaseDeDatos;
import me.parzibyte.crudsqlite.modelos.Venta;


public class VentasController {
    private AyudanteBaseDeDatos ayudanteBaseDeDatos;
    private String NOMBRE_TABLA = "Ventas";

    public VentasController(Context contexto) {
        ayudanteBaseDeDatos = new AyudanteBaseDeDatos(contexto);
    }


    public int eliminarVentas(Venta ventas) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        String[] argumentos = {String.valueOf(ventas.getId())};
        return baseDeDatos.delete(NOMBRE_TABLA, "id = ?", argumentos);
    }

    public long nuevaVenta(Venta ventas) {
        // writable porque vamos a insertar
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaInsertar = new ContentValues();
        valoresParaInsertar.put("producto", ventas.getProducto());
        valoresParaInsertar.put("cantidad", ventas.getCantidad());
        valoresParaInsertar.put("descripcion", ventas.getDescripcion());
        return baseDeDatos.insert(NOMBRE_TABLA, null, valoresParaInsertar);
    }

    public int guardarCambios(Venta ventaEditada) {
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getWritableDatabase();
        ContentValues valoresParaActualizar = new ContentValues();
        valoresParaActualizar.put("producto", ventaEditada.getProducto());
        valoresParaActualizar.put("cantidad", ventaEditada.getCantidad());
        valoresParaActualizar.put("descripcion", ventaEditada.getDescripcion());
        // where id...
        String campoParaActualizar = "id = ?";
        // ... = idMascota
        String[] argumentosParaActualizar = {String.valueOf(ventaEditada.getId())};
        return baseDeDatos.update(NOMBRE_TABLA, valoresParaActualizar, campoParaActualizar, argumentosParaActualizar);
    }

    public ArrayList<Venta> obtenerVentas() {
        ArrayList<Venta> ventas = new ArrayList<>();
        // readable porque no vamos a modificar, solamente leer
        SQLiteDatabase baseDeDatos = ayudanteBaseDeDatos.getReadableDatabase();
        // SELECT nombre, edad, id
        String[] columnasAConsultar = {"producto", "cantidad", "descripcion", "id"};
        Cursor cursor = baseDeDatos.query(
                NOMBRE_TABLA,//from mascotas
                columnasAConsultar,
                null,
                null,
                null,
                null,
                null
        );

        if (cursor == null) {
            /*
                Salimos aquí porque hubo un error, regresar
                lista vacía
             */
            return ventas;

        }
        // Si no hay datos, igualmente regresamos la lista vacía
        if (!cursor.moveToFirst()) return ventas;

        // En caso de que sí haya, iteramos y vamos agregando los
        // datos a la lista de mascotas
        do {
            // El 0 es el número de la columna, como seleccionamos
            // nombre, edad,id entonces el nombre es 0, edad 1 e id es 2
            String productoObtenidoDeBD = cursor.getString(0);
            int cantidadObtenidaDeBD = cursor.getInt(1);
            String descripcionObtenidoDeBD = cursor.getString(2);
            long idVenta = cursor.getLong(3);
            Venta ventaObtenidaDeBD = new Venta(productoObtenidoDeBD, cantidadObtenidaDeBD, descripcionObtenidoDeBD, idVenta);
            ventas.add(ventaObtenidaDeBD);
        } while (cursor.moveToNext());

        // Fin del ciclo. Cerramos cursor y regresamos la lista de mascotas :)
        cursor.close();
        return ventas;
    }
}