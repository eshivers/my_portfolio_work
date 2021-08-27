/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student Names:
 * Description: Prg01 - Molecule Class
 */

/**
 * Molecules form when two or more atoms form chemical bonds with each other.
 */
public class Molecule {

    private Node   head;
    private String name;

    // TODO: initialize the molecule with the information provided by the user
    public Molecule(final String name) {

    }

    // TODO: returns the name of the molecule
    public String getName() {
        return null; // placeholder so the code compliles
    }

    // TODO: returns true/false depending whether the molecule is empty or not
    public boolean isEmpty() {
        return false; // placeholder so the code compliles
    }

    // TODO: The elements in a molecule must be distinct; therefore, the add method should check if an element is already in the molecule before adding it; also, the elements in a molecule should respect the Hill system, which states that carbon atoms are listed first, hydrogen atoms next, and then all other elements are added in alphabetical order
    public void add(final Element element, int amount) {

    }

    // TODO: adds the given element, assuming that the amount is Node.DEFAULT_AMOUNT which is 1
    public void add(final Element element) {

    }

    // TODO: returns true/false depending whether the target element can be found in the molecule or not
    public boolean contains(final Element target) {
        return false; // placeholder so the code compliles
    }

    // TODO: returns the number of elements in the molecule
    public int size() {
        return 0; // placeholder so the code compliles
    }

    // TODO: returns the element at the given index location (null if the index is invalid)
    public Element get(int index) {
       return null; // placeholder so the code compliles
    }

    // TODO: returns a textual representation of the molecule
    @Override
    public String toString() {
        return ""; // placeholder so the code compliles
    }
}
