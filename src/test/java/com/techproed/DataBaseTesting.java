package com.techproed;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.sql.*;

public class DataBaseTesting {
    /*
    1. Database baglanma
           connection=DriverManager.getConnection(“url”, “kullaniciAdi”, “sifre”);
    2.Query gonderip data alma
           statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    3. Bu dataları test caselerde kullanıp assertion yapma.  ResultSet objesiyle datayı kullanırız.
            resultSet = statement.executeQuery("SELECT * FROM Book;”);
            String beklenenDeger = resultSet.getString(“BookName”);
            Assert.assertEquals(beklenenDeger, gercekDeger);

                            HOST            PORT  DB Name
String url = "jdbc:mysql://107.182.225.121:3306/LibraryMgmt";
String username = "techpro";
String password = “tchpr2020";


     */
    String url = "jdbc:mysql://107.182.225.121:3306/LibraryMgmt";  //DataBase URL
    String username = "techpro";
    String password = "tchpr2020";
    //Connection, Statement, ResultSet objelerini olusturalim
    Connection connection;
    Statement statement;
    ResultSet resultSet;

    @Before
    public void setUp() throws SQLException {
        //getConnnection methodu ile databayse baglaniyoruz
        connection= DriverManager.getConnection(url,username,password);
        //createStaement methoduyla stament objesi olusturuyoruz. Bu objeyi kullanarak resultset objesi olusturacagiz
        statement=connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
    }

    @Test
    public void Test1() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM Book;");
        //ilk satiri atliyoruz
        resultSet.next();
        String deger1=resultSet.getString("BookName");
        System.out.println("DEGER1 :"+deger1);
        System.out.println("===============");

        //TC_02_BookName deki tum degerleri yazdir
        int rowSayisi=1;  //su an ilk satirda oldugumuzdan 1 den basliyoruz
        while (resultSet.next()){  //bir sonraki deger varsa git yoksa gitme
            Object deger2=resultSet.getObject("BookName");
            System.out.println(deger2.toString());
            rowSayisi++;
        }
        System.out.println("Row Sayisi: "+ rowSayisi);
        //TC_03_toplam  14 satirin olup olmadigini test et
        Assert.assertEquals(14,rowSayisi);


        //TC_04  5. degerin JAVA olup olmadigini test et
        //5.satira git
        resultSet.absolute(5);
        //o satirdaki degeri al
        String deger5=resultSet.getString("BookName");
        Assert.assertEquals("JAVA",deger5); //Fail cünkü Java yazilmis . Bug bulduk




    }
    @Test
    public void Test2() throws SQLException {
        resultSet = statement.executeQuery("SELECT * FROM Book;");
        //TC_05_son degerin UIPath olup olmadigini test et
        resultSet.last();  //dinamic code ile son satira gittik
        String deger6 = resultSet.getString("BookName");
        Assert.assertEquals("UIPath",deger6);


        //TC_06_ilk satirdaki degerin SQL olup olmadigii test et
       // resultSet.absolute(1);
        resultSet.first();

        String deger7=resultSet.getString("BookName");
        Assert.assertEquals("SQL",deger7);

    }
    @Test
    public void Test3() throws SQLException {
        //MetaData: Datayla ilgili bilgiler
        DatabaseMetaData dbMetaData = connection.getMetaData();
        System.out.println("UserName: "+dbMetaData.getUserName());
        System.out.println("Datbase Name: "+dbMetaData.getDatabaseProductName());
        System.out.println("Datbase Version : "+dbMetaData.getDatabaseProductVersion());
        


    }

}
