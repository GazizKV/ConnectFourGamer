package four;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonReset extends JButton {

    public ButtonReset(String text) {
        super(text);
        setName("ButtonReset");
        setVisible(true);
        setBounds(0, 0, 30, 30);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() != 1) return;
                ConnectFour.cells.forEach(cell -> {
                    cell.setText(" ");
                    cell.setBackground(ConnectFour.baseColor);
                    ConnectFour.sign = "X";
                });
                ConnectFour.isStop = false;
            }
        });
    }


}
