package view;

import controller.UsuarioController;
import model.UsuarioModel;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CadastroUsuarioView extends JFrame{
    private JPanel panel1;
    private JPanel panel2;
    private JLabel tituloPrincipal;
    private JTextArea escreveNome;
    private JLabel nomeUsuario;
    private JLabel fieldEmail;
    private JTextArea escreveNum;
    private JTextField escreveEmail;
    private JComboBox sexo;
    private JButton buttonConfirma;
    private JMenuBar menuBar;
    private UsuarioController usuarioController = new UsuarioController();
    public CadastroUsuarioView() throws SQLException {
        menuBar = new JMenuBar();
        this.setTitle("Sistema - Biblioteca - Livros");
        this.setContentPane(panel1);
        this.setContentPane(panel2);
        this.setSize(700, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        buttonConfirma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioModel usuario = new UsuarioModel();
                usuario.setNome(escreveNome.getText());
                usuario.setEmail(escreveEmail.getText());
                usuario.setNumeroCelular(escreveNum.getText());
                usuario.setSexo((String) sexo.getSelectedItem());
                try {
                    JOptionPane.showMessageDialog(null, usuarioController.salvarUsuario(usuario));
                    dispose();
                    new UsuarioView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        criaMenu();
    }

    public void criaMenu(){
        this.setJMenuBar(menuBar);
        JMenuItem menu = new JMenuItem("Menu");
        menuBar.add(menu);
        JMenuItem usuarios = new JMenuItem("Usuários");
        menuBar.add(usuarios);
        JMenuItem livros = new JMenuItem("Livros");
        menuBar.add(livros);
        livros.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new LivroView();
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
    }
}