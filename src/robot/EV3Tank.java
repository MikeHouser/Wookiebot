package robot;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import colorsensor.ColorController;
import colorsensor.IColorSensor;
import colorsensor.MyEV3ColorSensor;
import compass.CompassController;
import compass.MockupCompassProxy;
import config.RobotConfig;
import distancesensor.DistanceController;
import distancesensor.MockupDistanceSensor;
import led.MockupLed;
import lejos.hardware.Sound;
import lejos.hardware.port.Port;
import lejos.remote.ev3.RemoteEV3;
import motor.EV3Motor;
import motor.IMotor;
import motor.MotorController;
import motor.MotorType;

public class EV3Tank extends RobotBase {

	private RemoteEV3 ev3;

	@Override
	public void initRobot() {
		System.out.println("Started startMotorController of RobotCommander.");

		try {
			this.ev3 = new RemoteEV3(RobotConfig.getIpAddressEv3());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		this.ev3.isLocal();
		this.ev3.setDefault();

		super.compassController = new CompassController(new MockupCompassProxy());

		IMotor leftMotor = new EV3Motor(this.ev3, RobotConfig.getEv3MotorType(), RobotConfig.getEv3LeftMotorPort(), RobotConfig.getMotorBrakeOnStop(), MotorType.LEFT);
		IMotor rightMotor = new EV3Motor(this.ev3, RobotConfig.getEv3MotorType(), RobotConfig.getEv3RightMotorPort(), RobotConfig.getMotorBrakeOnStop(), MotorType.RIGHT);
		super.motorController = new MotorController(leftMotor, rightMotor);

		super.distanceController = new DistanceController(new MockupDistanceSensor());

		Port sensorPort1 = this.ev3.getPort(RobotConfig.getEv3ColorSensorPort());
		IColorSensor colorSensor = new MyEV3ColorSensor(sensorPort1);
		super.colorController = new ColorController(colorSensor);

		super.led = new MockupLed();

		super.initRobot();
		super.initRobotComplete();

		System.out.println("Finished startMotorController of RobotCommander.");
	}

	@Override
	public void Beep() {
		Sound.beep();
	}
}