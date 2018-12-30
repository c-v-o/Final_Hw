package enter.Factions;

import java.util.ArrayList;

import enter.Characters.Dogface;
import enter.Characters.ScorpionEssence;
import enter.Characters.SnakeSpirit;
import enter.Gui.Turn;
import enter.Map.Point;
import enter.Map.Positions;
import enter.Characters.Character;
import enter.Operations.Operation;

public class Demons {
    private ArrayList<Character> allDemons;
    private Positions positions;
    private Object lock;
    private Turn turn;
    private Operation lastOp;
    private ArrayList<Operation> opList;
    private ArrayList<Point> initDemons;
    private ArrayList<Thread> demonThreads = new ArrayList<>();


    public void setOpList(ArrayList<Operation> opList) { this.opList = opList; }

    public void setLastOp(Operation lastOp){ this.lastOp = lastOp; }


    public void setTurn(Turn turn){
        this.turn = turn;
    }

    public void setLock(Object lock){
        this.lock = lock;
    }

    private void init(Character nc){
        allDemons.add(nc);
        positions.getMap()[nc.getX()][nc.getY()].setEmpty(false);
        positions.getMap()[nc.getX()][nc.getY()].setCharacter(nc);
    }


    public Demons(Positions positions, AllCharacters.EndFlag endflag, ArrayList<Point> initDemons){
        this.positions=positions;
        allDemons = new ArrayList<>();
        this.initDemons = initDemons;

        int size = initDemons.size();
        Point[] array = initDemons.toArray(new Point[size]);

        init(new ScorpionEssence(array[0].getX(),array[0].getY(),0,16,positions,endflag));
        for(int j = 1;j<size-1;j++) {
            init(new Dogface(array[j].getX(), array[j].getY(), j, 16+2*j, positions, endflag));
        }
        init(new SnakeSpirit(array[size-1].getX(),array[size-1].getY(),size-1,14+2*size,positions,endflag));

    }

    public ArrayList<Character> getAllDemons(){
        return allDemons;
    }


    public void initThreads(){
        for(Character demon:allDemons){
            demon.setLock(lock);
            demon.setTurn(turn);
            demonThreads.add(new Thread(demon));
        }
    }


    public void startThread(){
        for(Thread thread:demonThreads){
            thread.start();
        }
    }

}
