/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_haixiao_jnidemo_NdkJniUtils */

#ifndef _Included_com_haixiao_jnidemo_NdkJniUtils
#define _Included_com_haixiao_jnidemo_NdkJniUtils
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_haixiao_jnidemo_NdkJniUtils
 * Method:    getCLanguageString
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_haixiao_jnidemo_NdkJniUtils_getCLanguageString
  (JNIEnv *, jobject);

/*
 * Class:     com_haixiao_jnidemo_NdkJniUtils
 * Method:    IirFilter
 * Signature: ([FII)[F
 */
JNIEXPORT jfloatArray JNICALL Java_com_haixiao_jnidemo_NdkJniUtils_IirFilter
  (JNIEnv *, jobject, jfloatArray, jint, jint);

/*
 * Class:     com_haixiao_jnidemo_NdkJniUtils
 * Method:    calcHeartRate
 * Signature: ([F)F
 */
JNIEXPORT jfloat JNICALL Java_com_haixiao_jnidemo_NdkJniUtils_calcHeartRate
  (JNIEnv *, jobject, jfloatArray);

#ifdef __cplusplus
}
#endif
#endif
