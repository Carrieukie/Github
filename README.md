# Github

Note!! Work still in progress.

### Tech-stack

* Tech-stack
    * [Kotlin](https://kotlinlang.org/) - a cross-platform, statically typed, general-purpose programming language with type inference.
    * [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - perform background operations.
    * [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html) - handle the stream of data asynchronously that executes sequentially.
    * [Dagger hilt](https://dagger.dev/hilt/) - a pragmatic lightweight dependency injection framework.
    * [Jetpack](https://developer.android.com/jetpack)
        * [Room](https://developer.android.com/topic/libraries/architecture/room) - a persistence library provides an abstraction layer over SQLite.
        * [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - is an observable data holder.
        * [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - perform action when lifecycle state changes.
        * [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - store and manage UI-related data in a lifecycle conscious way.
a larger dataset from local storage or over network. This approach allows your app to use both network bandwidth and system resources more efficiently. .
        * [Navigation components](https://developer.android.com/guide/navigation/navigation-getting-started) - Navigation refers to the interactions that allow users 
to navigate across, into, and back out from the different pieces of content within your app.

    * [Glide](https://bumptech.github.io/glide/) - Glide is a fast and efficient image loading library for Android focused on smooth scrolling. Glide offers an easy 
to use API, a performant and extensible resource decoding pipeline and automatic resource pooling.
    * [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java

    

* Architecture
    * MVVM - Model View View Model

## App Architecture
   This app integrates directly into the recommended [Android app architecture](https://developer.android.com/jetpack/guide). It majorly uses the paging library to 
load and display pages of data from a larger dataset from local storage or over network. This approach allows your app to use both network bandwidth and system 
resources more efficiently.The paging lib operates in three layers :
   
   - The repository layer
   - The ViewModel layer
   - The UI layer

      
      ### Repository layer

      The primary Paging library component in the repository layer is RemoteMediator. A RemoteMediator object handles paging from a layered data source, such as a 
network data source with a local database cache.It Defines a set of callbacks used to incrementally load data from a remote source into a local source wrapped by a 
PagingSource, e.g., loading data from network into a local db cache.
      
      ### ViewModel layer
      The Pager component provides a public API for constructing instances of PagingData that are exposed in reactive streams, based on a PagingSource object and a 
PagingConfig configuration object. The component that connects the ViewModel layer to the UI is PagingData. A PagingData object is a container for a snapshot of 
paginated data. It queries a PagingSource object and stores the result.
      
      ### UI layer
      
      The primary Paging library component in the UI layer is PagingDataAdapter, a RecyclerView adapter that handles paginated data.This class is a convenience 
wrapper around AsyncPagingDataDiffer that implements common default behavior for item counting, and listening to update events.To present a Pager, use collectLatest 
to observe Pager.flow and call submitData whenever a new PagingData is emitted.PagingDataAdapter listens to internal PagingData loading events as pages are loaded, 
and uses DiffUtil on a background thread to compute fine grained updates as updated content in the form of new PagingData objects are received.

      ### Domain
      
      The ```domain``` module contains domain model classes which represent the data we will be handling across presentation and data layer.Use cases are also provided in the domain layer and orchestrate the flow of data from the data layer onto the presentation layer and a split into modular pieces serving one particular purpose.
      The UseCases use a ```BaseUseCase``` interface that defines the parameters its taking in and output this helps in creating fakes using in testing.

      ### Data
      
      - ```data-remote```
      Handles data interacting with the network and is later serverd up to the presentation layer through domain object
      
      - ```data-local```
      Handles persistence of object with Room ORM from.This module is responsible for handling all local related logic and serves up data to and from the presentation layer through domain objects.With this separation we can easily swap in new or replace the database being used without causeing major ripples across the codebase.
      
      ### Splash Screen
      The splash screen uses the network resource bound algorithm to load and cache the configs. * The network bound resource is an algorithm that provides an easy function to fetch resource from both the database and the network. Depending on your needs, you can either :
      
      * Make a network request when there's no data in your cache.
      
      * Display data from your cache when there's no internet.
      
      * Display data from your cache as placeholder instead of a loading screen as you fetch fresh data from the internet.
      
      ### Network Bound Resource

```kotlin
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {

    //First step, fetch data from the local cache
    val data = query().first()

    //If shouldFetch returns true,
    val resource = if (shouldFetch(data)) {

        //Dispatch a message to the UI that you're doing some background work
        emit(Resource.Loading(data))

        try {

            //make a networking call
            val resultType = fetch()

            //save it to the database
            saveFetchResult(resultType)

            //Now fetch data again from the database and Dispatch it to the UI
            query().map { Resource.Success(it) }

        } catch (throwable: Throwable) {

            //Dispatch any error emitted to the UI, plus data emmited from the Database
            query().map { Resource.Error(throwable, it) }

        }

        //If should fetch returned false
    } else {
        //Make a query to the database and Dispatch it to the UI.
        query().map { Resource.Success(it) }
    }

    //Emit the resource variable
    emitAll(resource)
}
```
