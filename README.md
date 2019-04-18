## HOW TO LUNCH IT
    - the command is "java Game", the Game class contains the main function
    - if you want to compile it "javac @make.txt", it's a command to compile
        everything mentioned in make.txt

## COMMENTS
    - The game had a background music, but it had to be remove for the zipfile
      to be less than 20MB.

## GAME CONTROLS
    - start menu: '1' to start with bonuses
                  '2' for increase alien speed, no bonus
                  '3' mode 2 players
                  'escape' to quit
                  'm' for the lore (press 'r' to return to menu)
    - ingame: arrows left/right to move left/right, shift to stop
              arrows up down to rotate left/right
              space to fire
              if mode 2 players:
                    arrows and ctrl, shift to stop
                    'wasd' and space bar, 'f' to stop
              'p' to pause the game
                  'r' to resume to the game after pause
                  'escape' to quit
    - end game: 'escape' to quit
                'r' to restart

## GAME MECHANICS
    - only the aliens at the bottom can shoot and be shot
    - you have barriers that have 15 lives, get red when 5 lives left
        nothing can pass through and EACH bullet destroyed by the barrier
        lessen the lives of 1 life
    - mode 2 players, you win if your partner died 3 times and have a better
        score, otherwise it's an equality, but your score in the scoreboard
        is the sum of both scores, to make collaboration counts
    - the points given depend on the row of the alien
    - if you killed 10 enemies without dying, gain a shield
    - if you waited 5 seconds without shooting, gain a bonus bullet that kill
      the Alien you touch and the aliens on its left an right (only if they are
      also shooters
    - the bonus alien that cross the screen every 10 seconds gives 100 points
    - if the aliens reach protections level, protections are destroyed, if they
      reach your level you lose

## GAME BONUSES
    - red scope: increase fire rate
    - same spaceship: extra life
    - lightning: shied (transform player's spaceship)
    - cherries: double player's speed

## CODE
### This game is composed of many classes (see each class code to know more about
### what they are doing):
####    Alien
    - Manage the behaviours that can have aliens.
####    AlienBonus
    - It contains the functions to draw and manage the alien bonus position, if
      it has been killed, give points to the player.
####    AlienBonusTimer
    - Used by Alienbonus only, to manage the 10 secondes intervals.
####    AlienBullet
    - Manage aliens' bullets, check if they killed a player etc.
####    Bonus
    - This class has function called by the SpaceInvaders class, to managed all
      bonuses.
    - Every bonuses have the same structure, so only the first one is
      commented.
####    BonusFireRate
    - When caught, increases the player fire rate for 2 seconds.
####    BonusLife
    - When caught, gives one bonus life to the player.
####    BonusShield
    - When caught gives a protection shield to the player for 2 seconds.
####    BonusSpeed
    - When caught gives a speed bonus to the player for 2 seconds.
####    Bullet
    - Manage position of player bullets, if it if off-screen, touched an alien,
      if the player can shoot again (timer), increase player's points
####    DrawAll
    - This class has functions called by Game class to draw all screens and
      events that need to appear during the game.
####    WaitTimer
    - This class is used by Game class to manage waiting time of game events.
####    Game
    - This class is the final class, it manages all the game and call all the
      functions needed written in SpaceInvaders class to make the game works
      properly. It contains the main function.
####    IngameTimer
    - This class is used by many others to manage all timing during the game.
####    Player
    - Manage all movements of the players including the momentum, their states,
      draw them, etc.
####    Protections
    - Manage the states of the protections, their lifes and draw them.
####    RefreshTimer
    - This class is a simplyfied timer, to get a constant refresh rate no matter
      the computer your are on.
####    Scoreboard
    - This class manage the highscores stored in the .txt files.
####    SpaceInvaders
    - Contains all functions for the game to work properly uses all previous
      "sub-"classes.
####    Wrapper
    - Having such a class is the way to do a "pass by reference" in Java. It was
      needed to make the values used by mostly SpaceInvaders changed by sub-
      functions in other classes.
