Feature: Calculate the laser on the board
  Scenario: Calculate the laser on the board
    Given i load level 6
    And rotate the tiles
    And place a the splitter tile and rotate it
    When i calculate the laser tree
    Then the laser tree should be constructed

