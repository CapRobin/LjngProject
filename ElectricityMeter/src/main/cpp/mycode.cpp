//
// Created by Administrator on 2016-11-28.
//

#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_zfg_myndkdemoabc_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
