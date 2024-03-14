import java.util.ArrayList;

import java.util.Scanner;

/**
 * library
 */
public class library {
  ArrayList<AccountInfo> accountDB = new ArrayList<>();
  ArrayList<BookInfo> bookDB = new ArrayList<>();
  public ArrayList<BookInfo> getBookDB() {
    return bookDB;
  }
  public ArrayList<AccountInfo> getAccountDB() {
    return accountDB;
  }
  public static void main(String[] args) {
    library library = new library();
    Scanner scan = new Scanner(System.in);
    ArrayList<AccountInfo> accountDB = library.getAccountDB();
    ArrayList<BookInfo> bookDB = library.getBookDB();
    AccountOperation accountOperation = new AccountOperation();
    BookOperation bookOperation = new BookOperation();
    AccountInfo currentAccount = new AccountInfo(0, new String(), new String(), 0);
    Hud hud = new Hud();
    Boolean exit = false;
    while (!exit) {
      hud.preMenu();
      switch (scan.nextInt()) {
        case 1:accountOperation.register(accountDB);break;
        case 2:currentAccount = accountOperation.login(accountDB); exit = true;break;
        default:break;
      }
    }
    exit = false;
    while (!exit) {  
      hud.mainMenu();
      switch (scan.nextInt()) {
        case 1: bookOperation.traBook(bookDB);break;
        case 2: bookOperation.landBook(currentAccount, bookDB);break;
        case 3: bookOperation.returnBook(currentAccount, bookDB);break;
        case 5: exit = true;break;
        case 4: 
        Boolean exitadmin = false;
        while (!exitadmin) {
          hud.adminMenu();
          switch (scan.nextInt()) {
            case 1:bookOperation.addBook(currentAccount, bookDB);exitadmin = true;break;
            case 2:bookOperation.delBook(currentAccount, bookDB);exitadmin = true;break;
            case 3:bookOperation.editBook(currentAccount, bookDB);exitadmin = true;break;
            case 4:exitadmin = true;break;
            default:
              break;
          }
        }
        default:
          break;
      }
    }
  scan.close();
  }
}

class Hud {
  void preMenu() {
    System.out.println("\t\t\t**************************************");
    System.out.println("\t\t\t*         图书管理系统v0.1           *");
    System.out.println("\t\t\t*                                    *");
    System.out.println("\t\t\t**************************************");
    System.out.println("\t\t\t             1.注册");
    System.out.println("\t\t\t             2.登陆");
    System.out.println("\t\t\t请输入选项:");
  }
  void mainMenu() {
    System.out.println("\t\t\t**************************************");
    System.out.println("\t\t\t*         图书管理系统v0.1           *");
    System.out.println("\t\t\t*                                    *");
    System.out.println("\t\t\t**************************************");
    System.out.println("\t\t\t              1.查找图书");
    System.out.println("\t\t\t              2.借用图书");
    System.out.println("\t\t\t              3.归还图书");
    System.out.println("\t\t\t              4.管理员菜单");
    System.out.println("\t\t\t              5.退出程序");
    System.out.println("\t\t\t请输入选项:");
  }
  void adminMenu() {
    System.out.println("\t\t\t**************************************");
    System.out.println("\t\t\t*         图书管理系统管理员菜单     *");
    System.out.println("\t\t\t*                                    *");
    System.out.println("\t\t\t**************************************");
    System.out.println("\t\t\t              1.添加图书");
    System.out.println("\t\t\t              2.删除图书");
    System.out.println("\t\t\t              3.编辑图书");
    System.out.println("\t\t\t              4.退出菜单");
    System.out.println("\t\t\t请输入选项:");
  }
}

class AccountOperation {
  void register(ArrayList<AccountInfo> accountDB) {
    Scanner scan = new Scanner(System.in);
    String usrname = new String();
    String passwd = new String();
    char authorOpt;
    int author = 0;
    boolean exit = false;
    System.out.println("\t\t\t请输入用户名:");
    usrname = scan.next();
    System.out.println("\t\t\t请输入密码:");
    passwd = scan.next();
    while (!exit) {
      System.out.println("\t\t\t是否注册为管理员(Y/N):");
      authorOpt = scan.next().charAt(0);
      System.out.printf("输入为%c", authorOpt);
      switch (authorOpt) {
        case 'Y': author = 1;exit = true;break;
        case 'N': exit = true;break;
        default:System.out.println("请重新输入！");break;
      }
    }
    
    AccountInfo newUsr = new AccountInfo(accountDB.size(), usrname, passwd, author);
    accountDB.add(newUsr);
  }
   AccountInfo login(ArrayList<AccountInfo> accountDB) {
    while (true) {
      Scanner scan = new Scanner(System.in);
      AccountInfo accountInfo;
      int id;
      String passwd = new String();
      System.out.println("\t\t\t请输入账户(id):");
      id = scan.nextInt();
      System.out.println("\t\t\t请输入密码:");
      passwd = scan.next();
      
      if(accountDB.size() <= id || id < 0) {
        System.out.println("\t\t\t不存在此账户!");
        continue;
      }
      if(!passwd.equals(accountDB.get(id).passwd)) {
        System.out.println("\t\t\t密码错误!");
      }
      accountInfo = accountDB.get(id);
      return accountInfo;
    }
  }
}

class BookOperation {
  int idSelector(ArrayList<BookInfo> bookDB) {
    Scanner scan = new Scanner(System.in);
    int id;
    while (true) {
      traBook(bookDB);
      System.out.println("请输入需要操作的书籍id:");
      id = scan.nextInt();
      if (id >= bookDB.size() || id < 0) {
        System.out.println("请输入正确的书籍id:");
        continue;
      }
      break;
    }
    
    return id;
  }
  void addBook(AccountInfo accountInfo, ArrayList<BookInfo> bookDB) {
    if(accountInfo.author == 0) {
      System.out.println("错误！权限不足！");
      return;
    }
    String name = new String();
    String author = new String();
    Scanner scan = new Scanner(System.in);
    System.out.println("请输入名称:");
    name = scan.next();
    System.out.println("请输入作者:");
    author = scan.next();
    BookInfo newBook = new BookInfo(bookDB.size(), name, author);
    bookDB.add(newBook);
    
  } 
  void traBook(ArrayList<BookInfo> bookDB) {
    System.out.println("\t\t\tid      name      author");
    for(BookInfo book: bookDB) {
      if(book == null) {
        continue;
      }
      System.out.printf("\t\t\t%d      %s      %s\n", book.id, book.name, book.author);
    }
  }
  void delBook(AccountInfo accountInfo, ArrayList<BookInfo> bookDB) {
    if(accountInfo.author == 0) {
      System.out.println("错误！权限不足！");
      return;
    }
    bookDB.set(idSelector(bookDB), null);
}
  void editBook(AccountInfo accountInfo, ArrayList<BookInfo> bookDB) {
    if(accountInfo.author == 0) {
      System.out.println("错误！权限不足！");
      return;
    }
    Scanner scan = new Scanner(System.in);
    BookInfo currentBook = bookDB.get(idSelector(bookDB));
    System.out.println("请输入新的名字:");
    currentBook.name = scan.next();
    System.out.println("请输入新的作者:");
    currentBook.author = scan.next();
    System.out.println("请输入借出状态:");
    currentBook.island = scan.nextBoolean();
    
  }
  void landBook(AccountInfo accountInfo, ArrayList<BookInfo> bookDB) {
    BookInfo currentBook;
    while (true) {
      currentBook = bookDB.get(idSelector(bookDB));
      if (currentBook.island) {
        System.out.println("该书已借出");
        continue;
      }
      break;
    }
    currentBook.island = true;
    accountInfo.landList.add(currentBook.id);
  }

  void returnBook(AccountInfo accountInfo, ArrayList<BookInfo> bookDB) {
    BookInfo currentBook;
    int bookid;
    while (true) {
      currentBook = bookDB.get(idSelector(bookDB));
      if (currentBook.island == false || accountInfo.landList.contains(currentBook.id)) {
        System.out.println("您没有借走此书籍");
        continue;
      }
      break;
    }
    bookid = currentBook.id;
    currentBook.island = false;
    accountInfo.landList.removeIf(id -> id.equals(bookid));
  }
}

class AccountInfo {
  int id;
  String name;
  String passwd;
  int author;
  ArrayList<Integer> landList;
  public AccountInfo(int id, String name, String passwd, int author) {
    this.id = id;
    this.name = name;
    this.author = author;
    this.passwd = passwd;
  }
}

class BookInfo {
  int id;
  String name;
  String author;
  Boolean island;
  public BookInfo(int id, String name, String author) {
    this.id = id;
    this.name = name;
    this.author = author;
  }
}


