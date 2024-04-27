Feature: Game card initializations and configurations based on levels

  Scenario: Load level data from a JSON fil e
    Given I have a JSON configuration for level "<level>"
    When I initialize the Card with the level "<level>"
    Then the system should load the configuration from "level_<level>.json" if "<level>" is a number


  Scenario Outline: Initializing a game card with specific level
    Given I have initialized a game card with level <level>
    When I retrieve the card configuration
    Then the card should have the specified number of special tiles for that level
    Examples:
      | level |

  Scenario: Setting up the tiles on a level 1 card
    Given I have initialized a game card with level 1
    When I retrieve the card configuration
    Then the card should contain a LaserTile at position (0, 0)
    And the card should contain a CellBlockerTile at position (0, 3)
    And the card should contain a CheckPointTile at position (3, 2)
    And the card should contain a SplitterTile at position (3, 3)
    And the card should contain a MirrorTile at position (4, 3)

  Scenario: Checking placeable tiles array
    Given I have initialized a game card with level 1
    When I request the array of placeable tiles
    Then the array should reflect counts of 1 for targetMirrorTiles, 0 for splitterTiles, 0 for checkPointTiles, 1 for doubleTiles, and 0 for cellBlockerTiles

