package statemachine.linefollower;

import config.RobotConfig;
import robot.IRobot;
import robot.RobotMessage;
import shared.ColorType;
import statemachine.RobotState;
import statemachine.RobotStateContext;
import util.ConsoleHelper;

import java.text.MessageFormat;

public class FollowLineState extends RobotState {
    private ColorType colorType;
    private final boolean CONSOLE_OUTPUT = true;

    public FollowLineState(ColorType colorType) {
        this.colorType = colorType;
    }

    @Override
    public String getName() {
        return "Follow line";
    }

    @Override
    public void initState(RobotStateContext context) {
        super.initState(context);

        context.getRobot().startDriveForward();
    }

    @Override
    public void handleRobotMessage(RobotStateContext context, RobotMessage message, String data) {
        super.handleRobotMessage(context, message, data);

        IRobot robot = context.getRobot();
        switch (message) {
            case ColorChanged: {
                ColorType newColor = ColorType.valueOf(data);
                if (newColor != this.colorType) {
                    if (CONSOLE_OUTPUT) {
                        ConsoleHelper.printlnPurple(MessageFormat.format(
                                "Lost the color to follow {0} and found {1} instead.",
                                this.colorType, newColor));
                    }

                    // Stop movement
                    robot.stopDriveForward();

                    new Thread(() -> {
                        if (CONSOLE_OUTPUT) {
                            ConsoleHelper.printlnDefault("Wait for motors to stop - start");
                        }

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        context.getRobot().waitForMotorsToStop();

                        if (CONSOLE_OUTPUT) {
                            ConsoleHelper.printlnDefault("Wait for motors to stop - stop");
                        }

                        transition(context);
                    } ).start();
                }
            }
        }
    }

    @Override
    public void transition(RobotStateContext context) {
        RobotState newState;
       if(RobotConfig.getFindLineTimeBased()) {
           newState = new FindLineStateTimeBased(RobotConfig.getFindLineInitialMoveMs(), this.colorType, true);
       } else {
           newState = new FindLineStateStepperBased(0, this.colorType, true);
       }

        context.setState(newState);
    }

    @Override
    public void stopRobot(RobotStateContext context) {
        context.getRobot().stopDriveForward();

        super.stopRobot(context);
    }
}
