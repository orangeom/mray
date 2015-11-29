public class Vector3f
{
	private float m_x;
	private float m_y;
	private float m_z;

	public Vector3f(float x, float y, float z)
	{
		m_x = x;
		m_y = y;
		m_z = z;
	}

	public Vector3f copy()
	{
		return new Vector3f(m_x, m_y, m_z);
	}

	public float getX()
	{
		return m_x;
	}

	public float getY()
	{
		return m_y;
	}

	public float getZ()
	{
		return m_z;
	}

	public Vector3f add(Vector3f v)
	{
		m_x += v.getX();
		m_y += v.getY();
		m_z += v.getZ();
		return this;
	}

	public Vector3f getAdd(Vector3f v)
	{
		return new Vector3f(m_x + v.getX(), m_y + v.getY(), m_z + v.getZ());
	}

	public Vector3f sub(Vector3f v)
	{
		m_x -= v.getX();
		m_y -= v.getY();
		m_z -= v.getZ();
		return this;
	}

	public Vector3f getSub(Vector3f v)
	{
		return new Vector3f(m_x - v.getX(), m_y - v.getY(), m_z - v.getZ());
	}

	public Vector3f mul(float d)
	{
		m_x *= d;
		m_y *= d;
		m_z *= d;
		return this;
	}

	public Vector3f getMul(float d)
	{
		return new Vector3f(m_x * d, m_y * d, m_z * d);
	}

	public float dot(Vector3f v)
	{
		return m_x * v.getX() + m_y * v.getY() + m_z * v.getZ();
	}

	public float lengthSquared()
	{
		return m_x * m_x + m_y * m_y + m_z * m_z;
	}

	public float length()
	{
		return (float) Math.sqrt(lengthSquared());
	}

	public Vector3f normalize()
	{
		float length = length();
		m_x /= length;
		m_y /= length;
		m_z /= length;
		return this;
	}

	public String toString()
	{
		return "(" + m_x + ", " + m_y + ", " + m_z + ")";
	}
}