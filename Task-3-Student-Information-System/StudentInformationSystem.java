import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StudentInformationSystem extends JFrame implements ActionListener {

    private JTextField txtId, txtName, txtCourse;
    private JButton btnAdd, btnUpdate, btnDelete, btnClear;

    public StudentInformationSystem() {
        setTitle("Student Information System");
        setSize(500, 280);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(15, 15));

        // ================= FORM PANEL =================
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Student Details"));

        formPanel.add(new JLabel("Student ID:"));
        txtId = new JTextField();
        formPanel.add(txtId);

        formPanel.add(new JLabel("Student Name:"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Course:"));
        txtCourse = new JTextField();
        formPanel.add(txtCourse);

        // ================= BUTTON PANEL =================
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));

        btnAdd = new JButton("Add");
        btnUpdate = new JButton("Update");
        btnDelete = new JButton("Delete");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        // ================= ADD PANELS TO FRAME =================
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // ================= ACTION LISTENERS =================
        btnAdd.addActionListener(this);
        btnUpdate.addActionListener(this);
        btnDelete.addActionListener(this);
        btnClear.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnAdd) {
            JOptionPane.showMessageDialog(this, "Student Added!");
        } 
        else if (e.getSource() == btnUpdate) {
            JOptionPane.showMessageDialog(this, "Student Updated!");
        } 
        else if (e.getSource() == btnDelete) {
            JOptionPane.showMessageDialog(this, "Student Deleted!");
        } 
        else if (e.getSource() == btnClear) {
            txtId.setText("");
            txtName.setText("");
            txtCourse.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentInformationSystem());
    }
}
