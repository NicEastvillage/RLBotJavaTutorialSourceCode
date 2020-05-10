package goblin.util;

import com.google.flatbuffers.FlatBufferBuilder;
import goblin.vector.Vec3;
import rlbot.cppinterop.RLBotDll;
import rlbot.cppinterop.RLBotInterfaceException;
import rlbot.flat.BallPrediction;
import rlbot.flat.PredictionSlice;
import rlbot.render.RenderPacket;
import rlbot.render.Renderer;

import java.awt.*;

/**
 * A renderer with extended functionality. Remember to call <code>startPacket</code> and
 * <code>finishAndSendIfDifferent</code>. You cannot use this and the <code>BotLoopRenderer</code> at the same time.
 */
public class SmartRenderer extends Renderer {

    private RenderPacket previousPacket;

    public SmartRenderer(int index) {
        super(index);
    }

    public void startPacket() {
        builder = new FlatBufferBuilder(1000);
    }

    public void finishAndSendIfDifferent() {
        RenderPacket packet = doFinishPacket();
        if (!packet.equals(previousPacket)) {
            RLBotDll.sendRenderPacket(packet);
            previousPacket = packet;
        }
    }

    /**
     * Draw a cube with the given center and size
     */
    public void drawCube(Color color, Vec3 center, double size) {

        double r = size / 2;

        drawLine3d(color, center.plus(new Vec3(-r, -r, -r)), center.plus(new Vec3(-r, -r, r)));
        drawLine3d(color, center.plus(new Vec3(r, -r, -r)), center.plus(new Vec3(r, -r, r)));
        drawLine3d(color, center.plus(new Vec3(-r, r, -r)), center.plus(new Vec3(-r, r, r)));
        drawLine3d(color, center.plus(new Vec3(r, r, -r)), center.plus(new Vec3(r, r, r)));

        drawLine3d(color, center.plus(new Vec3(-r, -r, -r)), center.plus(new Vec3(-r, r, -r)));
        drawLine3d(color, center.plus(new Vec3(r, -r, -r)), center.plus(new Vec3(r, r, -r)));
        drawLine3d(color, center.plus(new Vec3(-r, -r, r)), center.plus(new Vec3(-r, r, r)));
        drawLine3d(color, center.plus(new Vec3(r, -r, r)), center.plus(new Vec3(r, r, r)));

        drawLine3d(color, center.plus(new Vec3(-r, -r, -r)), center.plus(new Vec3(r, -r, -r)));
        drawLine3d(color, center.plus(new Vec3(-r, -r, r)), center.plus(new Vec3(r, -r, r)));
        drawLine3d(color, center.plus(new Vec3(-r, r, -r)), center.plus(new Vec3(r, r, -r)));
        drawLine3d(color, center.plus(new Vec3(-r, r, r)), center.plus(new Vec3(r, r, r)));
    }

    /**
     * Draw a cross with the given center and size
     */
    public void drawCross(Color color, Vec3 center, double size) {

        double r = size / 2;

        drawLine3d(color, center.plus(new Vec3(-r, 0, 0)), center.plus(new Vec3(r, 0, 0)));
        drawLine3d(color, center.plus(new Vec3(0, -r, 0)), center.plus(new Vec3(0, r, 0)));
        drawLine3d(color, center.plus(new Vec3(0, 0, -r)), center.plus(new Vec3(0, 0, r)));
    }

    /**
     * Draw the next few seconds of ball prediction
     */
    public void drawBallPrediction(Color color, double duration) {
        try {
            BallPrediction ballPrediction = RLBotDll.getBallPrediction();
            Vec3 previousLocation = null;
            int stop = (int) (60 * RLMath.clamp(duration, 0, 6.0));
            for (int i = 0; i < ballPrediction.slicesLength(); i += 4) {
                PredictionSlice slice = ballPrediction.slices(i);
                if (i >= stop) {
                    break;
                }
                Vec3 location = new Vec3(slice.physics().location());
                if (previousLocation != null) {
                    drawLine3d(color, previousLocation, location);
                }
                previousLocation = location;
            }

        } catch (RLBotInterfaceException ignored) {

        }
    }
}
