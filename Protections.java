public class Protections
{
    String red1 = "png/barrier_red_1.png";
    String red2 = "png/barrier_red_2.png";

    String png1;
    String png2;
    IngameTimer timer;
    int state;
    double x;

    int lives;

    public Protections(int position, int color)
    {
        if (position == 1)
            x = 640;
        else
            x = 1280;

        if (color == 1)
        {
            png1 = "png/barrier_pink_1.png";
            png2 = "png/barrier_pink_2.png";
        }
        else
        {
            png1 = "png/barrier_blue_1.png";
            png2 = "png/barrier_blue_2.png";
        }

        timer = new IngameTimer(50);
        state = 0;
        lives = 15;
    }

    public void draw()
    {
        if (state == 0)
            StdDraw.picture(x, 250, png1);
        else
            StdDraw.picture(x, 250, png2);

        if (timer.time == 0)
        {
            state = (state + 1) % 2;
            timer = new IngameTimer(50);
        }
    }

    public void checkColor()
    {
        if (lives <= 5)
        {
            png1 = red1;
            png2 = red2;
        }
    }

    public int getLives()
    {
        return lives;
    }

    public int hasTouched(AlienBullet bullet)
    {
        if (bullet.getY() <= 260)
        {
            double bX = bullet.getX();
            if (bX >= x - 100 && bX <= x + 120) //chosen area, by testing
            {
                lives--;
                return 1;
            }
        }
        return -1;
    }

    public int hasTouched(Bullet bullet)
    {
        if (bullet.getY() >= 230)
        {
            double bX = bullet.getX();
            if (bX >= x - 100 && bX <= x + 120) //chosen area, by testing
            {
                lives--;
                return 1;
            }
        }
        return -1;
    }
}
