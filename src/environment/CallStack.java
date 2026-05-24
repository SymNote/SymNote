package environment;

import java.util.ArrayDeque;
import java.util.Deque;

public class CallStack {
    private final Deque<ActivationRecord> stack = new ArrayDeque<>();
    private final int MAX_DEPTH = 400;

    public void push(ActivationRecord record, int line) {
        if (stack.size() >= MAX_DEPTH) {
            throw new RuntimeException("SymNote StackOverflow: Maximum recursion depth of " + MAX_DEPTH + " exceeded at line " + line);
        }
        stack.push(record);
    }

    public ActivationRecord pop() {
        if (stack.isEmpty()) {
            throw new RuntimeException("Fatal Error: Attempted to pop a frame from an empty call stack.");
        }
        return stack.pop();
    }

    public ActivationRecord peek() {
        return stack.peek();
    }

    public boolean isEmpty() {
        return stack.isEmpty();
    }
}