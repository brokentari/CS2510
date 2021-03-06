// PROBELM 3

interface IHousing {
  
}

class Hut implements IHousing {
  int capacity;
  int population;
  
  Hut(int capacity, int population) {
    this.capacity = capacity;
    this.population = population;
  }
}

class Inn implements IHousing {
  String name;
  int capacity;
  int population;
  int stalls;
  
  Inn(String name, int capacity, int population, int stalls) {
    this.name = name;
    this.capacity = capacity;
    this.population = population;
    this.stalls = stalls;
  }
}

class Castle implements IHousing {
  String name;
  String familyName;
  int population;
  int carriageHouse;
  
  Castle(String name, String familyName, int population, int carriageHouse) {
    this.name = name;
    this.familyName = familyName;
    this.population = population;
    this.carriageHouse = carriageHouse;
  }
}

interface ITransportation {
  
}

class Horse implements ITransportation {
  String name;
  String color;
  IHousing to;
  IHousing from;
  
  Horse(IHousing to, IHousing from, String name, String color) {
    this.name = name;
    this.color = color;
    this.to = to;
    this.from = from;
  }
}

class Carriage implements ITransportation {
  int tonnage;
  IHousing to;
  IHousing from;
  
  Carriage(IHousing to, IHousing from, int tonnage) {
    this.tonnage = tonnage;
    this.to = to;
    this.from = from;
  }
}

class ExamplesTravel {
  IHousing hovel = new Hut(5, 1);
  IHousing winterfell = new Castle("Winterfell", "Stark", 500, 6);
  IHousing crossroads = new Inn("Inn At The Crossroads", 40, 20, 12);
  IHousing chief = new Hut(10, 2);
  IHousing edinburgh = new Castle("Edinburgh", "Mwynfawr", 700, 10);
  IHousing giant = new Inn("Sleeping Giant Inn", 50, 10, 15);
  
  ITransportation horse1 = new Horse(this.edinburgh, this.giant, "Pegasus", "white");
  ITransportation horse2 = new Horse(this.hovel, this.winterfell, "Donkey", "grey");
  ITransportation carriage1 = new Carriage(this.crossroads, this.edinburgh, 2);
  ITransportation carriage2 = new Carriage(this.winterfell, this.giant, 1);
}