Feature: Laser operations

  Scenario Outline: Initialize a new laser
    Given I initialize a laser at <x>, <y> with orientation <orientation>
    Then the laser should be positioned at <x>, <y> with orientation <orientation>

    Examples:
      | x | y | orientation |
      | 0 | 0 | 0           |
      | 5 | 5 | 180         |
      | -1 | -1 | 270       |


  Scenario Outline: Get coordinates of the laser
    Given a laser is positioned at <x>, <y> with orientation <orientation>
    When I retrieve the coordinates
    Then the result should be x=<x> and y=<y>

    Examples:
      | x | y | orientation |
      | 0 | 0 | 0           |
      | 1 | 1 | 90          |

  Scenario Outline: Set new coordinates of the laser
    Given a laser is positioned at <initial_x>, <initial_y> with orientation <orientation>
    When I set the new position to <new_x>, <new_y>
    Then the laser should move to <new_x>, <new_y>

    Examples:
      | initial_x | initial_y | new_x | new_y | orientation |
      | 0         | 0         | 2     | 2     | 0           |
      | 1         | 1         | 3     | 3     | 90          |

  Scenario Outline: Get and set orientation
    Given a laser is positioned at <x>, <y> with orientation <initial_orientation>
    When I set the orientation to <new_orientation>
    Then the orientation should be <new_orientation>

    Examples:
      | x | y | initial_orientation | new_orientation |
      | 0 | 0 | 0                   | 180             |
      | 1 | 1 | 90                  | 270             |

  Scenario: Check equality of two lasers
    Given a laser is positioned at 1, 1 with orientation 90
    And another laser is positioned at 1, 1 with orientation 90
    When I compare the two lasers
    Then they should be considered equal



  Scenario Outline: Check the toString output of the laser
    Given I initialize a laser at <x>, <y> with orientation <orientation>
    When I check the string representation of the laser
    Then the output should be "Laser{x,y = <x>,<y> ; orientation = <orientation>}"

    Examples:
      | x  | y   | orientation |
      | 0  | 0   | 0           |
      | 10 | -10 | 180         |
      | -5 | 5   | 270         |



