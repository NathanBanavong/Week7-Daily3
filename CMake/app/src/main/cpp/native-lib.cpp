#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_consultants_cmake_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Enter Numbers to Add";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_consultants_cmake_MainActivity_showFROMNJI(JNIEnv *env, jobject, jint a,
                                                            jint b) {

    // TODO
    std::int16_t sum = a + b;
    std::to_string(sum);
    return env->;

}