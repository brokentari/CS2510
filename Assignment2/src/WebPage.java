import tester.Tester;

interface ILoItem {
  int getItemsSize();

  int getItemsLength();

  String getItemsImgNames();

}

interface IItem {
  int getSize();

  int getLength();

  String getImgName();
  
}

class WebPage {
  String title;
  String url;
  ILoItem items;

  WebPage(String title, String url, ILoItem items) {
    this.title = title;
    this.url = url;
    this.items = items;
  }

  int totalImageSize() {
    return items.getItemsSize();
  }

  int textLength() {
    return this.title.length() + this.items.getItemsLength();
  }

  String images() {
    String images_str = this.items.getItemsImgNames();
    return images_str.substring(0, this.items.getItemsImgNames().length() - 1);
  }
}

class Text implements IItem {
  String contents;

  Text(String contents) {
    this.contents = contents;
  }

  @Override
  public int getSize() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getLength() {
    // TODO Auto-generated method stub
    return this.contents.length();
  }

  @Override
  public String getImgName() {
    // TODO Auto-generated method stub
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

  @Override
  public int getSize() {
    // TODO Auto-generated method stub
    return this.size;
  }

  @Override
  public int getLength() {
    // TODO Auto-generated method stub
    return this.fileName.length() + this.fileType.length();
  }

  @Override
  public String getImgName() {
    // TODO Auto-generated method stub
    return fileName + "." + fileType + ", ";
  }

}

class Link implements IItem {
  String name;
  WebPage page;

  Link(String name, WebPage page) {
    this.name = name;
    this.page = page;
  }

  @Override
  public int getSize() {
    // TODO Auto-generated method stub
    return this.page.totalImageSize();
  }

  @Override
  public int getLength() {
    // TODO Auto-generated method stub
    return this.name.length() + this.page.textLength();
  }

  @Override
  public String getImgName() {
    // TODO Auto-generated method stub
    return this.page.images();
  }

}

class MtLoItem implements ILoItem {
  MtLoItem() {
  }

  @Override
  public int getItemsSize() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public int getItemsLength() {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public String getItemsImgNames() {
    // TODO Auto-generated method stub
    return "";
  }
}

class ConsLoItem implements ILoItem {
  IItem first;
  ILoItem rest;

  ConsLoItem(IItem first, ILoItem rest) {
    this.first = first;
    this.rest = rest;
  }


  @Override
  public int getItemsSize() {
    // TODO Auto-generated method stub
    return this.first.getSize() + this.rest.getItemsSize();
  }

  @Override
  public int getItemsLength() {
    // TODO Auto-generated method stub
    return this.first.getLength() + this.rest.getItemsLength();
  }

  @Override
  public String getItemsImgNames() {
    // TODO Auto-generated method stub
      return this.first.getImgName().concat(this.rest.getItemsImgNames());
  }
}

class ExamplesWebPage {
  ILoItem emptyItem = new MtLoItem();

  // HtDP webapge
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
  boolean testImgSize(Tester t) {
    return t.checkExpect(oodPage.totalImageSize(), 4300)
        && t.checkExpect(htdpPage.totalImageSize(), 4300)
        && t.checkExpect(fundiesWP.totalImageSize(), 9240);
  }

  boolean testLength(Tester t) {
    return t.checkExpect(htdpPage.textLength(), 34) 
        && t.checkExpect(oodPage.textLength(), 72)
        && t.checkExpect(fundiesWP.textLength(), 182);
  }

  boolean testImgNames(Tester t) {
    return t.checkExpect(htdpPage.images(), "htdp.tiff")
        && t.checkExpect(oodPage.images(), "htdp.tiff")
        && t.checkExpect(fundiesWP.images(), "wvh-lab.png, profs.jpeg, htdp.tiff, htdp.tiff");
  }

}
