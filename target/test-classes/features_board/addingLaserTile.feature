Feature: Adding LaserTile

  Scenario: Adding a LaserTile to the board
    Given a board
    And a cursor position at (0,0)
    When the user adds a LaserTile to the cursor position
    Then the board should have a LaserTile at position (0,0)

    # Using the addTile() Method





