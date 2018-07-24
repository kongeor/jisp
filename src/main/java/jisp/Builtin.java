package jisp;

import java.util.Objects;

import static jisp.Cons.Cons_;

public abstract class Builtin implements IFn {

    private final String name;

    public Builtin(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "<BuiltinFn:" + name + ">";
    }

    public static Builtin PRINTLN = new Builtin("println") {

        @Override
        public Object apply(Env env, Cons args) {
            StringBuilder sb = new StringBuilder();
            Cons a = args;
            while (a != null) {
                sb.append(a.car());
                a = (Cons) a.cdr();
            }
            System.out.println(sb.toString());
            return null;
        }
    };

    public static Builtin PLUS = new Builtin("+") {

        @Override
        public Object apply(Env env, Cons args) {
            int sum = 0;
            Cons c = args;
            while (c != null) {
                sum = Math.addExact(sum, (Integer) c.car());
                c = (Cons) c.cdr();
            }
            return sum;
        }
    };

    public static Builtin MULT = new Builtin("*") {

        @Override
        public Object apply(Env env, Cons args) {
            int sum = 1;
            Cons c = args;
            while (c != null) {
                sum = Math.multiplyExact(sum, (Integer) c.car());
                c = (Cons) c.cdr();
            }
            return sum;
        }
    };

    public static Builtin MINUS = new Builtin("-") {

        @Override
        public Object apply(Env env, Cons args) {
            int n1 = (int) args.car();
            int n2 = (int) ((Cons)args.cdr()).car();
            return n1 - n2;
        }
    };

    public static Builtin LT = new Builtin("<") {

        @Override
        public Object apply(Env env, Cons args) {
            int left = (int) args.car();
            int right = (int) ((Cons)args.cdr()).car();
            return left < right;
        }
    };

    public static Builtin EQ = new Builtin("=") {

        @Override
        public Object apply(Env env, Cons args) {
            Object left = args.car();
            Object right = ((Cons)args.cdr()).car();
            return Objects.equals(left, right);
        }
    };

    public static Builtin CONS = new Builtin("cons") {

        @Override
        public Object apply(Env env, Cons args) {
            Object left = args.car();
            Object right = null;
            if (args.cdr() != null) {
                right = ((Cons) args.cdr()).car();
            }
            return Cons_(left, right);
        }
    };

    public static Builtin CONS_Q = new Builtin("cons?") {

        @Override
        public Object apply(Env env, Cons args) {
            Object param = args.car();
            return param instanceof Cons;
        }
    };
}
