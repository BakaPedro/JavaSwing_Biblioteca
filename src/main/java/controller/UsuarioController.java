package controller;

import model.UsuarioModel;
import repository.UsuarioRepository;
import java.sql.SQLException;
import java.util.List;

public class UsuarioController {
    private UsuarioRepository usuarioRepository = new UsuarioRepository();

    public String salvarUsuario(UsuarioModel usuario) throws SQLException {
        return usuarioRepository.salvar(usuario);
    }

    public List<UsuarioModel> listarTodos() throws SQLException{
        return usuarioRepository.listarUsuarios();
    }

    public String deletar(Long id){
        UsuarioModel usuario = usuarioRepository.buscarId(id);
        return usuarioRepository.deletar(usuario);
    }
    public UsuarioModel buscarPorNome(String nome) throws SQLException {
        UsuarioModel usuario = usuarioRepository.buscarPorNome(nome);
        return usuario;
    }

}
