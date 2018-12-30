package enter.Map;

import enter.Characters.Character;

public class Position {
    private int indexx;
    private int indexy;
    private Character character;
    boolean empty;

    public Position(int x, int y) {
        empty = true;
        indexx=x;
        indexy=y;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean b) {
        this.empty = b;
    }

    public void setCharacter(Character c){
        this.character=c;
    }

    public int getIndexx(){
        return indexx;
    }

    public int getIndexy(){
        return indexy;
    }

    public Character getCharacter(){
        return character;
    }

}
