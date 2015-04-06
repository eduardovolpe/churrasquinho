package br.com.e3.churrasquinho;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo on 06/04/2015.
 */
public class DBAdapter {


    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private String[] colunas = {DBHelper.ID,DBHelper.NOME,DBHelper.VALOR};

    public DBAdapter(Context context){
        dbHelper = new DBHelper(context);
    }
    //abre a conexão
    public void open(){
        database = dbHelper.getWritableDatabase();
    }
    //encerra a conexão
    public void close(){
        database.close();
    }

    public void adicionar (String nomeCarne, double valorCarne){
        ContentValues contentValues = new ContentValues();

        // pega e atribui os dados
        contentValues.put(DBHelper.NOME, nomeCarne);
        contentValues.put(DBHelper.VALOR, valorCarne);

        //insere no banco
        database.insert(DBHelper.TABELA,null,contentValues);
    }

    // Verificar com o prof. Sergio o CURSOR
    public Cursor getVeiculos(){
        Cursor cursor = database.rawQuery(
                "select id,nomeCarne,valorCarne from " + DBHelper.TABELA, null);

        return cursor;

    }

    private Carne cursorCarne(Cursor cursor){
        Carne veiculo = new Carne(cursor.getLong(0),
                cursor.getString(1),
                cursor.getDouble(2)
        return carne;
    }
    public Carne getCarne(long id){
        Cursor cursor = database.query(DBHelper.TABELA,
                colunas, DBHelper.ID + " = " + id, null, null, null, null);

        cursor.moveToFirst();
        return cursorCarne(cursor);
    }

    public List<Carne> listar (){
        Cursor cursor = this.getCarne();
        List<Carne> lista = new ArrayList<Carne>();

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            Carne carne;

            while (!cursor.isAfterLast()){
                carne = cursorCarne(cursor);
                lista.add(carne);
                cursor.moveToNext();
            }
        }
        return lista;
    }

}
