import java.lang.Math;

public class Bullet
{
    double x;
    double y;
    double rotation_rad;
    int rotation_deg;
    int side;
    String png;
    IngameTimer timer;
    int launched_by_player;

    public Bullet(double X, double Y, int degree, int by_player)
    {
        if (by_player == 1)
            png = "png/laserFirst.png";
        else
            png = "png/laserSecond.png";

        //separate case if the vessel is rotated left or right
        //because it is not the same calcul
        if (degree <= 45)
        {
            side = 0;
            rotation_rad = Math.toRadians(degree);
            //100 is the length of the spaceship
            //those are juste math to know from where to launch the bullet
            x = X - Math.sin(rotation_rad) * 60.00;
            y = Y + Math.cos(rotation_rad) * 60.00;
        }
        else
        {
            side = 1;
            rotation_rad = Math.toRadians(360 - degree);
            x = X + Math.sin(rotation_rad) * 60.00;
            y = Y + Math.cos(rotation_rad) * 60.00;
        }
        rotation_deg = degree;
        launched_by_player = by_player;

        //the timer here is to control the fire rate
        timer = new IngameTimer(Wrapper.rate);
    }

    public void draw()
    {
        StdDraw.picture(x, y, png, rotation_deg);
    }

    public void move()
    {
        if (side == 1)
            x = x + Math.sin(rotation_rad) * 15;
        else
            x = x - Math.sin(rotation_rad) * 15;

        y = y + Math.cos(rotation_rad) * 15;
    }

    public int getTime()
    {
        return timer.time;
    }

    public int checkKill(Alien alien)
    {
        double aX = alien.getX();
        double aY = alien.getY();

        if ((x - aX) * (x - aX) + (y - aY) * (y - aY) <= 1500)
            return 1;
        return 0;
    }

    public int isOutOfScreen()
    {
        if (x < 0 || x > 1152 || y > 648)
            return 1;
        return 0;
    }

    public void increasePoints(int row)
    {
        if (launched_by_player == 1)
            Wrapper.first_player_points += 10 * row;
        else
            Wrapper.second_player_points += 10 * row;
    }
}
