#include <jni.h>
#include <string>
#include <vector>
#include <random>
#include <cfloat>
#include <cmath>

#include "MovieController.hpp"


#define DATA_CLASS_PATH_MOVIE \
  "com/jimvang/ndkmasterdetail/data/MovieItem"  // Class path to Movie model.

#define DATA_CLASS_PATH_MOVIEDETAIL \
  "com/jimvang/ndkmasterdetail/data/MovieDetailItem"  // Class path to mMvieDetail model.

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_jimvang_ndkmasterdetail_ItemListActivity_getMovieItemsFromJNI(JNIEnv *env,
                                                                       jobject /* this */,
                                                                       jint numberOfMovies /*Use negative/0 number for random */) {
    /*
     * This MUST be called before anything else, so call it on the first line of the function.
     * Most likely has to do with the JNIEnv pointer being modified making it so the FindClass()
     * method doesn't work correctly.
     */
    jclass movieClass = env->FindClass(DATA_CLASS_PATH_MOVIE);

    if (numberOfMovies < 1 || numberOfMovies > 20) {
        // If an "invalid number" is passed in generate a random number from 1 to 20.
        std::random_device rd;
        std::mt19937 gen(rd());
        std::uniform_real_distribution<double> dist(-1, std::nextafter(20, DBL_MAX));
        numberOfMovies = int(dist(gen));
    }
    // Create a MovieController object with a list of Movies.
    movies::MovieController movieController = movies::MovieController(numberOfMovies);

    // Get the native Movie list that's been created.
    std::vector<movies::Movie *> movieNativeVector = movieController.getMovies();

    // Create a java object array for holding our objects, when transferring to the Java code
    // it will be a regular object array. So in our case an array of MovieItems (i.e. MovieItem[])
    jobjectArray objectArray = env->NewObjectArray(int(movieNativeVector.size()),
                                                   movieClass, nullptr);
    int vecSize = int(movieNativeVector.size());
    for (int i = 0; i < vecSize; i++) {
        // Get the Native movie object
        movies::Movie *nativeMovie = movieNativeVector[i];

        // Get the data we need from the native movie object that we will assign to the new jObject.
        const char *c = nativeMovie->name.c_str();
        jstring stringArg = env->NewStringUTF(c);
        jint intArg = nativeMovie->lastUpdated;
        // Get the constructor method with the two fields as parameters (string, int). They must
        // be defined like this, no space nor comma between the two parameters, followed by a V
        // to indicate the method has a void return value. "<init>" is used for constructors.
        jmethodID midConstructor = env->GetMethodID(movieClass, "<init>",
                                                    "(Ljava/lang/String;I)V");
        // Create our java object using the constructor method we retrieved and the arguments.
        jobject movieObject = env->NewObject(movieClass, midConstructor, stringArg, intArg);

        // Put the java movie object in our object array.
        env->SetObjectArrayElement(objectArray, i, movieObject);
    }
    return objectArray;
}