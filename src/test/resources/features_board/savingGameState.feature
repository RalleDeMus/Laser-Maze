Feature: Saving game state

  Scenario: Saving the game state
    Given a board with boardsize (5) and size (100)
    And several tiles placed on the board
    When the user saves the game state
    Then a JSON file containing the board state and extra tiles information should be created

    # Using the saveGameState() method




