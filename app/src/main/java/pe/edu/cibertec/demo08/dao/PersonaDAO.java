package pe.edu.cibertec.demo08.dao;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

import pe.edu.cibertec.demo08.entities.Persona;

/**
 * Created by luisrios on 9/5/15.
 */
public class PersonaDAO {

    public ArrayList<Persona> listPersona() {
        ArrayList<Persona> lstPersona = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = DataBaseHelper.myDataBase.query("Persona", null, null, null, null, null, null);

            if (cursor.moveToFirst()) {
                do {
                    Persona persona = new Persona();
                    persona.setIdPersona(cursor.isNull(cursor.getColumnIndex("IdPersona")) ? 0 : cursor.getInt(cursor.getColumnIndex("IdPersona")) );
                    persona.setNombre(cursor.isNull(cursor.getColumnIndex("Nombre")) ? "" : cursor.getString(cursor.getColumnIndex("Nombre")));
                    persona.setApellido(cursor.isNull(cursor.getColumnIndex("Apellido")) ? "" : cursor.getString(cursor.getColumnIndex("Apellido")));
                    persona.setEdad(cursor.isNull(cursor.getColumnIndex("Edad")) ? 0 : cursor.getInt(cursor.getColumnIndex("Edad")));
                    persona.setDNI(cursor.isNull(cursor.getColumnIndex("DNI")) ? "" : cursor.getString(cursor.getColumnIndex("DNI")));
                    lstPersona.add(persona);
                } while (cursor.moveToNext());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return lstPersona;
    }

    public void addPersona(Persona persona) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("Nombre", persona.getNombre());
            cv.put("Apellido", persona.getApellido());
            cv.put("Edad", persona.getEdad());
            cv.put("DNI", persona.getDNI());
            DataBaseHelper.myDataBase.insert("Persona", null, cv);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updatePersona(Persona persona) {
        try {
            ContentValues cv = new ContentValues();
            cv.put("Nombre", persona.getNombre());
            cv.put("Apellido", persona.getApellido());
            cv.put("Edad", persona.getEdad());
            cv.put("DNI", persona.getDNI());
            DataBaseHelper.myDataBase.update( "Persona", cv, "IdPersona = ?", new String[]{String.valueOf(persona.getIdPersona())} );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deletePersona(Persona persona) {
        try {
            DataBaseHelper.myDataBase.delete("Persona", "IdPersona = ?", new String[]{String.valueOf(persona.getIdPersona())});
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}