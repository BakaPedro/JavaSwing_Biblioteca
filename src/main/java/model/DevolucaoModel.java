package model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "devolucoes")
public class DevolucaoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDevolucao;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idEmprestimo", nullable = false)
    private EmprestimoModel emprestimo;
    private String dataDevolucao;

    public Long getIdDevolucao() {
        return idDevolucao;
    }
    public EmprestimoModel getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(EmprestimoModel emprestimo) {
        this.emprestimo = emprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
