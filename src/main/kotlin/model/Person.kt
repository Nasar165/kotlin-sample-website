package com.mcsunity.model

import com.mcsunity.model.inteface.IPerson
import kotlinx.serialization.Serializable

@Serializable
class Person(override val firstName: String, override val lastName: String) : IPerson