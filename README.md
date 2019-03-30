# revolut_test

Backend Test

Design and implement a RESTful API (including data model and the backing implementation)
for money transfers between accounts.
 

### How to start application

Works with jdk 1.8

Command to start:
```
mvn clean package
java -jar target/test_task-1.0-SNAPSHOT.jar
```

### How it's work

Rest API: http://localhost:8080/api/transfer

Send request with ```Content-Type: application/json```

example of JSON:
```json
{
  "paymentPurpose": "need",
  "accountNumberFrom": 321,
  "recipientAccount": 123,
  "transferAmount": 1000
}
```

#### Parameters to request

```
     * Purpose of payment
    String paymentPurpose;

     * Sender's account
    long accountNumberFrom;

     * Recipient account
    long recipientAccount;

     * Transfer amount
    BigDecimal transferAmount;
```

#### Response example
Example of success response
```json
{
  "accountAmountFrom":2500,
  "accountAmountTo":2200,
  "accountNumberFrom":321,
  "paymentPurpose":"need",
  "recipientAccount":123,
  "transferAmount":1000,
}
```
Example of failed response
```json
{
  "accountNumberFrom":321,
  "recipientAccount":123,
  "rejectionReason":"Error when transferring money amount insufficient to transfer.You try to transfer money sum: 1000 on you account amount: 500",
  "transferAmount":1000,
}
```

#### What account we have in DB to transfer money
1. Account number: 123,
Amount of account: 1200

2. Account number: 321,
Amount of account: 3500

3. Account number: 213,
Amount of account: 23000

