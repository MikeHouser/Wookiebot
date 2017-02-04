package robot;

import colorsensor.ColorController;
import colorsensor.MockupColorSensor;
import compass.CompassController;
import compass.MockupCompassProxy;
import distancesensor.DistanceController;
import distancesensor.MockupDistanceSensor;
import led.MockupLed;
import motor.MockupMotor;
import motor.MotorController;
import motor.MotorType;

public class MockupRobot extends RobotBase {

    @Override
    public void initRobot() {
	    super.compassController = new CompassController(new MockupCompassProxy());

	    super.motorController = new MotorController(
	            new MockupMotor(0, 100, MotorType.LEFT),
                new MockupMotor(0, 100, MotorType.RIGHT));

	    super.distanceController = new DistanceController(new MockupDistanceSensor());

	    super.led = new MockupLed();

	    super.colorController = new ColorController(new MockupColorSensor());

        super.initRobot();

        super.initRobotComplete();
    }

    @Override
    public void startTurnLeft() {
        this.printMessage("Started to turn left.");
        MockupCompassProxy.angleChangeValue = -1;
        super.startTurnLeft();
    }

    @Override
    public void stopTurnLeft() {
        this.printMessage("Stopped to turn left.");
        MockupCompassProxy.angleChangeValue = 0;
        super.stopTurnLeft();
    }

    @Override
    public void startTurnRight() {
        this.printMessage("Started to turn right");
        MockupCompassProxy.angleChangeValue = 1;
        super.startTurnRight();
    }

    @Override
    public void stopTurnRight() {
        this.printMessage("Stopped to turn right");
        MockupCompassProxy.angleChangeValue = 0;
        super.stopTurnRight();
    }


    @Override
    public void printMessage(String message) {
        super.printMessage("MockupRobot: ", message);
    }
}