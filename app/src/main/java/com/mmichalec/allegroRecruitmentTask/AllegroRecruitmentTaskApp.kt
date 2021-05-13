package com.mmichalec.allegroRecruitmentTask

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
//Class required by Dagger Hilt
//Without the Gradle plugin, the base class must be specified in the annotation and
// the annotated class must extend the generated class:
@HiltAndroidApp
class AllegroRecruitmentTaskApp: Application(){}