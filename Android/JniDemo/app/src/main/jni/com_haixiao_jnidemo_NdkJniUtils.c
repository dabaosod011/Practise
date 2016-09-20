//
// Created by Hai Xiao on 8/14/16.
//
#include <jni.h>
#include <string.h>
#include "com_haixiao_jnidemo_NdkJniUtils.h"
/*
 * Class:     io_github_yanbober_ndkapplication_NdkJniUtils
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_haixiao_jnidemo_NdkJniUtils_getCLanguageString
        (JNIEnv *env, jobject obj) {
    return (*env)->NewStringUTF(env,"test string from jni");
}


JNIEXPORT jfloatArray JNICALL Java_com_haixiao_jnidemo_NdkJniUtils_IirFilter
        (JNIEnv *env, jobject obj, jfloatArray pcgBuffer, jint start, jint size) {
    jfloatArray result;
    result = (*env)->NewFloatArray(env,size);
    if (result == NULL) {
        return NULL;
    }

    return result;
}

JNIEXPORT jfloat JNICALL Java_com_haixiao_jnidemo_NdkJniUtils_calcHeartRate
        (JNIEnv *env, jobject obj, jfloatArray ecgBuffer) {
    jsize length = (*env)->GetArrayLength(env,ecgBuffer); //取Java 版的ecgBuffer 长度
    return length;
}

