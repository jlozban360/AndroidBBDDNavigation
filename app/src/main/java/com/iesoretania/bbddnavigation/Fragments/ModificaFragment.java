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
import android.widget.Toast;

import com.iesoretania.bbddnavigation.Alumno;
import com.iesoretania.bbddnavigation.BaseDatos.DataBaseHelper;
import com.iesoretania.bbddnavigation.R;
import com.iesoretania.bbddnavigation.databinding.FragmentModificaBinding;

public class ModificaFragment extends Fragment {
    private FragmentModificaBinding binding;
    private DataBaseHelper dataBaseHelper; //OJO! No olvidar importar la clase DataBaseHelper

    public ModificaFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentModificaBinding.inflate( inflater, container, false );
        return binding.getRoot( );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        // Creamos nuestra instancia de la base de datos
        dataBaseHelper = new DataBaseHelper( getContext( ) );

        // Ejecutamos la modificación según el DNI
        binding.buttonMatricularModifica.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick( View view ) {
                String dni = binding.editTextDNIModifica.getText( ).toString( );
                String nombre = binding.editTextNombreModifica.getText( ).toString( );
                String apellidos = binding.editTextApellidosModifica.getText( ).toString( );

                // Obtener dinámicamente el valor del sexo
                int selectedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId( );
                String sexo;

                if ( selectedRadioButtonId == R.id.radioButtonMasculino )
                    sexo = "masculino";
                else if ( selectedRadioButtonId == R.id.radioButtonFemenino )
                    sexo = "femenino";
                else
                {
                    Toast.makeText( requireContext( ), "Selecciona un sexo", Toast.LENGTH_SHORT ).show( );
                    return; // Salimos del método si no se seleccionó un sexo
                }

                if( dni.isEmpty( ) || nombre.isEmpty( ) || apellidos.isEmpty( ) )
                    Toast.makeText( requireContext( ), "Rellena todos los campos", Toast.LENGTH_SHORT ).show( ) ;
                else
                {
                    if( !dataBaseHelper.alumnoExiste( dni ) ) //Introducimos un mensaje de error si el alumno no existe
                        Toast.makeText(requireContext(), "El alumno con DNI " + dni + " no existe", Toast.LENGTH_LONG).show();
                    else
                    {
                        Alumno alumno = new Alumno( dni, nombre, apellidos, sexo );
                        long valor = dataBaseHelper.actualizarAlumno(alumno);

                        if( valor == -1 )
                            Toast.makeText( getContext( ), "Alumno no modificado: error", Toast.LENGTH_LONG ).show( );
                        else if( valor > 0 )
                            Toast.makeText( getContext( ), "Alumno modificado correctamente", Toast.LENGTH_LONG ).show( );
                    }
                }
            }
        });

        binding.buttonCancelarModifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View view )
            {
                // Limpiamos los campos del formulario (No es necesario pero lo hacemos igual para ver como se limpian los campos)
                binding.editTextDNIModifica.setText( "" );
                binding.editTextNombreModifica.setText( "" );
                binding.editTextApellidosModifica.setText( "" );
                binding.radioGroup.check( R.id.radioButtonMasculino );

                // Volvemos a nuestro fragmento inicial
                NavController navController = Navigation.findNavController( requireActivity( ), R.id.nav_host_fragment_content_main );
                navController.navigate( R.id.action_modificaFragment_to_inicioFragment );
            }
        });
    }
}