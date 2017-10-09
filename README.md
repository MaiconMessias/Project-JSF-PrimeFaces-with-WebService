**Project Jsf PrimeFaces with WebService REST JSON**
===================
------
### Introduction
-----
This project was based on another project that can be accessed through the link <b>[Simple Project Jsf PrimeFaces and Hibernate](https://github.com/MaiconMessias/Jsf-PrimeFaces-Hibernate-Simple-CRUD)</b>. 
The difference is that this project uses a JSON REST web service to get your requests. For this the library was used <b>[Gson](https://mvnrepository.com/artifact/com.google.code.gson/gson)</b>, for data exchange. 
This project also differs in the method of competition. In the project that served as base was used the method Pessimist Lock and in this was used the method Optimistic Lock.

### **Project ObjectsDataBaseLibraryWebService**
<p>This project was created to implement the Javabeans and classes that handle processes with the database.
  Data modeling was designed using <b>UML</b> and <b>DER</b>, being implemented in this project. This implementation was
placed in the form of a Java library, to be reused in other test projects. The database used was the postgreSQL.</p>

### **Project JSF-Primefaces-WebService-REST-JSON-JERSEY**
This project uses the frameworks <b>[JSF version 2.2](https://docs.oracle.com/cd/E13224_01/wlw/docs103/guide/webapplications/jsf/jsf-app-tutorial/Introduction.html)</b>, <b>[PrimeFaces version 5.0](https://www.primefaces.org/)</b> and <b>[Hibernate 4.2.21 (version of the file core) ](http://hibernate.org/)</b>. It contains the files ManagedBeans and pages .xhtml.
<p>The project is incomplete and presents only one research screen, however there is some complexity in the overall scope of the project. This project was developed only to place
in practice the tools cited at the learning level. The project will not work on older browsers such as IE 8.0.</p>
<p> The project also offers two languages: <b>English</b> and <b>Brazilian Portuguese</b>. To test this feature, change the language of your browser. On the Firefox 55.0.3, this option is in the path <kbd>Options -> Content</kbd>.</p>

### **Project WebService-REST-JSON-Jersey-JBOSS7**
In this project, was implemented the web service REST JSON.

------
### More information
-----

The script <kbd>script_create.sql</kbd> creates the necessary tables for the projects to work.