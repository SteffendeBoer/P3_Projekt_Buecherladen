package org.example; // Das Package (Ordner-Namespace). Muss zum Ordnerpfad passen.

import java.awt.BorderLayout;               // Layout-Manager: ordnet Komponenten in NORTH/SOUTH/EAST/WEST/CENTER an.
import java.awt.event.ItemEvent;            // Event-Objekt für Checkbox/Toggle (ausgewählt/abgewählt).
import java.awt.event.ItemListener;         // Interface: reagiert auf ItemEvents (z.B. Checkbox angeklickt).
import java.awt.event.KeyEvent;             // Key-Codes (z.B. VK_N für Shortcut-Taste).

import javax.swing.Action;                  // Swing Action: kapselt "was passiert beim Klick" + Name/Icon/Shortcut.
import javax.swing.DefaultListModel;        // Datenmodell für eine JList (Liste von Einträgen).
import javax.swing.DefaultListSelectionModel;// Auswahlmodell (welche Einträge in der Liste ausgewählt sind).
import javax.swing.ImageIcon;               // Icon-Klasse für Buttons/Menüeinträge.
import javax.swing.JButton;                 // Button.
import javax.swing.JCheckBoxMenuItem;       // Menüeintrag mit Checkbox (an/aus).
import javax.swing.JFrame;                  // Hauptfenster.
import javax.swing.JList;                   // Liste zur Anzeige von Suchergebnissen.
import javax.swing.JMenu;                   // Menü (z.B. "Actions").
import javax.swing.JMenuBar;                // Menüleiste oben im Fenster.
import javax.swing.JMenuItem;               // Normaler Menüeintrag.
import javax.swing.JPanel;                  // Container für andere Komponenten.
import javax.swing.JScrollPane;             // Scrollbar-Container (z.B. für JList).
import javax.swing.JTextField;              // Textfeld.
import javax.swing.JToolBar;                // Toolbar (Leiste mit Buttons).
import javax.swing.text.Document;           // Text-Datenmodell (Inhalt eines Textfelds).
import javax.swing.text.PlainDocument;      // Einfaches Document für Text.

// import org.example.actions.BookNewAction;   // Eure Action: "Neues Buch erstellen".
// import org.example.actions.BookSearchAction;// Eure Action: "Suche ausführen".
import org.example.model.BookManager;       // Eure Klasse, die Bücher verwaltet & durchsucht.

public class MVCApp extends JFrame implements ItemListener { // MVCApp ist ein Fenster (JFrame) und hört auf ItemEvents.

    // (Optional) Eine statische Referenz auf die App. In deinem Code wird sie aktuell NICHT benutzt.
    private static MVCApp app;

    // Swing Actions (Controller-Teil): diese werden an Buttons/Menüs gehängt.
    // WICHTIG: Variablen in Java klein schreiben -> bookSearchAction statt BookSearchAction.
    private Action bookNewAction;       // Action für "Neues Buch"
    private Action bookSearchAction;    // Action für "Suchen"  (war bei dir teils falsch benannt)

    // Checkbox im Menü, mit der man die New-Action aktivieren/deaktivieren kann.
    private JCheckBoxMenuItem bookNewActionStateToggleItem;

    // Service/Model-Teil: verwaltet die Bücher und liefert Suchergebnisse.
    private BookManager bookService = new BookManager();

    // ListModel: hier landen die Suchergebnisse, die die JList anzeigt.
    private DefaultListModel searchResultListModel = new DefaultListModel();

    // SelectionModel: merkt sich, welcher Eintrag in der Liste ausgewählt ist.
    private DefaultListSelectionModel searchResultSelectionModel = new DefaultListSelectionModel();

    // Document: Text-Datenmodell für das Suchfeld (damit die SearchAction den Text auslesen kann).
    private Document searchInput = new PlainDocument();

    // Konstruktor (private, weil App über main() gestartet wird).
    private MVCApp() {
        super("BücherLaden"); // Setzt den Fenstertitel.

        this.addWindowListener(new MVCAppWindowListener()); // Listener für Fenster-Events (z.B. schließen).
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);       // Beim Schließen der App: Programm beenden.
        this.setSize(1600, 900);                            // Fenstergröße.

        this.setLayout(new BorderLayout()); // Layout für das Hauptfenster: oben Toolbar, Mitte Inhalt.

        createActions(); // Erst Actions erstellen, damit Menüs/Buttons sie benutzen können.

        this.setJMenuBar(createMenuBar());                         // Menüleiste setzen.
        this.add(createToolBar(), BorderLayout.PAGE_START);        // Toolbar oben hinzufügen.
        this.add(createMainPanel(), BorderLayout.CENTER);          // Hauptpanel in die Mitte.

        this.setLocationByPlatform(true); // OS darf die Position des Fensters bestimmen.
        this.setVisible(true);            // Fenster anzeigen.
    }

    // Einstiegspunkt: hier startet das Programm.
    public static void main(String[] args) {
        // Swing sollte immer im "Event Dispatch Thread" (EDT) gestartet werden.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MVCApp(); // Erzeugt und zeigt die App.
            }
        });
    }

    // Hier werden die Actions initialisiert (also was passiert bei "New" und "Search").
    private void createActions() {

        // BookNewAction: erhält Anzeigetext, Icon, Tooltip/Description und eine Shortcut-Taste.
        bookNewAction = new BookNewAction(
                "New ",                    // Text der Action (für Menüeintrag / Button).
                createIcon("/icons/13.gif"), // Icon-Datei aus den Ressourcen (Classpath).
                "Creates a new book object.",// Beschreibung (z.B. Tooltip).
                KeyEvent.VK_N               // Shortcut-Key (z.B. N).
        );

        // BookSearchAction: bekommt Zugriff auf Suchtext (Document), Ergebnis-Model und Service.
        // WICHTIG: Bei dir stand "SearchAction =" -> das ist ein Fehler (Variable existiert nicht).
        bookSearchAction = new SearchAction(
                searchInput,               // hier liest die Action den Suchtext aus
                searchResultListModel,      // hier schreibt die Action die Treffer rein
                bookService                // damit kann die Action im BookManager suchen
        );
    }

    // Baut die Menüleiste zusammen.
    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar(); // Obere Menüleiste.

        JMenu stateMenu = new JMenu("States");   // Menü für Zustände/Schalter (z.B. Action enabled).
        JMenu booksMenu = new JMenu("Actions");  // Menü für Aktionen (z.B. New/Search).

        // Menüeintrag für die New-Action erzeugen (nimmt Name/Shortcut/Icon automatisch von der Action).
        JMenuItem bookNewItem = new JMenuItem(bookNewAction);

        // Icon im Menü ausblenden (weil wir evtl. Icons nur in Toolbar wollen).
        bookNewItem.setIcon(null);

        // Checkbox-Menüeintrag, der die Action an/aus schaltet. 
        bookNewActionStateToggleItem = new JCheckBoxMenuItem("New  Action enabled");

        // Startzustand: aktiviert.
        bookNewActionStateToggleItem.setSelected(true);

        // ItemListener: wenn Checkbox an/aus, wird itemStateChanged() aufgerufen.
        bookNewActionStateToggleItem.addItemListener(this);

        // Menu-Einträge in die Menüs packen.
        booksMenu.add(bookNewItem);                 // "New" kommt in Actions-Menü.
        stateMenu.add(bookNewActionStateToggleItem);// Checkbox kommt in States-Menü.

        // Menüs zur Menüleiste hinzufügen.
        menuBar.add(booksMenu);
        menuBar.add(stateMenu);

        return menuBar; // Fertige Menüleiste zurückgeben.
    }

    // Baut die Toolbar oben zusammen.
    private JToolBar createToolBar() {

        JToolBar toolBar = new JToolBar(); // Toolbar-Container.

        // Button, der dieselbe Action nutzt wie der Menüeintrag.
        JButton bookNewButton = new JButton(bookNewAction);

        bookNewButton.setFocusPainted(false); // Keine gestrichelten Fokus-Ränder zeichnen.
        bookNewButton.setText(null);          // Kein Text im Button (nur Icon).

        toolBar.add(bookNewButton); // Button zur Toolbar hinzufügen.

        return toolBar; // Fertige Toolbar zurückgeben.
    }

    // Baut das zentrale Panel (Suchfeld + Button + Ergebnisliste).
    private JPanel createMainPanel() {

        // JList zum Anzeigen der Suchergebnisse.
        JList searchResultList = new JList();

        // Die JList zeigt die Einträge aus dem Model an.
        searchResultList.setModel(searchResultListModel);

        // Die JList nutzt dieses SelectionModel (welcher Eintrag ist markiert?).
        searchResultList.setSelectionModel(searchResultSelectionModel);

        // Hauptpanel, das oben das Suchfeld und in der Mitte die Liste enthält.
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Panel für Such-Eingabe (Textfeld + Suchbutton nebeneinander).
        JPanel searchInputPanel = new JPanel();
        searchInputPanel.setLayout(new BorderLayout());

        // Textfeld, das sein Document "searchInput" nutzt (damit die Action den Text lesen kann).
        JTextField searchField = new JTextField(searchInput, null, 0);

        // Textfeld in die Mitte (CENTER) des Suchpanels.
        searchInputPanel.add(searchField, BorderLayout.CENTER);

        // Wenn ENTER im Textfeld gedrückt wird -> Suche ausführen (ActionListener ist die Action).
        // WICHTIG: Bei dir stand bookSearchAction, aber Variable war nicht korrekt definiert.
        searchField.addActionListener(bookSearchAction);

        // Suchbutton rechts neben dem Textfeld, nutzt ebenfalls die SearchAction.
        JButton searchButton = new JButton(bookSearchAction);

        searchButton.setFocusPainted(false); // Fokus-Rand aus.

        // Button rechts (EAST) in das Suchpanel.
        searchInputPanel.add(searchButton, BorderLayout.EAST);

        // Panel für die Ergebnisliste (mit Scrollbars).
        JPanel searchResultPanel = new JPanel();
        searchResultPanel.setLayout(new BorderLayout());

        // ScrollPane macht die Liste scrollbar.
        JScrollPane scrollableSearchResult = new JScrollPane(searchResultList);

        // ScrollPane in die Mitte des Ergebnis-Panels.
        searchResultPanel.add(scrollableSearchResult, BorderLayout.CENTER);

        // Suchpanel oben in MainPanel, Ergebnispanel in die Mitte.
        mainPanel.add(searchInputPanel, BorderLayout.NORTH);
        mainPanel.add(searchResultPanel, BorderLayout.CENTER);

        return mainPanel; // Fertiges Hauptpanel zurückgeben.
    }

    // Wird aufgerufen, wenn ein ItemEvent passiert (bei uns: Checkbox im Menü).
    @Override
    public void itemStateChanged(ItemEvent e) {

        // Das Event kommt von einem JCheckBoxMenuItem -> casten wir zurück.
        JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) (e.getSource());

        // true wenn ausgewählt, false wenn abgewählt.
        boolean selected = (e.getStateChange() == ItemEvent.SELECTED);

        // Wenn genau unsere Checkbox geändert wurde:
        if (checkBoxMenuItem.equals(bookNewActionStateToggleItem)) {

            // Dann schalten wir die New-Action an/aus.
            // Effekt: Menüeintrag/Button werden disabled/enabled.
            bookNewAction.setEnabled(selected);
        }
    }

    /**
     * Lädt ein Icon aus den Ressourcen (Classpath) und gibt ein ImageIcon zurück.
     * @param filePath Pfad zur Ressource, z.B. "/icons/13.gif"
     * @return ImageIcon oder null, wenn Ressource nicht gefunden
     */
    private ImageIcon createIcon(String filePath) {

        // Sucht die Ressource relativ zur Klasse (Classpath).
        java.net.URL imgURL = getClass().getResource(filePath);

        // Wenn nicht gefunden -> Fehler ausgeben + null zurück.
        if (imgURL == null) {
            System.err.println("Resource not found: " + imgURL);
            return null;
        } else {
            // Sonst Icon erstellen und zurückgeben.
            return new ImageIcon(imgURL);
        }
    }
}
