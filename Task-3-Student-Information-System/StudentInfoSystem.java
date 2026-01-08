import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class StudentInfoSystem extends Application {

    TableView<Student> table;
    TextField idField, nameField, courseField;

    ObservableList<Student> studentList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {

        // Input fields
        idField = new TextField();
        idField.setPromptText("Student ID");

        nameField = new TextField();
        nameField.setPromptText("Student Name");

        courseField = new TextField();
        courseField.setPromptText("Course");

        // Buttons
        Button addBtn = new Button("Add");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");

        // Table columns
        TableColumn<Student, String> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(data -> data.getValue().idProperty());

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());

        TableColumn<Student, String> courseCol = new TableColumn<>("Course");
        courseCol.setCellValueFactory(data -> data.getValue().courseProperty());

        table = new TableView<>();
        table.setItems(studentList);
        table.getColumns().addAll(idCol, nameCol, courseCol);

        // Add student
        addBtn.setOnAction(e -> {
            if (validate()) {
                studentList.add(new Student(
                        idField.getText(),
                        nameField.getText(),
                        courseField.getText()
                ));
                clearFields();
            }
        });

        // Update student
        updateBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null && validate()) {
                selected.setId(idField.getText());
                selected.setName(nameField.getText());
                selected.setCourse(courseField.getText());
                table.refresh();
                clearFields();
            }
        });

        // Delete student
        deleteBtn.setOnAction(e -> {
            Student selected = table.getSelectionModel().getSelectedItem();
            if (selected != null) {
                studentList.remove(selected);
                clearFields();
            }
        });

        // Load selected row into fields
        table.setOnMouseClicked(e -> {
            Student s = table.getSelectionModel().getSelectedItem();
            if (s != null) {
                idField.setText(s.getId());
                nameField.setText(s.getName());
                courseField.setText(s.getCourse());
            }
        });

        // Layout
        VBox inputBox = new VBox(10, idField, nameField, courseField, addBtn, updateBtn, deleteBtn);
        inputBox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setLeft(inputBox);
        root.setCenter(table);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Student Information System");
        stage.setScene(scene);
        stage.show();
    }

    private boolean validate() {
        if (idField.getText().isEmpty() ||
            nameField.getText().isEmpty() ||
            courseField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validation Error");
            alert.setHeaderText(null);
            alert.setContentText("All fields are required!");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        courseField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
