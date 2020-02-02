class CakeRecipe {
  double flourWeight;
  double sugarWeight;
  double eggWeight;
  double butterWeight;
  double milkWeight;
  boolean areVolumes = false;
  
  CakeRecipe(double flourWeight, double sugarWeight, double eggWeight, double butterWeight, 
      double milkWeight, boolean areVolumes) {
    if (areVolumes) {
      flourWeight = flourWeight * 4.25;
      sugarWeight = sugarWeight * 7;
      eggWeight = eggWeight * 1.75;
      butterWeight = butterWeight * 8;
      milkWeight = milkWeight * 8;
    }
      
    if (flourWeight == sugarWeight) {
      this.flourWeight = flourWeight;
      this.sugarWeight = sugarWeight;
    }
    else {
      throw new IllegalArgumentException("Flour and sugar weights are not equal.");
    }
    if (eggWeight == butterWeight) {
      this.eggWeight = eggWeight;
      this.butterWeight = butterWeight;
    }
    else {
      throw new IllegalArgumentException("Egg and butter weights are not equal.");
    }
    if ((eggWeight + milkWeight) == sugarWeight) {
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