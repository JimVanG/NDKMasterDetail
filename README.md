# NDKMasterDetail
This application uses the `NDK` to create and supply basic data that is transferred via the `JNI`. The `JNI` methods convert the C++ objects into the Java model classes specified by the app.

The application's UI is designed for different screen sizes. When run on large devices the main layout is contained in a two-pane Master-Detail UI, with the data list on the left and the detail on the right.

# Application's Purpose

The purpose of this application is to display multi screen support and the usage of the `NDK`/`JNI`.

# Running the Application

The application uses `productFlavors` to reduce it's size. The `productFlavors` are split up around the different CPU architectures. So the application will only include what is necessary for the specific build, thus reducing the application's size.

As a result of the `productFlavor` usage, when running the application from AndroidStudio the specific build variant will have to be chosen for the target device. 

**For example:** If the target device is an Android Emulator the build variant will most likely be an `x86` CPU architecture. So an `x86` build variant will have to be selected before being able to build on the emulator.

To change the build variant: `Build`->`Select Build Variant...`, then choose the target devices corresponding build variant.
