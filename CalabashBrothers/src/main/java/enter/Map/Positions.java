package enter.Map;

public class Positions {
    private Position[] map[];

    public Positions() {
        map = new Position[15][15];
        for (int i = 0; i < 15; i++)
            for (int j = 0; j < 15; j++)
                map[i][j] = new Position(i, j);
    }

    public Position[][] getMap() {
        return map;
    }
}
