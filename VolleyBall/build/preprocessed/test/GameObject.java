/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author mohamed
 */
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.Image;

public abstract class GameObject {
    public Sprite objSprite;
    protected  int width ,height ;// To hold size of the gameObject
    public int x,y;// To hold current position of the gameObject
    public int speed; //to holde the speed of the gameObject

    public String currentDirect;

    protected int boundLeft, boundRight;

    protected int gravity;
    public int velocityY;
    public boolean isJumping;

    public int ground;

    /*
     @@ objImage --- object image
     @@ boundLeft , boundRight --- left and right boundries
     @@ speed --- object speed in x direction
     @@ x , y --- postion of the gameObject
     @@ width , height --- size of the gameobject
     @@ Direct --- gameobject direction left or right
     @@ gravity , velocityY , isJumping --- game physics in y direction
     @@ ground -- the position of the ground
     *
     */

    public GameObject(Image objImage , int boundLeft , int boundRight , int speed ,int x , int y , int width , int height , String Direct , int gravity , int velocityY,boolean isJumping ,int ground) throws Exception
    {
        this.width = width;
        this.height = height;

        objSprite = new Sprite (objImage ,this.width , this.height);

        this.speed = speed;

        this.x = x;
        this.y = y;

        currentDirect = Direct;

        this.boundLeft = boundLeft;
        this.boundRight = boundRight;

        this.gravity = gravity;
        this.velocityY = velocityY;
        this.isJumping = isJumping;
        
        this.ground = ground;
    }


    public void check_Left_Right()
    {
        if(currentDirect.equals(Resources.Right))
        {
            x = Math.min( boundRight- width/2, x + speed);
        }
        else if(currentDirect.equals(Resources.Left))
        {
            x = Math.max(boundLeft - width/2 , x - speed);
        }
    }
    
    public String getDirect()
    {
        return currentDirect;
    }
    
    public abstract  void Action();

    public void update()
    {
        check_Left_Right();
        if(isJumping)
        {

            if(y - velocityY <= ground)
            {
                y -= velocityY;
                velocityY +=gravity;

            }
            else
                Action();
        }


    }
    
    public void draw()
    {
        objSprite.setPosition(x,y);
    }


}
