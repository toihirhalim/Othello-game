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
        
        Element gamesElement = racine.getChild("games");
        
        List<Element> games = gamesElement.getChildren("game");
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

    static boolean ajouterGame(Game g) {
        try {
            Element gamesElement = racine.getChild("games");
            String Id = "" + g.id;
            Element game = getGame(Id);
            if(game != null){
                gamesElement.removeContent(game);
            }
            game = new Element("game");
            Attribute idGame = new Attribute("id", "" + Id);
            game.setAttribute(idGame);
            
            Element moves = new Element("moves");
            Element boards = new Element("boards");
            Element parameters = new Element("parameters");
            
            Element playWithComputer = new Element("playWithComputer");
            playWithComputer.setText("" + g.playWithComputer);
            
            Element blackPlayNow = new Element("blackPlayNow");
            blackPlayNow.setText("" + g.blackPlayNow);
            
            Element countDowns = new Element("countDowns");
            
            Element blackCountDown = new Element("blackCountDown");
            Element whiteCountDown = new Element("whiteCountDown");
            
            Element blackMin = new Element("blackMin");
            Element blackSec = new Element("blackSec");
            Element whiteMin = new Element("whiteMin");
            Element whiteSec = new Element("whiteSec");
            
            blackMin.setText("" + g.getBlackPlayerTimer().getMin());
            blackSec.setText("" + g.getBlackPlayerTimer().getSec());
            whiteMin.setText("" + g.getWhitePlayerTimer().getMin());
            whiteSec.setText("" + g.getWhitePlayerTimer().getSec());

            blackCountDown.addContent(blackMin);
            blackCountDown.addContent(blackSec);
            whiteCountDown.addContent(whiteMin);
            whiteCountDown.addContent(whiteSec);

            countDowns.addContent(blackCountDown);
            countDowns.addContent(whiteCountDown);

            
            Element players = new Element("players");
            
            
            Element blackPlayer = new Element("blackPlayer");
            Element blackPlayerName = new Element("blackPlayerName");
            blackPlayerName.setText(g.blackPlayer.getName());
            Element blackPlayerScore = new Element("blackPlayerScore");
            blackPlayerScore.setText("" + g.blackPlayer.getScore());
            
            Element whitePlayer = new Element("whitePlayer");
            Element whitePlayerName = new Element("whitePlayerName");
            whitePlayerName.setText(g.whitePlayer.getName());
            Element whitePlayerScore = new Element("whitePlayerScore");
            whitePlayerScore.setText("" + g.whitePlayer.getScore());
            
            blackPlayer.addContent(blackPlayerName);
            blackPlayer.addContent(blackPlayerScore);
            whitePlayer.addContent(whitePlayerName);
            whitePlayer.addContent(whitePlayerScore);
            
            players.addContent(blackPlayer);
            players.addContent(whitePlayer);
            
            parameters.addContent(playWithComputer);
            parameters.addContent(blackPlayNow);
            parameters.addContent(players);
            parameters.addContent(countDowns);
            
            int id = 1;
            for(Move m : g.moves){
                Element move = new Element("move");
                
                Attribute idAttribute = new Attribute("id", "" + id++);
                
                Element x = new Element("iIndex");
                x.setText("" + m.i);
                Element y = new Element("jIndex");
                y.setText("" + m.j);
                Element color = new Element("color");
                color.setText(m.color);
                
                move.setAttribute(idAttribute);
                move.addContent(x);
                move.addContent(y);
                move.addContent(color);
                
                moves.addContent(move);
            }
            id = 0;
            for(String b : g.boards){
                Element board = new Element("board");
                
                Attribute idAttribute = new Attribute("id", "" + id++);
                
                board.setText(b);
                board.setAttribute(idAttribute);
                
                boards.addContent(board);
            }
            
            game.addContent(parameters);
            game.addContent(moves);
            game.addContent(boards);
            
            gamesElement.addContent(game);
            
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    static List<GameItem> listGames(){
        try{
            List<GameItem> gameItems = new ArrayList();

            Element gamesElement = racine.getChild("games");

            List<Element> games = gamesElement.getChildren("game");
            Iterator<Element> i = games.iterator();

            while (i.hasNext()) {
                Element courant = (Element) i.next();
                int gameId = Integer.parseInt(courant.getAttributeValue("id"));
                
                Element blackPlayer = courant.getChild("parameters").getChild("players").getChild("blackPlayer");
                Element whitePlayer = courant.getChild("parameters").getChild("players").getChild("whitePlayer");
                
                
                String blackPlayerName = blackPlayer.getChild("blackPlayerName").getText();
                String whitePlayerName = whitePlayer.getChild("whitePlayerName").getText();
                
                
                int blackPlayerScore = Integer.parseInt(blackPlayer.getChild("blackPlayerScore").getText());
                int whitePlayerScore  = Integer.parseInt(whitePlayer.getChild("whitePlayerScore").getText());
                
                
                gameItems.add(new GameItem(gameId, blackPlayerName, whitePlayerName, blackPlayerScore, whitePlayerScore));
            }
            
            return gameItems;
        }catch(Exception e){}
        return null;
    }
    
    static Game xmlToGame(Element gameElement){
        Game game = new Game();
        
        try{
            
            //parameters
            
            String id = gameElement.getAttributeValue("id");
            
            game.id = Integer.parseInt(id);
            
            game.playWithComputer = gameElement.getChild("parameters").getChild("playWithComputer").getText().equals("true");
            game.blackPlayNow = gameElement.getChild("parameters").getChild("blackPlayNow").getText().equals("true");
            
            game.blackPlayerTimer = new GameTimer(
                    Integer.parseInt(gameElement.getChild("parameters").getChild("countDowns").getChild("blackCountDown").getChild("blackMin").getText()),
                    Integer.parseInt(gameElement.getChild("parameters").getChild("countDowns").getChild("blackCountDown").getChild("blackSec").getText())
            );
            game.whitePlayerTimer = new GameTimer(
                    Integer.parseInt(gameElement.getChild("parameters").getChild("countDowns").getChild("whiteCountDown").getChild("whiteMin").getText()),
                    Integer.parseInt(gameElement.getChild("parameters").getChild("countDowns").getChild("whiteCountDown").getChild("whiteSec").getText())
            );
            
            String name = gameElement.getChild("parameters").getChild("players").getChild("blackPlayer").getChild("blackPlayerName").getText();
            String scr = gameElement.getChild("parameters").getChild("players").getChild("blackPlayer").getChild("blackPlayerScore").getText();
            int score = Integer.parseInt(scr);
            
            game.blackPlayer = new Player(name, "b", score);
            
            
            name = gameElement.getChild("parameters").getChild("players").getChild("whitePlayer").getChild("whitePlayerName").getText();
            scr = gameElement.getChild("parameters").getChild("players").getChild("whitePlayer").getChild("whitePlayerScore").getText();
            score = Integer.parseInt(scr);
            
            game.whitePlayer = new Player(name, "w", score);
            
            //moves
            
            List<Element> movesElement = gameElement.getChild("moves").getChildren("move");
            Iterator<Element> i = movesElement.iterator();
            
            List<Move> moves = new ArrayList();
            
            while (i.hasNext()) {
                Element courant = (Element) i.next();
                
                String x = courant.getChild("iIndex").getText();
                String y = courant.getChild("jIndex").getText();
                String color = courant.getChild("color").getText();
                
                int row = Integer.parseInt(x);
                int col = Integer.parseInt(y);
                
                moves.add(new Move(row, col, color));
            }
            
            game.moves = moves;
            if(moves.size() < 0) game.lastMove = moves.get(moves.size() -1);
            
            //boards
            List<Element> boardsElement = gameElement.getChild("boards").getChildren("board");
            i = boardsElement.iterator();
            
            List<String> boards = new ArrayList();
            
            while (i.hasNext()) {
                Element courant = (Element) i.next();
                
                String board = courant.getText();
                
                boards.add(board);
                
            }
            
            game.boards = boards;
            game.board = game.stringToBoard(boards.get(boards.size() - 1));
            
        }catch(Exception e){
            return new Game();
        }
        
        return game;
    }
    
    public static boolean saveGame(Game game){
        if(racine == null) initialize();
        ajouterGame(game);
        enregistre();
        return true;
    }
    public static List<GameItem> listAllGames(){
        if(racine == null) initialize();
        
        return listGames();
    }
    
    public static Game getGame(int id){
        if(racine == null) initialize();
        
        Element game = getGame("" + id);
        
        if(game != null){
            return xmlToGame(game);
        }
        
        return null;
    }
    
    public static Game getLastGame(){
        if(racine == null) initialize();
        
        Element game = getGame("0");
        
        if(game != null){
            return xmlToGame(game);
        }
        
        return null;
    }
   
}
