Feature: Rotating tile

  Scenario: Rotating a tile on the board
    Given a bord with a size of 5x5 and a square size of 50
    And a LaserTile at position (1,1)
    When the user rotates the LaserTile at (1,1)
    Then the LaserTile at (1,1) should be rotated

    # Using the rotateSelectedTile() method