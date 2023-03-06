package tictactoe;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe extends JFrame {
    private Gameplay game;
    private final static List<Button> buttons = new ArrayList<>();
    private final JButton[] playerButtons = new JButton[2];
    private final JButton resetButton = new JButton();
    private final static JLabel statusLabel = new JLabel();
    private boolean isGameStarted;

    public TicTacToe() {
        setTitle("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 400);
        setLayout(new BorderLayout());
        setupGUI();
        setGameStarted(false);
        setButtonsEnabled(false);
        setVisible(true);
    }

    public void setupGUI() {
        setJMenuBar(getMenu());
        add(getTopPanel(), BorderLayout.NORTH);
        add(getBoard(), BorderLayout.CENTER);
        add(getBottomPanel(), BorderLayout.SOUTH);
    }

    public static List<Button> getButtons() {
        return buttons;
    }

    public synchronized static void setButtonsEnabled(boolean enabled) {
        for (Button button : getButtons()) {
            button.setEnabled(enabled);
        }
    }

    public synchronized void setGameStarted(boolean started) {
        for (JButton button : playerButtons) {
            button.setEnabled(!started);
        }
        this.isGameStarted = started;
    }

    private void newGame(String playerX, String playerO) {
        this.game = new Gameplay(playerX, playerO);
        setGameStarted(true);
        game.start();
    }

    private void reset() {
        setGameStarted(false);
        setButtonsEnabled(false);
        this.game.close();
        try {
            this.game.join(2000);
        } catch (InterruptedException e) {
            e.getStackTrace();
        }
        for (Button button : buttons) {
            button.setText(" ");
            button.setBackground(Color.YELLOW);
            button.setForeground(Color.BLACK);
        }
        statusLabel.setText("Game is not started");
    }

    public static void setStatusLabel(String text) {
        statusLabel.setText(text);
    }

    private JMenuBar getMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");
        menuGame.setName("MenuGame");
        List<JMenuItem> menuItems = getMenus();

        JMenuItem menuExit = new JMenuItem("Exit");
        menuExit.setName("MenuExit");
        menuExit.addActionListener(actionEvent -> System.exit(0));

        for (JMenuItem menuItem : menuItems) {
            menuGame.add(menuItem);
        }
        menuGame.addSeparator();
        menuGame.add(menuExit);
        menuBar.add(menuGame);
        return menuBar;
    }

    private List<JMenuItem> getMenus() {
        List<JMenuItem> menuItems = new ArrayList<>();
        for (MenuEnum menu : MenuEnum.values()) {
            JMenuItem menuItem = new JMenuItem(String.format("%s vs %s", menu.playerX, menu.playerO));
            menuItem.setName(String.format("Menu%s%s", menu.playerX, menu.playerO));
            setMenuAction(menuItem, menu);
            menuItems.add(menuItem);
        }
        return menuItems;
    }

    private JPanel getTopPanel() {
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        topPanel.setBorder(new EmptyBorder(10, 10, 0, 10));

        for (int i = 1; i <=2; i++) {
            JButton playerButton = new JButton("Human");
            playerButton.setName(String.format("ButtonPlayer%d", i));
            setPlayerButtonAction(playerButton);
            playerButtons[i - 1] = playerButton;
        }

        resetButton.setText("Start");
        resetButton.setName("ButtonStartReset");
        resetButton.addActionListener(actionEvent -> {
            if (!isGameStarted) {
                newGame(playerButtons[0].getText(), playerButtons[1].getText());
                resetButton.setText("Reset");
            } else {
                reset();
                resetButton.setText("Start");
            }
        });
        topPanel.add(playerButtons[0]);
        topPanel.add(resetButton);
        topPanel.add(playerButtons[1]);

        return topPanel;
    }

    private JPanel getBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        statusLabel.setName("LabelStatus");
        statusLabel.setText("Game is not started");
        bottomPanel.add(statusLabel);

        return bottomPanel;
    }

    private JPanel getBoard() {
        JPanel board = new JPanel(new GridLayout(3, 3, 10, 10));
        board.setBorder(new EmptyBorder(10, 10, 0, 10));

        for (int i = 3; i > 0; i--) {
            for (char c = 'A'; c <= 'C'; c++) {
                Button button = new Button(c, i);
                setDefaultButtonActionListener(button);
                buttons.add(button);
                board.add(button);
            }
        }
        return board;
    }

    private void setPlayerButtonAction(JButton playerButton) {
        playerButton.addActionListener(actionEvent ->
                playerButton.setText(playerButton.getText().equals("Human") ? "Robot" : "Human"));
    }

    private void setDefaultButtonActionListener(Button button) {
        button.addActionListener(actionEvent -> {
            if (isGameStarted && game.getButtonsEnabled()) {
                game.makeMove(button);
            }
        });
    }

    private void setMenuAction(JMenuItem menuItem, MenuEnum menuEnum) {
        menuItem.addActionListener(actionEvent -> {
            if (game != null) reset();
            this.playerButtons[0].setText(menuEnum.playerX);
            this.playerButtons[1].setText(menuEnum.playerO);
            newGame(menuEnum.playerX, menuEnum.playerO);
            this.resetButton.setText("Reset");
        });
    }
}