package com.mmichalec.allegroRecruitmentTask.data

data class RepoDetails(
    val id: Int,
    val name: String,
    val description: String? = null,
    val created_at: String,
    val updated_at: String? = null,
    val url: String,
    val download_url: String,
    val language: String,
    val watchers_count: Int,
    val subscribers_count: Int,
) {
}