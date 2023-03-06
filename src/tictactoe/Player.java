package tictactoe;

public abstract class Player {
    private final String symbol;
    private final Gameplay game;

    public Player(Gameplay game, String symbol) {
        this.game = game;
        this.symbol = symbol;
    }

    abstract void makeTurn();

    public String getSymbol() {
        return symbol;
    }

    abstract String getPlayerType();

    public Gameplay getGame() {
        return game;
    }
}
