package com.example.renatocouto_atividadeead.ui.listadiciplina;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.renatocouto_atividadeead.R;
import com.example.renatocouto_atividadeead.auxiliar.Mensagens;
import com.example.renatocouto_atividadeead.databinding.FragmentListaDisciplinasBinding;
import com.example.renatocouto_atividadeead.model.entity.Disciplina;
import com.example.renatocouto_atividadeead.ui.cadastrar.CadastrarDisciplinaFragment;

import java.util.List;

public class ListarDisciplinasFragment extends Fragment {

    private FragmentListaDisciplinasBinding binding;
    private ListarDisciplinasViewModel disciplinasViewModel;
    private ItemListarDisciplinasAdapter itemDisciplinasAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ListarDisciplinasViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ListarDisciplinasViewModel.class);

        binding = FragmentListaDisciplinasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        disciplinasViewModel = new ViewModelProvider(this).get(ListarDisciplinasViewModel.class);

        carregarDisciplinas();

        observarMensagens();

        return root;
    }// onCreate

    private void observarMensagens() {
        disciplinasViewModel.getMsResult().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("erroSalvar")) {
                    Mensagens.showSnackbarPersonalizado(getView(), getString(R.string.erro_ao_recuperar_a_lista_de_disciplinas), 1, 1200);
                }
                if (s.equals("erroExcluir")) {
                    Mensagens.showSnackbarPersonalizado(getView(), getString(R.string.erro_ao_excluir), 1, 1200);
                }
                if (s.equals("sucessoExcluir")) {
                    Mensagens.showSnackbarPersonalizado(getView(), getString(R.string.disciplina_excluida_com_sucesso), 2, 1200);
                }
            }
        });
    }

    private void carregarDisciplinas() {
        disciplinasViewModel.getDisciplinas().observe(getViewLifecycleOwner(), new Observer<List<Disciplina>>() {
            @Override
            public void onChanged(List<Disciplina> disciplinas) {
                configurarRecyclerView(disciplinas);
            }
        });
    }

    private void configurarRecyclerView(List<Disciplina> disciplinas) {
        RecyclerView recyclerViewDisciplina = binding.recyclerViewDisciplina;
        recyclerViewDisciplina.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        itemDisciplinasAdapter = new ItemListarDisciplinasAdapter(disciplinas, new ItemListarDisciplinasAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Disciplina disciplina) {
                editarDisciplina(disciplina);
            }

            @Override
            public void onDeleteClick(Disciplina disciplina) {
                excluirDisciplina(disciplina);
            }
        });
        recyclerViewDisciplina.setAdapter(itemDisciplinasAdapter);
    }

    private void excluirDisciplina(Disciplina disciplina) {
        new AlertDialog.Builder(getContext()).setTitle(R.string.confirmar_exclusao)
                .setMessage(getString(R.string.realmente_deseja_deletar) + disciplina.getNome() + "?")
                .setPositiveButton(getString(R.string.deletar), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //sim
                        disciplinasViewModel.deletar(disciplina);
                    }
                }).setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //não
                        dialog.dismiss();
                    }
                }).create().show();
    }

    private void editarDisciplina(Disciplina disciplina) {
        Bundle result = new Bundle();
        result.putSerializable("disciplina", disciplina);

//        CadastrarDisciplinaFragment cadastrarDisciplinaFragment = new CadastrarDisciplinaFragment();
//        cadastrarDisciplinaFragment.setArguments(result);
//
//        requireActivity().getSupportFragmentManager().beginTransaction()
//                .replace(R.id.nav_host_fragment_content_main, cadastrarDisciplinaFragment)
//                .addToBackStack(null)
//                .commit();
//
//        Bundle args = new Bundle();
//        args.putSerializable("disciplina", disciplina);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_cadastrar, result);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;

    }
}