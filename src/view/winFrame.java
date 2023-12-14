package view;

import controller.GameController;
import model.Chessboard;

import javax.swing.*;

public class winFrame extends JFrame {
    private GameController gameController;
    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }

    public winFrame(int width, int height) {
        setTitle("You Win!");
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addAgain();
        addExit();
        addBackground();
        addNextLevel();
    }

    private void addBackground() {
        ImageIcon bg = new ImageIcon("src/picture/winBackground.png");
        System.out.println(bg.toString());
        JLabel bagr = new JLabel(bg);
        bagr.setBounds(0, 0, 750, 1000);
        bagr.setLocation(0, 0);
        add(bagr);
    }

    private void addNextLevel() {
        ImageIcon ag = new ImageIcon("src/picture/winNextLevel.png");
        System.out.println(ag.toString());
        JButton nextLevel = new JButton(ag);
        nextLevel.setBounds(0, 0, 200, 79);
        nextLevel.setLocation(270, 420);
        nextLevel.addActionListener(e -> {
            this.dispose();
            gameController.MSteps += 5;
            gameController.Gscores += 180;
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810, gameController.MSteps, gameController.Gscores);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
            mainFrame.setVisible(true);
        });
        add(nextLevel);
    }

    private void addAgain() {
        ImageIcon ag = new ImageIcon("src/picture/winAgain.png");
        JButton Again = new JButton();
        Again.setIcon(ag);
        Again.setBounds(0, 0, 200, 79);
        Again.setLocation(270, 510);
        Again.addActionListener(e -> {
            this.dispose();
            ChessLogin loginFrame = new ChessLogin();
            loginFrame.setVisible(true);
        });
        add(Again);
    }

    private void addExit() {
        ImageIcon ag = new ImageIcon("src/picture/winExit.png");
        JButton Exit = new JButton();
        Exit.setIcon(ag);
        Exit.setBounds(0, 0, 200, 79);
        Exit.setLocation(270, 600);
        Exit.addActionListener(e -> {
            this.dispose();
        });
        add(Exit);
    }
}
