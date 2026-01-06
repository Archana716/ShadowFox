import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class StudentInformationSystem extends JFrame implements ActionListener {

    // Components
    private JTextField txtId, txtName, txtCourse;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;
    private JTable table;
    private DefaultTableModel model;

    // Data storage
    private ArrayList<Student> students = new ArrayList<>();

    public StudentInformationSystem() {

        setTitle("Student Information System");
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        inputPanel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        inputPanel.add(txtId);

        inputPanel.add(new JLabel("Student Name:"));
        txtName = new JTextField();
        inputPanel.add(txtName);

        inputPanel.add(new JLabel("Course:"));
        txtCourse = new JTextField();
        inputPanel.add(txtCourse);

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        inputPanel.add(btnAdd);
        inputPanel.add(btnUpdate);
        inputPanel.add(btnDelete);
        inputPanel.add(btnClear);

        // Table
        model = new DefaultTableModel(new String[]{"ID", "Name", "Course"}, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);

        // Add panels
        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Event handling
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                txtId.setText(model.getValueAt(row, 0).toString());
                txtName.setText(model.getValueAt(row, 1).toString());
                txtCourse.setText(model.getValueAt(row, 2).toString());
            }
        });
    }

    // Button actions
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == btnAdd) {
            addStudent();
        } else if (e.getSource() == btnUpdate) {
            updateStudent();
        } else if (e.getSource() == btnDelete) {
            deleteStudent();
        } else if (e.getSource() == btnClear) {
            clearFields();
        }
    }

    private void addStudent() {
        String id = txtId.getText();
        String name = txtName.getText();
        String course = txtCourse.getText();

        if (id.isEmpty() || name.isEmpty() || course.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!");
            return;
        }

        students.add(new Student(id, name, course));
        model.addRow(new Object[]{id, name, course});
        clearFields();
    }

    private void updateStudent() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to update!");
            return;
        }

        model.setValueAt(txtId.getText(), row, 0);
        model.setValueAt(txtName.getText(), row, 1);
        model.setValueAt(txtCourse.getText(), row, 2);

        clearFields();
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a student to delete!");
            return;
        }

        model.removeRow(row);
        clearFields();
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
        txtCourse.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentInformationSystem().setVisible(true);
        });
    }
}

// Student class
class Student {
    String id;
    String name;
    String course;

    Student(String id, String name, String course) {
        this.id = id;
        this.name = name;
        this.course = course;
    }
}
