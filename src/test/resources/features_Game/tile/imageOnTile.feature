Feature: Image of a tile should have matching function

  Scenario: A beamSplitter is hit by laser
    Given a beamSplitter tile has been placed
    And it has been hit by a laser
    Then the tile should split the beam

  Scenario: A cellBlocker is hit by laser
    Given a cellBlocker tile has been placed
    And it has been hit by a laser
    Then  the tile should block the beam

  Scenario: A checkPoint is hit by laser
    Given a checkPoint tile has been placed
    And it has been hit by a laser
    Then  the tile should block the laser

  Scenario: The laser goes over a checkPoint tile but is not hit by laser
    Given a checkPoint tile has been placed
    And the laser passes the tile of the checkPoint
    And the tile has not been hit
    Then  the tile should not block the laser

  Scenario: A doubleMirror is hit by laser
    Given a doubleMirror tile has been placed
    And it has been hit by a laser
    Then  the tile should reflect the laser 90 deg

  Scenario: A targetMirror is hit by laser
    Given a doubleMirror tile has been placed
    And it has been hit by a laser
    When the target side is hit
    Then the tile should not reflect
    And target counter should +1

  Scenario: A targetMirror is hit by laser
    Given a doubleMirror tile has been placed
    And it has been hit by a laser
    When neither the target or the mirror is hit
    Then the tile should not reflect

  Scenario: A targetMirror is hit by laser
    Given a doubleMirror tile has been placed
    And it has been hit by a laser
    When the mirror side is hit
    Then the tile should reflec the laser 90 deg