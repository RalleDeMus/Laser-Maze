Feature: CellBlockerTile properties

Scenario: Load CellBlocker Image
  Given that the AssetServer has loaded the CellBlocker image
  When the CellBlockerTile is created
  Then it should have the CellBlocker image set

Scenario: Check CellBlocker Properties

  Given a CellBlockerTile instance
  When I check its properties
  Then it should be non-moveable and non-rotateable
  And its mirror directions should be 0,0,0,0
  And its passable directions should be 1,1,1,1

Scenario: CellBlocker Interaction
  Given a CellBlockerTile instance on a game board
  And a player trying to move the CellBlockerTile
  When the player attempts to move
  Then the player should not be able to move the CellBlockerTile

