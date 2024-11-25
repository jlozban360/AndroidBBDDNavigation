package com.iesoretania.bbddnavigation.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.iesoretania.bbddnavigation.BaseDatos.DataBaseHelper;
import com.iesoretania.bbddnavigation.R;
import com.iesoretania.bbddnavigation.databinding.FragmentBorrarBinding;

public class BorrarFragment extends Fragment {
    private FragmentBorrarBinding binding;
    private DataBaseHelper dataBaseHelper; //OJO! No olvidar importar la clase DataBaseHelper

    public BorrarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBorrarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Creamos nuestra instancia de la base de datos
        dataBaseHelper = new DataBaseHelper( getContext( ) );

        //Ejecutamos el borrado según el DNI
        binding.buttonMatricular.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                String dni = binding.editTextDNIBorrar.getText( ).toString( );

                if( dni.isEmpty( ) )
                    Toast.makeText( requireContext( ), "Rellena todos los campos", Toast.LENGTH_SHORT ).show( );
                else
                {
                    if( !dataBaseHelper.alumnoExiste( dni ) ) {
                        Toast.makeText( requireContext( ), "El alumno con DNI " + dni + " no existe", Toast.LENGTH_LONG ).show( ); //Introducimos un mensaje de error si el alumno no existe)
                    }
                    else
                    {
                        long valor = dataBaseHelper.eliminarAlumno( dni );

                        if( valor == -1 )
                            Toast.makeText( getContext( ), "Alumno no borrado: error", Toast.LENGTH_LONG).show( );
                        else if( valor > 0 )
                            Toast.makeText( getContext( ), "Alumno borrado correctamente", Toast.LENGTH_LONG).show( );
                    }
                }
            }
        });
    }
}