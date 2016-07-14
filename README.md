# com.catalogobject.test
## A Selenium webdriver Library Architecture Testing Framework for catalog.com 

- Common package
  - [CaptureScreenshot.java](https://github.com/vacuumzhang/com.catalogobject.test/blob/master/src/com/catalog/common/CaptureScreenshot.java): capture screen when test case false.
  - [Common.java](https://github.com/vacuumzhang/com.catalogobject.test/blob/master/src/com/catalog/common/Common.java): selenium action class with log4j and testng
  - [DataSetters.java](https://github.com/vacuumzhang/com.catalogobject.test/blob/master/src/com/catalog/common/DataSetters.java): properties file reader 
  - [ExcelReader.java](https://github.com/vacuumzhang/com.catalogobject.test/blob/master/src/com/catalog/common/ExcelReader.java): read data from an excel sheet. (Id, password)

- [Tests package](https://github.com/vacuumzhang/com.catalogobject.test/tree/master/src/com/catalog/tests): test case in junit or testng (new reportng include)

- [Page Factory package](https://github.com/vacuumzhang/com.catalogobject.test/tree/master/src/com/catalog/pagefactory): page class with web elements
