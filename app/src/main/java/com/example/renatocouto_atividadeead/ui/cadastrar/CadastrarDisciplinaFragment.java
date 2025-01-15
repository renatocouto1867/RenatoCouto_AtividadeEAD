package com.example.renatocouto_atividadeead.ui.cadastrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.renatocouto_atividadeead.R;
import com.example.renatocouto_atividadeead.auxiliar.Mensagens;
import com.example.renatocouto_atividadeead.databinding.FragmentCadastrarDisciplinaBinding;
import com.example.renatocouto_atividadeead.model.entity.Disciplina;

public class CadastrarDisciplinaFragment extends Fragment {
    private FragmentCadastrarDisciplinaBinding binding;
    private CadastrarDisciplinaViewModel viewModel;
    private Disciplina disciplina;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(CadastrarDisciplinaViewModel.class);
        binding = FragmentCadastrarDisciplinaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        androidx.appcompat.widget.Toolbar toolbar = requireActivity().findViewById(R.id.toolbar);


        Bundle bundle = getArguments();
        if (bundle != null) {
            disciplina = (Disciplina) bundle.getSerializable("disciplina");
            binding.editNomeDisciplina.setText(disciplina.getNome());
            binding.editNotaObtida.setText(String.valueOf(disciplina.getNotaObtida()));
            toolbar.setTitle(R.string.editar_disciplina);

        } else disciplina = new Disciplina();

        binding.buttonSalvar.setOnClickListener(v -> salvarDisciplina(disciplina));

        viewModel.getMsResult().observe(getViewLifecycleOwner(), msResult -> {
            if (msResult.equals("sucesso")) {
                Mensagens.showSnackbarPersonalizado(requireView(), getString(R.string.disciplina_gravada_com_sucesso), 2, 1200);
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
                navController.navigate(R.id.nav_lista_disciplinas);
            }
            if (msResult.equals("Erro")){
                Mensagens.showSnackbarPersonalizado(requireView(), getString(R.string.houve_um_erro_ao_gravar_a_disciplina), 2, 1200);
            }
        });

        return root;
    }

    private void salvarDisciplina(Disciplina disciplina) {
        String nome = binding.editNomeDisciplina.getText().toString();
        String notaStr = binding.editNotaObtida.getText().toString();

        if (nome.isEmpty()) {
            binding.editNomeDisciplina.requestFocus();
            binding.editNomeDisciplina.setError(getString(R.string.nome_obrigatorio));

            return;
        }

        if (notaStr.isEmpty()) {
            binding.editNotaObtida.requestFocus();
            binding.editNotaObtida.setError(getString(R.string.nota_obrigatoria));
            return;
        }

        try {
            double nota = Double.parseDouble(notaStr);
            disciplina.setNome(nome);
            disciplina.setNotaObtida(nota);
            viewModel.salvarDisciplina(disciplina);
            binding.editNomeDisciplina.setText("");
            binding.editNotaObtida.setText("");
            binding.editNomeDisciplina.requestFocus();

        } catch (NumberFormatException e) {
            binding.editNotaObtida.requestFocus();
            binding.editNotaObtida.setError(getString(R.string.nota_invalida));
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}