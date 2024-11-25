package com.iesoretania.bbddnavigation.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.iesoretania.bbddnavigation.Alumno;
import com.iesoretania.bbddnavigation.BaseDatos.DataBaseHelper;
import com.iesoretania.bbddnavigation.R;
import com.iesoretania.bbddnavigation.databinding.FragmentMatriculaAlumnoBinding;

import java.util.ArrayList;
import java.util.List;

public class MatriculaAlumnoFragment extends Fragment {
    private FragmentMatriculaAlumnoBinding binding;

    //OJO! No olvidar importar la clase DataBaseHelper
    private DataBaseHelper dataBaseHelper;

    public MatriculaAlumnoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMatriculaAlumnoBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        //------------------
        // Logica del Spinner
        //------------------

        //Creamos nuestra lista de items del Spinner
        List<String> items = new ArrayList<>();
        items.add( "Masculino" );
        items.add( "Femenino" );

        //Adapter para el Spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<>( requireContext( ), android.R.layout.simple_spinner_item, items );
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item );

        //Seteamos nuestro adapter al Spinner
        binding.spinnerSexo.setAdapter( adapter );

        //------------------
        // Logica de botones
        //------------------

        // Creamos nuestra instancia de la base de datos
        dataBaseHelper = new DataBaseHelper( getContext( ) );

        // Botón Matricular
        binding.buttonMatricular.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                String dni = binding.editTextDNI.getText().toString();
                String nombre = binding.editTextNombre.getText().toString();
                String apellidos = binding.editTextApellidos.getText().toString();
                String sexo = binding.spinnerSexo.getSelectedItem().toString();

                if( dni.isEmpty( ) || nombre.isEmpty( ) || apellidos.isEmpty( ) ) //Sexo NO puede ser Empty porque es un Spinner y ya tiene un valor por defecto
                {
                    Toast.makeText( requireContext( ), "Rellena todos los campos", Toast.LENGTH_SHORT ).show( );
                }
                else
                {
                    //Comprobamos si el alumno ya existe
                    if( dataBaseHelper.alumnoExiste( dni ) )
                        Toast.makeText( requireContext( ), "El alumno con DNI " + dni + " ya existe", Toast.LENGTH_LONG ).show( );
                    else
                    {
                        //Creamos nuestro objeto alumno
                        Alumno alumno = new Alumno( dni, nombre, apellidos, sexo );

                        //Creamos nuestra instancia de la base de datos
                        long valor = dataBaseHelper.insertarAlumno( alumno );

                        //Comprobamos si se ha insertado correctamente
                        if( valor == -1 )
                            Toast.makeText( getContext( ), "Alumno no insertado: error", Toast.LENGTH_LONG ).show( );
                        else if( valor > 0 )
                            Toast.makeText( getContext( ), "Alumno insertado correctamente", Toast.LENGTH_LONG ).show( );
                    }
                }
            }
        });

        // Botón Cancelar
        binding.buttonCancelar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                // Limpiamos los campos del formulario (No es necesario pero lo hacemos igual para ver como se limpian los campos)
                binding.editTextDNI.setText( "" );
                binding.editTextNombre.setText( "" );
                binding.editTextApellidos.setText( "" );
                binding.spinnerSexo.setSelection( 0 );

                // Volvemos a nuestro fragmento inicial
                NavController navController = Navigation.findNavController( requireActivity( ), R.id.nav_host_fragment_content_main );
                navController.navigate( R.id.action_matriculaAlumnoFragment_to_inicioFragment );
            }
        });
    }
}