package enter.Gui;

import enter.Map.Point;
import enter.Operations.Operation;
import javafx.application.Platform;

import java.util.ArrayList;

public class Replay implements Runnable {
    //将记录存储起来

    private Controller controller;

    ArrayList<Point> initcbs = new ArrayList<>();
    ArrayList<Point> initdemons = new ArrayList<>();
    ArrayList<Operation> oplist = new ArrayList<>();

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public ArrayList<Point> getInitcbs() {
        return initcbs;
    }

    public ArrayList<Point> getInitdemons() {
        return initdemons;
    }

    public ArrayList<Operation> getOplist() {
        return oplist;
    }

    @Override
    public void run() {

        try {
            Platform.runLater(() -> {
            for (Point point : initcbs) {
                    controller.initOneCharacter(1, point.getindex(), point.getX(), point.getY());
                } });
            Thread.sleep(40);

            Platform.runLater(() -> {
                for (Point point : initdemons) {
                    controller.initOneCharacter(2, point.getindex(), point.getX(), point.getY());
                }
            });
            Thread.sleep(40);


            for (Operation op : oplist) {
                Platform.runLater(() -> {
                    controller.updateOneOp(op.getType(), op.getFaction(), op.getIndex(), op.getDx(), op.getDy());
                });
                Thread.sleep(40);
            }

        } catch (Exception e) {
        }
    }


}





