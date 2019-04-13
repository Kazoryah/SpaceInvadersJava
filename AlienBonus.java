import java.awt.Font;
import java.awt.Color;

public class AlienBonus
{
    double x;
    double y; //position
    String png; //image
    AlienBonusTimer timer;
    double speed;
    int is_drawn;
    int is_alive;
    int player;
    IngameTimer timer_score;
    double draw_score_x;

    public AlienBonus()
    {
        x = -100;
        y = 1025;
        png = "png/alien_bonus_red.png";
        speed = 10;
        timer = new AlienBonusTimer();
        timer_score = null;
        is_drawn = 0;
        is_alive = 0;
        player = -1;
    }

    public void draw()
    {
        if (is_drawn == 1)
            StdDraw.picture(x, y, png);
    }

    public void drawScore()
    {
        if (timer_score != null && timer_score.getTime() != 0)
        {
            StdDraw.setFont(new Font ("Arial", Font.BOLD, 30));
            if (player == 1)
                StdDraw.setPenColor(new Color(255, 38, 90));

            if (player == 2)
                StdDraw.setPenColor(new Color(49, 43, 143));

            StdDraw.text(draw_score_x, y, "100");
        }
        else
            timer_score = null;
    }

    public void move()
    {
        if (is_alive == 1)
            x += speed;
    }

    public int isTouched(Bullet bullet)
    {
        double bX = bullet.getX();
        double bY = bullet.getY();

        if ((x - bX) * (x - bX) + (y - bY) * (y - bY) <= 1849)
        {
            if (bullet.getPlayer() == 1)
            {
                Wrapper.first_player_points += 100;
                player = 1;
            }
            else
            {
                Wrapper.second_player_points += 100;
                player = 2;
            }
            timer_score = new IngameTimer(1000);
            draw_score_x = x;

            return 1;
        }
        return 0;
    }

    public int manageBonusAlien(Bullet bullet)
    {
        if (is_drawn == 1)
        {
            if (bullet != null && isTouched(bullet) == 1)
            {
                is_drawn = 0;
                return 1;
            }
        }

        if (speed > 0 && x >= 2020)
        {
            x = 2020;
            is_alive = 0;
            is_drawn = 0;
            speed = -speed;
        }
        else if (speed < 0 && x < -100)
        {
            x = -100;
            is_alive = 0;
            is_drawn = 0;
            speed = -speed;
        }

        if (timer.getTime() == 0)
        {
            is_drawn = 1;
            timer.resetTimer();
            is_alive = 1;
        }

        return 0;
    }

    public void restart()
    {
        timer = new AlienBonusTimer();
        x = -100;
        timer_score = null;
        is_drawn = 0;
        is_alive = 0;
    }

    public void pause()
    {
        timer.pause();
        if (timer_score != null)
            timer_score.pause();
    }

    public void resume()
    {
        timer.resume();
        if (timer_score != null)
            timer_score.resume();
    }
}
