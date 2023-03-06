package tictactoe;

public class SimulatedGameplay {

    private char[][] grid;
    private final char player;

    public SimulatedGameplay(String playerSymbol) {
        updateGameBoard();
        this.player = playerSymbol.charAt(0);
    }

    public char getPlayer() {
        return this.player;
    }

    public char getOpponent() {
        return getPlayer() == 'X' ? 'O' : 'X';
    }

    public void placeCell(int[] coordinates, char player) {
        this.grid[coordinates[0]][coordinates[1]] = player;
    }

    public boolean isCellOccupied(int x, int y) {
        return this.grid[x][y] != ' ';
    }

    public boolean checkIfWon(char player) {
        for (char[] chars : this.grid) {
            if (chars[0] == player && chars[1] == player && chars[2] == player) {
                return true;
            }
        }
        for (int i = 0; i < this.grid.length; i++) {
            if (grid[0][i] == player && grid[1][i] == player && grid[2][i] == player){
                return true;
            }
        }
        return grid[0][0] == player && grid[1][1] == player && grid[2][2] == player ||
                grid[0][2] == player && grid[1][1] == player && grid[2][0] == player;
    }

    public void updateGameBoard() {
        char[][] grid = new char[3][3];
        int counter = 0;
        while (counter < 9) {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    grid[i][j] = TicTacToe.getButtons().get(counter).getText().charAt(0);
                    counter++;
                }
            }
        }
        this.grid = grid;
    }
}