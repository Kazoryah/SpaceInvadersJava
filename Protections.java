public class Protections
{
    double x;

    String red1 = "png/barrier_red_1.png"; //images when the barrier is runing
    String red2 = "png/barrier_red_2.png"; //out of lives
    String png1; //first picture
    String png2; //second picture

    IngameTimer timer; //to know when alternate the pictures and have a pretty
    int state; //to alternate the picture                             //effect
    int lives; //number of lives remaining

    //constructor
    public Protections(int position, int color)
    {
        if (position == 1)
            x = 640; //chosen coordinates
        else
            x = 1280;

        if (color == 1)
        {
            png1 = "png/barrier_pink_1.png";
            png2 = "png/barrier_pink_2.png";
        }
        else
        {
            png1 = "png/barrier_blue_1.png";
            png2 = "png/barrier_blue_2.png";
        }

        timer = new IngameTimer(50);
        state = 0;
        lives = 15;
    }

    //draw protection
    public void draw()
    {
        if (state == 0)
            StdDraw.picture(x, 250, png1);
        else
            StdDraw.picture(x, 250, png2);
    }

    //check the lives and the picture to display
    public void checkState()
    {
        //change color
        if (lives <= 5)
        {
            png1 = red1;
            png2 = red2;
        }

        //change the image to be displayed
        if (timer.time == 0)
        {
            state = (state + 1) % 2;
            timer = new IngameTimer(50);
        }
    }

    //getter for lives
    public int getLives()
    {
        return lives;
    }

    //check if the prictection has been touched by an alien bullet
    public int hasTouched(AlienBullet bullet)
    {
        if (bullet.getY() <= 260) //chosen area by testing
        {
            double bX = bullet.getX();
            if (bX >= x - 100 && bX <= x + 120) //chosen area, by testing
            {
                lives--;
                return 1;
            }
        }
        return -1;
    }

    //check if the protection has been touched by a player bullet
    public int hasTouched(Bullet bullet)
    {
        if (bullet.getY() >= 230) //chosen are, by testing
        {
            double bX = bullet.getX();
            if (bX >= x - 100 && bX <= x + 120) //chosen area, by testing
            {
                lives--;
                return 1;
            }
        }
        return -1;
    }
}
