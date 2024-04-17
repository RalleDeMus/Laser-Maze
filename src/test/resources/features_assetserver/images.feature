Feature:Load images

  Scenario: Successful load of "beamSplitter" image
  Given assetName "beamSplitter"
  When the method loadImages() is executed
  Then the image beamSplitter.png is loaded

  Scenario: Successful load of "cellBlocker" image
  Given assetName "cellBlocker"
  When the method loadImages() isexecuted
  Then the image cellBlocker.png is loaded

  Scenario: Successful load of "checkPoint" image
  Given assetName "checkPoint"
  When the method loadImages() is executed
  Then the image checkPoint.png is loaded

  Scenario: Successful load of "doubleMirror" image
  Given assetName "doubleMirror"
  When the method loadImages() is executed
  Then the image doubleMirror.png isloaded

  Scenario: Successful load of "empty" image
  Given assetName "empty"
  When the method loadImages() is executed
  Then the image empty.png is loaded

  Scenario: Successful load of "laser" image
  Given assetName "laser"
  When the method loadImages() is executed
  Then the image laser.png is loaded

  Scenario: Successful load of "targetMirror" image
  Given assetName "targetMirror"
  When the method loadImages() is executed
  Then the image targetMirror.png is loaded

  Scenario: Successful load of "laserRay" image
  Given assetName "laserRay"
  When the method loadImages() is executed
  Then the image laserRay.png is loaded






