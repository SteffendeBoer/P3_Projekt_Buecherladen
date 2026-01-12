package de.maxi.buecherladen.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import de.maxi.buecherladen.model.Employee;
import de.maxi.buecherladen.model.EmployeeManager;

/**
 * Fügt einen neuen Mitarbeiter / eine neue Mitarbeiterin hinzu.
 */
public class EmployeeNewAction extends AbstractAction {

    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JTextField emailField;
    private final JTextField phoneField;
    private final JComboBox<Employee.Role> roleCombo;
    private final DefaultListModel<Employee> employeeListModel;
    private final EmployeeManager employeeManager;

    public EmployeeNewAction(JTextField firstNameField,
                             JTextField lastNameField,
                             JTextField emailField,
                             JTextField phoneField,
                             JComboBox<Employee.Role> roleCombo,
                             DefaultListModel<Employee> employeeListModel,
                             EmployeeManager employeeManager) {
        super("Add Employee");
        this.firstNameField = firstNameField;
        this.lastNameField  = lastNameField;
        this.emailField     = emailField;
        this.phoneField     = phoneField;
        this.roleCombo      = roleCombo;
        this.employeeListModel = employeeListModel;
        this.employeeManager   = employeeManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String firstName = firstNameField.getText().trim();
        String lastName  = lastNameField.getText().trim();
        String email     = emailField.getText().trim();
        String phone     = phoneField.getText().trim();
        Employee.Role role = (Employee.Role) roleCombo.getSelectedItem();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            return;
        }

        Employee emp = employeeManager.addEmployee(firstName, lastName, email, phone, role);
        employeeListModel.addElement(emp);

        firstNameField.setText("");
        lastNameField.setText("");
        emailField.setText("");
        phoneField.setText("");
    }
}
