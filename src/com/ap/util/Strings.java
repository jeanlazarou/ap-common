package com.ap.util;

/**
 * Description: Some useful string functions..
 * @author Jean Lazarou
 * @version 1.0
 */
public class Strings
{
    public static String toStringLiteral(String str)
    {
        char[] s = str.toCharArray();

        StringBuffer buffer = new StringBuffer(s.length);

        char c;

        buffer.setLength(0);

        for (int i = 0; i < s.length; ++i)
        {
            c = s[i];

            if (c == '"')
                buffer.append("\\\"");
            else if (c == '\r')
                buffer.append("\\r");
            else if (c == '\n')
                buffer.append("\\n");
            else if (c == '\\')
                buffer.append("\\\\");
            else
                buffer.append(c);
        }

        return buffer.toString();
    }

    public static String replace(String s, char oldChar, String newSequence)
    {
        char c;

        StringBuffer buffer = new StringBuffer(s.length());

        for (int i = 0; i < s.length(); ++i)
        {
            c = s.charAt(i);

            if (c == oldChar)
            {
                buffer.append(newSequence);
            }
            else
            {
                buffer.append(c);
            }
        }

        return buffer.toString();
    }

	public static String replace(String s, char oldChar, String newSequence, int times)
	{
		char c;
		int count = 0;

		StringBuffer buffer = new StringBuffer(s.length());

		for (int i = 0; i < s.length(); ++i)
		{
			c = s.charAt(i);

			if (c == oldChar)
			{
				count++;
				buffer.append(newSequence);

				if (times > 0 && count >= times)
				{
				    buffer.append(s.substring(i + 1));
					return buffer.toString();
				}
			}
			else
			{
				buffer.append(c);
			}
		}

		return buffer.toString();
	}

	public static String replace(String s, String oldSequence, String newSequence, int times)
	{
		int i;
		int prev = 0;
		int count = 0;

		final int len = oldSequence.length();

		StringBuffer buffer = new StringBuffer(s.length());

		while ((i = s.indexOf(oldSequence, prev)) != -1)
		{
			count++;
			
			buffer.append(s.substring(prev, i));
			buffer.append(newSequence);
			
			prev = i + len;

			if (times > 0 && count >= times) {
				break;
			}
			
		}

		if (prev < s.length()) {
			buffer.append(s.substring(prev));
		}
		
		return buffer.toString();
	}
}