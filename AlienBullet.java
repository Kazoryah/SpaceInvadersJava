public class AlienBullet
{
    double x;
    double y;
    String png = "png/laser_alien.png";
    IngameTimer timer;

    public AlienBullet(double x, double y)
    {
        this.x = x;
        this.y = y - 70;
        timer = new IngameTimer(1000);
    }

    public void draw()
    {
        StdDraw.picture(x, y, png);
    }

    public void move()
    {
        y -= 40;
    }

    public int checkKill(Player player)
    {
        double pX = player.getX();
        double pY = player.getY() - 30; //minus the radius of the bullet image

        if ((x - pX) * (x - pX) + (y - pY) * (y - pY) <= 2704) //52x52
        {                                          //chose 52 because it is
            player.die();                          //the player size on screen
            StdAudio.play("audio/died.wav");
            return 1;
        }

        return 0;
    }

    public int isOutOfScreen()
    {
        if (y < 0)
            return 1;
        return 0;
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
