Feature: Tile rotatable
  Scenario: Rotating a tile changes its orientation
    Given I have a rotatable game tile with an initial orientation of <initialOrientation>
    When i rotate the tile once
    Then the tiles orientation should be initial +1

