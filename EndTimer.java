import java.util.TimerTask;
import java.util.Timer;

public class EndTimer
{
    public EndTimer() throws Exception
    {
        Timer timer = new Timer();
        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                synchronized (Wrapper.lock){Wrapper.lock.notify();}
                timer.cancel();
            }
        };
        timer.schedule(task, 2000, 2000);
    }
}
