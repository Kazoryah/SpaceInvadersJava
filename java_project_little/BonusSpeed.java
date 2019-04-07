import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

public class BonusSpeed
{
    double x;
    double y;
    IngameTimer still_active;
    IngameTimer still_drawn;
    int draw;
    String png = "png/bonus_speed.png";

    public BonusSpeed()
    {
        x = (double)(int)(Math.random() * 1344);
        y = 98;

        //if it is outside the range of the spaceship, put it back in range
        if (x > 1260)
            x = 1260;
        if (x < 84)
            x = 84;
        //first timer to know when the bonus needs to disappear
        still_drawn = new IngameTimer(2000);
        still_active = null;
        draw = 1;
    }

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

    public int stillDrawn()
    {
        return still_drawn.time;
    }

    public int stillActive()
    {
        if (still_active == null)
            return -1;
        return still_active.time;
    }

    public int stayCreated(Player player)
    {
        double pX = player.getX();

        if (x < pX - 140 || x > pX + 140)
            return 1;
        return 0;
    }

    public int checkState(Player player)
    {
        if (still_active != null && still_active.time == 0 )
            return 2;
        else if (still_drawn.time == 0 && draw == 1)
            return 0;
        else if (draw == 1)
        {
            double pX = player.getX();

            if ((x - pX) * (x - pX) < 10000)
            {
                startBonus();
                return 1;
            }
        }
        return -1;
    }
}
