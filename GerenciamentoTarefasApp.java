import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GerenciamentoTarefasApp {
    private JFrame frame;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskList;
    private JTextField taskField;
    private JComboBox<String> categoryComboBox;
    private ArrayList<String> tasks;
    private Map<String, List<String>> categorizedTasks;

    public GerenciamentoTarefasApp() {
        frame = new JFrame("Gerenciamento de Tarefas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        tasks = new ArrayList<>();
        categorizedTasks = new HashMap<>();
        categorizedTasks.put("Todas", tasks);

        taskListModel = new DefaultListModel<>();
        taskList = new JList<>(taskListModel);
        taskList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        taskField = new JTextField();
        JButton addButton = new JButton("Adicionar");
        JButton deleteButton = new JButton("Excluir");
        JButton completeButton = new JButton("Marcar como Concluída");
        categoryComboBox = new JComboBox<>(new String[]{"Todas", "Trabalho", "Estudo", "Lazer"});

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BorderLayout());

        inputPanel.add(taskField, BorderLayout.CENTER);
        inputPanel.add(addButton, BorderLayout.EAST);
        inputPanel.add(deleteButton, BorderLayout.WEST);
        inputPanel.add(completeButton, BorderLayout.SOUTH);

        frame.add(categoryComboBox, BorderLayout.NORTH);
        frame.add(new JScrollPane(taskList), BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        // Event listeners
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String task = taskField.getText();
                if (!task.isEmpty()) {
                    String selectedCategory = (String) categoryComboBox.getSelectedItem();
                    categorizedTasks.get(selectedCategory).add(task);
                    updateTaskList();
                    taskField.setText("");
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = taskList.getSelectedIndex();
                if (selectedIndex != -1) {
                    String selectedTask = taskListModel.getElementAt(selectedIndex);
                    tasks.remove(selectedTask);
                    updateTaskList();
                }
            }
        });

        completeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                markTaskAsCompleted();
            }
        });

        categoryComboBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTaskList();
            }
        });

        frame.setVisible(true);
    }

    private void markTaskAsCompleted() {
        int selectedIndex = taskList.getSelectedIndex();
        if (selectedIndex != -1) {
            String selectedTask = taskListModel.getElementAt(selectedIndex);
            if (!selectedTask.endsWith(" [Concluída]")) {
                selectedTask = selectedTask + " [Concluída]";
                taskListModel.set(selectedIndex, selectedTask);
            }
        }
    }

    private void updateTaskList() {
        taskListModel.clear();

        String selectedCategory = (String) categoryComboBox.getSelectedItem();
        List<String> selectedTasks = categorizedTasks.get(selectedCategory);

        for (String task : selectedTasks) {
            taskListModel.addElement(task);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GerenciamentoTarefasApp();
            }
        });
    }
}
