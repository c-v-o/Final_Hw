package enter.Operations;

import enter.Characters.Character;

public class Operation {
    public enum OpType{
        MOVE,
        BATTLE
    }
    OpType opType;
    int type;
    Character character;
    private int dx;
    private int dy;
    private int faction;
    private int index;

    public int getDx(){ return this.dx; }
    public int getDy(){ return this.dy; }
    public int getFaction(){ return faction;}
    public int getIndex(){return index;}
    public int getType(){ return type;}

    public Operation(OpType opType, Character character){
        this.opType = opType;
        this.character = character;
        this.dx = character.getX();
        this.dy = character.getY();
    }

    public Operation(int type,int dx, int dy, int faction, int index){
        this.type = type;
        this.dx =dx;
        this.dy = dy;
        this.faction = faction;
        this.index = index;
    }

    public OpType getOpType(){
        return this.opType;
    }

    public Character getCharacter(){
        return this.character;
    }

    public void setOpType(OpType opType){
        this.opType = opType;
    }

    public void setCharacter(Character character){
        this.character = character;
    }


}
