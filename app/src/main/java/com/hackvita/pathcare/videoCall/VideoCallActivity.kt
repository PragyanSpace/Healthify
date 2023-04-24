package com.hackvita.pathcare.videoCall

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hackvita.pathcare.R
import com.hackvita.pathcare.databinding.ActivityVideoCallBinding
import io.agora.rtc.IRtcEngineEventHandler
import io.agora.rtc.RtcEngine
import io.agora.rtc.video.VideoCanvas
import io.agora.rtc.video.VideoEncoderConfiguration




class VideoCallActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoCallBinding
    private lateinit var rtcEngine: RtcEngine
    private var localUid: Int = 0
    private var remoteUid: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_call)

        rtcEngine = RtcEngine.create(applicationContext, "a08ed9e174a94e049480e4a2bdc3e812", object : IRtcEngineEventHandler() {
            override fun onUserJoined(uid: Int, elapsed: Int) {
                runOnUiThread {
                    remoteUid = uid
                    setupRemoteVideoView(remoteUid)
                }
            }

            override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
                super.onJoinChannelSuccess(channel, uid, elapsed)
                // Handle join channel success
                setupLocalVideoView()
            }

        })

        rtcEngine.joinChannel(null, "progyan", null, 0);

        rtcEngine.setVideoEncoderConfiguration(
            VideoEncoderConfiguration(
                VideoEncoderConfiguration.VD_640x360,
                VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_15,
                VideoEncoderConfiguration.STANDARD_BITRATE,
                VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT
            )
        )

        rtcEngine.enableVideo();
        rtcEngine.enableLocalVideo(true);

        rtcEngine.setDefaultAudioRoutetoSpeakerphone(true)

    }

    private fun setupRemoteVideoView(remoteUid: Int) {

        // Set up the remote video view
        val remoteVideoView = RtcEngine.CreateRendererView(this)
        val remoteVideoViewContainer = binding.remoteVideoViewContainer

        rtcEngine.setupRemoteVideo(VideoCanvas(remoteVideoView, VideoCanvas.RENDER_MODE_HIDDEN, remoteUid))
        remoteVideoViewContainer.addView(remoteVideoView)

    }

    private fun setupLocalVideoView()
    {
        val localVideoViewContainer: FrameLayout = binding.localVideoViewContainer
        val localVideoView = RtcEngine.CreateRendererView(this)
        rtcEngine.setupLocalVideo(VideoCanvas(localVideoView, VideoCanvas.RENDER_MODE_HIDDEN, localUid))
        localVideoViewContainer.addView(localVideoView)

        binding.endCallButton.setOnClickListener {
            rtcEngine.leaveChannel()
        }
    }

}