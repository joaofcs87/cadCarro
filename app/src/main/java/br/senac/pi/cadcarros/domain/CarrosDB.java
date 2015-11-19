package br.senac.pi.cadcarros.domain;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aluno on 06/11/2015.
 */
public class CarrosDB extends SQLiteOpenHelper {

    private static final String TAG = "sql";
    //nome do banco
    private static final String NOME_BANCO = "cursoandroid.sqlite";
    private static final int VERSAO_BANCO = 1;

    public CarrosDB(Context context) {
        //context,nome do banco, factory, versao
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando a tabela carros...");
        db.execSQL("CREATE TABLE IF NOT EXISTS carro(" +
                "_id integer primary key autoincrement," +
                "nome text unique," +
                "marca text)");
        Log.d(TAG, "Tabela carro criada com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //caso mude a versão do banco de dados o  SQL de atualização deve ser executado aqui

    }
    //
    public long save(Carro carro) {
        long id = carro.getId();
        //abrindo o bd em forma de escrita
        SQLiteDatabase db = getWritableDatabase();
        try {
            //ContentValues responsavel por pegar os dados informados pelo usuário
            ContentValues values = new ContentValues();
            //
            values.put("nome", carro.getNome());
            values.put("marca", carro.getMarca());
            if (id != 0) {
                String _id = String.valueOf(carro.getId());
                String[] whereArgs = new String[]{_id};
                //update carro set values = ... where id = ?
                int count = db.update("carro", values, "_id=?", whereArgs);
                return count;
            } else {
                //insert into carro values (...)
                id = db.insert("carro", "", values);
                return id;
            }

        } finally {
            db.close();

        }
    }

    public int delete(Carro carro){

        SQLiteDatabase db = getWritableDatabase();
        try {

            //delete from carro where _id=?
            int count = db.delete("carro","_id=?",new String[]{String.valueOf(carro.getId())});
            Log.i(TAG, "Deletou [" + count + "] registro.");
            return count;

        }finally {

            db.close();
        }
    }

    //listar - consultar com todos os carros

    public List<Carro> findAll(){
        SQLiteDatabase db = getWritableDatabase();
        try{

            //select * from carro
            Cursor cursor = db.query("carro", null, null, null, null, null, null, null);
            return toList(cursor);

        }finally {

            db.close();

        }
    }

    public List<Carro> toList (Cursor cursor){

        List<Carro> carros = new ArrayList<Carro>();
        if(cursor.moveToFirst()){
            do{
                Carro carro = new Carro();
                carros.add(carro);
                //recupera os atributos de carro
                carro.setId(cursor.getLong(cursor.getColumnIndex("_id")));
                carro.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                carro.setMarca(cursor.getString(cursor.getColumnIndex("marca")));
            } while(cursor.moveToNext());

        }
        return carros;
    }

}
