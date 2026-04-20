# Vehicle Rental System

This is a full-featured Android application for managing vehicle rentals. It handles everything from user registration and login to vehicle management and booking.

## Core Features

*   **Dual Roles**: Built-in support for both Owners (to manage fleet) and Customers (to rent vehicles).
*   **Fleet Management**: Owners can easily add new cars or bikes, view their current inventory, and process returns.
*   **Booking System**: Customers can browse the available fleet, select dates using a date picker, and see an automated cost calculation.
*   **Local Persistence**: Uses the **Room Database** to store all user and vehicle information locally—no internet connection required for core data features.
*   **Payment Simulation**: Integrated with the Stripe SDK to demonstrate a realistic payment flow.
*   **Material UI**: Clean and modern interface designed with Google's Material Design components.

## How to Run the Project

1.  **Open in Android Studio**: Use the "Open" option and point it to the project root folder.
2.  **Gradle Sync**: Let Android Studio finish the initial Gradle sync to download the latest dependencies.
3.  **Run**: Hit the green 'Run' button. The app is configured with `compileSdk 37`, so it works best on recent emulator versions (API 30+).

### Default Credentials
*   **Owner Account**: 
    *   Email: `owner@vehiclerental.com`
    *   Password: `owner123`
*   **Customer Account**: Simply click "Create Account" on the login screen to register your own test customer locally.

## Project Structure (Quick View)

*   `com.vehiclerental.app`: Contains all the Activities and UI logic.
*   `com.vehiclerental.database`: Room Entities and DAOs for local storage.
*   `com.vehiclerental.repository`: Handles data operations between the UI and database.
*   `com.vehiclerental.viewmodel`: Provides data to the UI components.

## Technical Details

*   **Language**: Java
*   **Database**: Room (SQLite)
*   **Image Loading**: Glide
*   **Payments**: Stripe SDK
*   **UI**: Material Components

## Reflection: My Journey (Jan 2026 - Apr 2026)

Working on this project over the past few months has been an incredible learning experience. Building a full-scale app from scratch helped me understand how different Android components work together.

### What I Learned
*   **Architecture**: I learned how to separate concerns using the **MVVM** pattern, making the code much easier to manage.
*   **Data Persistence**: Mastering **Room Database** was a big win—learning how to handle local data without needing a constant internet connection.
*   **Modern UI**: I got much more comfortable with **Material Design** and creating layouts that look good and feel intuitive.
*   **Third-Party Integration**: Integrating the **Stripe SDK** taught me how to handle external APIs and simulate real-world workflows like payments.

### Challenges I Faced
*   **State Management**: One of the toughest parts was keeping the UI in sync with the database, especially when vehicles were being booked or returned.
*   **Dependency Conflicts**: Updating libraries to their latest versions (like moving to SDK 37) caused some initial build errors that took time to troubleshoot and fix.
*   **Permissions**: Handling image permissions for the vehicle gallery (especially with newer Android versions) was tricky to get right.

Overall, completing this app and seeing it run smoothly has given me a lot of confidence in my Android development skills.

---
*Created as part of the Vehicle Rental System Project - Started January 2026, Completed April 2026*
