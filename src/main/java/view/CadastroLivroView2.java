package view;

import controller.LivroController;
import model.LivroModel;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.sql.SQLException;


public class CadastroLivroView2 extends JFrame{
    private JPanel panel;
    private JButton buttonSalvar;
    private JLabel tituloPrincipal;
    private JLabel titulo;
    private JTextField escreveTitulo;
    private JTextArea escreveTema;
    private JTextField escreveAutor;
    private JFormattedTextField escreveDataPulicacao;
    private JTextField escreveQtDisponivel;
    private JLabel tema;
    private JLabel autor;
    private JLabel dataPublicacao;
    private JLabel qtDisponivel;
    private JLabel isbn;
    private JTextField escreveISBN;
    private LivroController livroController = new LivroController();
    public CadastroLivroView2() throws SQLException {
        this.setContentPane(panel);
        panel = new JPanel();
        this.setTitle("Cadastro de Livros");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
//        try{
//            MaskFormatter mask = new MaskFormatter("##/##/####");
//            mask.setPlaceholderCharacter('_');
//            escreveDataPulicacao= new JFormattedTextField(mask);
//            panel.add(escreveDataPulicacao);
//
//        }catch (ParseException ex){
//            JOptionPane.showMessageDialog(null, "Erro ao inserir");
//        }
        buttonSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivroModel livro = new LivroModel();
                livro.setTitulo(escreveTitulo.getText());
                livro.setIsbn(escreveISBN.getText());
                livro.setTema(escreveTema.getText());
                livro.setAutor(escreveAutor.getText());
                livro.setDataPubli(escreveDataPulicacao.getText());
                livro.setQtDisponivel(Integer.parseInt(escreveQtDisponivel.getText()));
                try {
                    JOptionPane.showMessageDialog(null, livroController.salvar(livro));
                    dispose();
                    new LivroView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.setVisible(true);
        criaMenu();

    }

    public void criaMenu() {
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenuItem menu = new JMenuItem("Menu");
        JMenuItem usuarios = new JMenuItem("Usuários");
        JMenuItem livros = new JMenuItem("Livros");

        menuBar.add(menu);
        menuBar.add(usuarios);
        menuBar.add(livros);

        usuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new UsuarioView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Principal();
                dispose();
            }
        });
        livros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LivroView();
                    dispose();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

//    private void createUIComponents() {
//        new java.swing.JFormattedTextField(data);
//    }
}

