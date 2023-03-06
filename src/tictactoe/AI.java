package tictactoe;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AI extends Player {
    private final SimulatedGameplay gameplay;
    private final AI_level level;

    public AI(Gameplay game, String symbol) {
        super(game, symbol);
        gameplay = new SimulatedGameplay(symbol);
        level = getRandomLevel();
    }

    @Override
    public void makeTurn() {
        gameplay.updateGameBoard();
        try {
            TimeUnit.MILLISECONDS.sleep(300);
            while (true) {
                Button move = getMove();
                if (GameLogic.move(move, getSymbol())) {
                    return;
                }
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    String getPlayerType() {
        return "Robot";
    }

    private AI_level getRandomLevel() {
        Random random = new Random();
        int difficultyLevel = random.nextInt(3);
        return switch (difficultyLevel) {
            case 1 -> AI_level.MEDIUM;
            case 2 -> AI_level.HARD;
            default -> AI_level.EASY;
        };
    }

    private Button getMove() {

        int[] coordinates = getCoordinates(level);
        int index = switch (coordinates[0]) {
            case 0 -> coordinates[1];
            case 1 -> coordinates[1] + 3;
            case 2 -> coordinates[1] + 6;
            default -> throw new IllegalStateException("Unexpected value: " + coordinates[0]);
        };
        return TicTacToe.getButtons().get(index);
    }

    private int[] getCoordinates(AI_level level) {
        return switch (level) {
            case EASY -> getRandom();
            case MEDIUM -> medium();
            case HARD ->getBestMove();
        };
    }

    private int[] medium() {
        if (canWin(gameplay.getPlayer())) {
            return getWinningCoordinates(gameplay.getPlayer());
        } else if (canWin(gameplay.getOpponent())) {
            return getWinningCoordinates(gameplay.getOpponent());
        } else {
            return getRandom();
        }
    }

    private boolean canWin(char player) {
        return getWinningCoordinates(player)[0] != -1;
    }

    private int[] getWinningCoordinates(char player) {
        for (int[] move : getAvailableMoves()) {
            this.gameplay.placeCell(move, player);
            if (gameplay.checkIfWon(player)) {
                this.gameplay.placeCell(move, ' ');
                return move;
            }
            this.gameplay.placeCell(move, ' ');
        }
        return new int[]{-1, -1};
    }

    private int[] getBestMove() {
        int bestValue = -1000;
        int[] bestMove = new int[2];
        for (int[] move : getAvailableMoves()) {
            gameplay.placeCell(move, gameplay.getPlayer());
            int moveValue = minimax(false);
            gameplay.placeCell(move, ' ');

            if (moveValue > bestValue) {
                bestValue = moveValue;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMax) {
        if (gameplay.checkIfWon(gameplay.getPlayer())) {
            return 10;
        } else if (gameplay.checkIfWon(gameplay.getOpponent())) {
            return -10;
        } else if (getAvailableMoves().size() == 0) {
            return 0;
        }
        int best;
        if (isMax) {
            best = -1000;
            for (int[] move : getAvailableMoves()) {
                gameplay.placeCell(move, gameplay.getPlayer());
                best = Math.max(best, minimax(false));
                gameplay.placeCell(move, ' ');
            }
        } else {
            best = 1000;
            for (int[] move : getAvailableMoves()) {
                gameplay.placeCell(move, gameplay.getOpponent());
                best = Math.min(best, minimax(true));
                gameplay.placeCell(move, ' ');
            }
        }
        return best;
    }

    private List<int[]> getAvailableMoves() {
        List<int[]> moves = new ArrayList<>();
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                if (gameplay.isCellOccupied(x, y)) {
                    continue;
                }
                moves.add(new int[]{x, y});
            }
        }
        return moves;
    }

    public int[] getRandom() {
        Random rnd = new Random();
        while (true) {
            int x = rnd.nextInt(3);
            int y = rnd.nextInt(3);
            if (!this.gameplay.isCellOccupied(x, y)) {
                return new int[]{x, y};
            }
        }
    }
}
