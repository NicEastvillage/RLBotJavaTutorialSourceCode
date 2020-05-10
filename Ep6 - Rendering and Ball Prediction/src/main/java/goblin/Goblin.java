package goblin;

import goblin.prediction.BallPredictionHelper;
import goblin.prediction.PredictionData;
import goblin.state.AtbaState;
import goblin.state.KickoffState;
import goblin.state.State;
import goblin.util.SmartRenderer;
import rlbot.Bot;
import rlbot.ControllerState;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.GameTickPacket;
import goblin.boost.BoostManager;
import goblin.data.DataPacket;
import goblin.data.ControlsOutput;

import java.awt.*;

public class Goblin implements Bot {

    private final int playerIndex;

    public final SmartRenderer renderer;

    public State state = new AtbaState();

    public Goblin(int playerIndex) {
        this.playerIndex = playerIndex;
        this.renderer = new SmartRenderer(playerIndex);
    }

    /**
     * This is where we keep the actual bot logic. This function shows how to chase the ball.
     * Modify it to make your bot smarter!
     */
    private ControlsOutput processInput(DataPacket data) {
        if (data.isKickoff && !(state instanceof KickoffState)) {
            state = new KickoffState();
        }

        renderer.drawBallPrediction(Color.yellow, 6);

        return state.exec(data);
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

        renderer.startPacket();

        // Do the actual logic using our dataPacket.
        ControlsOutput output = processInput(dataPacket);

        renderer.finishAndSendIfDifferent();

        return output;
    }

    public void retire() {
        System.out.println("Retiring Goblin#" + playerIndex);
    }
}
