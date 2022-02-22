package com.mcsunity.model.inteface

import org.bson.conversions.Bson

interface IService {
    fun <T> get(): T
    fun <T> put(data: T): String
    fun <T> post(data: Any): T
    fun patch(filter: Bson, data: Bson): Boolean
    fun delete(data: Bson): Boolean
}