package matl;

import java.util.ArrayList;

import core.Renderer;
import math.Vector3f;
import prim.EngineObject;
import prim.Light;

public interface Material
{
	public Vector3f shadePoint(Renderer renderer, Vector3f rayDirection, int depth, Vector3f point, Vector3f eye, Vector3f normal, ArrayList<Light> lights);
}