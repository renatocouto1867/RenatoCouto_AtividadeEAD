package com.example.renatocouto_atividadeead.ui.listadiciplina;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renatocouto_atividadeead.databinding.FragmentListaDisciplinasBinding;
import com.example.renatocouto_atividadeead.model.entity.Disciplina;


import java.util.List;

public class DisciplinasFragment extends Fragment {

    private FragmentListaDisciplinasBinding binding;
    private DisciplinasViewModel disciplinasViewModel;
    private ItemDisciplinasAdapter itemDisciplinasAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DisciplinasViewModel slideshowViewModel =
                new ViewModelProvider(this).get(DisciplinasViewModel.class);

        binding = FragmentListaDisciplinasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        disciplinasViewModel = new ViewModelProvider(this).get(DisciplinasViewModel.class);
        disciplinasViewModel.getDisciplinas().observe(getViewLifecycleOwner(), new Observer<List<Disciplina>>() {
            @Override
            public void onChanged(List<Disciplina> disciplinas) {
                if (disciplinas != null) {
                    configurarRecyclerView(disciplinas);
                } else {
                    Toast.makeText(getContext(), "Erro ao baixar dados", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return root;
    }

    private void configurarRecyclerView(List<Disciplina> disciplinas) {
        RecyclerView recyclerViewDisciplina = binding.recyclerViewDisciplina;
        recyclerViewDisciplina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        itemDisciplinasAdapter = new ItemDisciplinasAdapter(disciplinas, new ItemDisciplinasAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Disciplina disciplina) {
                Toast.makeText(getContext(), "Implementar o edit", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onDeleteClick(Disciplina disciplina) {
                Toast.makeText(getContext(), "Implementar o delete", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewDisciplina.setAdapter(itemDisciplinasAdapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}