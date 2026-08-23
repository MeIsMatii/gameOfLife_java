import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
private JFrame window;
private JPanel grid;
private Cell[][] cells;

private boolean isPaused = false;

private Timer timer;

private int tick = 0;

void main() {
    createWindow();
    addCells();
    addListener();
    addTimer();
}

void createWindow() {
    JFrame window = new JFrame("Game of Thrones probably");
    window.setSize(600,600);
    window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    window.setVisible(true);

    this.window = window;
}

void addCells() {
    this.grid = new JPanel();
    int rows = 20;
    int cols = 20;

    cells = new Cell[rows][cols];

    grid.setLayout(new GridLayout(rows,cols));
    for(int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            Cell cell = new Cell();
            cells[i][j] = cell;
            grid.add(cell);
        }
    }

    this.window.add(this.grid);
}

private void addListener() {
    window.addKeyListener(new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {

            if(e.getKeyCode() == KeyEvent.VK_SPACE) {
                System.out.println("Space down");
                isPaused = !isPaused;
                pauseCells(isPaused);

                if(isPaused) {
                    timer.stop();
                } else {
                    timer.start();
                }
            }

        }

        @Override
        public void keyReleased(KeyEvent e) {

        }
    });
}

void addTimer() {
    timer = new Timer(100, e -> {
        tick(cells);
    });
}
void pauseCells(boolean isPaused) {
    for(Cell[] row : cells) {
        for(Cell cell : row) {
            cell.setModifiable(isPaused);
        }
    }
}

void tick(Cell[][] cells) {
    System.out.println("tick");
    boolean[][] nextState = new boolean[cells.length][cells[0].length];

    for(int row = 0; row<cells.length; row++) {
        for(int col= 0; col<cells[row].length;col++) {
            nextState[row][col] = calculate(row,col, cells);
        }
    }

    for(int row = 0; row < cells.length; row++) {
        for(int col= 0; col<cells[row].length;col++) {
            cells[row][col].setAlive(nextState[row][col]);
        }
    }
}

boolean calculate(int row, int col, Cell[][] cells) {
    int aliveNeighbours = 0;

    for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
        for (int colOffset = -1; colOffset <= 1; colOffset++) {
            //current cell
            if (rowOffset == 0 && colOffset == 0) {
                continue;
            }

            int neighbourRow = row + rowOffset;
            int neighbourCol = col + colOffset;

            //neighbour needs to be inside grid
            if (neighbourRow < 0) {
                neighbourRow = cells.length + neighbourRow;
            } else if(neighbourRow >= cells.length) {
                neighbourRow = neighbourRow - cells.length;
            }
            if (neighbourCol < 0) {
                neighbourCol = cells.length + neighbourCol;
            } else if(neighbourCol >= cells.length) {
                neighbourCol = neighbourCol - cells.length;
            }

            if (cells[neighbourRow][neighbourCol].isAlive()) {
                aliveNeighbours++;
            }

        }
    }
    boolean isAlive;
    Cell cell = cells[row][col];
    if (cell.isAlive()) {
        isAlive = aliveNeighbours == 2 || aliveNeighbours == 3;
    } else {
        isAlive = aliveNeighbours == 3;
    }

    return isAlive ;
}
