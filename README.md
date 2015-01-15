# Database-Generic-DAO

 - The purpose of this framework is to ease the coding DAO layer objects. Includes all of the basic CRUD methods as well as various "find" or "search" methods.
 - Uses [Google Generic Search](https://code.google.com/p/hibernate-generic-dao/wiki/Search) that offers a powerful and flexible search functionality.
 - Implemented using Spring and Hibernate

Usage overview
===========
The following example shows how to perform CRUD and Search operations in a Dummy Database table.

Database Table DDL
===========
```sql
CREATE TABLE GenericDaoTable (
  ID       int(10) NOT NULL AUTO_INCREMENT, 
  aString  varchar(100) NOT NULL UNIQUE, 
  aDate    date NOT NULL,
  aInt 	   int(10) NOT NULL,
  PRIMARY KEY (ID));
```

Hibernate Mapping
===========
```xml
<hibernate-mapping>
    <class name="com.github.vsspt.common.db.impl.hibernate.tests.GenericDaoObj" table="GenericDaoTable">
        <id name="ID" type="java.lang.Long">
            <column name="ID" />
            <generator class="identity" />
        </id>
        <property name="aString" type="string">
            <column name="aString" length="100" not-null="true" unique="true" />
        </property>
        <property name="aDate" type="java.util.Date">
            <column name="aDate" not-null="true"/>
        </property>
        <property name="aInt" type="int">
            <column name="aInt" not-null="true"/>
        </property>        
                
    </class>
</hibernate-mapping>
```

Java Model
===========
```java
public class GenericDaoObj{

    private Long ID;
    private String aString;
    private Date aDate;
    private int aInt;
    
    ...
    
```

CRUD Operations
===========
```java
  IGenericDAO<GenericDaoObj, Long> dao = new GenericDAO<>(GenericDaoObj.class);
  
  public void testCreate(){
    GenericDaoObj newObject = getNewObject();
    dao.save(newObject);
  }
  
  public void testUpdate(){
    GenericDaoObj toUpdateObject = getUpdateObject();
    dao.update(toUpdateObject);
  }  
  
  public void testDelete(){
    Long key = getKey();
    dao.delete(key);
  }   
  
```

Search features
===========
The GET operation uses the features provided by [Google Generic Search](https://code.google.com/p/hibernate-generic-dao/wiki/Search) to offer a powerful and flexible search functionalities.

```java
public void searchOr(Long id1, Long id2){

    Search search = new Search();
    search.addFilterOr(Filter.equal("ID", id), Filter.equal("ID", id2));
	
    GenericSearch orSearch = new GenericSearch(search);

    PagingList<GenericDaoObj> page = dao.get(orSearch);

    int total = page.getTotalRecords();
    int nbrRecords = page.getList().size();

    LOG.debug("testOr - Found [{}] of [{}] records with ID = [{} OR {}], values [{}].", new Object[] {nbrRecords, total, id, id2, page.getList()});
}

public void testLike() {
    String like_value = "LIKE_VALUE";
    String property = "aString";
    Search search = new Search();

    search.addFilterLike(property, like_value);
	
    GenericSearch likeSearch = new GenericSearch(search);

    PagingList<GenericDaoObj> page = dao.get(likeSearch);

    int total = page.getTotalRecords();
    int nbrRecords = page.getList().size();

    LOG.debug("testLike - Found [{}] of [{}] records [{} LIKE '{}%'], values [{}].", new Object[] {nbrRecords, total, property, like_value, page.getList()});
  }
  
 public void testDisjunction(Long id_1, Long id_2, Long id_3) {
	
    Search search = new Search();
    search.setDisjunction(Boolean.TRUE);

    search.addFilter(Filter.equal("ID", id_1));
    search.addFilter(Filter.equal("ID", id_2));
    search.addFilter(Filter.equal("ID", id_3));
	
    GenericSearch disjunctionSearch = new GenericSearch(search);

    PagingList<GenericDaoObj> page = dao.get(disjunctionSearch);

    int total = page.getTotalRecords();
    int nbrRecords = page.getList().size();

    LOG.debug("testDisjunction - Found [{}] of [{}] records with with ID = [{} OR {} OR {}], values [{}].", new Object[] {nbrRecords, total, id_1, id_2, id_3, page.getList()});
  }
  
public void testBetween(Date beginDate, Date endDate) {

    Search search = new Search();
    search.addFilterAnd(Filter.greaterOrEqual("aDate", beginDate), Filter.lessThan("aDate", endDate));

    GenericSearch betweenSearch = new GenericSearch(search);
	
    PagingList<GenericDaoObj> page = dao.get(betweenSearch);

    int total = page.getTotalRecords();
    int nbrRecords = page.getList().size();

    LOG.debug("testBetween - Found [{}] of [{}] records with dates [{} <= DATE < {}], values [{}].", new Object[] {nbrRecords, total, beginDate, endDate, page.getList()});
  }  
  

```

Maven Dependencies - API
===========
```xml
<dependency>
  <groupId>com.github.vsspt</groupId>
  <artifactId>db-generic-dao-api</artifactId>
  <version>1.0.0</version>
</dependency>
```

Maven Dependencies - Spring Hibernate Implementation
===========
```xml
<dependency>
  <groupId>com.github.vsspt</groupId>
  <artifactId>db-generic-dao-spring-hibernate</artifactId>
  <version>1.0.0</version>
</dependency>
```