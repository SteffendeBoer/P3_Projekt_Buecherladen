package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Buch;
import org.example.model.Exemplar;
import javax.swing.*;
import java.awt.*;

public class ExemplarAnlegenDialog extends JDialog {
    private JComboBox<Buch> comboBuecher;
    private JTextField txtExemplarID;
    private BibliothekService service;

    public ExemplarAnlegenDialog(Frame owner, BibliothekService service) {
        super(owner, "Neues Exemplar hinzufügen", true);
        this.service = service;
        setSize(400, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Buch-Auswahl aus den Stammdaten
        mainPanel.add(new JLabel("Buch auswählen:"));
        comboBuecher = new JComboBox<>(service.getAlleBuecher().toArray(new Buch[0]));
        mainPanel.add(comboBuecher);

        // 2. ID-Eingabe
        mainPanel.add(new JLabel("Neue Exemplar-ID:"));
        txtExemplarID = new JTextField();
        // Automatischer Vorschlag für die ID
        int naechsteID = service.getAlleExemplare().size() + 1001;
        txtExemplarID.setText(String.valueOf(naechsteID));
        mainPanel.add(txtExemplarID);

        // Buttons
        JPanel southPanel = new JPanel();
        JButton btnSpeichern = new JButton("Anlegen");
        JButton btnAbbrechen = new JButton("Abbrechen");

        btnSpeichern.addActionListener(e -> {
            Buch gewaehlt = (Buch) comboBuecher.getSelectedItem();
            try {
                int id = Integer.parseInt(txtExemplarID.getText());
                // Wir nutzen hier die Methode istVerleihbar() aus deinem Modell
                Exemplar neu = new Exemplar(id, gewaehlt.getIsbn(), true);
                
                service.addExemplar(neu);
                JOptionPane.showMessageDialog(this, "Exemplar für '" + gewaehlt.getTitel() + "' wurde angelegt.");
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID muss eine Zahl sein!");
            }
        });

        btnAbbrechen.addActionListener(e -> dispose());
        southPanel.add(btnSpeichern);
        southPanel.add(btnAbbrechen);

        add(mainPanel, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }
}