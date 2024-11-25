package com.iesoretania.bbddnavigation.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iesoretania.bbddnavigation.Alumno;
import com.iesoretania.bbddnavigation.AlumnoAdapter;
import com.iesoretania.bbddnavigation.BaseDatos.DataBaseHelper;
import com.iesoretania.bbddnavigation.R;
import com.iesoretania.bbddnavigation.databinding.FragmentInicioBinding;
import com.iesoretania.bbddnavigation.databinding.FragmentListarBinding;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListarFragment extends Fragment {
    private FragmentListarBinding binding;
    private DataBaseHelper dataBasehelper; //OJO! No olvidar importar la clase DataBaseHelper

    public ListarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListarBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        // Creamos nuestra instancia de la base de datos
        dataBasehelper = new DataBaseHelper(getContext());

        // Ejecutamos la consulta en un hilo separado
        List<Alumno> listaAlumnos = dataBasehelper.obtenerAlumnos();

        // Actualizamos la lista en el hilo principal
        AlumnoAdapter adapter = new AlumnoAdapter(requireContext(), R.layout.item_alumno, listaAlumnos);
        binding.ListViewID.setAdapter(adapter);
    }
}