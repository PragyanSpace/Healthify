package com.hackvita.pathcare

import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import io.agora.agorauikit_android.*
import io.agora.rtc2.Constants


class VideoCallActivity : AppCompatActivity() {

//    val agView = AgoraVideoViewer(this, AgoraConnectionData("a08ed9e174a94e049480e4a2bdc3e812"))


    // Object of AgoraVideoVIewer class
    private var agView: AgoraVideoViewer? = null

    // Fill the App ID of your project generated on Agora Console.
    private val appId = "a08ed9e174a94e049480e4a2bdc3e812"

    // Fill the channel name.
    private val channelName = "healthyfy"

    // Fill the temp token generated on Agora Console.
    private val token = "007eJxTYLhyOfbAPAv52fNdb6UZRFU03jcUFcv1DPFf56qwxNPKIkaBIdHAIjXFMtXQ3CTR0iTVwMTSxMIg1STRKCkl2TjVwtDo3fvZKQ2BjAx7prGxMjJAIIjPyZCRmphTklGZVsnAAABq1iAW"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call2)
        initializeAndJoinChannel();

//        this.addContentView(
//            agView,
//            FrameLayout.LayoutParams(
//                FrameLayout.LayoutParams.MATCH_PARENT,
//                FrameLayout.LayoutParams.MATCH_PARENT
//            )
//        )

// Join channel "test"
        agView?.join("healthyfy", role= Constants.CLIENT_ROLE_BROADCASTER)
    }

    private fun initializeAndJoinChannel() {
        // Create AgoraVideoViewer instance
        agView = try {
            AgoraVideoViewer(
                this,
                AgoraConnectionData(appId, token),
                AgoraVideoViewer.Style.FLOATING,
                AgoraSettings(),
                null
            )
        } catch (e: Exception) {
            Log.e(
                "AgoraVideoViewer",
                "Could not initialize AgoraVideoViewer. Check that your app Id is valid."
            )
            Log.e("Exception", e.toString())
            return
        }
        // Add the AgoraVideoViewer to the Activity layout
        addContentView(
            agView, FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT
            )
        )
        // Check permission and join a channel
//        if (DevicePermissionKt.requestPermissions(AgoraVideoViewer, this)) {
            joinChannel()
//        } else {
            val joinButton = Button(this)
            joinButton.text = "Allow camera and microphone access, then click here"
            joinButton.setOnClickListener {
//                if (DevicePermissionKt.requestPermissions(
//                        AgoraVideoViewer,
//                        applicationContext
//                    )
//                ) {
                    (joinButton.parent as ViewGroup).removeView(joinButton)
                    joinChannel()
//                }
//            }
            addContentView(
                joinButton,
                FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, 200)
            )
        }
    }

    fun joinChannel() {
        agView!!.join(channelName, token, Constants.CLIENT_ROLE_BROADCASTER, 0)
    }


}