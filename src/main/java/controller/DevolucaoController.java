package controller;

import model.DevolucaoModel;
import repository.DevolucaoRepository;


import java.sql.SQLException;
import java.util.List;

public class DevolucaoController {
    private DevolucaoRepository devolucaoRepository = new DevolucaoRepository();

    public String salvarDevolucao(DevolucaoModel devolucao) throws SQLException {
        return devolucaoRepository.salvar(devolucao);
    }

    public List<DevolucaoModel> listarTodos() throws SQLException{
        return devolucaoRepository.listarDevolucoes();
    }
}
