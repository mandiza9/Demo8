package pe.edu.cibertec.demo08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pe.edu.cibertec.demo08.adapter.recyclerview.RVPersonasAdapter;
import pe.edu.cibertec.demo08.dao.DataBaseHelper;
import pe.edu.cibertec.demo08.dao.PersonaDAO;
import pe.edu.cibertec.demo08.entities.Persona;

public class MainActivity extends AppCompatActivity implements RVPersonasAdapter.RVPersonasAdapterCallBack {

    private EditText etPrincipalFilter;
    private Button btPrincipalAdd;
    private RecyclerView rvPrincipal;
    private RVPersonasAdapter rvPersonasAdapter;
    private DataBaseHelper dataBaseHelper;


    private ArrayList<Persona> mLstPersona;
    public final static String ARG_PERSONA = "persona";
    private final static int REQUEST_CODE = 1;
    private final static int REQUEST_CODE_CLICK = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPrincipalFilter = (EditText) findViewById(R.id.etPrincipalFilter);
        btPrincipalAdd = (Button) findViewById(R.id.btPrincipalAdd);
        rvPrincipal = (RecyclerView) findViewById(R.id.rvPrincipal);

        btPrincipalAdd.setOnClickListener( btPrincipalAddOnClickListener );

        try {
            dataBaseHelper = new DataBaseHelper(MainActivity.this);
            dataBaseHelper.createDataBase();
            dataBaseHelper.openDataBase();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        rvPersonasAdapter = new RVPersonasAdapter(MainActivity.this);
        rvPrincipal.setHasFixedSize(true);
        rvPrincipal.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rvPrincipal.setAdapter(rvPersonasAdapter);

    }


    @Override
    public void onPersonaClick(Persona persona, int position) {
        //Toast.makeText(MainActivity.this, persona.getNombre(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra(ARG_PERSONA, persona);
        startActivityForResult(intent, REQUEST_CODE_CLICK);

    }

    //metodo del boton agregar
    View.OnClickListener btPrincipalAddOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, Main2Activity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ( requestCode == REQUEST_CODE && resultCode == RESULT_OK ){
            Persona persona = data.getParcelableExtra( ARG_PERSONA );

        }else if ( requestCode == REQUEST_CODE_CLICK && resultCode == RESULT_OK ) {
            Persona persona = data.getParcelableExtra(ARG_PERSONA);
        }

        RVPersonasAdapter lista = new RVPersonasAdapter(MainActivity.this);
        rvPrincipal.setAdapter( lista );
    }
}
