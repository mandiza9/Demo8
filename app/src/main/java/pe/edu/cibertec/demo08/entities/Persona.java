package pe.edu.cibertec.demo08.entities;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by luisrios on 9/5/15.
 */
public class Persona implements Parcelable {
    private int idPersona;
    private String nombre;
    private String apellido;
    private int edad;
    private String DNI;

    public int getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(int idPersona) {
        this.idPersona = idPersona;
    }

    public Persona() {
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        this.DNI = DNI;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    protected Persona(Parcel in) {
        idPersona = in.readInt();
        nombre = in.readString();
        apellido = in.readString();
        edad = in.readInt();
        DNI = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(idPersona);
        dest.writeString(nombre);
        dest.writeString(apellido);
        dest.writeInt(edad);
        dest.writeString(DNI);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Persona> CREATOR = new Parcelable.Creator<Persona>() {
        @Override
        public Persona createFromParcel(Parcel in) {
            return new Persona(in);
        }

        @Override
        public Persona[] newArray(int size) {
            return new Persona[size];
        }
    };
}