package com.mmichalec.allegroRecruitmentTask.data


data class Repo (
    val id: String,
    val name: String,
    val descriptor: String? = null,
    val created_at: String,
    val update_at: String? = null,
){
    val attributionUrl get() = "https://api.github.com/repos/allegro/$name"
    //TODO Remove
    //nested class for inside arrays part2 8:00
}