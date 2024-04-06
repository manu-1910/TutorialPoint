package com.nativeapp.tutorialpoint

open class ClassEntity constructor(val section: String, val lectureList: List<LectureEntity>){
    private val sectionName:String = section
    private val lectures: List<LectureEntity> = lectureList
}

open class LectureEntity constructor(val name: String, val url: String){
    private val lectureName: String = name
    private val lectureURL: String = url
}
