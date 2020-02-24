class MtList<T> implements IList<T> {
  /*- TEMPLATE:
   *
   * METHODS:
   * this.filter(IPred<T> pred) : IList<T>
   * this.map(IFunc<T, U> f) : IList<U>
   * this.foldr(IFunc<T, U, U> func, U base) : U
   * this.length() : int
   * this.addElement(T t) : IList<T>
   * this.filter2(ICompare<T> func, T t) : IList<T>
   * this.append(IList<T> list2) : IList<T>
   * this.generate(ICompare<T> comp, T t, IFunc2<T, U, U> func, U base) : U
   */
  public IList<T> filter(IPred<T> pred) {
    return this;
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new MtList<U>();
  }

  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    return base;
  }

  public int length() {
    return 0;
  }

  public IList<T> addElement(T t) {
    return new ConsList<T>(t, this);
  }

  public IList<T> filter2(ICompare<T> func, T t) {
    return this;
  }

  public IList<T> append(IList<T> list2) {
    return list2;
  }

  public <U> U generate(ICompare<T> comp, T t, IFunc2<T, U, U> func, U base) {
    return base;
  }
}

class ConsList<T> implements IList<T> {
  T first;
  IList<T> rest;

  ConsList(T first, IList<T> rest) {
    this.first = first;
    this.rest = rest;
  }

  /*- TEMPLATE:
   * a
   * FIELDS:
   * this.first : T
   * this.rest : IList<T>
   *
   * METHODS:
   * this.filter(IPred<T> pred) : IList<T>
   * this.map(IFunc<T, U> f) : IList<U>
   * this.foldr(IFunc<T, U, U> func, U base) : U
   * this.length() : int
   * this.addElement(T t) : IList<T>
   * this.filter2(ICompare<T> func, T t) : IList<T>
   * this.append(IList<T> list2) : IList<T>
   * this.generate(ICompare<T> comp, T t, IFunc2<T, U, U> func, U base) : U
   */

  public IList<T> filter(IPred<T> pred) {
    if (pred.apply(this.first)) {
      return new ConsList<T>(this.first, this.rest.filter(pred));
    }
    else {
      return this.rest.filter(pred);
    }
  }

  public <U> IList<U> map(IFunc<T, U> f) {
    return new ConsList<U>(f.apply(this.first), this.rest.map(f));
  }

  public <U> U foldr(IFunc2<T, U, U> func, U base) {
    return func.apply(this.first, this.rest.foldr(func, base));
  }

  public IList<T> addElement(T t) {
    return new ConsList<T>(t, this);
  }

  public int length() {
    return 1 + this.rest.length();
  }

  public IList<T> filter2(ICompare<T> func, T t) {
    if (func.apply(this.first, t)) {
      return this.rest.filter2(func, t);
    }
    else {
      return new ConsList<T>(this.first, this.rest.filter2(func, t));
    }
  }

  public IList<T> append(IList<T> list2) {
    return new ConsList<T>(this.first, this.rest.append(list2));
  }

  public <U> U generate(ICompare<T> comp, T t, IFunc2<T, U, U> func, U base) {
    if (comp.apply(this.first, t)) {
      return func.apply(this.first, this.rest.generate(comp, t, func, base));
    }
    else {
      return this.rest.generate(comp, t, func, base);
    }
  }
}
