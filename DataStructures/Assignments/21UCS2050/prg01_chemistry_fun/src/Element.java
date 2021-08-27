/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student Names:
 * Description: Prg01 - Element Class
 */

/**
 * A chemical element is a pure substance that consists of one type of atom. Users can create an element by informing its atom letter and (optionally) a name. Atom letters are always written in upper case. For example, element "C" is named "carbon".
 */
public class Element {

    private String atomLetter;
    private String name;

    public Element(String atomLetter, String name) {
        this.atomLetter = atomLetter.toUpperCase();
        this.name = name.toLowerCase();
    }

    public Element(String atomLetter) {
        this(atomLetter, "");
    }

    public String getAtomLetter() {
        return atomLetter;
    }

    public String getName() {
        return name;
    }

    // TODO: two elements are considered to be equal if they have the same atom letter.
    @Override
    public boolean equals(final Object obj) {
        return false; // placeholder so the code compiles
    }

    @Override
    public String toString() {
        return "Element{" +
                "'" + atomLetter + "'" +
                ", name='" + name + '\'' +
                '}';
    }
}
