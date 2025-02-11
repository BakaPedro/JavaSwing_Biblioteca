package view;

import controller.EmprestimoController;
import controller.LivroController;
import model.EmprestimoModel;
import model.LivroModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class EmprestimosView extends JFrame{
    private JLabel tituloPrincipal;
    private JPanel jpanelPrincipal;
    private JButton buttonAdd;
    private JButton deleteButton;
    private JTable tableEmprestimo;
    JMenuBar menuBar = new JMenuBar();
    private DefaultListModel<String> modeloLista;
    public EmprestimosView() throws SQLException {
        criaMenu();
        menuBar = new JMenuBar();

        this.setTitle("Sistema - Biblioteca - Livros");
        this.setContentPane(jpanelPrincipal);
        this.setSize(800, 480);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    new FazerEmprestimoView();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                dispose();
            }
        });
    atualizarTabela();
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
    public static class EmprestimoTabela extends AbstractTableModel {

        private final String[] colunasDaTabela = new String[]{
                "IdEmprestimo", "Livro", "Usuario", "Data de Emprestimo", "Data de Devolução"};
        private final List<EmprestimoModel> listaDeEmprestimos;


        public EmprestimoTabela(List<EmprestimoModel> listaDeEmprestimos) {
            this.listaDeEmprestimos = listaDeEmprestimos;
        }
        public int getRowCount() {
            return listaDeEmprestimos.size();
        }
        public int getColumnCount(){
            return colunasDaTabela.length;
        }
        public Object getValueAt(int rowIndex, int columnIndex){
            return switch (columnIndex){
                case 0 -> listaDeEmprestimos.get(rowIndex).getIdEmprestimo();
                case 1 -> listaDeEmprestimos.get(rowIndex).getLivro().getTitulo();
                case 2 -> listaDeEmprestimos.get(rowIndex).getUsuario().getNome();
                case 3 -> listaDeEmprestimos.get(rowIndex).getDataEmprestimo();
                case 4 -> listaDeEmprestimos.get(rowIndex).getDataDevolucao();
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
    private void atualizarTabela() throws SQLException {
        EmprestimoController emprestimoController = new EmprestimoController();
        try {
            tableEmprestimo.setModel(new EmprestimoTabela(emprestimoController.listarTodos()));
            } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }
}
