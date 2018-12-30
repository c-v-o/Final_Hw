package enter.Strategies;

import enter.Map.Point;

import java.util.ArrayList;

public class ZFs {
    public enum ZF{
        ChangShe,
        HeYi,
        FangMen,
        YuLin
    }

    private ArrayList<Point> initCBs;
    private ArrayList<Point> initDemons;

    public void setInitCBs(ArrayList<Point> initCBs){ this.initCBs = initCBs;}
    public void setInitDemons(ArrayList<Point> initDemons){ this.initDemons = initDemons;}

    public void lineUp(ZF zf, String faction){
        if(faction == "葫芦娃"){
            switch(zf){
                case ChangShe: ChangShe1();break;
                case HeYi:HeYi1();break;
                case FangMen:FangMen1();break;
            }
        }
        else{
            switch(zf){
                case ChangShe: ChangShe2();break;
                case HeYi: HeYi2();break;
                case YuLin:YuLin2();break;
            }

        }
    }

    public void ChangShe1(){
        initCBs.clear();
        initCBs.add(new Point(3,4));
        initCBs.add(new Point(3,5));
        initCBs.add(new Point(3,6));
        initCBs.add(new Point(3,7));
        initCBs.add(new Point(3,8));
        initCBs.add(new Point(3,9));
        initCBs.add(new Point(3,10));
        initCBs.add(new Point(3,11));
    }

    public void HeYi1(){
        initCBs.clear();
        initCBs.add(new Point(5,7));
        initCBs.add(new Point(4,6));
        initCBs.add(new Point(4,8));
        initCBs.add(new Point(3,5));
        initCBs.add(new Point(3,9));
        initCBs.add(new Point(2,4));
        initCBs.add(new Point(2,10));
        initCBs.add(new Point(2,7));
    }

    public void FangMen1(){
        initCBs.clear();
        initCBs.add(new Point(5,7));
        initCBs.add(new Point(4,6));
        initCBs.add(new Point(4,8));
        initCBs.add(new Point(3,5));
        initCBs.add(new Point(3,9));
        initCBs.add(new Point(2,6));
        initCBs.add(new Point(2,8));
        initCBs.add(new Point(1,7));
    }

    public void ChangShe2(){
        initDemons.clear();
        initDemons.add(new Point(11,2));
        initDemons.add(new Point(11,3));
        initDemons.add(new Point(11,4));
        initDemons.add(new Point(11,5));
        initDemons.add(new Point(11,6));
        initDemons.add(new Point(11,7));
        initDemons.add(new Point(11,8));
        initDemons.add(new Point(11,9));
        initDemons.add(new Point(11,10));
        initDemons.add(new Point(11,11));
        initDemons.add(new Point(11,12));
        initDemons.add(new Point(11,13));
    }

    public void HeYi2(){
        initDemons.clear();
        initDemons.add(new Point(9,7));
        initDemons.add(new Point(10,6));
        initDemons.add(new Point(10,8));
        initDemons.add(new Point(11,5));
        initDemons.add(new Point(11,9));
        initDemons.add(new Point(12,4));
        initDemons.add(new Point(12,10));
        initDemons.add(new Point(13,3));
        initDemons.add(new Point(13,11));
        initDemons.add(new Point(14,2));
        initDemons.add(new Point(14,12));
        initDemons.add(new Point(14,7));
    }

    public void YuLin2(){
        initDemons.clear();
        initDemons.add(new Point(9,7));
        initDemons.add(new Point(10,6));
        initDemons.add(new Point(10,7));
        initDemons.add(new Point(10,8));
        initDemons.add(new Point(11,5));
        initDemons.add(new Point(11,6));
        initDemons.add(new Point(11,7));
        initDemons.add(new Point(11,8));
        initDemons.add(new Point(11,9));
        initDemons.add(new Point(12,7));
        initDemons.add(new Point(13,7));
        initDemons.add(new Point(13,8));
    }



}
