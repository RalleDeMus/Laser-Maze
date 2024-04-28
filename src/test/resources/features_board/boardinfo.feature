Feature: Convert integer to Tile objects

  Scenario Outline: Valid integer input returns corresponding Tile
    Given a valid integer input <input>
    When the intToTile method is called
    Then it should return a <tile> object

    Examples:
      | input | tile            |
      | 0     | "MirrorTile"    |
      | 1     | "SplitterTile"  |
      | 2     | "CheckPointTile"|
      | 3     | "DoubleTile"    |
      | 4     | "LaserTile"     |

  Scenario: Invalid integer input returns null
    Given an invalid integer input 5
    When the intToTile method is called
    Then it should return null
