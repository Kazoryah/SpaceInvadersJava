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
        x = (double)(int)(Math.random() * 1920);
        y = 140;

        if (x > 1800)
            x = 1800;
        if (x < 120)
            x = 120;

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

        if (x < pX - 200 || x > pX + 200)
            return 1;
        return 0;
    }

    public int checkState(Player player)
    {
        if (still_active != null && still_active.time == 0)
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

    public void pause()
    {
        if (draw == 1)
            still_drawn.pause();
        else
            still_active.pause();
    }

    public void resume()
    {
        if (draw == 1)
            still_drawn.resume();
        else
            still_active.resume();
    }

    public void drawRectangle()
    {
        double n = 0.05 * (2000 - still_active.getN());
        StdDraw.filledRectangle(1890, 50 + n, 10, n);
    }
}
