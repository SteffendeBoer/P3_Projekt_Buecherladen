package de.maxi.buecherladen.model;

/**
 * Repräsentiert einen Mitarbeiter bzw. eine Mitarbeiterin.
 * Über das Rollen-Feld lassen sich Geschäftsführer, Chefs, normale Angestellte usw. unterscheiden.
 */
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Role role;

    public enum Role {
        CEO,
        MANAGER,
        EMPLOYEE
    }

    public Employee(int id, String firstName, String lastName,
                    String email, String phone, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    // Getters und Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    @Override
    public String toString() {
        return firstName + " " + lastName + " (" + role + ")";
    }
}
