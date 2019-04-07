import java.awt.Font;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;

public final class DrawAll
{
    static Font ingame_font = new Font("Arial",  Font.BOLD, 30);
    static Font score_solo_font = new Font("Arial",  Font.BOLD, 60);
    static Font scoreboard_font = new Font("Arial", Font.BOLD, 65);
    static Color colorFirst = new Color(255, 38, 90);
    static Color colorSecond = new Color(49, 43, 143);
    static Color died_color = new Color(216, 4, 4);

    static String background = "png/screen_background_ingame.jpg";
    static String life_first = "png/life_spaceship_first.png";
    static String life_second = "png/life_spaceship_second.png";
    static String start = "png/screen_start_v4.png";
    static String dead = "png/smoke_cloud_1.png";
    static String dead_screen = "png/screen_down_solo.png";
    static String dead_player1 = "png/screen_down_multi_1.png";
    static String dead_player2 = "png/screen_down_multi_2.png";
    static String game_over5 = "png/screen_game_over_5.png";
    static String game_over4 = "png/screen_game_over_4.png";
    static String game_over3 = "png/screen_game_over_3.png";
    static String game_over2 = "png/screen_game_over_2.png";
    static String game_over1 = "png/screen_game_over_1.png";
    static String won_ingame = "png/screen_won_ingame.png";
    static String won_player1 = "png/screen_won_player_1.png";
    static String won_player2 = "png/screen_won_player_2.png";
    static String won_aliens = "png/screen_aliens_won.png";
    static String equality = "png/screen_equality.png";
    static String pause = "png/screen_pause.png";
    static String more = "png/screen_more.png";

    public static void drawStart()
    {
        StdDraw.picture(672, 378, start);
    }

    public static void drawBackground()
    {
        StdDraw.picture(672, 378, background);
    }

    public static void drawLivesFirst(int lives)
    {
        for (int i = 0; i < lives; i++)
            StdDraw.picture(35 + 31 * i, 42, life_first);
    }

    public static void drawLivesSecond(int lives)
    {
        for (int i = 0; i < lives; i++)
        {
            StdDraw.picture(1309 - 31 * i, 42, life_second);
        }
    }

    public static void drawPoints(SpaceInvaders SI)
    {
        StdDraw.setFont(ingame_font);
        StdDraw.setPenColor(colorFirst);
        StdDraw.text(1260, 42, Integer.toString(Wrapper.first_player_points));
    }

    public static void drawPointsMulti(SpaceInvaders SI)
    {
        StdDraw.setFont(ingame_font);
        StdDraw.setPenColor(colorFirst);
        StdDraw.text(448, 42, Integer.toString(Wrapper.first_player_points));
        StdDraw.setPenColor(colorSecond);
        StdDraw.text(896, 42, Integer.toString(Wrapper.second_player_points));
    }

    public static void drawDead(Player player)
    {
        StdDraw.picture(player.getX(), 91, dead);
    }

    public static void drawDeadScreen()
    {
        StdDraw.picture(672, 378, dead_screen);
    }

    public static void drawAliensWon()
    {
        StdDraw.picture(672, 378, won_aliens);
    }

    public static void drawDeadPlayer1()
    {
        StdDraw.picture(672, 378, dead_player1);
    }

    public static void drawDeadPlayer2()
    {
        StdDraw.picture(672, 378, dead_player2);
    }

    public static void drawGameOver(int level, int aliens_won, int lives,
                                    int lives2, int time, int changed)
                                    throws Exception
    {
        if (time == 5)
            StdDraw.picture(672, 378, game_over5);
        else if (time == 4)
            StdDraw.picture(672, 378, game_over4);
        else if (time == 3)
            StdDraw.picture(672, 378, game_over3);
        else if (time == 2)
            StdDraw.picture(672, 378, game_over2);
        else if (time == 1)
            StdDraw.picture(672, 378, game_over1);
        else
            return;

        if (level != 3)
        {
            StdDraw.setFont(score_solo_font);
            StdDraw.setPenColor(colorFirst);
            StdDraw.text(672, 665,
                    Integer.toString(Wrapper.first_player_points));
            drawScores("scoreboard_solo.txt", level, changed);
        }
        else
        {
            if (aliens_won == 1)
                StdDraw.picture(672, 630, equality);
            else if (Wrapper.first_player_points < Wrapper.second_player_points
                     && lives2 > 0)
                StdDraw.picture(672, 630, won_player2);
            else if (Wrapper.first_player_points > Wrapper.second_player_points
                     && lives > 0)
                StdDraw.picture(672, 630, won_player1);
            else
                StdDraw.picture(672, 630, equality);

            StdDraw.setFont(ingame_font);
            StdDraw.setPenColor(colorFirst);
            StdDraw.text(448, 700,
                    Integer.toString(Wrapper.first_player_points));
            StdDraw.setPenColor(colorSecond);
            StdDraw.text(896, 700,
                    Integer.toString(Wrapper.second_player_points));
            drawScores("scoreboard_multi.txt", level, changed);
        }
    }

    public static void drawPause()
    {
        StdDraw.picture(672, 378, pause);
    }

    public static void drawMore()
    {
        StdDraw.picture(672, 378, more);
    }

    public static void drawWon()
    {
        StdDraw.picture(672, 378, won_ingame);
    }

    public static void drawScores(String path, int level, int changed)
                                  throws Exception
    {
        try (BufferedReader scoreboard = new BufferedReader(new
        FileReader(path)))
        {
            Color c;
            if (level == 3)
                c = StdDraw.RED;
            else
                c = colorFirst;
            StdDraw.setFont(scoreboard_font);
            StdDraw.setPenColor(StdDraw.WHITE);
            if (changed == 1)
                StdDraw.setPenColor(c);
            StdDraw.text(241, 313, scoreboard.readLine());
            StdDraw.setPenColor(StdDraw.WHITE);
            if (changed == 2)
                StdDraw.setPenColor(c);
            StdDraw.text(241, 248, scoreboard.readLine());
            StdDraw.setPenColor(StdDraw.WHITE);
            if (changed == 3)
                StdDraw.setPenColor(c);
            StdDraw.text(241, 185, scoreboard.readLine());

            scoreboard.close();
        }
    }
}
