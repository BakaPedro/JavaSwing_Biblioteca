package repository;
import model.LivroModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroRepository {
    private static LivroRepository instance;
    protected EntityManager entityManager;

    public LivroRepository(){entityManager = getEntityManager();}

    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }

    public static LivroRepository getInstance() {
        if (instance == null) {
            instance = new LivroRepository();
        }
        return instance;
    }

    public String salvar(LivroModel livro) throws SQLException {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(livro);
            entityManager.getTransaction().commit();
            return "Salvo com Sucesso.";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<LivroModel> listarLivros() {
        try {
            List<LivroModel> livros = entityManager.createQuery("from LivroModel").getResultList();
            return livros;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }

    public String deletar(LivroModel livro){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(livro);
            entityManager.getTransaction().commit();
            return "Removido com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public LivroModel buscarId(Long idLivro) {
        LivroModel livro = new LivroModel();
        try {
            livro = entityManager.find(LivroModel.class, idLivro);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livro;
    }
    public LivroModel buscarPorTitulo(String titulo) throws SQLException {
        LivroModel livro = new LivroModel();
        try {
            livro = entityManager.createQuery(
                    "SELECT l FROM LivroModel l WHERE l.titulo = :titulo", LivroModel.class)
                    .setParameter("titulo", titulo)
                    .getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livro;
    }

}
