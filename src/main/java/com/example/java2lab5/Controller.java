package com.example.java2lab5;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Controller extends Application {
    @FXML
    public Button button_start, button_exit;
    @FXML
    public TextField field_height, field_width, field_mines;
    @FXML
    public AnchorPane game_window;
    @FXML
    public GridPane game_grid;
    private static int vertical_cells;
    private static int horizontal_cells;
    private static int mines_needed;
    public static Scene game;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("minesweeper.fxml")));
        game = new Scene(root);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(game);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void launch() {
        if (field_height.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Введите высоту поля", ButtonType.OK);
            alert.showAndWait();
        } else {
            vertical_cells = Integer.parseInt(field_height.getText());
        }
        if (field_width.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Введите ширину поля", ButtonType.OK);
            alert.showAndWait();
        } else {
            horizontal_cells = Integer.parseInt(field_width.getText());
        }
        if (field_mines.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Введите количество мин", ButtonType.OK);
            alert.showAndWait();
        } else {
            mines_needed = Integer.parseInt(field_mines.getText());
        }
        if (vertical_cells > 1 && vertical_cells < 31 && horizontal_cells > 1 && horizontal_cells < 41 && mines_needed > 0 && mines_needed <= (horizontal_cells * vertical_cells - 1)) {
            game.setRoot(Minesweeper.createContent());
        } else {
            Alert alert = new Alert(Alert.AlertType.NONE, "Высота должна больше 1 и меньше 31;\nШирина должна больше 1 и меньше 31;\nКоличество мин должно быть больше 1 и \nне больше количества ячеек.", ButtonType.OK);
            alert.showAndWait();
            alert.getResult();
        }
    }

    public void exit() {
        System.exit(0);
    }

    public static int getHorizontal_cells() {
        return horizontal_cells;
    }

    public static int getMines_needed() {
        return mines_needed;
    }

    public static int getVertical_cells() {
        return vertical_cells;
    }
}
