package view;

import javax.swing.*;

public class winFrame extends JFrame {
    public winFrame(int width, int height) {
        setTitle("You Win!");
        setSize(width, height);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addAgain();
        addExit();
        addBackground();
    }

    private void addBackground(){
        ImageIcon bg=new ImageIcon("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\winBackground.png");
        System.out.println(bg.toString());
        JLabel bagr=new JLabel(bg);
        bagr.setBounds(0,0,750,1000);
        bagr.setLocation(0,0);
        add(bagr);
    }
    private void addAgain(){
        ImageIcon ag=new ImageIcon("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\winAgain.png");
        JButton Again=new JButton();
        Again.setIcon(ag);
        Again.setBounds(0,0,200,79);
        Again.setLocation(270,450);
        Again.addActionListener(e -> {
            this.dispose();
            ChessLogin loginFrame=new ChessLogin();
            loginFrame.setVisible(true);
        });
        add(Again);
    }
    private void addExit(){
        ImageIcon ag=new ImageIcon("C:\\Users\\Tim\\IdeaProjects\\Pro\\src\\picture\\winExit.png");
        JButton Exit=new JButton();
        Exit.setIcon(ag);
        Exit.setBounds(0,0,200,79);
        Exit.setLocation(270,580);
        Exit.addActionListener(e -> {
            this.dispose();
        });
        add(Exit);
    }
}
