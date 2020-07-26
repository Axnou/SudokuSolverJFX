import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.FileInputStream;


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
        String fxmlDocPath = "C:/Users/Gaming/IdeaProjects/SudokuSolverJFX/GUIScene.fxml";
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
                if(!controller.getClickable() && controller.getSolvingProcess().getT().isAlive()) {
                    controller.timerBoxUpdate();
                    update();

                }
            }
        }.start();

    }

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
