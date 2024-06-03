# Boilerplate-CRUD-Spring-MVC-6
Boilerplate CRUD Web App created with Spring MVC 6 by [StackPuz](https://stackpuz.com).

## Demo
Checkout the live demo at https://demo.stackpuz.com

## Features
- Fully Responsive Layout.
- Sorting, Filtering and Pagination on Data List.
- User Management, User Authentication and Authorization, User Profile, Reset Password.
- Input Mask and Date Picker for date and time input field with Form Validation and CSRF Protection.

![Responsive Layout](https://stackpuz.com/img/feature/responsive.gif)  
![Data List](https://stackpuz.com/img/feature/list.gif)  
![User Module](https://stackpuz.com/img/feature/user.png)  
![Input Mask and Date Picker](https://stackpuz.com/img/feature/date.gif)

## Minimum requirements
- Maven 3.5.0
- JAVA 17
- MySQL 5.7

## Installation
1. Clone this repository. `git clone https://github.com/stackpuz/Boilerplate-CRUD-Spring-MVC-6.git .`
2. Create a new database and run [/database.sql](/database.sql) script to create tables and import data.
3. Edit the database configuration in [/src/main/webapp/WEB-INF/app-servlet.xml](/src/main/webapp/WEB-INF/app-servlet.xml) file.
    ```
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource">
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver" />
        <property name="url" value="jdbc:mysql://127.0.0.1:3306/testdb?characterEncoding=UTF-8&amp;useSSL=false" />
        <property name="username" value="root" />
        <property name="password" value="" />
    </bean>
    ```

## Run project

1. Run Spring project. `mvn package cargo:run`
2. Navigate to `http://localhost:8080/app`
3. Login with user `admin` password `1234`

## Customization
To customize this project to use other Database Engines, CSS Frameworks, Icons, Input Mask, Date picker, Date format, Font and more. Please visit [stackpuz.com](https://stackpuz.com).

## License

[MIT](https://opensource.org/licenses/MIT)