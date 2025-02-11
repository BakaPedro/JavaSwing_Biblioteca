package repository;

import model.DevolucaoModel;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DevolucaoRepository {
    private static DevolucaoRepository instance;
    protected EntityManager entityManager;

    public DevolucaoRepository(){entityManager = getEntityManager();}

    private EntityManager getEntityManager(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null){
            entityManager = factory.createEntityManager();
        }
        return entityManager;
    }
    public static DevolucaoRepository getInstance(){
        if (instance == null){
            instance = new DevolucaoRepository();
        }
        return instance;
    }
    public String salvar(DevolucaoModel devolucao) throws SQLException {
        try{
            entityManager.getTransaction().begin();
            entityManager.persist(devolucao);
            entityManager.getTransaction().commit();
            return "Salvo com sucesso";
        }catch (Exception e){
            return e.getMessage();
        }
    }
    public List<DevolucaoModel> listarDevolucoes(){
        try{
            List<DevolucaoModel> devolucoes = entityManager.createQuery("from DevolucaoModel").getResultList();
            return devolucoes;
        }catch (Exception e){
            return new ArrayList<>();
        }
    }
    public DevolucaoModel buscarId(Long IdDevolucao){
        DevolucaoModel devolucao = new DevolucaoModel();
        try {
            devolucao = entityManager.find(DevolucaoModel.class, IdDevolucao);
        }catch (Exception e){
            e.printStackTrace();
        }
        return devolucao;
    }
}
