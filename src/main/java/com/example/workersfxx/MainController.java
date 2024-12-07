package com.example.workersfxx;

import com.example.workersfxx.models.ClassContainer;
import com.example.workersfxx.models.ClassEmployee;
import com.example.workersfxx.models.Employee;
import com.example.workersfxx.models.EmployeeCondition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class MainController {
    @FXML private ListView<String> groupListView;
    @FXML private TableView<Employee> employeeTableView;
    @FXML private TableColumn<Employee, String> colName, colSurname;
    @FXML private TableColumn<Employee, String> colCondition;
    @FXML private TableColumn<Employee, Integer> colBirthYear;
    @FXML private TableColumn<Employee, Double> colSalary;
    @FXML private TextField textFilter;

    private ClassContainer classContainer;
    private ObservableList<String> groupNames;
    private ObservableList<Employee> employees;

    public void initialize() {
        classContainer = new ClassContainer();
        groupNames = FXCollections.observableArrayList();
        employees = FXCollections.observableArrayList();

        // Bind data to UI components
        groupListView.setItems(groupNames);
        employeeTableView.setItems(employees);

        // Set up table columns to use JavaFX properties
        colName.setCellValueFactory(data -> data.getValue().imieProperty());
        colSurname.setCellValueFactory(data -> data.getValue().nazwiskoProperty());
        colCondition.setCellValueFactory(data -> data.getValue().stanProperty().asString());
        colBirthYear.setCellValueFactory(data -> data.getValue().rokUrodzeniaProperty().asObject());
        colSalary.setCellValueFactory(data -> data.getValue().wynagrodzenieProperty().asObject());

        // Add predefined groups
        groupNames.addAll("Group A", "Group B", "Group C");
        classContainer.addClass("Group A", 10);
        classContainer.addClass("Group B", 15);
        classContainer.addClass("Group C", 20);

        // Event listeners
        groupListView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) updateEmployeeTable(newVal);
        });

        textFilter.setOnAction(e -> filterEmployees());
    }

    private void updateEmployeeTable(String groupName) {

        ClassEmployee group = classContainer.getClassEmployee(groupName);
        if (group != null) {
            employees.setAll(group.getEmployees());
        }
    }


    private void filterEmployees() {
        String filter = textFilter.getText();

        // Avoid updating the entire ObservableList with filtered results
        // Use a new list to hold the filtered employees
        ObservableList<Employee> filteredList = FXCollections.observableArrayList(
                employees.filtered(emp -> emp.getNazwisko().contains(filter))
        );

        // Set the table items with the filtered list
        employeeTableView.setItems(filteredList);
    }


    @FXML
    private void onAddEmployee() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField nameField = new TextField();
        nameField.setPromptText("First Name");
        TextField surnameField = new TextField();
        surnameField.setPromptText("Last Name");
        TextField salaryField = new TextField();
        salaryField.setPromptText("Salary");
        TextField birthYearField = new TextField();
        birthYearField.setPromptText("Year of Birth");

        dialogPane.setContent(new VBox(10, nameField, surnameField, salaryField, birthYearField));

        dialog.setResultConverter(button -> {
            if (button == ButtonType.OK) {
                return new Employee(
                        nameField.getText(),
                        surnameField.getText(),
                        EmployeeCondition.OBECNY, // Assuming OBECNY is a default value
                        Integer.parseInt(birthYearField.getText()), // Default birth year
                        Double.parseDouble(salaryField.getText())
                );
            }
            return null;
        });

        dialog.showAndWait().ifPresent(employee -> {
            String groupName = groupListView.getSelectionModel().getSelectedItem();
            if (groupName != null) {
                classContainer.getClassEmployee(groupName).addEmployee(employee);
                updateEmployeeTable(groupName);
            }
        });
    }

    @FXML
    private void onRemoveEmployee() {
        // Get the selected employee from the TableView
        Employee selected = employeeTableView.getSelectionModel().getSelectedItem();

        // If an employee is selected, remove it from the group
        if (selected != null) {
            String groupName = groupListView.getSelectionModel().getSelectedItem();

            // Remove the employee from the selected group
            if (groupName != null) {
                classContainer.getClassEmployee(groupName).removeEmployee(selected);

                // Update the employee table to reflect the changes
                updateEmployeeTable(groupName);  // Refresh the table with updated list
            }
        }
    }


    @FXML
    private void onModifyEmployee() {
        Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setWynagrodzenie(selected.getWynagrodzenie() + 100); // Example modification
            employeeTableView.refresh();
        }
    }
}
