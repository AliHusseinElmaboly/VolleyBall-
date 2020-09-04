/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package test;

/**
 *
 * @author mohamed
 */
public class Score {

    public static final int  maxValue= 7;
    private int value =0;

    public Score( int value)
    {
        this.value= value;
    }

    public void setValue(int value)
    {
        this.value= value;
    }

    public int getValue() {
        return value;
    }

}
