/**
 * Class with reference to root node. Handles tasks regarding the whole
 * data-structure. Administers all nodes.
 */
public class Trie {
    private Node root;

    /**
     * Constructor for the Trie class. Generates an empty tree.
     */
    public Trie() {
        root = new Node();
    }

    /**
     * Expands the data structure to insert new key and sets points to
     * specified value. Will return false and not modify the data structure if
     * key is already present.
     *
     * @param key    key used to identify a node in the tree
     * @param points value that's supposed to be inserted
     * @return boolean indicating if insertion was successful
     */
    public boolean add(String key, Integer points) {
        Node currentNode = root;
        for (int i = 0; i < key.length(); i++) {
            char currentChar = key.charAt(i);
            if (currentNode.getChild(currentChar) == null) {
                Node nextNode = new Node(currentChar, currentNode);
            } else if (i == key.length() - 1) {
                return false;
            }
            currentNode = currentNode.getChild(currentChar);
        }
        currentNode.setPoints(points);
        return true;
    }

    /**
     * Removes a data value of a specified key and trims tree to remove
     * unnecessary nodes from the tree. Removal will fail if specified
     * key is not found in the tree.
     *
     * @param key key of the node that should be removed
     * @return boolean indication if removal was successful
     */
    public boolean remove(String key) {
        if (root.find(key) == null || root.find(key).getPoints() == null) {
            return false;
        } else {
            root.find(key).remove();
            return true;
        }
    }

    /**
     * Changes the value saved for the specified key to a new value. Method
     * will not change anything if key is not present in the tree.
     *
     * @param key    key for which a new value should be saved
     * @param points new value that overrides an old value
     * @return boolean indication if change was successful
     */
    public boolean change(String key, Integer points) {
        if (root.find(key) == null || root.find(key).getPoints() == null) {
            return false;
        } else {
            root.find(key).setPoints(points);
            return true;
        }
    }

    /**
     * Gets the points value saved for a specified key. Will return null
     * object if there is no value for the specified key.
     *
     * @param key key for which a value should be returned
     * @return value as Integer saved at the keys location or null
     */
    public Integer points(String key) {
        if (root.find(key) == null) {
            return null;
        } else {
            return root.find(key).getPoints();
        }
    }

    /**
     * Generates a readable String representation of the current state of the
     * whole data structure in a one-line format. Includes all keys and
     * values. Useful for printing to the console.
     *
     * @return string representation of the tree
     */
    @Override
    public String toString() {
        return root.toString();
    }
}
