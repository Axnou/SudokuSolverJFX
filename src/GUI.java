import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;


import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GUI extends Application {

    private List<StackPane> stackPaneList = new ArrayList<>();
    private List<Label> labelList = new ArrayList<>();
    private boolean solver = false;
    private boolean startSolving = false;

    public GUI() {

    }


    @Override
    public void start(Stage stage) throws Exception {

        //loaderin luonti ja FXML-tiedoston hakeminen
        FXMLLoader loader = new FXMLLoader();

        //XML tiedoston osoite

        String localDir = System.getProperty("user.dir");
        File file = new File(localDir + "//GUIScene.fxml");
        String fxmlDocPath = file.getPath();
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);

        //GUI luodaan lukemalla FXML-tiedosto
        AnchorPane root = (AnchorPane) loader.load(fxmlStream);
        Controller controller = loader.getController();
        controller.createPaneAndSetLabel(labelList, stackPaneList);

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Sudoku Solver");
        stage.setResizable(false);
        stage.fullScreenProperty();
        stage.show();

        new AnimationTimer() {

            @Override
            public void handle(long l) {
                //if-lauseke varmistaa, että kellon päivitys ja GUI:n päivittäminen alkavat vasta kun nappia painetaan
                //ja loppuvat kun sudokun ratkaisija on valmis
                if(!controller.getClickable() && controller.getSolvingProcess().getT().isAlive()) {
                    controller.timerBoxUpdate();
                    update();

                }
            }
        }.start();

    }

    //Tarkistaa onko sudokutaulukossa ja GUI:ssa sama numero samassa kohtaa
    //Jos ei, asettaa GUI:hin saman numeron.
    //mikäli sudokun numero > 0 asettaa GUI:n ruudun reunojen väriksi vihreän.
    //Jos sudokun numero = -1, asettaa GUI:n ruudun reunojen väriksi punaisen
    //(tarkoittaa ruutuun asti ollaan päästy mutta sille ei löydetty sopivaa numeroa)
    //jos sudokun numero = 0, tietää, että ruudussa ei olla käyty ja jättää sen ennalleen.
    public void update() {

        for (int i = 0; i < Sudoku.sudoku.length; i++) {
            for (int j = 0; j < Sudoku.sudoku[i].length; j++) {
                int labIndex = i * 9 + j;
                labelList.get(labIndex).setText("0");
                if (Integer.valueOf(labelList.get(labIndex).getText()) != Sudoku.sudoku[i][j] && Sudoku.sudoku[i][j] != 0 && Sudoku.sudoku[i][j] != -1) {
                    labelList.get(labIndex).setText(Integer.toString(Sudoku.sudoku[i][j]));
                    stackPaneList.get(labIndex).setStyle("-fx-border-color: lime; -fx-border-width: 3;");
                } else if (Sudoku.sudoku[i][j] == -1){
                    labelList.get(labIndex).setText("");
                    stackPaneList.get(labIndex).setStyle("-fx-border-color: red; -fx-border-width: 3;");
                } else {
                    labelList.get(labIndex).setText("");
                }
            }
        }
    }

    public static void main(String[] args) {

        Application.launch(GUI.class);

    }
}
