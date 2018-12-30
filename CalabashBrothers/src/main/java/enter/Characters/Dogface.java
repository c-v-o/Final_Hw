package enter.Characters;

import enter.Factions.AllCharacters;
import enter.Map.Position;
import enter.Map.Positions;
import enter.Operations.Operation;

//喽啰
public class Dogface extends Character {
    //static int num = 0;
    public Dogface(int x1, int y1, int n, int runturn, Positions positions, AllCharacters.EndFlag endflag) {
        super(x1,y1,n,runturn,positions,endflag);
        //if(num>=10) throw new Exception("最多有10名小喽啰！");
        //if(identifier<0||identifier>9) throw new Exception("没有这样的小兵！");
        switch (identifier) {
            case 1:
                setProperties("小喽啰1","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 2:
                setProperties("小喽啰2","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 3:
                setProperties("小喽啰3","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 4:
                setProperties("小喽啰4","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 5:
                setProperties("小喽啰5","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 6:
                setProperties("小喽啰6","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 7:
                setProperties("小喽啰7","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 8:
                setProperties("小喽啰8","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 9:
                setProperties("小喽啰9","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
            case 10:
                setProperties("小喽啰10","妖精", "image/小喽啰.png", "image/妖精尸体.png");
                break;
        }

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
            if(X<35) {
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



    }

}
