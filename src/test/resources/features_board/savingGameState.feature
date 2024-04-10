Feature: Saving game state

  Scenario: Saving the game state
    Given a board with a size of 5x5 and a square size of 50
    And several tiles placed on the board
    When the user saves the game state
    Then a JSON file containing the board state and extra tiles information should be created

    # Using the saveGameState method