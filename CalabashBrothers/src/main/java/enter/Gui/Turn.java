package enter.Gui;

import enter.Factions.AllCharacters;
import enter.Characters.Character;

public class Turn {
    private int turn;


    public Turn(int turn){
        this.turn = turn;
    }

    public void nextTurn() {
        turn = (turn+1)%40;
    }


    public void setTurn(int turn){
        this.turn = turn;
    }

    public int getTurn() {
        return this.turn;
    }
}
