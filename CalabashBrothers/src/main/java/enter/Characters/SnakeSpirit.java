package enter.Characters;

import enter.Factions.AllCharacters;
import enter.Map.Position;
import enter.Map.Positions;
import enter.Operations.Operation;

public class SnakeSpirit extends Character {
    public SnakeSpirit(int x1, int y1, int n, int runturn, Positions positions, AllCharacters.EndFlag endflag){
        super(x1,y1,n,runturn,positions,endflag);
        this.setProperties("蛇精","妖精", "image/蛇精.png", "image/妖精尸体.png");
    }


    @Override
    public void battle(){
        Character target;
        Position posup=positions.getMap()[x][y-1];
        Position posleft=positions.getMap()[x-1][y];
        Position posdown=positions.getMap()[x][y+1];
        Position posright;
        if(x<14){ posright=positions.getMap()[x+1][y]; }
        else{posright = new Position(x+1,y);}

        if(!posup.isEmpty()&&posup.getCharacter().getFaction()=="葫芦娃")
            target = posup.getCharacter();
        else if(!posleft.isEmpty()&&posleft.getCharacter().getFaction()=="葫芦娃")
            target=posleft.getCharacter();
        else if(!posright.isEmpty()&&posright.getCharacter().getFaction()=="葫芦娃")
            target=posright.getCharacter();
        else target=posdown.getCharacter();


        double X = Math.random()*100;
        if(target.getName()=="老爷爷"){
            //System.out.println("进入战斗");
            if(X<40) {
                target.beKilled();
                lastOp.setCharacter(target);
                opList.add(new Operation(Operation.OpType.BATTLE,target));
                System.out.println(this.getName()+"杀死了"+target.getName());
                //positions.getMap()[target.getX()][target.getY()].setEmpty(true);//尸体视为空
            }
            else {
                this.beKilled();
                lastOp.setCharacter(this);
                opList.add(new Operation(Operation.OpType.BATTLE,this));
                System.out.println(this.getName()+"被"+target.getName()+"杀死了！");
                //positions.getMap()[this.getX()][this.getY()].setEmpty(true);
            }
        }
        else{
            if(X<55) {
                target.beKilled();
                lastOp.setCharacter(target);
                opList.add(new Operation(Operation.OpType.BATTLE,target));
                System.out.println(this.getName()+"杀死了"+target.getName());
                //positions.getMap()[target.getX()][target.getY()].setEmpty(true);//尸体视为空
            }
            else {
                this.beKilled();
                lastOp.setCharacter(this);
                opList.add(new Operation(Operation.OpType.BATTLE,this));
                System.out.println(this.getName()+"被"+target.getName()+"杀死了！");
                //positions.getMap()[this.getX()][this.getY()].setEmpty(true);
            }
        }



    }
}
