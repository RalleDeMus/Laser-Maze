Feature: Game save and loading
  Scenario: Saving current game
    Given game is started
    When the player choose to close the game
    Then the game should be saved

    Scenario: Loading of existing game
      Given the game is not started
      When the player wants to play again
      Then the lost saved game should be displayed

      Scenario: Loading of challenge card