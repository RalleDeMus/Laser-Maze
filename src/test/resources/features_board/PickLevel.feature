Feature: load levels and test configurations

  Scenario Outline: Start a level and test for card tiles and configuration
    Given I have started a game with multiple "<level>"
    When I load the level
    Then the card should load with the configuration of the level
    Examples:
      | level  |
      | 1      |
      | 4      |
      | 8      |

