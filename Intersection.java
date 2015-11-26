public class Intersection
{
	private float m_distance;
	private Vector3f m_normal;
	private boolean m_isIntersect;
	private Vector3f m_color;

	public Intersection(float distance, Vector3f normal, Vector3f color)
	{
		m_distance = distance;
		m_normal = normal;
		m_isIntersect = true;
		m_color = color;
	}

	public Intersection()
	{
		m_isIntersect = false;
		m_distance = -1.0f;
	}

	public boolean isIntersect()
	{
		return m_isIntersect;
	}

	public float getDistance()
	{
		return m_distance;
	}

	public Vector3f getNormal()
	{
		return m_normal;
	}

	public Vector3f getColor()
	{
		return m_color;
	}
}