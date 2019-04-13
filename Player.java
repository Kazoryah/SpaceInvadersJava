public class Player
{
    double x; //position x axis
    double y; //position y axis
    double start_x; //x position at start, used for restart
    int rotation; //degree of rotation (left/right)
    int is_alive; //to know if the player is alive (1 yes)
    int player; //to if the player is the first player or the second
    String png; //image of the player
    String shield = "png/spaceship_shield_250.png"; //image of the player shield
    double dx; //coefficient of acceleration
    double decreasing; //friction coefficent
    double speed; //speed of the player
    int blocked; //used for the bounce, to know if the player reached the border
    Bullet last_bullet; //to know if the player can shoot (rate)
    IngameTimer has_waited_enough;
    int is_shielded;

    //constructor
    public Player(double x, double y, int player)
    {
        this.x = x;
        this.y = y;
        start_x = x;
        this.player = player;
        rotation = 0;
        is_alive = 1;
        if (player == 1) //player 1 and 2 have different spaceships
            png = "png/spaceship_first_250.png";
        else
            png = "png/spaceship_second_250.png";
        dx = 2.5; //chosen coeff
        decreasing = 1; //chosen coeff
        speed = 0;
        blocked = 0;
        last_bullet = new Bullet(0,0,0, player); //to avoid null exception
        has_waited_enough = new IngameTimer(5000);
        is_shielded = 0;
    }

    //draw the vessel
    public void draw()
    {
        StdDraw.picture(x, y, png, rotation);
    }

    //used to draw shield
    public void drawShield()
    {
        StdDraw.picture(x, y, shield, rotation);
    }

    //the degree of rotation is into [0;360[
    //start at the top of the spaceship, trigonometric sense
    //the max rotation is 45 degrees left and right (chosen)
    private void rightRotation()
    {
        if (rotation == 0)
            rotation = 355;
        else if (rotation <= 45 || rotation > 315)
            rotation -= 5;
    }

    private void leftRotation()
    {
        if (rotation < 45 || rotation >= 315)
            rotation = (rotation + 5) % 360;
    }

    //if the player can move, move (blocked is changed to 1 by a function below
    private void moveRight(int speed_bonus)
    {
        if (blocked == 0)
        {
            if (speed < 15 + speed_bonus) //chosen max speed
                speed += dx;
            x += speed;
        }
    }

    //this is called overloading when two functions are called the same but
    //do not have same parameters. The one below is used when there is no bonus
    //in the level
    private void moveRight()
    {
        if (blocked == 0)
        {
            if (speed < 15)
                speed += dx;
            x += speed;
        }
    }

    public void moveLeft(int speed_bonus)
    {
        if (blocked == 0)
        {
            if (speed > -15 - speed_bonus)
                speed -= dx;
            x += speed;
        }
    }

    public void moveLeft()
    {
        if (blocked == 0)
        {
            if (speed > -15)
                speed -= dx;
            x += speed;
        }
    }

    //this function is to ensure friction, to drop out the momemtum the
    //spaceship acquired
    public void decreasing()
    {
        if (speed > 0) //going right
        {
            speed -= decreasing;
            if (speed < 0) //if speed changed sign, we crossed 0 so there should
                speed = 0; //be no more friction
            x += speed;
        }
        else if (speed < 0) //going left
        {
            speed += decreasing;
            if (speed > 0)
                speed = 0;
            x += speed;
        }
    }

    //check if the player reached the end of the screen, change blocked variable
    public void checkBoundaries()
    {
        if (x < 120) //chosen distance
            blocked = -1; //-1 for the left border
        if (x > 1800) //chosen distance
            blocked = 1; //1 for the right border
        //need to specify border to manage the bounce
    }

    //make the player bounce: reverse speed to change direction, and desacitvate
    //player control over the spaceship until the spaceship was pushed away to a
    //chosen point
    public void bounce()
    {
        if (blocked == 1) //if the player reached the right border
        {
            if (speed > 0)
                speed = -speed;
            speed -= dx;
            x += speed;
            if (x <= 1780) //chosen point where the player can control the
                blocked = 0; //spaceship again
        }
        if (blocked == -1) //if the player reached the left border
        {
            if (speed < 0)
                speed = -speed;
            speed += dx;
            x += speed;
            if (x >= 140)
                blocked = 0;
        }
        //if the player has not touch any border do nothing
    }

    //getter for is_alive
    public int isAlive()
    {
        return is_alive;
    }

    //manage player movement
    public void move()
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(37))
                moveLeft();
            else if (StdDraw.isKeyPressed(39))
                moveRight();
            else if (StdDraw.isKeyPressed(16))
                speed = 0;
            else
                decreasing();

            if (StdDraw.isKeyPressed(38))
                rightRotation();
            else if (StdDraw.isKeyPressed(40))
                leftRotation();

            bounce(); //is called to manage bounce if needed
            checkBoundaries(); //called to check if the player is out of screen
        }
    }

    public void move(int speed_bonus)
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(37))
                moveLeft(speed_bonus);
            else if (StdDraw.isKeyPressed(39))
                moveRight(speed_bonus);
            else if (StdDraw.isKeyPressed(16))
                speed = 0;
            else
                decreasing();

            if (StdDraw.isKeyPressed(38))
                rightRotation();
            else if (StdDraw.isKeyPressed(40))
                leftRotation();

            bounce();
            checkBoundaries();
        }
    }

    //diffrent move function because of the different key binding
    public void moveMulti()
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(65))
                moveLeft();
            else if (StdDraw.isKeyPressed(68))
                moveRight();
            else if (StdDraw.isKeyPressed(70))
                speed = 0;
            else
                decreasing();

            if (StdDraw.isKeyPressed(87))
                rightRotation();
            if (StdDraw.isKeyPressed(83))
                leftRotation();

            bounce();
            checkBoundaries();
        }

    }

    //they are two different functions again because of different key biding
    public void fireMulti(Bullet[] bullets)
    {
        int is_shooting = last_bullet.getTime();
        if (StdDraw.isKeyPressed(17) && is_alive == 1 && is_shooting == 0)
        {
            int i = 0;
            while (i < 50 && bullets[i] != null) //seek a place for the bullet
                i++; //there is a limited number (50) of bullets on the screen but
            if  (i < 50) //it is never going to go over that number so it does
            {            //not affect the game
                bullets[i] = new Bullet(x, y, rotation, player);//create bullet
                if (has_waited_enough.getTime() == 0)
                    bullets[i].superBullet();
                last_bullet = bullets[i];
                has_waited_enough = new IngameTimer(5000);
                StdAudio.play("audio/fire.wav"); //play the sound
            }
        }
    }

    public void fire(Bullet[] bullets)
    {
        int is_shooting = last_bullet.getTime();
        if (StdDraw.isKeyPressed(32) && is_alive == 1 && is_shooting == 0)
        {
            int i = 0;
            while (i < 50 && bullets[i] != null)
                i++;
            if  (i < 50)
            {
                bullets[i] = new Bullet(x, y, rotation, player);
                if (has_waited_enough.getTime() == 0)
                    bullets[i].superBullet();
                last_bullet = bullets[i];
                has_waited_enough = new IngameTimer(5000);
                StdAudio.play("audio/fire.wav");
            }
        }
    }

    //fucntion to make the player die (setter)
    public void die()
    {
        is_alive = 0;
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

    //called when restart, restart values that need it
    public void restart()
    {
        is_alive = 1;
        speed = 0;
        blocked = 0;
        x = start_x;
        has_waited_enough = new IngameTimer(5000);
        is_shielded = 0;
    }

    public int isShielded()
    {
        return is_shielded;
    }

    public void increaseKills()
    {
        is_shielded++;
    }

    public void zeroKill()
    {
        is_shielded = 0;
    }

    public void pause()
    {
        has_waited_enough.pause();
    }

    public void resume()
    {
        has_waited_enough.resume();
    }
}
