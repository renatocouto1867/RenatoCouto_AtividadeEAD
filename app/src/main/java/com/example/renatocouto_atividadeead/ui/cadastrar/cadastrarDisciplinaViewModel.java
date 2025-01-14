package com.example.renatocouto_atividadeead.ui.cadastrar;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividadeead.model.entity.Disciplina;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class cadastrarDisciplinaViewModel extends ViewModel {
    private final MutableLiveData<String> mText;
    private final FirebaseDatabase database;
    private final DatabaseReference disciplinasRef;
    private final MutableLiveData<Boolean> salvamentoSucesso;
    private final MutableLiveData<String> mensagemErro;

    public cadastrarDisciplinaViewModel() {
        mText = new MutableLiveData<>();
        salvamentoSucesso = new MutableLiveData<>();
        mensagemErro = new MutableLiveData<>();
        database = FirebaseDatabase.getInstance();
        disciplinasRef = database.getReference("disciplinas");
    }

    public void salvarDisciplina(String nome, double nota) {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(nome);
        disciplina.setNotaObtida(nota);
        disciplina.setId(disciplinasRef.push().getKey());

        Log.d("Firebase", "Tentando salvar disciplina: " + disciplina.getNome());

        disciplinasRef.child(disciplina.getId())
                .setValue(disciplina)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Disciplina salva com sucesso! ID: " + disciplina.getId());
                    Log.d("Firebase", "Dados salvos: Nome=" + disciplina.getNome() +
                            ", Nota=" + disciplina.getNotaObtida());
                    salvamentoSucesso.setValue(true);
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Erro ao salvar disciplina: " + e.getMessage(), e);
                    mensagemErro.setValue("Erro ao salvar: " + e.getMessage());
                    salvamentoSucesso.setValue(false);
                });
    }
    public LiveData<Boolean> getSalvamentoSucesso() {
        return salvamentoSucesso;
    }

    public LiveData<String> getMensagemErro() {
        return mensagemErro;
    }
}