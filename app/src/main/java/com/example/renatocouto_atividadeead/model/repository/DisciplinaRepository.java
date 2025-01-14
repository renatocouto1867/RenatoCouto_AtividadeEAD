package com.example.renatocouto_atividadeead.model.repository;

import androidx.annotation.NonNull;

import com.example.renatocouto_atividadeead.model.entity.Disciplina;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DisciplinaRepository {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    DatabaseReference disciplinasRef = database.getReference("disciplinas");

    public void salvarDisciplina(Disciplina disciplina, OnSalvarDisciplinaListener listener) {

        //para gerar o novo id
        if (disciplina.getId() == null || disciplina.getId().isEmpty()) {
            disciplina.setId(disciplinasRef.push().getKey());
        }

        disciplinasRef.child(disciplina.getId()).setValue(disciplina)
                .addOnSuccessListener(aVoid -> {
                    listener.onSucesso();
                })
                .addOnFailureListener(e -> {
                    listener.onFalha(e);
                });

    }

    public void getAllDisciplinas(OnDisciplinasCarregamentoListener listener) {
        ArrayList<Disciplina> disciplinaList = new ArrayList<>();

        disciplinasRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Disciplina disciplina = dataSnapshot.getValue(Disciplina.class);
                    if (disciplina != null) {
                        disciplinaList.add(disciplina);
                    }
                }
                listener.onDisciplinasCarregadas(disciplinaList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                listener.onError(error);
            }
        });
    }

    public void getDisciplinaById(String id) {
        //criar o buscar por id
    }

    public void atualizarDisciplina(Disciplina disciplina) {
        if (disciplina.getId() != null) {
            //criar o salvar
        }
    }

    public void deleteDisciplina(String id, OnDeletarDisciplinaListener listener) {
        if (id != null && !id.isEmpty()) {
            disciplinasRef.child(id).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        listener.onSucesso();
                    })
                    .addOnFailureListener(e -> {
                        listener.onFalha(e);
                    });
        } else {
            listener.onFalha(new Exception("ID inválido"));
        }
    }

    //interfaces para o callback
    public interface OnDisciplinasCarregamentoListener {
        void onDisciplinasCarregadas(ArrayList<Disciplina> disciplinas);

        void onError(DatabaseError error);
    }

    public interface OnSalvarDisciplinaListener {
        void onSucesso();

        void onFalha(Exception e);
    }

    public interface OnDeletarDisciplinaListener {
        void onSucesso();

        void onFalha(Exception e);
    }
}
