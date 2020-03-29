package com.example.servercontrol

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction




interface AWSInterface {
    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    fun startMNFT(request: StartServerRequest?): StartServerResponse?
    @LambdaFunction
    fun stopMNFT(request: StartServerRequest?): StartServerResponse?
}