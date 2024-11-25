package com.iesoretania.bbddnavigation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AlumnoAdapter extends ArrayAdapter<Alumno> {
    Context ctx;
    int layoutTemplate;
    List<Alumno> listaAlumnos;

    public AlumnoAdapter(@NonNull Context context, int resource, @NonNull List<Alumno> objects) {
        super(context, resource, objects); // Asegúrate de pasar la lista aquí
        ctx = context;
        layoutTemplate = resource;
        listaAlumnos = objects;
    }

    public static class ViewHolder
    {
        TextView textviewDNI;
        TextView textviewNombre;
        TextView textviewApellidos;
        ImageView imageviewAlumno;
    }

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(ctx).inflate(layoutTemplate, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textviewApellidos = convertView.findViewById(R.id.textViewApellidos);
            viewHolder.textviewNombre = convertView.findViewById(R.id.textViewNombre);
            viewHolder.textviewDNI = convertView.findViewById(R.id.textViewDNI);
            viewHolder.imageviewAlumno = convertView.findViewById(R.id.imageViewSexo );
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Alumno alumno = listaAlumnos.get( position );

        viewHolder.textviewDNI.setText( alumno.getDni( ) );
        viewHolder.textviewNombre.setText( alumno.getNombre( ) );
        viewHolder.textviewApellidos.setText( alumno.getApellidos( ) );

        if( alumno.getSexo( ).equalsIgnoreCase( "masculino" ) )
            viewHolder.imageviewAlumno.setImageResource( R.drawable.hombre );
        else
            viewHolder.imageviewAlumno.setImageResource( R.drawable.mujer );

        return convertView;
    }
}