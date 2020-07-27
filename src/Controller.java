import javafx.fxml.FXML;

import javafx.geometry.Pos;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class Controller {


    @FXML
    private GridPane grid0;
    @FXML
    private GridPane grid01;
    @FXML
    private GridPane grid02;
    @FXML
    private Label timer;
    @FXML
    private GridPane[] grids;

    private Boolean clickable;
    private long start;
    private boolean finished;
    private SolverRunnable solvingProcess;

    public Controller() {

    }


    //Lisää GUI:hin kellon ja asettaa napin niin, että sitä voi klikata
    @FXML
    private void initialize() {
        grids = new GridPane[]{grid0, grid01, grid02};
        clickable = true;
        finished = false;
        timerBox();
    }


    //asettaa sudokutaulun alkutilaan
    public void createPaneAndSetLabel(List labels, List stackPanes) {

        int row = 0;

        for (int i = 0; i < grids.length; i++) {
            for (int j = 0; j < grids[i].getRowCount(); j++) {
                for (int c = 0; c < grids[i].getColumnCount(); c++) {

                    Label labs = new Label("");

                    int value = Sudoku.sudoku[row][c];
                    if (value != 0) {
                        String number = Integer.toString(value);
                        labs.setText(number);
                    }

                    StackPane stackPane = new StackPane();

                    stackPane.getChildren().add(labs);
                    grids[i].add(stackPane, c, j);

                    StackPane.setAlignment(labs, Pos.CENTER);
                    labs.setFont(Font.font("System", 32));

                    labels.add(labs);
                    stackPanes.add(stackPane);
                }
                row++;
            }
        }
    }

    //metodi joka alkaa kun nappia painetaan
    public void pressAndSolve() {
        if (clickable) {
            //varmistaa että nappia voi klikata vain kerran
            clickable = false;

            //luo uuden threadin. Tarvitaan jotta GUI:n päivitys ja Sudokun ratkaisija voivat toimia saman aikaisesti,
            //koska ratkaisija on loop, joka ei muuten tarjoaisi mahdollisuutta GUI:n päivitykseen
            solvingProcess = new SolverRunnable("Solver");
            start = System.currentTimeMillis();
            solvingProcess.start();
        }
    }

    //kello
    public void timerBox() {
        timer.setText("Aika: 00:00:00");
        start = System.currentTimeMillis();
    }

    //kello päivitys
    public void timerBoxUpdate() {
        long now = System.currentTimeMillis();

        long time = now - start;

        int timeInSec = (int) time / 1000;

        int minutes = 0;
        int hours = 0;

        if (timeInSec > 9) {
            minutes += timeInSec / 60;
            timeInSec = timeInSec - minutes * 60;
            if (timeInSec < 10) {
                timer.setText("Aika: 0" + hours + ":0" + minutes + ":0" + timeInSec);
            } else {
                timer.setText("Aika: 0" + hours + ":0" + minutes + ":" + timeInSec);
            }
            if (minutes > 9) {
                hours += minutes / 60;
                minutes = minutes - hours * 60;
                if (minutes > 9) {
                    if (timeInSec < 10) {
                        timer.setText("Aika: 0" + hours + ":" + minutes + ":0" + timeInSec);
                    } else {
                        timer.setText("Aika: 0" + hours + ":" + minutes + ":" + timeInSec);
                    }
                } else {
                    if (timeInSec < 10) {
                        timer.setText("Aika: 0" + hours + ":0" + minutes + ":0" + timeInSec);
                    } else {
                        timer.setText("Aika: 0" + hours + ":0" + minutes + ":" + timeInSec);
                    }
                }
            }
        } else {
            timer.setText("Aika: 0" + hours + ":0" + minutes + ":0" + timeInSec);
        }
    }

    public Boolean getClickable() {
        return clickable;
    }

    public SolverRunnable getSolvingProcess() {
        return solvingProcess;
    }
}

