# Admin1
#Rest Springboot Example with thymeleaf
Need to install lombok to run on Eclipse or add getters and setter  follow directions on web page
https://projectlombok.org/download.html
The application uses the H2 database and is prefilled with some event 
SpringBoot is the Framework with SpringData Jpa for the persistent layer and Thymeleaf for the view laver
The application can be run in eclipse or with maven
The gradle build is not up to date

Swagger allows you to see all the Rest URL's
http://localhost:8080/swagger-ui.html

#Purchase url will add to database
http://localhost:8080/api/v1/order/purchase?&userName=JoeO&qty=5&showId=123
Status Code will be C
#Cancel all will cancel whole tranaction
http://localhost:8080/api/v1/order/cancelAll?transactionId=7&userName=JoeO&qty=5&showId=123
Cancel will cancel the whole tranaction and Mark the status X

#Cancel 
http://localhost:8080/api/v1/order/cancel?transactionId=7&userName=JoeO&qty=5&showId=123
will modify the tranaction and mark the status M
#Exchange 
test total with different quantities This will modify the original order and mark the status code M and create a new
order with the transfered tickets and make the status code C
 http://localhost:8080/api/v1/order/exchange?transactionId=3&userName=JoeO&qty=4&showIdFrom=456&showIdTo=789
	 * test duplicate shows this will return an error message and modify nothing
	 http://localhost:8080/api/v1/order/exchange?transactionId=3&userName=JoeO&qty=4&showIdFrom=456&showIdTo=456




Get to the login page
http://localhost:8080/

userName and Password =admin everything else fails

This will bring you to a search Screen which you can enter and ShowFromId and ShowToId
Lot more styling to do



