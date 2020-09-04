/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author mohamed
 */
import java.io.IOException;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class VolleyBallGame extends GameCanvas implements Runnable{
    
    private boolean isPlay; // Game Loop runs when isPlay is true
    private int width; // To hold screen width
    private int height; // To hold screen height
    private int backgroundWidth; // To hold backgound width
    private int backgroundHeight; // To hold background height
   

    // Sprites to be used
    private Sprite backgroundSprite;


    // game objects
    private Character player;
    private Character computer;
    private Ball ball;
    
    //To hold screen starting viewpoint
    private int scnX;

    // Layer Manager
    private LayerManager layerManager;

    // game delay
    private long delay; // To give thread consistency
    private long lifeTime; // To  how long do we live for
    private long startTime;
    private long currentTime;
    private byte turn;

    private String gameState;

    // Constructor and initialization
    public VolleyBallGame() throws  Exception
    {
        super(true);
        width = getWidth();
        height = getHeight();
        
        scnX =  width/4;

        // set size of the background
        backgroundWidth = Resources.backgroundWidth;
        backgroundHeight = Resources.backgroundHeight;

        
        //manage delay
        delay = 30;
        startTime = System.currentTimeMillis();
        currentTime = startTime;
        lifeTime = 300;

        turn =1;

        //Load game Images
        Image playerImage = Image.createImage("images/player.png");
        Image computerImage = Image.createImage("images/enemy.png");
        Image ballImage = Image.createImage("images/Ball.png");
        Image backgroundImage = Image.createImage("images/Background.png");


        //initiale game Objects
        player = new Character( playerImage , 0 , backgroundWidth/2 , 70 , 3 ,5 );
        computer = new Character(computerImage ,backgroundWidth/2 ,backgroundWidth ,backgroundWidth - 100 ,0,3);
        ball = new Ball(ballImage ,-50 , backgroundWidth +50,4 ,90 , 150);
        backgroundSprite = new Sprite(backgroundImage);

        layerManager = new LayerManager();
        //append game  object in layerManager
        layerManager.append(ball.objSprite);
        layerManager.append(player.objSprite);
        layerManager.append(computer.objSprite);
        layerManager.append(backgroundSprite);

        gameState = Resources.Ready;

    }

    // Automatically start thread for game loop
    public void start()
    {
        isPlay = true;
        Thread t = new Thread(this);
        t.start();
    }

    public void stop()
    {
        isPlay = false;
    }
    
    // Main Game Loop
    public void run()
    {
        Graphics g = getGraphics();
        while (isPlay == true)
        {
            if(Resources.CurrentState ==Resources.Play)
            {
                input();
                update();
               
            }
            else if(    Resources.CurrentState == Resources.Pause  ||
                        Resources.CurrentState ==  Resources.Win  ||
                        Resources.CurrentState ==  Resources.Lose )

            {
                int keyStates = getKeyStates();
                
                //enter
                if ((keyStates & FIRE_PRESSED) != 0)
                {
                    Resources.CurrentState = Resources.Play;
                    gameState = "";
                }
            }

            drawScreen(g);
          
            
        try { 
                Thread.sleep(delay);
            }
        catch (InterruptedException ie) {}

        }
    }
    
    // Method to update the gameplay
    private void update()
    {
	currentTime =System.currentTimeMillis();
        if(currentTime - startTime  >= lifeTime)
        {
            startTime = System.currentTimeMillis();
            
            player.objSprite.nextFrame();
            computer.objSprite.nextFrame();

        }
        player.update();
        computer.update();
        ball.update();
        computer.AIUpdate(ball.x , ball.y);

        checkCollision();
        
        updateCamera();


    }


    public void checkCollision()
    {
        if(player.objSprite.collidesWith(ball.objSprite,false) && turn == 0 )
        {
            ball.currentDirect = Resources.Right;
       
            turn =1;
            ball.ground = player.y;
            
            if(!player.isJumping)
            {

                 if(player.currentAnim == 0 || player.currentAnim == 2)
                 {
                     player.setSequence(2);
                     ball.speed = 5;
                     ball.gravity = -1;
                 }
                 else if(player.currentAnim == 1 )
                     ball.speed = 3;
                    ball.gravity = -1;
            }
            else
            {
                 ball.speed = 8;
                 ball.gravity = -2;
            }

            
        }
        else if(computer.objSprite.collidesWith(ball.objSprite, false) && turn == 1)
        {
            ball.currentDirect = Resources.Left;


            turn =0;
            ball.ground = computer.y;

             if(!computer.isJumping)
             {
                 if(computer.currentAnim == 0 || computer.currentAnim == 2)
                 {
                     computer.setSequence(2);
                     ball.speed = 5;
                 }
                 else if(computer.currentAnim == 1 )      
                     ball.speed = 3;
            }
            else
                ball.speed = 4;
        }
        else
        {
            ball.ground =  Resources.groundY+70;
            if( Resources.CurrentState ==  Resources.Win )
            {
                player.increaseScore();
                if (player.getScore() == Score.maxValue)
                {
                    //player is the Winner
                    gameState = Resources.Winner;
                }
                 reset(90 , 150 , Resources.Right , 3 , 0 , 1);
               
            }
            else if( Resources.CurrentState ==  Resources.Lose )
            {
               computer.increaseScore();
                if (computer.getScore() == Score.maxValue)
                {
                    //computer is the Winner
                    gameState = Resources.GameOver;
                }
               reset(250 , 150 , Resources.Left , 0 , 3 ,0);
               
            }
        }
    }
    
    // update The camera position regards to the ball
    public void updateCamera()
    {
        String direct = ball.getDirect();

        if( direct.equals(Resources.Left))
        {
             scnX = Math.max(0, scnX - 2);
        }
        else if( direct.equals(Resources.Right))
        {
            
                    scnX = Math.min(backgroundWidth/4,  scnX  + 2);

                
        }
    }
    
    // Method to Handle User Inputs
    private void input() {
        int keyStates = getKeyStates();


            // Up
            if ((keyStates & UP_PRESSED) != 0 && !player.isJumping)
            {
              player.setSequence(5);
              player.isJumping = true;
              player.velocityY = 16;
              
            }

            // Left
            if ((keyStates & LEFT_PRESSED) != 0)
            {
               player.setSequence(6);
               player.currentDirect =  Resources.Left;
            }
            // Right
            else if ((keyStates & RIGHT_PRESSED) !=0 )
            {   
                player.setSequence(6);
                player.currentDirect =  Resources.Right;
            }
            // Down
            else if ((keyStates & DOWN_PRESSED) !=0)
            {
              player.setSequence(1);
            }
            else if(!player.isJumping)
            {   
               player.currentDirect =  Resources.None;

              player.setSequence(0);
            }
        
        

    }



    // Method to Reset the game
    private void  reset( int ballx, int bally , String Direct , int playerSeq, int computerSeq , int gameTurn)
    {
        player.setSequence(playerSeq);
        computer.setSequence(computerSeq);
        ball.x = ballx;
        ball.y = bally;
        ball.currentDirect = Direct;
        ball.speed =4;
        ball.gravity = -1;

        turn = (byte) gameTurn;

        //reset characters positions
        player.x = 70;
        computer.x = backgroundWidth - 100;

        scnX =  width/4;
        ball.velocityY = 17;
        if( gameState == Resources.Winner || gameState == Resources.GameOver )
        {
            player.setScore(0);
            computer.setScore(0);
        }
        else
            gameState = Resources.Ready;
        
    }

    //Metthod to repaint the Background
    private void createBackground(Graphics g)
    {
        g.setColor(0x000000);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    
    //Method to Display The UI
    private void drawUI(Graphics g){

        // draw the result of the game
        g.drawString(player.getScore() + "", width/2 - scnX+10, height *5/6,Graphics.TOP|Graphics.LEFT);
        g.drawString(computer.getScore() + "",width/2 - scnX+125, height *5/6,Graphics.TOP|Graphics.LEFT);

        // draw the game state
        g.drawString(gameState,width/2, height/2,Graphics.TOP|Graphics.HCENTER);

    }

    // Method to Display Graphics
    private void drawScreen(Graphics g)
    {
        createBackground(g);
        g.setColor(0x0000ff);

        // display gameObjects
        player.draw();
        computer.draw();
        ball.draw();  
        
        // display all layers
        layerManager.setViewWindow(scnX ,0,width,height);
        layerManager.paint(g,0,0);
        
        //display The UI
        drawUI(g);
        
        flushGraphics();
    }


}
