## HOW TO LUNCH IT
    - the command is "java Game", the Game class manage everything
    - if you want to compile it "javac @make.txt", it's a command to compile
        everything mentioned in make.txt

## IDEAS/TO MODIFY
    - Protections
    - increase difficulty after restart
    - page of options
    - audio

## GAME CONTROLS
    - start menu: '1' to start with bonuses
                  '2' for increase alien speed, no bonus
                  '3' mode 2 players
    - ingame: arrows left/right to move left/right
              arrows up down to rotate left/right
              space to fire
              if mode 2 players:
                    arrows and control
                    wasd and space bar
              'p' to pause the game
              'r' to resume to the game after pause
    - end game: escape to quit (no effect yet)
                 'r' to restart
## GAME MECHANICS
    - Only the aliens at the bottom can shoot and be shot

## GAME BONUSES
    - black spaceship: increase fire rate
    - same spaceship: extra life
    - funny black spaceship: shied (transform player's spaceship)
    - alien head: double player's speed

## CODE
# The game is composed of multiple classes: AlienBullet, Alien, BonusFireRate,
# BonusLife, Bullet, Game, Player, SpaceInvaders.
    - Player:
        x, y -> position
        rotation -> degree of rotation of the spaceship (left/right)
        png -> image of the spaceship
        function draw() to draw the spaceship (see StdDraw.picture for details)
        functions to move and rotate, and a getter for is_alive
    - Alien:
        its position, image, function draw()
    - AlienBullet:
        position, draw(), and function to make it move down
    - Bullet:
        position of the bullet, its rotation (direction) in radian and degree,
            we need one to draw the bullet and the other to calculate its next
            position
        function to control the fire rate (put variable time at 0 when the next
            bullet can be fired
        side is a variable to know which side the bullet is going, not the
            same calculus between left direction and right direction in move()
    - BonusFireRate:
        position of the bonus on the screen
        variable time to know when the BonusFireRate object needs to ne delete
        variable has_ended to know when the bonus effect is finished
    - SpaceInvaders:
            contains all functions for the game to work properly
            uses all previous classes, see .java to know more about the
            functions
    - Game:
        this class is the final class, it uses the SpaceInvaders class and
            manage all the game and call all the functions neede written in
            SpaceInvaders. Note that this class is very important beacause the
            function main() is static, and all classes here as we are in Object
            Oriented Programming are non-static. So we need a static function
            to call all non-static others from other classes. (That's how I
            understand it, I may be wrong, but it works so...)
