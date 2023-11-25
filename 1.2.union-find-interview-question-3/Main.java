public class Main {
    public static void main(String[] args) {
        SuccessorWithDeleteSolution solution = new SuccessorWithDeleteSolution(10);
        solution.remove(3);
        solution.remove(2);
        solution.remove(4);
        solution.remove(9);
        solution.remove(8);
        System.out.println(solution.successor(9));
    }
}
