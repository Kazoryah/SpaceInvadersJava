import java.util.Timer;
import java.util.TimerTask;

public class IngameTimer
{
    Timer timer;
    int time;
    int n;
    int pause;
    long rate;

    public IngameTimer(long rate)
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
                if (n >= rate)
                    time = 0;

            }
        };

        timer.schedule(task, 50, 50);
        this.rate = rate;
    }

    public int getTime()
    {
        return time;
    }

    public void resetTimer()
    {
        time = 1;
        n = 0;
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
                if (n >= rate)
                    time = 0;
            }
        };
        if (pause >= rate)
            timer.schedule(task, rate, rate);
        else
            timer.schedule(task, rate - pause, rate);
    }

    public int getN()
    {
        return n;
    }
}
