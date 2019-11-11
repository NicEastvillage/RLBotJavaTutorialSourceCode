package goblin;

import goblin.state.AtbaState;
import goblin.state.State;
import goblin.vector.Vec3;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallPrediction;
import rlbot.flat.GameTickPacket;
import rlbot.manager.BotLoopRenderer;
import rlbot.render.Renderer;
import goblin.boost.BoostManager;
import goblin.data.CarData;
import goblin.data.DataPacket;
import goblin.data.ControlsOutput;
import goblin.prediction.BallPredictionHelper;

import java.awt.*;

public class Goblin implements Bot {

    private final int playerIndex;

    public State state = new AtbaState();

    public Goblin(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    /**
     * This is where we keep the actual bot logic. This function shows how to chase the ball.
     * Modify it to make your bot smarter!
     */
    private ControlsOutput processInput(DataPacket input) {
        return state.exec(input);
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
        DataPacket dataPacket = new DataPacket(this, packet);

        // Do the actual logic using our dataPacket.
        return processInput(dataPacket);
    }

    public void retire() {
        System.out.println("Retiring Goblin#" + playerIndex);
    }
}
