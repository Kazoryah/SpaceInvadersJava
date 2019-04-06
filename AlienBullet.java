public class AlienBullet
{
    double x;
    double y;
    String png = "png/laser_alien.png";

    public AlienBullet(double X, double Y)
    {
        x = X;
        y = Y;
    }

    public void draw()
    {
        StdDraw.picture(x, y, png);
    }

    public void move()
    {
        y -= 25;
    }

    public void checkKill(Player player)
    {
        double pX = player.getX();
        double pY = player.getY();

        if ((x - pX) * (x - pX) + (y - pY) * (y - pY) <= 2500)
            player.die();
    }

    public int isOutOfScreen()
    {
        if (y < 0)
            return 1;
        return 0;
    }
}
