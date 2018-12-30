package enter.Characters;

import enter.Factions.AllCharacters;
import enter.Gui.Turn;
import enter.Map.Position;
import enter.Map.Positions;
import enter.Operations.Operation;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;


public class Character implements Runnable{
    //角色基类 名字 阵营
    protected String name;
    protected String faction;
    protected int x, y;
    protected int identifier;
    protected boolean alive;
    protected int runturn;

    protected Positions positions;
    ImageView view1;//
    ImageView view2;//尸体

    protected Object lock;
    protected Turn turn;
    protected AllCharacters.EndFlag endflag;
    protected Operation lastOp;
    protected ArrayList<Operation> opList;


    public int getIdentifier(){ return this.identifier; }

    public void setOpList(ArrayList<Operation> opList) { this.opList = opList; }

    public void setLastOp(Operation lastOp){ this.lastOp = lastOp; }


    public void setTurn(Turn turn){
        this.turn = turn;
    }


    public void setLock(Object lock){ this.lock=lock; }

    public void setX(int x){this.x = x;}
    public void setY(int y){this.y = y;}


    public Character(int x1, int y1, int n, int runturn, Positions positions, AllCharacters.EndFlag endflag) {
        x = x1;
        y = y1;
        this.positions = positions;
        this.alive=true;
        this.runturn = runturn;
        this.endflag = endflag;
        this.identifier = n;
    }

    public void beKilled(){
        this.alive=false;
        //System.out.println(this.getName()+"死了！");
        positions.getMap()[x][y].setEmpty(true);
    }


    protected void setProperties(String n, String f, String url1, String url2) {
        this.name = n;
        this.faction = f;
        this.view1 = new ImageView(new Image(url1));
        this.view1.setFitWidth(40);
        this.view1.setFitHeight(40);

        this.view2 = new ImageView(new Image(url2));
        this.view2.setFitWidth(40);
        this.view2.setFitHeight(40);
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public ImageView getView1(){
        return view1;
    }
    public ImageView getView2() { return view2; }
    public String getName(){
        return name;
    }

    public String getFaction(){
        return faction;
    }

    public boolean getAlive(){
        return alive;
    }

    @Override
    public void run() {
        try {
            while (!endflag.isEnd()) {
                //首先判断是否有一方已经全部死亡
                synchronized (lock) {
                    if (turn.getTurn() == runturn) {
                        if (this.getAlive()) {
                            int sx = x, sy = y;
                            int rs = tryMove();
                            if (rs == 0) {
                                //找人战斗
                                lastOp.setOpType(Operation.OpType.BATTLE);
                                battle();
                            } else if (rs == 1) {
                                //已经移动了
                                lastOp.setOpType(Operation.OpType.MOVE);
                                lastOp.setCharacter(this);
                                opList.add(new Operation(Operation.OpType.MOVE,this));
                                System.out.println(this.getName() + "：move from" + sx + "," + sy + "to" + x + "," + y);
                            } else if (rs == 2) {
                                endflag.setEnd();//设置结束标志
                            }
                            turn.nextTurn();
                            lock.notifyAll();
                        } else {
                            System.out.println(this.getName() + "已死，无法活动！");
                            turn.nextTurn();
                            turn.nextTurn();
                            //跳过更新
                            lock.notifyAll();
                        }
                    } else {
                        //System.out.println(turn);
                        lock.wait();
                    }
                }
            }
        } catch (InterruptedException e) { }
        System.out.println(this.getName()+"线程结束！");
    }


    public int tryMove() {
        int rs = 2;
        //移动原则：存在活着的敌人并且没有敌人与自己相邻就移动，移动的时候在不冲撞友军的情况下优先横向
        Position posup = positions.getMap()[x][y - 1];
        Position posleft;
        if(x>0) { posleft = positions.getMap()[x - 1][y]; }
        else {posleft = new Position(x-1,y);}
        Position posright;
        if(x<14){ posright=positions.getMap()[x+1][y]; }
        else{posright = new Position(x+1,y);}
        Position posdown = positions.getMap()[x][y + 1];

        Boolean noenemy=true;
        if(!posup.isEmpty()&&posup.getCharacter().getFaction()!= this.getFaction()) noenemy=false;
        else if(!posleft.isEmpty()&&posleft.getCharacter().getFaction()!= this.getFaction()) noenemy=false;
        else if (!posright.isEmpty() && posright.getCharacter().getFaction() != this.getFaction()) noenemy = false;
        else if(!posdown.isEmpty()&&posdown.getCharacter().getFaction()!=this.getFaction()) noenemy=false;

        if (noenemy) {//上下右没有敌人
            //确定方向 上下左右 1234 注意不冲撞友军 优先横向
            int mindistance = 30;
            int direction = 0;

            for (int i = 0; i < 15; i++)
                for (int j = 0; j < 15; j++) {
                    Position pos = positions.getMap()[i][j];
                    if (!pos.isEmpty()) {
                        Character c = pos.getCharacter();
                        if (c.getFaction() != this.getFaction()) {
                            int distance = Math.abs(c.getX() - x) + Math.abs(c.getY() - y);
                            if (distance < mindistance) {
                                mindistance = distance;

                                if (c.getX() > x && posright.isEmpty())
                                    direction = 4;
                                else if (c.getX() < x && posleft.isEmpty())
                                    direction = 3;
                                else if (c.getY() > y && posdown.isEmpty())
                                    direction = 2;
                                else if (c.getY() < y && posup.isEmpty())
                                    direction = 1;
                                else System.out.println("留在原地！");
                            }
                        }
                    }

                }

            //根据方向移动,并且更新地图信息
            if (mindistance != 30) {
                switch (direction) {
                    case 1: {
                        positions.getMap()[x][y].setEmpty(true);//原位置上有一些垃圾信息，但是不影响程序行为
                        y -= 1;
                        positions.getMap()[x][y].setEmpty(false);
                        positions.getMap()[x][y].setCharacter(this);
                        break;
                    }
                    case 2: {
                        positions.getMap()[x][y].setEmpty(true);
                        y += 1;
                        positions.getMap()[x][y].setEmpty(false);
                        positions.getMap()[x][y].setCharacter(this);
                        break;
                    }
                    case 3: {
                        positions.getMap()[x][y].setEmpty(true);
                        x -= 1;
                        positions.getMap()[x][y].setEmpty(false);
                        positions.getMap()[x][y].setCharacter(this);
                        break;
                    }
                    case 4: {
                        positions.getMap()[x][y].setEmpty(true);
                        x += 1;
                        positions.getMap()[x][y].setEmpty(false);
                        positions.getMap()[x][y].setCharacter(this);
                        break;
                    }
                    default:
                        break;
                }
                rs = 1;
            } else {
                //一个敌人都没有，此时应当终止线程
                endflag.setEnd();
                rs = 2;
            }

        }
        else {
            //周围有敌人
            rs= 0;
            System.out.println(this.getName()+"：准备战斗！");
        }
        return rs;
    }


    public void battle(){
        //选择战斗目标，进行战斗，判定胜负，死者bekilled
    }
}


