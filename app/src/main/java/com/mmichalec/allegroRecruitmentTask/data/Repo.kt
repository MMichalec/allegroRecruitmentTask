package com.mmichalec.allegroRecruitmentTask.data


data class Repo(
    val id: Int,
    val name: String,
    val description: String? = null,
    val created_at: String,
    val updated_at: String? = null,
){
    val attributionUrl get() = "https://api.github.com/repos/allegro/$name"
    //TODO Remove
    //nested class for inside arrays part2 8:00
}