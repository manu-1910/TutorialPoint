package com.nativeapp.tutorialpoint.repositories

import com.google.gson.annotations.SerializedName
import com.nativeapp.tutorialpoint.ClassEntity
import com.nativeapp.tutorialpoint.LectureEntity

class ClassModel constructor(
    @SerializedName("section_name")
    val sectionTitle: String,
    @SerializedName("lectures")
    val lecture: List<LectureModel>
)/*: ClassEntity(sectionTitle, lecture) {

}*/

class LectureModel constructor(
    @SerializedName("lecture_url")
    val lectureurl: String,
    @SerializedName("lecture_name")
    val title: String
)/*: LectureEntity(title, lectureurl){


}*/
