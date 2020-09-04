/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.microedition.lcdui.Image;

/**
 *
 * @author mohamed
 */
public class Resources {
    public final static int playerWidth = 64;
    public final static int playerHeight = 64;
    
    public final static int ballWidth = 16;
    public final static int ballHeight = 16;

    public final static int backgroundWidth = 320;
    public final static int backgroundHeight = 320;
    public final static int groundY = 150;

    public final static String Left = "Left";
    public final static String Right = "Right";
    public final static String None = "None";

    //game states
    public final static String Win = "Win";
    public final static String Lose = "Lose";
    public final static String Play = "Play";
    public final static String Pause = "Pause";

    public final static String Ready ="Ready !";
    public final static String GameOver ="Game Over  :(";
    public final static String Winner ="You Are The Winner :)";
    
    
    public static String CurrentState = Pause;


}
