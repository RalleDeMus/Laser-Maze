Feature: Tile Functionality

  Scenario: Creating a Tile
    Given a non-moveable and rotateable tile
    When the tile is created
    Then the tile should be non-moveable and rotateable

  Scenario: Cloning a Tile
    Given a tile with certain properties and image
    When the tile is cloned
    Then the cloned tile should have the same properties

  Scenario: Rotating a Tile
    Given a tile with an orientation 0
    When the tile is rotated by 2 units
    Then the tile's orientation should be 2



