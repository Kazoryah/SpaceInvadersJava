import java.util.Timer;
import java.util.TimerTask;

public class RefreshTimer
{
    int time;

    public RefreshTimer(long rate)
    {
        time = 1;
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                time = 0;
            }
        };
        timer.schedule(task, rate, rate);
    }

    public int getTime()
    {
        return time;
    }
}
