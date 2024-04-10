Feature: Tile rotatability

  Scenario: Player tries to rotate a tile they placed themself
    Given a tile has been created
    And a tile has been placed
    When the player press r
    Then the tile should be rotatable

  Scenario: A tile has been preplaced
    Given a tile has been created
    And a tile has been preplaced
    Then the tile should not be rotatable
