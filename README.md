# Premier - Movie Discovery App

A modern Android application built with Jetpack Compose that allows users to discover top-rated movies and explore similar films. This project demonstrates enterprise-level Android development practices with clean architecture, dependency injection, and modern UI components.

## 🏗️ Architecture

This project follows **Clean Architecture** principles with clear separation of concerns:

### Layers
- **UI Layer**: Jetpack Compose screens and components
- **Domain Layer**: Business logic and use cases (implicit in ViewModels)
- **Data Layer**: Repository pattern with API integration

### Key Architectural Decisions
- **MVVM Pattern**: ViewModels manage UI state and business logic
- **Dependency Injection**: Dagger Hilt for dependency management
- **Navigation**: Jetpack Navigation Compose for type-safe navigation
- **State Management**: LiveData with Compose integration
- **Image Loading**: Coil for efficient image loading and caching

## 🛠️ Tech Stack

### Core Technologies
- **Kotlin**: Primary programming language
- **Jetpack Compose**: Modern declarative UI toolkit
- **Material Design 3**: Latest Material Design components
- **Dagger Hilt**: Dependency injection framework

### Architecture Components
- **ViewModel**: UI-related data holder with lifecycle awareness
- **LiveData**: Observable data holder for UI updates
- **Navigation Compose**: Type-safe navigation between screens

### Networking & Data
- **Retrofit**: HTTP client for API communication
- **RxJava2**: Reactive programming for asynchronous operations
- **Gson**: JSON serialization/deserialization
- **Coil**: Image loading library optimized for Compose

### Testing
- **JUnit**: Unit testing framework
- **Mockito**: Mocking framework for tests
- **AssertJ**: Fluent assertions for better test readability

## 📱 Features

### Main Screen
- Display top-rated movies in a scrollable list
- Each movie card shows poster, title, overview, and rating
- Tap to navigate to detailed view

### Movie Detail Screen
- Full movie information with backdrop image
- Movie title and detailed overview
- Horizontal scrollable list of similar movies
- Navigation back to main screen

### UI/UX Features
- **Material Design 3**: Modern, accessible design system
- **Responsive Layout**: Optimized for different screen sizes
- **Loading States**: Proper loading indicators during data fetch
- **Error Handling**: User-friendly error messages with retry options
- **Smooth Navigation**: Seamless transitions between screens

## 🏢 Enterprise-Level Practices

### Code Organization
```
app/src/main/kotlin/biz/filmeroo/premier/
├── api/                    # API models and service interfaces
├── app/                    # Application class and DI modules
├── base/                   # Base classes (ViewModels, etc.)
├── navigation/             # Navigation setup and screen definitions
├── ui/
│   ├── components/         # Reusable UI components
│   ├── screens/           # Screen-specific composables
│   └── theme/             # Theme, colors, typography
├── main/                  # Main screen feature
└── detail/                # Detail screen feature
```

### Design Patterns
- **Repository Pattern**: Centralized data access
- **Observer Pattern**: LiveData for reactive UI updates
- **Dependency Injection**: Loose coupling and testability
- **Single Responsibility**: Each class has one clear purpose
- **Composition over Inheritance**: Compose functions over class hierarchies

### Code Quality
- **Separation of Concerns**: Clear boundaries between layers
- **Testable Code**: Dependency injection enables easy testing
- **Consistent Naming**: Clear, descriptive naming conventions
- **Error Handling**: Comprehensive error states and user feedback
- **Performance**: Efficient image loading and list rendering

## 🚀 Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- JDK 17
- Android SDK 34

### Setup
1. Clone the repository
2. Open in Android Studio
3. Sync project with Gradle files
4. Run the app on device or emulator

### API Configuration
The app uses The Movie Database (TMDb) API. The API key is included for demo purposes but should be moved to a secure location in production.

## 🧪 Testing

### Unit Tests
- **ViewModels**: Business logic and state management
- **Repository**: Data layer functionality
- **API Service**: Network layer testing

### Running Tests
```bash
./gradlew test
```

## 🔄 Migration from Legacy Code

This project was migrated from XML layouts and traditional Activities to Jetpack Compose:

### What Was Changed
- **UI Layer**: XML layouts → Jetpack Compose
- **Navigation**: Activity-based → Navigation Compose
- **Image Loading**: Picasso → Coil
- **Styling**: XML styles → Compose theming
- **List Management**: RecyclerView → LazyColumn/LazyRow

### Benefits Achieved
- **Reduced Boilerplate**: Less code for UI components
- **Type Safety**: Compile-time navigation safety
- **Better Performance**: Compose's efficient recomposition
- **Modern Development**: Latest Android development practices
- **Improved Maintainability**: Cleaner, more readable code

## 📈 Future Enhancements

- **Offline Support**: Room database for caching
- **Search Functionality**: Search movies by title
- **Favorites**: Save favorite movies locally
- **Dark Theme**: Complete dark mode support
- **Pagination**: Infinite scrolling for large datasets
- **Detailed Analytics**: User interaction tracking
- **Performance Monitoring**: Crash reporting and performance metrics

## 🤝 Contributing

This project follows enterprise development standards:
- Write comprehensive unit tests
- Follow existing code style and architecture
- Update documentation for new features
- Ensure backward compatibility

## 📄 License

This project is for educational and demonstration purposes.