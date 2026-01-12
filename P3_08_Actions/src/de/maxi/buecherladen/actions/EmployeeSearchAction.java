package de.maxi.buecherladen.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
    import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import de.maxi.buecherladen.model.Employee;
import de.maxi.buecherladen.model.EmployeeManager;

/**
 * Sucht Mitarbeiter:innen anhand eines Suchfeldes.
 */
public class EmployeeSearchAction extends AbstractAction {

    private final Document searchInput;
    private final DefaultListModel<Employee> searchResult;
    private final EmployeeManager employeeManager;

    public EmployeeSearchAction(Document searchInput,
                                DefaultListModel<Employee> searchResult,
                                EmployeeManager employeeManager) {
        super("Search");
        this.searchInput = searchInput;
        this.searchResult = searchResult;
        this.employeeManager = employeeManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String query = searchInput.getText(0, searchInput.getLength()).trim();
            searchResult.clear();
            if (query.isEmpty()) {
                return;
            }
            List<Employee> matched = employeeManager.searchEmployees(query);
            matched.forEach(searchResult::addElement);
        } catch (BadLocationException ex) {
            System.err.println("Fehler beim Auslesen des Suchfeldes: " + ex.getMessage());
        }
    }
}
