package br.senac.pi.cadcarros;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.senac.pi.cadcarros.domain.Carro;
import br.senac.pi.cadcarros.domain.CarrosDB;

public class MainActivity extends AppCompatActivity {

    CarrosDB carrosDB;
    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carrosDB = new CarrosDB(this);

        findViewById(R.id.btnCadastrar).setOnClickListener(cadastrarCarro());
        findViewById(R.id.btnListarCarros).setOnClickListener(listagemCarros());
    }

    private View.OnClickListener cadastrarCarro(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database = carrosDB.getWritableDatabase();
                EditText edtCarro = (EditText) findViewById(R.id.edtCarro);
                EditText edtFabricante = (EditText) findViewById(R.id.edtFabricante);
                ContentValues values = new ContentValues();
                values.put("nome",edtCarro.getText().toString());
                values.put("marca",edtFabricante.getText().toString());

                long id = database.insert("carro", null, values);

                if (id != 0){
                    Toast.makeText(getApplicationContext(),getString(R.string.cad_ok),Toast.LENGTH_LONG).show();
                    //depois de cadastrar mostra os campos limpos para outro cadastro com foco no edtCarro
                    edtCarro.setText("");
                    edtFabricante.setText("");
                    edtCarro.requestFocus();
                }else {
                    Toast.makeText(getApplicationContext(),getString(R.string.cad_erro),Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    private View.OnClickListener listagemCarros(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ListaCarrosActivity.class);
                startActivity(intent);
            }
        };
    }


    /*private View.OnClickListener cadastrar() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText edtCarro = (EditText) findViewById(R.id.edtCarro);
                EditText edtFabricante = (EditText) findViewById(R.id.edtFabricante);

                String strCarro = edtCarro.getText().toString();
                String strFabricante = edtFabricante.getText().toString();

                //Criando e instanciando um objeto da classe Carro
                Carro carro = new Carro();

                carro.setNome(strCarro);
                carro.setMarca(strFabricante);

                //Criando e instanciando um objeto da classe CarrosDB

                CarrosDB carroDB = new CarrosDB(MainActivity.this);


                if (carroDB.save(carro) != -1){

                    Toast toastCadastro = Toast.makeText(MainActivity.this,getString(R.string.cad_ok),Toast.LENGTH_LONG);
                    toastCadastro.show();
                    edtCarro.setText("");
                    edtFabricante.setText("");
                    edtCarro.requestFocus();
                }else {
                    Toast toastCadastro = Toast.makeText(MainActivity.this, getString(R.string.cad_erro),Toast.LENGTH_LONG);
                    toastCadastro.show();
                }



            }
        };
    }*/

}
