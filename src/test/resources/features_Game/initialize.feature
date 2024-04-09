Feature: Game Initialization

  Scenario: succesful start game
    Given the game is not started
    When I start the game
    Then the game scene should be visible

