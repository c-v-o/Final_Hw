package enter.Gui;

import enter.Factions.AllCharacters;
import enter.Factions.CBs;
import enter.Factions.Demons;
import javafx.application.Platform;

public class UpdateUi implements Runnable {

    private Controller controller;
    private CBs cbs;
    private Demons demons;
    private Object lock;
    private Turn turn;
    private int runturn;
    private AllCharacters.EndFlag endflag;
    private AllCharacters allCharacters;

    public void setAllCharacters(AllCharacters allCharacters) {
        this.allCharacters = allCharacters;
    }


    public UpdateUi(Controller controller) {
        this.controller = controller;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }

    public void setCbs(CBs cbs) {
        this.cbs = cbs;
    }

    public void setDemons(Demons demons) {
        this.demons = demons;
    }

    public void setLock(Object lock) {
        this.lock = lock;
    }

    public void setEndflag(AllCharacters.EndFlag endflag) {
        this.endflag = endflag;
    }


    @Override
    public void run() {
        try {
            while (!endflag.isEnd()) {
                synchronized (lock) {
                    if (turn.getTurn() % 2 == 1) {

                        //Thread.yield();//总是看看有没有其他线程在等待
                        Platform.runLater(() -> {
                            controller.updateLastOp();//更新葫芦娃
                        });
                        turn.nextTurn();
                        lock.notifyAll();
                        Thread.sleep(60);


                    } else {
                        lock.wait();
                    }
                }
            }

        } catch (InterruptedException e) {
            System.out.println("InterruptedException!");
        }

        System.out.println("刷新线程结束！");
    }


}





