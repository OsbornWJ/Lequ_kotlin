package com.xujie.lequ.data.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by wj on 2016/6/29.
 */
class GirlsBean {

    var isError: Boolean = false

    var results: List<ResultsEntity>? = null

    class ResultsEntity() : Parcelable {
        var _id: String? = null
        var createdAt: String? = null
        var desc: String? = null
        var publishedAt: String? = null
        var source: String? = null
        var type: String? = null
        var url: String? = null
        var isUsed: Boolean = false
        var who: String? = null

        constructor(parcel: Parcel) : this() {
            _id = parcel.readString()
            createdAt = parcel.readString()
            desc = parcel.readString()
            publishedAt = parcel.readString()
            source = parcel.readString()
            type = parcel.readString()
            url = parcel.readString()
            isUsed = parcel.readByte() != 0.toByte()
            who = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(_id)
            parcel.writeString(createdAt)
            parcel.writeString(desc)
            parcel.writeString(publishedAt)
            parcel.writeString(source)
            parcel.writeString(type)
            parcel.writeString(url)
            parcel.writeByte(if (isUsed) 1 else 0)
            parcel.writeString(who)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<ResultsEntity> {
            override fun createFromParcel(parcel: Parcel): ResultsEntity {
                return ResultsEntity(parcel)
            }

            override fun newArray(size: Int): Array<ResultsEntity?> {
                return arrayOfNulls(size)
            }
        }


    }
}