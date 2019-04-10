import java.util.Timer;
import java.util.TimerTask;

public class IngameTimer
{
    int time;

    public IngameTimer(long rate)
    {
        time = 1;
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                time = 0;
                if (rate != -1)
                    timer.cancel();
            }
        };

        if (rate == -1)
            timer.schedule(task, 10000, 20000);
        else
            timer.schedule(task, rate, rate);
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
