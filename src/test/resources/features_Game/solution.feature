Feature: Option to see solution

  Scenario: to check if player solution is correct
    Given the game has started
    When A solution has been submitted by the player
    Then the player should be told if the solution is correct

    Scenario: To see solution
      Given the game has started
      When the player at anygiven time wants to see the solution
      Then the player should have the option to see the solution

