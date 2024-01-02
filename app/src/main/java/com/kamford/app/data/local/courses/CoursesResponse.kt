package com.kamford.app.data.local.courses

import com.kamford.app.data.ResultsResponse
import com.kamford.app.di.Constants
import com.squareup.moshi.Json


data class CoursesResponse(
    @Json(name="id") var id: String,
    @Json(name="name") var name: String?,
    @Json(name="path") var path: String?,
    @Json(name="description") var description: String?,
    @Json(name="isType") var isType: Int,
    @Json(name="author") var author: String?,
    @Json(name="parentId") var parentId: String?,
    @Json(name="modelId") var modelId: String?,

    @Json(name="courseList") val nextCourseList: List<CoursesResponse>?,
)


fun ResultsResponse.asStoreCourseList() :Collection<Course>? = courseList?.map { it.asStoreCourse() }

fun CoursesResponse.asCourseList() :Collection<Course>? = nextCourseList?.map { it.asStoreCourse() }

fun CoursesResponse.asStoreCourse() = Course(
    id = id,
    name = name,
    path = path,
    description = description,
    isType = isType,
    author = author,
    parentId = parentId,
    modelId = modelId,

    webCourseUrl = Constants.KAMFORD_APP_WEB + "/portal/course/" + id,
    webCoursePageUrl = Constants.KAMFORD_APP_WEB + "/portal/course/page/" + id,
    blogCourseUrl = Constants.KAMFORD_APP_BLOG + "author-" + author +"/course/" + id,
)