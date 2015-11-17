package br.senac.pi.cadcarros;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import br.senac.pi.cadcarros.domain.CarrosDB;

public class AlteraCarroActivity extends AppCompatActivity {
    //inserido
    private CarrosDB carrosDB;
    private SQLiteDatabase db;
    private EditText edtAlteraNomeCarro, edtAlteraMarcaCarro;
    private TextView txtIdCarro;
    private String id;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_altera_carro);
        //inserido
        id = getIntent().getStringExtra("id");
        carrosDB = new CarrosDB(this);
        txtIdCarro = (TextView) findViewById(R.id.txtIdCarro);
        edtAlteraNomeCarro = (EditText) findViewById(R.id.edtAlteraNomeCarro);
        edtAlteraMarcaCarro = (EditText) findViewById(R.id.edtAlteraMarcaCarro);
        cursor = carregaCarro(Integer.parseInt(id));
        txtIdCarro.setText(cursor.getString(cursor.getColumnIndexOrThrow("_id")));
        edtAlteraNomeCarro.setText(cursor.getString(cursor.getColumnIndexOrThrow("nome")));
        edtAlteraMarcaCarro.setText(cursor.getString(cursor.getColumnIndexOrThrow("marca")));
        findViewById(R.id.btnAlterarCarro).setOnClickListener(alterarCarro());

    }
}
