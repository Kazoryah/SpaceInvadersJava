import java.lang.Math;

public class SpaceInvaders
{
    int level;
//====
    Player player;
    Player player2;
    Bullet[] bullets; //all the bullets of the player
    Protections[] protections; //protections are stored in this array
//====
    Alien[][] aliens; //all the aliens
    AlienBullet[] alien_bullets; //all the aliens' bullets
    int moving_right; //sense of movement of the aliens
    int left_border; //the leftmost alien
    int right_border; //the rightmost alien
    int[] shooter_row; //row of the alien that can shoot for each column
    double aliens_speed; //speed of the alien
    int aliens_steps; //number of time the aliens went down, for Game to know
               //when to increase the speed of the aliens
    int aliens_killed; //number of alien killed, for Game to know when to
                      //increase the speed of the aliens
    double difficulty; //used to compare a random, to know if the alien fires
    int aliens_won; //to know if the aliens won when they are too low
    int aliens_left; //to know how many aliens are left
    AlienBonus alien_bonus;

    public SpaceInvaders(int level, int number_of_wins)
    {
        this.level = level;
        if (level != 3)
            player = new Player(960, 140, 1); //coordinates chosen
        else
            player = new Player(640, 140, 1); //coordinates chosen
        player2 = new Player(1280, 140, 2);
        bullets = new Bullet[50]; //50 bullets max on the screen, that's enough
        alien_bullets = new AlienBullet[50];
        initializeBullets(); //call to clear the lists above
        aliens = new Alien[4][9]; //chosen number of aliens
        initializeAliens(number_of_wins); //call to initiate the 36 aliens objects
        left_border = 0;
        right_border = 8;
        shooter_row = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        aliens_speed = 2 + number_of_wins; //chosen speed
        aliens_killed = 0;
        aliens_left = 36;
        aliens_won = 0;
        difficulty = 0.01; //chosen rate of fire of the aliens
        aliens_steps = 0;
        initializeProtections();
        alien_bonus = new AlienBonus();
     }

    //function to call player movement functions
    public void movePlayer()
    {
        if (level != 3)
        {
            player.move(Wrapper.extra_speed);
            player.fire(bullets);
        }
        else
        {
            player.moveMulti();
            player2.move();
            player.fire(bullets);
            player2.fireMulti(bullets);
        }
    }

    //function to draw player, aliens, bullets, bonuses and protections
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

        if (player.isAlive() == 0 && level == 3) //to fix drawing when player2
        {                                        //pass on player1 position
            if (player.isShielded() > 9)
                player2.drawShield();
            else
                player2.draw();
        }
        //draw player
        if ((Wrapper.is_bonus == 4 && Wrapper.bonus_shield.stillActive() == 1)
            || player.isShielded() > 9)
            player.drawShield();
        else
            player.draw();

        if (level == 3 && player.isAlive() == 1)
        {
            if (player2.isShielded() > 9)
                player2.drawShield();
            else
                player2.draw();
        }

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
        StdDraw.setPenColor(StdDraw.RED);
        if (Wrapper.is_bonus == 1)
        {
            if (Wrapper.bonus_fire_rate.draw == 1)
                Wrapper.bonus_fire_rate.draw();
            else
                Wrapper.bonus_fire_rate.drawRectangle();
        }
        if (Wrapper.is_bonus == 2)
        {
            if (Wrapper.bonus_life.draw == 1)
                Wrapper.bonus_life.draw();
        }
        if (Wrapper.is_bonus == 3)
        {
            if (Wrapper.bonus_speed.draw == 1)
                Wrapper.bonus_speed.draw();
            else
                Wrapper.bonus_speed.drawRectangle();
        }
        if (Wrapper.is_bonus == 4)
        {
            if (Wrapper.bonus_shield.draw == 1)
                Wrapper.bonus_shield.draw();
            else
                Wrapper.bonus_shield.drawRectangle();
        }

        //draw protections
        if (level != 2)
        {
            if (protections[0] != null)
                protections[0].draw();
            if (protections[1] != null)
                protections[1].draw();
        }

        //draw alien bonus
        alien_bonus.draw();
        alien_bonus.drawScore();
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
    public void initializeAliens(int number_of_wins)
    {
        for (int i = 0; i < 4; i++)
        {
            for (int j = 0; j < 9; j++)
                aliens[i][j] = new Alien(i, j, (number_of_wins + 3) / 4);
                                                //this is to do a round up div

            if (number_of_wins > 0)
                number_of_wins--;
        }

        //the aliens at the bottom are shooters
        for (int j = 0; j < 9; j++)
            aliens[0][j].shooter();
    }

    //create the protections
    public void initializeProtections()
    {
        if (level == 1)
            protections = new Protections[]{new Protections(1, 1),
                          new Protections(2, 1)};
        else if (level == 3)
            protections = new Protections[]{new Protections(1, 1),
                          new Protections(2, 2)};
        else
            protections = new Protections[]{null, null};
    }

    //update which aliens are on the sides
    //update the postion of all aliens
    //look if the shooter is too low (end game)
    public void updateAliens()
    {
        for (int j = 0; j < 9; j++)
        {
            //make shooters shoot, look if he is too low
            int row = shooter_row[j];
            if (row < 4)
            {
                if (player.isAlive() == 1)
                    aliens[row][j].shoot(difficulty, player, alien_bullets);
                if (level == 3 && player2.isAlive() == 1)
                    aliens[row][j].shoot(difficulty, player2, alien_bullets);

                int n = aliens[row][j].isTooLow();
                if (n == 1)
                    aliens_won = 1;
                else if (n == 2)
                    protections = new Protections[]{null, null};
            }

            //make aliens move and go down if needed
            for (int i = 0; i < 4; i++)
            {
                if (aliens[i][j] != null)
                {
                    if (Wrapper.moving_down == 1)
                    {
                        aliens[i][j].moveDown();
                        aliens_steps++;
                    }
                    aliens[i][j].move(aliens_speed);
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

        //move alien bonus
        alien_bonus.move();
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
                            int k = bullets[i].checkKill(aliens, row, j,
                                                shooter_row, player, player2);
                            if (k > 0)
                            {
                                aliens_killed += k;
                                aliens_left -= k;
                                bullets[i] = null;
                            }
                            else if (k == 0)
                                bullets[i] = null;
                            else
                            {
                                //if bullet touched a shielded alien, it
                                //disappears
                                int l = row + 1;
                                while (l < 4 && bullets[i] != null)
                                {
                                    if (bullets[i].checkKill(aliens, l, j,
                                            shooter_row, player, player2) == 0)
                                        bullets[i] = null;

                                    l++;
                                }
                            }
                        }
                        j++;
                    }
                }
            }

            //see if player touched the bonus alien
            if (alien_bonus.manageBonusAlien(bullets[i]) == 1)
                bullets[i] = null;

            //check if bullet touch a protection
            if (bullets[i] != null && protections[0] != null
                && protections[0].hasTouched(bullets[i]) == 1)
                bullets[i] = null;
            if (bullets[i] != null && protections[1] != null
                && protections[1].hasTouched(bullets[i]) == 1)
                bullets[i] = null;

            //check if an anlien bullet touched the player
            //also check if the bullet is off screen
            if (alien_bullets[i] != null)
            {
                alien_bullets[i].move();
                int d = alien_bullets[i].checkKill(player);
                if (d == 1)
                //destroy bullet if it killed the player or touched the shield
                    alien_bullets[i] = null;
                else if (level == 3)
                {
                    d = alien_bullets[i].checkKill(player2);
                    if (d == 1)
                        alien_bullets[i] = null;
                }

                if (d == 0 && alien_bullets[i].isOutOfScreen() == 1)
                    alien_bullets[i] = null;

                //check if bullet touch a protection
                if (alien_bullets[i] != null && protections[0] != null
                    && protections[0].hasTouched(alien_bullets[i]) == 1)
                    alien_bullets[i] = null;
                if (alien_bullets[i] != null && protections[1] != null
                    && protections[1].hasTouched(alien_bullets[i]) == 1)
                    alien_bullets[i] = null;
            }
        }
    }

    //function to see if there is still a protection, and call the function
    //to update image
    public void updateProtections()
    {
        if (protections[0] != null)
        {
            protections[0].checkState();
            if (protections[0].getLives() == 0)
                protections[0] = null;
        }

        if (protections[1] != null)
        {
            protections[1].checkState();
            if (protections[1].getLives() == 0)
                protections[1] = null;
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
        alien_bonus.restart();
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

    //getter for aliens_won
    public int aliensWon()
    {
        return aliens_won;
    }

    public void pause()
    {
        player.pause();
        player2.pause();

        if (Wrapper.is_bonus == 1)
            Wrapper.bonus_fire_rate.pause();
        else if (Wrapper.is_bonus == 2)
            Wrapper.bonus_life.pause();
        else if (Wrapper.is_bonus == 3)
            Wrapper.bonus_speed.pause();
        else if (Wrapper.is_bonus == 4)
            Wrapper.bonus_shield.pause();

        alien_bonus.pause();
    }

    public void resume()
    {
        player.resume();
        player2.resume();

        if (Wrapper.is_bonus == 1)
            Wrapper.bonus_fire_rate.resume();
        else if (Wrapper.is_bonus == 2)
            Wrapper.bonus_life.resume();
        else if (Wrapper.is_bonus == 3)
            Wrapper.bonus_speed.resume();
        else if (Wrapper.is_bonus == 4)
            Wrapper.bonus_shield.resume();

        alien_bonus.resume();
    }
}
