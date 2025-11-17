package org.example.actions;

import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.example.model.Student;
import org.example.model.StudentManager;

public class StudentSearchAction extends AbstractAction {
    
    private Document searchInput;
    private DefaultListModel searchResult;
    private StudentManager studentService;

    /*public StudentSearchAction(String text, ImageIcon icon, String desc, Integer mnemonic) {
        super(text, icon);
        putValue(SHORT_DESCRIPTION, desc);
        putValue(MNEMONIC_KEY, mnemonic);
    }*/
    
    public StudentSearchAction(Document searchInput, DefaultListModel searchResult, StudentManager studentService){
        super("Search");
        this.searchInput = searchInput;
        this.searchResult = searchResult;
        this.studentService = studentService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            List<Student> matchedStudents = studentService.searchStudents(searchInput.getText(0, searchInput.getLength()));
            
            searchResult.clear();
            for (Student student : matchedStudents) {
                Object elementModel = student;
                searchResult.addElement(elementModel);
            }
        } catch (BadLocationException ex) {
            System.err.println("BadLocationException: " + ex.getMessage());
        }
    }

}
