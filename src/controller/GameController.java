package controller;

import listener.GameListener;
import model.*;
import view.CellComponent;
import view.ChessComponent;
import view.ChessLogin;
import view.ChessboardComponent;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Random;
import java.util.Scanner;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;

    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;
    public boolean whichNext = false;//记录按下nextStep按钮后是让棋子落下还是生成新棋子

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;

        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }

    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
            }
        }
    }

    // click an empty cell


    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        // TODO: Init your swap function here.棋子交换功能
        if (selectedPoint != null && selectedPoint2 != null) {
            var p1 = model.getChessPieceAt(selectedPoint);
            var p2 = model.getChessPieceAt(selectedPoint2);
            this.model.swapChessPiece(selectedPoint, selectedPoint2);
            whetherEnoughToRemove(selectedPoint);
            whetherEnoughToRemove(selectedPoint2);
            if (!whetherEnoughToRemove(selectedPoint) && !whetherEnoughToRemove(selectedPoint2)) {
                model.setChessPiece(selectedPoint, p1);
                model.setChessPiece(selectedPoint2, p2);
                System.out.println("[" + selectedPoint.getRow() + "," + selectedPoint.getCol() + "] and [" + selectedPoint2.getRow() + "," + selectedPoint2.getCol() + "] can not be swapped.");
            } else {
                remove(selectedPoint);
                remove(selectedPoint2);
                view.addStep(1);
                view.check();
                System.out.println("[" + selectedPoint.getRow() + "," + selectedPoint.getCol() + "] and [" + selectedPoint2.getRow() + "," + selectedPoint2.getCol() + "] have been swapped.");
            }
            view.labelStep.setText("Steps:" + view.getStep());
            view.labelScores.setText("Scores:" + view.getScores());
            view.initiateChessComponent(model);
            view.repaint();
        }
    }


    public boolean whetherEnoughToRemove(ChessboardPoint point) {
        int countRow = 1, countCol = 1;
        if (point.getRow() >= 0 && point.getRow() < 8 && point.getCol() >= 0 && point.getCol() < 8 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol())) != null) {
            if (point.getCol() != 7 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 1)) != null) {
                if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 1)).getName())) {
                    countCol++;
                    if (point.getCol() != 6 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 2)) != null) {
                        if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 2)).getName()))
                            countCol++;
                    }
                }
            }
            if (point.getCol() != 0 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 1)) != null) {
                if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 1)).getName())) {
                    countCol++;
                    if (point.getCol() != 1 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 2)) != null) {
                        if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 2)).getName()))
                            countCol++;
                    }
                }
            }
            if (point.getRow() != 7 && model.getChessPieceAt(new ChessboardPoint(point.getRow() + 1, point.getCol())) != null) {
                if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() + 1, point.getCol())).getName())) {
                    countRow++;
                    if (point.getRow() != 6 && model.getChessPieceAt(new ChessboardPoint(point.getRow() + 2, point.getCol())) != null) {
                        if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() + 2, point.getCol())).getName()))
                            countRow++;
                    }
                }
            }
            if (point.getRow() != 0 && model.getChessPieceAt(new ChessboardPoint(point.getRow() - 1, point.getCol())) != null) {
                if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() - 1, point.getCol())).getName())) {
                    countRow++;
                    if (point.getRow() != 1 && model.getChessPieceAt(new ChessboardPoint(point.getRow() - 2, point.getCol())) != null) {
                        if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() - 2, point.getCol())).getName()))
                            countRow++;
                    }
                }
            }
        }
        point.setCountRow(countRow);
        point.setCountCol(countCol);
        return countRow > 2 || countCol > 2;
    }

    public void remove(ChessboardPoint point) {
        if (point.countRow > 2) {
            if (point.getRow() >= 0 && point.getRow() < 8 && point.getCol() >= 0 && point.getCol() < 8) {
                if (point.getRow() != 7 && model.getChessPieceAt(new ChessboardPoint(point.getRow() + 1, point.getCol())) != null) {
                    if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() + 1, point.getCol())).getName())) {
                        view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow() + 1, point.getCol()));
                        model.removeChessPiece(new ChessboardPoint(point.getRow() + 1, point.getCol()));
                        view.addScores(10);
                        if (point.getRow() != 6 && model.getChessPieceAt(new ChessboardPoint(point.getRow() + 2, point.getCol())) != null) {
                            if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() + 2, point.getCol())).getName())) {
                                view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow() + 2, point.getCol()));
                                model.removeChessPiece(new ChessboardPoint(point.getRow() + 2, point.getCol()));
                                view.addScores(10);
                            }
                        }
                    }
                }
                if (point.getRow() != 0 && model.getChessPieceAt(new ChessboardPoint(point.getRow() - 1, point.getCol())) != null) {
                    if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() - 1, point.getCol())).getName())) {
                        view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow() - 1, point.getCol()));
                        model.removeChessPiece(new ChessboardPoint(point.getRow() - 1, point.getCol()));
                        view.addScores(10);
                        if (point.getRow() != 1 && model.getChessPieceAt(new ChessboardPoint(point.getRow() - 2, point.getCol())) != null) {
                            if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow() - 2, point.getCol())).getName())) {
                                view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow() - 2, point.getCol()));
                                model.removeChessPiece(new ChessboardPoint(point.getRow() - 2, point.getCol()));
                                view.addScores(10);
                            }
                        }
                    }
                }
            }
        }
        if (point.countCol > 2) {
            if (point.getRow() >= 0 && point.getRow() < 8 && point.getCol() >= 0 && point.getCol() < 8) {
                if (point.getCol() != 7 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 1)) != null) {
                    if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 1)).getName())) {
                        view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow(), point.getCol() + 1));
                        model.removeChessPiece(new ChessboardPoint(point.getRow(), point.getCol() + 1));
                        view.addScores(10);
                        if (point.getCol() != 6 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 2)) != null) {
                            if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() + 2)).getName())) {
                                view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow(), point.getCol() + 2));
                                model.removeChessPiece(new ChessboardPoint(point.getRow(), point.getCol() + 2));
                                view.addScores(10);
                            }
                        }
                    }
                }
                if (point.getCol() != 0 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 1)) != null) {
                    if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 1)).getName())) {
                        view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow(), point.getCol() - 1));
                        model.removeChessPiece(new ChessboardPoint(point.getRow(), point.getCol() - 1));
                        view.addScores(10);
                        if (point.getCol() != 1 && model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 2)) != null) {
                            if (model.getChessPieceAt(point).getName().equals(model.getChessPieceAt(new ChessboardPoint(point.getRow(), point.getCol() - 2)).getName())) {
                                view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow(), point.getCol() - 2));
                                model.removeChessPiece(new ChessboardPoint(point.getRow(), point.getCol() - 2));
                                view.addScores(10);
                            }
                        }
                    }
                }
            }
        }
        if (point.countRow > 2 || point.countCol > 2) {
            view.removeChessComponentAtGrid(new ChessboardPoint(point.getRow(), point.getCol()));
            model.removeChessPiece(new ChessboardPoint(point.getRow(), point.getCol()));
            view.addScores(10);
        }
    }

    @Override
    public void onPlayerNextStep() {
        // TODO: Init your next step function here
        int staticScore = view.getScores();
        int[] array = new int[]{0, 0, 0, 0, 0, 0, 0, 0};//记录消除区上方有多少个棋子
        if (whichNext == true) {
            for (int i = 0; i < 64; i++) {
                ChessboardPoint point = new ChessboardPoint(i / 8, i % 8);
                if (whetherEnoughToRemove(point)) {
                    remove(point);
                    view.initiateChessComponent(model);
                    view.repaint();
                }
            }
            whichNext = !whichNext;
        }
        for (int j = 0; j < 8; j++) {
            int count = 0;//只有第一次出现null才计入数组
            for (int i = 0; i < 7; i++) {
                if (model.getChessPieceAt(new ChessboardPoint(i, j)) != null && model.getChessPieceAt(new ChessboardPoint(i + 1, j)) == null) {
                    int i1 = i;
                    count++;
                    while (i1 < 7 && model.getChessPieceAt(new ChessboardPoint(i1 + 1, j)) == null) {
                        i1++;
                    }
                    model.setChessPiece(new ChessboardPoint(i1, j), model.getChessPieceAt(new ChessboardPoint(i, j)));
                    model.removeChessPiece(new ChessboardPoint(i, j));
                    if (count == 1) {
                        array[j] = i + 1;
                    }
                    i = -1;
                }
            }
        }
        for (int j = 0; j < 8; j++) {
            for (int i = 0; i < array[j]; i++) {
                if (model.getChessPieceAt(new ChessboardPoint(i, j)) != null)
                    view.removeChessComponentAtGrid(new ChessboardPoint(i, j));
            }
            for (int i = 0; i < 8; i++) {
                if (model.getChessPieceAt(new ChessboardPoint(i, j)) == null)
                    model.setChessPiece(new ChessboardPoint(i, j), new ChessPiece(Util.RandomPick(new String[]{"💎", "⚪", "▲", "🔶"})));
            }
        }
        for (int i = 0; i < 64; i++) {
            if (whetherEnoughToRemove(new ChessboardPoint(i / 8, i % 8))) {
                whichNext = !whichNext;
                break;
            }
        }
        view.initiateChessComponent(model);
        view.repaint();
        if (whichNext == true)
            System.out.println("There are still some match can be moved.");
        else {
            System.out.println("Nothing can be moved,please continue your next step.");
            view.setScores(staticScore);
        }
        this.selectedPoint = null;
        this.selectedPoint2 = null;
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint != null && selectedPoint2 != null){
            System.out.println("Please click the NextStep button");
            return;
        }
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }

        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }
    }
    public void saveGameFromFile(String path) {
        try{
            String filename="/Users/wanglingli/Documents/loads/"+path;
            File file=new File(filename);
            if(file.createNewFile()){
                FileWriter writer=new FileWriter(file);
                System.out.println(filename);
                String A=String.valueOf(view.getMaxSteps());
                writer.write(A);
                writer.write("\n");
                String B=String.valueOf(view.getGoalscores());
                writer.write(B);
                writer.write("\n");
                String C=String.valueOf(view.step);
                writer.write(C);
                writer.write("\n");
                String D=String.valueOf(view.scores);
                writer.write(D);
                writer.write("\n");
                for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                    for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                        if(model.getGrid()[i][j].getPiece().getName()==""){
                            writer.write("1");
                        } else if (model.getGrid()[i][j].getPiece().getName()=="") {
                            writer.write("2");
                        }else if (model.getGrid()[i][j].getPiece().getName()=="▲") {
                            writer.write("3");
                        }else if (model.getGrid()[i][j].getPiece().getName()=="") {
                            writer.write("4");
                        }
                        writer.write(" ");
                    }
                    writer.write("\n");
                }
                String E=String.valueOf(view.getShuffleTime());
                writer.write(E);
                writer.close();
                System.out.println("success");
            }else{
                int n=JOptionPane.showConfirmDialog(null,"The name of file has existed, whether to substitute?","Warning",JOptionPane.YES_NO_OPTION);
                if(n==0){
                    FileWriter writer=new FileWriter(file);
                    String A=String.valueOf(view.getMaxSteps());
                    writer.write(A);
                    writer.write("\n");
                    String B=String.valueOf(view.getGoalscores());
                    writer.write(B);
                    writer.write("\n");
                    String C=String.valueOf(view.step);
                    writer.write(C);
                    writer.write("\n");
                    String D=String.valueOf(view.scores);
                    writer.write(D);
                    writer.write("\n");
                    for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                        for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                            if(model.getGrid()[i][j].getPiece().getName()==""){
                                writer.write("1");
                            } else if (model.getGrid()[i][j].getPiece().getName()=="") {
                                writer.write("2");
                            }else if (model.getGrid()[i][j].getPiece().getName()=="▲") {
                                writer.write("3");
                            }else if (model.getGrid()[i][j].getPiece().getName()=="") {
                                writer.write("4");
                            }
                            writer.write(" ");
                        }
                        writer.write("\n");
                    }
                    String E=String.valueOf(view.getShuffleTime());
                    writer.write(E);
                    writer.close();
                }
            }
        }catch(IOException e){
            System.out.println("no");
            e.printStackTrace();
        }
    }

    public void loadGameFromFile(){
        File file = new File("/Users/wanglingli/Documents/loads");
        File[] tempList = file.listFiles();
        if(tempList.length==0){
            JOptionPane.showMessageDialog(null,"No existing loads","WARNING",JOptionPane.ERROR_MESSAGE);
        }else {
            Object[] filenames = new String[tempList.length];
            for (int i = 0; i < tempList.length; i++) {
                if (tempList[i].isFile()) {
                    filenames[i] = tempList[i].getName();
                }
            }
            String A=(String) JOptionPane.showInputDialog(null, "The load you want", "Read load", JOptionPane.QUESTION_MESSAGE,null, filenames, filenames[filenames.length - 1]);
            try (Scanner sc = new Scanner(new FileReader("/Users/wanglingli/Documents/loads/" + A))) {
                view.setMaxSteps(sc.nextInt());
                view.lableMaxSteps.setText("MaxSteps:" + view.getMaxSteps());
                view.setGoalscores(sc.nextInt());
                view.lableGoalScores.setText("Gaol::" + view.getGoalscores());
                view.setStep(sc.nextInt());
                view.labelStep.setText("Steps:" + view.getStep());
                view.setScores(sc.nextInt());
                view.labelScores.setText("Scores:" + view.getScores());
                for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                    for (int k = 0; k < Constant.CHESSBOARD_COL_SIZE.getNum(); k++) {
                        ChessboardPoint a = new ChessboardPoint(i, k);
                        String f = sc.next();
                        if (f.equals("1")) {
                            ChessPiece d = new ChessPiece("");
                            model.setChessPiece(a, d);
                        } else if (f.equals("2")) {
                            ChessPiece c = new ChessPiece("");
                            model.setChessPiece(a, c);
                        } else if (f.equals("3")) {
                            ChessPiece c = new ChessPiece("▲");
                            model.setChessPiece(a, c);
                        } else if (f.equals("4")) {
                            ChessPiece c = new ChessPiece("");
                            model.setChessPiece(a, c);
                        }
                    }
                }
                view.setShuffleTime(sc.nextInt());
                view.shuffle.setText("Shuffle!\n"+view.getShuffleTime());
                view.initiateChessComponent(model);
                view.repaint();
            } catch (IOException ex) {
                System.out.println("no");
            }
        }
    };

    public void newChessboard() {
        view.setStep(0);
        view.labelStep.setText("Steps:" + view.step);
        view.setScores(0);
        view.labelScores.setText("Scores:" + view.scores);
        model.initPieces(0);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public void Shuffle() {
        if (view.ShuffleTime > 0) {
            view.deShuffle();
            view.shuffle.setText("Shuffle!\n" + view.getShuffleTime());
            model.initPieces(0);
            view.initiateChessComponent(model);
            view.repaint();
        }
    }
}
