package view;

import controller.EmprestimoController;
import controller.LivroController;
import controller.UsuarioController;
import model.EmprestimoModel;
import model.LivroModel;
import model.UsuarioModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class FazerEmprestimoView extends JFrame{
    private JButton salvarButton;
    private JLabel tituloPrincipal;
    private JLabel dataEmprestimo;
    private JFormattedTextField escreveDataEmprestimo;
    private JLabel dataDevolucaoPrev;
    private JFormattedTextField escreveDataDevolucaoPrev;
    private JLabel dataDevolucao;
    private JFormattedTextField escreveDataDevolucao;
    private JPanel jpanelPrincipal;
    private JList<String> listaUsuarios;
    private JCheckBox checkLivro;
    private JScrollPane livroScrollPane;
    private JPanel panelLivro;
    JMenuBar menuBar = new JMenuBar();
    private DefaultListModel<String> modeloLista;
    public FazerEmprestimoView() throws SQLException {

        this.setTitle("Sistema - Biblioteca - Livros");
        this.setContentPane(jpanelPrincipal);
        this.setSize(800, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        criaMenu();
        carregarUsuarios();
        carregarLivros();

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nomeUsuarioSelecionado = listaUsuarios.getSelectedValue();
                    if (nomeUsuarioSelecionado == null) {
                        JOptionPane.showMessageDialog(null, "Selecione um usuário antes de salvar.");
                        return;
                    }
                    UsuarioController usuarioController = new UsuarioController();
                    System.out.println("Usuário selecionado: " + nomeUsuarioSelecionado);
                    UsuarioModel usuario = usuarioController.buscarPorNome(nomeUsuarioSelecionado);
                    if (usuario == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                        return;
                    }

                    LivroController livroController = new LivroController();
                    EmprestimoController emprestimoController = new EmprestimoController();

                    boolean peloMenosUmLivroSelecionado = false;

                    for (int i = 0; i < panelLivro.getComponentCount(); i++) {
                        if (panelLivro.getComponent(i) instanceof JCheckBox) {
                            JCheckBox checkBox = (JCheckBox) panelLivro.getComponent(i);
                            if (checkBox.isSelected()) {
                                LivroModel livro = livroController.buscarPorTitulo(checkBox.getText());
                                if (livro != null) {
                                    EmprestimoModel emprestimo = new EmprestimoModel();
                                    emprestimo.setUsuario(usuario);
                                    emprestimo.setLivro(livro);
                                    emprestimo.setDataEmprestimo(escreveDataEmprestimo.getText());
                                    emprestimo.setDataDevolucao(escreveDataDevolucaoPrev.getText());
                                    emprestimoController.salvarEmprestimo(emprestimo);
                                    peloMenosUmLivroSelecionado = true;
                                    new EmprestimosView();
                                    dispose();
                                }
                            }
                        }
                    }

                    if (!peloMenosUmLivroSelecionado) {
                        JOptionPane.showMessageDialog(null, "Selecione pelo menos um livro.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Empréstimo registrado com sucesso!");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
                    dispose();
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
                dispose();
            }
        });
    }
    private void carregarUsuarios() throws SQLException {
        modeloLista = new DefaultListModel<>();
        UsuarioController usuarioController = new UsuarioController();
        List<UsuarioModel> listaDeUsuarios = usuarioController.listarTodos();

        for (UsuarioModel usuario : listaDeUsuarios) {
            modeloLista.addElement(usuario.getNome());
        }

        listaUsuarios.setModel(modeloLista);

        listaUsuarios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nomeSelecionado = listaUsuarios.getSelectedValue();
//                if (nomeSelecionado != null) {
//                    atualizarTabela(nomeSelecionado);
//                }
            }
        });
    }

    private void carregarLivros() throws SQLException {
        LivroController livroController = new LivroController();
        List<LivroModel> listaDeLivros = livroController.listarTodos();

        panelLivro.setLayout(new BoxLayout(panelLivro, BoxLayout.Y_AXIS));
        panelLivro.removeAll();

        for (LivroModel livro : listaDeLivros) {
            JCheckBox checkBox = new JCheckBox(livro.getTitulo());
            panelLivro.add(checkBox);
        }

    }

}

