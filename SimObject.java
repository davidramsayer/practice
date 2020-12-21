package cool;

import java.util.Random;
import java.util.Enumeration;
import java.util.Vector;
import java.awt.Color;

abstract public class SimObject implements Runnable {

    protected Grid grid;
    protected int mrow;
    protected int mcol;

    private boolean isPaused;

    private Random random;

    static final int TURN_DELAY = 500;
    static final int TURN_RANDOM = 200;

    public SimObject ()
    {
        random = new Random();
    }

    final public void init (int row, int col, /*@non_null@*/ Grid grid)
    {
        this.mrow = row;
        this.mcol = col;
        this.grid = grid;
        this.isPaused = false;
    }

    public void run ()
    {
        isPaused = false;

        while (true) {
            if (!isPaused) {
                executeTurn ();
                delay (TURN_DELAY + random.nextInt(TURN_RANDOM));
            }
        }
    }

    public void resumeObject()
    {
        isPaused = false;
    }

    public void pauseObject()
    {
        isPaused = true;
    }

    public boolean isPaused()
    {
        return isPaused;
    }

    abstract public void executeTurn();

    public /*@non_null@*/ Color getColor ()
    {
        return Color.green;
    }

    final public int getRow ()
    {
        return mrow;
    }

    final public void delay (int ms)
    {
        try {
            Thread.sleep (ms);
        } catch (InterruptedException ie) {
            ;
        }
    }

    final public int getColumn ()
    {
        return mcol;
    }

    synchronized public Enumeration getNeighbors () {

        Vector objects = new Vector ();

        for (int row = mrow - 1; row <= mrow + 1; row++) {
            for (int col = mcol - 1; col <= mcol + 1; col++) {
                if (row != mrow || col != mcol) { // don't count yourself
                    if (grid.validLocation (row, col)) {
                        SimObject obj = grid.getObjectAt (row, col);

                        if (obj != null) {
                            objects.addElement (obj);
                        }
                    }
                }
            }
        }

        return objects.elements ();
    }

    final public Grid getGrid ()
    {
        return grid;
    }

}

