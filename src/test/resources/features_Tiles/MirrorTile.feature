Feature: MirrorTile properties

  Scenario: Load MirrorTile Image
    Given that the AssetServer has loaded the Mirror image
    When the MirrorTile is created
    Then it should have the Mirror image set

  Scenario: Check Mirror Properties
    Given a MirrorTile instance
    When I check its propperties
    Then it should be non-moveablle and rotateable
    And its mirror direction shouldd be 0,1,3,0
    And its passable direction shouldd be 0,1,1,0

  Scenario: Mirror Interaction
    Given a MirrorTile instance on a game board
    And a player trying to move the MirrorTile
    When the player attemppts to move
    Then the player should not be able to move the MirrorTile

