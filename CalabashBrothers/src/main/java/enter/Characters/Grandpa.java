package enter.Characters;

import enter.Factions.AllCharacters;
import enter.Map.Position;
import enter.Map.Positions;
import enter.Operations.Operation;

public class Grandpa extends Character {
    public Grandpa(int x1, int y1, int n, int runturn, Positions positions, AllCharacters.EndFlag endflag) {
        super(x1, y1, n,runturn, positions, endflag);
        this.setProperties("老爷爷", "葫芦娃", "image/老爷爷.png", "image/葫芦娃尸体.png");
    }


    @Override
    public void battle() {
        Character target;
        Position posup = positions.getMap()[x][y - 1];
        Position posright = positions.getMap()[x + 1][y];
        Position posdown = positions.getMap()[x][y + 1];
        Position posleft;
        if (x > 0) {
            posleft = positions.getMap()[x - 1][y];
        } else {
            posleft = new Position(x - 1, y);
        }

        if (!posup.isEmpty() && posup.getCharacter().getFaction() == "妖精")
            target = posup.getCharacter();
        else if (!posright.isEmpty() && posright.getCharacter().getFaction() == "妖精")
            target = posright.getCharacter();
        else if (!posleft.isEmpty() && posleft.getCharacter().getFaction() == "妖精")
            target = posleft.getCharacter();
        else target = posdown.getCharacter();

        double X = Math.random() * 100;
        if(target.getName()=="蝎子精"){
            if(X<50) {
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
            if(X<60) {
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
            if(X<70) {
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





