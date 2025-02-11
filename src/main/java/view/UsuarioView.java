package view;
import controller.UsuarioController;
import model.UsuarioModel;
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class UsuarioView extends JFrame {
    private UsuarioController usuarioController = new UsuarioController();
    private JMenuBar menuBar = new JMenuBar();
    private JPanel jpanelPrincipal;
    private JPanel UsuarioView;
    private JLabel jLabel;
    private JTable tabelaInfos;
    private JButton buttonApagar;
    private JButton buttonAdd;
    private JButton buttonEdit;
    private JList<String> listaUsuarios;
    private DefaultListModel<String> modeloLista;

    public UsuarioView() throws SQLException {
        criaMenu();
        carregarUsuarios();
        tabelaInfos.setModel(new UsuarioView.UsuarioTabela(usuarioController.listarTodos()));
        tabelaInfos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (int i = 0; i < tabelaInfos.getColumnCount(); i++) {
            tabelaInfos.getColumnModel().getColumn(i).setPreferredWidth(150);
        }
        menuBar = new JMenuBar();
        this.setTitle("Sistema - Biblioteca - Livros");
        this.setContentPane(jpanelPrincipal);
        this.setSize(700, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        buttonApagar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeSelecionado = listaUsuarios.getSelectedValue();

                if (nomeSelecionado != null) {
                    try {
                        UsuarioModel usuarioParaRemover = usuarioController.listarTodos().stream()
                                .filter(usuario -> usuario.getNome().equals(nomeSelecionado))
                                .findFirst()
                                .orElse(null);

                        if (usuarioParaRemover != null) {
                            Long idUserSec = usuarioParaRemover.getIdUsuario();

                            JOptionPane.showMessageDialog(null, usuarioController.deletar(idUserSec));
                            carregarUsuarios();
                            tabelaInfos.setModel(new UsuarioTabela(usuarioController.listarTodos()));
                        } else {
                            JOptionPane.showMessageDialog(null, "Usuário não encontrado!");
                        }
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao remover usuário!");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um usuário para remover!");
                }
            }
        });
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new CadastroUsuarioView();
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
    }
    public static class UsuarioTabela extends AbstractTableModel{
        private final String[] colunasDaTabela = new String[]{"IdUsuario", "Nome", "Sexo", "Numero de Celular", "Email"};
        private List<UsuarioModel> listaDeUsuarios;

        public UsuarioTabela(List<UsuarioModel> listaDeUsuarios) {
            this.listaDeUsuarios = listaDeUsuarios;
        }
        public int getRowCount() {
            return listaDeUsuarios.size();
        }
        public int getColumnCount(){
            return colunasDaTabela.length;
        }
        public Object getValueAt(int rowIndex, int columnIndex){
            return switch (columnIndex){
                case 0 -> listaDeUsuarios.get(rowIndex).getIdUsuario();
                case 1 -> listaDeUsuarios.get(rowIndex).getNome();
                case 2 -> listaDeUsuarios.get(rowIndex).getSexo();
                case 3 -> listaDeUsuarios.get(rowIndex).getNumeroCelular();
                case 4 -> listaDeUsuarios.get(rowIndex).getEmail();
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
                if (nomeSelecionado != null) {
                    atualizarTabela(nomeSelecionado);
                }
            }
        });
    }
    private void atualizarTabela(String nomeUsuario) {
        UsuarioController usuarioController = new UsuarioController();

        try {
            UsuarioModel usuarioSelecionado = usuarioController.listarTodos().stream()
                    .filter(usuario -> usuario.getNome().equals(nomeUsuario))
                    .findFirst()
                    .orElse(null);

            if (usuarioSelecionado != null) {
                List<UsuarioModel> usuarioList = List.of(usuarioSelecionado);
                UsuarioTabela modeloFiltrado = new UsuarioTabela(usuarioList);
                tabelaInfos.setModel(modeloFiltrado);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
