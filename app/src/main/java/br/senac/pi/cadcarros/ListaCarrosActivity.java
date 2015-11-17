package br.senac.pi.cadcarros;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import br.senac.pi.cadcarros.domain.Carro;
import br.senac.pi.cadcarros.domain.CarrosDB;

public class ListaCarrosActivity extends AppCompatActivity {
    //codMeu
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
        listView.setOnItemClickListener(deletarItem());
    }

    private View.OnClickListener listarCarros() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor carros = database.query("carro", campos, null, null, null, null, null);
                if (carros.getCount() > 0) {

                    dataSource = new SimpleCursorAdapter(ListaCarrosActivity.this, R.layout.row, carros, campos, new int[]{R.id.txtNomeCarro, R.id.txtMarcaCarro});
                    listView.setAdapter(dataSource);
                } else {
                    Toast.makeText(ListaCarrosActivity.this, getString(R.string.zero_registros), Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    //recupera o item do banco pelo _id e faz o delete
    private AdapterView.OnItemClickListener deletarItem() {
        return new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long itemSelecionado = id;
                AlertDialog.Builder builder = new AlertDialog.Builder(ListaCarrosActivity.this);
                builder.setTitle("Pergunta");
                builder.setMessage("O que deseja fazer?");
                builder.setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String codigo;
                        Carro c = new Carro();
                        Cursor carro = database.query("carro",campos, null, null, null, null, null);
                        carro.moveToPosition(id);
                        codigo = carro.getString(carro.getColumnIndexOrThrow("_id"));
                        Intent intent = new Intent(getApplicationContext(),AlteraCarroActivity.class);
                        intent.putExtra("id",codigo);
                        startActivity(intent);
                        finish();
                        //Toast.makeText(ListaCarrosActivity.this, "Clicou em Editar", Toast.LENGTH_LONG).show();

                    }
                });
                builder.setNegativeButton("Deletar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        //long itemSelecionado = id;
                        Log.i("carro", "ID do item selecionado: " + itemSelecionado);
                        Carro carro = new Carro();
                        carro.setId(itemSelecionado);
                        carrosDB.delete(carro);

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();


            }
        };
    }
}
