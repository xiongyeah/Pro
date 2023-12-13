package view;

import controller.GameController;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    private final int ONE_CHESS_SIZE;
    private GameController gameController;
    public JLabel Steps;
    public JLabel Scores;
    public JLabel GoalScores;
    public JLabel MaxSteps;
    public JButton shuffle;
    private int Gscores;
    private int MSteps;
    private ChessboardComponent chessboardComponent;
    private int shuffletime=3;

    public ChessGameFrame(int width, int height, int Steps, int Scores) {
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        MSteps = Steps;
        Gscores = Scores;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        playSound("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\6.wav");

        addJMueBar();
        addLabelMaxSteps();
        addLabelGoalScores();
        addLabelScores();
        addLabel();
        addHelloButton();
        addShuffleButton();
        addSwapConfirmButton();
        addNextStepButton();
        addChessboard();
        chessboardComponent.setGoalscores(Scores);
        chessboardComponent.setMaxSteps(Steps);
        addBack();
        //addLoadButton();
        //addOpenButton();
    }

    private void playSound(String soundFilePath) {
        try {
            File soundFile = new File(soundFilePath);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (Exception ex) {
        }
    }
    public void setGscores(int gscores) {
        Gscores = gscores;
    }

    public void setMSteps(int MSteps) {
        this.MSteps = MSteps;
    }

    public void addBack() {
        ImageIcon back = new ImageIcon("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\backGround.png");
        JLabel bg = new JLabel(back);
        bg.setLocation(0, 0);
        bg.setBounds(-50, 0, 1408, 780);
        add(bg);
    }

    public void addJMueBar() {
        JMenuBar Menu = new JMenuBar();
        JMenu Functions = new JMenu("Functions");
        JMenu About = new JMenu("About us");

        JMenuItem Re = new JMenuItem("ReStart");
        JMenuItem Relog = new JMenuItem("ReLogin");
        JMenuItem REload = new JMenuItem("SaveLoad");
        JMenuItem REOpen = new JMenuItem("ReadLoad");

        JMenuItem Corp = new JMenuItem("Corporations");
        JMenuItem Sup = new JMenuItem("Support us");


        Functions.add(Re);
        Functions.add(Relog);
        Functions.add(REload);
        Functions.add(REOpen);
        About.add(Corp);
        About.add(Sup);
        Menu.add(Functions);
        Menu.add(About);
        this.setJMenuBar(Menu);

        Relog.addActionListener(e -> {
            this.dispose();
        });
        Re.addActionListener(e -> {
            chessboardComponent.newChessboard();
        });
        REload.addActionListener(e -> {
            String path = JOptionPane.showInputDialog(this, "Input Path here");
            chessboardComponent.saveGameFromFile(path);
            this.dispose();
        });
        REOpen.addActionListener(e -> {
            chessboardComponent.loadGameFromFile();
        });

        Corp.addActionListener(e -> {
            AboutFrame a=new AboutFrame();
            a.setDefaultCloseOperation(2);
            a.setVisible(true);
        });//❗️❗️❗️

        Sup.addActionListener(e -> {
            SupportFrame A = new SupportFrame(850, 1200);
            A.setVisible(true);
        });

    }

    class AboutFrame extends JFrame {
        public AboutFrame() {
            setTitle("About us");
            addAll();
            setSize(200,200);
            setLocationRelativeTo(null);
            setLayout(null);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }
        private void addAll(){
            JLabel All1=new JLabel("前段编写：12212762 王苓郦");
            JLabel All2=new JLabel("后端编写：12311103 熊烨");
            All1.setFont(new Font("宋体", Font.BOLD, 12));
            All2.setFont(new Font("宋体", Font.BOLD, 12));
            All1.setVisible(true);
            All1.setLocation(20,0);
            All1.setSize(200,100);
            add(All1);
            All2.setVisible(true);
            All2.setLocation(20,30);
            All2.setSize(200,100);
            add(All2);
        }
    }//❗️❗️❗️

    private void addChessboard() {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5-70, HEIGTH / 10);
        add(chessboardComponent);
        chessboardComponent.labelStep = Steps;
        chessboardComponent.labelScores = Scores;
        chessboardComponent.lableGoalScores = GoalScores;
        chessboardComponent.lableMaxSteps = MaxSteps;
        chessboardComponent.shuffle=shuffle;
        chessboardComponent.ShuffleTime=shuffletime;
    }

    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }

    /**
     * 在游戏面板中添加棋盘
     */


    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Steps:");
        Steps = statusLabel;
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(500, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addLabelScores() {
        JLabel statusLabel = new JLabel("Scores:");
        Scores = statusLabel;
        statusLabel.setLocation(HEIGTH, HEIGTH / 10 + 20);
        statusLabel.setSize(500, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addLabelMaxSteps() {
        JLabel statusLabel = new JLabel("MaxSteps:" + MSteps);
        MaxSteps = statusLabel;
        statusLabel.setLocation(HEIGTH, HEIGTH / 10 - 40);
        statusLabel.setSize(500, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    private void addLabelGoalScores() {
        JLabel statusLabel = new JLabel("Gaol:" + Gscores);
        GoalScores = statusLabel;
        statusLabel.setLocation(HEIGTH + 155, HEIGTH / 10 - 40);
        statusLabel.setSize(500, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addShuffleButton() {
        UIManager.put("Button.foreground", Color.BLACK);
        JButton button = new JButton("Shuffle!\n"+shuffletime);
        shuffle=button;
        button.addActionListener((e) -> chessboardComponent.Shuffle());
        button.setLocation(HEIGTH, HEIGTH / 10 +360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addHelloButton() {
        UIManager.put("Button.foreground", Color.BLACK);
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    private void addSwapConfirmButton() {
        UIManager.put("Button.foreground", Color.BLACK);
        JButton button = new JButton("Confirm Swap");
        button.addActionListener((e) -> chessboardComponent.swapChess());
        button.addActionListener(new SoundButtonListener1());
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    class SoundButtonListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\8.wav");
        }

        private void playSound(String soundFilePath) {
            try {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception ex) {
            }
        }
    }

    private void addNextStepButton() {
        UIManager.put("Button.foreground", Color.BLACK);
        JButton button = new JButton("Next Step");
        button.addActionListener((e) -> chessboardComponent.nextStep());
        button.addActionListener(new SoundButtonListener2());
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }
    class SoundButtonListener2 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            playSound("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\next.wav");
        }

        private void playSound(String soundFilePath) {
            try {
                File soundFile = new File(soundFilePath);
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);

                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                clip.start();
            } catch (Exception ex) {
            }
        }
    }

}
