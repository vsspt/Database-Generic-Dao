# Database-Generic-Dao
Database Generic AO
===========

 - The purpose of this framework is ease the coding DAO layer objects. Includes all of the basic CRUD methods as well as various "find" or "search" methods.
 - Uses [Google Generic Search](https://code.google.com/p/hibernate-generic-dao/wiki/Search) that offers a powerful and flexible search functionality.
 - Implemented using Spring and Hibernate

Usage overview
===========
Hibernate Mapping
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
```java
public class GenericDaoObj{

    private Long ID;
    private String aString;
    private Date aDate;
    private int aInt;
    
    ...
    
```

CRUD Operations
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
