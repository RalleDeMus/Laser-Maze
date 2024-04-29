Feature: Multiplayer to play with multiple players
  Scenario: 2 players play together in multiplayer
    Given a level is selected
    When the first player solves the level in 1 second
    And the second player solves the level in 2 seconds
    Then the scoreboard is shown