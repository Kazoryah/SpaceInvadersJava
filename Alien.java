import java.lang.Math;

public class Alien
{
    double x;
    double y;
    double start_x;
    double start_y;
    int shooter;
    String pngShield = "png/alien_shield_300.png";
    String png = "png/alien_300.png";
    AlienBullet last_bullet;
    IngameTimer timer;

    public Alien(int i, int j)
    {
        x = 600 + 100 * j;
        y = 800 + 80 * i;
        start_x = x;
        start_y = y;
        shooter = 0;
        last_bullet = new AlienBullet(0, 0);
        timer = new IngameTimer(1);
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
        if (timer.time == 0)
        {
            if (Wrapper.moving_right == 1)
                x += alien_speed;
            else
                x -= alien_speed;
        }
    }

    public void moveDown()
    {
        y -= 25;
        timer = new IngameTimer(400);
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
        if (last_bullet.timer.time == 0)
        {
            if (Math.random() < difficulty && x > player.getX() - 300
                && x < player.getX() + 300)
            {
                int i = 0;
                while (i < 50 && alien_bullets[i] != null)
                    i++;
                if (i < 50)
                {
                    alien_bullets[i] = new AlienBullet(x, y);
                    last_bullet = alien_bullets[i];
                }
            }
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
