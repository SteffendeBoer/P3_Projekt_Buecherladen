package org.example.gui;

import org.example.logic.BibliothekService;
import org.example.model.Ausleihe;
import org.example.model.Buch;
import org.example.model.Nutzer;
import org.example.model.Exemplar;

import javax.swing.*;
import java.awt.*;

public class AusleiheAnlegenDialog extends JDialog {
    private JComboBox<Buch> comboWerke;
    private JComboBox<Exemplar> comboExemplare;
    private JComboBox<Nutzer> comboNutzer;
    private BibliothekService service;

    public AusleiheAnlegenDialog(Frame owner, BibliothekService service) {
        super(owner, "Neue Ausleihe", true);
        this.service = service;
        setLayout(new BorderLayout());
        setSize(450, 350);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Werke laden
        comboWerke = new JComboBox<>(service.getAlleBuecher().toArray(new Buch[0]));
        // 2. Nutzer laden
        comboNutzer = new JComboBox<>(service.getAlleNutzer().toArray(new Nutzer[0]));
        // 3. Leere ComboBox für Exemplare
        comboExemplare = new JComboBox<>();

        // LOGIK: Wenn ein Werk ausgewählt wird, lade passende Exemplare
        comboWerke.addActionListener(e -> aktualisiereExemplarListe());

        panel.add(new JLabel("1. Buch (Werk) wählen:"));
        panel.add(comboWerke);
        panel.add(new JLabel("2. Exemplar wählen:"));
        panel.add(comboExemplare);
        panel.add(new JLabel("3. Nutzer wählen:"));
        panel.add(comboNutzer);

        // Initial beim Start einmal laden
        aktualisiereExemplarListe();

        JButton btnSpeichern = new JButton("Ausleihe bestätigen");
        btnSpeichern.addActionListener(e -> {
            Exemplar ex = (Exemplar) comboExemplare.getSelectedItem();
            Nutzer n = (Nutzer) comboNutzer.getSelectedItem();

            if (ex != null && n != null) {
                long heute = System.currentTimeMillis();
                long frist = heute + (14L * 24 * 60 * 60 * 1000);
                int neueID = service.getAlleAusleihen().size() + 1;

                // Verknüpfung über die Exemplar-ID
                Ausleihe neue = new Ausleihe(neueID, ex.getExemplarID(), n.getNutzerID(), heute, frist, 0.0);
                
                service.ausleiheDurchfuehren(neue, ex);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Kein verfügbares Exemplar ausgewählt!");
            }
        });

        add(panel, BorderLayout.CENTER);
        add(btnSpeichern, BorderLayout.SOUTH);
    }

    private void aktualisiereExemplarListe() {
        comboExemplare.removeAllItems();
        Buch ausgewähltesWerk = (Buch) comboWerke.getSelectedItem();
        
        if (ausgewähltesWerk != null) {
            for (Exemplar ex : service.getAlleExemplare()) {
                // Prüfung: Gleiche ISBN UND das Stück muss verleihbar sein
                if (ex.getIsbn().equals(ausgewähltesWerk.getIsbn()) && ex.istVerleihbar()) {
                    comboExemplare.addItem(ex);
                }
            }
        }
    }
}