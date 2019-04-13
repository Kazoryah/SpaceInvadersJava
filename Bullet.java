import java.lang.Math;

public class Bullet
{
    double x;
    double y; //coordinates
    double rotation_rad; // the rotation of the bullet
    int rotation_deg; //bot in radian an degrees to avoid redoing convertion
    int side; //to know the rotation side of the bullet, because each side has
    String png; //bullet image                               //dfferent calculs
    IngameTimer timer;//to know if the player can shoot again
    int launched_by_player; //to know the color of the bullet and at who to give
                            //points if it killed an alien
    int super_bullet;

    //constructor
    public Bullet(double x, double y, int degree, int by_player)
    {
        if (by_player == 1)
            png = "png/laser_first_player.png";
        else
            png = "png/laser_second_player.png";

        //separate case if the vessel is rotated left or right
        //because it is not the same calcul
        if (degree <= 45)
        {
            side = 0;
            rotation_rad = Math.toRadians(degree);
            //50 is the length from the spaceship to the where the bullet pops
            //those are juste math to know from where to launch the bullet
            this.x = x - Math.sin(rotation_rad) * 50.00;
            this.y = y + Math.cos(rotation_rad) * 50.00;
        }
        else
        {
            side = 1;
            rotation_rad = Math.toRadians(360 - degree);
            this.x = x + Math.sin(rotation_rad) * 50.00;
            this.y = y + Math.cos(rotation_rad) * 50.00;
        }

        rotation_deg = degree;
        launched_by_player = by_player;

        //the timer here is to control the fire rate
        timer = new IngameTimer(Wrapper.rate);

        super_bullet = 0;
    }

    //draw bullet
    public void draw()
    {
        StdDraw.picture(x, y, png, rotation_deg);
    }

    //move bullet
    public void move()
    {
        if (side == 1)
            x = x + Math.sin(rotation_rad) * 25;
        else
            x = x - Math.sin(rotation_rad) * 25;

        y = y + Math.cos(rotation_rad) * 25;
    }

    //getter for timer
    public int getTime()
    {
        return timer.getTime();
    }

    //check if the bullet killed an alien
    public int checkKill(Alien[][] aliens, int i, int j, int[] shooter_row,
                         Player player1, Player player2)
    {
        Alien alien = aliens[i][j];
        double aX = alien.getX();
        double aY = alien.getY();

        if ((x - aX) * (x - aX) + (y - aY) * (y - aY) <= 1849) //43x43
        {                                       //I chose 43 because it is the
            int n = 0;                          //alien size on the screen
            if (super_bullet == 1)
            {
                if (j - 1 > -1)
                {
                    if (aliens[i][j - 1] != null &&
                        aliens[i][j - 1].lessenLives() == 0)
                    {
                        killAlien(aliens, i, j - 1, shooter_row, player1,
                                  player2);
                        n++;
                    }
                }
                if (j + 1 < 9)
                {
                    if (aliens[i][j + 1] != null &&
                        aliens[i][j + 1].lessenLives() == 0)
                    {
                        killAlien(aliens, i, j + 1, shooter_row, player1,
                                  player2);
                        n++;
                    }
                }
            }

            if (alien.lessenLives() == 0)
            {
                killAlien(aliens, i, j, shooter_row, player1, player2);
                StdAudio.play("audio/alien_die.wav");
                return ++n;
            }
            else
                return 0; //if the alien wasnt a shooter
                          //if the alien was a shooter but had multiple lives
        }
        return -1;
    }

    public void killAlien(Alien[][] aliens, int i, int j, int[] shooter_row,
                          Player player1, Player player2)
    {
        aliens[i][j] = null;
        if (i + 1 < 4)
            aliens[i + 1][j].shooter();
        increasePoints(i + 1, player1, player2);
        shooter_row[j]++;
    }

    //check is the bullet is out of screen and needs to be destroyed
    public int isOutOfScreen()
    {
        if (x < 0 || x > 1920 || y > 1080)
            return 1;
        return 0;
    }

    //function to increase players' points
    public void increasePoints(int row, Player player1, Player player2)
    {
        if (launched_by_player == 1)
        {
            Wrapper.first_player_points += 10 * row; //points given were chosen
            player1.increaseKills();
        }
        else
        {
            Wrapper.second_player_points += 10 * row;
            player2.increaseKills();
        }
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

    public double getPlayer()
    {
        return launched_by_player;
    }

    public void superBullet()
    {
        png = "png/laser_super.png";
        super_bullet = 1;
    }
}
