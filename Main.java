import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class Main {
    // Declaração de variáveis de instância
    private JFrame frame;                  // Janela principal da aplicação
    private DefaultListModel<String> ModeloLista;  // Modelo da lista de tarefas
    private JList<String> ListaTarefas;    // Componente de lista de tarefas
    private JTextField CampoTarefas;      // Campo de entrada de tarefas
    private JComboBox<String> BoxSelecao;  // Caixa de seleção para categorias
    private ArrayList<Tarefas> Tarefinhas;  // Lista de tarefas


    // Construtor da classe principal
    public Main() {
        // Configuração da janela principal
        frame = new JFrame("Gerenciamento de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        Tarefinhas = new ArrayList<>();  // Inicialização da lista de tarefas

        // Configuração do modelo da lista de tarefas e da lista em si
        ModeloLista = new DefaultListModel<>();
        ListaTarefas = new JList<>(ModeloLista);
        ListaTarefas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Configuração de componentes de entrada
        CampoTarefas = new JTextField();
        JButton Adicionar = new JButton("Adicionar");
        JButton Deletar = new JButton("Excluir");
        JButton Concluir = new JButton("Marcar como Concluída");
        BoxSelecao = new JComboBox<>(new String[]{"Todas", "Trabalho", "Estudo", "Lazer"});

        // Criação do painel de entrada
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputPanel.add(CampoTarefas, BorderLayout.CENTER);
        inputPanel.add(Adicionar, BorderLayout.EAST);
        inputPanel.add(Deletar, BorderLayout.WEST);
        inputPanel.add(Concluir, BorderLayout.SOUTH);

        // Adiciona os componentes à janela principal
        frame.add(BoxSelecao, BorderLayout.NORTH);
        frame.add(new JScrollPane(ListaTarefas), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Event listeners para os botões e o seletor
        Adicionar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lida com a ação de adicionar uma nova tarefa
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
                // Lida com a ação de excluir uma tarefa selecionada
                int selectedIndex = ListaTarefas.getSelectedIndex();
                if (selectedIndex != -1) {
                    Tarefinhas.remove(selectedIndex);
                    AtualizarLista();
                }
            }
        });

        Concluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Lida com a ação de marcar uma tarefa como concluída
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
                // Lida com a mudança na seleção do seletor
                AtualizarLista();
            }
        });

        frame.setVisible(true);  // Torna a janela visível
    }

    // Método para atualizar a lista de tarefas com base na seleção do seletor
    private void AtualizarLista() {
        ModeloLista.clear();

        String CategoriaSelecionada = (String) BoxSelecao.getSelectedItem();

        for (Tarefas Tarefas : Tarefinhas) {
            if (CategoriaSelecionada.equals("Todas") || Tarefas.getCategoria().equals(CategoriaSelecionada) || (Tarefas.isConcluido() && CategoriaSelecionada.equals("Todas"))) {
                ModeloLista.addElement(Tarefas.toString());
            }
        }
    }
}

// Classe que representa uma tarefa
class Tarefas {
    private String Descricao;
    private String Categoria;
    private boolean Concluido;

    // Construtor de tarefas
    public Tarefas(String Descricao, String Categoria) {
        this.Descricao = Descricao;
        this.Categoria = Categoria;
        this.Concluido = false;
    }

    // Obtém a descrição da tarefa
    public String getDescription() {
        return Descricao;
    }

    // Obtém a categoria da tarefa
    public String getCategoria() {
        return Categoria;
    }

    // Verifica se a tarefa está concluída
    public boolean isConcluido() {
        return Concluido;
    }

    // Define se a tarefa está concluída ou não
    public void setConcluido(boolean Concluido) {
        this.Concluido = Concluido;
    }

    // Gera uma representação de string da tarefa (incluindo "Concluída" se aplicável)
    @Override
    public String toString() {
        if (Concluido) {
            return Descricao + " [Concluída]";
        }
        return Descricao;
    }
}
