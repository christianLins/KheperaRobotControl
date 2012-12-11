SET COPYCMD=/Y
xcopy "../RobotControl1/bin" "./controllers"
java -jar ./ksim.jar
END