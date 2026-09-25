EDUMART MARKETPLACE UPDATE
==========================

This update unifies Sell, Donate and Exchange listings on:
1. Home -> Latest Marketplace Listings
2. Browse Marketplace -> All Listings

Listing types:
- SELL
- DONATE
- EXCHANGE

Every unified listing shows:
- image
- listing type
- item name
- category
- condition
- description
- seller/donor/owner
- price for sale
- wanted item for exchange

DONATION REQUEST FLOW
=====================
1. Student creates a donation.
2. Donation appears in Browse/Home.
3. Another student clicks Request Donation.
4. Donor opens Donation Requests from the sidebar or Donate page.
5. Donor can Accept or Reject.
6. On Accept, the donation becomes Claimed and other pending
   requests for that donation are rejected.

RESPONSIVE DESKTOP WINDOWS
==========================
Main marketplace and major feature windows now open maximized
and have a minimum size instead of a fixed 1200x750/1150x750
window.

DATABASE
========
Run edumart_updates.sql in MySQL Workbench before compiling.

COMPILE
=======
javac -cp "lib\mysql-connector-j-26.7.0.jar" -d out src\*.java

RUN
===
java -cp "out;lib\mysql-connector-j-26.7.0.jar" StartEduMart

IMPORTANT
=========
Copy the updated .java files into your existing EduMart/src folder.
Do not delete your existing DAO files/classes that are not included
in this update.

New Java files:
- UnifiedListing.java
- UnifiedListingDAO.java
- UnifiedListingCardPanel.java
- DonationRequestDAO.java
- DonationRequestsFrame.java
