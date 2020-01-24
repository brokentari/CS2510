interface Deli { }

class Soup implements Deli {
  String name;
  int price;
  boolean vegetarian;
  
  Soup(String name, int price, boolean vegetarian) {
    this.name = name;
    this.price = price;
    this.vegetarian = vegetarian;
  }
}

class Salad implements Deli {
  String name;
  int price;
  boolean vegetarian;
  
  Salad(String name, int price, boolean vegetarian) {
    this.name = name;
    this.price = price;
    this.vegetarian = vegetarian;
  }
}

class Sandwich implements Deli {
  String name;
  int price;
  String bread;
  Fillings fillings;
  
  Sandwich(String name, int price, String bread, Fillings fillings) {
    this.name = name;
    this.price = price;
    this.bread = bread;
    this.fillings = fillings;
  }
}

class Fillings {
  String filling1;
  String filling2;
  
  Fillings(String filling1, String filling2) {
    this.filling1 = filling1;
    this.filling2 = filling2;
  }
}

class ExamplesDeli{
  Fillings fil1 = new Fillings("peanut butter", "jelly");
  Fillings fil2 = new Fillings("ham", "cheese");
  
  Salad sal1 = new Salad("Caesar", 50, true);
  Salad sal2 = new Salad("Meat", 25, false);
  
  Soup sou1 = new Soup("Chicken", 75, true);
  Soup sou2 = new Soup("Broth", 10, false);
  
  Sandwich san1 = new Sandwich("PB&J", 20, "whole", fil1);
  Sandwich san2 = new Sandwich("subway", 40, "italian", fil2);
  
}