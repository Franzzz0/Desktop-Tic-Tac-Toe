package tictactoe;

public enum MenuEnum {
    HUMAN_HUMAN ("Human", "Human"),
    HUMAN_ROBOT ("Human", "Robot"),
    ROBOT_HUMAN ("Robot", "Human"),
    ROBOT_ROBOT ("Robot", "Robot");

    final String playerX;
    final String playerO;

    MenuEnum(String playerX, String playerO) {
        this.playerX = playerX;
        this.playerO = playerO;
    }
}
