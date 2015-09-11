package pe.edu.cibertec.demo08;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import pe.edu.cibertec.demo08.dao.PersonaDAO;
import pe.edu.cibertec.demo08.entities.Persona;

public class Main2Activity extends AppCompatActivity {

    private Button btSecondGuardar;
    private Button btSecondEliminar;
    private TextInputLayout tilSecondNombre, tilSecondApellido, tilSecondEdad, tilSecondDNI;
    private int position = -1;
    private int idpersona = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btSecondGuardar = (Button) findViewById(R.id.btSecondGuardar);
        btSecondEliminar = (Button) findViewById(R.id.btSecondEliminar);

        tilSecondNombre = (TextInputLayout) findViewById(R.id.tilSecondNombre);
        tilSecondApellido = (TextInputLayout) findViewById(R.id.tilSecondApellido);
        tilSecondEdad = (TextInputLayout) findViewById(R.id.tilSecondEdad);
        tilSecondDNI = (TextInputLayout) findViewById(R.id.tilSecondDNI);

        //SI VIENE DESDE UN CLICK DE LA LISTA, LLENO MIS DATOS PARA EDITAR
        if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(MainActivity.ARG_PERSONA)) {
            Persona persona = getIntent().getParcelableExtra(MainActivity.ARG_PERSONA);
            tilSecondNombre.getEditText().setText(persona.getNombre());
            tilSecondApellido.getEditText().setText(persona.getApellido());
            tilSecondEdad.getEditText().setText(String.valueOf(persona.getEdad()));
            tilSecondDNI.getEditText().setText(persona.getDNI());
            idpersona = persona.getIdPersona();
            btSecondEliminar.setEnabled( true );
        }


        btSecondGuardar.setOnClickListener( btSecondGuardarOnClickListener );
        btSecondEliminar.setOnClickListener( btSecondEliminarOnClickListener );

    }

    //BOTON DE AGREGAR Y ACTUALIZAR
    View.OnClickListener btSecondGuardarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean isCorrect = true;
            Persona persona = new Persona();

            tilSecondNombre.setErrorEnabled(false);
            tilSecondApellido.setErrorEnabled(false);
            tilSecondEdad.setErrorEnabled(false);
            tilSecondDNI.setErrorEnabled(false);

            if (tilSecondNombre.getEditText().getText().toString().trim().length() <= 0) {
                tilSecondNombre.setError("Ingrese un nombre");
                tilSecondNombre.setErrorEnabled(true);
                isCorrect = false;
            } else
                persona.setNombre(tilSecondNombre.getEditText().getText().toString().trim());

            if (tilSecondApellido.getEditText().getText().toString().trim().length() <= 0) {
                tilSecondApellido.setError("Ingrese un apellido");
                tilSecondApellido.setErrorEnabled(true);
                isCorrect = false;
            } else
                persona.setApellido(tilSecondApellido.getEditText().getText().toString().trim());

            if (tilSecondEdad.getEditText().getText().toString().trim().length() <= 0) {
                tilSecondEdad.setError("Ingrese una edad");
                tilSecondEdad.setErrorEnabled(true);
                isCorrect = false;
            } else
                persona.setEdad(Integer.parseInt(tilSecondEdad.getEditText().getText().toString().trim()));

            if (tilSecondDNI.getEditText().getText().toString().trim().length() != 8) {
                tilSecondDNI.setError("Ingrese un DNI válido");
                tilSecondDNI.setErrorEnabled(true);
                isCorrect = false;
            } else
                persona.setDNI(tilSecondDNI.getEditText().getText().toString().trim());

            if (isCorrect) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.ARG_PERSONA, persona);
                setResult(RESULT_OK, intent);

                PersonaDAO consultas = new PersonaDAO();
                if ( idpersona != 0 ){
                    persona.setIdPersona( idpersona );
                    consultas.updatePersona( persona );
                }else
                    consultas.addPersona( persona );
                finish();
            }

        }
    };

    View.OnClickListener btSecondEliminarOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            PersonaDAO consultas = new PersonaDAO();

            if (idpersona != 0) {
                Persona personaeliminar = new Persona();
                personaeliminar.setIdPersona( idpersona );
                consultas.deletePersona(personaeliminar );
                finish();
            }
        }
    };

}
