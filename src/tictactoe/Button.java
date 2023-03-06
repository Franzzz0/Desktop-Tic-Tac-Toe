package tictactoe;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {

    public Button(char letter, int number) {
        String name = String.format("%s%d", letter, number);
        setText(" ");
        setName("Button" + name);
        setBackground(Color.YELLOW);
        setFont(new Font(Font.DIALOG, Font.BOLD, 30));
        setFocusPainted(false);
    }
}
