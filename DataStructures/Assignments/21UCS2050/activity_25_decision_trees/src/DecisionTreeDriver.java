/*
 * CS2050 - Computer Science II - Summer 2021
 * Instructor: Thyago Mota
 * Description: Activity 25 - DecisionTreeDriver
 */

import java.io.*;
import java.util.*;

public class DecisionTreeDriver {

    private static final String DATA_FOLDER = "datasets";
    private static final String FILE_NAME = "realestate.csv";
    public static final boolean DEBUG = false;

    // reads the input file and return a list of data point objects
    public static LinkedList<DataPoint> loadData() throws FileNotFoundException {
        LinkedList<DataPoint> dps = new LinkedList<>();
        Scanner in = new Scanner(new FileInputStream(DATA_FOLDER + "/" + FILE_NAME));
        in.nextLine(); // ignore header line (always)
        LinkedList<Integer> targets = new LinkedList<>();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            String data[] = line.split(",");
            int target = Integer.parseInt(data[data.length-1]);
            if (!targets.contains(target))
                targets.append(target);
            DataPoint dp = new DataPoint(data.length - 1, target);
            for (int i = 0; i < data.length - 1; i++)
                dp.setAttribute(i, Double.parseDouble(data[i]));
            dps.append(dp);
        }
        in.close();
        Iterator<DataPoint> it = dps.iterator();
        while (it.hasNext()) {
            DataPoint dp = it.next();
            dp.setNumberTargets(targets.size());
        }
        return dps;
    }

    // computes the entropy of a given list of data point objects
    public static double entropy(final LinkedList<DataPoint> dps) {
        Iterator<DataPoint> it = dps.iterator();
        DataPoint dp = it.next();
        int numberTargets = dp.getNumberTargets();
        int counts[] = new int[numberTargets];
        counts[dp.getTarget()]++;
        int total = 1;
        while (it.hasNext()) {
            dp = it.next();
            total++;
            counts[dp.getTarget()]++;
        }
        double entropy = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] != 0) {
                double p = (double) counts[i] / total;
                entropy += (-1) * p * Math.log(p);
            }
        }
        return entropy;
    }

    // implements the ID3 algorithm from a given list of data point objects and another list of attribute indices
    // returns a decision tree
    public static DecisionTree id3(LinkedList<DataPoint> dps, LinkedList<Integer> attributes) {

        if (DEBUG) {
            System.out.println("dps = " + dps.size() + " " + dps);
            System.out.println("attributes = " + attributes);
        }

        // creates a tree
        DecisionTree tree = new DecisionTree();

        // checks if all data points have the same target value
        Iterator<DataPoint> dpsIt = dps.iterator();
        DataPoint dp = dpsIt.next();
        int target = dp.getTarget();
        int numberTargets = dp.getNumberTargets();
        boolean sameTarget = true;
        while (dpsIt.hasNext()) {
            dp = dpsIt.next();
            if (dp.getTarget() != target) {
                sameTarget = false;
                break;
            }
        }

        // if all data points have the same target, labels the tree with the target and returns the tree
        if (sameTarget) {
            if (DEBUG) {
                System.out.println("same target value found!");
                System.out.println("{target = " + target + "}");
            }
            tree.setRoot(new Target(target));
            return tree;
        }

        // if the list of attributes is empty, labels the tree with the most common target and returns the tree
        if (attributes.isEmpty()) {
            int targetCounts[] = new int[numberTargets];
            dpsIt = dps.iterator();
            while (dpsIt.hasNext()) {
                dp = dpsIt.next();
                targetCounts[dp.getTarget()]++;
            }
            target = 0;
            for (int i = 1; i < targetCounts.length; i++) {
                if (targetCounts[i] > targetCounts[target])
                    target = i;
            }

            if (DEBUG)
                System.out.println("{target = " + target + "}");

            tree.setRoot(new Target(target));
            return tree;
        }

        // finds the pair of attribute index and value that best classifies the data points
        double entropy = entropy(dps); // current entropy value
        if (DEBUG)
            System.out.println(entropy);
        double infoGain  = -1;
        int    bestAttr  = -1;
        double bestValue = -1;
        LinkedList<DataPoint> left = new LinkedList<>();
        LinkedList<DataPoint> right = new LinkedList<>();
        Iterator<Integer> attrIt = attributes.iterator();
        while (attrIt.hasNext()) {

            int attribute = attrIt.next();

            // 1st gets all possible attribute values
            LinkedList<Double> attributeValues = new LinkedList<>();
            dpsIt = dps.iterator();
            while (dpsIt.hasNext()) {
                dp = dpsIt.next();
                if (!attributeValues.contains(dp.getAttribute(attribute)))
                    attributeValues.append(dp.getAttribute(attribute));
            }

            // then evaluate each attribute value as a potential split attribute
            Iterator<Double> valIt = attributeValues.iterator();
            while (valIt.hasNext()) {

                // value is the current attribute value
                double value = valIt.next();

                // let's use tempLeft and tempRight to split the list of data point objects
                LinkedList<DataPoint> tempLeft = new LinkedList<>();
                LinkedList<DataPoint> tempRight = new LinkedList<>();

                // separates data points based on attribute (index and value) pair
                dpsIt = dps.iterator();
                while (dpsIt.hasNext()) {
                    dp = dpsIt.next();
                    if (dp.getAttribute(attribute) <= value)
                        tempLeft.append(dp);
                    else
                        tempRight.append(dp);
                }

                // if either side of the split is empty, that means that the split failed, so skip it!
                if (tempLeft.size() == 0 || tempRight.size() == 0)
                    continue;

                // now let's compute the information gain
                double currentEntropy = (entropy(tempLeft) + entropy(tempRight)) / 2;
                if (DEBUG) {
                    System.out.println("leftEntropy: " + entropy(tempLeft));
                    System.out.println("rightEntropy: " + entropy(tempRight));
                    System.out.println("currentEntropy = " + currentEntropy);
                }
                if (entropy - currentEntropy > infoGain) {
                    infoGain = entropy - currentEntropy;
                    if (DEBUG)
                        System.out.println("infoGain = " + infoGain);
                    bestAttr = attribute;
                    bestValue = value;

                    // now let's update left and right with the split
                    left.clear();
                    Iterator<DataPoint> itLeft = tempLeft.iterator();
                    while (itLeft.hasNext())
                        left.append(itLeft.next());
                    Iterator<DataPoint> itRight = tempRight.iterator();
                    right.clear();
                    while (itRight.hasNext())
                        right.append(itRight.next());
                }
            }
        } // end of finding best attribute/value

        if (DEBUG) {
            System.out.println("left: " + left.size() + " " + left);
            System.out.println("right: " + right.size() + " " + right);
        }

        // if left or right is empty, labels the tree with the most common target
        // found (on left or right) and returns the tree
        if (left.size() == 0 || right.size() == 0) {
            LinkedList<DataPoint> nonEmpty = left.size() == 0? right : left;
            int targetCounts[] = new int[numberTargets];
            dpsIt = nonEmpty.iterator();
            while (dpsIt.hasNext()) {
                dp = dpsIt.next();
                targetCounts[dp.getTarget()]++;
            }
            target = 0;
            for (int i = 1; i < targetCounts.length; i++) {
                if (targetCounts[i] > targetCounts[target])
                    target = i;
            }
            tree.setRoot(new Target(target));
            if (DEBUG)
                System.out.println("{target = " + target + "}");
            return tree;
        }

        // sets the label and call recursively to the left and right
        // don't forget to remove the attribute already evaluated!
        Decision tuple = new Decision(bestAttr, bestValue);
        tree.setRoot(new Decision(bestAttr, bestValue));
        if (DEBUG)
            System.out.println("<" + bestAttr + ", " + bestValue + ">");
        attributes.remove(bestAttr);
        tree.setLeft(id3(left, (LinkedList<Integer>) attributes.clone()));
        tree.setRight(id3(right, (LinkedList<Integer>) attributes.clone()));

        // returns the tree
        return tree;
    }

    // main method
    public static void main(String[] args) throws FileNotFoundException {

        // data points initialization
        LinkedList<DataPoint> dps = loadData();

        // gets the list of attributes (assume all data points have the same attributes
        DataPoint dp = dps.getFirst();
        LinkedList<Integer> attributes = new LinkedList<>();
        for (int i = 0; i < dp.getNumberAttributes(); i++)
            attributes.append(i);

        // call id3
        DecisionTree tree = id3(dps, attributes);

        // print the resulting tree
        System.out.println("Decision tree: " + tree);

        // create some data points to classify
        DataPoint realEstateDP = new DataPoint(4);
        realEstateDP.setAttribute(0, 3);
        realEstateDP.setAttribute(1, 30000);
        realEstateDP.setAttribute(2, 0);
        realEstateDP.setAttribute(3, 1);
        System.out.println("Before classification " + realEstateDP);
        tree.classify(realEstateDP);
        System.out.println("After classification " + realEstateDP);

    }
}
