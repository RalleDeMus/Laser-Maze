Feature: Removing Tile

  Scenario: Removing a tile from the board
    Given a board with a size of 5x5 and a square size of 50
    And a MirrorTile at positoin (2,2)
    When the user removes the MirrorTile at (2,2)
    Then the board should not have a tile at position (2,2)

    # Using the removeTile() method