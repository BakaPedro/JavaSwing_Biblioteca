package model;


import javax.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name="idLivro")
@Table(name="livros")
public class LivroModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLivro;
    private String titulo, tema, dataPubli, autor, isbn;
    private int qtDisponivel;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getDataPubli() {
        return dataPubli;
    }

    public void setDataPubli(String dataPubli) {
        this.dataPubli = dataPubli;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Long getIdLivro(){return idLivro;}

    public int getQtDisponivel() {
        return qtDisponivel;
    }

    public void setQtDisponivel(int qtDisponivel) {
        this.qtDisponivel = qtDisponivel;
    }
}
