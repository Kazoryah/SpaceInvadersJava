import java.util.Timer;
import java.util.TimerTask;
import java.lang.Math;

public class BonusLife
{
    double x;
    double y;
    IngameTimer still_drawn;
    IngameTimer still_active;
    int draw;
    String png = "png/spaceship_first.png";

    public BonusLife()
    {
        x = (double)(int)(Math.random() * 1344);
        y = 98;

        if (x > 1260)
            x = 2260;
        if (x < 84)
            x = 84;
        still_drawn = new IngameTimer(2000);
        still_active = null;
        draw = 1;
    }

    public void draw()
    {
        StdDraw.picture(x, y, png);
    }

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
        if ((still_active != null && still_active.time == 0)
            || (still_drawn.time == 0 && draw == 1))
            return 0;
        else if (draw == 1)
        {
            double pX = player.getX();

            if ((x - pX) * (x - pX) < 4900)
            {
                startBonus();
                return 1;
            }
        }
        return -1;
    }
}
