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

public class CadastroLivroView extends JFrame {
    private JPanel panel1;
    private JPanel panel2;
    private JLabel tituloPrincipal;
    private JLabel fieldTitulo;
    private JTextArea escreveTitulo;
    private JLabel fieldISBN;
    private JTextArea escreveTemas;
    private JTextField escreveISBN;
    private JButton buttonConfirma;
    private JTextField escreveAutor;
    private JFormattedTextField escreveDataFormatada;
    private JLabel fieldTemas;
    private JLabel fieldAutor;
    private JLabel fieldData;
    private JTextField textFieldQtDisp;
    private JLabel fieldQtDisp;
    private LivroController livroController = new LivroController();
    MaskFormatter data;
    public CadastroLivroView() throws SQLException {

        this.setTitle("Cadastro de Livros");
        this.setContentPane(panel1);
        this.setContentPane(panel2);
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        try{
            data = new MaskFormatter("##/##/####");
        }catch (ParseException ex){
            JOptionPane.showMessageDialog(null, "Erro ao inserir");
        }
        buttonConfirma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivroModel livro = new LivroModel();
                livro.setTitulo(escreveTitulo.getText());
                livro.setIsbn(escreveISBN.getText());
                livro.setTema(escreveTemas.getText());
                livro.setAutor(escreveAutor.getText());
                livro.setDataPubli(escreveDataFormatada.getText());
                livro.setQtDisponivel(Integer.parseInt(textFieldQtDisp.getText()));
                try {
                    JOptionPane.showMessageDialog(null, livroController.salvar(livro));
                    dispose();
                    new LivroView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

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
