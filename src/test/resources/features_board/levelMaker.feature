Feature: level maker for the game to create your own levels
  Scenario: User creates a level
    Given a board
    When the user places a MirrorTile and LaserTile on the board
    And the user adds increments number of targets
    And the user increments number of placeable MirrorTiles
    Then the user should be able to save the temporary level

 Scenario: User tests own level
   Given a newly created level
   When the user places the correct tiles
   And the user completes the level
   Then the user should be able to save the level

 Scenario: User plays custom level
   Given a selected custom level
   When the player loads the level
   And plays the level
   Then the player can complete the level
