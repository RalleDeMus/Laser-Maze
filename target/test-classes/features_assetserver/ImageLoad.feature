Feature: Load image

  Scenario: Successful load of "beamSplitter" image
    Given the asset server is initialized
    When I load an image with name beamSplitter
    When I request an image named beamSplitter
    Then I should receive the corresponding image

    # Using the loadImages() and getImage() methods

