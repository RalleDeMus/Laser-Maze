Feature: Tile movability

  Scenario: Player places a tile on the board themself
    Given a tile has been created
    When a player with the cursor clicks on tile that was not pre placed
    Then the tile should be movable

  Scenario: A tile has been preplaced
    Given a tile has been created
    And a tile has been preplaced
    Then the tile should not be movable
