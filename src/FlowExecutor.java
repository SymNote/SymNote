import environment.Environment;
import environment.Variable;
import gen.SymNoteParser;
import environment.ActivationRecord;

import java.util.ArrayList;
import java.util.List;

public class FlowExecutor {
    private final SymNoteInterpreter interpreter;

    public FlowExecutor(SymNoteInterpreter interpreter) {
        this.interpreter = interpreter;
    }

    public Object executeCall(SymNoteParser.CallExprContext ctx) {
        String functionName = ctx.ID().getText();
        List<Object> args = new ArrayList<>();

        if (ctx.arguments() != null) {
            for (SymNoteParser.ExpressionContext exprCtx : ctx.arguments().expression()) {
                Object argVal = interpreter.visit(exprCtx);
                if (argVal == null) {
                    throw new RuntimeException(
                            "Cannot pass void value as argument at line " + ctx.getStart().getLine());
                }
                args.add(argVal);
            }
        }

        switch (functionName) {
            case "set_bpm":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "set_bpm() requires one numeric argument at line " + ctx.getStart().getLine());
                }
                Object bpmArg = args.getFirst();
                if (!(bpmArg instanceof Number)) {
                    throw new RuntimeException(
                            "set_bpm() requires an int or float, but got "
                                    + ErrorHelper.typeName(bpmArg) + " (value: '" + bpmArg + "') at line "
                                    + ctx.getStart().getLine());
                }
                float bpmVal = ((Number) bpmArg).floatValue();
                if (bpmVal <= 0) {
                    throw new RuntimeException(
                            "set_bpm() requires a positive tempo, but got " + bpmVal
                                    + " at line " + ctx.getStart().getLine()
                                    + ". Typical values are between 40 and 300.");
                }
                interpreter.bpm = bpmVal;
                return null;
            case "load_synth":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "load_synth() requires one string argument (the instrument name) at line "
                                    + ctx.getStart().getLine());
                }
                if (!(args.getFirst() instanceof String)) {
                    throw new RuntimeException(
                            "load_synth() requires a string instrument name, but got "
                                    + ErrorHelper.typeName(args.getFirst()) + " at line "
                                    + ctx.getStart().getLine()
                                    + ". Example: load_synth(\"piano\")");
                }
                String synthName = String.valueOf(args.getFirst());
                String key = synthName.trim().toLowerCase().replace("-", "").replace("_", "").replace(" ", "");
                java.util.List<String> validSynths = java.util.Arrays.asList(
                    "piano", "organ", "bass", "sawtooth", "square", "guitar", "strings", 
                    "pad", "choir", "trumpet", "sax", "flute", "bell", "pluck"
                );
                if (!validSynths.contains(key)) {
                    throw new RuntimeException(
                            "Unknown synth name '" + synthName + "' at line " 
                            + ctx.getStart().getLine() 
                            + ". See documentation for supported instruments.");
                }
                return new Synth(synthName);
            case "use_synth":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "use_synth() requires one argument (a synth variable) at line "
                                    + ctx.getStart().getLine());
                }
                if (!(args.getFirst() instanceof Synth)) {
                    throw new RuntimeException(
                            "use_synth() requires a synth variable, but got "
                                    + ErrorHelper.typeName(args.getFirst()) + " at line "
                                    + ctx.getStart().getLine()
                                    + ". Did you forget to assign the result of load_synth()?");
                }
                interpreter.currentSynthName = ((Synth) args.getFirst()).getName();
                return null;
            case "print":
                if (args.isEmpty()) {
                    throw new RuntimeException(
                            "print() requires one argument at line " + ctx.getStart().getLine());
                }
                interpreter.output.println(args.getFirst());
                return null;

            case "sin":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                return (float) Math.sin(toNumber(args.getFirst(), functionName, ctx.getStart().getLine()).doubleValue());
            
            case "cos":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                return (float) Math.cos(toNumber(args.getFirst(), functionName, ctx.getStart().getLine()).doubleValue());
            
            case "pow":
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                float base = toNumber(args.get(0), functionName, ctx.getStart().getLine()).floatValue();
                float exp = toNumber(args.get(1), functionName, ctx.getStart().getLine()).floatValue();
                return (float) Math.pow(base, exp);
                
            case "sqrt":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                float sqrtVal = toNumber(args.getFirst(), functionName, ctx.getStart().getLine()).floatValue();
                if (sqrtVal < 0) {
                    throw new RuntimeException("Math Domain Error: sqrt() cannot take negative numbers at line " + ctx.getStart().getLine());
                }
                return (float) Math.sqrt(sqrtVal);

            case "abs":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                Object absArg = args.getFirst();
                if (absArg instanceof Integer) return Math.abs((Integer) absArg);
                return Math.abs(toNumber(absArg, functionName, ctx.getStart().getLine()).floatValue());
                
            case "min":
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                Object min1 = args.get(0);
                Object min2 = args.get(1);
                if (min1 instanceof Integer && min2 instanceof Integer) {
                    return Math.min((Integer) min1, (Integer) min2);
                }
                return Math.min(toNumber(min1, functionName, ctx.getStart().getLine()).floatValue(), 
                                toNumber(min2, functionName, ctx.getStart().getLine()).floatValue());

            case "max":
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                Object max1 = args.get(0);
                Object max2 = args.get(1);
                if (max1 instanceof Integer && max2 instanceof Integer) {
                    return Math.max((Integer) max1, (Integer) max2);
                }
                return Math.max(toNumber(max1, functionName, ctx.getStart().getLine()).floatValue(), 
                                toNumber(max2, functionName, ctx.getStart().getLine()).floatValue());

            case "round":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                return Math.round(toNumber(args.getFirst(), functionName, ctx.getStart().getLine()).floatValue());

            case "rand":
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                if (!(args.get(0) instanceof Integer) || !(args.get(1) instanceof Integer)) {
                    throw new RuntimeException("rand() requires two 'int' arguments at line " + ctx.getStart().getLine());
                }
                int min = (Integer) args.get(0);
                int max = (Integer) args.get(1);
                if (min > max) throw new RuntimeException("rand() min cannot be greater than max at line " + ctx.getStart().getLine());
                return min + (int)(Math.random() * ((max - min) + 1));

            case "gcd":
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                if (!(args.get(0) instanceof Integer) || !(args.get(1) instanceof Integer)) {
                    throw new RuntimeException("gcd() requires two 'int' arguments at line " + ctx.getStart().getLine());
                }
                return gcdHelper((Integer) args.get(0), (Integer) args.get(1));

            case "lcm":
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                if (!(args.get(0) instanceof Integer) || !(args.get(1) instanceof Integer)) {
                    throw new RuntimeException("lcm() requires two 'int' arguments at line " + ctx.getStart().getLine());
                }
                int lcmA = (Integer) args.get(0);
                int lcmB = (Integer) args.get(1);
                if (lcmA == 0 || lcmB == 0) return 0;
                return Math.abs(lcmA * lcmB) / gcdHelper(lcmA, lcmB);

            case "is_prime":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                if (!(args.getFirst() instanceof Integer)) {
                    throw new RuntimeException("is_prime() requires an 'int' argument at line " + ctx.getStart().getLine());
                }
                int n = (Integer) args.getFirst();
                if (n <= 1) return false;
                if (n == 2 || n == 3) return true;
                if (n % 2 == 0 || n % 3 == 0) return false;
                for (int i = 5; i * i <= n; i += 6) {
                    if (n % i == 0 || n % (i + 2) == 0) return false;
                }
                return true;

            case "fib":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                if (!(args.getFirst() instanceof Integer)) {
                    throw new RuntimeException("fib() requires an 'int' argument at line " + ctx.getStart().getLine());
                }
                int fibN = (Integer) args.getFirst();
                if (fibN < 0) throw new RuntimeException("fib() requires a non-negative integer at line " + ctx.getStart().getLine());
                if (fibN <= 1) return fibN;
                int fibA = 0, fibB = 1, fibC;
                for (int i = 2; i <= fibN; i++) {
                    fibC = fibA + fibB;
                    fibA = fibB;
                    fibB = fibC;
                }
                return fibB;

            case "transpose": {
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                if (!(args.get(0) instanceof Note) || !(args.get(1) instanceof Integer)) {
                    throw new RuntimeException("transpose() requires (note, int) at line " + ctx.getStart().getLine());
                }
                Note nNote = (Note) args.get(0);
                int semi = (Integer) args.get(1);
                String transposedVal = transposeNoteHelper(nNote.getValue(), semi, ctx.getStart().getLine());
                return new Note(transposedVal);
            }

            case "clamp": {
                requireArgs(functionName, args, 3, ctx.getStart().getLine());
                float val = toNumber(args.get(0), functionName, ctx.getStart().getLine()).floatValue();
                float minV = toNumber(args.get(1), functionName, ctx.getStart().getLine()).floatValue();
                float maxV = toNumber(args.get(2), functionName, ctx.getStart().getLine()).floatValue();
                return Math.max(minV, Math.min(maxV, val));
            }

            case "avg": {
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                float avg1 = toNumber(args.get(0), functionName, ctx.getStart().getLine()).floatValue();
                float avg2 = toNumber(args.get(1), functionName, ctx.getStart().getLine()).floatValue();
                return (avg1 + avg2) / 2.0f;
            }

            case "scale": {
                requireArgs(functionName, args, 5, ctx.getStart().getLine());
                float val = toNumber(args.get(0), functionName, ctx.getStart().getLine()).floatValue();
                float inMin = toNumber(args.get(1), functionName, ctx.getStart().getLine()).floatValue();
                float inMax = toNumber(args.get(2), functionName, ctx.getStart().getLine()).floatValue();
                float outMin = toNumber(args.get(3), functionName, ctx.getStart().getLine()).floatValue();
                float outMax = toNumber(args.get(4), functionName, ctx.getStart().getLine()).floatValue();
                return outMin + (val - inMin) * (outMax - outMin) / (inMax - inMin);
            }

            case "is_power_of_two":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                int n2 = (Integer) args.getFirst();
                return (n2 > 0) && ((n2 & (n2 - 1)) == 0);

            case "sum_digits":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                int num = Math.abs((Integer) args.getFirst());
                int sum = 0;
                while (num > 0) {
                    sum += num % 10;
                    num /= 10;
                }
                return sum;

            case "fourier_wave": {
                requireArgs(functionName, args, 2, ctx.getStart().getLine());
                float tVal = toNumber(args.get(0), functionName, ctx.getStart().getLine()).floatValue();
                int nHarm = (Integer) args.get(1);
                float wave = 0;
                for (int i = 1; i <= nHarm; i++) {
                    wave += (float) (Math.sin(2 * Math.PI * i * tVal) / i);
                }
                return wave;
            }

            case "pitch_to_freq":
                requireArgs(functionName, args, 1, ctx.getStart().getLine());
                Note noteArg = (Note) args.getFirst();
                int midi = new GridExecutor(interpreter).noteToMidi(noteArg.getValue(), ctx.getStart().getLine());
                return (float) (440.0 * Math.pow(2.0, (midi - 69.0) / 12.0));
            
            default:
                if (interpreter.routines.containsKey(functionName)) {
                    SymNoteParser.RoutineDeclContext routineCtx = interpreter.routines.get(functionName);

                    Environment routineEnv = new Environment(interpreter.globalEnv);

                    ActivationRecord frame = new ActivationRecord(functionName, routineEnv);
                    interpreter.callStack.push(frame, ctx.getStart().getLine());

                    Environment callerEnv = interpreter.env;
                    interpreter.env = routineEnv;

                    try {
                        int expectedParams = routineCtx.parameters() == null ? 0
                                : routineCtx.parameters().param().size();
                        if (args.size() != expectedParams) {
                            throw new RuntimeException("Routine '" + functionName + "' expected " + expectedParams
                                    + " arguments but got " + args.size() + " at line " + ctx.getStart().getLine());
                        }

                        // Bind arguments to parameters
                        if (routineCtx.parameters() != null) {
                            for (int i = 0; i < routineCtx.parameters().param().size(); i++) {
                                String paramName = routineCtx.parameters().param(i).ID().getText();
                                String paramType = routineCtx.parameters().param(i).type().getText();
                                Object argValue = args.get(i);

                                interpreter.checkType(paramType, argValue,
                                        "argument '" + paramName + "' in routine '" + functionName + "'",
                                        ctx.getStart().getLine());

                                if (paramType.equals("int")) {
                                    interpreter.env.define(paramName,
                                            new Variable(paramType, ((Integer) argValue).intValue()));
                                } else if (paramType.equals("float")) {
                                    interpreter.env.define(paramName,
                                            new Variable(paramType, ((Number) argValue).floatValue()));
                                } else {
                                    interpreter.env.define(paramName, new Variable(paramType, argValue));
                                }
                            }
                        }

                        // Execute routine block
                        try {
                            interpreter.visit(routineCtx.blockRoutine());
                        } catch (ReturnException re) {
                            throw re; // let the outer catch (ReturnException) handle it
                        } catch (RuntimeException e) {
                            // Attach call-stack trace if not already present
                            String msg = e.getMessage();
                            String trace = ErrorHelper.formatCallStack(interpreter.callStack);
                            if (msg != null && !trace.isEmpty() && !msg.contains("Call stack:")) {
                                throw new RuntimeException(msg + trace);
                            }
                            throw e;
                        }

                        // If we get here, no return statement was hit
                        if (!routineCtx.type().getText().equals("void")) {
                            throw new RuntimeException(
                                    "Routine '" + functionName + "' declared return type '"
                                            + routineCtx.type().getText() + "' but has no return statement"
                                            + " (defined at line " + routineCtx.getStart().getLine() + ")");
                        }
                        return null;

                    } catch (ReturnException e) {
                        Object returnValue = e.getValue();
                        String expectedType = routineCtx.type().getText();
                        int line = ctx.getStart().getLine();

                        if (expectedType.equals("void")) {
                            if (returnValue != null) {
                                throw new RuntimeException(
                                        "Void routine '" + functionName
                                                + "' cannot return a value — remove 'return <value>' at line " + line);
                            }
                            return null;
                        }

                        if (returnValue == null) {
                            throw new RuntimeException(
                                    "Routine '" + functionName + "' must return a value of type '"
                                            + expectedType + "', but returned nothing at line " + line);
                        }

                        // Cast return value to the expected type
                        if (expectedType.equals("float")) {
                            interpreter.checkType("float", returnValue,
                                    "return value of routine '" + functionName + "'", line);
                            return ((Number) returnValue).floatValue();
                        }

                        // Check if the type matches exactly (strict check - no implicit casting)
                        interpreter.checkTypeStrict(expectedType, returnValue, "return value of " + functionName, line);

                        return returnValue;
                    } catch (RuntimeException e) {
                        String msg = e.getMessage();
                        String trace = ErrorHelper.formatCallStack(interpreter.callStack);
                        if (msg != null && !trace.isEmpty() && !msg.contains("Call stack:")) {
                            throw new RuntimeException(msg + trace);
                        }
                        throw e;
                    } finally {
                        interpreter.callStack.pop();
                        interpreter.env = callerEnv;
                    }
                }

                // Otherwise, it must be a Track
                SymNoteParser.TrackDeclContext trackCtx;
                try {
                    trackCtx = (SymNoteParser.TrackDeclContext) interpreter.env.getTrack(functionName);
                } catch (RuntimeException e) {
                    // Neither a routine nor a track, give a suggestion
                    String hint = ErrorHelper.suggest(functionName, interpreter.env, interpreter.routines);
                    throw new RuntimeException(
                            "Undefined routine or track '" + functionName + "' at line "
                                    + ctx.getStart().getLine() + hint);
                }

                Environment trackEnv = new Environment(interpreter.globalEnv);
                ActivationRecord trackFrame = new ActivationRecord(functionName, trackEnv);
                interpreter.callStack.push(trackFrame, ctx.getStart().getLine());

                Environment previousEnv = interpreter.env;
                String previousSynth = interpreter.currentSynthName;
                interpreter.env = trackEnv;

                try {
                    int expectedParams = trackCtx.parameters() == null ? 0 : trackCtx.parameters().param().size();
                    if (args.size() != expectedParams) {
                        throw new RuntimeException("Track '" + functionName + "' expected " + expectedParams
                                + " argument(s) but got " + args.size() + " at line " + ctx.getStart().getLine());
                    }

                    if (trackCtx.parameters() != null) {
                        for (int i = 0; i < trackCtx.parameters().param().size(); i++) {
                            String paramName = trackCtx.parameters().param(i).ID().getText();
                            String paramType = trackCtx.parameters().param(i).type().getText();
                            Object argValue = args.get(i);

                            interpreter.checkType(paramType, argValue,
                                    "argument '" + paramName + "' in track '" + functionName + "'",
                                    ctx.getStart().getLine());

                            if (paramType.equals("int")) {
                                interpreter.env.define(paramName,
                                        new Variable(paramType, ((Integer) argValue).intValue()));
                            } else if (paramType.equals("float")) {
                                interpreter.env.define(paramName,
                                        new Variable(paramType, ((Number) argValue).floatValue()));
                            } else {
                                interpreter.env.define(paramName, new Variable(paramType, argValue));
                            }
                        }
                    }

                    interpreter.visit(trackCtx.blockTrack());
                } finally {
                    interpreter.callStack.pop();
                    interpreter.currentSynthName = previousSynth;
                    interpreter.env = previousEnv;
                }

                return null;
        }
    }

    public Object executeParallel(SymNoteParser.ParallelStmtContext ctx) {
        long startTick = interpreter.currentTick;
        long maxTick = startTick;
        Environment baseEnv = interpreter.env;
        String baseSynth = interpreter.currentSynthName;
        float baseBpm = interpreter.bpm;

        for (SymNoteParser.ParallelEntryContext entryCtx : ctx.parallelEntry()) {
            interpreter.currentTick = startTick;
            interpreter.env = new Environment(baseEnv);
            interpreter.currentSynthName = baseSynth;
            interpreter.bpm = baseBpm;

            interpreter.visit(entryCtx.callExpr());
            maxTick = Math.max(maxTick, interpreter.currentTick);
        }

        interpreter.env = baseEnv;
        interpreter.currentSynthName = baseSynth;
        interpreter.bpm = baseBpm;
        interpreter.currentTick = maxTick;
        return null;
    }

    // For iteration
    public Object executeParallel(SymNoteParser.ParallelIterationStmtContext ctx) {
        long startTick = interpreter.currentTick;
        long maxTick = startTick;
        Environment baseEnv = interpreter.env;
        String baseSynth = interpreter.currentSynthName;
        float baseBpm = interpreter.bpm;

        for (SymNoteParser.ParallelEntryContext entryCtx : ctx.parallelEntry()) {
            interpreter.currentTick = startTick;
            interpreter.env = new Environment(baseEnv);
            interpreter.currentSynthName = baseSynth;
            interpreter.bpm = baseBpm;

            interpreter.visit(entryCtx.callExpr());
            maxTick = Math.max(maxTick, interpreter.currentTick);
        }

        interpreter.env = baseEnv;
        interpreter.currentSynthName = baseSynth;
        interpreter.bpm = baseBpm;
        interpreter.currentTick = maxTick;
        return null;
    }

    private void requireArgs(String func, List<Object> args, int count, int line) {
        if (args.size() != count) {
            throw new RuntimeException("Function '" + func + "()' requires exactly " + count 
                + " argument(s), but got " + args.size() + " at line " + line);
        }
    }

    private Number toNumber(Object obj, String func, int line) {
        if (obj instanceof Number) return (Number) obj;
        throw new RuntimeException("Function '" + func + "()' requires a numeric argument, but got " 
            + ErrorHelper.typeName(obj) + " at line " + line);
    }

    private int gcdHelper(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    private String transposeNoteHelper(String noteText, int semi, int line) {
        int midi = new GridExecutor(interpreter).noteToMidi(noteText, line);
        midi += semi;
        
        if (midi < 0 || midi > 127) {
            throw new RuntimeException("Transposed note out of MIDI range (0-127) at line " + line);
        }
        
        String[] notes = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
        String noteName = notes[midi % 12];
        int octave = (midi / 12) - 1;
        
        return noteName + octave;
    }
}