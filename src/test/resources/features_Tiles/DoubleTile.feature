Feature: DoubleTile properties

  Scenario: Load DoubleTile Image
    Given that the AssetServer has loaded the Double image
    When the DoubleTile is created
    Then it should have the Double image set

  Scenario: Check Double Properties
    Given a DoubleTile instance
    When I check its propertiess
    Then it should be non-moveablee and rotateable
    And its mirror directionn should be 3,1,3,1
    And its passable directionn should be 1,1,1,1

  Scenario: Double Interaction
    Given a DoubleTile instance on a game board
    And a player trying to move the DoubleTile
    When the player attempts to movve
    Then the player should not be able to move the DoubleTile

