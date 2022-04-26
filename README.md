# Game-App

## Depenedncy injection
The app uses the most relevant dependency injection library - dagger hilt. Dagger Hilt is very easy to implement, but also very powerfull as it works in android intergration testing. 
The only downside is that you cannot launch a test fragment that is annotated with @AndroidEntryPoint, because you also have to mark host (activity) with the same annotation.
That problem is solved by creating another module (debug) and creating test activity that will be the host for the fragments (in this application HiltTestActivity). 
