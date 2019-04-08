import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

public class BonusFireRate
{
    double x;
    double y; //coordinates
    IngameTimer still_active; //timer to know if the player still has the bonus
    IngameTimer still_drawn; //to know if the bonus needs to disappear
    int draw; //to know the state of the bonus
    String png = "png/bonus_fire_rate.gif"; //bonus image

    public BonusFireRate()
    {
        x = (double)(int)(Math.random() * 1920);
        y = 140; //chosen

        //if it is outside the range of the spaceship, put it back in range
        if (x > 1800)
            x = 1800;
        if (x < 120)
            x = 120;
        //first timer to know when the bonus needs to disappear
        still_drawn = new IngameTimer(2000);
        still_active = null;
        draw = 1;
    }

    //draw the bonus
    public void draw()
    {
        StdDraw.picture(x, y, png);
    }

    //if the player catch the bonus, this function will be called
    //it starts a knew timer to know when the bonus is finished
    public void startBonus()
    {
        draw = 0;
        still_active = new IngameTimer(2000);
    }

    //getter for timer
    public int stillDrawn()
    {
        return still_drawn.getTime();
    }

    //getter for secodn timer
    public int stillActive()
    {
        if (still_active == null)
            return -1;
        return still_active.getTime();
    }

    //to know if the bonus is created, no if the bonus is to close from the
    //player
    public int stayCreated(Player player)
    {
        double pX = player.getX();

        if (x < pX - 200 || x > pX + 200) //chosen distance
            return 1;
        return 0;
    }

    //check if the player caught the bonus
    public int checkState(Player player)
   {
       //if the bonus is finished after being caught or after being caught
       if (still_active != null && still_active.time == 0)
           return 2; //for sound, to know if the player had the bonus effect
       else if (still_drawn.time == 0 && draw == 1)
           return 0;
       else if (draw == 1) //if it is still drawn, ie not caught by the player
       {                   //it is to avoid the player to be able to catch the
           double pX = player.getX(); //same bonus mulitple times

           if ((x - pX) * (x - pX) < 10000) //100*100 chosen distance, it is the
           {                                //radius of the bonus image
               startBonus();
               return 1;
           }
       }
       return -1;
   }
}
