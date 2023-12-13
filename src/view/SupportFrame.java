package view;

import javax.swing.*;

public class SupportFrame extends JFrame {
    public SupportFrame(int width,int height){
        setTitle("Support us");
        setSize(width,height);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        //addJLabel();
    }

    /*private void addJLabel() {
        ImageIcon My=new ImageIcon("/Users/wanglingli/Downloads/demo/IMG_0843.JPG");
        JLabel pic=new JLabel(My);
        pic.setSize(828,1124);
        pic.setLocation(0,0);
        pic.setVisible(true);
        add(pic);
    }*/


}
