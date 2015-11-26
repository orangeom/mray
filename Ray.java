public class Ray
{
	private Vector3f m_origin;
	private Vector3f m_direction;

	public Ray(Vector3f origin, Vector3f direction)
	{
		m_origin = origin.copy();
		m_direction = direction.copy().normalize();
	}

	public Vector3f getOrigin()
	{
		return m_origin;
	}

	public Vector3f getDirection()
	{
		return m_direction;
	}
}