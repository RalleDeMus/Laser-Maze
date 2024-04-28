Feature: SplitterTile properties

  Scenario: Load SplitterTile Image
    Given that the AssetServer has loaded the Splitter image
    When the SplitterTile is created
    Then it should have the Splitter image set

  Scenario: Check Splitter Properties
    Given a SplitterTile instance
    When I check its prooperties
    Then it should be non-mooveable and rotateable
    And its mirror direction shoould be 3,1,3,1
    And its passable direction shoould be 1,1,1,1

  Scenario: Splitter Interaction
    Given a SplitterTile instance on a game board
    And a player trying to move the SplitterTile
    When the player atteempts to move
    Then the player should not be able to move the SplitterTile