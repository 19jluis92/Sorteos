# Sorteos

1. Clone the projet
2. mvn clean install


# Data base
1. install MySql server
2. create the database "sorteos"![image](https://user-images.githubusercontent.com/8491553/231277829-84cfa4fa-5265-4888-b2b5-49146e77c1ea.png)
![image](https://user-images.githubusercontent.com/8491553/231277897-fadbed35-02e7-4cb1-a51a-79f73b6f9f26.png)
3. import the sql dump file /src/main/resources/META-INF/Sorteos10-04-2023.sql 
![image](https://user-images.githubusercontent.com/8491553/231278076-bc18292d-515b-4e27-8abc-2450a71f8bcc.png)
4. restore


# Problems

Database connection 
src/main/resources/META-INF/persistence.xml
...
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/sorteos?zeroDateTimeBehavior=CONVERT_TO_NULL"/> <--- update port or configuration
      <property name="javax.persistence.jdbc.user" value="root"/> <--- update for your user name
      <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value="root"/> <--- update for your pass
...

DatePicker issue
only if you need update the UI you have to use netbeans and add the plugin to check the datepicker

SQL error 
...
ERROR: Table 'sorteos.numeros' doesn't exist
Exception in thread "AWT-EventQueue-0" javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet
...
work around 
1. "good luck"
