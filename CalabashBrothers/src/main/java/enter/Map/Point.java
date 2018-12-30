package enter.Map;

public class Point {
    private int x;
    private int y;

    private int index;

    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
    public int getindex(){return this.index;}

    public Point(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Point(int x, int y, int index){
        this.x = x;
        this.y = y;
        this.index = index;
    }
}
