import java.io.FileReader;
import java.io.BufferedReader;
import java.io.PrintWriter;

public final class Scoreboard
{
    //classic scoreboard manipulation
    //the only thing is that the scores are saved in a .txt file and need to be
    //fetched, and rewriitten
    public static int checkSolo() throws Exception
    {
        int changed = -1;

        try (BufferedReader scoreboard = new BufferedReader(new
        FileReader("scoreboard_solo.txt")))
        {
            int score1 = Integer.parseInt(scoreboard.readLine());
            int score2 = Integer.parseInt(scoreboard.readLine());
            int score3 = Integer.parseInt(scoreboard.readLine());

            if (score1 < Wrapper.first_player_points)
            {
                score3 = score2;
                score2 = score1;
                score1 = Wrapper.first_player_points;
                changed = 1;
            }
            else if (score2 < Wrapper.first_player_points)
            {
                score3 = score2;
                score2 = Wrapper.first_player_points;
                changed = 2;
            }
            else if (score3 < Wrapper.first_player_points)
            {
                score3 = Wrapper.first_player_points;
                changed = 3;
            }

            scoreboard.close();

            PrintWriter file = new PrintWriter("scoreboard_solo.txt", "UTF-8");
            file.println(score1);
            file.println(score2);
            file.println(score3);
            file.close();
        }

        return changed;
    }

    public static int checkMulti() throws Exception
    {
        int changed = -1;

        try (BufferedReader scoreboard = new BufferedReader(new
        FileReader("scoreboard_multi.txt")))
        {
            int score1 = Integer.parseInt(scoreboard.readLine());
            int score2 = Integer.parseInt(scoreboard.readLine());
            int score3 = Integer.parseInt(scoreboard.readLine());

            if (score1 < Wrapper.first_player_points
                         + Wrapper.second_player_points)
            {
                score3 = score2;
                score2 = score1;
                score1 = Wrapper.first_player_points +
                    Wrapper.second_player_points;
                changed = 1;
            }
            else if (score2 < Wrapper.first_player_points
                              + Wrapper.second_player_points)
            {
                score2 = score1;
                score2 = Wrapper.first_player_points +
                    Wrapper.second_player_points;
                changed = 2;
            }
            else if (score3 < Wrapper.first_player_points
                              + Wrapper.second_player_points)
            {
                score3 = Wrapper.first_player_points +
                    Wrapper.second_player_points;
                changed = 3;
            }

            scoreboard.close();


            PrintWriter file = new PrintWriter("scoreboard_multi.txt", "UTF-8");
            file.println(score1);
            file.println(score2);
            file.println(score3);
            file.close();
        }

        return changed;
    }
}
