# TechCare Services Mobile App

**TechCare Services** is an Android mobile application developed using **Java** in **Android Studio**.  
The app allows customers to submit repair requests and book services, while technicians can manage and update these requests. It was developed as part of an academic mobile development project.


## Table of Contents
- [Features](#features)
- [User Roles](#user-roles)
- [Customer Guide](#customer-guide)
- [Technician Guide](#technician-guide)
- [Technologies](#technologies)
- [Database](#database)
- [Notes](#notes)


## Features
- Customer account registration and login with input validation
- Browse and search repair services
- Submit repair requests with issue description and optional photo
- Booking confirmation with service method, date, time, and pickup address
- Real-time notifications for status updates
- FAQ and Maintenance Tips sections
- Contact Us page with phone, email, and office information


## User Roles

1. **Customer**
   - Can browse services, submit repair requests, receive notifications, and view bookings.
2. **Technician**
   - Pre-configured accounts in the database
   - Can view incoming repair requests, accept/reject them, update statuses, and maintain repair history.


## Customer Guide
- **Account Registration & Login**  
  - New users can sign up with valid details  
  - Existing users can log in with email and password  
  - Input validation ensures fields are complete, emails are valid, and passwords are strong

- **Browsing & Searching Services**  
  - Services are displayed as cards on the service page  
  - Search highlights matching services for easy visibility

- **Submitting a Repair Request**  
  1. Scroll or search for a service  
  2. Select a service → redirected to Repair Request page  
  3. Enter issue description and optionally attach a photo  
  4. Submit request → redirected to Booking page

- **Booking Confirmation**  
  - Select service method: Drop-off or Pickup  
  - Provide pickup address (if applicable) and preferred date/time  
  - Request is submitted to technicians

- **Notifications & Status Updates**  
  - Real-time notifications on repair status  
  - Unread notifications have badge count and color change when read

- **Additional Features**  
  - FAQ section, Maintenance Tips, and Contact Us page


## Technician Guide
- Login with pre-configured credentials
- View incoming repair requests
- Accept or reject requests  
- Update status for accepted repairs  
- Completed repairs move to Repair History  
- Status updates automatically notify customers


## Technologies
- **Programming Language:** Java  
- **IDE:** Android Studio  
- **Database:** SQLite (local database storage)  


## Database
- SQLite used for storing user accounts, repair requests, and notifications
- Technician accounts are hardcoded in the database


## Notes
- Developed as part of a campus mobile development assessment  
- Designed for **learning purposes** and demonstrating Android app development concepts  
- Focuses on user-friendly interface, interactivity, and notification system


