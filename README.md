# Game-App

## Task
Develop an app that displays the list of games. The user should first select a genre. On the game list screen, the user should be able to change the genre and click on each game to see more details.

## Tools used
Material design, Coroutines, ViewModel, Hilt, Retrofit2, OkHTTPMockWebServer, ViewPager2, Navigation Component, Datastore, Glide, Paging3, Truth, Turbine, JUnit, Mockito, Espresso, SafeArgs, Firebase

## Architecture
The app is developed by following MVVM (Model-View-ViewModel) architecture.

![image](https://user-images.githubusercontent.com/71450900/166103018-beef3ef1-2c23-4aaf-bfd7-d03d14784c86.png)

![architecture](https://user-images.githubusercontent.com/71450900/166103402-e3b6b618-b281-4ff2-beb4-22eb7ba2f782.png)


The app is divided into 2 main parts - Core and Feature games. The core package is the package that contains all the global logic that can be shared between multiple features. Feature_games package consists of 3 main parts - data, domain and presentation. 

The data part contains code that has the task of communicating with remote and local databases. Also, it should contain all logic (like dtos) that is used only to transfer data objects. 

The domain part is the most important logic part. Its main task is to make bussines logic reusable. The package mostly contains use cases that are injected into viewModels, resulting in a more decoupled application. For example, the app needs to retrieve games from the remote database in 2 different fragments (and viewModels). By using useCases, we can create GetGamesUseCase that can be injected into both viewModels. This way we also do not repeat ourselves.

The presentation part is the layer of the application that contains all Android specific components (fragment, viewModel, services...).


## Depenedncy injection
The app uses the most relevant dependency injection library- Dagger Hilt. Dagger Hilt is very easy to implement, but also very powerful as it works in Android integration tests. 
One drawback is that you cannot launch a test fragment annotated with @AndroidEntryPoint, as you also need to mark the host (activity) with the same annotation.
This problem is solved by creating another module (Debug) and a test activity that serves as a host for the fragments (in this application HiltTestActivity).

## Data Source
Data is fetched from RAWG api https://api.rawg.io/docs/.

The app uses the retrofit2 library to communicate with the remote rawg api. Endpoints used: /genres, /games. 

Retrofit2 requires the creation of a service interface that defines functions and their responses. The service is then injected into the repositories that transfer, map, and return objects to the use cases. The games layer has one more abstraction - the PagingSource, which paginates games from the API. 
During development, I ran into a problem with injecting dependencies into the PagingSource class. The only way to pass the dependencies is through constructor injection, because field injection with Hilt (in non-Android classes) requires Context, which should not be present in non-Android classes. Also, constructor injection does not work because the of query parameters which are passed dynamically. The solution was to create the repository where the required dependencies are injected and create a method that accepts query parameters and then passes all parameters to the PagingSource constructor.

## UI
Application creates it's UI using standard xml designing.
Design inspired from: https://dribbble.com/shots/14930319-Game-Store-App

## Tests
App also contains Unit and Intergration tests. Note: Intergration tests should run from the device that has disabled animations.

## Principles Followed
I have tried to follow as many design principles as possible. Most notably, I focused the most on dependency inversion, which allowed me to swap out repository dependencies during testing.
I also tried to avoid repeating myself, naming variables and functions so that they do what they are expected to do, giving each class a single responsibility, etc.


![game-app-mockup](https://user-images.githubusercontent.com/71450900/166105446-e0bc3467-2053-4f33-955d-3ddeb61c3a32.jpg)


