package cool;

import java.util.Vector;
import java.util.Enumeration;

public class Grid {
    private int rows;
    private int columns;
    private Vector simobjects;

    Grid (int p_rows, int p_columns)
    {
        rows = p_rows;
        columns = p_columns;
        simobjects = new Vector();
    }

    public int numRows()
    {
        return rows;
    }

    public int numColumns()
    {
        return columns;
    }

    synchronized public void startObjects()
    {
        Enumeration els = simobjects.elements ();

        while (els.hasMoreElements ()) {
            SimObject current = (SimObject) els.nextElement ();

            if (current.isPaused ()) {
                current.resumeObject ();
            } else {
                Thread simObjectThread = new Thread (current);
                simObjectThread.start();
            }
        }
    }

    synchronized public void pauseObjects ()
    {
        Enumeration els = simobjects.elements ();

        while (els.hasMoreElements ()) {
            SimObject current = (SimObject) els.nextElement ();
            current.pauseObject();
        }
    }

    synchronized public void setObjectAt (int row, int col, Class objectClass)
    {
        if (objectClass != null) {
            try {
                SimObject newObject = (SimObject) objectClass.newInstance();
                newObject.init (row, col, this);
                simobjects.remove(getObjectAt(row, col));
                simobjects.add(newObject);
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (java.lang.IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    synchronized public void setObjectAt (int row, int col, SimObject newObject)
    {
        newObject.init (row, col, this);
        simobjects.remove(getObjectAt(row, col));
        simobjects.add(newObject);
    }

    synchronized public boolean isSquareEmpty (int row, int col) throws RuntimeException
    {
        return (getObjectAt (row, col) == null);
    }

    public boolean validLocation (int row, int col)
    {
        if((row < 0) || (row > numRows() - 1)) {
            return false;
        }

        if((col < 0) || (col > numColumns() - 1)) {
            return false;
        }

        return true;
    }


    public SimObject grabObjectAt (int row, int col) throws RuntimeException
    {
        if ((row < 0) || (row > numRows () - 1)) {
            throw new RuntimeException ("Bad row parameter to getObjectAt: " + row);
        }

        if ((col < 0) || (col > numColumns () - 1)) {
            throw new RuntimeException ("Bad column parameter to getObjectAt: " + col);
        }

        Enumeration els = simobjects.elements ();

        while (els.hasMoreElements ()) {
            SimObject current = (SimObject) els.nextElement ();

            if ((current.getRow() == row) && (current.getColumn() == col))
            {
                return current;
            }
        }

        return null;
    }

    synchronized public SimObject getObjectAt (int row, int col) throws RuntimeException
    {
        return grabObjectAt (row, col);
    }

    synchronized public void clearGrid() {
        for (int i = 0; i < numRows(); i++) {
            for (int j = 0; j < numColumns(); j++) {
                simobjects.remove(getObjectAt(i, j));
            }
        }
    }
}
