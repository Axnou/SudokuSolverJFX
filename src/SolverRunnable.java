public class SolverRunnable implements Runnable {

    private Thread t;
    private String name;

    public SolverRunnable(String name) {
        this.name = name;
    }

    public void start () {
        if (t == null) {
            t = new Thread (this, name);
            t.start ();
        }
    }

    public Thread getT() {
        return t;
    }

    @Override
    public void run() {
        Sudoku.solverGUI();
    }
}
