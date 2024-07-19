package com.example.java2lab5;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class Cell extends StackPane {
    private final int x;
    private final int y;
    private boolean is_mine;
    private boolean is_open = false;
    private boolean flagged = false;
    private static final int size = 20;
    public Rectangle border = new Rectangle(size - 2, size - 2);
    public Text text = new Text();
    public Text flag = new Text();

    public Cell(int x, int y, boolean is_mine) {
        this.x = x;
        this.y = y;
        this.is_mine = is_mine;
        border.setStroke(Color.LIGHTGRAY);
        border.setFill(Color.WHITE);
        flag.setText("");
        flag.setFill(Color.RED);
        text.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        flag.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
        text.setVisible(false);
        getChildren().addAll(border, text, flag);
        setTranslateX(x * size);
        setTranslateY(y * size);
        setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                open();
            } else if (e.getButton() == MouseButton.SECONDARY) {
                setFlag();
            }
        });
    }

    public void open() {
        if (is_open) return;
        is_open = true;
        text.setVisible(true);
        flag.setText("");
        border.setFill(Color.LIGHTGRAY);
        if (is_mine) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Вы проиграли :((9(9(", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.exit(0);
                return;
            }
            System.exit(0);
        }
        if (Minesweeper.getCellsOpened() == Minesweeper.getVerticalCells() * Minesweeper.getHorizontalCells() - Minesweeper.getMinesNeeded() - 1) {
            Alert alert = new Alert(Alert.AlertType.NONE, "Поздравляем! Вы выйграли!", ButtonType.OK);
            alert.showAndWait();
            if (alert.getResult() == ButtonType.OK) {
                System.exit(0);
                return;
            }
            System.exit(0);
        } else {
            Minesweeper.incrementCellsOpened();
        }
        if (text.getText().isEmpty()) {
            Minesweeper.getNeighbours(this).forEach(Cell::open);
        }
    }

    public void setFlag() {
        if (is_open) return;
        if (flagged) {
            flagged = false;
            flag.setText("");
        } else {
            flagged = true;
            flag.setText("⚐");
        }
    }

    public static int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isMine() {
        return is_mine;
    }

    public void setIsMine(boolean is_mine) {
        this.is_mine = is_mine;
    }

}
