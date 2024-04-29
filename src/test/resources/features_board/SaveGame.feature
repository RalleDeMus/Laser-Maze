Feature: Saving game state

  Scenario: Saving a board with pieces
    Given a board
    And a MirrorTile that is not movable and not rotatable placed on the board
    And a LaserTile that is movable and rotatable placed on the board
    When the user triggers the save game state
    Then a JSON file containing the board state and extra tiles information should be created

  Scenario: Saving a board with extra tiles
    Given a board
    And a MirrorTile that is not movable and not rotatable placed on the board
    And 2 extra MirrorTiles and 1 extra SplitterTile
    When the user triggers the save game state
    Then a JSON file containing the board state and extra tiles information should be created
