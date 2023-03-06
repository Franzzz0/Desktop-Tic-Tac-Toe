package tictactoe;

public class Human extends Player {

    public Human(Gameplay game, String symbol) {
        super(game, symbol);
    }

    @Override
    public void makeTurn() {
        getGame().setButtonsEnabled(true);
        while (true) {
            if (!getGame().getButtonsEnabled()) {
                break;
            }
        }
    }

    @Override
    String getPlayerType() {
        return "Human";
    }
}
