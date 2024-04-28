Feature: Adding and Removing a tile
  Background:
    Given a board (5,100)
    And a cursor position at (0,0)

  Scenario Outline: Adding a tile to the board
    When the user adds a "<TileType>" to the cursor position
    Then the board should have a "<TileType>" at position (0,0)

    Examples:
      | TileType       |
      | MirrorTile     |
      | LaserTile      |
      | SplitterTile   |
      | DoubleTile     |
      | CheckPointTile |

  Scenario Outline: Removing a tile from the board
    Given the user adds a "<TileType>" to the cursor position
    When the user removes the tile at x 0 and y 0
    Then the board should not have a tile at position x 0 and y 0

    Examples:
      | TileType       |
      | MirrorTile     |
      | LaserTile      |
      | SplitterTile   |
      | DoubleTile     |
      | CheckPointTile |

  Scenario: Rotating tile on the board
    Given the user adds a MirrorTile to the cursor position
    When the user rotates the tile at cursor position
    Then the tile at cursor position should be rotated



