package taty;

import java.util.Objects;

public class Cons implements TatyExpr {

    private final TatyExpr car;
    private final TatyExpr cdr;
    private final int length;

    public Cons(TatyExpr car) {
        this(car, NilExpr.NIL);
    }

    public Cons(TatyExpr car, TatyExpr cdr) {
        this.car = car;
        this.cdr = cdr;
        this.length = 1 + lengthImpl(cdr);

    }

    private int lengthImpl(TatyExpr expr) {
        if (expr == NilExpr.NIL) {
            return 0;
        } else if (expr instanceof Cons) {
            return ((Cons) expr).length();
        } else {
            return 1;
        }
    }

    public int length() {
        return this.length;
    }

    public TatyExpr car() {
        return car;
    }

    public TatyExpr cdr() {
        return cdr;
    }

    public TatyExpr lookup(SymbExpr sym) {
        TatyExpr c = this;
        while (c != NilExpr.NIL) {
            Cons head = (Cons) ((Cons) c).car;
            if (Objects.equals(head.car, sym)) {
                return head.cdr;
            }
            c = ((Cons) c).cdr;
        }
        return null;
    }

    public TatyExpr nth(int n) {
        int idx = 0;
        TatyExpr current = this;
        while (current != NilExpr.NIL && idx != n) {
            current = ((Cons) current).cdr;
            idx++;
        }
        return ((Cons) current).car;
    }

    public Cons reverse() {
        if (!(cdr instanceof Cons)) {
            if (cdr == NilExpr.NIL) {
                return this;
            } else {
                return new Cons(cdr, car);
            }
        } else {
            TatyExpr c = cdr;
            Cons rev = new Cons(car);
            while (c != NilExpr.NIL) {
                rev = new Cons(((Cons) c).car, rev);
                c = ((Cons) c).cdr;
            }
            return rev;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(car, ((Cons)obj).car)
                && Objects.equals(cdr, ((Cons)obj).cdr);
    }

    @Override
    public String toString() {
        return toStrImpl(new StringBuilder());
    }

    private String toStrImpl(StringBuilder sb) {
        Cons c = this;
        sb.append("(");
        while(true) {
            sb.append(c.car);

            if (c.cdr != NilExpr.NIL) {
                if (!(c.cdr instanceof Cons)) {
                    sb.append(" . ").append(c.cdr);
                    break;
                }  else {
                    sb.append(" ");
                    c = (Cons)c.cdr;
                }
            } else {
                break;
            }
        }
        return sb.append(")").toString();
    }

    public static void main(String[] args) {
        Cons cons = new Cons(new NumberExpr(5),
                new Cons(new NumberExpr(10),
                        new Cons(new NumberExpr(15))));

        // System.out.println(cons.prn());
        // System.out.println(cons.reverse().prn());
    }
}
