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
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public void insert(BirdRecord r) throws DictionaryException {
        if (this.root.isEmpty()) {
            this.root = new Node(r);
        } else {
            Node current = this.root;

            while(true) {
                int comparison = current.getData().getDataKey().compareTo(r.getDataKey());
                if (comparison == 0) {
                    throw new DictionaryException("The record already exists in the dictionary");
                }

                if (comparison == 1) {
                    if (!current.hasLeftChild()) {
                        current.setLeftChild(new Node(r));
                        break;
                    }

                    current = current.getLeftChild();
                } else if (comparison == -1) {
                    if (!current.hasRightChild()) {
                        current.setRightChild(new Node(r));
                        break;
                    }

                    current = current.getRightChild();
                }
            }
        }
    }
    /**
     * Removes the record with Key k from the dictionary. It throws a
     * DictionaryException if the record is not in the dictionary.
     *
     * @param k
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public void remove (DataKey k) throws DictionaryException {
        Node current;
        int comparison, priorcomp;

        if (root == null || root.isEmpty()) {
            throw new DictionaryException("No entry found");
        } else {
            current = root;
            while (true) {
                comparison = current.getData().getDataKey().compareTo(k);
                priorcomp = comparison;
                if (comparison == 0) {
                    break;
                }
                if (comparison == 1) {
                    if (current.hasLeftChild()) {
                        current = current.getLeftChild();
                    } else {
                        throw new DictionaryException("No entry found");
                    }
                }
                if (comparison == -1) {
                    if (current.hasRightChild()) {
                        current = current.getRightChild();
                    } else {
                        throw new DictionaryException("No entry found");
                    }
                }
            }
            if (!current.hasRightChild()) {
                if (current == root && !current.hasLeftChild()) {
                    root = new Node();
                    return;
                } else if (current == root) {
                    root = current.getLeftChild();
                    return;
                }

                if (priorcomp == 1) {
                    current.getParent().setLeftChild(current.getLeftChild());
                } else if (priorcomp == -1) {
                    current.getParent().setRightChild(current.getLeftChild());
                }
            } else {
                Node temp = current.getRightChild();
                if (!temp.hasLeftChild()) {
                    temp.setLeftChild(current.getLeftChild());
                    if (current == root) {
                        root = temp;
                        return;
                    }
                    if (priorcomp == 1) {
                        current.getParent().setLeftChild(temp);
                    } else if (priorcomp == -1) {
                        current.getParent().setRightChild(temp);
                    }
                } else {
                    Node x = null;
                    while (true) {
                        x = temp.getLeftChild();
                        if (!x.hasLeftChild()) {
                            break;
                        }
                        temp = x;
                    }
                    temp.setLeftChild(x.getRightChild());
                    x.setLeftChild(current.getLeftChild());
                    x.setRightChild(current.getRightChild());
                    if (current == root) {
                        root = x;
                        return;
                    }
                    if (priorcomp == 1) {
                        current.getParent().setLeftChild(x);
                    } else if (priorcomp == -1) {
                        current.getParent().setRightChild(x);
                    }
                }
            }
        }
    }
    /**
     * Returns the successor of k (the record from the ordered dictionary with
     * the smallest key larger than k); it returns null if the given key has no
     * successor. The given key DOES NOT need to be in the dictionary.
     *
     * @param k
     * @return
     * @throws assignment/birds/DictionaryException.java
     */
    @Override
    public BirdRecord successor(DataKey k) throws DictionaryException{
        Node current = root;
        int comparison;
        if(root.isEmpty()) {
            throw new DictionaryException("There is a successor for the given key");
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
     * @throws assignment/birds/DictionaryException.java
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
        if(current.hasLeftChild()) {
            Node predecessor = current.getLeftChild();
            while (true) {
                if (predecessor.isLeaf()) {
                    break;
                } else {
                    if (predecessor.hasRightChild()) {
                        predecessor = predecessor.getRightChild();
                    }else {
                        break;
                    }
                }
            }
            return predecessor.getData();
        } else {

            Node predecessor = current.getParent();
            Node temp = current;
            while (predecessor != null && temp == predecessor.getLeftChild()) {
                temp = predecessor;
                predecessor = predecessor.getParent();
            }
            if (predecessor == null) {
                throw new DictionaryException("Throw is no predecessor for the given record key");
            } else {
                return predecessor.getData();
            }
        }


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
