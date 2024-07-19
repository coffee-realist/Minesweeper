package com.example.java2lab5;

import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minesweeper {

    private static final int vertical_cells = Controller.getVertical_cells();
    private static final int horizontal_cells = Controller.getHorizontal_cells();
    private static final int mines_needed = Controller.getMines_needed();
    private static final int width = Cell.getSize() * (horizontal_cells + 1);
    private static final int height = Cell.getSize() * (vertical_cells + 3);
    private static int cells_opened = 0;
    private static final Cell[][] grid = new Cell[horizontal_cells][vertical_cells];

    public static Parent createContent() {
        Controller.game.getWindow().setWidth(width);
        Controller.game.getWindow().setHeight(height);
        Pane root = new Pane();
        root.setPrefSize(width, height);
        int minesGenerated = 0;
        for (int y = 0; y < vertical_cells; y++) {
            for (int x = 0; x < horizontal_cells; x++) {
                Cell cell = new Cell(x, y, false);
                grid[x][y] = cell;
                root.getChildren().add(cell);
            }
        }

        while (minesGenerated < mines_needed) {
            Random random = new Random();
            int mine_x = random.nextInt(horizontal_cells);
            int mine_y = random.nextInt(vertical_cells);
            Cell cell = grid[mine_x][mine_y];
            if (!cell.isMine()) {
                cell.setIsMine(true);
                minesGenerated++;
            }
        }

        for (int y = 0; y < vertical_cells; y++) {
            for (int x = 0; x < horizontal_cells; x++) {
                Cell cell = grid[x][y];
                if (cell.isMine()) {
                    cell.text.setText("\uD83D\uDCA3");
                    continue;
                }
                long mines = getNeighbours(cell).stream().filter(Cell::isMine).count();
                if (mines > 0) cell.text.setText(String.valueOf(mines));
                switch ((int) mines) {
                    case 1 -> cell.text.setFill(Color.BLUE);
                    case 2 -> cell.text.setFill(Color.GREEN);
                    case 3 -> cell.text.setFill(Color.RED);
                    case 4 -> cell.text.setFill(Color.PURPLE);
                    case 5 -> cell.text.setFill(Color.MAROON);
                    case 6 -> cell.text.setFill(Color.TURQUOISE);
                    case 7 -> cell.text.setFill(Color.BLACK);
                    case 8 -> cell.text.setFill(Color.GRAY);
                }
            }
        }
        return root;
    }

    public static List<Cell> getNeighbours(Cell cell) {
        List<Cell> neighbours = new ArrayList<>();
        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };
        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];
            int newX = cell.getX() + dx;
            int newY = cell.getY() + dy;
            if (isValid(newX, newY)) {
                neighbours.add(grid[newX][newY]);
            }
        }
        return neighbours;
    }

    public static boolean isValid(int x, int y) {
        return (x >= 0 && x < horizontal_cells && y >= 0 && y < vertical_cells);
    }

    public static int getCellsOpened() {
        return cells_opened;
    }

    public static int getHorizontalCells() {
        return horizontal_cells;
    }

    public static int getMinesNeeded() {
        return mines_needed;
    }

    public static int getVerticalCells() {
        return vertical_cells;
    }

    public static void incrementCellsOpened() {
        cells_opened++;
    }
}
