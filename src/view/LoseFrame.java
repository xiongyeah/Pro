package view;

import controller.GameController;
import model.Chessboard;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

public class LoseFrame extends JFrame {
    public LoseFrame(int width, int height) {
        setTitle("You lose!");
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addRestartButton();
        addExit();
        addBackground();
    }

    private void addExit() {
        JButton Ex=new JButton();
        ImageIcon RE=new ImageIcon(".\\src\\picture\\loseExit.png");
        Ex.setIcon(RE);
        setFont(new Font("Rockwell", Font.BOLD, 100));
        Ex.setBounds(162, 643, 473, 125);
        Ex.addActionListener(e -> {this.dispose();});
        add(Ex);
    }

    private void addRestartButton(){
        JButton Restart=new JButton();
        ImageIcon RE=new ImageIcon(".\\src\\picture\\loseRestart.png");
        Restart.setIcon(RE);
        setFont(new Font("Rockwell", Font.BOLD, 40));
        Restart.setBounds(162, 478, 470, 120);
        Restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ChessLogin loginFrame=new ChessLogin();
                loginFrame.setVisible(true);

            }
        });
        add(Restart);
    }

    private void addBackground(){
        ImageIcon bg=new ImageIcon(".\\src\\picture\\loseBackground.png");
        JLabel bagr=new JLabel(bg);
        bagr.setBounds(0,0,792,800);
        bagr.setLocation(-30,0);
        add(bagr);
    }

}
