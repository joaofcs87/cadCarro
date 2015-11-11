package br.senac.pi.cadcarros;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.cadcarros.domain.CarrosDB;

public class ListaCarrosActivity extends AppCompatActivity {

    private CursorAdapter dataSource;
    private SQLiteDatabase database;
    private static final String campos[] = {"nome", "marca", "_id"};
    ListView listView;
    CarrosDB carrosDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_carros);
        //codMeu
        listView = (ListView) findViewById(R.id.listView);
        carrosDB = new CarrosDB(this);
        database = carrosDB.getWritableDatabase();
        findViewById(R.id.btnListarCarros).setOnClickListener(listarCarros());
    }

    private View.OnClickListener listarCarros() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor carros = database.query("carro", campos, null, null, null, null, null);
                if (carros.getCount() > 0){

                    dataSource = new SimpleCursorAdapter(ListaCarrosActivity.this,R.layout.row, carros, campos, new int[]{R.id.txtNomeCarro,R.id.txtMarcaCarro});
                    listView.setAdapter(dataSource);
                }else{
                    Toast.makeText(ListaCarrosActivity.this,getString(R.string.zero_registros),Toast.LENGTH_LONG).show();
                }
            }
        };
    }
}
