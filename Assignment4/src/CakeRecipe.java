import tester.Tester;

class CakeRecipe {
  double flourWeight;
  double sugarWeight;
  double eggWeight;
  double butterWeight;
  double milkWeight;
  boolean areVolumes = false;
  
  CakeRecipe() {
    this.flourWeight = 0;
    this.sugarWeight = 0;
    this.eggWeight = 0;
    this.butterWeight = 0;
    this.milkWeight = 0;
  }
  
  CakeRecipe(double flourWeight, double sugarWeight, double eggWeight, double butterWeight, 
      double milkWeight, boolean areVolumes) {
    final double EPSILON = 0.1;
    if (areVolumes) {
      flourWeight = flourWeight * 4.25;
      sugarWeight = sugarWeight * 7;
      eggWeight = eggWeight * 1.75;
      butterWeight = butterWeight * 8;
      milkWeight = milkWeight * 8;
    }
      
    if ((Math.abs(flourWeight - sugarWeight) < EPSILON)) {
      this.flourWeight = flourWeight;
      this.sugarWeight = sugarWeight;
    }
    else {
      throw new IllegalArgumentException("Flour and sugar weights are not equal.");
    }
    if ((Math.abs(eggWeight - butterWeight) < EPSILON)) {
      this.eggWeight = eggWeight;
      this.butterWeight = butterWeight;
    }
    else {
      System.out.print("egg weight: " + sugarWeight +
          "\nbutter weight: " + butterWeight + "\n");
      throw new IllegalArgumentException("Egg and butter weights are not equal.");
    }
    if ((Math.abs((eggWeight + milkWeight) 
        - sugarWeight) < EPSILON)) {
      this.milkWeight = milkWeight;
    } else {
      throw new IllegalArgumentException("The flour weight is not equal to the total"
          + " of the milk and egg weights.");
    }
    this.areVolumes = areVolumes;
  }

  
  CakeRecipe(double flourWeight, double sugarWeight, double eggWeight, double butterWeight, 
      double milkWeight) {
    this(flourWeight, sugarWeight, eggWeight, butterWeight, milkWeight, false);
  }
  
  CakeRecipe (double flourWeight, double eggWeight, double milkWeight) {
    this(flourWeight, (eggWeight + milkWeight), eggWeight, eggWeight, milkWeight, false);
  }
  
  CakeRecipe(double flourWeight, double eggWeight, double milkWeight, boolean areVolumes) {
    this(flourWeight, (eggWeight + milkWeight), eggWeight, eggWeight, milkWeight, areVolumes);
  }
  
  boolean sameRecipe(CakeRecipe other) {
    final double EPSILON = 0.001;
    return ((Math.abs(this.flourWeight - other.flourWeight) < EPSILON)
        && (Math.abs(this.sugarWeight - other.sugarWeight) < EPSILON)
        && (Math.abs(this.eggWeight - other.eggWeight) < EPSILON)
        && (Math.abs(this.butterWeight - other.butterWeight) < EPSILON)
        && (Math.abs(this.milkWeight - other.milkWeight) < EPSILON));
  }
}
  
class ExamplesCakes {
  CakeRecipe cake1 = new CakeRecipe(7, 7, 6, 6, 1);
  CakeRecipe cake2 = new CakeRecipe(7, 7, 6, 6, 1);
  CakeRecipe cake3 = new CakeRecipe(1.647, 1, 3.4285, 0.75, 0.125, true);
  CakeRecipe cake4 = new CakeRecipe(4, 2, 2);
  
  boolean testSame(Tester t) {
    return t.checkExpect(this.cake1.sameRecipe(cake2), true)
        && t.checkExpect(this.cake1.sameRecipe(cake3), true)
        && t.checkExpect(this.cake1.sameRecipe(cake4), false);
  }
}