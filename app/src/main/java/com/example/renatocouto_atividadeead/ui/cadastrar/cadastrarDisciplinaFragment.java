package com.example.renatocouto_atividadeead.ui.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.renatocouto_atividadeead.databinding.FragmentCadastrarDisciplinaBinding;

public class cadastrarDisciplinaFragment extends Fragment {
    private FragmentCadastrarDisciplinaBinding binding;
    private cadastrarDisciplinaViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(cadastrarDisciplinaViewModel.class);
        binding = FragmentCadastrarDisciplinaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurar o botão de salvar
        binding.buttonSalvar.setOnClickListener(v -> salvarDisciplina());

        // Observar resultado do salvamento
        viewModel.getSalvamentoSucesso().observe(getViewLifecycleOwner(), sucesso -> {
            if (sucesso) {
                // Limpar campos
                binding.editNomeDisciplina.setText("");
                binding.editNotaObtida.setText("");
                Toast.makeText(getContext(), "Disciplina salva com sucesso!", Toast.LENGTH_SHORT).show();
            }
        });

        // Observar mensagens de erro
        viewModel.getMensagemErro().observe(getViewLifecycleOwner(), erro -> {
            if (erro != null && !erro.isEmpty()) {
                Toast.makeText(getContext(), erro, Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

    private void salvarDisciplina() {
        String nome = binding.editNomeDisciplina.getText().toString();
        String notaStr = binding.editNotaObtida.getText().toString();

        // Validações básicas
        if (nome.isEmpty()) {
            binding.editNomeDisciplina.setError("Nome é obrigatório");
            return;
        }

        if (notaStr.isEmpty()) {
            binding.editNotaObtida.setError("Nota é obrigatória");
            return;
        }

        try {
            double nota = Double.parseDouble(notaStr);
            viewModel.salvarDisciplina(nome, nota);
            Toast.makeText(getActivity(), "Disciplina salva", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException e) {
            binding.editNotaObtida.setError("Nota inválida");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}