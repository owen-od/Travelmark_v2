# Mobile App Development Assignment 2

Name: Owen O'Donnell

## Overview

An android application created in Android studio and written in Kotlin. The application was developed for assignment 2 of the mobile application development module in the HDip in Computer Science in SETU. The application allows users to add “travelmarks” in three pre-defined categories: things to do; sights to see; and foods to eat. Users must select a title, location and category for each travelmark and may additionally add a description, image, location and rating.
Users can also select favourites and then filter and/or search within these categories and their favourite travelmarks.

The purpose of the application is to allow users to record their travels to quickly find and share recommendations with others, or research upcoming trips and save travelmarks that they would like to visit on upcoming trips. 

The Model-view-presenter (MVP) pattern is used throughout and the application includes the below features:

+ Full CRUD functionality
+ Splash screen on application launch
+ User log in and account creation
+ Persistent storage of users, travelmarks and images with Firebase realtime DB and storage
+ View all travelmarks on map activity with zoom to travelmark and info on travelmark displayed when marker clicked
+ Change Google map type on map of all travelmarks by selecting option in spinner (normal, satellite and terrain currently available)
+ Search for travelmarks by location (including in categories and favourites) with SearchView
+ Filter travelmarks by selected category or favourites by selecting option in ChipGroup
+ Swipe to delete travelmark functionality and click to edit travelmark
+ Use of UI elements such as radio buttons, switch, rating bar, chipset, spinner, text input layouts
+ Navigation with bottom navigation and back/up button
+ Night mode feature and user preference saved with Shared Preferences
+ Use of last known location as default when adding new travelmark location on map

## Setup requirements

+ Clone this repository with the following command: `git clone https://github.com/owen-od/Travelmark_v2`

+ Open project in Android studio and run (note this will require and emulator running API 29)

## App Design/Screenshots

### Splash Screen and Authentication

A custom splash screen containing the app branding is displayed for 3 seconds when the application is launched.
Users may then log in or create an account.

>Splash Screen and login register (in day mode)

![Splash screen](/app/src/images/splash_day.PNG)

![Login](/app/src/images/login_day.PNG)

![Register](/app/src/images/register_day.PNG)

If night mode is activated these will be shown in night mode as the user preference from last login is saved. Example below:

![Login](/app/src/images/login_night.PNG)

### List travelmarks

All travelmarks are displayed back to the user in a recycler view on the app home page. Each travelmark is clickable so the user can read and edit its details. Travelmarks can deleted by swiping left or from within edit mode.

>List all travelmarks

![List Travelmarks](/app/src/images/travelmark_list_day.PNG)

Night mode may be selected by moving the switch toggle at the top right (and this will be activated for the rest of the screenshots)

![List Travelmarks](/app/src/images/travelmark_list.PNG)

### Add or edit travelmarks

Users can add a new travelmark by clicking the + icon on the bottom navigation. The location, title and category fields are mandatory. Users may optionally add a description, rating, location and image and add to favourites. Text input layouts are used to show character count for edit text fields. 

>Add new travelmark

![Add Travelmark](/app/src/images/travelmark_add.PNG)

Users may edit existing travelmarks by clicking on them. This opens an edit view where all selected details are pre-populated (i.e. location, title, description, image, map location, category, rating, favourite).

>Edit travelmark

![Edit Travelmark](/app/src/images/travelmark_edit.PNG)

### Search and filter travelmarks

Users can search travelmarks by location on the main page. Users may also filter travelmarks by category or favourites and search within these categories.

>Filter travelmarks by category or favourites

![Filter Travelmarks](/app/src/images/travelmark_favourites.PNG)

>Search travelmarks by location

![Search Travelmarks](/app/src/images/travelmark_search.PNG)

### View on map

Users can view all travelmarks on a map. Individual travelmarks can be selected by clicking on the marker. They will then be zoomed to and information on them will be displayed.

>View all travelmarks on map (with normal map selected)

![Travelmarks Map](/app/src/images/travelmark_map_all.PNG)

>View single travelmark on map (with satellite map selected)

![Satellite Map](/app/src/images/travelmark_map_satellite.PNG)

## Persistence/Storage

Firebase is used for authentication and storage of users, travelmarks and images

>Firebase authentication

![Firebase authentication](/app/src/images/firebase_auth.PNG)

>Firebase realtime database

![Firebase database](/app/src/images/firebase_db.PNG)

>Firebase storage

![Firebase Storage](/app/src/images/firebase_storage.PNG)

## References

In addition to the official documentation and lecture materials, the below resources were consulted for certain features implemented, although no solution was taken directly from the material:

+ [Chips and chip groups](https://www.digitalocean.com/community/tutorials/android-p-chips-chipgroup)
+ [Search view with recycler activity](https://www.geeksforgeeks.org/android-searchview-with-recyclerview-using-kotlin/)
+ [Splash screen](https://www.youtube.com/watch?v=Q0gRqbtFLcw&ab_channel=Stevdza-San)
+ [Text Input Layout](https://www.geeksforgeeks.org/how-to-use-material-text-input-layout-in-android/)
+ [Bottom Navigation](https://www.geeksforgeeks.org/bottom-navigation-bar-in-android-using-kotlin/)
+ [Delete from Firebase Storage with URL](https://stackoverflow.com/questions/45103085/deleting-file-from-firebase-storage-using-url)
+ [Saving switch button state with Shared Preferences](https://www.geeksforgeeks.org/how-to-save-switch-button-state-in-android/)

## Project demo

A video demo of the project is available on Youtube at the following url [ToDo]
