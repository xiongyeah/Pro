import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.ChessLogin;
import view.LoseFrame;
import view.winFrame;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            //LoseFrame lose = new LoseFrame(769, 800);
            //lose.setVisible(true);
            //winFrame win=new winFrame(750,730);
            //win.setVisible(true);
            ChessLogin loginFrame=new ChessLogin();
            loginFrame.setVisible(true);
        });
    }

}
