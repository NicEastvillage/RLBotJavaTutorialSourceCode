package goblin;

import goblin.vector.Vec3;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallPrediction;
import rlbot.flat.GameTickPacket;
import rlbot.flat.QuickChatSelection;
import rlbot.manager.BotLoopRenderer;
import rlbot.render.Renderer;
import goblin.boost.BoostManager;
import goblin.input.CarData;
import goblin.input.DataPacket;
import goblin.output.ControlsOutput;
import goblin.prediction.BallPredictionHelper;

import java.awt.*;

public class Goblin implements Bot {

    private final int playerIndex;

    public Goblin(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * This is where we keep the actual bot logic. This function shows how to chase the ball.
     * Modify it to make your bot smarter!
     */
    private ControlsOutput processInput(DataPacket input) {

        Vec3 ballPosition = input.ball.position.flatten();
        CarData myCar = input.car;
        Vec3 carPosition = myCar.position.flatten();

        // Subtract the two positions to get a vector pointing from the car to the ball.
        Vec3 carToBall = ballPosition.minus(carPosition);

        double steer = -myCar.orientation.left.dot(carToBall.normalized()) * 5;

        return new ControlsOutput()
                .withSteer(steer)
                .withThrottle(1);
    }

    /**
     * This is a nice example of using the rendering feature.
     */
    private void drawDebugLines(DataPacket input, CarData myCar, boolean goLeft) {
        // Here's an example of rendering debug data on the screen.
        Renderer renderer = BotLoopRenderer.forBotLoop(this);

        // Draw a line from the car to the ball
        renderer.drawLine3d(Color.BLUE, myCar.position, input.ball.position);

        // Draw a line that points out from the nose of the car.
        renderer.drawLine3d(goLeft ? Color.BLUE : Color.RED,
                myCar.position.plus(myCar.orientation.forward.scaled(150)),
                myCar.position.plus(myCar.orientation.forward.scaled(300)));

        renderer.drawString3d(goLeft ? "left" : "right", Color.WHITE, myCar.position, 2, 2);

        try {
            // Draw 3 seconds of ball prediction
            BallPrediction ballPrediction = RLBotDll.getBallPrediction();
            BallPredictionHelper.drawTillMoment(ballPrediction, myCar.elapsedSeconds + 3, Color.CYAN, renderer);
        } catch (RLBotInterfaceException e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getIndex() {
        return this.playerIndex;
    }

    /**
     * This is the most important function. It will automatically get called by the framework with fresh data
     * every frame. Respond with appropriate controls!
     */
    @Override
    public ControllerState processInput(GameTickPacket packet) {

        if (packet.playersLength() <= playerIndex || packet.ball() == null || !packet.gameInfo().isRoundActive()) {
            // Just return immediately if something looks wrong with the data. This helps us avoid stack traces.
            return new ControlsOutput();
        }

        // Update the boost manager and tile manager with the latest data
        BoostManager.loadGameTickPacket(packet);

        // Translate the raw packet data (which is in an unpleasant format) into our custom DataPacket class.
        // The DataPacket might not include everything from GameTickPacket, so improve it if you need to!
        DataPacket dataPacket = new DataPacket(packet, playerIndex);

        // Do the actual logic using our dataPacket.
        ControlsOutput controlsOutput = processInput(dataPacket);

        return controlsOutput;
    }

    public void retire() {
        System.out.println("Retiring sample bot " + playerIndex);
    }
}
