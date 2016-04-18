package com.example.nico.calculoestructuras.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nico.calculoestructuras.Negocio.Barra;
import com.example.nico.calculoestructuras.Negocio.CargaEnBarra;
import com.example.nico.calculoestructuras.Negocio.CargaEnNudo;
import com.example.nico.calculoestructuras.Negocio.Conectividad;
import com.example.nico.calculoestructuras.Negocio.Nudo;
import com.example.nico.calculoestructuras.Negocio.Vinculo;

import java.util.ArrayList;

/**
 * Created by Nico on 9/11/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    //Nombre tablas
    private final static String DATABASE_NAME_BARRA_TABLE = "Barras";
    private final static String DATABASE_NAME_NUDO_TABLE = "Nudos";
    private final static String DATABASE_NAME_CONECTIV_TABLE = "Conectividades";
    private final static String DATABASE_NAME_VINCULOS_TABLE = "Vinculos";
    private final static String DATABASE_NAME_CARGAENNUDO_TABLE = "CargasEnNudos";
    private final static String DATABASE_NAME_CARGAENBARRA_TABLE = "CargasEnBarras";

    //Sentencias de creaci�n
    private final static String DATABASE_CREATE_STATEMENT_NUDO="CREATE TABLE [Nudos] (" +
            "[idnudo] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[coordx] FLOAT  NOT NULL, " +
            "[coordy] FLOAT  NOT NULL)";
    private final static String DATABASE_CREATE_STATEMENT_BARRA="CREATE TABLE [Barras] (" +
            "[idbarra] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[elasticidad] FLOAT  NOT NULL," +
            "[inercia] FLOAT  NOT NULL," +
            "[area] FLOAT  NOT NULL)";
    private final static String DATABASE_CREATE_STATEMENT_VINCULO="CREATE TABLE [Vinculos] " +
            "([idvinculo] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[nudovinculado] INTEGER   NOT NULL REFERENCES nudo(idnudo)," +
            "[restx] FLOAT," +
            "[resty] FLOAT," +
            "[restgiro] FLOAT)";

    private final static String DATABASE_CREATE_STATEMENT_CONECTIVIDAD="CREATE TABLE [Conectividades] (" +
            "[idconectividad] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[barraconectada] INTEGER   NOT NULL REFERENCES barras(idbarra)," +
            "[niconec] INTEGER   NOT NULL REFERENCES nudos(idnudo), " +
            "[nfconec] INTEGER NOT NULL REFERENCES nudos (idnudo))";
    private final static String DATABASE_CREATE_STATEMENT_CARGAENNUDO="CREATE TABLE [CargasEnNudos] (" +
            "[idcargaNudo] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[nudoCargado] INTEGER   NOT NULL REFERENCES nudos(idnudo)," +
            "[cargaenx] FLOAT, " +
            "[cargaeny] FLOAT, "+
            "[cargaenz] FLOAT)";
    private final static String DATABASE_CREATE_STATEMENT_CARGASENBARRAS="CREATE TABLE [CargasEnBarras] (" +
            "[idcargabarra] INTEGER  NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "[barracargada] INTEGER   NOT NULL REFERENCES barra(idbarra)," +
            "[cargadistribuida] FLOAT, " +
            "[cargapuntualdistanudo] FLOAT, "+
            "[cargapuntualenx] FLOAT, "+
            "[cargapuntualeny] FLOAT, "+
            "[cargapuntualenz] FLOAT)";

    //Sentencias de eliminaci�n
    private final static String DATABASE_NAME = "databaseMath";
    private final static String DATABASE_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME;
    private final static String TABLE_NUDOS_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_NUDO_TABLE;
    private final static String TABLE_BARRAS_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_BARRA_TABLE;
    private final static String TABLE_CONECTIV_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_CONECTIV_TABLE;
    private final static String TABLE_VINCULOS_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_VINCULOS_TABLE;
    private final static String TABLE_CARGAENNUDO_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_CARGAENNUDO_TABLE;
    private final static String TABLE_CARGAENBARRA_DROP_STATEMENT = "DROP TABLE IF EXISTS " + DATABASE_NAME_CARGAENBARRA_TABLE;

    private Context context;

    private static DataBaseHelper databaseInstance;

    public static DataBaseHelper getDatabaseInstance(Context context)
    {
        if(databaseInstance == null)
        {
            databaseInstance = new DataBaseHelper(context.getApplicationContext());
        }
        return databaseInstance;
    }

    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, name, factory, version);
        this.context = context;
    }

    private DataBaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(DATABASE_CREATE_STATEMENT_NUDO);
        db.execSQL(DATABASE_CREATE_STATEMENT_BARRA);
        db.execSQL(DATABASE_CREATE_STATEMENT_CONECTIVIDAD);
        db.execSQL(DATABASE_CREATE_STATEMENT_VINCULO);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGAENNUDO);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGASENBARRAS);
    }


    private void crearTablaNudo()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_NUDO);
    }

    private void crearTablaBarra()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_BARRA);
    }

    private void crearTablaConec()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_CONECTIVIDAD);
    }
    private void crearTablaVinculo()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_VINCULO);
    }
    private void crearTablaCargaEnNudo()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_CARGAENNUDO);
    }
    private void crearTablaCargaEnBarra()
    {
        databaseInstance.getWritableDatabase().execSQL(DATABASE_CREATE_STATEMENT_CARGASENBARRAS);
    }

    private void borrarTablaNudo()
    {
        databaseInstance.getWritableDatabase().execSQL(TABLE_NUDOS_DROP_STATEMENT);
    }

    private void borrarTablaBarra()
    {
        databaseInstance.getWritableDatabase().execSQL(TABLE_BARRAS_DROP_STATEMENT);
    }

    private void borrarTablaConec()
    {
        databaseInstance.getWritableDatabase().execSQL(TABLE_CONECTIV_DROP_STATEMENT);
    }

    private void borrarTablaViculo(){
        databaseInstance.getWritableDatabase().execSQL(TABLE_VINCULOS_DROP_STATEMENT);
    }
    private void borrarTablaCargaEnNudo(){
        databaseInstance.getWritableDatabase().execSQL(TABLE_CARGAENNUDO_DROP_STATEMENT);
    }
    private void borrarTablaCargaEnBarra(){
        databaseInstance.getWritableDatabase().execSQL(TABLE_CARGAENBARRA_DROP_STATEMENT);
    }

    public void inicializarBD()
    {
        this.borrarTablaConec();
        this.borrarTablaNudo();
        this.borrarTablaBarra();
        this.borrarTablaViculo();
        this.borrarTablaCargaEnBarra();
        this.borrarTablaCargaEnNudo();
        this.crearTablaNudo();
        this.crearTablaBarra();
        this.crearTablaConec();
        this.crearTablaVinculo();
        this.crearTablaCargaEnBarra();
        this.crearTablaCargaEnNudo();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL(DATABASE_DROP_STATEMENT);
        db.execSQL(TABLE_BARRAS_DROP_STATEMENT);
        db.execSQL(TABLE_NUDOS_DROP_STATEMENT);
        db.execSQL(TABLE_CONECTIV_DROP_STATEMENT);
        db.execSQL(TABLE_VINCULOS_DROP_STATEMENT);
        db.execSQL(TABLE_CARGAENBARRA_DROP_STATEMENT);
        db.execSQL(TABLE_CARGAENNUDO_DROP_STATEMENT);
        db.execSQL(DATABASE_CREATE_STATEMENT_BARRA);
        db.execSQL(DATABASE_CREATE_STATEMENT_NUDO);
        db.execSQL(DATABASE_CREATE_STATEMENT_VINCULO);
        db.execSQL(DATABASE_CREATE_STATEMENT_CONECTIVIDAD);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGASENBARRAS);
        db.execSQL(DATABASE_CREATE_STATEMENT_CARGAENNUDO);
    }

    public ArrayList<Barra> getBarrasFromDB()
    {
        Cursor cursor = null;
        ArrayList<Barra> array = new ArrayList<>();
        cursor = getReadableDatabase().query(DATABASE_NAME_BARRA_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Barra b = new Barra(cursor.getInt(cursor.getColumnIndex("idbarra")),cursor.getDouble(cursor.getColumnIndex("elasticidad")),cursor.getDouble(cursor.getColumnIndex("area")),cursor.getDouble(cursor.getColumnIndex("inercia")));
            array.add(b);
        }
        this.close();
        return array;
    }
    public ArrayList<Nudo> getNudosFromDB()
    {
        ArrayList<Nudo> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_NUDO_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Nudo n = new Nudo(cursor.getInt(cursor.getColumnIndex("idnudo")), cursor.getDouble(cursor.getColumnIndex("coordx")),cursor.getDouble(cursor.getColumnIndex("coordy")));
            array.add(n);
        }
        this.close();
        return array;
    }
    public ArrayList<Conectividad> getConecFromDB()
    {
        Cursor cursor = null;
        ArrayList<Conectividad> array = new ArrayList<>();
        cursor = getReadableDatabase().query(DATABASE_NAME_CONECTIV_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Conectividad c = new Conectividad(cursor.getInt(cursor.getColumnIndex("barraconectada")),cursor.getInt(cursor.getColumnIndex("niconec")), cursor.getInt(cursor.getColumnIndex("nfconec")));
            array.add(c);
        }
        this.close();
        return array;
    }

    public ArrayList<Vinculo> getVinculoFromDB(){
        ArrayList<Vinculo> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_VINCULOS_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {

            Vinculo v = new Vinculo( cursor.getInt(cursor.getColumnIndex("nudovinculado")));
            if(cursor.getDouble(cursor.getColumnIndex("restx"))!=0){
                v.setRestX(cursor.getDouble(cursor.getColumnIndex("restx")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("resty"))!=0){
                v.setRestY(cursor.getDouble(cursor.getColumnIndex("resty")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("restgiro"))!=0){
                v.setRestGiro(cursor.getDouble(cursor.getColumnIndex("restgiro")));
            }
            array.add(v);
        }
        this.close();
        return array;
    }
    public ArrayList<CargaEnBarra> getCargaEnBarraFromDB(){
        ArrayList<CargaEnBarra> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_CARGAENBARRA_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {

            CargaEnBarra v = new CargaEnBarra( cursor.getInt(cursor.getColumnIndex("barracargada")));
            if(cursor.getDouble(cursor.getColumnIndex("cargadistribuida"))!= 0){
                v.setCargaDirstribuida(cursor.getDouble(cursor.getColumnIndex("cargadistribuida")));

            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualdistanudo"))!=0){
                v.setCargaPuntualDistANudo(cursor.getDouble(cursor.getColumnIndex("cargapuntualdistanudo")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualenx"))!=0){
                v.setCargaPuntualEnX(cursor.getDouble(cursor.getColumnIndex("cargapuntualenx")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualeny"))!=0){
                v.setCargaPuntualEnY(cursor.getDouble(cursor.getColumnIndex("cargapuntualeny")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargapuntualenz")) !=0){
                v.setCargaPuntualEnZ(cursor.getDouble(cursor.getColumnIndex("cargapuntualenz")));
            }
            array.add(v);
        }
        this.close();
        return array;
    }
    public ArrayList<CargaEnNudo> getCargaEnNudoFromDB(){
        ArrayList<CargaEnNudo> array = new ArrayList<>();
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_CARGAENNUDO_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {

            CargaEnNudo v = new CargaEnNudo( cursor.getInt(cursor.getColumnIndex("nudocargado")));
            if(cursor.getDouble(cursor.getColumnIndex("cargaenx")) != 0) {
                v.setCargaEnX(cursor.getDouble(cursor.getColumnIndex("cargaenx")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargaeny")) != 0) {
                v.setCargaEnY(cursor.getDouble(cursor.getColumnIndex("cargaeny")));
            }
            if(cursor.getDouble(cursor.getColumnIndex("cargaenz")) != 0) {
                v.setCargaEnZ(cursor.getDouble(cursor.getColumnIndex("cargaenz")));
            }
            array.add(v);
        }
        this.close();
        return array;
    }
    public void insertBarra(ContentValues values)
    {
        getWritableDatabase().insert(DATABASE_NAME_BARRA_TABLE, null, values);
        this.close();
    }
    public void insertNudo(ContentValues values)
    {
        getWritableDatabase().insert(DATABASE_NAME_NUDO_TABLE, null, values);
        this.close();
    }
    public void insertConec(ContentValues values)
    {

        getWritableDatabase().insert(DATABASE_NAME_CONECTIV_TABLE, null, values);
        this.close();
    }

    public void updateConec(ContentValues values, int nroConec){
        getWritableDatabase().update(DATABASE_NAME_CONECTIV_TABLE, values,"idconectividad = ?", new String[]{String.valueOf(nroConec)} );
    }

    public void insertVinculo(ContentValues values){

        getWritableDatabase().insert(DATABASE_NAME_VINCULOS_TABLE, null, values);
        this.close();
    }
    public void insertCargaEnBarra(ContentValues values){
        getWritableDatabase().insert(DATABASE_NAME_CARGAENBARRA_TABLE,null,values);
        this.close();
    }
    public void insertCargaEnNudo(ContentValues values){
        getWritableDatabase().insert(DATABASE_NAME_CARGAENNUDO_TABLE,null,values);
        this.close();
    }

    public void getNudos()
    {
        Cursor cursor = null;
        cursor = getReadableDatabase().query(DATABASE_NAME_NUDO_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Log.d("NUDO: ", " x: " + cursor.getDouble(cursor.getColumnIndex("coordx")) + " y: " + cursor.getDouble(cursor.getColumnIndex("coordy")));
        }
        this.close();
    }


    public void getBarras()
    {
        Cursor cursor = null;

        cursor = getReadableDatabase().query(DATABASE_NAME_BARRA_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Log.d("BARRA: ", "id: " + cursor.getInt(cursor.getColumnIndex("idbarra")) + " i: " + cursor.getDouble(cursor.getColumnIndex("inercia")) + " e: " + cursor.getDouble(cursor.getColumnIndex("elasticidad")) + " a: " + cursor.getDouble(cursor.getColumnIndex("area")));
        }
        this.close();
    }

    public Barra findBarraFromDB(int idbarra)
    {
        Cursor cursor = null;
        Barra b = new Barra(1,1.1,1.1,1.1);
        String [] args = new String[1];
        args[0] = " " + idbarra;
        cursor =getReadableDatabase().rawQuery("SELECT * FROM " + DATABASE_NAME_BARRA_TABLE + " WHERE idbarra=?", args);
        while(cursor.moveToNext())
        {
            b=new Barra(cursor.getInt(cursor.getColumnIndex("idbarra")),cursor.getDouble(cursor.getColumnIndex("elasticidad")),cursor.getDouble(cursor.getColumnIndex("area")),cursor.getDouble(cursor.getColumnIndex("inercia")));

        }
        this.close();
        return b;
    }
    public int findLastIDBarraInserted()
    {
        Cursor cursor = null;
        int index=0;
        Barra b = new Barra(1,1.1,1.1,1.1);
        cursor =getReadableDatabase().rawQuery("SELECT MAX(idbarra) FROM " + DATABASE_NAME_BARRA_TABLE,null);
        while(cursor.moveToNext())
        {

            index = cursor.getInt(0);
        }
        this.close();
        return index;
    }




    public void getConec()
    {
        Cursor cursor = null;

        cursor = getReadableDatabase().query(DATABASE_NAME_CONECTIV_TABLE, null, null, null, null, null, null);
        while(cursor.moveToNext())
        {
            Log.d("CONECT: ", "id: " + cursor.getInt(cursor.getColumnIndex("idconectividad")) + " B: " + cursor.getInt(cursor.getColumnIndex("barraconectada")) + " NI: " + cursor.getInt(cursor.getColumnIndex("niconec")) + " NF: " + cursor.getInt(cursor.getColumnIndex("nfconec")));
        }
        this.close();
    }


}
