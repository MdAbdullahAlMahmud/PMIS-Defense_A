package com.mkrlabs.pmisdefence.api

import com.mkrlabs.pmisdefence.model.NotificationItem
import com.mkrlabs.pmisdefence.model.NotificationResponse
import com.mkrlabs.pmisdefence.model.notifications.TopicWiseNotification
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface AppAPI{

    @POST("send")
    suspend fun postNotification(@Body() notificationItem: NotificationItem, @Header("Authorization") authHeader: String?) : Response<NotificationResponse>

    @POST("send")
    suspend fun topicWiseNotification(@Body() notificationItem: TopicWiseNotification, @Header("Authorization") authHeader: String?) : Response<NotificationResponse>

}