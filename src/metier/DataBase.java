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
        }catch(Exception e) {
            document = new Document();
            racine = new Element(pracine);
            document.addContent(racine);
            enregistre();
        }
    }
    static void enregistre() {
        try {
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
        
        while (i.hasNext()) {
            Element courant = (Element) i.next();
            if(id != null && id.equals("0")) return courant;
            String att = courant.getAttributeValue("id");
            if (id != null && id.equals(att)) {
                return courant;
            }
        }
        return null;
    }
    
    static Element getLastGame() {
        List<Element> games = racine.getChildren(game);
        Iterator<Element> i = games.iterator();
        int count = 0;
        Element courant = null;
        while (i.hasNext()) {
            courant = (Element) i.next();
            count++;
        }
        //update game static numberGames
        return courant;
    }
}
