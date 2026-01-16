package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Nutzer;
import javax.swing.*;
import java.awt.*;

public class NutzerEingabeDialog extends JDialog {
    private JTextField txtID = new JTextField();
    private JTextField txtVorname = new JTextField();
    private JTextField txtNachname = new JTextField();
    private JTextField txtEmail = new JTextField();


    public NutzerEingabeDialog(Frame owner, BibliothekService service) {
        super(owner, "Nutzer anlegen", true);
        setSize(300, 200);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // GridLayout für das Formular (3 Zeilen, 2 Spalten)
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        formPanel.add(new JLabel("Nutzer-ID:"));
        formPanel.add(txtID);
        formPanel.add(new JLabel("Vorname:"));
        formPanel.add(txtVorname);
        formPanel.add(new JLabel("Nachname:"));
        formPanel.add(txtNachname);

        add(formPanel, BorderLayout.CENTER);

        JButton btnSave = new JButton("Speichern");
        btnSave.addActionListener(e -> {
            if (!txtNachname.getText().isEmpty()) {
                try {
                    int id = Integer.parseInt(txtID.getText());
                    Nutzer n = new Nutzer(id, txtVorname.getText(), txtNachname.getText());
                    service.addNutzer(n);
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID muss eine Zahl sein!");
                }
            }
        });

        JPanel south = new JPanel();
        south.add(btnSave);
        add(south, BorderLayout.SOUTH);
    }
}