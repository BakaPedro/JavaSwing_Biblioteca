package controller;

import model.EmprestimoModel;
import model.LivroModel;
import repository.EmprestimoRepository;
import java.sql.SQLException;
import java.util.List;

public class EmprestimoController {
    private EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

    public String salvarEmprestimo(EmprestimoModel emprestimo) throws SQLException{
        return emprestimoRepository.salvar(emprestimo);
    }

    public List<EmprestimoModel> listarTodos() throws SQLException{
        return emprestimoRepository.listarEmprestimos();
    }

    public String deletar(Long id){
        EmprestimoModel emprestimo = emprestimoRepository.buscarId(id);
        return emprestimoRepository.deletar(emprestimo);
    }
    public EmprestimoModel Id(Long id){
        EmprestimoModel emprestimo = emprestimoRepository.buscarId(id);
        return emprestimo;
    }
}
