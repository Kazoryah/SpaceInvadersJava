## HOW TO LUNCH IT
    - the command is "java Game", the Game class manage everything
    - if you want to compile it "javac @make.txt", it's a command to compile
        everything mentioned in make.txt

## IDEAS/TO MODIFY
    - increase difficulty after restart/ whole balance
    - audio
        - noise when player moves
        - noise when aliens move/go down
        - noise when players fire (adjust where the sound comes from)
        - noise when barriers are down
        - sound for bonus vessel
    - special aliens
    - continue updating screen images
    - alternate screens menu to make sentences flash
    - bullets destroyed by player bullet
    - png for second player shield
    - return to menu button

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
    - Only the aliens at the bottom can shoot and be shot
    - You have barriers that have 15 lives, get red when 5 lives left
        nothing can pass through and EACH bullet destroyed by the barrier
        lessen the lives of 1 life
    - mode 2 players, you win if your partner died 3 times and have a better
        score, otherwise it's an equality, but your score in the scoreboard
        is the sum of both scores, to make collaboration counts
    - the points given depend on the row of the alien

## GAME BONUSES
    - red scope: increase fire rate
    - same spaceship: extra life
    - lightning: shied (transform player's spaceship)
    - cherries: double player's speed

## CODE
### This game is composed of many classes (see each class code to know more about
### what they are doing):
####    Alien
####    AlienBullet
####    Bonus
    - This class has function called by the SpaceInvaders class, to managed all
      bonuses.
    - Every bonuses have the same structure, so only the first one is
      commented.
####    BonusFireRate
####    BonusLife
####    BonusShield
####    BonusSpeed
####    Bullet
####    DrawAll
    - This class has functions called by Game class to draw all screens and
      events that need to appear during the game.
####    EndTimer
    - This class is used by Game class to manage waiting time of game events.
####    Game
    - This class is the final class, it manages all the game and call all the
      functions needed to make the game works properly written in SpaceInvaders
      class. Note that this class is very important because it contains the
      function main() that is static, and allows the computer to create an
      instance of the program, it cant be created as an object.
      Most classes here as we are in Object Oriented Programming are non-static
      because we need to create objects related to the classs. So we need a
      static function to create objects from other classes (that's how I
      understand it, I may be not fully correct, but it works so...).
####    IngameTimer
    - This class is used by many others to manage all timing during the game.
####    Player
####    Protections
####    RefreshTimer
    - This class is a simplyfied timer, to get a constant refresh rate no matter
      the computer your are on.
####    Scoreboard
####    SpaceInvaders
    - Contains all functions for the game to work properly uses all previous
      "sub-"classes.
####    Wrapper
    - Having such a class is the way to do a "pass by reference" in Java.
