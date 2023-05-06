package com.mkrlabs.pmisdefence.model

import java.io.Serializable

data class Project(
    var projectUID :String = "",
    var projectID :String = "",
    var projectType : String= "",
    var projectName :String= "",
    var projectDescription :String= "",
    var projectMotivation :String= "",
    var projectObjective :String= "",
    var teacher_id :String= "",
    var userList :List<Student>? =null
    ):Serializable
