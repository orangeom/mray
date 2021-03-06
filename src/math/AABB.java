package math;

public class AABB
{
	private Vector3f m_min;
	private Vector3f m_max;

	public AABB(Vector3f min, Vector3f max)
	{
		m_min = min;
		m_max = max;
	}

	public Vector3f[] getVertices()
	{
		return new Vector3f[]{             m_max,
							  new Vector3f(m_max.getX(), m_min.getY(), m_max.getZ()),
							  new Vector3f(m_min.getX(), m_min.getY(), m_max.getZ()),
							  new Vector3f(m_min.getX(), m_max.getY(), m_max.getZ()),
							  new Vector3f(m_max.getX(), m_max.getY(), m_min.getZ()),
							  new Vector3f(m_max.getX(), m_min.getY(), m_min.getZ()),
							               m_min,
							  new Vector3f(m_min.getX(), m_max.getY(), m_min.getZ())};
	}

	public Vector3f getMin()
	{
		return m_min;
	}

	public Vector3f getMax()
	{
		return m_max;
	}

	// https://tavianator.com/fast-branchless-raybounding-box-intersections/
	public float intersect(Ray ray)
	{
		Vector3f origin = ray.getOrigin();
		Vector3f direction = ray.getDirection();
		float tmin = Float.NEGATIVE_INFINITY;
		float tmax = Float.POSITIVE_INFINITY;

		if (direction.getX() != 0f)
		{
			float tx1 = (m_min.getX() - origin.getX()) / direction.getX();
			float tx2 = (m_max.getX() - origin.getX()) / direction.getX();

			tmin = Math.max(tmin, Math.min(tx1, tx2));
			tmax = Math.min(tmax, Math.max(tx1, tx2));
		}

		if (direction.getY() != 0f)
		{
			float ty1 = (m_min.getY() - origin.getY()) / direction.getY();
			float ty2 = (m_max.getY() - origin.getY()) / direction.getY();

			tmin = Math.max(tmin, Math.min(ty1, ty2));
			tmax = Math.min(tmax, Math.max(ty1, ty2));
		}

		if (direction.getZ() != 0f)
		{
			float tz1 = (m_min.getZ() - origin.getZ()) / direction.getZ();
			float tz2 = (m_max.getZ() - origin.getZ()) / direction.getZ();

			tmin = Math.max(tmin, Math.min(tz1, tz2));
			tmax = Math.min(tmax, Math.max(tz1, tz2));
		}

		if (tmax >= tmin && tmax >= 0)
		{
			return tmin;
		}
		else
		{
			return 0f;
		}
	}

	public boolean intersect(AABB box)
	{
		Vector3f bmin = box.getMin();
		Vector3f bmax = box.getMax();
		return !(m_min.getX() > bmax.getX() || m_max.getX() < bmin.getX() ||
				 m_min.getY() > bmax.getY() || m_max.getY() < bmin.getY() ||
				 m_min.getZ() > bmax.getZ() || m_max.getZ() < bmin.getZ());
	}

	public String toString()
	{
		return "min:" + m_min.toString() + " max:" + m_max.toString();
	}
}