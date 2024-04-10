Feature: Firing the laser from the LaserTile

  Scenario: Firing the laser with a set orientation and direction
    Given a LaserTile has been created
    And an orientation of the LaserTile has been set
    And a direction of the LaserTile has been set
    When the laser is fired from the LaserTile
    Then the laser should fire in the direction and orientation that was set
