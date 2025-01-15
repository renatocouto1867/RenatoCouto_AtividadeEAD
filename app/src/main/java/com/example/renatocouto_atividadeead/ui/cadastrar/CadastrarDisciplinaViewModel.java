package com.example.renatocouto_atividadeead.ui.cadastrar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividadeead.model.entity.Disciplina;
import com.example.renatocouto_atividadeead.model.repository.DisciplinaRepository;

public class CadastrarDisciplinaViewModel extends ViewModel {
        
        private final MutableLiveData<String> msResult;
        private final DisciplinaRepository repository;
    

    public CadastrarDisciplinaViewModel() {
        msResult = new MutableLiveData<>();
        repository = new DisciplinaRepository();    
    }

    public void salvarDisciplina(Disciplina disciplina) {
        repository.salvarDisciplina(disciplina, new DisciplinaRepository.OnSalvarDisciplinaListener() {
            @Override
            public void onSucesso() {
                msResult.setValue("sucesso");
            }

            @Override
            public void onFalha(Exception e) {
                msResult.setValue("Erro");
            }
        });
    
    }   
    
    public LiveData<String> getMsResult() {
        return msResult;
    }
}