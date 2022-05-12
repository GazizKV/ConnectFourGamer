package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Cell extends JButton {
    public Cell(String label) throws HeadlessException {
        super(" ");
        setName("Button" + label);
        setFocusPainted(false);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != 1 || ConnectFour.isStop) return;
                JButton currentButton = (JButton) e.getSource();
                String text = currentButton.getName();
                String column = text.substring(6, 7);
                for (int i = 1; i < 7; i++) {
                    String nextText = "Button" + column + i;
                    for (Cell nextCell : ConnectFour.cells) {
                        if (nextCell.getName().equals(nextText) && nextCell.getText().equals(" ")) {
                            setSign(nextCell);
                            if (ConnectFour.checkWin(nextCell)) ConnectFour.isStop = true;
                            return;
                        }
                    }
                }
            }
        });
    }

    void setSign(Cell cell) {
        cell.setText(ConnectFour.sign);
        if (Objects.equals(ConnectFour.sign, "X")) ConnectFour.sign = "O";
        else ConnectFour.sign = "X";
    }


}
