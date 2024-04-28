Feature: load levels and test configurations

  Scenario Outline: Start a level
    Given I have started a game with multiple "<level>"
    When I load the level
    Then the card should load with the configuration of the level
    Examples:
      | level  |
      | 1  |


  Scenario: Load level 1
    Given I have loaded the card "1"
    Then the card should have the specified number of special tiles for that level

  Scenario: Checking placeable tiles array
    When I request the array of placeable tiles
    Then the array should reflect counts of 1 for targetMirrorTiles, 0 for splitterTiles, 0 for checkPointTiles, 1 for doubleTiles, and 0 for cellBlockerTiles

  Scenario: Setting up the tiles on a level 1 card
    Given I have loaded the card "<level_1.json>"
    Then the card should contain a LaserTile at position (0,0)
    And the card should contain a CellBlockerTile at position (0,3)
    And the card should contain a CheckPointTile at position (3,2)
    And the card should contain a SplitterTile at position (3,3)
    And the card should contain a MirrorTile at position (4,3)