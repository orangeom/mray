import java.util.ArrayList;

public class Scene
{
	private ArrayList<EngineObject> m_objects;
	private ArrayList<Light> m_lights;
	private static Vector3f skyColor = new Vector3f(153f, 204f, 255f);

	public Scene()
	{
		m_objects = new ArrayList<EngineObject>();
		m_lights = new ArrayList<Light>();
	}

	public void addObject(EngineObject object)
	{
		m_objects.add(object);
	}

	public void addLight(Light light)
	{
		m_lights.add(light);
	}

	public Vector3f traceRay(Ray ray)
	{
		Intersection min = getIntersection(ray, -1);

		if (min.isIntersect())
		{
			Vector3f inter = ray.getPoint(min.getDistance());

			Vector3f totalDiffuse = new Vector3f(0f, 0f, 0f);
			Vector3f totalSpecular = new Vector3f(0f, 0f, 0f);

			for (int i = 0; i < m_lights.size(); i++)
			{
				Light light = m_lights.get(i);
				Ray lightRay = new Ray(inter, light.getPosition().getSub(inter));
				Intersection toLight = getIntersection(lightRay, min.getObjectIndex());

				if (!toLight.isIntersect())
				{
					totalDiffuse.add(getDiffuse(light, min.getNormal(), inter));
					totalSpecular.add(getSpecular(light, min.getNormal(), inter, new Vector3f(0f, 0f, 0f)));
				}
				else
				{
					// totalDiffuse.add(getDiffuse(light, min.getNormal(), inter).mul(0.4f));
				}
			}

			Vector3f ambient = getAmbient(min.getColor());
			ambient.add(totalSpecular);
			Vector3f objectColor = min.getColor();
			return new Vector3f(Math.min(255f, objectColor.getX() * totalDiffuse.getX() + ambient.getX()),
								Math.min(255f, objectColor.getY() * totalDiffuse.getY() + ambient.getY()),
								Math.min(255f, objectColor.getZ() * totalDiffuse.getZ() + ambient.getZ()));
		}
		else
		{
			return skyColor;
		}
	}

	private Intersection getIntersection(Ray ray, int ignore)
	{
		float dist = Float.MAX_VALUE;
		Vector3f normal = new Vector3f(0f, 0f, 0f);
		Vector3f color = new Vector3f(0f, 0f, 0f);
		int index = -1;
		boolean intersect = false;

		for (int i = 0; i < m_objects.size(); i++)
		{
			if (i == ignore)
			{
				continue;
			}

			Intersection inter = m_objects.get(i).intersect(ray);
			if (inter.isIntersect() && inter.getDistance() < dist)
			{
				dist = inter.getDistance();
				index = i;
				normal = inter.getNormal();
				color = inter.getColor();
				intersect = true;
			}
		}

		if (intersect)
		{
			return new Intersection(dist, normal, color, index);
		}
		else
		{
			return new Intersection();
		}
	}

	private Vector3f getAmbient(Vector3f color)
	{
		return color.getMul(0.4f).add(skyColor.getMul(0.01f));
		// return color.getMul(0.4f);

	}

	private Vector3f getDiffuse(Light light, Vector3f normal, Vector3f point)
	{
		Vector3f pointToLight = light.getPosition().getSub(point);
		float distance = pointToLight.length();
		float cos = Math.max(0f, pointToLight.dot(normal) / distance);
		float intensity = Math.min(1f / cos, light.getRadius() / distance);
		return light.getColor().getMul((cos * intensity) / 255f);
	}

	private Vector3f getSpecular(Light light, Vector3f normal, Vector3f point, Vector3f eye)
	{
		Vector3f lightPosition = light.getPosition();
		Vector3f pointToLight = lightPosition.getSub(point);
		Vector3f pointToEye = eye.getSub(point);
		if (pointToLight.dot(normal) < 0f)
		{
			return new Vector3f(0f, 0f, 0f);
		}
		Vector3f reflected = normal.getMul(pointToLight.dot(normal))
								   .sub(lightPosition).mul(2f)
								   .add(lightPosition)
								   .sub(point);
		float cos = pointToEye.dot(reflected) / (pointToEye.length() * reflected.length());
		if (cos < 0f)
		{
			System.out.println(cos);
			cos = 0f;
		}
		Vector3f color = new Vector3f(255f, 255f, 255f);
		return color.mul(0.15f).mul((float) Math.pow(cos, 90));
	}
}