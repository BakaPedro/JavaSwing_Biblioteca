package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class Principal extends JFrame {
    private JMenuBar menuBar;
    private JLabel letreiro;
    private JPanel panel1;
    private JButton buttonEmprestimo;
    private JButton buttonDevolucao;

    public Principal(){
            menuBar = new JMenuBar();
            panel1 = new JPanel();
            letreiro = new JLabel("Boas Vindas");
            buttonDevolucao = new JButton("Registrar Devolução");
            buttonEmprestimo = new JButton("Realizar Empréstimo de um Livro");
            letreiro.setFont(new Font("Arial", Font.ITALIC, 30));
            panel1.add(letreiro);
            panel1.add(buttonEmprestimo);
            panel1.add(buttonDevolucao);
            criaMenu();
            this.setTitle("Sistema - Biblioteca");
            this.setContentPane(panel1);
            this.setSize(640, 480);
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setVisible(true);
            this.setLocationRelativeTo(null);
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
