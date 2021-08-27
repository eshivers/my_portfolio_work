/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student(s) Name(s): Erica Shivers
 * Description: Prg 02 - KeyedObject class
 */

/** a KeyedObject models objects with a key (a string) and some content (also a string); because it implements the HasKey interface, it must override the getKey method (which returns the keyed object's key) */
public class KeyedObject implements HasKey {

    private String key;
    private String content;

    public KeyedObject(String key, String content) {
        this.key = key;
        this.content = content;
    }

    // TODO: overrides the getKey method defined in the HasKey interface
    @Override
    public String getKey() {
        return this.key;
    }

    // TODO: implement the getContent method
    public String getContent() {
        return this.content;
    }

    // TODO: implement the setContent method
    public void setContent(String content) {
        this.content = content;
    }

    // TODO: override Object's clone by returning a new KeyedObject with the same key and content of the method's callee object
    @Override
    protected Object clone() {
        return new KeyedObject(this.getKey(),this.getContent());
    }

    // TODO: override Object's toString, returning a string representation of the (callee) keyed object
    @Override
    public String toString() {
        return "KeyedObject{key='" + this.getKey() + "', content='" + this.getContent() + "'}";
    }
}
