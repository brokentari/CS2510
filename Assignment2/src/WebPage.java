import tester.Tester;

// represents a list of items
interface ILoItem {
  // calculate total sizes of all the images
  int getItemsSize();

  // counts the length of all the text in a website
  int getItemsLength();

  // generates a string of all the images in a webpage
  String getItemsImgNames();

}

// represents an item in a WebPage
interface IItem {
  // returns the size of an Image
  int getSize();

  // returns the length of all text associated to an Item
  int getLength();

  // returns the name and file type of an Image file
  String getImgName();

}


// represents a website
class WebPage {
  String title;
  String url;
  ILoItem items;

  WebPage(String title, String url, ILoItem items) {
    this.title = title;
    this.url = url;
    this.items = items;
  }

  /*
   * fields:
   *  this.title ... String
   *  this.url ... String
   *  this.items ... ILoItem
   * methods
   *  this.totalImageSize() ... int
   *  this.textLength() ... int
   *  this.images() ... String
   * methods for fields
   *  this.items.getItemsSize() ... int
   *  this.items.getItemsLength() ... int
   *  this.items getItemsImgName() ... String
   */

  //returns the size of an Image
  int totalImageSize() {
    return items.getItemsSize();
  }

  //returns the length of all text associated to an Item
  int textLength() {
    return this.title.length() + this.items.getItemsLength();
  }

  String images() {
    return this.items.getItemsImgNames();
  }
}

class Text implements IItem {
  String contents;

  Text(String contents) {
    this.contents = contents;
  }

  /*
   * fields:
   *  this.contents ... String
   * methods:
   *  this.getSize() ... int
   *  this.getLength() ... int
   *  this.getImgName() ... int
   */

  //returns the size of an Image
  public int getSize() {
    return 0;
  }

  //returns the length of all text associated to an Item
  public int getLength() {
    return this.contents.length();
  }

  // returns the name and file type of an Image file
  public String getImgName() {
    return "";
  }
}

class Image implements IItem {
  String fileName;
  int size;
  String fileType;

  Image(String fileName, int size, String fileType) {
    this.fileName = fileName;
    this.size = size;
    this.fileType = fileType;
  }

  /*
   * fields:
   *  this.fileName ... String
   *  this.size ... int
   *  this.fileType ... String
   * methods:
   *  this.getSize() ... int
   *  this.getLength() ... int
   *  this.getImgName() ... int
   */

  //returns the size of an Image
  public int getSize() {
    return this.size;
  }

  //returns the length of all text associated to an Item
  public int getLength() {
    return this.fileName.length() + this.fileType.length();
  }

  // returns the name and file type of an Image file
  public String getImgName() {
    return fileName + "." + fileType;
  }
}

class Link implements IItem {
  String name;
  WebPage page;

  Link(String name, WebPage page) {
    this.name = name;
    this.page = page;
  }

  /*
   * fields:
   *  this.name ... String
   *  this.page ... WebPage
   * methods:
   *  this.getSize() ... int
   *  this.getLength() ... int
   *  this.getImgName() ... int
   * methods for fields:
   *  this.totalImageSize() ... int
   *  this.textLength() ... int
   *  this.images() ... String 
   */

  //returns the size of an Image
  public int getSize() {
    return this.page.totalImageSize();
  }

  //returns the length of all text associated to an Item
  public int getLength() {
    return this.name.length() + this.page.textLength();
  }

  // returns the name and file type of an Image file
  public String getImgName() {
    return this.page.images();
  }
}

// represents an empty item
class MtLoItem implements ILoItem {
  MtLoItem() {
  }

  /*
   * methods:
   *  this.getItemsSize() ... int
   *  this.getItemsLength() ... int
   *  this.getItemsImgNames() ... String
   */

  //calculate total sizes of all the images
  public int getItemsSize() {
    return 0;
  }

  //counts the length of all the text in a website
  public int getItemsLength() {
    return 0;
  }

  // generates a string of all the images in a webpage
  public String getItemsImgNames() {
    return "";
  }
}

// represents an item and the rest of the list
class ConsLoItem implements ILoItem {
  IItem first;
  ILoItem rest;

  ConsLoItem(IItem first, ILoItem rest) {
    this.first = first;
    this.rest = rest;
  }

  /*
   * fields:
   *  this.first ... IItem
   *  this.rest ... IItem
   * methods:
   *  this.getItemsSize() ... int
   *  this.getItemsLength() ... int
   *  this.getItemsImgNames() ... String
   * methods for fields
   *  this.first.getSize() ... int
   *  this.first.getLength() ... int
   *  this.first.getImgName() ... int
   *  this.rest.getItemsSize() ... int
   *  this.rest.getItemsLength() ... int
   *  this.rest.getItemsImgNames() ... String
   */

  //calculate total sizes of all the images
  public int getItemsSize() {
    return this.first.getSize() + this.rest.getItemsSize();
  }

  //counts the length of all the text in a website
  public int getItemsLength() {
    return this.first.getLength() + this.rest.getItemsLength();
  }

  // generates a string of all the images in a webpage
  public String getItemsImgNames() {
    if (this.rest.getItemsImgNames().equals("")) {
      return this.first.getImgName().replace(", ", "");
    }
    else if (this.first.getImgName().equals("")) {
      return "" + this.rest.getItemsImgNames();
    }
    else {
      return this.first.getImgName().concat(", ").concat(this.rest.getItemsImgNames());
    }
  }
}

class ExamplesWebPage {
  ILoItem emptyItem = new MtLoItem();

  // Example webpage
  IItem exampleText = new Text("Example Webpage");
  IItem exampleImgOne = new Image("dog", 560, "jpeg");
  IItem exampleImgTwo = new Image("cat", 700, "gif");
  IItem exampleLinkOne = new Link("void", null);
  IItem exampleLinkTwo = new Link("local", new WebPage("local", "192.168.1.1", emptyItem));
  IItem exampleLinkThree = new Link("construction", null);
  ILoItem exampleItems = new ConsLoItem(exampleText,
      new ConsLoItem(exampleImgOne, new ConsLoItem(exampleImgTwo,
          new ConsLoItem(exampleLinkOne, new ConsLoItem(exampleLinkTwo, 
              new ConsLoItem(exampleLinkThree, emptyItem))))));
  WebPage examplePage = new WebPage("Example", "example.net", exampleItems);

  // HtDP webpage
  IItem htdpText = new Text("How to Design Programs");
  IItem htdpImg = new Image("htdp", 4300, "tiff");
  ILoItem htdpItems = new ConsLoItem(htdpText, new ConsLoItem(htdpImg, emptyItem));
  WebPage htdpPage = new WebPage("HtDP", "htdp.org", htdpItems);

  // OOD webpage
  IItem oodText = new Text("Stay classy, Java");
  IItem oodLink = new Link("Back to the Future", htdpPage);
  ILoItem oodItems = new ConsLoItem(oodText, new ConsLoItem(oodLink, emptyItem));
  WebPage oodPage = new WebPage("OOD", "ccs.neu.edu/OOD", oodItems);

  // Fundies II webpage
  IItem fundiesTextOne = new Text("Home sweet home");
  IItem fundiesTextTwo = new Text("The staff");
  IItem fundiesImgOne = new Image("wvh-lab", 400, "png");
  IItem fundiesImgTwo = new Image("profs", 240, "jpeg");
  IItem fundiesLinkOne = new Link("A Look Back", htdpPage);
  IItem fundiesLinkTwo = new Link("A Look Ahead", oodPage);
  ILoItem fundiesItems = new ConsLoItem(fundiesTextOne,
      (new ConsLoItem(fundiesImgOne, new ConsLoItem(fundiesTextTwo, new ConsLoItem(fundiesImgTwo,
          new ConsLoItem(fundiesLinkOne, new ConsLoItem(fundiesLinkTwo, emptyItem)))))));
  WebPage fundiesWP = new WebPage("Fundies II", "ccs.neu.edu/Fundies2", fundiesItems);

  // Tests
  // test for getting the image size
  boolean testGetImgSize(Tester t) {
    return t.checkExpect(exampleImgOne.getSize(), 560)
        && t.checkExpect(htdpImg.getSize(), 4300)
        && t.checkExpect(fundiesImgTwo.getSize(), 240);
  }

  // test for getting content length
  boolean testGetLength(Tester t) {
    return t.checkExpect(exampleText.getLength(), 15)
        && t.checkExpect(htdpImg.getLength(), 8)
        && t.checkExpect(fundiesTextTwo.getLength(), 9);
  }

  // test for getting image name
  boolean testGetImgName(Tester t) {
    return t.checkExpect(exampleImgOne.getImgName(), "dog.jpeg") 
        && t.checkExpect(htdpImg.getImgName(), "htdp.tiff") 
        && t.checkExpect(fundiesLinkOne.getImgName(), "htdp.tiff");
  }

  // test for calculating total image size
  boolean testImgSize(Tester t) {
    return t.checkExpect(oodPage.totalImageSize(), 4300)
        && t.checkExpect(htdpPage.totalImageSize(), 4300)
        && t.checkExpect(fundiesWP.totalImageSize(), 9240);
  }

  // test for counting all the letters in a webpage
  boolean testLength(Tester t) {
    return t.checkExpect(htdpPage.textLength(), 34) && t.checkExpect(oodPage.textLength(), 72)
        && t.checkExpect(fundiesWP.textLength(), 182);
  }

  // test for generating a string of image names
  boolean testImgNames(Tester t) {
    return t.checkExpect(fundiesWP.images(), "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff");
  }
}
