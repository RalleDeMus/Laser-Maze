Feature: Removing Tile

  Scenario: Removing a tile from the board
    Given a board with a size of 5 and a square size of 100
    And a MirrorTile at position x 2 and y 2
    When the user removes the MirrorTile at x 2 and y 2
    Then the board should not have a tile at position x 2 and y 2

    # Using the removeTile() method



