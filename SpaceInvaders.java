import java.lang.Math;

public class SpaceInvaders
{
    int level;
//====
    Player player;
    Player player2;
    Bullet[] bullets; //all the bullets of the player
    Bullet last_bullet; //last bullet reference, to get its time variable to
                        //know when the player can shoot again
    Bullet last_bullet2;
//====
    Alien[][] aliens; //all the aliens
    AlienBullet[] alien_bullets; //all the aliens' bullets
    int moving_right; //sense of movement of the aliens
    int left_border; //the leftmost alien
    int right_border; //the rightmost alien
    int[] shooter_row; //row of the alien that can shoot for each column
    int aliens_speed; //speed of the alien
    int aliens_steps; //number of time the aliens went down, for Game to know
               //when to increase the speed of the aliens
    int aliens_killed; //number of alien killed, for Game to know when to
                      //increase the speed of the aliens
    double difficulty; //used to compare a random, to know if the alien fires
    int aliens_won;
    int aliens_left;

    public SpaceInvaders(int lvl)
    {
        level = lvl;
        if (level != 3)
            player = new Player(960, 130, 1); //coordinates chosen
        else
            player = new Player(640, 130, 1); //coordinates chosen
        player2 = new Player(1280, 130, 2);
        bullets = new Bullet[50]; //50 bullets max on the screen, that's enough
        alien_bullets = new AlienBullet[50];
        initializeBullets(); //call to clear the lists above
        aliens = new Alien[4][9]; //chosen number of aliens
        initializeAliens(); //call to initiate the 36 aliens objects
        left_border = 0;
        right_border = 8;
        shooter_row = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        aliens_speed = 5; //chosen speed
        aliens_killed = 0;
        aliens_left = 36;
        aliens_won = 0;
        last_bullet2 = new Bullet(0, 0, 0, 2);
        last_bullet = new Bullet(0, 0, 0, 1); //initiated to avoid null
        difficulty = 0.03;                    //exception
        aliens_steps = 0;
     }

    //function that catch any key pressed:
    //left and right arrows to move left or right
    //up and down arrows to rotate right and left
    //space to fire
    public void movePlayer()
    {
        if (level != 3)
        {
            player.move(Wrapper.extra_speed);
            int i = player.fire(last_bullet.getTime(), bullets);
            if (i != -1)
                last_bullet = bullets[i];
        }
        else
        {
            player.moveMulti();
            player2.move();
            int i = player.fire(last_bullet.getTime(), bullets);
            if (i != -1)
                last_bullet = bullets[i];
            i = player2.fireMulti(last_bullet2.getTime(), bullets);
            if (i != -1)
                last_bullet2 = bullets[i];
        }
    }

    //function to draw player, aliens, bullets, bonuses
    public void drawEverything()
    {
        //draw bullets
        for (int i = 0; i < 50; i++)
        {
            if (bullets[i] != null)
                bullets[i].draw();
            if (alien_bullets[i] != null)
                alien_bullets[i].draw();
        }

        if (player.isAlive() == 0 && level == 3) //to fix drawinf when player2
            player2.draw();                     //pass on player1 position
        //draw player
        if (Wrapper.is_bonus == 4 && Wrapper.bonus_shield.stillActive() == 1)
            player.draw("png/testShield.png");
        else
            player.draw();

        if (level == 3 && player.isAlive() == 1)
            player2.draw();

        //draw aliens
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (aliens[i][j] != null)
                   aliens[i][j].draw();
            }
        }

        //draw bonuses
        if (Wrapper.is_bonus == 1 && Wrapper.bonus_fire_rate.draw == 1)
            Wrapper.bonus_fire_rate.draw();
        if (Wrapper.is_bonus == 2 && Wrapper.bonus_life.draw == 1)
            Wrapper.bonus_life.draw();
        if (Wrapper.is_bonus == 3 && Wrapper.bonus_speed.draw == 1)
            Wrapper.bonus_speed.draw();
        if (Wrapper.is_bonus == 4 && Wrapper.bonus_shield.draw == 1)
            Wrapper.bonus_shield.draw();
    }

    //initialize every spot of the arrays of bullets to null
    public void initializeBullets()
    {
        for (int i = 0; i < 50; i++)
        {
            bullets[i] = null;
            alien_bullets[i] = null;
        }
    }

    //initialize the array of aliens (create all aliens)
    public void initializeAliens()
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                aliens[i][j] = new Alien(i, j);
            }
        }

        //the aliens at the bottom are shooters
        for (int j = 0; j < 9; j++)
            aliens[0][j].shooter();
    }

    //update which aliems are on the sides
    //update the postion of all aliens
    //look if the shooter is too low (end game)
    public void updateAliens()
    {
        for (int j = 0; j < 9; j++)
        {
            int row = shooter_row[j];
            if (row < 4)
            {
                if (player.isAlive() == 1)
                    aliens[row][j].shoot(difficulty, player, alien_bullets);
                if (level == 3 && player2.isAlive() == 1)
                    aliens[row][j].shoot(difficulty, player2, alien_bullets);

                if (aliens[row][j].isTooLow() == 1)
                    aliens_won = 1;
            }

            for (int i = 0; i < 4; i++)
            {
                if (aliens[i][j] != null)
                {
                    aliens[i][j].move(aliens_speed);
                    if (Wrapper.moving_down == 1)
                    {
                        aliens[i][j].moveDown();
                        aliens_steps++;
                    }
                }
            }
        }
        Wrapper.moving_down = 0; //otherwise they would keep going down

        //check if aliens need to change direction
        //the higher alien is the only one checked, since it would be the last
        //to be killed anyway
        //check which alien is the leftmost or rightmost
        while (left_border < 9 && aliens[3][left_border] == null)
            left_border++;

        while (right_border > -1 && aliens[3][right_border] == null)
            right_border--;
        if (aliens_left > 0)
        {
            aliens[3][left_border].checkBorder();
            aliens[3][right_border].checkBorder();
        }

        //there are bonuses in level one
        //aliens speed increase less fast and not from their steps
        //modulos in each level have been chosen
        if (level == 1 && aliens_killed != 0 && aliens_killed % 10 == 0)
        {
                aliens_speed += 2;
                aliens_killed = 0;
        }
        else if (level == 2 || level == 3)
        {
              if (aliens_killed != 0 && aliens_killed % 7 == 0)
              {
                  aliens_speed += 2;
                  aliens_killed = 0;
              }
              if (aliens_steps != 0 && aliens_steps % 5 == 0)
              {
                  aliens_speed += 2;
                  aliens_steps = 0;
              }
        }
    }

    //if no bonus, see if it creates one
    //otherwise check the state of the current bonus, if it needs to be
    //delete or if the player caught it
        public void updateBonus()
        {
            //creates bonus if no bonus
            if (Wrapper.is_bonus == 0)
            {
                if (Math.random() < 0.01) //0.01 is a chosen rate of bonus drop
                {
                    double random = Math.random();
                    //chose which bonus to drop
                    if (random < 0.25)//increase fire rate
                        Bonus.createBonusFireRate(player);
                    else if (random < 0.5) //extra life
                        Bonus.createBonusLife(player);
                    else if (random < 0.75)
                        Bonus.createBonusSpeed(player);
                    else
                        Bonus.createBonusShield(player);
                }
            }
            else
            {
                //manage existing bonus
                if (Wrapper.is_bonus == 1)
                    Bonus.checkBonusFireRate(player);
                else if (Wrapper.is_bonus == 2)
                    Bonus.checkBonusLife(player);
                else if (Wrapper.is_bonus == 3)
                    Bonus.checkBonusSpeed(player);
                else
                    Bonus.checkBonusShield(player);
            }
        }

        //function to move all the bullets
        //check if the bullets need to be destroyed or touched an alien
        //check if the player has been touched
        //check bullets that are off screen
        public void updateBullets()
        {
            //check if any bullet touched an alien or is off screen
            //it simulates a circle with 50 of radius and see if the bullet is
            //in this circle (see circle equation)
            for (int i = 0; i < 50; i++)
            {
                if (bullets[i] != null)
                {
                    bullets[i].move();
                    if (bullets[i].isOutOfScreen() == 1)
                        bullets[i] = null;
                    else
                    {
                        int j = 0;
                        int row;
                        while (j < 9 && bullets[i] != null)
                        {
                            row = shooter_row[j];
                            if (row < 4)
                            {
                                if (bullets[i].checkKill(aliens[row][j]) == 1)
                                {
                                    aliens[row][j] = null;
                                    aliens_killed++;
                                    aliens_left--;
                                    shooter_row[j]++;
                                    bullets[i].increasePoints(row + 1);
                                    if (row + 1 < 4)
                                        aliens[row + 1][j].shooter();
                                    bullets[i] = null;
                                }
                            }
                            j++;
                        }
                    }
                }

                //check if an anlien bullet touched the player
                //also check if the bullet is off screen
            if (alien_bullets[i] != null)
            {
                alien_bullets[i].move();
                if (Wrapper.is_bonus != 4
                    || Wrapper.bonus_shield.stillActive() != 1)
                {
                    alien_bullets[i].checkKill(player);
                    if (level == 3)
                        alien_bullets[i].checkKill(player2);

                    if (alien_bullets[i].isOutOfScreen() == 1)
                        alien_bullets[i] = null;
                }
            }
        }
    }

    //called by Game to reinitialize the game when the player died but still
    //has at least one life
    public void restart()
    {
        //replace aliens
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (aliens[i][j] != null)
                    aliens[i][j].restart();
            }
        }

        //replace player
        //erase bullets
        initializeBullets();
        aliens_won = 0;
        player.restart();
        player2.restart();
        //erase bonus
        Wrapper.is_bonus = 0;
        Wrapper.rate = 500;
        Wrapper.bonus_fire_rate = null;
        Wrapper.bonus_shield = null;
        Wrapper.bonus_speed = null;
        Wrapper.bonus_life = null;
        Wrapper.extra_speed = 0;
        //reinitialize the steps
        aliens_steps = 0;
    }

    //getter number_of_aliens for Game
    public int aliensLeft()
    {
        return aliens_left;
    }

    //getter alien_killed for Game
    public int aliensKilled()
    {
        return aliens_killed;
    }

    public int aliensWon()
    {
        return aliens_won;
    }
}
