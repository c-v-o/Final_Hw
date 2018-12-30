package enter.Characters;

import enter.Factions.AllCharacters;
import enter.Map.Position;
import enter.Map.Positions;
import enter.Operations.Operation;

public final class CB extends Character {
    static int num = 0;
    public CB(int x1, int y1, int n, int runturn, Positions positions, AllCharacters.EndFlag endflag) {
        super(x1, y1,n, runturn,positions,endflag);
        //if(num >= 7) throw new Exception("葫芦娃数量不可超过7个！");
        //if (identifier > 6 || identifier < 0) throw new Exception("没有这样的葫芦娃！");
        switch (identifier) {
            case 0:
                setProperties("老大", "葫芦娃", "image/cb1.png", "image/葫芦娃尸体.png");
                break;
            case 1:
                setProperties("老二", "葫芦娃", "image/cb2.png", "image/葫芦娃尸体.png");
                break;
            case 2:
                setProperties("老三", "葫芦娃", "image/cb3.png", "image/葫芦娃尸体.png");
                break;
            case 3:
                setProperties("老四", "葫芦娃", "image/cb4.png", "image/葫芦娃尸体.png");
                break;
            case 4:
                setProperties("老五", "葫芦娃", "image/cb5.png", "image/葫芦娃尸体.png");
                break;
            case 5:
                setProperties("老六", "葫芦娃", "image/cb6.png", "image/葫芦娃尸体.png");
                break;
            case 6:
                setProperties("老七", "葫芦娃", "image/cb7.png", "image/葫芦娃尸体.png");
                break;
        }

        num += 1;
    }



    @Override
    public void battle(){
        Character target;
        Position posup=positions.getMap()[x][y-1];
        Position posright=positions.getMap()[x+1][y];
        Position posdown=positions.getMap()[x][y+1];
        Position posleft;
        if(x>0) { posleft = positions.getMap()[x - 1][y]; }
        else {posleft = new Position(x-1,y);}

        if(!posup.isEmpty()&&posup.getCharacter().getFaction()=="妖精")
            target = posup.getCharacter();
        else if(!posright.isEmpty()&&posright.getCharacter().getFaction()=="妖精")
            target=posright.getCharacter();
        else if(!posleft.isEmpty()&&posleft.getCharacter().getFaction()=="妖精")
            target=posleft.getCharacter();
        else target=posdown.getCharacter();

        double X = Math.random()*100;
        if(target.getName()=="蝎子精"){
            if(X<30) {
                target.beKilled();
                lastOp.setCharacter(target);
                opList.add(new Operation(Operation.OpType.BATTLE,target));
                System.out.println(this.getName()+"杀死了"+target.getName()+"!");
                //positions.getMap()[target.getX()][target.getY()].setEmpty(true);//尸体视为空
            }
            else {
                this.beKilled();
                lastOp.setCharacter(this);
                opList.add(new Operation(Operation.OpType.BATTLE,this));
                System.out.println(this.getName()+"被"+target.getName()+"杀死了!");
                //positions.getMap()[this.getX()][this.getY()].setEmpty(true);
            }
        }
        else if(target.getName() == "蛇精"){
            if(X<45) {
                target.beKilled();
                lastOp.setCharacter(target);
                opList.add(new Operation(Operation.OpType.BATTLE,target));
                System.out.println(this.getName()+"杀死了"+target.getName()+"!");
            }
            else {
                this.beKilled();
                lastOp.setCharacter(this);
                opList.add(new Operation(Operation.OpType.BATTLE,this));
                System.out.println(this.getName()+"被"+target.getName()+"杀死了!");
            }
        }
        else{
            if(X<65) {
                target.beKilled();
                lastOp.setCharacter(target);
                opList.add(new Operation(Operation.OpType.BATTLE,target));
                System.out.println(this.getName()+"杀死了"+target.getName()+"!");
            }
            else {
                this.beKilled();
                lastOp.setCharacter(this);
                opList.add(new Operation(Operation.OpType.BATTLE,this));
                System.out.println(this.getName()+"被"+target.getName()+"杀死了！");
            }
        }


    }


}
