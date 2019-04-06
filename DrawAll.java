import java.awt.Font;
import java.awt.Color;

public final class DrawAll
{
    static Font ingame_font = new Font("Arial",  Font.BOLD, 30);
    static Color colorFirst = new Color(255, 38, 90);
    static Color colorSecond = new Color(49, 43, 143);
    static Font died_font = new Font("Arial", Font.BOLD, 60);
    static Font died_multi_font = new Font("Arial", Font.BOLD, 120);
    static Color died_color = new Color(216, 4, 4);

    static String background = "png/screen_background_ingame.jpg";
    static String life_first = "png/life_spaceship_first.png";
    static String life_second = "png/life_spaceship_second.png";
    static String start = "png/screen_start_v4.png";
    static String dead = "png/smoke_cloud_1.png";
    static String dead_screen = "png/screen_down_solo.png";
    static String dead_player1 = "png/screen_down_multi_1.png";
    static String dead_player2 = "png/screen_down_multi_2.png";
    static String game_over = "png/screen_game_over_5.png";
    static String won_player1 = "png/screen_won_player_1.png";
    static String won_player2 = "png/screen_won_player_2.png";
    static String won_aliens = "png/screen_aliens_won.png";
    static String equality = "png/screen_equality.png";
    static String pause = "png/screen_pause.png";
    static String more = "png/screen_more.png";

    public static void drawStart()
    {
        StdDraw.picture(960, 540, start);
    }

    public static void drawBackground()
    {
        StdDraw.picture(960, 540, background);
    }

    public static void drawLivesFirst(int lives)
    {
        for (int i = 0; i < lives; i++)
            StdDraw.picture(50 + 45 * i, 60, life_first);
    }

    public static void drawLivesSecond(int lives)
    {
        for (int i = 0; i < lives; i++)
        {
            StdDraw.picture(1870 - 45 * i, 60, life_second);
        }
    }

    public static void drawPoints(SpaceInvaders SI)
    {
        StdDraw.setFont(ingame_font);
        StdDraw.setPenColor(colorFirst);
        StdDraw.text(1800, 60, Integer.toString(Wrapper.first_player_points));
    }

    public static void drawPointsMulti(SpaceInvaders SI)
    {
        StdDraw.setFont(ingame_font);
        StdDraw.setPenColor(colorFirst);
        StdDraw.text(640, 60, Integer.toString(Wrapper.first_player_points));
        StdDraw.setPenColor(colorSecond);
        StdDraw.text(1280, 60, Integer.toString(Wrapper.second_player_points));
    }

    public static void drawDead(Player player)
    {
        StdDraw.picture(player.getX(), 130, dead);
    }

    public static void drawDeadScreen()
    {
        StdDraw.picture(960, 540, dead_screen, 1200, 200);
    }

    public static void drawAliensWon()
    {
        StdDraw.picture(960, 540, won_aliens, 1200, 200);
    }

    public static void drawDeadPlayer1()
    {
        StdDraw.picture(960,540, dead_player1, 1200, 200);
    }

    public static void drawDeadPlayer2()
    {
        StdDraw.picture(960, 540, dead_player2, 1200, 200);
    }

    public static void drawGameOver(int level, int aliens_won, int lives,
                                    int lives2)
    {
        StdDraw.picture(960, 540, game_over);
        if (level != 3)
        {
            StdDraw.setPenColor(colorFirst);
            StdDraw.text(960, 950,
                    Integer.toString(Wrapper.first_player_points));
        }
        else
        {
            if (aliens_won == 1)
                StdDraw.picture(960, 900, equality);
            else if (Wrapper.first_player_points < Wrapper.second_player_points
                     && lives2 > 0)
                StdDraw.picture(960, 900, won_player2);
            else if (Wrapper.first_player_points > Wrapper.second_player_points
                     && lives > 0)
                StdDraw.picture(960, 900, won_player1);
            else
                StdDraw.picture(960, 900, equality);

            StdDraw.setPenColor(colorFirst);
            StdDraw.text(640, 1000,
                    Integer.toString(Wrapper.first_player_points));
            StdDraw.setPenColor(colorSecond);
            StdDraw.text(1280, 1000,
                    Integer.toString(Wrapper.second_player_points));
        }
    }

    public static void drawPause()
    {
        StdDraw.picture(960, 540, pause);
    }

    public static void drawMore()
    {
        StdDraw.picture(960, 540, more);
    }
}
