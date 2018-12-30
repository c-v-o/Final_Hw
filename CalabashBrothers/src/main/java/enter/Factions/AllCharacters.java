package enter.Factions;

import java.util.ArrayList;
import enter.Characters.Character;
import enter.Characters.CB;
import enter.Characters.Grandpa;
import enter.Gui.Turn;
import enter.Map.Point;
import enter.Map.Positions;
import enter.Operations.Operation;
import enter.Strategies.ZFs;

public class AllCharacters {
    private CBs cbs;
    private Demons demons;
    private Positions positions;
    private Object lock;
    private Turn turn;
    private EndFlag endflag;
    private Operation lastOp;
    private ArrayList<Operation> opList;
    private ArrayList<Point> initCBs = new ArrayList<>();
    private ArrayList<Point> initDemons = new ArrayList<>();
    private ZFs zfs;


    public ArrayList<Point> getInitCBs(){
        return this.initCBs;
    }

    public ArrayList<Point> getInitDemons(){
        return this.initDemons;
    }

    public ZFs getZfs(){ return zfs; }

    public class EndFlag{
        boolean end;
        EndFlag(){ end = false;}
        public boolean isEnd(){ return end; }
        public void setEnd(){ end = true; }
    }

    public void setTurn(Turn turn){
        this.turn = turn;
        cbs.setTurn(turn);
        demons.setTurn(turn);
    }

    public void setLock(Object lock){
        this.lock = lock;
        cbs.setLock(lock);
        demons.setLock(lock);
    }


    public EndFlag getEndflag(){ return endflag; }
    public Operation getLastOp(){ return lastOp; }
    public ArrayList<Operation> getOpList(){ return opList; }

    //改人物坐标，刷新地图
    public void changeZF(){
        for(int i=0;i<positions.getMap().length;i++) {
            for(int j=0;j<positions.getMap()[i].length;j++)
                positions.getMap()[i][j].setEmpty(true);
        }

        int size1 = initCBs.size();
        Point[] array1 = initCBs.toArray(new Point[size1]);
        int x = 0;
        for(Character cb:cbs.getAllCBs()) {
            if(cb.getAlive()) {
                cb.setX(array1[x].getX());
                cb.setY(array1[x].getY());
                positions.getMap()[array1[x].getX()][array1[x].getY()].setEmpty(false);
                positions.getMap()[array1[x].getX()][array1[x].getY()].setCharacter(cb);
                x++;
            }
        }


        int size2 = initDemons.size();
        Point[] array2 = initDemons.toArray(new Point[size2]);
        int y = 0;
        for(Character demon:demons.getAllDemons()) {
            if(demon.getAlive()) {
                demon.setX(array2[y].getX());
                demon.setY(array2[y].getY());
                positions.getMap()[array2[y].getX()][array2[y].getY()].setEmpty(false);
                positions.getMap()[array2[y].getX()][array2[y].getY()].setCharacter(demon);
                y++;
            }
        }
    }

    public AllCharacters(Positions positions){
        endflag = new EndFlag();
        lastOp = new Operation(Operation.OpType.MOVE,new Character(0,0,0,0,positions,endflag));
        opList = new ArrayList<>();


        //填写人物初始位置信息list
        zfs = new ZFs();
        zfs.setInitCBs(initCBs);
        zfs.setInitDemons(initDemons);
        zfs.lineUp(ZFs.ZF.HeYi,"葫芦娃");
        zfs.lineUp(ZFs.ZF.HeYi,"妖精");


        this.positions = positions;
        cbs=new CBs(positions,endflag,initCBs);
        demons=new Demons(positions,endflag,initDemons);



        cbs.setLastOp(lastOp);
        cbs.setOpList(opList);
        demons.setLastOp(lastOp);
        demons.setOpList(opList);
        //cbs.initThreads();
        //demons.initThreads();
        for(Character c1:cbs.getAllCBs()) {
            c1.setLastOp(lastOp);
            c1.setOpList(opList);
        }
        for(Character c2:demons.getAllDemons()) {
            c2.setLastOp(lastOp);
            c2.setOpList(opList);
        }
    }

    public CBs getCbs(){ return this.cbs; }
    public Demons getDemons(){ return this.demons; }


}
