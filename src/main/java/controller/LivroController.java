package controller;

import model.LivroModel;
import repository.LivroRepository;
import java.sql.SQLException;
import java.util.List;

public class LivroController {
    private LivroRepository livroRepository = new LivroRepository();

    public String salvar(LivroModel livro) throws SQLException{
        return livroRepository.salvar(livro);
    }

    public List<LivroModel> listarTodos() throws SQLException{
        return livroRepository.listarLivros();
    }

    public String deletar(Long id){
       LivroModel livro = livroRepository.buscarId(id);
       return livroRepository.deletar(livro);
    }
}
