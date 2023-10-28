import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class ProblemK {

    private static class Automata {

        private final Map<Integer, Map<Character, Set<Integer>>> transitions = new HashMap<>();

        public Automata(int n) {
            for (int i = 0; i < n; i++) {
                transitions.put(i, new HashMap<>());
            }
        }

        public void addTransition(int start, char c, int end) {
            Map<Character, Set<Integer>> elem = ttransitions.get(start);
            if (!elem.containsKey(c)) {
                Set<Integer> set = new HashSet<>();
                set.add(end);
                transitions.get(start).put(c, set);
            } else {
                Set<Integer> set = transitions.get(start).get(c);
                set.add(end);
            }
        }

        public Set<Integer> transition(int state, char c) {
            if (!this.transitions.get(state).containsKey(c))
                return null;
            return this.transitions.get(state).get(c);
        }

        public boolean isFinal(Set<Integer> s) {
            return s.contains(this.transitions.size() - 1);
        }
    }

    static class Pair {
        private Set<Integer> s1;
        private Set<Integer> s2;

        public Pair(Set<Integer> s1, Set<Integer> s2) {
            this.s1 = s1;
            this.s2 = s2;
        }

        @Override
        public boolean equals(Object other) {
            return other instanceof Pair p && p.s1.equals(this.s1) && p.s2.equals(this.s2);
        }

        @Override
        public int hashCode() {
            return Objects.hash(s1, s2);
        }
    }

    static boolean checkEquality(Automata m1, Automata m2) {
        Queue<Pair> queue = new ArrayDeque<>();
        Set<Integer> start1 = new HashSet<>();
        start1.add(0);
        Set<Integer> start2 = new HashSet<>();
        start2.add(0);
        Pair root = new Pair(start1, start2);
        Set<Pair> checked = new HashSet<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            checked.add(pair);
            if (m1.isFinal(pair.s1) != m2.isFinal(pair.s2))
                return false;
            for (char c = 'a'; c <= 'z'; c++) {
                Set<Integer> result1 = new HashSet<>();
                for (int state : pair.s1) {
                    Set<Integer> transitions = m1.transition(state, c);
                    if (transitions != null)
                        result1.addAll(transitions);
                }
                Set<Integer> result2 = new HashSet<>();
                for (int state : pair.s2) {
                    Set<Integer> transitions = m2.transition(state, c);
                    if (transitions != null)
                        result2.addAll(transitions);
                }
                Pair child = new Pair(result1, result2);
                if (!checked.contains(child))
                    queue.add(child);
            }
        }
        return true;
    }

    static Automata loadAutomata(Scanner sc) {
        int n = sc.nextInt();
        Automata m = new Automata(n);
        int e = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < e; i++) {
            int state = sc.nextInt();
            char c = sc.next().charAt(0);
            int end = sc.nextInt();
            m.addTransition(state, c, end);
        }
        return m;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int e = sc.nexloadAutomata(sc);
        Automata m2 = loadAutomata(sc);
        if (checkEquality(m1, m2)) {
            System.out.println("PROBLEM");
        } else {
            System.out.println("NO PROBLEM");
        }
    }
}