package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Ausleihe;
import javax.swing.*;
import java.awt.*;

public class AusleiheAnlegenDialog extends JDialog {
    private JTextField txtID = new JTextField();
    private JTextField txtExemplarID = new JTextField();
    private JTextField txtNutzerID = new JTextField();
    private JTextField txtGebuehr = new JTextField();

    public AusleiheAnlegenDialog(Frame owner, BibliothekService service) {
        super(owner, "Neue Ausleihe", true);
        setSize(350, 250);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Anforderung: GridLayout (4 Zeilen, 2 Spalten)
        JPanel form = new JPanel(new GridLayout(4, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Ausleih-ID:"));
        form.add(txtID);
        form.add(new JLabel("Exemplar-ID:"));
        form.add(txtExemplarID);
        form.add(new JLabel("Nutzer-ID:"));
        form.add(txtNutzerID);
        form.add(new JLabel("Gebühr:"));
        form.add(txtGebuehr);

        add(form, BorderLayout.CENTER);

        JButton btnSave = new JButton("Ausleihen");
        btnSave.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtID.getText());
                int eID = Integer.parseInt(txtExemplarID.getText());
                int nID = Integer.parseInt(txtNutzerID.getText());
                double gebuehr = Double.parseDouble(txtGebuehr.getText());

                long jetzt = System.currentTimeMillis();
                long frist = jetzt + (14L * 24 * 60 * 60 * 1000); // 14 Tage Frist

                Ausleihe a = new Ausleihe(id, eID, nID, jetzt, frist, gebuehr);
                service.addAusleihe(a);
                dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Bitte prüfen Sie Ihre Eingaben!");
            }
        });

        JPanel south = new JPanel();
        south.add(btnSave);
        add(south, BorderLayout.SOUTH);
    }
}