/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.*;

/**
 * @author mohamed
 */
public class MainMidlet extends MIDlet {
    
    private Display display;
    
    public void startApp()
    {
        try
        {
            display = Display.getDisplay(this);
            VolleyBallGame gameCanvas = new VolleyBallGame();
            gameCanvas.start();
            display.setCurrent(gameCanvas);
        } catch (Exception ex)
        {
            System.out.println(ex);
        }
    }
    public Display getDisplay() {
    return display;
    }
    public void pauseApp() {
    }

    public void destroyApp(boolean unconditional)
    {
        exit();
    }

    public void exit()
    {
        System.gc();
        destroyApp(false);
        notifyDestroyed();
    }
}
