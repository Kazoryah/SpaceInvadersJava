import java.util.Timer;
import java.util.TimerTask;

public class IngameTimer
{
    int time;
    int n;

    public IngameTimer(long rate)
    {
        time = 1;
        n = 0;
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                n += 100;
                if (n == rate)
                {
                    time = 0;
                    if (rate != -1)
                        timer.cancel();
                }
                else if (rate == -1)
                    time = 0;
            }
        };

        if (rate == -1)
            timer.schedule(task, 10000, 20000);
        else
            timer.schedule(task, 100, 100);
    }

    public int getTime()
    {
        return time;
    }

    public void resetTimer()
    {
        time = 1;
    }
}
