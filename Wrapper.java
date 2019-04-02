public class Wrapper
{
    //this calss is used to pass object by reference

    public static int moving_right = 1;
    public static int moving_down = 0; //if the aliens reached the end of the
                                       //screen
    public static int is_bonus = 0; //know if bonus is activated and which one
    public static long rate = 500; //the rate at which the player can shoot
    public static int extra_life = 0;
    public static int extra_speed = 0;

    public static int first_player_points;
    public static int second_player_points;

    public static BonusFireRate bonus_fire_rate = null;
    public static BonusLife bonus_life = null;
    public static BonusSpeed bonus_speed = null;
    public static BonusShield bonus_shield = null;

    public static int extraLife()
    {
        int res = extra_life;
        extra_life = 0;
        return res;
    }
}
