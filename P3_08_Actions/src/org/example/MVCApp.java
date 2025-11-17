package org.example;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;
import org.example.actions.StudentNewAction;
import org.example.actions.StudentSearchAction;
import org.example.model.StudentManager;


public class MVCApp extends JFrame implements ItemListener {

    private static MVCApp app;

    Action studentNewAction, StudentSearchAction;
    JCheckBoxMenuItem studentNewActionStateToggleItem;

    StudentManager studentService = new StudentManager();
    DefaultListModel searchResultListModel = new DefaultListModel();
    DefaultListSelectionModel searchResultSelectionModel = new DefaultListSelectionModel();
    Document searchInput = new PlainDocument();

    private MVCApp() {
        super("MVC App");
        this.addWindowListener(new MVCAppWindowListener());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(640, 300);

        this.setLayout(new BorderLayout());

        createActions();

        this.setJMenuBar(createMenuBar());
        this.add(createToolBar(), BorderLayout.PAGE_START);
        this.add(createMainPanel(), BorderLayout.CENTER);

        this.setLocationByPlatform(true);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MVCApp();
            }
        });
    }

    private void createActions() {
        studentNewAction = new StudentNewAction("New ",
                createIcon("/icons/13.gif"),
                "Creates a new Student object.",
                KeyEvent.VK_N);

        StudentSearchAction = new StudentSearchAction(searchInput,
                searchResultListModel, studentService);

    }

    private JMenuBar createMenuBar() {

        // create menu bar
        JMenuBar menuBar = new JMenuBar();

        // create menu
        JMenu stateMenu = new JMenu("States");
        JMenu studentsMenu = new JMenu("Actions");

        // create action menu items
        JMenuItem studentNewItem = new JMenuItem(studentNewAction);
        studentNewItem.setIcon(null); // do not show the action's icon

        // create toggle menu items
        studentNewActionStateToggleItem = new JCheckBoxMenuItem("New Action enabled");
        studentNewActionStateToggleItem.setSelected(true);
        studentNewActionStateToggleItem.addItemListener(this);

        // add menu items to menu
        studentsMenu.add(studentNewItem);
        stateMenu.add(studentNewActionStateToggleItem);

        // add menu to menu bar
        menuBar.add(studentsMenu);
        menuBar.add(stateMenu);

        return menuBar;
    }

    private JToolBar createToolBar() {

        // create tool bar
        JToolBar toolBar = new JToolBar();

        // create buttons
        JButton studentNewButton = new JButton(studentNewAction);
        studentNewButton.setFocusPainted(false); // do not show focus borders
        studentNewButton.setText(null); // do not show the action's text

        // add buttons to tool bar
        toolBar.add(studentNewButton);

        return toolBar;
    }

    private JPanel createMainPanel() {

        // create result list 
        JList searchResultList = new JList();
        searchResultList.setModel(searchResultListModel);
        searchResultList.setSelectionModel(searchResultSelectionModel);

        // create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // create search input panel
        JPanel searchInputPanel = new JPanel();
        searchInputPanel.setLayout(new BorderLayout());

        JTextField searchField = new JTextField(searchInput, null, 0);
        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchField.addActionListener(StudentSearchAction);

        JButton searchButton = new JButton(StudentSearchAction);
        searchButton.setFocusPainted(false);
        searchInputPanel.add(searchButton, BorderLayout.EAST);

        // create search result panel
        JPanel searchResultPanel = new JPanel();
        searchResultPanel.setLayout(new BorderLayout());
        JScrollPane scrollableSearchResult = new JScrollPane(searchResultList);
        searchResultPanel.add(scrollableSearchResult, BorderLayout.CENTER);

        // add input and result panels to main panel
        mainPanel.add(searchInputPanel, BorderLayout.NORTH);
        mainPanel.add(searchResultPanel, BorderLayout.CENTER);

        return mainPanel;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        JCheckBoxMenuItem checkBoxMenuItem = (JCheckBoxMenuItem) (e.getSource());
        boolean selected = (e.getStateChange() == ItemEvent.SELECTED);

        //Set the enabled state of the appropriate Action.
        if (checkBoxMenuItem.equals(studentNewActionStateToggleItem)) {
            studentNewAction.setEnabled(selected);
        }
    }

    /**
     * Returns an ImageIcon, or null if the path was invalid.
     * @param filePath The file path
     * @return the ImageIcon object created
     */
    private ImageIcon createIcon(String filePath) {
        java.net.URL imgURL = getClass().getResource(filePath);

        if (imgURL == null) {
            System.err.println("Resource not found: " + imgURL);
            return null;
        } else {
            //return new ImageIcon(new ImageIcon(imgURL).getImage().getScaledInstance(24, 24, Image.SCALE_DEFAULT));
            return new ImageIcon(imgURL);
        }
    }

}
