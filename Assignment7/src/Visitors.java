import java.util.function.BiFunction;
import java.util.function.Function;
import tester.Tester;

interface IArithVisitor<R> extends Function<IArith, R> {
  R visitConst(Const c);

  R visitUnaryFormula(UnaryFormula uf);

  R visitBinaryFormula(BinaryFormula bf);

}

class Negate implements Function<Double, Double> {
  public Double apply(Double d) {
    return (-1 * d);
  }
}

class Square implements Function<Double, Double> {
  public Double apply(Double d) {
    return Math.pow(d, 2);
  }
}

class Plus implements BiFunction<Double, Double, Double> {
  public Double apply(Double left, Double right) {
    return left + right;
  }
}

class Minus implements BiFunction<Double, Double, Double> {
  public Double apply(Double left, Double right) {
    return left - right;
  }
}

class Multiply implements BiFunction<Double, Double, Double> {
  public Double apply(Double left, Double right) {
    return left * right;
  }
}

class Divide implements BiFunction<Double, Double, Double> {
  public Double apply(Double left, Double right) {
    return left / right;
  }
}

class EvalVisitor implements IArithVisitor<Double> {
  public Double visitConst(Const c) {
    return c.num;
  }

  public Double visitUnaryFormula(UnaryFormula uf) {
    return uf.func.apply(new EvalVisitor().apply(uf.child));
  }

  public Double visitBinaryFormula(BinaryFormula bf) {
    return bf.func.apply(new EvalVisitor().apply(bf.left), new EvalVisitor().apply(bf.right));
  }

  public Double apply(IArith t) {
    return t.accept(this);
  }
}

class PrintVisitor implements IArithVisitor<String> {
  public String visitConst(Const c) {
    return Double.toString(c.num);
  }

  public String visitUnaryFormula(UnaryFormula uf) {
    return "(" + uf.name + " " + new PrintVisitor().apply(uf.child) + ")";
  }

  public String visitBinaryFormula(BinaryFormula bf) {
    return "(" + bf.name + " " + new PrintVisitor().apply(bf.left) + " "
        + new PrintVisitor().apply(bf.right) + ")";
  }

  public String apply(IArith t) {
    return t.accept(this);
  }
}

class DoublerVisitor implements IArithVisitor<IArith> {
  public IArith visitConst(Const c) {
    return new Const(c.num * 2);
  }

  public IArith visitUnaryFormula(UnaryFormula uf) {
    return new UnaryFormula(uf.func, uf.name, new DoublerVisitor().apply(uf.child));
  }

  public IArith visitBinaryFormula(BinaryFormula bf) {
    return new BinaryFormula(bf.func, bf.name, new DoublerVisitor().apply(bf.left),
        new DoublerVisitor().apply(bf.right));
  }

  public IArith apply(IArith t) {
    return t.accept(this);
  }
}

class NoNegativeResults implements IArithVisitor<Boolean> {
  public Boolean visitConst(Const c) {
    return c.num >= 0;
  }

  public Boolean visitUnaryFormula(UnaryFormula uf) {
    return new NoNegativeResults().apply(uf.child) && (0 <= new EvalVisitor().apply(uf));
  }

  public Boolean visitBinaryFormula(BinaryFormula bf) {
    return new NoNegativeResults().apply(bf.left) && new NoNegativeResults().apply(bf.right)
        && (0 <= new EvalVisitor().apply(bf));
  }

  public Boolean apply(IArith t) {
    return t.accept(this);
  }
}

interface IArith {
  <R> R accept(IArithVisitor<R> visitor);
}

class Const implements IArith {
  double num;

  Const(double num) {
    this.num = num;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitConst(this);
  }
}

class UnaryFormula implements IArith {
  Function<Double, Double> func;

  String name;

  IArith child;

  UnaryFormula(Function<Double, Double> func, String name, IArith child) {
    this.func = func;
    this.name = name;
    this.child = child;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitUnaryFormula(this);
  }
}

class BinaryFormula implements IArith {
  BiFunction<Double, Double, Double> func;
  String name;
  IArith left;
  IArith right;

  BinaryFormula(BiFunction<Double, Double, Double> func, String name, IArith left, IArith right) {
    this.func = func;
    this.name = name;
    this.left = left;
    this.right = right;
  }

  public <R> R accept(IArithVisitor<R> visitor) {
    return visitor.visitBinaryFormula(this);
  }
}

class ExamplesArith {
  IArithVisitor<Double> eval = new EvalVisitor();
  IArithVisitor<String> print = new PrintVisitor();
  IArithVisitor<IArith> doubler = new DoublerVisitor();
  IArithVisitor<Boolean> noNeg = new NoNegativeResults();

  // formulas
  Function<Double, Double> neg = new Negate();
  Function<Double, Double> sqr = new Square();
  BiFunction<Double, Double, Double> plus = new Plus();
  BiFunction<Double, Double, Double> minus = new Minus();
  BiFunction<Double, Double, Double> mul = new Multiply();
  BiFunction<Double, Double, Double> div = new Divide();

  IArith con1 = new Const(4);
  IArith con1Doubled = new Const(8);
  IArith con2 = new Const(3);
  IArith con2Doubled = new Const(6);
  IArith uNeg = new UnaryFormula(neg, "neg", con1);
  IArith uSqr = new UnaryFormula(sqr, "sqr", new Const(3));
  IArith biAdd = new BinaryFormula(plus, "plus", con1, con2);
  IArith biSub = new BinaryFormula(minus, "minus", con2, con1);
  IArith biMul = new BinaryFormula(mul, "mul", con1, con2);
  IArith biDiv = new BinaryFormula(div, "div", new Const(10), new Const(2));
  IArith biNested = new BinaryFormula(plus, "plus", biMul, biDiv);

  boolean testEval(Tester t) {
    return t.checkExpect(eval.apply(con1), 4.0) && t.checkExpect(eval.apply(uNeg), -4.0)
        && t.checkExpect(eval.apply(uSqr), 9.0) && t.checkExpect(eval.apply(biAdd), 7.0)
        && t.checkExpect(eval.apply(biSub), -1.0) && t.checkExpect(eval.apply(biMul), 12.0)
        && t.checkExpect(eval.apply(biDiv), 5.0) && t.checkExpect(eval.apply(biNested), 17.0);
  }

  boolean testPrint(Tester t) {
    return t.checkExpect(print.apply(con1), "4.0") && t.checkExpect(print.apply(uNeg), "(neg 4.0)")
        && t.checkExpect(print.apply(uSqr), "(sqr 3.0)")
        && t.checkExpect(print.apply(biAdd), "(plus 4.0 3.0)")
        && t.checkExpect(print.apply(biSub), "(minus 3.0 4.0)")
        && t.checkExpect(print.apply(biMul), "(mul 4.0 3.0)")
        && t.checkExpect(print.apply(biDiv), "(div 10.0 2.0)")
        && t.checkExpect(print.apply(biNested), "(plus (mul 4.0 3.0) (div 10.0 2.0))");
  }

  boolean testDoubler(Tester t) {
    return t.checkExpect(doubler.apply(con1), new Const(8.0))
        && t.checkExpect(doubler.apply(uNeg), new UnaryFormula(neg, "neg", con1Doubled))
        && t.checkExpect(doubler.apply(uSqr), new UnaryFormula(sqr, "sqr", new Const(6)))
        && t.checkExpect(doubler.apply(biAdd),
            new BinaryFormula(plus, "plus", con1Doubled, con2Doubled))
        && t.checkExpect(doubler.apply(biSub),
            new BinaryFormula(minus, "minus", con2Doubled, con1Doubled))
        && t.checkExpect(doubler.apply(biMul),
            new BinaryFormula(mul, "mul", con1Doubled, con2Doubled))
        && t.checkExpect(doubler.apply(biDiv),
            new BinaryFormula(div, "div", new Const(20), new Const(4)))
        && t.checkExpect(doubler.apply(biNested),
            new BinaryFormula(plus, "plus", new BinaryFormula(mul, "mul", con1Doubled, con2Doubled),
                new BinaryFormula(div, "div", new Const(20), new Const(4))));
  }

  boolean testNoNeg(Tester t) {
    return t.checkExpect(noNeg.apply(con1), true) && t.checkExpect(noNeg.apply(uNeg), false)
        && t.checkExpect(noNeg.apply(uSqr), true) && t.checkExpect(noNeg.apply(biAdd), true)
        && t.checkExpect(noNeg.apply(biSub), false) && t.checkExpect(noNeg.apply(biMul), true)
        && t.checkExpect(noNeg.apply(biDiv), true) && t.checkExpect(noNeg.apply(biNested), true);
  }
}
