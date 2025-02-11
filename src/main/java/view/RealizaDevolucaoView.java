package view;

import controller.DevolucaoController;
import controller.EmprestimoController;
import model.DevolucaoModel;
import model.EmprestimoModel;
import repository.EmprestimoRepository;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class RealizaDevolucaoView extends JFrame{
    private JLabel tituloPrincipal;
    private JLabel dataDevolucao;
    private JFormattedTextField escreveDataDevolucao;
    private JButton salvarButton;
    private JPanel jpanelPrincipal;
    JMenuBar menuBar = new JMenuBar();
    private DefaultListModel<String> modeloLista;

    public RealizaDevolucaoView(Long idEmprestimo){
        menuBar = new JMenuBar();
        criaMenu();
        this.setTitle("Sistema - Biblioteca - Livros");
        this.setContentPane(jpanelPrincipal);
        this.setSize(800, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    EmprestimoController emprestimoController = new EmprestimoController();
                    EmprestimoModel emprestimo = emprestimoController.Id(idEmprestimo);
                    DevolucaoController devolucao = new DevolucaoController();
                    DevolucaoModel devolucaoModel = new DevolucaoModel();

                    devolucaoModel.setDataDevolucao(escreveDataDevolucao.getText());
                    devolucaoModel.setEmprestimo(emprestimo);
                    devolucao.salvarDevolucao(devolucaoModel);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });

    }
            public void criaMenu(){
                this.setJMenuBar(menuBar);
                JMenuItem menu = new JMenuItem("Menu");
                menuBar.add(menu);
                JMenuItem usuarios = new JMenuItem("Usuários");
                menuBar.add(usuarios);
                JMenuItem livros = new JMenuItem("Livros");
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
            }

}
