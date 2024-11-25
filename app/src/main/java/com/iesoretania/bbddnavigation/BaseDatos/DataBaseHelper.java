package com.iesoretania.bbddnavigation.BaseDatos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.iesoretania.bbddnavigation.Alumno;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {

    // Constantes para la base de datos
    private static final String DATABASE_NAME = "escuela.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "Alumnos";
    public static final String COLUMN_DNI = "dni";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDOS = "apellidos";
    public static final String COLUMN_SEXO = "sexo";

    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_DNI + " TEXT PRIMARY KEY, " +
                    COLUMN_NOMBRE + " TEXT NOT NULL, " +
                    COLUMN_APELLIDOS + " TEXT NOT NULL, " +
                    COLUMN_SEXO + " TEXT NOT NULL" +
                    ");";

    // Constructor
    public DataBaseHelper( Context context ) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    // Métodos de la clase SQLiteOpenHelper
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL( CREATE_TABLE );
    }

    @Override
    public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
    {
        // Elimina la tabla si ya existe y crea una nueva
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate( db );
    }

    // Operaciones CRUD
    public long insertarAlumno(Alumno alumno)
    {
        SQLiteDatabase db = getWritableDatabase( );
        ContentValues contentValues = new ContentValues( );

        contentValues.put( DataBaseHelper.COLUMN_DNI, alumno.getDni( ) );
        contentValues.put( DataBaseHelper.COLUMN_NOMBRE, alumno.getNombre( ) );
        contentValues.put( DataBaseHelper.COLUMN_APELLIDOS, alumno.getApellidos( ) );
        contentValues.put( DataBaseHelper.COLUMN_SEXO, alumno.getSexo( ) );

        return db.insert( DataBaseHelper.TABLE_NAME, null, contentValues );
    }

    public int actualizarAlumno( Alumno alumno )
    {
        SQLiteDatabase db = getWritableDatabase( );
        ContentValues contentValues = new ContentValues( );

        contentValues.put( DataBaseHelper.COLUMN_NOMBRE, alumno.getNombre( ) );
        contentValues.put( DataBaseHelper.COLUMN_APELLIDOS, alumno.getApellidos( ) );
        contentValues.put( DataBaseHelper.COLUMN_SEXO, alumno.getSexo( ) );

        String whereClause = DataBaseHelper.COLUMN_DNI + " = ?";
        String[] whereArgs = { alumno.getDni( ) };

        return db.update( DataBaseHelper.TABLE_NAME, contentValues, whereClause, whereArgs );
    }

    public int eliminarAlumno( String dni )
    {
        SQLiteDatabase db = getWritableDatabase( );
        String whereClause = DataBaseHelper.COLUMN_DNI + " = ?";
        String[] whereArgs = { dni };

        return db.delete( DataBaseHelper.TABLE_NAME, whereClause, whereArgs );
    }

    public boolean alumnoExiste( String dni ) // Comprueba si un alumno existe en la base de datos por su DNI
    {
        SQLiteDatabase db = this.getReadableDatabase( );
        String query = "SELECT COUNT(*) FROM alumnos WHERE dni = ?";
        Cursor cursor = db.rawQuery( query, new String[]{ dni } );

        boolean existe = false;
        if( cursor.moveToFirst( ) ) {
            existe = cursor.getInt( 0 ) > 0; // Verifica si el resultado es mayor a 0
        }

        cursor.close( );
        return existe;
    }

    public List<Alumno> obtenerAlumnos( )
    {
        List<Alumno> lista = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase( );
        Cursor cursor = db.rawQuery( "SELECT dni, nombre, apellidos, sexo FROM alumnos", null );

        if( cursor.moveToFirst( ) ) {
            do {
                String dni = cursor.getString( 0 );
                String nombre = cursor.getString( 1 );
                String apellidos = cursor.getString( 2 );
                String sexo = cursor.getString( 3 );
                lista.add( new Alumno( dni, nombre, apellidos, sexo ) );
            } while ( cursor.moveToNext( ) );
        }

        cursor.close( );
        db.close( );

        return lista;
    }
}
