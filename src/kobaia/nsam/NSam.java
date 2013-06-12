package kobaia.nsam;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * NSam parser. Create an instance of this class and pass it N3 files for parsing.
 * @author MekanikZain (mekanikzain@gmail.com)
 * @version $Revision: 1.2 $
 * @since 2005. 7. 28.
 */
public class NSam
{
    public NSam()
    {
    }

    /**
     * Sets the statement handler.
     * @param handler the statement handler instance
     */
    public void setStatementHandler(StatementHandler handler)
    {
        if (handler == null)
        {
            System.err.println("StatementHandler reference is null! Just returns...");
            return;
        }
        this.handler = handler;
    }

    /**
     * Reads and parses an N3 document. 
     * @param reader the reader through which an N3 document can be read
     * @param docUri the URI of the document
     * @throws IOException if reading from the reader fails
     * @throws ParseException if any parsing error occurs during parsing
     */
    public void read(Reader reader, String docUri) throws IOException, ParseException
    {
        if (AssertionDeligate.Enabled)
            AssertionDeligate.isTrue(reader != null && docUri != null);
        NsamParser parser = new NsamParser(reader);
        parser.setContentHandler(this.handler);
        parser.setDocumentUrl(docUri);
        parser.triples();
    }

    /**
     * Reads and parses an N3 file located at a URL.
     * @param url the URL of the N3 document
     * @throws MalformedURLException if <code>url</code> is in an invalid format
     * @throws IOException if <code>url</code> is invalid for reading
     * @throws ParseException if parsing the N3 document at <code>url</code> fails
     */
    public void read(String url) throws MalformedURLException, IOException, ParseException
    {
        if (url == null)
        {
            System.err.println("URL is null. Just returns.");
            return;
        }
        URL urlobj = new URL(url);
        NsamParser parser = new NsamParser(new BufferedReader(new InputStreamReader(urlobj.openStream(), "UTF-8")));
        parser.setContentHandler(this.handler);
        parser.setDocumentUrl(url);
        parser.triples();
    }

    private StatementHandler handler = null;
}
