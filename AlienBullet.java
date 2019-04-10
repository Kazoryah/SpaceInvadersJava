public class AlienBullet
{
    double x;
    double y; //coordinates on screen
    String png = "png/laser_alien.png"; //alienbullet image
    IngameTimer timer; //timer used to know if the alien can shoot again
                       //it could have been put in the alien class too, it is
                       //a choice

    //constructor
    public AlienBullet(double x, double y)
    {
        this.x = x;
        this.y = y - 70; //x and y are the alien's position, so -70 to make the
        timer = new IngameTimer(1000); //bullet appear in front of the alien not
                                       //on it
    }

    //draw alienbullet
    public void draw()
    {
        StdDraw.picture(x, y, png);
    }

    //move alienbullet
    public void move()
    {
        y -= 40; //chosen speed
    }

    //check if the bullet touched the player
    public int checkKill(Player player)
    {
        double pX = player.getX();
        double pY = player.getY() - 30; //minus the radius of the bullet image

        if ((x - pX) * (x - pX) + (y - pY) * (y - pY) <= 2704) //52x52
        {                                          //chose 52 because it is
                                                   //the player size on screen
            if (Wrapper.is_bonus != 4
                || Wrapper.bonus_shield.stillActive() != 1)
            {
                //player.die();
                StdAudio.play("audio/died.wav"); //play a sound if player dies
            }
            return 1; //return 1 if it touched the player
        }

        return 0; //else return 0
    }

    //check if the bullet is out of the screen and needs to be destroyed
    public int isOutOfScreen()
    {
        if (y < 0)
            return 1;
        return 0;
    }

    //getter for x
    public double getX()
    {
        return x;
    }

    //getter fot y
    public double getY()
    {
        return y;
    }
}
