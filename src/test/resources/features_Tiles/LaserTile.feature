Feature: LaserTile properties

  Scenario: Load LaserTile Image
    Given that the AssetServer has loaded the Laser image
    When the LaserTile is created
    Then it should have the Laser image set

  Scenario: Check Laser Properties
    Given a LaserTile instance
    When I check its propertiies
    Then it should be non-moveablee and rotateablee
    And its mirror directiion should be 0,0,0,0
    And its passable directiion should be 0,0,0,0

  Scenario: Laser Interaction
    Given a LaserTile instance on a game board
    And a player trying to move the LaserTile
    When the player attempts to moove
    Then the player should not be able to move the LaserTile

