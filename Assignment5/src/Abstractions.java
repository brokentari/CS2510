import javalib.funworld.WorldScene;

interface IPred<T> {
  boolean apply(T t);
}

interface IFunc<X, Y> {
  Y apply(X x);
}

interface IFunc2<A1, A2, R> {
  R apply(A1 arg1, A2 arg2);
}

interface ICompare<T> {
  boolean apply(T arg1, T arg2);
}

interface IList<T> {
  IList<T> filter(IPred<T> pred);

  <U> IList<U> map(IFunc<T, U> f);

  <U> U foldr(IFunc2<T, U, U> func, U base);

  IList<T> addElement(T t);

  IList<T> filter2(ICompare<T> func, T t);

  IList<T> append(IList<T> list2);

  <U> U generate(ICompare<T> comp, T t, IFunc2<T, U, U> func, U base);

  int length();
}

interface IGamePiece {
  IGamePiece move();

  WorldScene draw(WorldScene scene);

  boolean inContactWith(AGamePiece gp);

  public IList<AGamePiece> explodes();

}