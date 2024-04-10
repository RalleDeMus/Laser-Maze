Feature: MirrorTile orientation

  Scenario: Successfully setting and retrieving the orientation of a MirrorTile
    Given a MirrorTile has been placed
    When the orientation of the MirrorTile to 90
    Then the orientation of the MirrorTile should be 90
