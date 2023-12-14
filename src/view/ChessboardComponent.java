package view;


import controller.GameController;
import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    public int step = 0;
    public int scores = 0;

    private int goalscores;
    private int MaxSteps;
    public JButton shuffle;
    public int ShuffleTime = 3;
    public JLabel lableGoalScores;
    public JLabel lableMaxSteps;
    public JLabel labelStep;
    public JLabel labelScores;
    public boolean whetherSwap = true;

    public void addStep(int n) {
        step = step + n;
    }

    public void setScores(int n) {
        scores = n;
    }

    public void setStep(int n) {
        step = n;
    }

    public int getStep() {
        return step;
    }

    public void addScores(int n) {
        scores = scores + n;
    }

    public int getScores() {
        return scores;
    }

    public void setMaxSteps(int maxSteps) {
        MaxSteps = maxSteps;
    }

    public void setGoalscores(int goalscores) {
        this.goalscores = goalscores;
    }

    public int getGoalscores() {
        return goalscores;
    }

    public int getMaxSteps() {
        return MaxSteps;
    }

    public int getShuffleTime() {
        return ShuffleTime;
    }

    public void setShuffleTime(int shuffleTime) {
        ShuffleTime = shuffleTime;
    }

    public void deShuffle() {
        ShuffleTime--;
    }

    public int getCHESS_SIZE() {
        return CHESS_SIZE;
    }

    private GameController gameController;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 8;
        int height = CHESS_SIZE * 8;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);

        initiateGridComponents();
        registerController(gameController);
    }

    public void initiateGridComponents() {
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.WHITE, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard
                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    if (gridComponents[i][j].getComponentCount() != 0) {
                        gridComponents[i][j].remove(0);
                    }
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece));
                }
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        // Note re-validation is required after remove / removeAll.
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;

    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y / CHESS_SIZE + ", " + point.x / CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y / CHESS_SIZE, point.x / CHESS_SIZE);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void swapChess() {
        if (whetherSwap == true) {
            gameController.onPlayerSwapChess();
        } else {
            JFrame reminder = new JFrame();
            JOptionPane.showMessageDialog(reminder, "Please click the nextStep button to continue!");
        }
        if (gameController.swapOrNot == true)
            whetherSwap = false;
    }

    public void nextStep() {
        gameController.onPlayerNextStep();
        this.whetherSwap = true;
    }

    public void newChessboard() {
        gameController.newChessboard();
    }

    public void Shuffle() {
        gameController.Shuffle();
    }

    public void saveGameFromFile(String path) {
        gameController.saveGameFromFile(path);
    }

    public void loadGameFromFile() {
        gameController.loadGameFromFile();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }

    public void check() {
        if (getStep() >= MaxSteps && getScores() < goalscores) {
            LoseFrame lose = new LoseFrame(769, 800);
            lose.setVisible(true);
        }
        if (getStep() < MaxSteps && getScores() >= goalscores) {
            winFrame win = new winFrame(750, 780);
            win.setVisible(true);
            win.setGameController(gameController);
        }
    }//在写结果
}
