package rusting.graphics;

import arc.graphics.Color;
import rusting.content.Fxr;

public class graphicEffects {

    private static float[][][] floatArr;

    public static void trailEffect(Color trailColour, float x, float y, float width, float radius, float alpha, float sclTime, float sclTimeOut, float[][] points){
        floatArr = new float[][][]{{{width, radius}, {alpha, sclTime, sclTimeOut}}, points};
        Fxr.lineCircles.at(x, y, 0, trailColour, floatArr);
    }
}
