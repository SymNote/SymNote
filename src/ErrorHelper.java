import environment.Environment;
import environment.Variable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Provides Levenshtein-based "Did you mean?" suggestions and call stack
 * formatting
 */
public class ErrorHelper {

    private ErrorHelper() {
    }

    /**
     * Returns a suggestion string like " (Did you mean 'volume'?)" when a close
     * match to {@code name} exists in the current environment chain or routine
     * table.
     */
    public static String suggest(String name, Environment env, Map<String, ?> routines) {
        List<String> candidates = new ArrayList<>();

        Environment current = env;
        while (current != null) {
            candidates.addAll(current.variableNames());
            current = current.getParent();
        }

        if (routines != null) {
            candidates.addAll(routines.keySet());
        }

        String best = null;
        int bestDist = Integer.MAX_VALUE;

        for (String candidate : candidates) {
            if (candidate.equals(name))
                continue;
            int dist = levenshtein(name.toLowerCase(), candidate.toLowerCase());
            if (dist < bestDist) {
                bestDist = dist;
                best = candidate;
            }
        }

        // Only suggest when the edit distance is small relative to the name length
        int threshold = Math.max(2, name.length() / 3);
        if (best != null && bestDist <= threshold) {
            return " (Did you mean '" + best + "'?)";
        }
        return "";
    }

    /**
     * Formats the current call stack as a multi-line string for appending to error
     * messages.
     */
    public static String formatCallStack(environment.CallStack callStack) {
        if (callStack == null || callStack.isEmpty())
            return "";
        StringBuilder sb = new StringBuilder("\nCall stack:");
        for (String frame : callStack.frames()) {
            sb.append("\n  at ").append(frame);
        }
        return sb.toString();
    }

    public static String typeName(Object v) {
        if (v instanceof Integer)
            return "int";
        if (v instanceof Float || v instanceof Double)
            return "float";
        if (v instanceof Boolean)
            return "bool";
        if (v instanceof String)
            return "string";
        if (v instanceof Synth)
            return "synth";
        if (v instanceof Note)
            return "note";
        if (v == null)
            return "void";
        return v.getClass().getSimpleName();
    }

    private static int levenshtein(String a, String b) {
        int m = a.length(), n = b.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++)
            dp[i][0] = i;
        for (int j = 0; j <= n; j++)
            dp[0][j] = j;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[m][n];
    }
}
