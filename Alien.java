import java.lang.Math;

public class Alien
{
    double x;
    double y; //postion on screen
    double start_x;
    double start_y; //both used to restart when player died
    int shooter; //1 if the alien can shoot (first row)
    String pngShield = "png/alien_shield_300.png"; //alien shield image
    String png = "png/alien_300.png"; //alien image
    AlienBullet last_bullet; //alien last bullet, to know if it can shoot
    IngameTimer timer; //to make the aliens stop a bit after they went down
    int lives;
    String clear = "png/alien_shield_clear_purple.png";
    String purple = "png/alien_shield_purple.png";
    String red = "png/alien_shield_red.png";

    //constructor
    public Alien(int i, int j, int extra_lives)
    {
        x = 600 + 115 * j;
        y = 750 + 80 * i; //chosen distances
        start_x = x;
        start_y = y;
        shooter = 0;
        last_bullet = new AlienBullet(0, 0); //init last_bullet to avoid null
        timer = new IngameTimer(1);                               //exception
        lives = 1 + extra_lives;
    }

    //draw alien
    public void draw()
    {
        if (shooter == 0)
            StdDraw.picture(x, y, pngShield);
        else if (lives >= 3)
            StdDraw.picture(x, y, red);
        else if (lives == 2)
            StdDraw.picture(x, y, purple);
        else
            StdDraw.picture(x, y, png);
        StdDraw.text(x,y, Integer.toString(lives));
    }

    //setter to make the alien a shooter one
    public void shooter()
    {
        shooter = 1;
    }

    //move the alien
    public void move(double alien_speed)
    {
        if (timer.time == 0)
        {
            if (Wrapper.moving_right == 1) //if moving right
                x += alien_speed;
            else //if moving left
                x -= alien_speed;
        }
    }

    //move the alien down
    public void moveDown()
    {
        y -= 40; //chosen distance
        timer = new IngameTimer(400); //chosen freeze time
    }

    //check if the alien touched the border
    public void checkBorder()
    {
        if (Wrapper.moving_right == 0 && x < 100) //chosen distance
        {
            Wrapper.moving_right = 1;
            Wrapper.moving_down = 1;
        }
        else if (Wrapper.moving_right == 1 && x > 1820) //chosen distance
        {
            Wrapper.moving_right = 0;
            Wrapper.moving_down = 1;
        }

    }

    //check if aliens reached the bottom of the screen or the barrier level
    public int isTooLow()
    {
        if (y <= 200)
            return 1;
        else if (y <= 280)
            return 2;
        return 0;
    }

    //make the alien shoot
    public void shoot(double difficulty, Player player, AlienBullet[]
                      alien_bullets)
    {
        if (last_bullet.timer.time == 0)
        {
            if (Math.random() < difficulty && x > player.getX() - 300
                && x < player.getX() + 300) //if player is in range
            {
                int i = 0;
                while (i < 50 && alien_bullets[i] != null) //seek place
                    i++;
                if (i < 50)
                {
                    alien_bullets[i] = new AlienBullet(x, y);
                    last_bullet = alien_bullets[i];
                }
            }
        }
    }

    //restart alien variables
    public void restart()
    {
        x = start_x;
        y = start_y;
    }

    //getter for x
    public double getX()
    {
        return x;
    }

    //getter for y
    public double getY()
    {
        return y;
    }

    public int getLives()
    {
        return lives;
    }

    public int lessenLives()
    {
        if (shooter == 1)
        {
            lives--;
            return lives;
        }
        else
            return -1;
    }
}
