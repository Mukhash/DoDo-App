# DoDo App
DoDo Android application

<p align="left">  
DoDo is a demo application based on modern Android application stacks and MVVM architecture.
</p>
</br>

<p align="left">
<img src="/preview/dodo_screenshot.jpg" width="30%"/>
</p>

## Download
Go to the [Releases](https://github.com/Mukhash/dodo/releases) to download the lastest APK.

<img src="/preview/dodo_preview.gif" align="right" width="32%"/>

## Tech stack & Open-source libraries
- Minimum SDK level 26
- [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous.
- JetPack
  - LiveData - notify domain layer data to views.
  - Lifecycle - dispose of observing data when lifecycle state changes.
  - ViewModel - UI related data holder, lifecycle aware.
  - Room Persistence - construct a database using the abstract layer.
- Architecture
  - MVVM Architecture (View - DataBinding - ViewModel - Model)
  - Repository pattern
- [Material-Components](https://github.com/material-components/material-components-android) - Material design components recyclerView, cardView.

- What's next
  - Saving states of UI
  - Animation for recycler view
  - Timestamps for added tasks
  - Choice of background colors for list items
  - Reminders
  - Recorders
