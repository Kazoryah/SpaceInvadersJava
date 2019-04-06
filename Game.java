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

        int playing = 1;
        int level;

        while (playing == 1)
        {
            IngameTimer timer1 = null;
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
                    while (!StdDraw.isKeyPressed(82)){} //R key
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
                }
            }

            //create a new SpaceInvaders object and initliaze Wrapper variables
            Wrapper.initializeVariables();
            SpaceInvaders SI = new SpaceInvaders(level);
        //THE GAME
            while (SI.aliensWon() == 0)
            {
                if (SI.aliensLeft() == 0)
                {
                    SI = new SpaceInvaders(level);
                    Wrapper.initializeVariables();
                }

            //pause
                if (StdDraw.isKeyPressed(80))
                {
                    while(!StdDraw.isKeyPressed(82))
                    {
                        DrawAll.drawPause();
                        StdDraw.show();
                    }
                }
            //calcul part
                SI.movePlayer();
                SI.updateBullets();
                SI.updateAliens();

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
                        Wrapper.repositioning();
                    }
                }

                //need to pause the display of the images, otherwise
                //we literally see nothing
                StdDraw.show(10);
            }

            if (SI.aliensWon() == 1)
                DrawAll.drawAliensWon();

            if (lives == 0 && level != 3)
                DrawAll.drawAliensWon();
            else if (lives == 0)
                DrawAll.drawDeadPlayer1();
            else if (lives2 == 0)
                DrawAll.drawDeadPlayer2();
            StdDraw.show();

            EndTimer timer = new EndTimer();
            synchronized (Wrapper.lock){Wrapper.lock.wait();}

            if (SI.aliensWon() == 1)
                break;

            DrawAll.drawGameOver(level, SI.aliensWon(), lives, lives2);

            StdDraw.show(10);

            //wait for key pressed
            while(true)
            {
                if (StdDraw.isKeyPressed(27)) //escape key
                {
                    playing = 0;
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
