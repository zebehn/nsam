package kobaia.nsam;

/**
 * Receive notification of the statements contained in an N3 document.
 * This is the main interface that most NSam applications should implement.
 * If the application needs to be informed of the content of an N3 document, it implements this interface and
 * registers an instance of the implementation with the NSam parser using the kobaia.nsam.NSam.setStatementHandler method.
 * @author Mekan Ikzain (mekanikzain@gmail.com)
 * @version $Revision: 1.1 $
 * @since 2005. 7. 28.
 */
public interface StatementHandler
{
    /**
     * Receive notification of a statement. NSam parser invokes this method every time it recognizes a triple.
     * <code>objectType</code> is the full URI that reveals the type of the object. If the object is a resource,
     * then <code>objectType</code> is <code>http://www.w3.org/2000/01/rdf-schema#Resource</code>. If the object
     * is a data value such as integer, string etc, then <code>objectType</code> is the corresponding XSD datatype URI.
     * For example, if the object is "11" that represents an integer value, then <code>objectType</code> is
     * <code>http://www.w3.org/2001/XMLSchema#int</code>.
     * @param subject the full URI of the subject.
     * @param predicate the full URI of the predicate.
     * @param object the full URI if the object is the resource, the string representation of the object if it is a data value 
     * @param objectType the full URI that indicates the type of the object
     */
    void statement(String subject, String predicate, String object, String objectType);

    /**
     * Receive notification of a prefix declaration.
     * @param prefix the prefix string
     * @param uri the URI string
     */
    void prefix(String prefix, String uri);

    /**
     * Receive notification of the start of a formula. For any usual formula, the sequence of call is as follows:
     * <pre>
     *   startFormula()
     *   statement(...)
     *   statement(...)
     *   ...
     *   formulaConnector(...)
     *   statement(...)
     *   ...
     *   endFormula()
     * </pre>
     * The <code>statement(...)</code>s before the <code>formulaConnector(...)</code> represents the antecedent (or left hand side) of
     * a formula. The <code>statement(...)</code>s after the <code>formulaConnector(...)</code> represents the consequent
     * (or right hand side) of a formula. If the right hand side is empty(<code>{}</code>), <code>endFormula()</code> is
     * called without preceding <code>statement(...)</code> calls.
     */
    void startFormula();

    /**
     * Receive notification of the end of a formula.
     *
     */
    void endFormula();

    /**
     * Receive notification of the connector of a formula. For example, if you have <code>{?x ?p ?y} log:implies {?x ?p1 ?y}.</code>,
     * <code>log:implies</code> is the connector for this formula. <code>=></code> is implicitly converted to <code>log:implies</code>.
     * @param connector the URI string representation of the connector
     */
    void formulaConnector(String connector);
}
