package enter.Factions;


import enter.Characters.CB;
import enter.Characters.Character;
import enter.Characters.Grandpa;
import enter.Gui.Controller;
import enter.Gui.Turn;
import enter.Map.Point;
import enter.Map.Positions;
import enter.Operations.Operation;

import java.util.ArrayList;

public class CBs {

    private ArrayList<Character> allCBs;
    private Positions positions;
    private Object lock;
    private Turn turn;
    private Operation lastOp;
    private ArrayList<Operation> opList;
    private ArrayList<Point> initCBs;
    private ArrayList<Thread> cbThreads = new ArrayList<>();


    public void setOpList(ArrayList<Operation> opList) { this.opList = opList; }

    public void setLastOp(Operation lastOp){ this.lastOp = lastOp; }

    public void setTurn(Turn turn){
        this.turn = turn;
    }

    public void setLock(Object lock){ this.lock = lock; }



    private void init(Character nc){
        allCBs.add(nc);
        positions.getMap()[nc.getX()][nc.getY()].setEmpty(false);
        positions.getMap()[nc.getX()][nc.getY()].setCharacter(nc);

    }

    public CBs(Positions positions, AllCharacters.EndFlag endflag, ArrayList<Point> initCBs) {
        this.positions = positions;
        this.initCBs=initCBs;
        allCBs = new ArrayList<>();


        int size = initCBs.size();
        Point[] array = initCBs.toArray(new Point[size]);
        for(int i = 0;i<size-1;i++){
            init(new CB(array[i].getX(), array[i].getY(), i, 2*i, positions, endflag));
        }
        init(new Grandpa(array[size-1].getX(),array[size-1].getY(),size-1,2*size-2,positions,endflag));
    }


    public void initThreads(){
        for(Character cb:allCBs){
            cb.setLock(lock);
            cb.setTurn(turn);
            Thread thread = new Thread(cb);
            cbThreads.add(thread);
        }
    }

    public void startThread() throws Exception{
        for(Thread thread: cbThreads){
            thread.start();
        }
    }

    public ArrayList<Character> getAllCBs(){
        return allCBs;
    }
}
