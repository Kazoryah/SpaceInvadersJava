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

    static String background = "png/ingame.jpg";
    static String spaceship_first = "png/finalSpaceship.gif";
    static String spaceship_second = "png/finalSecondSpaceship.gif";
    static String start = "png/startFinal.jpg";
    static String dead = "png/smoke-cloud-03.png";
    static String dead_screen = "png/downOnePlayer.png";
    static String dead_player1 = "png/downPlayer1.png";
    static String dead_player2 = "png/downPlayer2.png";
    static String game_over = "png/finalLost.jpg";
    static String won_player1 = "png/wonPlayer1.png";
    static String won_player2 = "png/wonPlayer2.png";
    static String equality = "png/equality";

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
            StdDraw.picture(50 + 45 * i, 60, spaceship_first, 100, 100);
    }

    public static void drawLivesSecond(int lives)
    {
        for (int i = 0; i < lives; i++)
        {
            StdDraw.picture(1870 - 45 * i, 60, spaceship_second, 100, 100);
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
        StdDraw.picture(player.getX(), 145, dead);
    }

    public static void drawDeadScreen()
    {
        StdDraw.picture(960, 540, dead_screen, 1200, 200);
    }

    public static void drawAliensWon()
    {
        StdDraw.setPenColor(died_color);
        StdDraw.setFont(died_font);
        StdDraw.text(960, 540, "ALIENS WON.");
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
}
