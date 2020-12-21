package cool;

import javax.swing.JPanel;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class SimObjectChooser extends JPanel {

    private Class selectedClass;
    private JComboBox combolist;
    private String[] classes;

    SimObjectChooser ()
    {
        selectedClass = null;
        classes = null;
        combolist = new JComboBox ();

        combolist.addActionListener
                (new ActionListener () {
                     public void actionPerformed (ActionEvent e) {
                         JComboBox cb = (JComboBox) e.getSource ();
                         String classname = (String) cb.getSelectedItem ();
                         setSelectedClass (classname);
                     }
                 }
                );

        add (combolist);
    }

    public void addClass (String className) throws ClassNotFoundException
    {
        Class newClass = Class.forName (className);

        String [] oldclasses = classes;

        if (oldclasses == null) {
            classes = new String [1];
            selectedClass = newClass;
        } else {
            classes = new String [oldclasses.length + 1];
        }

        if (oldclasses != null) {
            for (int i = 0; i < oldclasses.length; i++) {
                classes[i] = oldclasses[i];
            }
        }

        classes[classes.length - 1] = className;
        combolist.addItem (className);
    }

    public Class getSelectedClass()
    {
        return selectedClass;
    }

    public void setSelectedClass (String classname)
    {
        try {
            Class newClass = Class.forName (classname);
            selectedClass = newClass;
        } catch (java.lang.ClassNotFoundException ex) {
            System.err.println ("BUG: Selected class is not available: " + classname);
            ex.printStackTrace();
        }
    }

}
