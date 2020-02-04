package mmr.maidmodredo.utils.helper;

public class RendererHelper {

    public static void setLightmapTextureCoords(int pValue) {

        //		int ls = pValue % 65536;

        //		int lt = pValue / 65536;

        int ls = pValue & 0xffff;

        int lt = pValue >>> 16;

        //GLX.glMultiTexCoord2f(GLX.GL_TEXTURE1, ls, lt);
    }
}
