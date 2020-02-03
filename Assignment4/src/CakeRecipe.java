import tester.Tester;

class CakeRecipe {
  double flour;
  double sugar;
  double egg;
  double butter;
  double milk;
  boolean areVolumes = false;

  CakeRecipe() {
    this.flour = 0;
    this.sugar = 0;
    this.egg = 0;
    this.butter = 0;
    this.milk = 0;
    this.areVolumes = false;
  }

  CakeRecipe(double flour, double sugar, double egg, double butter, double milk,
      boolean areVolumes) {
    final double epsilon = 0.1;
    if (areVolumes) {
      flour = flour * 4.25;
      sugar = sugar * 7;
      egg = egg * 1.75;
      butter = butter * 8;
      milk = milk * 8;
    }

    if ((Math.abs(flour - sugar) < epsilon)) {
      this.flour = flour;
      this.sugar = sugar;
    }
    else {
      throw new IllegalArgumentException("Flour and sugar weights are not equal.");
    }
    if ((Math.abs(egg - butter) < epsilon)) {
      this.egg = egg;
      this.butter = butter;
    }
    else {
      throw new IllegalArgumentException("Egg and butter weights are not equal.");
    }
    if ((Math.abs((egg + milk) - sugar) < epsilon)) {
      this.milk = milk;
    }
    else {
      throw new IllegalArgumentException(
          "The flour weight is not equal to the total" + " of the milk and egg weights.");
    }
    this.areVolumes = areVolumes;
  }

  CakeRecipe(double flourWeight, double sugarWeight, double eggWeight, double butterWeight,
      double milkWeight) {
    this(flourWeight, sugarWeight, eggWeight, butterWeight, milkWeight, false);
  }

  CakeRecipe(double flourWeight, double eggWeight, double milkWeight) {
    this(flourWeight, (eggWeight + milkWeight), eggWeight, eggWeight, milkWeight, false);
  }

  CakeRecipe(double flourWeight, double eggWeight, double milkWeight, boolean areVolumes) {
    this(flourWeight, (eggWeight + milkWeight), eggWeight, eggWeight, milkWeight, areVolumes);
  }

  boolean sameRecipe(CakeRecipe other) {
    final double epsilon = 0.001;
    return ((Math.abs(this.flour - other.flour) < epsilon)
        && (Math.abs(this.sugar - other.sugar) < epsilon)
        && (Math.abs(this.egg - other.egg) < epsilon)
        && (Math.abs(this.butter - other.butter) < epsilon)
        && (Math.abs(this.milk - other.milk) < epsilon));
  }
}

class ExamplesCakes {
  CakeRecipe cake1 = new CakeRecipe(7, 7, 6, 6, 1);
  CakeRecipe cake2 = new CakeRecipe(7, 7, 6, 6, 1);
  CakeRecipe cake3 = new CakeRecipe(1.647, 1, 3.4285, 0.75, 0.125, true);
  CakeRecipe cake4 = new CakeRecipe(4, 2, 2);
  CakeRecipe cake5 = new CakeRecipe(1, 1, 1, 1, 1);

  boolean testSame(Tester t) {
    return t.checkExpect(this.cake1.sameRecipe(cake2), true)
        && t.checkExpect(this.cake1.sameRecipe(cake3), true)
        && t.checkExpect(this.cake1.sameRecipe(cake4), false);
  }
}