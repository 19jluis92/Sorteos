# Sorteos

1. Clone the projet
2. mvn clean install

Note: you must have maven 

# Data base
1. install [MySql server](https://dev.mysql.com/downloads/installer/)
2. create the database "sorteos"

![image](https://user-images.githubusercontent.com/8491553/231277829-84cfa4fa-5265-4888-b2b5-49146e77c1ea.png)

![image](https://user-images.githubusercontent.com/8491553/231277897-fadbed35-02e7-4cb1-a51a-79f73b6f9f26.png)

3. import the sql dump file /src/main/resources/META-INF/Sorteos10-04-2023.sql 

![image](https://user-images.githubusercontent.com/8491553/231278076-bc18292d-515b-4e27-8abc-2450a71f8bcc.png)

4. restore


# Problems

## Database connection 
src/main/resources/META-INF/persistence.xml
```
      <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/sorteos?zeroDateTimeBehavior=CONVERT_TO_NULL"/> <--- update port or configuration
      <property name="javax.persistence.jdbc.user" value="root"/> <--- update for your user name
      <property name="javax.persistence.jdbc.driver" value="com.mysql.cj.jdbc.Driver"/>
      <property name="javax.persistence.jdbc.password" value="root"/> <--- update for your pass
```

### DatePicker issue
only if you need update the UI you have to use netbeans and add the plugin to check the datepicker
check your .m2/repository folder and look for the artiact LGoodDatePicker

1. [in your netbeans ](https://docs.oracle.com/javase/tutorial/javabeans/quick/addbean.html)

### SQL error 

```
ERROR: Table 'sorteos.numeros' doesn't exist
Exception in thread "AWT-EventQueue-0" javax.persistence.PersistenceException: org.hibernate.exception.SQLGrammarException: could not extract ResultSet
```

### work around 
1."good luck"

## Instructions

Note
Melate has increased the numbers to select in its draws four times:
1. Number 555  date: 31/03/1993
2. Number 1547 date: 02/10/2002
3. Number 1877 date: 30/11/2005
4. Number 2088 date: 09/12/2007

So you can check the coincidences filter by specific dates to avoid merge different samples

![image](https://user-images.githubusercontent.com/8491553/231287552-8607e257-806f-420f-bd4f-fb0756077f75.png)

checking only with the last changes

and validate if some one win more than one time like the "estrella de la suerte"


![image](https://user-images.githubusercontent.com/8491553/231287915-a621b35f-0257-4926-8118-6c92b66aa223.png)

## The "Buscador Numeros" section
works to validate if the number win a prize in the past

![image](https://user-images.githubusercontent.com/8491553/231332362-b9fd4602-1b02-4c96-91fe-adabdb6b238e.png)
![image](https://user-images.githubusercontent.com/8491553/231332228-ad904a8f-0ca9-4702-b69d-4e85c172b9d9.png)

and you can validate the number of incidencies for a sequence number

![image](https://user-images.githubusercontent.com/8491553/231330751-fda002f7-ab06-480c-b1bf-ef4d0f8677d3.png)


## The "Registro Individual"
you can add manually the specific winner

![image](https://user-images.githubusercontent.com/8491553/231331663-64f69792-b1eb-4cd1-a5e8-95147e902c9e.png)

or download the file csv from the governance page and loaded(The system does not validate duplicate information)

