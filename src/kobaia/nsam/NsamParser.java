/* Generated By:JavaCC: Do not edit this line. NsamParser.java */
package kobaia.nsam;

import java.net.*;
import java.io.*;
import java.util.*;

final class NsamParser implements NsamParserConstants {
        protected void setDocumentUrl(String url)
        {
                if (url == null)
                {
                        System.err.println("Document URL is not available. Set it to http://nsam.com/Default/anonymous.n3");
                        url = "http://nsam.com/Default/anonymous.n3";
                }
                this.docUrl = url;
        }

        protected String getDocumentUrl()
        {
                if (this.docUrl == null)
                {
                        System.err.println("Document URL is not available. Set it to http://nsam.com/Default/anonymous.n3");
                        this.docUrl = "http://nsam.com/Default/anonymous.n3";
                }
                return this.docUrl;
        }

        protected void setContentHandler(StatementHandler handler)
        {
                if (handler == null)
                {
                        System.err.println("StatementHandler reference is null! Nothing happens...");
                        return;
                }
                this.handler = handler;
        }

        protected String getURI(QName qname)
        {
                if ("_".equals(qname.pref)) return (qname.pref + ":" + qname.lname);
                if (prefixMap.get("") == null)
                {
                        String url = getDocumentUrl();
                        prefixMap.put("",
                                                 (url.endsWith("/") || url.endsWith("#"))? url : url + "#");
                }
                Object nsUri = prefixMap.get(qname.pref);
                if (nsUri == null && qname.pref != null && qname.pref.length() > 0)
                {
                        System.err.println("Unknown namespace prefix found: " + qname.pref + " : Cannot proceed.");
                        System.exit(-1);
                }
                if (nsUri == null) nsUri = "";
                //System.out.println("Full URI: " + nsUri + qname.lname);
                return (nsUri + qname.lname);
        }

        protected String getAnonymousId()
        {
                return (prefixMap.get("") + "anon" + count++);
        }

        protected String getBaseNamespace()
        {
                if (prefixMap.get("") == null)
                {
                        String url = getDocumentUrl();
                        prefixMap.put("",
                                                 (url.endsWith("/") || url.endsWith("#"))? url : url + "#");
                }
                return (String)prefixMap.get("");
        }


        protected String getURI(String uri)
        {
                return uri;
        }

        protected void pushSubject(String subject)
        {
                if (subject == null)
                {
                        System.err.println("Null Subject!!");
                        return;
                }
                this.subjects.addFirst(subject);
        }

        protected String currentSubject()
        {
                if (this.subjects.size() == 0) return null;
                return (String)this.subjects.getFirst();
        }

        protected String popSubject()
        {
                if (this.subjects.isEmpty()) return null;
                return (String)this.subjects.removeFirst();
        }

        protected void cleanSubjects()
        {
                this.subjects.clear();
        }

        protected void cleanPrefixMap()
        {
                this.prefixMap.clear();
        }

        private Map prefixMap = new HashMap();
        private StatementHandler handler = null;
        private LinkedList subjects = new LinkedList();
        private int count = 0;
        private String docUrl = null;

  final public void triples() throws ParseException {
        String s;
        String t = "";
        cleanPrefixMap();
        cleanSubjects();
    label_1:
    while (true) {
      if (jj_2_1(2)) {
        ;
      } else {
        break label_1;
      }
      if (jj_2_2(2)) {
        prefix();
        jj_consume_token(PERIOD);
      } else if (jj_2_3(2)) {
        triple();
        jj_consume_token(PERIOD);
                              popSubject();
      } else if (jj_2_4(2)) {
        formula();
        jj_consume_token(PERIOD);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    }
  }

  final public void prefix() throws ParseException {
        String prefix, uri;
        Token t1=null, t2=null;
        String s;
    jj_consume_token(PREFIX);
    if (jj_2_5(2)) {
      t1 = jj_consume_token(QNamePref);
      s = uri();
                prefix = t1.toString();
                prefix = prefix.substring(0,prefix.length()-1);
                prefixMap.put(prefix,s);
                this.handler.prefix(prefix,s);
    } else if (jj_2_6(2)) {
      jj_consume_token(COLON);
      s = uri();
                prefixMap.put("",s);
                this.handler.prefix("",s);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void triple() throws ParseException {
        String s, o, p;
        List props;
    if (jj_2_10(2)) {
      s = node();
                                                         //System.out.println("Subject: " + s); 
                                                                  pushSubject(s);
      propertyAndObjectList();
    } else if (jj_2_11(2)) {
      if (jj_2_7(2)) {
        s = brace();
                                                 pushSubject(s);
      } else if (jj_2_8(2)) {
        s = bracket();
                                                 pushSubject(s);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
      if (jj_2_9(2)) {
        propertyAndObjectList();
      } else {
        ;
      }
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public void formula() throws ParseException {
        QName qn;
        boolean yes = false;
    jj_consume_token(LBRACE);
                                                 this.handler.startFormula();
    tripleList();
    jj_consume_token(RBRACE);
                                                 popSubject();
    if (jj_2_12(2)) {
      qn = qName();
                                 this.handler.formulaConnector(getURI(qn));
    } else if (jj_2_13(2)) {
      jj_consume_token(46);
                                         this.handler.formulaConnector(LOG.IMPLIES);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(LBRACE);
    if (jj_2_14(2)) {
      tripleList();
                                 yes = true;
    } else {
      ;
    }
    jj_consume_token(RBRACE);
                                                 if (yes) popSubject();
                                                 this.handler.endFormula();
  }

  final public void tripleList() throws ParseException {
    triple();
    label_2:
    while (true) {
      if (jj_2_15(2)) {
        ;
      } else {
        break label_2;
      }
      jj_consume_token(PERIOD);
                                         popSubject();
      triple();
    }
  }

  final public void propertyAndObjectList() throws ParseException {
    propertyAndObject();
    label_3:
    while (true) {
      if (jj_2_16(2)) {
        ;
      } else {
        break label_3;
      }
      jj_consume_token(SEMICOLON);
      propertyAndObject();
    }
  }

  final public void propertyAndObject() throws ParseException {
        List l = new ArrayList();
        String p, o;
        String anonymous;
    p = verb();

    object(currentSubject(),p);
    label_4:
    while (true) {
      if (jj_2_17(2)) {
        ;
      } else {
        break label_4;
      }
      jj_consume_token(COMMA);
      object(currentSubject(),p);
    }
  }

  final public void object(String subj,String pred) throws ParseException {
        String o;
        Token t;
    if (jj_2_18(2)) {
      o = node();
                                                                  // System.out.println("Node Recoged.");
                                                                  String type = RDFS.RESOURCE;
                                                                  if (o.startsWith("?") || o.startsWith("_")) type = "VARIABLE";
                                                                  this.handler.statement(subj, pred, o, type);
    } else if (jj_2_19(2)) {
      t = jj_consume_token(STRING);
                                                          this.handler.statement(subj, pred, t.toString().substring(1,t.toString().length()-1), XSD.STRING);
    } else if (jj_2_20(2)) {
      t = jj_consume_token(TypedString);
                                                  // System.out.println("Typed String: " + t.toString());
                                                                  int index = t.toString().indexOf("^^");
                                                                  String dataString = t.toString().substring(1,index-1);
                                                                  String typeString = t.toString().substring(index+2);
                                                                  boolean notQName = typeString.indexOf("/") != -1 || typeString.indexOf("@") != -1;
                                                                  index = typeString.indexOf(":");
                                                                  QName qname = new QName(typeString.substring(0,index), typeString.substring(index+1));
                                                                  this.handler.statement(subj, pred, dataString, getURI(qname));
    } else if (jj_2_21(2)) {
      t = jj_consume_token(NUMBER);
                                                          this.handler.statement(subj, pred, t.toString(), XSD.INT);
    } else if (jj_2_22(2)) {
      t = jj_consume_token(REAL);
                                                          this.handler.statement(subj, pred, t.toString(), XSD.DOUBLE);
    } else if (jj_2_23(2)) {
      o = bracket();
                                                  this.handler.statement(subj, pred, o, RDFS.RESOURCE);
    } else if (jj_2_24(2)) {
      o = brace();
                                                          this.handler.statement(subj, pred, o, RDFS.RESOURCE);
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
  }

  final public String node() throws ParseException {
        String s;
        QName qname;
        Token t;
        String anonymous;
    if (jj_2_25(2)) {
      qname = qName();
                                  {if (true) return getURI(qname);}
    } else if (jj_2_26(2)) {
      s = uri();
                                          {if (true) return s;}
    } else if (jj_2_27(2)) {
      t = jj_consume_token(ThisDoc);
                                  {if (true) return getDocumentUrl();}
    } else if (jj_2_28(2)) {
      t = jj_consume_token(Var);
                                          {if (true) return t.toString();}
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public String verb() throws ParseException {
        String p;
        Token t;
    if (jj_2_35(2)) {
      if (jj_2_29(2)) {
        jj_consume_token(HAS);
      } else if (jj_2_30(2)) {
        jj_consume_token(GHAS);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
      p = node();
    } else if (jj_2_36(2)) {
      if (jj_2_31(2)) {
        jj_consume_token(IS);
      } else if (jj_2_32(2)) {
        jj_consume_token(GIS);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
      p = node();
      if (jj_2_33(2)) {
        jj_consume_token(OF);
      } else if (jj_2_34(2)) {
        jj_consume_token(GOF);
      } else {
        jj_consume_token(-1);
        throw new ParseException();
      }
    } else if (jj_2_37(2)) {
      p = node();
    } else if (jj_2_38(2)) {
      t = jj_consume_token(Name);
                 if (t.toString().equals("a")) p=RDF.TYPE; else {if (true) throw new ParseException("Encountered " + t.toString());}
    } else if (jj_2_39(2)) {
      jj_consume_token(47);
                                 p=OWL.SAMEAS;
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
        {if (true) return p;}
    throw new Error("Missing return statement in function");
  }

  final public String bracket() throws ParseException {
        String anonymous;
    jj_consume_token(LBRACKET);
                                                                  pushSubject(getAnonymousId());
    label_5:
    while (true) {
      if (jj_2_40(2)) {
        ;
      } else {
        break label_5;
      }
      propertyAndObjectList();
    }
    jj_consume_token(RBRACKET);
                                                          {if (true) return popSubject();}
    throw new Error("Missing return statement in function");
  }

  final public String brace() throws ParseException {
        String anonymous;
        String list, rest;
        String n;
    if (jj_2_42(2)) {
      jj_consume_token(LPAREN);
                                                                  list = getAnonymousId();
                                                                  //System.out.println("Paren Opened with " + list); 

      object(list, RDF.FIRST);
                                                                          anonymous = list;
      label_6:
      while (true) {
        if (jj_2_41(2)) {
          ;
        } else {
          break label_6;
        }
                                              rest = getAnonymousId();
        object(rest, RDF.FIRST);
                                                                          this.handler.statement(list, RDF.REST, rest, RDFS.RESOURCE);
                                                                          list = rest;
      }
      jj_consume_token(RPAREN);
                                                                  this.handler.statement(list, RDF.REST, RDF.NIL, RDFS.RESOURCE);
                                                                          {if (true) return anonymous;}
    } else if (jj_2_43(2)) {
      jj_consume_token(LPAREN);
      jj_consume_token(RPAREN);
                                                                                {if (true) return RDF.NIL;}
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public QName qName() throws ParseException {
        Token p=null, n=null;
    if (jj_2_44(2)) {
      p = jj_consume_token(QName);
          //System.out.println("QName: " + (p==null?"":p.toString())); 
         int index = p.toString().indexOf(":");
         {if (true) return new QName(p.toString().substring(0,index),p.toString().substring(index+1));}
    } else if (jj_2_45(2)) {
      jj_consume_token(COLON);
        {if (true) return new QName("","");}
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  final public String uri() throws ParseException {
        Token t;
    if (jj_2_46(2)) {
      t = jj_consume_token(URIRef);
                {if (true) return t.toString().substring(1,t.toString().length()-1);}
    } else if (jj_2_47(2)) {
      t = jj_consume_token(FragID);
                {if (true) return t.toString().substring(1,t.toString().length()-1);}
    } else {
      jj_consume_token(-1);
      throw new ParseException();
    }
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_3(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_3(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_4(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_4(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_5(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_5(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_6(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_6(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_7(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_7(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_8(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_8(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_9(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_9(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_10(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_10(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_11(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_11(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_12(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_12(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_13(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_13(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_14(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_14(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_15(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_15(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_16(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_16(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_17(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_17(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_18(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_18(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_19(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_19(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_20(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_20(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_21(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_21(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_22(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_22(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_23(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_23(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_24(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_24(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_25(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_25(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_26(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_26(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_27(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_27(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_28(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_28(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_29(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_29(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_30(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_30(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_31(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_31(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_32(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_32(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_33(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_33(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_34(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_34(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_35(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_35(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_36(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_36(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_37(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_37(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_38(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_38(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_39(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_39(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_40(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_40(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_41(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_41(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_42(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_42(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_43(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_43(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_44(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_44(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_45(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_45(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_46(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_46(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_2_47(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_47(); }
    catch(LookaheadSuccess ls) { return true; }
  }

  private boolean jj_3_9() {
    if (jj_3R_13()) return true;
    return false;
  }

  private boolean jj_3_8() {
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3R_10() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_46()) {
    jj_scanpos = xsp;
    if (jj_3_47()) return true;
    }
    return false;
  }

  private boolean jj_3_46() {
    if (jj_scan_token(URIRef)) return true;
    return false;
  }

  private boolean jj_3_41() {
    if (jj_3R_18()) return true;
    return false;
  }

  private boolean jj_3_7() {
    if (jj_3R_11()) return true;
    return false;
  }

  private boolean jj_3_11() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_7()) {
    jj_scanpos = xsp;
    if (jj_3_8()) return true;
    }
    return false;
  }

  private boolean jj_3R_8() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_10()) {
    jj_scanpos = xsp;
    if (jj_3_11()) return true;
    }
    return false;
  }

  private boolean jj_3_10() {
    if (jj_3R_14()) return true;
    if (jj_3R_13()) return true;
    return false;
  }

  private boolean jj_3_24() {
    if (jj_3R_11()) return true;
    return false;
  }

  private boolean jj_3_23() {
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3_22() {
    if (jj_scan_token(REAL)) return true;
    return false;
  }

  private boolean jj_3_45() {
    if (jj_scan_token(COLON)) return true;
    return false;
  }

  private boolean jj_3_21() {
    if (jj_scan_token(NUMBER)) return true;
    return false;
  }

  private boolean jj_3R_15() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_44()) {
    jj_scanpos = xsp;
    if (jj_3_45()) return true;
    }
    return false;
  }

  private boolean jj_3_44() {
    if (jj_scan_token(QName)) return true;
    return false;
  }

  private boolean jj_3_20() {
    if (jj_scan_token(TypedString)) return true;
    return false;
  }

  private boolean jj_3_6() {
    if (jj_scan_token(COLON)) return true;
    if (jj_3R_10()) return true;
    return false;
  }

  private boolean jj_3_19() {
    if (jj_scan_token(STRING)) return true;
    return false;
  }

  private boolean jj_3_43() {
    if (jj_scan_token(LPAREN)) return true;
    if (jj_scan_token(RPAREN)) return true;
    return false;
  }

  private boolean jj_3R_18() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_18()) {
    jj_scanpos = xsp;
    if (jj_3_19()) {
    jj_scanpos = xsp;
    if (jj_3_20()) {
    jj_scanpos = xsp;
    if (jj_3_21()) {
    jj_scanpos = xsp;
    if (jj_3_22()) {
    jj_scanpos = xsp;
    if (jj_3_23()) {
    jj_scanpos = xsp;
    if (jj_3_24()) return true;
    }
    }
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3_18() {
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3_17() {
    if (jj_scan_token(COMMA)) return true;
    if (jj_3R_18()) return true;
    return false;
  }

  private boolean jj_3_5() {
    if (jj_scan_token(QNamePref)) return true;
    if (jj_3R_10()) return true;
    return false;
  }

  private boolean jj_3R_7() {
    if (jj_scan_token(PREFIX)) return true;
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_5()) {
    jj_scanpos = xsp;
    if (jj_3_6()) return true;
    }
    return false;
  }

  private boolean jj_3_4() {
    if (jj_3R_9()) return true;
    return false;
  }

  private boolean jj_3_3() {
    if (jj_3R_8()) return true;
    return false;
  }

  private boolean jj_3_1() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_2()) {
    jj_scanpos = xsp;
    if (jj_3_3()) {
    jj_scanpos = xsp;
    if (jj_3_4()) return true;
    }
    }
    return false;
  }

  private boolean jj_3_2() {
    if (jj_3R_7()) return true;
    return false;
  }

  private boolean jj_3R_11() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_42()) {
    jj_scanpos = xsp;
    if (jj_3_43()) return true;
    }
    return false;
  }

  private boolean jj_3_42() {
    if (jj_scan_token(LPAREN)) return true;
    if (jj_3R_18()) return true;
    return false;
  }

  private boolean jj_3R_17() {
    if (jj_3R_19()) return true;
    if (jj_3R_18()) return true;
    return false;
  }

  private boolean jj_3_34() {
    if (jj_scan_token(GOF)) return true;
    return false;
  }

  private boolean jj_3_16() {
    if (jj_scan_token(SEMICOLON)) return true;
    if (jj_3R_17()) return true;
    return false;
  }

  private boolean jj_3_33() {
    if (jj_scan_token(OF)) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_3R_17()) return true;
    return false;
  }

  private boolean jj_3_40() {
    if (jj_3R_13()) return true;
    return false;
  }

  private boolean jj_3R_12() {
    if (jj_scan_token(LBRACKET)) return true;
    Token xsp;
    while (true) {
      xsp = jj_scanpos;
      if (jj_3_40()) { jj_scanpos = xsp; break; }
    }
    if (jj_scan_token(RBRACKET)) return true;
    return false;
  }

  private boolean jj_3_15() {
    if (jj_scan_token(PERIOD)) return true;
    if (jj_3R_8()) return true;
    return false;
  }

  private boolean jj_3R_16() {
    if (jj_3R_8()) return true;
    return false;
  }

  private boolean jj_3_30() {
    if (jj_scan_token(GHAS)) return true;
    return false;
  }

  private boolean jj_3_32() {
    if (jj_scan_token(GIS)) return true;
    return false;
  }

  private boolean jj_3_29() {
    if (jj_scan_token(HAS)) return true;
    return false;
  }

  private boolean jj_3_39() {
    if (jj_scan_token(47)) return true;
    return false;
  }

  private boolean jj_3_35() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_29()) {
    jj_scanpos = xsp;
    if (jj_3_30()) return true;
    }
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3_38() {
    if (jj_scan_token(Name)) return true;
    return false;
  }

  private boolean jj_3_14() {
    if (jj_3R_16()) return true;
    return false;
  }

  private boolean jj_3_31() {
    if (jj_scan_token(IS)) return true;
    return false;
  }

  private boolean jj_3_37() {
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3_36() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_31()) {
    jj_scanpos = xsp;
    if (jj_3_32()) return true;
    }
    if (jj_3R_14()) return true;
    return false;
  }

  private boolean jj_3_13() {
    if (jj_scan_token(46)) return true;
    return false;
  }

  private boolean jj_3_12() {
    if (jj_3R_15()) return true;
    return false;
  }

  private boolean jj_3R_19() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_35()) {
    jj_scanpos = xsp;
    if (jj_3_36()) {
    jj_scanpos = xsp;
    if (jj_3_37()) {
    jj_scanpos = xsp;
    if (jj_3_38()) {
    jj_scanpos = xsp;
    if (jj_3_39()) return true;
    }
    }
    }
    }
    return false;
  }

  private boolean jj_3R_9() {
    if (jj_scan_token(LBRACE)) return true;
    if (jj_3R_16()) return true;
    return false;
  }

  private boolean jj_3R_14() {
    Token xsp;
    xsp = jj_scanpos;
    if (jj_3_25()) {
    jj_scanpos = xsp;
    if (jj_3_26()) {
    jj_scanpos = xsp;
    if (jj_3_27()) {
    jj_scanpos = xsp;
    if (jj_3_28()) return true;
    }
    }
    }
    return false;
  }

  private boolean jj_3_25() {
    if (jj_3R_15()) return true;
    return false;
  }

  private boolean jj_3_28() {
    if (jj_scan_token(Var)) return true;
    return false;
  }

  private boolean jj_3_47() {
    if (jj_scan_token(FragID)) return true;
    return false;
  }

  private boolean jj_3_27() {
    if (jj_scan_token(ThisDoc)) return true;
    return false;
  }

  private boolean jj_3_26() {
    if (jj_3R_10()) return true;
    return false;
  }

  /** Generated Token Manager. */
  public NsamParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;

  /** Constructor with InputStream. */
  public NsamParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public NsamParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new NsamParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Constructor. */
  public NsamParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new NsamParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
  }

  /** Constructor with generated Token Manager. */
  public NsamParser(NsamParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  /** Reinitialise. */
  public void ReInit(NsamParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      return token;
    }
    token = oldToken;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    Token errortok = token.next;
    int line = errortok.beginLine, column = errortok.beginColumn;
    String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
    return new ParseException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}

class QName
{
        public QName(String pref, String lname)
        {
//		assert pref != null && lname != null;
                this.pref = pref;
                this.lname = lname;
        }

        public String pref = null;
        public String lname = null;
}
