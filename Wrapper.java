public class Wrapper
{
    //this calss is used to pass object by reference

    public static int moving_right;
    public static int moving_down; //if the aliens reached the end of the
                                       //screen
    public static int is_bonus; //know if bonus is activated and which one
    public static long rate; //the rate at which the player can shoot
    public static int extra_life;
    public static int extra_speed;

    public static int first_player_points;
    public static int second_player_points;

    public static BonusFireRate bonus_fire_rate;
    public static BonusLife bonus_life;
    public static BonusSpeed bonus_speed;
    public static BonusShield bonus_shield;

    public static Object lock = new Object();

    //for Game class to know if the player caught an extra life, everytime
    //this function is called, extra_life is reset to 0 to avoid the player
    //to gain more than one life at a time
    public static int extraLife()
    {
        int res = extra_life;
        extra_life = 0;
        return res;
    }

    //init variables
    public static void initializeVariables()
    {
        moving_right = 1;
        moving_down = 0;
        is_bonus = 0;
        rate = 500;
        extra_life = 0;
        extra_speed = 0;
        bonus_fire_rate = null;
        bonus_life = null;
        bonus_speed = null;
        bonus_shield = null;
    }

    //reinitialize the movement of the aliens after restart
    public static void repositioning()
    {
        moving_right = 1;
    }
}
