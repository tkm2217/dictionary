package assignment.birds;

public class OrderedDictionary implements OrderedDictionaryADT {

    Node root;

    OrderedDictionary() {
        root = new Node();
    }

    /**
     * Returns the Record object with key k, or it returns null if such a record
     * is not in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public BirdRecord find(DataKey k) throws DictionaryException {
        Node current = root;
        int comparison;
        if (root.isEmpty()) {         
            throw new DictionaryException("There is no record matches the given key");
        }

        while (true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison == 0) { // key found
                return current.getData();
            }
            if (comparison == 1) {
                if (current.getLeftChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getLeftChild();
            } else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    // Key not found
                    throw new DictionaryException("There is no record matches the given key");
                }
                current = current.getRightChild();
            }
        }

    }

    /**
     * Inserts r into the ordered dictionary. It throws a DictionaryException if
     * a record with the same key as r is already in the dictionary.
     *
     * @param r
     * @throws birds.DictionaryException
     */
    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        Node x;
        int comparison;
        Node current = root;

        if (root.isEmpty()){
            root = new Node(x);
        }
        else {
            current = root;
            while (true) {
                comparison = current.getData().getDataKey().compareTo(x);

            }
        }
    }

    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws birds.DictionaryException
     */
    @Override
    public void remove(DataKey k) throws DictionaryException {
       find(k);

    }

    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * the smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException{
        Node current = root;
        int comparison;
        if(root.isEmpty()) {
            throw new DictionaryException("There is a predecessor for the given key");
        }
        while(true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison ==0){
                break;
            }
            if (comparison ==1) {
                if (current.getLeftChild() == null) {
                    return current.getData();
                }
                current = current.getLeftChild();
            }else if (comparison == -1) {
                if (current.getRightChild() == null) {
                    current = current.getParent();
                    return current.getData();
                }
                current = current.getRightChild();
            }
        }
        if(current.hasRightChild()) {
            Node successor = current.getRightChild();
            while (true) {
                if (successor.isLeaf()) {
                    break;
                } else {
                    if (successor.hasLeftChild()) {
                        successor = successor.getLeftChild();
                    }else {
                        break;
                    }
                }
            }
            return successor.getData();
        } else {

            Node successor = current.getParent();
            Node temp = current;
            while (successor != null && temp == successor.getRightChild()) {
                temp = successor;
                successor = successor.getParent();
            }
            if (successor == null) {
                throw new DictionaryException("Throw is no successor for the given record key");
            } else {
                return successor.getData();
            }
        }
    }

   
    /**
     * Returns the predecessor of k (the record from the ordered dictionary with
     * the largest key smaller than k) it returns null if the given key has no
     * predecessor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws birds.DictionaryException
     */
    @Override
    public BirdRecord predecessor(DataKey k) throws DictionaryException{
        Node current = root;
        int comparison;
        if(root.isEmpty()) {
            throw new DictionaryException("There is a predecessor for the given key");
        }
        while(true) {
            comparison = current.getData().getDataKey().compareTo(k);
            if (comparison ==0){
                break;
            }
            if (comparison ==1) {
                current = current.getParent();
            }
        }
        return null; // change this statement
    }

    /**
     * Returns the record with the smallest key in the ordered dictionary. Returns
     * null if the dictionary is empty.
     *
     * @return
     */
    @Override
    public BirdRecord smallest() throws DictionaryException{
        Node r = root;
        if (r == null){
            return null;
        }
        else {
            Node current = r;
            while (current.getLeftChild() != null){
                current = current.getLeftChild();
            }
            return current.getData();
        }
    }

    /*
	 * Returns the record with the largest key in the ordered dictionary. Returns
	 * null if the dictionary is empty.
     */
    @Override
    public BirdRecord largest() throws DictionaryException{
        Node r = root;
        if (r == null){
            return null;
        }
        else {
            Node current = r;
            while (current.getRightChild() != null){
                current = current.getRightChild();
            }
        return current.getData();
        }

    }
      
    /* Returns true if the dictionary is empty, and true otherwise. */
    @Override
    public boolean isEmpty (){
        return root.isEmpty();
    }
}
