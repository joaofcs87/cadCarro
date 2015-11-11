package br.senac.pi.cadcarros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import br.senac.pi.cadcarros.domain.Carro;
import br.senac.pi.cadcarros.domain.CarrosDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.btnCadastrar).setOnClickListener(cadastrar());
        findViewById(R.id.btnListarCarros).setOnClickListener(listagemCarros());
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

    private View.OnClickListener cadastrar() {
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
    }
}
