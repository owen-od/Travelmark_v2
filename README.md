
# Mobile App Development Assignment 1

Name: Owen O'Donnell

## Overview.

An android application created in Android studio and written in Kotlin. The application was developed for assignment 1 of the mobile application development module in the HDip in Computer Science in SETU. The application allows users to add “travelmarks” in three pre-defined categories: things to do; sights to see; and foods to eat. Users must select a title, location and category for each travelmark and may additionally add a description, image, location and rating.

The application contains the below additional features:

+ Full CRUD functionality
+ User Log in and account creation
+ Persistent storage of users and travelmarks
+ Splash screen on application launch
+ View all travelmarks on map activity
+ Search for travelmarks by location
+ Filter and search travelmarks by selected chip/category
+ Use of UI elements such as radio buttons, rating bar and chipset.

## Setup requirements.

+ Clone this repository with the following command: `git clone https://github.com/owen-od/Travelmark`

+ Open project in Android studio and run (note this will require and emulator running API 29)

## App Design.

### Splash Screen

A custom splash screen containing the app branding is displayed for 2 seconds when the application is launched.

>Splash Screen

![Splash screen](/app/src/images/splash.PNG)

### Authentication/sign up

On application launch, users may log in to their account or alternatively create an account. For illustration purposes, certain constraints are used and appropriate error messages are displayed back to the user.

>Login Screen

![Login](/app/src/images/login.PNG)

>Signup Screen

![Signup](/app/src/images/register.PNG)

### List travelmarks

All travelmarks are displayed back to the user in a recycler view on the app home page. Each travelmark is clickable so the user can read and edit its details

>List all travelmarks

![List Travelmarks](/app/src/images/travelmark_list.PNG)

### Add travelmarks

Users can add a new travelmark by clicking the + icon on the homepage and navigating to the add travelemark page. The location, title and category fields are mandatory. Users may optionally add a description, rating, location and image.

>Add new travelmark

![Add Travelmarks](/app/src/images/travelmark_add.PNG)

### Search and filter travelmarks

Users can search travelmarks by location on the main page. Users may also filter travelmarks by category.

>Search travelmarks by location

![Search Travelmarks](/app/src/images/travelmark_search.PNG)

>Filter travelmarks by category

![Filter Travelmarks](/app/src/images/travelmark_filter.PNG)

### View on map

Users can view all travelmarks on a map. Individual travelmarks can be selected by clicking on the marker and information on them will be displayed

>View all travelmarks on map

![Travelmarks Map](/app/src/images/travelmarks_map.PNG)

## References

In addition to the official documentation and lecture materials, the below resources were consulted for certain features implemented, although no solution was taken directly from the material:

+ [Chips and chip groups](https://www.digitalocean.com/community/tutorials/android-p-chips-chipgroup)
+ [Search view with recycler activity](https://www.geeksforgeeks.org/android-searchview-with-recyclerview-using-kotlin/)
+ [Splash screen](https://www.youtube.com/watch?v=Q0gRqbtFLcw&ab_channel=Stevdza-San)

## Project demo

A video demo of the project is available on Youtube at the following url [TODO]
