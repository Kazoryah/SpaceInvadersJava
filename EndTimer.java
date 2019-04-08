import java.util.TimerTask;
import java.util.Timer;

public class EndTimer
{
    public EndTimer(long how_long) throws Exception
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                //notify when the timer is down
                synchronized (Wrapper.lock){Wrapper.lock.notify();}
                timer.cancel();
            }
        };
        timer.schedule(task, how_long, how_long);
    }
}
