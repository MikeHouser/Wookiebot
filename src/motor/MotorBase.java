package motor;

import util.ConsoleHelper;

public class MotorBase implements IMotor {
    protected int minSpeedAbs = 0;
    protected int maxSpeedAbs = 100;

    protected int currentSpeed = 0;

    protected  MotorType motorType = MotorType.UNDEFINED;

    protected boolean isRotating = false;

    public MotorBase(int minSpeedAbs, int maxSpeedAbs, MotorType motorType) {
        this.maxSpeedAbs = maxSpeedAbs;
        this.minSpeedAbs = minSpeedAbs;
        this.motorType = motorType;
    }

    @Override
    public void setSpeed(int speedInPercent) {
        if (speedInPercent == 0) {
            // 0 = Stop
            this.currentSpeed = 0;
        } else if (speedInPercent == 1) {
            // 1 = Min Speed
            this.currentSpeed = this.minSpeedAbs;
        } else if (speedInPercent == 100) {
            // 100 = Max Speed
            this.currentSpeed = this.maxSpeedAbs;
        } else {
            // 2..100 Calculate Speed
            int diff = Math.abs(this.maxSpeedAbs - this.minSpeedAbs);
            float onePercent = (float)diff / (float)100;
            int offset = this.minSpeedAbs;
            if (this.minSpeedAbs > this.maxSpeedAbs) {
                offset *= -1;
            }
            float temp = Math.abs((onePercent * speedInPercent) + offset);
            this.currentSpeed = Math.round(temp);
        }
        ConsoleHelper.printlnDefault("Motor (" + this.motorType.name() + "): Speed is set to " + this.currentSpeed);
    }

    @Override
    public int getSpeed() {
        return this.currentSpeed;
    }

    @Override
    public void forward() {
        this.isRotating = true;
    }

    @Override
    public void backward() {
        this.isRotating = true;
    }

    @Override
    public void stop() {
        this.isRotating = false;
    }

    @Override
    public void init() {

    }

    @Override
    public void deInit() {

    }

    @Override
    public boolean isRotating() {
        return this.isRotating;
    }
}
