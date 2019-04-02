## Project guidelines/ideas
- **We should have at least**:
    - a class for the player containing
        - position on the screen
        - rotation position
        - its starting position (for the extra life restart)
        - a position from where to throw the bullet (not for the bullet to
          start in the middle of the ennemy/player shape)
        - an int to know if the player is dead
    - a class for the ennemy containing
        - position on the screen
        - its starting position
        - its position in the matrix (for the destruction)
        - an int to know if it is in the front row (can shoot)
        - a position from where to throw the bullet too
    - a class for the projectile containing
        - position
        - direction vector
    - a matrix containing all the ennemies objects (1)
    - 2 global ints (2) to know which column you need to look at to check if
      the next move is off the screen
    - a function that calculates the next position of the ennemies with the
      screen scale (for that we should take one ennemy on the edge, see if he
      is still on the screen after move, if it's ok then calculate the others,
      else change moving sens and calculate others positions)(a second matrix
      is needed, which will take the place of the first after calculation of
      all positions to avoid latency)
    - a function that update the postion of the bullets, see if it touched
      something or is out of the screen (then destroy the object(s))
        - for destruction of the ennemy play an animation and just remove it
          from the matrix by puting null-type, for the player play an animation
          and let the main function do the rest
        - don't forget to update the number of ennemies left
    - a function that refresh the screen with new positions
    - a class for the game managing:
        - containing (1), (2)
        - a int to know if the ennemies are moving left or right
        - number of lifes remaining
        - number of ennemies left
    - a global function that catch the entries and manage the game (displaying
      the start menu, starting the game, restarting, quit, win/lose display
      screen, and containing the screen scale)
    - drawings of the ennemy, the player and the bullet, and functions that
      draw them to facilitate the refreshing (those functions should also
      take the scale of the screen in parameter)

- **We can also have as bonus gameplay**:
    - a shield that destroy any bullet around the player, with limited use
    - a mode with to players (selection mode by special keys in the menu
      screen)
    - add some terrain shield like in the real game
    - bonuses that can be obtained in a certain way like extra life or
      increased fire rate

## Coding hints
- Don't forget to comment your code, the later should be understandable just by
  reading your comments
