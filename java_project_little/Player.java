public class Player
{
    double x;
    double y;
    double start_x;
    int rotation;
    int is_alive;
    int player;
    String png;
    String shield = "png/spaceship_shield_250.png";
    double dx;
    double decreasing;
    double speed;
    int blocked;

    public Player(double x, double y, int player)
    {
        this.x = x;
        this.y = y;
        start_x = x;
        this.player = player;
        rotation = 0;
        is_alive = 1;
        if (player == 1)
            png = "png/spaceship_first_250.png";
        else
            png = "png/spaceship_second_250.png";
        dx = 2.5;
        decreasing = 1;
        speed = 0;
        blocked = 0;
    }

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
    //the max rotation is 45 degrees left and right
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

    //also control the spaceship for it not to go off screen
    private void moveRight(int speed_bonus)
    {
        if (blocked == 0)
        {
            if (speed < 15 + speed_bonus)
                speed += dx;
            x += speed;
        }
    }

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

    public void decreasing()
    {
        if (speed > 0)
        {
            speed -= decreasing;
            if (speed < 0)
                speed = 0;
            x += speed;
        }
        else if (speed < 0)
        {
            speed += decreasing;
            if (speed > 0)
                speed = 0;
            x += speed;
        }
    }

    public void checkBoundaries()
    {
        if (x < 84)
            blocked = -1;
        if (x > 1260)
            blocked = 1;
    }

    public void bounce()
    {
        if (blocked == 1)
        {
            if (speed > 0)
                speed = -speed;
            speed -= dx;
            x += speed;
            if (x <= 1068)
                blocked = 0;
        }
        if (blocked == -1)
        {
            if (speed < 0)
                speed = -speed;
            speed += dx;
            x += speed;
            if (x >= 140)
                blocked = 0;
        }
    }

    public int isAlive()
    {
        return is_alive;
    }

    public void move()
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(37))
                moveLeft();
            else if (StdDraw.isKeyPressed(39))
                moveRight();
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

    public void move(int speed_bonus)
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(37))
                moveLeft(speed_bonus);
            else if (StdDraw.isKeyPressed(39))
                moveRight(speed_bonus);
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

    public void moveMulti()
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(65))
                moveLeft();
            else if (StdDraw.isKeyPressed(68))
                moveRight();
            else
                decreasing();

            if (StdDraw.isKeyPressed(87))
                rightRotation();
            if (StdDraw.isKeyPressed(83))
                leftRotation();

            bounce();
        }

        checkBoundaries();
    }

    public int fireMulti(int is_shooting, Bullet[] bullets)
    {
        if (StdDraw.isKeyPressed(17) && is_alive == 1 && is_shooting == 0)
        {
            int i = 0;
            while (i < 50 && bullets[i] != null)
                i++;
            if  (i < 50)
            {
                bullets[i] = new Bullet(x, y, rotation, player);
                StdAudio.play("audio/fire.wav");
            }
            else
                return -1;
            return i;
        }
        else
            return -1;
    }

    public int fire(int is_shooting, Bullet[] bullets)
    {
        if (StdDraw.isKeyPressed(32) && is_alive == 1 && is_shooting == 0)
        {
            int i = 0;
            while (i < 50 && bullets[i] != null)
                i++;
            if  (i < 50)
            {
                bullets[i] = new Bullet(x, y, rotation, player);
                StdAudio.play("audio/fire.wav");
            }
            else
                return -1;
            return i;
        }
        else
            return -1;
    }

    public void die()
    {
        is_alive = 0;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void restart()
    {
        is_alive = 1;
        speed = 0;
        blocked = 0;
        x = start_x;
    }
}
