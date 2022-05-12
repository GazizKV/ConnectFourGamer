package four;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ConnectFour extends JFrame {
    public static ArrayList<Cell> cells = new ArrayList<>();
    public static String sign = "X";
    public static Color baseColor = Color.GRAY;
    public static Color winColor = Color.CYAN;
    public static boolean isStop = false;

    public ConnectFour() {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 700);
        setVisible(true);
        setLayout(new GridLayout(2, 1));
        JPanel cellsPanel = new JPanel();
        cellsPanel.setBounds(0, 0, 600, 600);
        cellsPanel.setVisible(true);
        cellsPanel.setLayout(new GridLayout(6, 7));
        for (int j = 6; j >= 1; j--) {
            for (char i = 'A'; i <= 'G'; i++) {
                Cell cell = new Cell("" + i + j);
                cell.setBackground(baseColor);
                cellsPanel.add(cell);
                cells.add(cell);
            }
        }
        add(cellsPanel);
        add(new ButtonReset("Reset"));
        setVisible(true);
    }

    public static boolean checkWin(Cell lastCell) {
        String currentSign = lastCell.getText();
        String name = lastCell.getName();
        char currentColumn = name.charAt(6);
        int currentRow = Integer.parseInt(name.substring(7));
        boolean win1 = topDown(currentColumn, currentRow, currentSign, lastCell);
        boolean win2 = leftRight(currentColumn, currentRow, currentSign, lastCell);
        boolean win3 = leftRightTopDown(currentColumn, currentRow, currentSign, lastCell);
        boolean win4 = leftRightDownTop(currentColumn, currentRow, currentSign, lastCell);
        return win1 || win2 || win3 || win4;
    }

    private static boolean leftRightDownTop(char currentColumn, int currentRow, String currentSign, Cell currentCell) {
        char nextLeftColumn = currentColumn;
        char nextRightColumn = currentColumn;
        int toDownRow = currentRow;
        int toTopRow = currentRow;
        ArrayList<Cell> winLine = new ArrayList<>();
        winLine.add(currentCell);
        boolean leftExistEqual = true;
        boolean rightExistEqual = true;
        while (leftExistEqual || rightExistEqual) {
            --nextLeftColumn;
            --toDownRow;
            ++nextRightColumn;
            ++toTopRow;
            if (nextLeftColumn < 65 || toDownRow < 1) leftExistEqual = false;
            if (nextRightColumn > 71 || toTopRow > 6) rightExistEqual = false;
            if (leftExistEqual) {
                int res = check(nextLeftColumn, toDownRow, currentSign, winLine);
                if (res == 1) {
                    return true;
                } else if (res < 0) {
                    leftExistEqual = false;
                }
            }
            if (rightExistEqual) {
                int res = check(nextRightColumn, toTopRow, currentSign, winLine);
                if (res == 1) {
                    return true;
                } else if (res < 0) {
                    rightExistEqual = false;
                }
            }
        }
        return false;
    }

    private static boolean leftRightTopDown(char currentColumn, int currentRow, String currentSign, Cell currentCell) {
        char nextLeftColumn = currentColumn;
        char nextRightColumn = currentColumn;
        int toTopRow = currentRow;
        int toDownRow = currentRow;
        ArrayList<Cell> winLine = new ArrayList<>();
        winLine.add(currentCell);
        boolean toLeftTopSignIsEqual = true;
        boolean toRightDownSighIsEqual = true;
        while (toLeftTopSignIsEqual || toRightDownSighIsEqual) {
            --nextLeftColumn;
            ++toTopRow;
            ++nextRightColumn;
            --toDownRow;
            if (nextLeftColumn < 65 || toTopRow > 6) toLeftTopSignIsEqual = false;
            if (nextRightColumn > 71 || toDownRow < 1) toRightDownSighIsEqual = false;
            if (toLeftTopSignIsEqual) {
                int res = check(nextLeftColumn, toTopRow, currentSign, winLine);
                if (res == 1) {
                    return true;
                } else if (res < 0) {
                    toLeftTopSignIsEqual = false;
                }
            }
            if (toRightDownSighIsEqual) {
                int res = check(nextRightColumn, toDownRow, currentSign, winLine);
                if (res == 1) {
                    return true;
                } else if (res < 0) {
                    toRightDownSighIsEqual = false;
                }
            }
        }
        return false;
    }

    private static boolean leftRight(char currentColumn, int currentRow, String currentSign, Cell currentCell) {
        char nextRightColumn = currentColumn;
        char nextLeftColumn = currentColumn;
        ArrayList<Cell> winLine = new ArrayList<>();
        winLine.add(currentCell);
        boolean leftSignIsEqual = true;
        boolean rightSighIsEqual = true;
        while (leftSignIsEqual || rightSighIsEqual) {
            if (--nextLeftColumn > 64 && leftSignIsEqual) {
                int res = check(nextLeftColumn, currentRow, currentSign, winLine);
                if (res == 1) {
                    return true;
                } else if (res < 0) leftSignIsEqual = false;
            }
            if (++nextRightColumn < 72 && rightSighIsEqual) {
                int res = check(nextRightColumn, currentRow, currentSign, winLine);
                if (res == 1) {
                    return true;
                } else if (res < 0) rightSighIsEqual = false;
            }
        }
        return false;
    }

    private static boolean topDown(char currentColumn, int currentRow, String currentSign, Cell currentCell) {
        ArrayList<Cell> winLineCells = new ArrayList<>();
        winLineCells.add(currentCell);
        if (currentRow < 4) return false;
        while (--currentRow > 0) {
            int res = check(currentColumn, currentRow, currentSign, winLineCells);
            if (res > 1) {
                return true;
            } else if (res < 0) break;
        }
        return false;
    }

    private static int check(char column, int row, String currentSign, ArrayList<Cell> winLine) {
        Cell checkingCell = null;
        for (Cell next : cells) {
            if (next.getName().equals("Button" + column + row)) {
                checkingCell = next;
                break;
            }
        }
        if (checkingCell != null && checkingCell.getText().equals(currentSign)) {
            winLine.add(checkingCell);
            if (winLine.size() == 4) {
                for (Cell nextWinCell : winLine) {
                    nextWinCell.setBackground(ConnectFour.winColor);
                }
                return 1;
            }
            ;
            return 0;
        }
        return -1;
    }

}