/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vt.cs5244;

/**
 *
 * @author bethanyfahey
 */
public class Util {

    protected static Edge parseEdge(String theEdge) {

        Edge thisEdge = null;

        switch (theEdge) {
            case "TOP": {
                thisEdge = Edge.TOP;
                break;
            }

            case "BOTTOM": {
                thisEdge = Edge.BOTTOM;
                break;
            }

            case "LEFT": {
                thisEdge = Edge.LEFT;
                break;
            }

            case "RIGHT": {
                thisEdge = Edge.RIGHT;
                break;
            }
            default: {
                throw new DABException();
            }
        }

        return thisEdge;
    }

    protected static String parsePlayer(Player player) {
        
        String myPlayer = "";
        
        if(player == Player.ONE){
            myPlayer = "ONE";
        }else if(player == Player.TWO){
            myPlayer = "TWO";
        }else if(player == null){
            myPlayer = "Game Over!!";
        }
    
        return myPlayer;
    }
}
