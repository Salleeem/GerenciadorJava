import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Main {
    private JFrame frame;
    private DefaultListModel<String> ModeloLista;
    private JList<String> ListaTarefas;
    private JTextField CampoTarefas;
    private JComboBox<String> BoxSelecao;
    private ArrayList<Tarefas> Tarefinhas;

    public Main() {
        frame = new JFrame("Gerenciamento de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        Tarefinhas = new ArrayList<>();

        ModeloLista = new DefaultListModel<>();
        ListaTarefas = new JList<>(ModeloLista);
        ListaTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        CampoTarefas = new JTextField();
        JButton Adicionar = new JButton("Adicionar");
        JButton Deletar = new JButton("Excluir");
        JButton Concluir = new JButton("Marcar como Concluída");
        BoxSelecao = new JComboBox<>(new String[]{"Todas", "Trabalho", "Estudo", "Lazer"});

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputPanel.add(CampoTarefas, BorderLayout.CENTER);
        inputPanel.add(Adicionar, BorderLayout.EAST);
        inputPanel.add(Deletar, BorderLayout.WEST);
        inputPanel.add(Concluir, BorderLayout.SOUTH);

        frame.add(BoxSelecao, BorderLayout.NORTH);
        frame.add(new JScrollPane(ListaTarefas), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Event listeners
        Adicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String DescricaoTarefa = CampoTarefas.getText();
                if (!DescricaoTarefa.isEmpty()) {
                    String CategoriaSelecionada = (String) BoxSelecao.getSelectedItem();
                    Tarefas newTarefas = new Tarefas(DescricaoTarefa, CategoriaSelecionada);
                    Tarefinhas.add(newTarefas);
                    AtualizarLista();
                    CampoTarefas.setText("");
                }
            }
        });

        Deletar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = ListaTarefas.getSelectedIndex();
                if (selectedIndex != -1) {
                    Tarefinhas.remove(selectedIndex);
                    AtualizarLista();
                }
            }
        });

        Concluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = ListaTarefas.getSelectedIndex();
                if (selectedIndex != -1) {
                    Tarefas TarefaSelecionada = Tarefinhas.get(selectedIndex);
                    TarefaSelecionada.setConcluido(true);
                    AtualizarLista();
                }
            }
        });

        BoxSelecao.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AtualizarLista();
            }
        });

        frame.setVisible(true);
    }

    private void AtualizarLista() {
        ModeloLista.clear();

        String CategoriaSelecionada = (String) BoxSelecao.getSelectedItem();

        for (Tarefas Tarefas : Tarefinhas) {
            if (CategoriaSelecionada.equals("Todas") || Tarefas.getCategoria().equals(CategoriaSelecionada) || (Tarefas.isConcluido() && CategoriaSelecionada.equals("Todas"))) {
                ModeloLista.addElement(Tarefas.toString());
            }
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}


class Tarefas {
    private String Descricao;
    private String Categoria;
    private boolean Concluido;

    public Tarefas(String Descricao, String Categoria) {
        this.Descricao = Descricao;
        this.Categoria = Categoria;
        this.Concluido = false;
    }

    public String getDescription() {
        return Descricao;
    }

    public String getCategoria() {
        return Categoria;
    }

    public boolean isConcluido() {
        return Concluido;
    }

    public void setConcluido(boolean Concluido) {
        this.Concluido = Concluido;
    }

    @Override
    public String toString() {
        if (Concluido) {
            return Descricao + " [Concluída]";
        }
        return Descricao;
    }
}
