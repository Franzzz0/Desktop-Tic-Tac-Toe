package tictactoe;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GameLogic {

    public static boolean move(Button button, String player) {
        if (button.getText().equals(" ")) {
            button.setText(player);
            return true;
        }
        return false;
    }

    public static boolean isGameOver() {
        return allCellsOccupied() || won("X") || won("O");
    }

    public static boolean allCellsOccupied() {
        for (Button button : TicTacToe.getButtons()) {
            if (button.getText().equals(" ")) {
                return false;
            }
        }
        return true;
    }

    public static boolean won(String player) {
        return checkRows(player) || checkColumns(player) || checkDiagonals(player);
    }

    private static boolean checkRows(String player) {
        int[] rows = {0, 3, 6};
        return Arrays.stream(rows).anyMatch(row ->
                IntStream.rangeClosed(row, row + 2)
                        .allMatch(index -> TicTacToe.getButtons().get(index).getText().equals(player))
        );
    }

    private static boolean checkColumns(String player) {
        int[] cols = {0, 1, 2};
        return Arrays.stream(cols).anyMatch(col ->
                IntStream.iterate(col, i -> i <= col + 6, i -> i + 3)
                        .allMatch(i -> TicTacToe.getButtons().get(i).getText().equals(player))
        );
    }

    private static boolean checkDiagonals(String player) {
        if (IntStream.iterate(0, i -> i <= 8, i -> i + 4)
                .allMatch(i -> TicTacToe.getButtons().get(i).getText().equals(player))) return true;
        return IntStream.iterate(2, i -> i <= 6, i -> i + 2)
                .allMatch(i -> TicTacToe.getButtons().get(i).getText().equals(player));
    }
}
