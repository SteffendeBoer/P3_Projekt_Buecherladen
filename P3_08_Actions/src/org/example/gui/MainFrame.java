package org.example.gui;

import org.example.logic.BibliothekService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private BibliothekService service;

    public MainFrame() {
        // Logik-Service initialisieren
        service = new BibliothekService();

        // Fenster-Einstellungen
        setTitle("Bibliotheksverwaltung Team-Projekt");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Zentriert das Fenster

        // Layout-Manager: BorderLayout (wie gefordert)
        setLayout(new BorderLayout());

        // Header-Bereich
        JLabel header = new JLabel("Bücherladen Verwaltung", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(header, BorderLayout.NORTH);

        // Center-Bereich: Buttons in einem GridLayout
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 1, 10, 10)); // 4 Zeilen, 1 Spalte
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 20, 50));

        JButton btnBuecher = new JButton("Bücher verwalten");
        JButton btnNutzer = new JButton("Nutzer verwalten");
        JButton btnAusleihe = new JButton("Ausleihen / Rückgabe");
        JButton btnBeenden = new JButton("Beenden");

        buttonPanel.add(btnBuecher);
        buttonPanel.add(btnNutzer);
        buttonPanel.add(btnAusleihe);
        buttonPanel.add(btnBeenden);

        add(buttonPanel, BorderLayout.CENTER);

        // Event-Handling (ActionListener)
        btnBeenden.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // Hier kommen später die Aufrufe für die anderen Fenster rein
        btnBuecher.addActionListener(e -> JOptionPane.showMessageDialog(this, "Buch-Verwaltung folgt!"));
    }

    public static void main(String[] args) {
        // GUI im Event-Dispatch-Thread starten (Best Practice)
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}