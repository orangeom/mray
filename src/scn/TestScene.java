package scn;

import light.PointLight;
import light.SphereLight;
import math.Quaternion;
import math.Vector3f;
import matl.DiffuseMaterial;
import matl.DiffuseMaterial;
import matl.MirrorMaterial;
import prim.Cylinder;
import prim.GeometryInstance;
import prim.OBJLoader;
import prim.Plane;
import prim.Sphere;
import prim.VerticedGeometry;

// Temperory - Will put in scene describing text file parsing in later
public class TestScene
{
	public static Scene box()
	{
		Scene scene = new Scene();
		DiffuseMaterial white = new DiffuseMaterial(new Vector3f(255f, 255f, 255f));
		DiffuseMaterial red = new DiffuseMaterial(new Vector3f(255f, 0f, 0f));
		DiffuseMaterial blue = new DiffuseMaterial(new Vector3f(0f, 0f, 255f));
		MirrorMaterial mirror = new MirrorMaterial(new Vector3f(255f, 255f, 255f), 0f, 0.8f);


		VerticedGeometry cube = OBJLoader.loadFromFile("./res/cube.obj");

		Quaternion r1 = new Quaternion();
		r1.setFromAxisAngle(new Vector3f(0f, 1f, 0f), ((float) Math.PI * -30f) / 180f);
		scene.addObject(new GeometryInstance(new Vector3f(-3f, -2f, 12f), new Vector3f(4f, 4f, 4f), r1, cube, white));

		Quaternion r2 = new Quaternion();
		r2.setFromAxisAngle(new Vector3f(0f, 1f, 0f), ((float) Math.PI * 30f) / 180f);
		scene.addObject(new GeometryInstance(new Vector3f(2f, -3f, 8f), new Vector3f(2f, 2f, 2f), r2, cube, white));

		scene.addObject(new GeometryInstance(new Vector3f(0f, -9f, 10f), new Vector3f(10f, 10f, 10f), new Quaternion(), cube, white));
		scene.addObject(new GeometryInstance(new Vector3f(0f, 11f, 10f), new Vector3f(10f, 10f, 10f), new Quaternion(), cube, white));
		scene.addObject(new GeometryInstance(new Vector3f(0f, 1f, 20f), new Vector3f(10f, 10f, 10f), new Quaternion(), cube, white));

		scene.addObject(new GeometryInstance(new Vector3f(10f, 1f, 10f), new Vector3f(10f, 10f, 10f), new Quaternion(), cube, red));
		scene.addObject(new GeometryInstance(new Vector3f(-10f, 1f, 10f), new Vector3f(10f, 10f, 10f), new Quaternion(), cube, blue));

		scene.addLight(new SphereLight(new Vector3f(0f, 6f, 10f), 75f, 1f, new Vector3f(255f, 255f, 255f)));

		return scene;
	}

	public static Scene cube()
	{
		Scene scene = new Scene();

		VerticedGeometry ico = OBJLoader.loadFromFile("./res/icosahedron.obj");
		VerticedGeometry cube = OBJLoader.loadFromFile("./res/cube.obj");
		VerticedGeometry monkey = OBJLoader.loadFromFile("./res/monkey.obj");
		VerticedGeometry teapot = OBJLoader.loadFromFile("./res/teapot.obj");

		DiffuseMaterial m1 = new DiffuseMaterial(new Vector3f(255f, 255f, 255f));
		MirrorMaterial m2 = new MirrorMaterial(new Vector3f(255f, 255f, 255f), 0f, 0.8f);
		MirrorMaterial m3 = new MirrorMaterial(new Vector3f(255f, 255f, 255f), 0.1f, 0.5f);


		Quaternion r = new Quaternion();
		r.setFromAxisAngle(new Vector3f(0f, 1f, 0f), ((float) Math.PI * 30f) / 180f);

		scene.addObject(new GeometryInstance(new Vector3f(0f, -3f, 19f), new Vector3f(4f, 4f, 4f), r, cube, m2));
		Quaternion r2 = new Quaternion();
		r2.setFromAxisAngle(new Vector3f(0f, 1f, 0f), ((float) Math.PI * 45f) / 180f);
		scene.addObject(new GeometryInstance(new Vector3f(0f, 0.7f, 19f), new Vector3f(2f, 2f, 2f), r2, ico, m1));

		Quaternion rot = new Quaternion();
		rot.setFromAxisAngle(new Vector3f(1f, 0f, 0f), (float) Math.PI / 2f);
		Quaternion rot2 = new Quaternion();
		rot2.setFromAxisAngle(new Vector3f(0f, 0f, 1f), (float) Math.PI);

		scene.addObject(new GeometryInstance(new Vector3f(-5f, -3f, 15f), new Vector3f(2f, 2f, 2f), rot.multiply(rot2), monkey, m1));
		scene.addObject(new GeometryInstance(new Vector3f(5f, -5f, 15f), new Vector3f(3f, 3f, 3f), new Quaternion(), teapot, m3));

		scene.addObject(new Plane(new Vector3f(0f, -5f, 0f), new Vector3f(0f, 1f, 0f), m1));

		scene.addLight(new SphereLight(new Vector3f(-7f, 7f, 0f), 175f, 4f, new Vector3f(255f, 153f, 51f)));
		scene.addLight(new SphereLight(new Vector3f(7f, 7f, 0f), 175f, 4f, new Vector3f(51f, 153f, 255f)));

		return scene;
	}


	public static Scene diffuseBallAndPlane()
	{
		Scene scene = new Scene();

		VerticedGeometry teapot = OBJLoader.loadFromFile("./res/cube.obj");

		DiffuseMaterial red = new DiffuseMaterial(new Vector3f(255f, 100f, 80f));
		DiffuseMaterial green = new DiffuseMaterial(new Vector3f(100f, 255f, 80f));
		DiffuseMaterial blue = new DiffuseMaterial(new Vector3f(80f, 100f, 255f));
		MirrorMaterial mirror = new MirrorMaterial(new Vector3f(255f, 100f, 80f), 0f, 0.8f);

		DiffuseMaterial m2 = new DiffuseMaterial(new Vector3f(254f, 254f, 254f));


		scene.addObject(new Plane(new Vector3f(0f, -2f, 0f), new Vector3f(0f, 1f, 0f), m2));
		// scene.addObject(new Sphere(new Vector3f(0f, 0f, 10f), 2f, mirror));
		scene.addObject(new Sphere(new Vector3f(3f, -1f, 7f), 1f, green));
		scene.addObject(new Sphere(new Vector3f(-3f, -1f, 7f), 1f, blue));

		Quaternion q = new Quaternion();
		q.setFromAxisAngle(new Vector3f(0f, 1f, 0f), (float) Math.PI * 60f / 180f);

		scene.addObject(new GeometryInstance(new Vector3f(0f, -0.5f, 10f), new Vector3f(3f, 3f, 3f), q, teapot, mirror));


		scene.addLight(new SphereLight(new Vector3f(-20f, 20f, 0f), 500f, 2f, new Vector3f(182f, 126f, 91f)));
		// scene.addLight(new SphereLight(new Vector3f(7f, 3f, 0f), 300f, 4f, new Vector3f(51f, 153f, 255f)));

		return scene;
	}
}