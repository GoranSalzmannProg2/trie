/**
 * Class with references to itself and data storage. Used to build tree
 * structure.
 */
public class Node {
    private final Node parent;
    private final Node[] children;
    private Integer points;
    private final char ch;


    /**
     * Universal constructor for the Node class.
     *
     * @param ch     the character that identifies the node
     * @param parent the node object on which this new node should be set as
     *               a child
     */
    public Node(char ch, Node parent) {
        this.parent = parent;
        this.children = new Node[26];
        this.ch = ch;
        this.points = null;

        if (parent != null) {
            parent.setChild(ch, this);
        }
    }

    /**
     * Specialized constructor useful for constructing a root node.
     */
    public Node() {
        this('+', null);
    }

    /**
     * Finds a child Node by character.
     *
     * @param ch the character used to identify the child Node
     * @return the Node object identified through the corresponding edge
     */
    public Node getChild(char ch) {
        return this.children[ch - 'a'];
    }

    /**
     * Searches through the subtree produced by using the node this function
     * is called on as the root. Uses recursive depth first search to find
     * the node found at the end of a string key.
     *
     * @param key the string used in the search
     * @return the Node for the corresponding key if found, or null if no key
     * found
     */
    public Node find(String key) {
        char c = key.charAt(0);
        if (key.length() == 1) {
            return this.children[c - 'a'];
        } else {
            if (this.children[c - 'a'] == null) {
                return null;
            } else {
                return this.children[c - 'a'].find(key.substring(1));
            }
        }
    }

    /**
     * Sets the points saved in the Node to null and removes all unnecessary
     * nodes from the trie.
     */
    public void remove() {
        this.points = null;
        this.cleanup();
    }


    /**
     * Formats the Node with all children in a one-line string, for printing
     * to the console.
     *
     * @return the string representation of the data-structure
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.ch);
        if (points != null) {
            sb.append("[").append(points).append("]");
        }
        if (childrenCount() > 0) {
            sb.append("(");
            for (char c = 'a'; c <= 'z'; c++) {
                if (this.children[c - 'a'] != null) {
                    sb.append(this.children[c - 'a']);
                }
            }
            sb.append(")");
        }
        return sb.toString();
    }

    /**
     * Sets the points saved in the Node to a defined value.
     *
     * @param points the new value that's supposed to be saved in the Node
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

    /**
     * Returns the points saved in the Node, even if null;
     *
     * @return points value as Integer or null
     */
    public Integer getPoints() {
        return this.points;
    }

    /**
     * Counts the valid children nodes.
     *
     * @return number of non-null children nodes.
     */
    private int childrenCount() {
        int sum = 0;
        for (char c = 'a'; c <= 'z'; c++) {
            if (this.children[c - 'a'] != null) {
                sum++;
            }
        }
        return sum;
    }

    /**
     * Adds a node to the children array.
     *
     * @param ch    key for which to add the node
     * @param child the node which should be added
     */
    private void setChild(char ch, Node child) {
        this.children[ch - 'a'] = child;
    }

    /**
     * Removes all unnecessary nodes from the tree by recursively calling
     * itself.
     */
    private void cleanup() {
        if (this.ch == '+') {
            return;
        }
        if (this.childrenCount() == 0 && this.getPoints() == null) {
            this.parent.setChild(this.ch, null);
            this.parent.cleanup();
        }
    }
}
