import java.util.Timer;
import java.util.TimerTask;

public class AlienBonusTimer
{
    Timer timer;
    int time;
    int n;
    int pause;

    public AlienBonusTimer()
    {
        time = 1;
        n = 0;
        timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                n += 50;
                if (n == 10000)
                {
                    time = 0;
                    n = -10000;
                }
            }
        };

        timer.schedule(task, 50, 50);
    }

    public int getTime()
    {
        return time;
    }

    public void resetTimer()
    {
        time = 1;
    }

    public void pause()
    {
        pause = n;
    }

    public void resume()
    {
        time = 1;
        timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                n += 50;
                if (n == 10000)
                    time = 0;
            }
        };
        timer.schedule(task, 50, 50);
    }
}
