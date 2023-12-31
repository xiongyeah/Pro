package view;

import controller.GameController;
import model.Chessboard;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import view.winFrame;

public class ChessLogin extends JFrame {

    public ChessLogin() {
        setTitle("Login");
        setSize(400,400);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addWelcome();
        addNormalButton();
        addHardButton();
        addHellButton();
        addBack();
    }

    private void addBack() {
        ImageIcon bg=new ImageIcon("src/picture/loginBackground.png");
        JLabel back=new JLabel(bg);
        back.setBounds(0,0,400,500);
        back.setLocation(0,0);
        add(back);
    }

    private void addWelcome() {
        JLabel Welcome = new JLabel("Welcome to the world of Match-3!");
        Welcome.setSize(500, 60);
        Welcome.setLocation(20, 40);
        Welcome.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(Welcome);
    }


    private void addNormalButton() {
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.BLACK);
        JButton Normal = new JButton("Normal");
        Normal.setFont(new Font("Rockwell", Font.BOLD, 12));
        Normal.setBounds(100, 120, 200, 50);
        Normal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                UIManager.put("Button.foreground", Color.BLACK);
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,10,350);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
                mainFrame.setVisible(true);
                gameController.setGscores(350);
                gameController.setMSteps(10);
            }
        });
        add(Normal);
    }

    public void addHardButton() {
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.ORANGE);
        JButton Hard = new JButton("Hard");
        Hard.setFont(new Font("Rockwell", Font.BOLD, 12));
        Hard.setBounds(100, 200, 200, 50);
        Hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("Button.foreground", Color.BLACK);
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,20,900);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
                mainFrame.setVisible(true);
                gameController.setGscores(900);
                gameController.setMSteps(20);
            }
        });
        add(Hard);
    }

    public void addHellButton() {
        UIManager.put("Button.background", Color.WHITE);
        UIManager.put("Button.foreground", Color.RED);
        JButton Hell = new JButton("Hell");
        Hell.setFont(new Font("Rockwell", Font.BOLD, 12));
        Hell.setBounds(100, 280, 200, 50);
        Hell.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UIManager.put("Button.foreground", Color.BLACK);
                ChessGameFrame mainFrame = new ChessGameFrame(1100, 810,40,2000);
                GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard(0));
                mainFrame.setVisible(true);
                gameController.setGscores(2000);
                gameController.setMSteps(40);
            }
        });
        add(Hell);
    }

}
