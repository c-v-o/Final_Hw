package enter.Gui;

import enter.Characters.CB;
import enter.Factions.AllCharacters;
import enter.Factions.CBs;
import enter.Characters.Character;
import enter.Factions.Demons;
import enter.Map.Point;
import enter.Operations.Operation;
import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.GridPane;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Controller{

    private Stage stage;
    private Scene scene;
    private CBs cbs;
    private Demons demons;
    private UpdateUi updateui;
    private Object lock;
    private Turn turn;
    private AllCharacters allCharacters;
    private Replay replay;
    private Thread updateUiThread;
    private Thread replayThread;

    public void setReplay(Replay replay){ this.replay = replay;}


    public void setAllCharacters(AllCharacters allCharacters){ this.allCharacters = allCharacters; }

    public void setTurn(Turn turn){
        this.turn = turn;
    }

    @FXML
    private BorderPane mainUi;
    @FXML
    private GridPane world;


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;

    }
    public void setLock(Object lock){ this.lock=lock; }

    public void setUpdateui(UpdateUi updateui){
        this.updateui=updateui;
    }

    public void setCbs(CBs cbs){
        this.cbs=cbs;
    }
    public void setDemons(Demons demons){ this.demons=demons;}

    public void addChildren() {

        for (Character cb : cbs.getAllCBs()) {
            world.getChildren().remove(cb.getView1());
            world.getChildren().remove(cb.getView2());
            if (cb.getAlive())
                world.add(cb.getView1(), cb.getX(), cb.getY());
            else
                world.add(cb.getView2(), cb.getX(), cb.getY());
        }

        for (Character demon : demons.getAllDemons()) {
            world.getChildren().remove(demon.getView1());
            world.getChildren().remove(demon.getView2());
            if (demon.getAlive())
                world.add(demon.getView1(), demon.getX(), demon.getY());
            else
                world.add(demon.getView2(), demon.getX(), demon.getY());
        }

    }

    public void updateLastOp(){
        Operation.OpType type = allCharacters.getLastOp().getOpType();
        Character c = allCharacters.getLastOp().getCharacter();
        if(type == Operation.OpType.MOVE){
            world.getChildren().remove(c.getView1());
            world.add(c.getView1(), c.getX(), c.getY());
        }
        else {
            world.getChildren().remove(c.getView1());
            world.add(c.getView2(), c.getX(), c.getY());
        }

        System.out.println(c.getName());

    }




    public void outputBattlelog(File target) {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            document.setXmlStandalone(true);
            Element replayInformation = document.createElement("ReplayInformation");

            Element initCharacter = document.createElement("InitCharacter");
            Element operations = document.createElement("Operations");

            //填充初始节点
            int i = 0;
            for (Point point : allCharacters.getInitCBs()) {
                Element character = document.createElement("Character");

                Element faction = document.createElement("Faction");
                faction.setTextContent("1");
                character.appendChild(faction);

                Element index = document.createElement("Index");
                index.setTextContent("" + i);
                character.appendChild(index);

                Element sx = document.createElement("Sx");
                sx.setTextContent("" + point.getX());
                character.appendChild(sx);

                Element sy = document.createElement("Sy");
                sy.setTextContent("" + point.getY());
                character.appendChild(sy);

                initCharacter.appendChild(character);

                i = i + 1;
            }
            int j = 0;
            for (Point point : allCharacters.getInitDemons()) {
                Element character = document.createElement("Character");

                Element faction = document.createElement("Faction");
                faction.setTextContent("2");
                character.appendChild(faction);

                Element index = document.createElement("Index");
                index.setTextContent("" + j);
                character.appendChild(index);

                Element sx = document.createElement("Sx");
                sx.setTextContent("" + point.getX());
                character.appendChild(sx);

                Element sy = document.createElement("Sy");
                sy.setTextContent("" + point.getY());
                character.appendChild(sy);

                initCharacter.appendChild(character);

                j = j + 1;
            }
            replayInformation.appendChild(initCharacter);

            //填充操作信息
            for (Operation op : allCharacters.getOpList()) {
                Element oneOp = document.createElement("OneOp");

                //操作类型
                Element opType = document.createElement("OpType");
                if (op.getOpType() == Operation.OpType.MOVE)
                    opType.setTextContent("1");
                else
                    opType.setTextContent("2");
                oneOp.appendChild(opType);

                //人物阵营
                Element faction = document.createElement("Faction");
                if (op.getCharacter().getFaction() == "葫芦娃")
                    faction.setTextContent("1");
                else
                    faction.setTextContent("2");
                oneOp.appendChild(faction);

                //人物编号
                Element index = document.createElement("Index");
                index.setTextContent("" + op.getCharacter().getIdentifier());
                oneOp.appendChild(index);


                Element dx = document.createElement("Dx");
                dx.setTextContent("" + op.getDx());
                oneOp.appendChild(dx);

                Element dy = document.createElement("Dy");
                dy.setTextContent("" + op.getDy());
                oneOp.appendChild(dy);


                operations.appendChild(oneOp);
            }
            replayInformation.appendChild(operations);
            document.appendChild(replayInformation);


            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty(OutputKeys.INDENT, "yes");
            tf.transform(new DOMSource(document), new StreamResult(target));
        }catch(Exception e){}

    }

    public void updateOneOp(int opType, int faction, int index, int dx, int dy ) {


        if (opType == 1) {
            if (faction == 1) {
                int size = cbs.getAllCBs().size();
                Character[] array = cbs.getAllCBs().toArray(new Character[size]);
                world.getChildren().remove(array[index].getView1());
                world.add(array[index].getView1(), dx, dy);
            } else {
                int size = demons.getAllDemons().size();
                Character[] array = demons.getAllDemons().toArray(new Character[size]);
                world.getChildren().remove(array[index].getView1());
                world.add(array[index].getView1(), dx, dy);
            }
        }
        else {
            if (faction == 1) {
                int size = cbs.getAllCBs().size();
                Character[] array = cbs.getAllCBs().toArray(new Character[size]);
                world.getChildren().remove(array[index].getView1());
                world.add(array[index].getView2(), dx, dy);

            }
            else {
                int size = demons.getAllDemons().size();
                Character[] array = demons.getAllDemons().toArray(new Character[size]);
                world.getChildren().remove(array[index].getView1());
                world.add(array[index].getView2(), dx, dy);



            }
        }

    }




    public void initOneCharacter(int faction, int index, int sx, int sy) {

        if (faction == 1) {
            //System.out.println("1");
            int size = cbs.getAllCBs().size();
            Character[] array = cbs.getAllCBs().toArray(new Character[size]);

            world.getChildren().remove(array[index].getView1());
            world.getChildren().remove(array[index].getView2());
            world.add(array[index].getView1(), sx, sy);

        }
        else if (faction == 2) {
            //System.out.println("2");
            int size = demons.getAllDemons().size();
            Character[] array = demons.getAllDemons().toArray(new Character[size]);

            world.getChildren().remove(array[index].getView1());
            world.getChildren().remove(array[index].getView2());
            world.add(array[index].getView1(), sx, sy);

        }


    }




    public void inputBattlelog(File log) {

        try {
            ArrayList<Point> initcbs = replay.getInitcbs();
            ArrayList<Point> initdemons = replay.getInitdemons();
            ArrayList<Operation> oplist = replay.getOplist();
            initcbs.clear();
            initdemons.clear();
            oplist.clear();

            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = null;
            Document dom = null;

            db = dbf.newDocumentBuilder();
            dom = db.parse(log);

            //初始化
            NodeList brandlist = dom.getElementsByTagName("Character");
            for (int i = 0; i < brandlist.getLength(); i++) {
                //一个character
                Element brandelement = (Element) brandlist.item(i);
                NodeList typelist = brandelement.getChildNodes();

                int tmpIndex = 0, tmpSx = 0, tmpSy = 0, tmpFaction = 0;

                for (int j = 0; j < typelist.getLength(); j++) {
                    Node typenode = typelist.item(j);//一个character中的一个信息
                    if (typelist.item(j) instanceof Element) {
                        Element t = (Element) typenode;
                        if (t.getTagName() == "Faction") tmpFaction = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "Index") tmpIndex = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "Sx") tmpSx = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "Sy") tmpSy = Integer.parseInt(t.getTextContent());

                    }

                }

                if (tmpFaction == 1) initcbs.add(new Point(tmpSx, tmpSy, tmpIndex));
                else initdemons.add(new Point(tmpSx, tmpSy, tmpIndex));
            }


            //加载操作
            NodeList brandlist2 = dom.getElementsByTagName("OneOp");
            for (int i = 0; i < brandlist2.getLength(); i++) {
                //一个OneOp
                Element brandelement = (Element) brandlist2.item(i);
                NodeList typelist = brandelement.getChildNodes();

                int tmpType = 0, tmpDx = 0, tmpDy = 0, tmpIndex = 0, tmpFaction = 0;

                for (int j = 0; j < typelist.getLength(); j++) {
                    Node typenode = typelist.item(j);//一个OneOp中的一个信息
                    if (typelist.item(j) instanceof Element) {
                        Element t = (Element) typenode;
                        if (t.getTagName() == "Faction") tmpFaction = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "OpType") tmpType = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "Index") tmpIndex = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "Dx") tmpDx = Integer.parseInt(t.getTextContent());
                        else if (t.getTagName() == "Dy") tmpDy = Integer.parseInt(t.getTextContent());

                    }

                }

                //利用信息移动图片 1:move 2:battle
                System.out.println("OpType:" + tmpType + "  Faction:" + tmpFaction + "  Index:" + tmpIndex + "  Dx:" + tmpDx + "  Dy:" + tmpDy);
                oplist.add(new Operation(tmpType, tmpDx, tmpDy, tmpFaction, tmpIndex));


            }
            //new Thread(replay).start();

        } catch (Exception e) {
        } finally {
            System.out.println("解析结束");
        }

    }

    public void initUpdateUiThread(){
        updateUiThread = new Thread(updateui);
    }

    public void initReplayThread(){
        replayThread = new Thread(replay);
    }

    public void startReplayThread(){
        replayThread = new Thread(replay);
        replayThread.start();
    }

    //希望这个并发是这样的：葫芦娃1~7以及蝎子精轮流作出操作，操作包括移动（被队友围住就留在原地），或者战斗。
    //一轮操作完成后，更新界面
    public void startThread(){
        try {
            //addChildren();
            cbs.startThread();
            demons.startThread();
            updateUiThread.start();

        }catch (Exception e){}
    }



}
