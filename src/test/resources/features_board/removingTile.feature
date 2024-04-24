Feature: Removing Tile

  Scenario: Removing a tile from the board
    Given a board with size (5) and squaresize (100)
    And a MirrorTile at position (2) (2)
    When the user removes the MirrorTile at (2) (2)
    Then the board should not have a tile at position (2) (2)

    # Using the removeTile() method



