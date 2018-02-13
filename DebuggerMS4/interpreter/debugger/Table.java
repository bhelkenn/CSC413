package interpreter.debugger;
import java.util.HashMap;

/** <pre>
 *  Binder objects group 3 fields
 *  1. a value
 *  2. the next link in the chain of strings in the current scope
 *  3. the next link of a previous Binder for the same identifier
 *     in a previous scope
 *  </pre>
*/
class Binder {
  private int value;
  private String prevtop;   // prior string in same scope
  private Binder tail;      // prior binder for same string
                            // restore this when closing scope
  Binder(int v, String p, Binder t) {
	value=v; prevtop=p; tail=t;
  }

  int getValue() { return value; }
  String getPrevtop() { return prevtop; }
  Binder getTail() { return tail; }
}


/** <pre>
 * The Table class is similar to java.util.Dictionary, except that
 * each key must be a String and there is a scope mechanism.
 *
 * Consider the following sequence of events for table t:
 * t.put(String("a"),5)
 * t.beginScope()
 * t.put(String("b"),7)
 * t.put(String("a"),9)
 * 
 * strings will have the key/value pairs for Strings "a" and "b" as:
 * 
 * String("a") ->
 *     Binder(9, String("b") , Binder(5, null, null) )
 * (the second field has a reference to the prior String added in this
 * scope; the third field refers to the Binder for the String("a")
 * included in the prior scope)
 * Binder has 2 linked lists - the second field contains list of strings
 * added to the current scope; the third field contains the list of
 * Binders for the Strings with the same string id - in this case, "a"
 * 
 * String("b") ->
 *     Binder(7, null, null)
 * (the second field is null since there are no other strings to link
 * in this scope; the third field is null since there is no String("b")
 * in prior scopes)
 * 
 * top has a reference to String("a") which was the last string added
 * to current scope
 * 
 * Note: What happens if a string is defined twice in the same scope??
 * </pre>
*/
public class Table {

  private HashMap<String, Binder> strings = new HashMap();
  private String top;    // reference to last string added to
                         // current scope; this essentially is the
                         // start of a linked list of strings in scope
  private Binder marks;  // scope mark; essentially we have a stack of
                         // marks - push for new scope; pop when closing
                         // scope

  public Table(){}

 /**
  * Gets the int associated with the specified string in the Table.
  */
  public int get(String key) {
	return strings.get(key).getValue();
  }

 /**
  * Puts the specified value into the Table, bound to the specified String.<br>
  * Maintain the list of strings in the current scope (top);<br>
  * Add to list of strings in prior scope with the same string identifier
  */
  public void put(String key, int value) {
	strings.put(key, new Binder(value, top, strings.get(key)));
	top = key;
  }

 /**
  * Remembers the current state of the Table; push new mark on mark stack
  */
  public void beginScope() {
    marks = new Binder(-1,top,marks);
    top=null;
  }

 /**
  * Restores the table to what it was at the most recent beginScope
  *	that has not already been ended.
  */
  public void endScope() {
	while (top!=null) {
	   Binder e = strings.get(top);
	   if (e.getTail()!=null) strings.put(top,e.getTail());
	   else strings.remove(top);
	   top = e.getPrevtop();
	}
	top=marks.getPrevtop();
	marks=marks.getTail();
  }

  /**
   * @return a set of the Table's strings.
   */
  public java.util.Set<String> keys() {return strings.keySet();}
  public String getTop() {return top;}
  public void doPop(int n) {
      if (top != null) {
        for (int i = 0; i < n && top != null; i++) {
            Binder e = strings.get(top);
            if (e.getTail() != null) strings.put(top,e.getTail());
            else strings.remove(top);
            top = e.getPrevtop();
        }
      }
  }
}
