# Automated Transaction Data-Entry

This is a simple Java project built using the [Selenium]() testing framework. It starts off by downloading a transaction `csv` file from the investing brokerage platform [Sharesies]() and then enters that collected data into your own [Yahoo Portfolio](https://finance.yahoo.com/portfolios/). The project is built using the Page Object Model desgin pattern, for greater maintaince, extensions and testing.

<img src="docs/demo.gif">

## How to Run

1. Create Accounts for both [Yahoo Finance]() and [Sharesies]() and have transaction data present in Sheresies i.e. you have participated in action of buying and/or selling stocks or funds.
2. In your Yahoo Accout *create a Portfolio* with the name of your choosing (or `Current` which is the name of the Portfolio the program will enter data into). 
3. Add the stocks you currently hold or want data to be entered in for into the Portfolio.
4. Clone the project repo into your local machine. 
5. Download a version of [Chromedriver]() that is compatiable with your current [Chrome]() browser version. After installing, save it inside the project root folder.
6. Copy the Sample credentials file in the same location as the project and update it with your account credentials.
7. Install dependencies by building the project with Maven and finally compile and run.

---

## About

---