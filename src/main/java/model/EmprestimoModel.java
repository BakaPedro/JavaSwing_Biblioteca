package model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "emprestimos")
public class EmprestimoModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmprestimo;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="idLivro", nullable = false)
    private LivroModel livro;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioModel usuario;

    private String dataEmprestimo;
    private String dataDevolucao;


    public UsuarioModel getUsuario() {return usuario;}

    public void setUsuario(UsuarioModel usuario) {this.usuario = usuario;}

    public LivroModel getLivro() {return livro;}

    public void setLivro(LivroModel livro) {this.livro = livro;}

    public String getDataEmprestimo() {return dataEmprestimo;}

    public void setDataEmprestimo(String dataEmprestimo) {this.dataEmprestimo = dataEmprestimo;}

    public String getDataDevolucao() {return dataDevolucao;}

    public void setDataDevolucao(String dataDevolucao) {this.dataDevolucao = dataDevolucao;}
    public Long getIdEmprestimo() {return idEmprestimo;}
}
