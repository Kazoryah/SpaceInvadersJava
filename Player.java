public class Player
{
    double x;
    double y;
    double start_x;
    int rotation;
    int is_alive;
    int player;
    String png;

    public Player(double x, double y, int player)
    {
        this.x = x;
        this.y = y;
        start_x = x;
        this.player = player;
        rotation = 0;
        is_alive = 1;
        if (player == 1)
            png = "png/testSpaceFirst.png";
        else
            png = "png/testSpaceSecond.png";
    }

    public void draw()
    {
        StdDraw.picture(x, y, png, rotation);
//        StdDraw.setPenColor(StdDraw.RED);
//        StdDraw.circle(x, y, 100);
    }

    //used to draw shield
    public void draw(String image)
    {
        StdDraw.picture(x, y, image, rotation);
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
        if (x < 1800)
            x += (15 + speed_bonus);
    }

    private void moveRight()
    {
        if (x < 1800)
            x += 15;
    }

    public void moveLeft(int speed_bonus)
    {
        if (x > 120)
            x -= (15 + speed_bonus);
    }

    public void moveLeft()
    {
        if (x > 120)
            x -= 15;
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
            if (StdDraw.isKeyPressed(39))
                moveRight();
            if (StdDraw.isKeyPressed(38))
                rightRotation();
            if (StdDraw.isKeyPressed(40))
                leftRotation();
        }
    }

    public void move(int speed_bonus)
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(37))
                moveLeft(speed_bonus);
            if (StdDraw.isKeyPressed(39))
                moveRight(speed_bonus);
            if (StdDraw.isKeyPressed(38))
                rightRotation();
            if (StdDraw.isKeyPressed(40))
                leftRotation();
        }
    }

    public void moveMulti()
    {
        if (is_alive == 1)
        {
            if (StdDraw.isKeyPressed(65))
                moveLeft();
            if (StdDraw.isKeyPressed(68))
                moveRight();
            if (StdDraw.isKeyPressed(87))
                rightRotation();
            if (StdDraw.isKeyPressed(83))
                leftRotation();
        }
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
        x = start_x;
    }
}
