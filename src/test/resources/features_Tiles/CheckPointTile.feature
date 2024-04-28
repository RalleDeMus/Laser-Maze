Feature: CheckPointTile properties

  Scenario: Load CheckPointTile Image
    Given that the AssetServer has loaded the CheckPoint image
    When the CheckPointTile is created
    Then it should have the CheckPoint image set

  Scenario: Check CheckPoint Properties
    Given a CheckPointTile instance
    When I check its propertiees
    Then it should be non-moveable and rotateable
    And its mirror direction should be 0,0,0,0
    And its passable direction should be 0,1,0,1

  Scenario: CheckPoint Interaction
    Given a CheckPointTile instance on a game board
    And a player trying to move the CheckPointTile
    When the player attempts to movee
    Then the player should not be able to move the CheckPointTile

