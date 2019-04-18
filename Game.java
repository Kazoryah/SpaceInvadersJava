public class Game
{
    public static void main(String[] args) throws Exception
    {
        //size of standard screen
        StdDraw.setCanvasSize(1920, 1080);
        StdDraw.setXscale(0, 1920);
        StdDraw.setYscale(0, 1080);
        //enable the calcul off the next screen before displaying
        //increase fluidity
        StdDraw.enableDoubleBuffering();

        int level;
        int number_of_wins = 0;
        RefreshTimer refresh_timer; //needed to get the same refresh rate
                                    //between every computer

//        StdAudio.loop("audio/background_low.wav");
        //this music had to be remove for the zip to be less than 20MB...

        while (true) //the whole ame loop
        {
            IngameTimer timer1 = null; //timers for respawn
            IngameTimer timer2 = null;
            StdDraw.clear();
            int lives = 3; //chosen number of lives
            int lives2 = 3;
            Wrapper.first_player_points = 0;
            Wrapper.second_player_points = 0;
            //draw the menu screen, waiting for a key pressed
            while(true)
            {
                if (StdDraw.isKeyPressed(77)) //M key
                {
                    DrawAll.drawMore();
                    StdDraw.show();
                    while (!StdDraw.isKeyPressed(82)) //R key
                    {
                        if (StdDraw.isKeyPressed(27))
                            System.exit(0);
                    }
                }
                else
                {
                    DrawAll.drawStart();
                    StdDraw.show();
                    if (StdDraw.isKeyPressed(49)) //1 key
                    {
                        level = 1;
                        break;
                    }

                    if (StdDraw.isKeyPressed(50)) //2 key
                    {
                        level = 2;
                        break;
                    }
                    if (StdDraw.isKeyPressed(51)) //3 key
                    {
                        level = 3;
                        break;
                    }
                    if (StdDraw.isKeyPressed(27)) //escape key
                        System.exit(0);
                }
            }
            StdAudio.close();
            StdAudio.play("audio/start_click_v2.wav");
            //create a new SpaceInvaders object and initliaze Wrapper variables
            Wrapper.initializeVariables();
            SpaceInvaders SI = new SpaceInvaders(level, number_of_wins);
            refresh_timer = new RefreshTimer(1); //just to avoid a null
                            //comparision every iteration of the loop below
        //THE PLAYING PART
            while (SI.aliensWon() == 0)
            {
                if (refresh_timer.getTime() == 0)
                {
                    refresh_timer = new RefreshTimer(20);

                //restart if no aliens left
                    if (SI.aliensLeft() == 0)
                    {
                        DrawAll.drawWon();
                        StdDraw.show();
                        StdAudio.play("audio/win.wav");
                        WaitTimer timer = new WaitTimer(3000);
                        synchronized (Wrapper.lock){Wrapper.lock.wait();}
                        SI = new SpaceInvaders(level, ++number_of_wins);
                        Wrapper.initializeVariables();
                    }

                //pause screen
                    if (StdDraw.isKeyPressed(80))
                    {
                        SI.pause();
                        DrawAll.drawPause();
                        StdDraw.show();
                        while(true)
                        {
                            if (StdDraw.isKeyPressed(27)) //escape key
                                System.exit(0);

                            if (StdDraw.isKeyPressed(82)) //R key
                                break;
                        }
                        SI.resume();
                    }

                //calcul part
                    SI.movePlayer();
                    SI.updateBullets();
                    SI.updateAliens();

                    if (level != 2)
                        SI.updateProtections();

                    if (level == 1)
                    {
                        if (SI.player.isAlive() == 1)
                            SI.updateBonus();
                        if (Wrapper.extraLife() == 1)
                            lives++;
                    }

                //drawing part
                    StdDraw.clear();
                    //draw background
                    DrawAll.drawBackground();
                    //go SpaceInvaders.java to see what it draws
                    SI.drawEverything();
                    //draw rocket lives
                    DrawAll.drawLivesFirst(lives);

                    if (level != 3)
                        DrawAll.drawPoints(SI);
                    else
                    {
                        DrawAll.drawPointsMulti(SI);
                        DrawAll.drawLivesSecond(lives2);
                    }

                //check if player is still alive
                    if (SI.player.isAlive() == 0)
                    {
                        DrawAll.drawDead(SI.player);
                        if (timer1 == null)
                        {
                            timer1 = new IngameTimer(1000);
                            lives--;
                            if (lives == 0)
                                break;
                        }
                        else if (timer1.time == 0)
                        {
                            timer1 = null;
                            if (level != 3)
                                SI.restart();
                            else
                                SI.player.restart();
                            Wrapper.repositioning();
                        }
                        if (level != 3)
                            DrawAll.drawDeadScreen();
                   }

               //check game state (finished, lost, win
                   if (SI.player2.isAlive() == 0)
                   {
                        DrawAll.drawDead(SI.player2);
                        if (timer2 == null)
                        {
                            timer2 = new IngameTimer(1000);
                            lives2--;
                            if (lives2 == 0)
                                break;
                        }
                        else if (timer2.time == 0)
                        {
                            timer2 = null;
                            SI.player2.restart();
                        }
                    }
                }
                //need to pause the display of the images, otherwise
                //we literally see nothing
                StdDraw.show(10);
            }

            number_of_wins = 0;

            StdAudio.play("audio/game_over_v3.wav");

            if (SI.aliensWon() == 1)
            {
                DrawAll.drawAliensWon();
                DrawAll.drawDead(SI.player);
            }

            if (lives == 0 && level != 3)
                DrawAll.drawAliensWon();
            else if (lives == 0)
                DrawAll.drawDeadPlayer1();
            else if (lives2 == 0)
                DrawAll.drawDeadPlayer2();

            StdDraw.show();

            WaitTimer timer = new WaitTimer(1000);
            synchronized (Wrapper.lock){Wrapper.lock.wait();}

            IngameTimer timer3 = null;
            int n = 5;
            int changed;
            if (level != 3)
                changed = Scoreboard.checkSolo();
            else
                changed = Scoreboard.checkMulti();

            //wait for key pressed
            while(true)
            {
                if (n == -1)
                    break;
                else if (timer3 == null)
                    timer3 = new IngameTimer(1200);
                else if (timer3.time == 0)
                {
                    DrawAll.drawGameOver(level, SI.aliensWon(),
                                        lives, lives2, n, changed);
                    n--;
                    timer3 = null;
                    StdDraw.show();
                }
                if (StdDraw.isKeyPressed(27)) //escape key
                {
                    System.exit(0);
                    break;
                }
                if (StdDraw.isKeyPressed(82)) //r key
                {
                    break;
                }
            }
        }
    }
}
