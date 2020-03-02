import java.util.function.BiFunction;
import java.util.function.Function;

interface IArith {
  
}

class Const implements IArith {
  double num;
}

class UnaryFormula implements IArith {
  Function<Double, Double> func;
 
  String name;
  
  IArith child;
}


class BinaryFormula implements IArith {
  BiFunction<Double, Double, Double> func;
  String name;
  IArith left;
  IArith right;
}