package repository;

import model.EmprestimoModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoRepository {
    private static EmprestimoRepository instance;
    protected EntityManager entityManager;

    public EmprestimoRepository(){entityManager = getEntityManager();}

    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null){
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    public static EmprestimoRepository getInstance(){
        if (instance == null){
            instance = new EmprestimoRepository();
        }
        return instance;
    }
    public String salvar(EmprestimoModel emprestimo) throws SQLException{
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(emprestimo);
            entityManager.getTransaction().commit();
            return "Salvo com sucesso";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<EmprestimoModel> listarEmprestimos(){
        try{
            List<EmprestimoModel> emprestimos = entityManager.createQuery("from EmprestimoModel").getResultList();
            return emprestimos;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public String deletar(EmprestimoModel emprestimo){
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(emprestimo);
            entityManager.getTransaction().commit();
            return "Removido com sucesso";
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    public EmprestimoModel buscarId(Long IdEmprestimo){
        EmprestimoModel emprestimo = new EmprestimoModel();
        try {
            emprestimo = entityManager.find(EmprestimoModel.class, IdEmprestimo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return emprestimo;
    }
}
