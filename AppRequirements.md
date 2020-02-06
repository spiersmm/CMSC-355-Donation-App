**Log-On:**
- _**Required**_
  - Donor and Recipient accounts must be separate
    - App should look different for donors and recipients
  - User must be at least 18 years old
  - Password requirements are up to us to decide
  - Details Needed: Name, DOB, email, etc.
  - Account required for all app features
  - Have a way to reset password via email if forgotten
  - Organization Profile Requirements: Registration number, location, must have a .org email domain, etc.
  - SQLite database is good way to store Login ID and password
    - Preferably with a simple encryption
  - Account verification
    - Recipient through organizational email address
    - Donor through email and text
- _**Optional**_
  - Third Party Log-in (ex. Facebook) nice to have but not mandatory
  
&nbsp;
&nbsp;

**Donors List Items They are Willing to Donate and can add optional information about items:**
- _**Required**_
  - Donors should be able to easily see recipient details like location, interested categories, etc.
  - No limit to what items can be donated
    - No health sensitive items like vaccines, etc..
  - No minimum requirement on information for posting donation, but can include: 
    - Photo of item is optional (other donors & recipients can see pictures)
    - Items name, age, quantity, condition, description box for additional information
  - Donor chooses who they donate to
  - When donors post an item they should indicate whether item will need to be picked up or if they will deliver to recipient
  - Donors can reach out to recipients through in app messaging 
  - Donor should receive a receipt from transaction
  - Items should be automatically removed after 3 months
  - Should have item categories
    - We choose categories
  - Items should be sorted by time posted
  - Donors can remove postings
  - Donors can search for certain recipients
  - Donors can favorite recipients to see what they may need in the future
- _**Optional**_
  - Items can have tags 
    - Perishable, delicate, etc…
  - Donors could have public ratings
  - Donor posts could have comment function
    
&nbsp;
&nbsp;

**Recipients View Listed Items and Notify Donor if Interested:**
- _**Required**_
  - Recipients can only be organizations
  - Recipients should be able to navigate through donors and see what items are available for donation
  - Can mark items they are interested in 
  - Can filter items by location, item, etc..
    - Filter should put the best fitting entries at top of page
  - Can get notifications of favorite items
  - Can list items they are in need of
  - Displayed on screen: photo, name, description, category, quantity
  - Recipients can withdraw request
  - Recipients can search for certain donors
- _**Optional**_
  - Can have public ratings
    
&nbsp;
&nbsp;

**Historic Record for All Transactions for Each Account is Viewable within Account:**
- _**Required**_
  - Should include recipient/donor, date, object
  
&nbsp;
&nbsp;

**Communication Between Donors and Recipients:**
- _**Required**_
  - When recipient is interested in certain item listed by donor, they can mark it as an interested item and it will pop up notification to donor 
  - Donors then reach out to recipients via in-app messaging
  - No financial transactions will ever take place in app
- _**Optional**_
  - Communication channel 
  - Private messaging
  
&nbsp;
&nbsp;

**Other:**
- _**Required**_
  - App should be compatible with Android 7.0 and beyond
  - Should have google maps filter to show nearby organizations
- _**Optional**_
  - Can have a separate report option for abuse/fraudulent activity
  - Map functionality
