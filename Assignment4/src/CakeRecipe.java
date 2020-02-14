import tester.*;

// represents a cake recipe
class CakeRecipe {
  double flour;
  double sugar;
  double egg;
  double butter;
  double milk;

  // main constructor for creating a cake
  CakeRecipe(double flour, double sugar, double egg, double butter, double milk) {
    /*- TEMPLATE
    FIELDS:
    ... this.flour    ...         -- double
    ... this.sugar    ...         -- double
    ... this.egg      ...         -- double
    ... this.butter   ...         -- double
    ... this.milk     ...         -- double
    METHODS:
    ... this.sameRecipe() ...     -- boolean
    ... ingredient(double, double, String) ... - double
     */
    this.flour = new Utils().ingredient(flour, sugar, "Flour and sugar weights are not equal.");
    this.sugar = this.flour;
    this.egg = new Utils().ingredient(egg, butter, "Egg and butter weights are not equal.");
    this.butter = this.egg;
    this.milk = new Utils().ingredient(milk, flour - egg,
        "The flour weight is not equal to the total of the milk and egg weights.");
  }

  // constructor for flour, egg, and milk only
  CakeRecipe(double flour, double egg, double milk) {
    /*- TEMPLATE
    FIELDS:
    ... this.flour    ...         -- double
    ... this.egg      ...         -- double
    ... this.milk     ...         -- double
    METHODS:
    ... this.sameRecipe() ...     -- boolean
    ... ingredient(double, double, String) ... - double
     */
    this(flour, flour, egg, egg, milk);
  }

  // constructor for volume arguements
  CakeRecipe(double flour, double egg, double milk, boolean areVolumes) {
    /*- TEMPLATE
    FIELDS:
    ... this.flour    ...         -- double
    ... this.egg      ...         -- double
    ... this.milk     ...         -- double
    ... this.areVolumes ...       -- boolean
    METHODS:
    ... this.sameRecipe() ...     -- boolean
    ... ingredient(double, double, String) ... - double
     */
    this(new Utils().checkVolume(flour, 4.25, areVolumes),
        new Utils().checkVolume(flour, 4.25, areVolumes),
        new Utils().checkVolume(egg, 1.75, areVolumes),
        new Utils().checkVolume(egg, 1.75, areVolumes),
        new Utils().checkVolume(milk, 8.0, areVolumes));
  }

  // checks if this and the given recipe have equal amount
  // of ingredients
  boolean sameRecipe(CakeRecipe other) {
    /*- TEMPLATE
       FIELDS OF PARAMETERS:
       ... other.flour    ...         -- double
       ... other.sugar    ...         -- double
       ... other.egg      ...         -- double
       ... other.butter   ...         -- double
       ... other.milk     ...         -- double 
       ... other.areVolumes ...       -- boolean 
       PARAMETERS:
       ... other    ...         -- CakeRecipe
       METHODS FOR PARAMETERS
       ... other.sameRecipe()   -- boolean
     */
    double epsilon = 0.001;
    return ((Math.abs(this.flour - other.flour) < epsilon)
        && (Math.abs(this.egg - other.egg) < epsilon)
        && (Math.abs(this.milk - other.milk) < epsilon));
  }
}

// utility class
class Utils {
  Utils() {
  }
  /*
   * TEMPLATE Methods: ... ingredient(double, double, String) ... - double ...
   * checkVolume(double, double, boolean) ... - double
   */

  public double ingredient(double ingredient1, double ingredient2, String msg) {
    double epsilon = 0.001;
    if (Math.abs(ingredient1 - ingredient2) < epsilon) {
      return ingredient1;
    }
    else {
      throw new IllegalArgumentException(msg);
    }
  }

  public double checkVolume(double ingredient, double conversion, boolean areVolumes) {
    if (areVolumes) {
      return ingredient * conversion;
    }
    return ingredient;
  }

}

class ExamplesCakes {
  ExamplesCakes() {
  }

  Utils util = new Utils();
  CakeRecipe cake1 = new CakeRecipe(7, 7, 6, 6, 1);
  CakeRecipe cake2 = new CakeRecipe(7, 7, 6, 6, 1);
  CakeRecipe cake3 = new CakeRecipe(6, 5, 1);
  CakeRecipe cake4 = new CakeRecipe(4, 2, 2);
  CakeRecipe cake5 = new CakeRecipe(4, 2, 2, false);
  CakeRecipe cake6 = new CakeRecipe(2.294, 1, 1, true);

  // test for utility class
  boolean testIngredient(Tester t) {
    return t.checkExpect(util.checkVolume(5, 4.25, true), 21.25)
        && t.checkExpect(util.checkVolume(5, 4.25, false), 5.0)
        && t.checkExpect(util.checkVolume(10, 8, true), 80.0)
        && t.checkExpect(util.ingredient(50, 50, "error"), 50.0) && t.checkException(
            new IllegalArgumentException("error"), util, "ingredient", 30.0, 40.0, "error");
  }

  // tests for checking if two recipes are the same
  boolean testSameRecipe(Tester t) {
    return t.checkExpect(this.cake1.sameRecipe(cake2), true)
        && t.checkExpect(this.cake1.sameRecipe(cake3), false)
        && t.checkExpect(this.cake1.sameRecipe(cake4), false)
        && t.checkExpect(this.cake4.sameRecipe(cake5), true)
        && t.checkExpect(this.cake4.sameRecipe(cake6), false);
  }

  // test for catching invalid arguments on CakeRecipe constructor
  boolean testConstructorException(Tester t) {
    return t.checkConstructorException(
        new IllegalArgumentException("Flour and sugar weights are not equal."), "CakeRecipe", 3.0,
        6.0, 7.0, 6.0, 7.0)
        && t.checkConstructorException(
            new IllegalArgumentException("Flour and sugar weights are not equal."), "CakeRecipe",
            1.0, 2.0, 3.0, 4.0, 5.0)
        && t.checkConstructorNoException("Valid CakeRecipe", "CakeRecipe", 7.0, 7.0, 6.0, 6.0, 1.0);
  }
}