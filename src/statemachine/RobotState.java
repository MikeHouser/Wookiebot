package statemachine;

import robot.RobotMessage;
import shared.UserCommandContainer;
import statemachine.common.OfflineState;
import util.ConsoleHelper;

public class RobotState {

    public String getName() {
        return "RobotState";
    }

    public void writeMessage() {
        ConsoleHelper.printlnDefault("State:  " + this.getName());
    }

    public void initState(RobotStateContext context) {

    }

    public void handleRobotMessage(RobotStateContext context, RobotMessage message, String data) {

    }

    public void startRobot(RobotStateContext context) {

    }

    public void stopRobot(RobotStateContext context) {
        context.getRobot().deinitRobot();

        context.setState(new OfflineState());
    }

    public void handleCommand(RobotStateContext context, UserCommandContainer command) {
        switch (command.userCommand) {
            case START_LISTEN_DISTANCE: {
                context.getRobot().startListeningDistance();
                break;
            }
            case STOP_LISTEN_DISTANCE: {
                context.getRobot().stopListeningDistance();
                break;
            }
            case START_LISTEN_COLOR: {
                context.getRobot().startListeningColor();
                break;
            }
            case STOP_LISTEN_COLOR: {
                context.getRobot().stopListeningColor();
                break;
            }
            case START_LISTEN_COMPASS: {
                context.getRobot().startListeningCompass();
                break;
            }
            case STOP_LISTEN_COMPASS: {
                context.getRobot().stopListeningCompass();
                break;
            }
        }
    }
}