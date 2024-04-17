Feature: LaserTile orientation behavior

  Scenario: Setting and getting the orientation of a LaserTile
    Given a LaserTile has been created
    When I set the orientation of the LaserTile to 0
    Then the orientation of the LaserTile should be 0
