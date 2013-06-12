package kobaia.nsam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * A sample NSam application.
 * @author Mekan Ikzain (mekanikzain@gmail.com)
 * @version $Revision: 1.1 $
 * @since 2005. 7. 28.
 */
public class NSamApp
{
    public static void main(String[] args) throws ParseException, TokenMgrError, MalformedURLException, IOException
    {
        if (args.length < 1)
        {
            System.out.println("[Usage] java NSamApp [<URL> | test]");
            System.out.println("   (ex1) java NSamApp http://www.agfa.com/w3c/euler/owl-facts.n3");
            System.out.println("   (ex1) java NSamApp test");
            return;
        }
        String url = args[0];
        if ("test".equals(url))
        {
            test();
        }
        else
        {
            NSam parser = new NSam();
            StatementHandler h = new StatementHandlerImpl();
            parser.setStatementHandler(h);
            parser.read(url);
        }
    }

    public static void test() throws IOException, ParseException
    {
        FileReader n3ListFile = new FileReader("n3list.txt");
        BufferedReader reader = new BufferedReader(n3ListFile);
        NSam parser = new NSam();
        String aLine = "";
        int i = 1;
        while ((aLine = reader.readLine()) != null)
        {
            if (aLine.startsWith("#"))
                continue;
            StatementHandlerImpl1 h = new StatementHandlerImpl1();
            parser.setStatementHandler(h);
            parser.read(aLine);
            System.out.println("[" + i++ + ": Parsing done for " + aLine + "]");
            System.out.println("  - Stat: " + h.prefixCount() + " prefixes, " + h.statementCount() + " statements.");
        }
    }
}

/**
 * A sample statement handler. This handler just prints out the triples.
 * @author mekanikzain (mekanikzain@gmail.com)
 * @since 2005. 7. 28.
 */
class StatementHandlerImpl implements StatementHandler
{
    public void statement(String subject, String predicate, String object, String objectType)
    {
        System.out.println("Statement: " + subject + ", " + predicate + ", " + object + " = " + objectType);
    }

    public void prefix(String prefix, String uri)
    {
        System.out.println("Prefix: " + prefix + " = " + uri);
    }

    /**
     * @see kobaia.nsam.StatementHandler#endFormula()
     */
    public void endFormula()
    {
        System.out.println("[Formula Ended]");
    }

    /**
     * @see kobaia.nsam.StatementHandler#formulaConnector(java.lang.String)
     */
    public void formulaConnector(String connector)
    {
        System.out.println("  ===> (connector = " + connector + ")");
    }

    /**
     * @see kobaia.nsam.StatementHandler#startFormula()
     */
    public void startFormula()
    {
        System.out.println("[Formula Started]");
    }

}

/**
 * A sample statement handler. This handler just counts the number of prefixes and statements.
 * @author mekanikzain (mekanikzain@gmail.com)
 * @since 2005. 7. 28.
 */
class StatementHandlerImpl1 implements StatementHandler
{
    public void statement(String subject, String predicate, String object, String objectType)
    {
        statementCount++;
    }

    public void prefix(String prefix, String uri)
    {
        prefixCount++;
    }

    public int statementCount()
    {
        return this.statementCount;
    }

    public int prefixCount()
    {
        return this.prefixCount;
    }

    /**
     * @see kobaia.nsam.StatementHandler#endFormula()
     */
    public void endFormula()
    {
        formulaCount++;
    }

    /**
     * @see kobaia.nsam.StatementHandler#formulaConnector(java.lang.String)
     */
    public void formulaConnector(String connector)
    {
    }

    /**
     * @see kobaia.nsam.StatementHandler#startFormula()
     */
    public void startFormula()
    {
    }

    int statementCount = 0;

    int prefixCount = 0;

    int formulaCount = 0;
}