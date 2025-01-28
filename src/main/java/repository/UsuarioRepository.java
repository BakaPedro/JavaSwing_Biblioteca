package repository;
import model.UsuarioModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepository {
    private static UsuarioRepository instance;
    protected EntityManager entityManager;

    public UsuarioRepository(){entityManager = getEntityManager();}

    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static UsuarioRepository getInstance() {
        if (instance == null) {
            instance = new UsuarioRepository();
        }
        return instance;
    }

    public String salvar(UsuarioModel usuario) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(usuario);
            entityManager.getTransaction().commit();
            return "Salvo com Sucesso.";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<UsuarioModel> listarUsuarios() {
        try {
            List<UsuarioModel> usuarios = entityManager.createQuery("from UsuarioModel").getResultList();
            return usuarios;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public String deletar(UsuarioModel usuario){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(usuario);
            entityManager.getTransaction().commit();
            return "Removido com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public UsuarioModel buscarId(Long idUsuario) {
        UsuarioModel usuario = new UsuarioModel();
        try {
            usuario = entityManager.find(UsuarioModel.class, idUsuario);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usuario;
    }
}
