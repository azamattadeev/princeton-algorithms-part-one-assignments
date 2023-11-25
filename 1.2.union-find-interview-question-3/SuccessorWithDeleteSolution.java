public class SuccessorWithDeleteSolution {
    private WeightedQuickUnionWithMax weightedQuickUnionWithMax;
    private final int n;
    private boolean maxRemoved = false;

    public SuccessorWithDeleteSolution(int n) {
        weightedQuickUnionWithMax = new WeightedQuickUnionWithMax(n);
        this.n = n;
    }

    public void remove(int x) {
        if (x >= n || x < 0) throw new IllegalArgumentException("Wrong x");

        if (x == n - 1) {
            maxRemoved = true;
        }
        else {
            weightedQuickUnionWithMax.union(x, x + 1);
        }
    }

    public int successor(int x) {
        if (x >= n || x < 0) throw new IllegalArgumentException("Wrong x");
        int successor = weightedQuickUnionWithMax.findMax(x);
        return (successor == n - 1 && maxRemoved) ? -1 : successor;
    }

}
