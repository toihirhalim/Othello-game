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
    static String pracine = "Othello";
    static String fichier = "Othello.xml";
    
    public static void initialize() {
        try {
            lireFichier();
            try{
                Game.ids = Integer.parseInt(racine.getChild("serialId").getText());
            }catch(Exception e){}
        }catch(Exception e) {
            document = new Document();
            racine = new Element(pracine);
            racine.addContent(new Element("serialId"));
            racine.addContent(new Element("games"));
            document.addContent(racine);
            enregistre();
        }
    }
    static void enregistre() {
        try {
            racine.getChild("serialId").setText("" + Game.ids);
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
        Element GamesElement = racine.getChild("games");
        List<Element> games = GamesElement.getChildren("games");
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

    public static boolean ajouterGame(Game g) {
        try {
            String Id = "" + g.id;
            Element game = getGame(Id);
            if(game != null){
                racine.getChild("games").removeContent(game);
            }
            game = new Element("game");
            Attribute idGame = new Attribute("id", "" + Id);
            game.setAttribute(idGame);
            
            Element moves = new Element("moves");
            Element boards = new Element("boards");
            Element parameters = new Element("parameters");
            
            parameters.addContent(new Element("playWithComputer", "" + g.playWithComputer));
            parameters.addContent(new Element("blackPlayNow", "" + g.blackPlayNow));
            Element players = new Element("players");
            
            
            Element blackPlayer = new Element("blackPlayer");
            Element whitePlayer = new Element("whitePlayer");
            
            blackPlayer.addContent(new Element("name", g.blackPlayer.getName()));
            blackPlayer.addContent(new Element("name", "" + g.blackPlayer.getScore()));
            
            
            whitePlayer.addContent(new Element("name", g.whitePlayer.getName()));
            whitePlayer.addContent(new Element("name", "" + g.whitePlayer.getScore()));
            
            players.addContent(blackPlayer);
            players.addContent(whitePlayer);
            
            /*int id = 1;
            for(Move m : g.moves){
                Element move = new Element("move");
                
                Attribute idAttribute = new Attribute("id", "" + id++);
                
                Element x = new Element("iIndex", "" + m.i);
                Element y = new Element("jIndex", "" + m.j);
                Element color = new Element("color", m.color);
                
                move.setAttribute(idAttribute);
                move.addContent(x);
                move.addContent(y);
                move.addContent(color);
                
                moves.addContent(move);
            }
            id = 1;
            for(String b : g.boards){
                Element board = new Element("board");
                
                Attribute idAttribute = new Attribute("id", "" + id++);
                
                board.setText(b);
                board.setAttribute(idAttribute);
                
                boards.addContent(board);
            }*/
            
            game.addContent(parameters);
            game.addContent(moves);
            game.addContent(boards);
            
            racine.getChild("games").addContent(game);
            enregistre();
        } catch (Exception e) {
            return false;
        }
        return true;
    }


}
