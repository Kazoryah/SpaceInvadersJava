import java.lang.Math;

public class Alien
{
    double x;
    double y;
    double start_x;
    double start_y;
    int shooter;
    String pngShield = "png/alienShield.png";
    String png = "png/alien.png";

    public Alien(int i, int j)
    {
        x = 480 + 130 * j;
        y = 700 + 100 * i;
        start_x = x;
        start_y = y;
        shooter = 0;
    }

    public void draw()
    {
        if (this.shooter == 0)
            StdDraw.picture(x, y, pngShield);
        else
            StdDraw.picture(x, y, png);
    }

    public void shooter()
    {
        shooter = 1;
    }

    public void move(int alien_speed)
    {
        if (Wrapper.moving_right == 1)
            x += alien_speed;
        else
            x -= alien_speed;
    }

    public void checkBorder()
    {
        if (Wrapper.moving_right == 0 && x < 100)
        {
            Wrapper.moving_right = 1;
            Wrapper.moving_down = 1;
        }
        else if (Wrapper.moving_right == 1 && x > 1820)
        {
            Wrapper.moving_right = 0;
            Wrapper.moving_down = 1;
        }

    }

    public int isTooLow()
    {
        if (y <= 240)
            return 1;
        else
            return 0;
    }

    public void shoot(double difficulty, Player player, AlienBullet[]
                      alien_bullets)
    {
        if (Math.random() < difficulty && x > player.getX() - 300
            && x < player.getX() + 300)
        {
            int i = 0;
            while (i < 50 && alien_bullets[i] != null)
                i++;
            if (i < 50)
                alien_bullets[i] = new AlienBullet(x, y);
        }
    }

    public void restart()
    {
        x = start_x;
        y = start_y;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }
}