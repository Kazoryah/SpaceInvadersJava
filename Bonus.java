public final class Bonus
{
    //function to create the bonus
    public static void createBonusFireRate(Player player)
    {
        Wrapper.bonus_fire_rate = new BonusFireRate();
        //check if the bonus is not to close from the player
        if (Wrapper.bonus_fire_rate.stayCreated(player) == 1)
           Wrapper.is_bonus = 1;
        else //if to close, bonus canceled
            Wrapper.bonus_fire_rate = null;
    }

    //function to see is the bonus has to be destroyed or becomes active
    public static void checkBonusFireRate(Player player)
    {
        int state = Wrapper.bonus_fire_rate.checkState(player);
        if (state == 0 || state == 2)
        {
            Wrapper.is_bonus = 0;
            Wrapper.rate = 500;
            Wrapper.bonus_fire_rate = null;
            //if (state == 2)
            //add sound
        }
        else if (state == 1)
            Wrapper.rate = 100;
    }

    public static void createBonusLife(Player player)
    {
        Wrapper.bonus_life = new BonusLife();
        if (Wrapper.bonus_life.stayCreated(player) == 1)
            Wrapper.is_bonus = 2;
        else
            Wrapper.bonus_life = null;
    }

    public static void checkBonusLife(Player player)
    {
        int state = Wrapper.bonus_life.checkState(player);
        if (state == 0 || state == 2)
        {
            Wrapper.is_bonus = 0;
            Wrapper.bonus_life = null;
            //if (state == 2)
            //add sound
        }
        else if (state == 1)
            Wrapper.extra_life = 1;
    }

    public static void createBonusSpeed(Player player)
    {
        Wrapper.bonus_speed = new BonusSpeed();
        if (Wrapper.bonus_speed.stayCreated(player) == 1)
            Wrapper.is_bonus = 3;
        else
            Wrapper.bonus_speed = null;
    }

    public static void checkBonusSpeed(Player player)
    {
        int state = Wrapper.bonus_speed.checkState(player);
        if (state == 0 || state == 2)
        {
            Wrapper.is_bonus = 0;
            Wrapper.bonus_speed = null;
            Wrapper.extra_speed = 0;
            if (state == 2)
                StdAudio.play("audio/bonus_speed_2_end.wav");
        }
        else if (state == 1)
        {
            Wrapper.extra_speed = 15;
            StdAudio.play("audio/bonus_speed.wav");
        }
    }

    public static void createBonusShield(Player player)
    {
        Wrapper.bonus_shield = new BonusShield();
        if (Wrapper.bonus_shield.stayCreated(player) == 1)
            Wrapper.is_bonus = 4;
        else
            Wrapper.bonus_shield = null;
    }

    public static void checkBonusShield(Player player)
    {
        int state = Wrapper.bonus_shield.checkState(player);
        if (state == 0 || state == 2)
        {
            Wrapper.is_bonus = 0;
            Wrapper.bonus_shield = null;
            if (state == 2)
                StdAudio.play("audio/bonus_shield_end.wav");
        }
        else if (state == 1)
        {
            Wrapper.bonus_shield.startBonus();
            StdAudio.play("audio/bonus_shield.wav");
        }
    }
}
