#include <jni.h>
#include <string>
#include <vector>
#include <random>
#include <cfloat>
#include <cmath>

#include "MovieController.hpp"


#define DATA_CLASS_PATH_MOVIE \
  "com/jimvang/ndkmasterdetail/data/MovieItem;"  // Class path to Movie model.

#define DATA_CLASS_PATH_MOVIEDETAIL \
  "com/jimvang/ndkmasterdetail/data/MovieDetailItem"  // Class path to mMvieDetail model.

extern "C" JNIEXPORT jobjectArray JNICALL
Java_com_jimvang_ndkmasterdetail_ItemListActivity_getMovieItemsFromJNI(JNIEnv *env,
                                                                       jobject /* this */,
                                                                       jint numberOfMovies /*Use negative/0 number for random */
) {
    if (numberOfMovies < 1 || numberOfMovies > 20) {
        // If an "invalid number" is passed in generate a random number from 1 to 20.
        std::random_device rd;
        std::mt19937 gen(rd());
        std::uniform_real_distribution<double> dist(-1, std::nextafter(20, DBL_MAX));
        numberOfMovies = int(dist(gen));
    }
    // Create a MovieController object with a list of Movies.
    movies::MovieController movieController = movies::MovieController(numberOfMovies);

//    jclass movieClass = env->GetObjectClass(movieObject);

    jclass movieClass = env->FindClass("Lcom/jimvang/ndkmasterdetail/data/MovieItem;");


    std::vector<jobject> javaMovieList;

    // Get the native Movie list that's been created.
    std::vector<movies::Movie *> movieNativeVector = movieController.getMovies();

    // Get the native movie count so we can traverse the native movie list creating
    // java object models for the Android code.
    int movieNativeCount = int (movieNativeVector.size());
    for (int i = 0; i < movieNativeCount; i++) {
        //
        // Should remove this whole loop in favor of just adding the movies to a jObjectArray.
        //
        // Get the Native movie object
//        movies::Movie *nativeMovie = movieNativeVector.at(unsigned int(i));
        movies::Movie *nativeMovie = movieNativeVector[i];

        // Get the data we need from the native movie object that we will assign to the new jObject.
        jstring stringArg = env->NewStringUTF(nativeMovie->name.data());
        jint intArg = nativeMovie->lastUpdated;
        // Get the constructor method with the two fields as parameters (string, int).
        jmethodID midConstructor = env->GetMethodID(movieClass, "<init>",
                                                    "(Ljava/lang/String, I)V");
        jobject movieObject = env->NewObject(movieClass, midConstructor, stringArg, intArg);
        // Add java movie object to vector list.
//        javaMovieList.push_back(movieObject);
        javaMovieList.push_back(movieObject);

//        jmethodID methodId = env->GetMethodID(movieClass, "setName", "(Ljava/lang/String)V");
//        jobject javaObj = env->NewObject(movieClass, methodId, stringArg, intArg);

    }

    // Create a java object array for ease of use in the java side.
    jobjectArray objectArray = env->NewObjectArray(int (javaMovieList.size()), movieClass, nullptr);
    int vecSize = int (javaMovieList.size());
    for (int i = 0; i < vecSize; ++i) {
        // Get the java movie item from the vector so we can populate object array
        jobject mObject = javaMovieList[i];
        // Put the java movie object in our object array.
        env->SetObjectArrayElement(objectArray, i, mObject);
    }
    return objectArray;
}