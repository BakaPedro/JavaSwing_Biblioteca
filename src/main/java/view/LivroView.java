package view;

import controller.LivroController;
import model.LivroModel;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class LivroView extends JFrame{
    private LivroController livroController = new LivroController();
    JMenuBar menuBar = new JMenuBar();
    private JPanel jpanelPrincipal;
    private JPanel LivroView;
    private JLabel jlabelTitulo;
    private JTable tabelaInfos;
    private JButton buttonApagar;
    private JButton buttonEdit;
    private JButton buttonAdd;
    private JList<String> listaLivros;
    private DefaultListModel<String> modeloLista;

    public LivroView() throws SQLException {
        criaMenu();
        menuBar = new JMenuBar();
        carregarLivros();
        this.setTitle("Sistema - Biblioteca - Livros");
        this.setContentPane(jpanelPrincipal);
        this.setSize(800, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        buttonApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tituloSelecionado = listaLivros.getSelectedValue();

                if (tituloSelecionado != null) {
                    try {
                        LivroModel livroParaRemover = livroController.listarTodos().stream()
                                .filter(livro -> livro.getTitulo().equals(tituloSelecionado))
                                .findFirst()
                                .orElse(null);
                        if (livroParaRemover != null) {
                            Long idLivro = livroParaRemover.getIdLivro();
                            JOptionPane.showMessageDialog(null, livroController.deletar(idLivro));
                            carregarLivros();
                            tabelaInfos.setModel(new LivroTabela(livroController.listarTodos()));
                        } else {
                            JOptionPane.showMessageDialog(null, "Livro não encontrado!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao remover livro!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um livro para remover!");
                }
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CadastroLivroView();
                    dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erro ao abrir a tela de cadastro!");
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
    public static class LivroTabela extends AbstractTableModel {

        private final String[] colunasDaTabela = new String[]{"IdUsuario", "Nome", "Sexo", "Numero de Celular", "Email"};
        private List<LivroModel> listaDeLivros;


        public LivroTabela(List<LivroModel> listaDeLivros) {
            this.listaDeLivros = listaDeLivros;
        }
        public int getRowCount() {
            return listaDeLivros.size();
        }
        public int getColumnCount(){
            return colunasDaTabela.length;
        }
        public Object getValueAt(int rowIndex, int columnIndex){
            return switch (columnIndex){
                case 0 -> listaDeLivros.get(rowIndex).getIdLivro();
                case 1 -> listaDeLivros.get(rowIndex).getTitulo();
                case 2 -> listaDeLivros.get(rowIndex).getTema();
                case 3 -> listaDeLivros.get(rowIndex).getDataPubli();
                case 4 -> listaDeLivros.get(rowIndex).getAutor();
                case 5 -> listaDeLivros.get(rowIndex).getIsbn();
                case 6 -> listaDeLivros.get(rowIndex).getQtDisponivel();
                default -> "-";
            };
        }

        public String getColumnName(int columnIndex){
            return colunasDaTabela[columnIndex];
        }
        public Class<?> getColumnClass(int columnIndex){
            if (getValueAt(0, columnIndex)!=null){
                return getValueAt(0, columnIndex).getClass();
            }else
                return Object.class;
        }
    }
    private void carregarLivros() throws SQLException {
        modeloLista = new DefaultListModel<>();
        LivroController livroController = new LivroController();
        List<LivroModel> listaDeLivros = livroController.listarTodos();

        for (LivroModel livro : listaDeLivros) {
            modeloLista.addElement(livro.getTitulo());
        }

        listaLivros.setModel(modeloLista);

        listaLivros.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String nomeSelecionado = listaLivros.getSelectedValue();
                if (nomeSelecionado != null) {
                    atualizarTabela(nomeSelecionado);
                }
            }
        });
    }
    private void atualizarTabela(String tituloLivro) {
        LivroController livroController = new LivroController();
        try {
            LivroModel livroSelecionado = livroController.listarTodos().stream()
                    .filter(livro -> livro.getTitulo().equals(tituloLivro))
                    .findFirst()
                    .orElse(null);

            if (livroSelecionado != null) {
                List<LivroModel> livroList = List.of(livroSelecionado);
                LivroTabela modeloFiltrado = new LivroTabela(livroList);
                tabelaInfos.setModel(modeloFiltrado);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
