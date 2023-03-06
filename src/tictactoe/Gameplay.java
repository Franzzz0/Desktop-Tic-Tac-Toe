package tictactoe;

import java.awt.*;

public class Gameplay extends Thread {

    private final Player playerX;
    private final Player playerO;
    private volatile Player currentPlayer;
    private volatile boolean buttonsEnabled;
    private volatile boolean isStopped;

    public Gameplay(String x, String o) {
        this.playerX = x.equals("Human") ? new Human(this, "X") : new AI(this, "X");
        this.playerO = o.equals("Human") ? new Human(this, "O") : new AI(this, "O");
        this.currentPlayer = playerX;
        this.buttonsEnabled = false;
        this.isStopped = false;
    }

    @Override
    public void run() {
        do {
            TicTacToe.setStatusLabel(
                    String.format("The turn of %s Player (%s)",
                    currentPlayer.getPlayerType(),
                    currentPlayer.getSymbol())
            );
            currentPlayer.makeTurn();
            endTurn();
            if (isStopped) {
                return;
            }
        } while (!GameLogic.isGameOver());
        finishGame();
    }

    public void close() {
        this.isStopped = true;
        setButtonsEnabled(false);
    }

    public void makeMove(Button button) {
        if (GameLogic.move(button, currentPlayer.getSymbol())) {
            setButtonsEnabled(false);
        }
    }

    private Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public synchronized void endTurn() {
        if (GameLogic.won(getCurrentPlayer().getSymbol())) {
            TicTacToe.setStatusLabel(
                    String.format("The %s Player (%s) wins",
                            getCurrentPlayer().getPlayerType(),
                            getCurrentPlayer().getSymbol())
            );
        } else if (GameLogic.allCellsOccupied()) {
            TicTacToe.setStatusLabel("Draw");
        } else {
            currentPlayer = getCurrentPlayer().equals(playerX) ? playerO : playerX;
        }
    }

    public boolean getButtonsEnabled() {
        return buttonsEnabled;
    }

    public void setButtonsEnabled(boolean enabled) {
        this.buttonsEnabled = enabled;
        TicTacToe.setButtonsEnabled(enabled);
    }

    private void finishGame() {
        for (Button button : TicTacToe.getButtons()) {
            button.setBackground(Color.GRAY);
            button.setForeground(Color.GRAY.darker());
        }
    }
}
