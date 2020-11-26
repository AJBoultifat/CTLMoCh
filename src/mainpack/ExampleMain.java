package mainpack;

import static ctlFormula.Atom.atom;
import static ctlOperator.AU.AU;

import ctlChecker.CTLModelChecker;
import ctlFormula.Formula;
import kripkeSystem.KripkeStructure;
import kripkeSystem.State;

public class ExampleMain {
    public static void main(String[] args) {

        State s0 = new State("s0", "a");
        State s1 = new State("s1", "a");
        State s2 = new State("s2", "a");
        State s3 = new State("s3", "b");

        KripkeStructure kripkeStructure = new KripkeStructure();
        kripkeStructure.addIniState(s0);
        kripkeStructure.addState(s1, s2, s3);
        kripkeStructure.addTrans(s0, s1);
        kripkeStructure.addTrans(s0, s2);
        kripkeStructure.addTrans(s1, s2);
        kripkeStructure.addTrans(s2, s3);
        kripkeStructure.addTrans(s3, s1);

        Formula formula = AU(atom("a"), atom("b"));
        boolean Satisfied = CTLModelChecker.satisfies(kripkeStructure, formula);
        System.out.println(Satisfied);
      
    }
}
