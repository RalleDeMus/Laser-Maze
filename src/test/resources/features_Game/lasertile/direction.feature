Feature: LaserTile direction behavior

  Scenario: Setting and getting the direction of a LaserTile
    Given a LaserTile has been created
    When I set the direction of the LaserTile to west
    Then the direction of the LaserTile should be west
