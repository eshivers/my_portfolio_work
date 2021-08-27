/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Student Names: Erica Shivers
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
        this.name = name;
        this.head = null;
    }

    // TODO: returns the name of the molecule
    public String getName() {
        return this.name; // placeholder so the code compliles
    }

    // TODO: returns true/false depending whether the molecule is empty or not
    public boolean isEmpty() {
        return this.head == null; // placeholder so the code compliles
    }

    // TODO: The elements in a molecule must be distinct; therefore, the add method should check if an element is already in the molecule before adding it; also, the elements in a molecule should respect the Hill system, which states that carbon atoms are listed first, hydrogen atoms next, and then all other elements are added in alphabetical order
    public void add(final Element element, int amount) {
        if(!this.contains(element)){
            Node new_node = new Node(element,amount);
            if(this.head == null){
                this.head = new Node(element,amount);
            } else if(element.getAtomLetter() == "C"){
                new_node.setNext(this.head);
                this.head = new_node;
            } else if(element.getAtomLetter() == "H" && this.head.getElement().getAtomLetter() != "C"){
                new_node.setNext(this.head);
                this.head = new_node;
            } else if(element.getAtomLetter() == "H" && this.head.getElement().getAtomLetter() == "C"){
                Node temp = this.head.getNext();
                new_node.setNext(temp);
                this.head.setNext(new_node);
            } else {
                Node current = this.head;
                if(current.getNext() == null && current.getElement().getAtomLetter().compareTo(element.getAtomLetter()) < 0){
                    current.setNext(new_node);
                    return;
                } else if (current.getNext() == null && current.getElement().getAtomLetter().compareTo(element.getAtomLetter()) > 0){
                    new_node.setNext(this.head);
                    this.head = new_node;
                    return;
                }
                Node prev = this.head;
                while(current.getNext() != null &&
                        (current.getElement().getAtomLetter() == "C" ||
                        current.getElement().getAtomLetter() == "H" ||
                        current.getElement().getAtomLetter().compareTo(element.getAtomLetter()) < 0)
                    && !this.contains(element)){
                    prev = current;
                    current = current.getNext();
                }

                if(current.getElement().getAtomLetter() != "C" &&
                    current.getElement().getAtomLetter() != "H" &&
                    current.getElement().getAtomLetter().compareTo(element.getAtomLetter()) > 0){
                    new_node.setNext(current);
                    prev.setNext(new_node);
                } else {
                    new_node.setNext(current.getNext());
                    current.setNext(new_node);
                    prev.setNext(current);
                }
            }
        } else {
            return;
        }
    }

    // TODO: adds the given element, assuming that the amount is Node.DEFAULT_AMOUNT which is 1
    public void add(final Element element) {
        add(element,Node.DEFAULT_AMOUNT);
    }

    // TODO: returns true/false depending whether the target element can be found in the molecule or not
    public boolean contains(final Element target) {
        // placeholder so the code compliles
        Node current = this.head;
        while(current != null){
            if(current.getElement().equals(target)){
                return true;
            }
            current = current.getNext();
        }
        return false;
    }

    // TODO: returns the number of elements in the molecule
    public int size() {
        // placeholder so the code compliles
        int counter = 0;
        Node current = this.head;
        while(current != null) {
            counter++;
            current = current.getNext();
        }
        return counter;
    }

    // TODO: returns the element at the given index location (null if the index is invalid)
    public Element get(int index) {
        Node current = head;
        int count = 0;
        while (current != null) {
            if (count == index)
                return current.getElement();
            count++;
            current = current.getNext();
        }
        return null;
    }

    // TODO: returns a textual representation of the molecule
    @Override
    public String toString() {
        // placeholder so the code compliles
        String result = "\"" + this.getName() + "\":";
        Node current = head;
        while (current != null) {
            result += current.getElement().getAtomLetter();
            if(current.getAmount() > 1) {
                result += "_" + current.getAmount();
            }
            current = current.getNext();
        }
        return result;
    }

}
