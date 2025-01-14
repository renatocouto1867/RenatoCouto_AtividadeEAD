package com.example.renatocouto_atividadeead.ui.listadiciplina;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.renatocouto_atividadeead.model.entity.Disciplina;
import com.example.renatocouto_atividadeead.model.repository.DisciplinaRepository;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisciplinasViewModel extends ViewModel {

    private final MutableLiveData<List<Disciplina>> listMutableLiveData = new MutableLiveData<>();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    private final DisciplinaRepository disciplinaRepository = new DisciplinaRepository();

    public DisciplinasViewModel() {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                disciplinaRepository.getAllDisciplinas(new DisciplinaRepository.OnDisciplinasCarregamentoListener() {
                    @Override
                    public void onDisciplinasCarregadas(ArrayList<Disciplina> disciplinas) {
                        listMutableLiveData.postValue(disciplinas);
                    }

                    @Override
                    public void onError(DatabaseError error) {

                    }
                });

            }
        });

    }

    public LiveData<List<Disciplina>> getDisciplinas() {
        return listMutableLiveData;
    }
}