package de.maxi.buecherladen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verwaltet eine Liste von Mitarbeiter:innen.
 */
public class EmployeeManager {
    private final List<Employee> employees = new ArrayList<>();
    private int nextId = 1;

    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    public Employee addEmployee(String firstName, String lastName,
                                String email, String phone, Employee.Role role) {
        Employee emp = new Employee(nextId++, firstName, lastName, email, phone, role);
        employees.add(emp);
        return emp;
    }

    public void removeEmployee(Employee emp) {
        employees.remove(emp);
    }

    public void updateEmployee(Employee updated) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == updated.getId()) {
                employees.set(i, updated);
                break;
            }
        }
    }

    /**
     * Sucht Mitarbeiter:innen nach Namen (Vor‑ oder Nachname, case‑insensitive).
     */
    public List<Employee> searchEmployees(String query) {
        String q = query.toLowerCase();
        return employees.stream()
                        .filter(e -> e.getFirstName().toLowerCase().contains(q)
                                  || e.getLastName().toLowerCase().contains(q))
                        .collect(Collectors.toList());
    }
}
