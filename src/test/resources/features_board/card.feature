Feature: start level

  Scenario Outline: Start a level
    Given I have started a game with multiple "<level>"
    When I load the level
    Then the card should load with the configuration of the level
    Examples:
      | level_2.json  |
      | level_7.json  |
      | level_11.json |