/*
 * Created on 2005. 4. 29.
 */
package kobaia.nsam;

/**
 * A simple assertion class for asserting invariables.
 * @author ZEBEHN
 * @version $Id: AssertionDeligate.java,v 1.1 2007-09-10 08:49:36 zebehn Exp $
 * @since 2005. 4. 29.
 */
public class AssertionDeligate
{
    public static final boolean Enabled = true;

    public static final void isTrue(boolean assertion)
    {
        if (AssertionDeligate.Enabled && !assertion)
        {
            throw new RuntimeException("Assertion Failed!");
        }
    }
}
