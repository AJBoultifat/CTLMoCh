package ctlFormula;

import java.util.Objects;

public class Atom implements Formula {
    private String atomicPredicate;

    public Atom(String atomicPredicate) {
        this.atomicPredicate = atomicPredicate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Atom that = (Atom) o;
        return Objects.equals(atomicPredicate, that.atomicPredicate);
    }


    public static Atom atom(String atomicPredicate) {
        return new Atom(atomicPredicate);
    }

    @Override
    public Formula CTLBase() {
        return this;
    }
}
