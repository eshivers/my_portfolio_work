import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoleculeTest {

    @Test
    public void testSimpleMolecules() {
        // simple carbon
        Molecule m1 = new Molecule("");
        assertTrue(m1.isEmpty());
        assertEquals(0, m1.size());
        m1.add(new Element("C"));
        assertFalse(m1.isEmpty());
        assertEquals(1, m1.size());
        assertEquals(new Element("C"), m1.get(0));
        // simple hydrogen
        Molecule m2 = new Molecule("");
        m2.add(new Element("H"));
        assertFalse(m2.isEmpty());
        assertEquals(1, m2.size());
        assertEquals(new Element("H"), m2.get(0));
        // simple nitrogen
        Molecule m3 = new Molecule("");
        m3.add(new Element("N"));
        assertFalse(m3.isEmpty());
        assertEquals(1, m3.size());
        assertEquals(new Element("N"), m3.get(0));
        // simple oxygen
        Molecule m4 = new Molecule("");
        m4.add(new Element("O"));
        assertFalse(m4.isEmpty());
        assertEquals(1, m4.size());
        assertEquals(new Element("O"), m4.get(0));
    }

    @Test
    public void testAddingCarbonToMolecule() {
        // molecule does not have carbon initially
        Molecule m1 = new Molecule("");
        m1.add(new Element("O"));
        m1.add(new Element("C"));
        assertEquals(2, m1.size());
        assertEquals(new Element("C"), m1.get(0)); // carbon becomes 1st
        assertEquals(new Element("O"), m1.get(1)); // oxygen becomes 2nd
        // molecule already has carbon initially
        Molecule m2 = new Molecule("");
        m2.add(new Element("C"));
        m2.add(new Element("C")); // trying to add carbon again
        assertEquals(1, m2.size());
        assertEquals(new Element("C"), m2.get(0));
    }

    @Test
    public void testAddingHydrogenToMolecule() {
        // molecule does not have hydrogen initially
        Molecule m1 = new Molecule("");
        m1.add(new Element("O"));
        m1.add(new Element("H"));
        assertEquals(2, m1.size());
        assertEquals(new Element("H"), m1.get(0)); // hydrogen becomes 1st
        assertEquals(new Element("O"), m1.get(1)); // oxygen becomes 2nd
        // molecule already has hydrogen initially
        Molecule m2 = new Molecule("");
        m2.add(new Element("H"));
        m2.add(new Element("H")); // trying to add hydrogen again
        assertEquals(1, m2.size());
        assertEquals(new Element("H"), m2.get(0));
        // adding hydrogen in a molecule that has carbon
        Molecule m3 = new Molecule("");
        m3.add(new Element("C"));
        m3.add(new Element("H"));
        assertEquals(2, m3.size());
        assertEquals(new Element("C"), m3.get(0)); // carbon remains 1st
        assertEquals(new Element("H"), m3.get(1)); // hydrogen added as 2nd
        // adding hydrogen in a molecule that has carbon and some other element
        Molecule m4 = new Molecule("");
        m4.add(new Element("C"));
        m4.add(new Element("O"));
        m4.add(new Element("H"));
        assertEquals(3, m4.size());
        assertEquals(new Element("C"), m4.get(0)); // carbon remains 1st
        assertEquals(new Element("H"), m4.get(1)); // hydrogen added as 2nd
        assertEquals(new Element("O"), m4.get(2)); // oxygen is now 3rd
    }

    @Test
    public void testCaffeineMolecule() {
        Element C = new Element("C", "carbon");
        Element O = new Element("O", "oxygen");
        Element N = new Element("N", "nitrogen");
        Element H = new Element("H", "hydrogen");
        // test 1: C_8H_10N_4O_2
        Molecule m1 = new Molecule("");
        m1.add(C); m1.add(H); m1.add(N); m1.add(O);
        assertEquals(4, m1.size());
        assertEquals(C, m1.get(0));
        assertEquals(H, m1.get(1));
        assertEquals(N, m1.get(2));
        assertEquals(O, m1.get(3));
        // test 2: H_10C_8N_4O_2
        Molecule m2 = new Molecule("");
        m2.add(H); m2.add(C); m2.add(N); m2.add(O);
        assertEquals(4, m2.size());
        assertEquals(C, m2.get(0));
        assertEquals(H, m2.get(1));
        assertEquals(N, m2.get(2));
        assertEquals(O, m2.get(3));
        // test 3: C_8N_4O_2H_10
        Molecule m3 = new Molecule("");
        m3.add(C); m3.add(N); m3.add(O); m3.add(H);
        assertEquals(4, m3.size());
        assertEquals(C, m3.get(0));
        assertEquals(H, m3.get(1));
        assertEquals(N, m3.get(2));
        assertEquals(O, m3.get(3));
        // test 4: O_2H_10C_8N_4
        Molecule m4 = new Molecule("");
        m4.add(O); m4.add(H); m4.add(C); m4.add(N);
        assertEquals(4, m4.size());
        assertEquals(C, m4.get(0));
        assertEquals(H, m4.get(1));
        assertEquals(N, m4.get(2));
        assertEquals(O, m4.get(3));
        // test 5: O_2C_8N_4H_10
        Molecule m5 = new Molecule("");
        m5.add(O); m5.add(C); m5.add(N); m5.add(H);
        assertEquals(4, m5.size());
        assertEquals(C, m5.get(0));
        assertEquals(H, m5.get(1));
        assertEquals(N, m5.get(2));
        assertEquals(O, m5.get(3));
    }

    @Test
    public void testAddingSameElementToMolecule() {
        Element C = new Element("C", "carbon");
        Element O = new Element("O", "oxygen");
        Element N = new Element("N", "nitrogen");
        Element H = new Element("H", "hydrogen");
        Molecule caffeine = new Molecule("");
        caffeine.add(C); caffeine.add(H); caffeine.add(N); caffeine.add(O);
        // trying to add C
        caffeine.add(C);
        assertEquals(4, caffeine.size());
        assertEquals(C, caffeine.get(0));
        assertEquals(H, caffeine.get(1));
        assertEquals(N, caffeine.get(2));
        assertEquals(O, caffeine.get(3));
        // trying to add H
        caffeine.add(H);
        assertEquals(4, caffeine.size());
        assertEquals(C, caffeine.get(0));
        assertEquals(H, caffeine.get(1));
        assertEquals(N, caffeine.get(2));
        assertEquals(O, caffeine.get(3));
        // trying to add N
        caffeine.add(N);
        assertEquals(4, caffeine.size());
        assertEquals(C, caffeine.get(0));
        assertEquals(H, caffeine.get(1));
        assertEquals(N, caffeine.get(2));
        assertEquals(O, caffeine.get(3));
        // trying to add O
        caffeine.add(O);
        assertEquals(4, caffeine.size());
        assertEquals(C, caffeine.get(0));
        assertEquals(H, caffeine.get(1));
        assertEquals(N, caffeine.get(2));
        assertEquals(O, caffeine.get(3));
    }
}