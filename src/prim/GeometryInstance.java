package prim;

import java.util.ArrayList;

import math.AABB;
import math.Matrix4f;
import math.Quaternion;
import math.Ray;
import math.Vector3f;
import matl.Material;

public class GeometryInstance implements EngineObject
{
	private GeometryData m_geometry;
	private Material m_material;

	private Matrix4f m_transform;
	private Matrix4f m_transformTranspose;
	private Matrix4f m_inverseTransform;
	private Matrix4f m_inverseTranspose;
	private AABB m_boundingBox;

	public GeometryInstance(Vector3f position, Vector3f scale, Quaternion rotation, GeometryData geometry, Material material)
	{
		m_geometry = geometry;
		m_material = material;

		Matrix4f s = new Matrix4f();
		s.initScale(scale);
		Matrix4f rot = new Matrix4f(rotation);
		m_transform = s.multiply(rot);
		m_transform.initTranslate(position);

		m_inverseTransform = m_transform.getInverse();
		m_transformTranspose = m_transform.getTranspose();
		m_inverseTranspose = m_inverseTransform.getTranspose();

		Vector3f min = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
		Vector3f max = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

		Vector3f gmin = m_geometry.getBoundingBox().getMin();
		Vector3f gmax = m_geometry.getBoundingBox().getMax();

		Vector3f[] boxVertices = new Vector3f[]{gmin,
												gmax,
											    new Vector3f(gmin.getX(), gmin.getY(), gmax.getZ()),
												new Vector3f(gmin.getX(), gmax.getY(), gmin.getZ()),
												new Vector3f(gmax.getZ(), gmin.getY(), gmin.getZ()),
												new Vector3f(gmin.getX(), gmax.getY(), gmax.getZ()),
												new Vector3f(gmax.getX(), gmin.getY(), gmax.getZ()),
												new Vector3f(gmax.getX(), gmax.getY(), gmin.getZ())};
		for (int i = 0; i < 8; i++)
		{
			Vector3f v = m_transform.apply(boxVertices[i], 1f);
			if (v.getX() < min.getX())
			{
				min.setX(v.getX());
			}
			else if (v.getX() > max.getX())
			{
				max.setX(v.getX());
			}

			if (v.getY() < min.getY())
			{
				min.setY(v.getY());
			}
			else if (v.getY() > max.getY())
			{
				max.setY(v.getY());
			}

			if (v.getZ() < min.getZ())
			{
				min.setZ(v.getZ());
			}
			else if (v.getZ() > max.getZ())
			{
				max.setZ(v.getZ());
			}
		}
		m_boundingBox = new AABB(min, max);
	}

	public Intersection intersect(Ray ray)
	{
		if (!m_boundingBox.intersect(ray))
		{
			return new Intersection();
		}

		ArrayList<Triangle> triangles = m_geometry.getTriangles();
		Intersection min = new Intersection(Float.MAX_VALUE, Vector3f.zero(), m_material, -1);
		boolean intersected = false;

		for (int i = 0; i < triangles.size(); i++)
		{
			Triangle t = triangles.get(i);
			Vector3f v0 = t.getV0();
			Vector3f v10 = t.getV10();
			Vector3f v20 = t.getV20();
			Vector3f normal = t.getNormal();

			Ray newRay = new Ray(m_inverseTransform.apply(ray.getOrigin(), 1f), m_transformTranspose.apply(ray.getDirection(), 0f));
			float d = rayTriangleIntersect(newRay, v0, v10, v20, normal);
			if (d < 0)
			{
				continue;
			}

			Vector3f p = m_transform.apply(newRay.getPoint(d), 1f);
			d = ray.getOrigin().getSub(p).length();

			if (d > 0 && d < min.getDistance())
			{
				min.setDistance(d);
				min.setNormal(normal);
				intersected = true;
			}
		}

		if (intersected)
		{
			min.setNormal(m_inverseTranspose.apply(min.getNormal(), 0f).normalize());
			return min;
		}
		else
		{
			return new Intersection();
		}
	}

	private static float rayTriangleIntersect(Ray ray, Vector3f v0, Vector3f v10, Vector3f v20, Vector3f normal)
	{
		float d = normal.dot(v0.getSub(ray.getOrigin())) / normal.dot(ray.getDirection());
		if (d < 0)
		{
			return -1f;
		}

		Vector3f point = ray.getPoint(d);

		Vector3f p0 = point.getSub(v0);
		Vector3f ex = v10.getNormalized();
		Vector3f ey = ex.cross(normal);

		if (pointInTriangle(0f, 0f, v10.dot(ex), v10.dot(ey), v20.dot(ex), v20.dot(ey), p0.dot(ex), p0.dot(ey)))
		{
			return d;
		}
		else
		{
			return -1;
		}
	}

	private static boolean pointInTriangle(float ax, float ay, float bx, float by, float cx, float cy, float px, float py)
	{
		float aABC = triangleAreaTimesTwo(ax, ay, bx, by, cx, cy);
		float aABP = triangleAreaTimesTwo(ax, ay, bx, by, px, py);
		float aBCP = triangleAreaTimesTwo(bx, by, cx, cy, px, py);
		float aCAP = triangleAreaTimesTwo(cx, cy, ax, ay, px, py);
		return aABP + aBCP + aCAP <= 1.0001 * aABC;
	}

	private static float triangleAreaTimesTwo(float ax, float ay, float bx, float by, float cx, float cy)
	{
		return Math.abs((ax * by) + (bx * cy) + (cx * ay) - (ay * bx) - (by * cx) - (cy * ax));
	}
}