public static Node root;
static class Node {
    int low, high, sum;
    Node left, right;
    Node(int low, int high, int sum) {
        this.low = low;
        this.high = high;
        this.sum = sum;
        this.left = null;
        this.right = null;
    }

    public static Node buildST(int[] arr, int low, int high) {
        if (low == high)
            return new Node(low, high, arr[low]);
        int mid = (low + high) / 2;
        Node left = buildST(arr, low, mid);
        Node right = buildST(arr, mid + 1, high);
        Node parent = new Node(low, high, left.sum + right.sum);
        parent.left = left;
        parent.right = right;
        return parent;
    }

