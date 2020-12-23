/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.Attribute;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Toihir
 */
public class DataBase {
    static Document document;
    static Element racine;
    static String pracine = "games";
    static String fichier = "games.xml";
    static String game = "game";
    
    static void initialize() {
        try {
            lireFichier();
            try{
                Game.ids = Integer.parseInt(racine.getChild("numberGames").getText());
            }catch(Exception e){}
        }catch(Exception e) {
            document = new Document();
            racine = new Element(pracine);
            document.addContent(racine);
            enregistre();
        }
    }
    static void enregistre() {
        try {
            racine.getChild("numberGames").setText("" + Game.ids);
            XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
            sortie.output(document, new FileOutputStream(fichier));
        } catch (java.io.IOException e) {}
    }
    static void lireFichier() throws Exception {
        SAXBuilder sxb = new SAXBuilder();
        document = sxb.build(new File(fichier));
        racine = document.getRootElement();
    }
    
    static Element getGame(String id) {
        List<Element> games = racine.getChildren(game);
        Iterator<Element> i = games.iterator();
        Element last = null;
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            if (id != null && id.equals("0")){
                last = courant;
            }
            String att = courant.getAttributeValue("id");
            if (id != null && id.equals(att)) {
                return courant;
            }
        }
        return last;
    }
    
    static List<Move> toGame(Element game){
        try{
            List<Element> nodesElement = game.getChildren("node");
            Iterator<Element> i = nodesElement.iterator();
            
            String n = game.getAttributeValue("id");
            int N = Integer.parseInt(n);
            List<Move> moves = new ArrayList();
            while (i.hasNext()) {
                Element courant = (Element) i.next();
                String x = courant.getChild("iIndex").getText();
                String y = courant.getChild("jIndex").getText();
                String color = courant.getChild("color").getText();
                
                int row = Integer.parseInt(x);
                int col = Integer.parseInt(y);
                
                moves.add(0, new Move(row, col, color));
            }
            return moves;
        }catch(Exception e){
            return null;
        }
    }

    static boolean ajouterGame(List<Move> moves, int id) {
        try {
            String Id = "" + id;
            Element game = getGame(Id);
            if(game != null){
                racine.removeContent(game);
            }
            game = new Element("game");
            Attribute idGame = new Attribute("id", "" + Id);
            game.setAttribute(idGame);
            for(int i = 0; i  < moves.size() ; i++){
                Element move = new Element("node");
                game.addContent(move);
                
                Element x = new Element("iIndex");
                Element y = new Element("jIndex");
                Element color = new Element("color");
                
                move.addContent(x);
                move.addContent(y);
                move.addContent(color);
            }
            racine.addContent(game);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
