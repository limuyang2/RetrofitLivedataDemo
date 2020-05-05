package com.example.retrofitdemo

import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun moshiTest() {
        val json = """
            {
                
                "question_id": 1,
                "value": "",
                "name": "ccccc",
                "sub": {
                    "title": "test",
                    "sub_b": false
                },
                "sub2": {}
                
            }
        """

        val moshi = Moshi.Builder()
            .add(NullTypeAdapterFactory())
//            .add(KotlinJsonAdapterFactory())
            .build()
        val jsonAdapter: JsonAdapter<TestData> = moshi.adapter(TestData::class.java)

        val bean = jsonAdapter.fromJson(json)!!
//        StandardJsonAdapters
        println("---> bean _id: ${bean._id}")
        println("---> bean value: ${bean.value}       name: ${bean.name}         question_id: ${bean.question_id}       answer_id: ${bean.answer_id}")
        println("---> sub  bean : ${bean.sub.title}       sub_b: ${bean.sub.sub_b}")
        println("---> sub2 title: ${bean.sub2?.title}       sub_b: ${bean.sub2?.sub_b}")


        val json22 = """
            {
                "code": 100,
                "msg": {}
            }
        """

        val type = Types.newParameterizedType(BaseBean::class.java, InfoData::class.java)
        val jsonAdapter22: JsonAdapter<BaseBean<InfoData>> = moshi.adapter(type)
        val baseBean = jsonAdapter22.fromJson(json22)!!
        println("---> baseBean code: ${baseBean.code}")
    }


}

@JsonClass(generateAdapter = true)
data class TestData(
    val answer_id: Long = 1,
    val question_id: Int,
    val value: String = "v",
    val name: String = "33",
    val sub: Sub,
    val sub2: Sub2? = Sub2()
) {
    @Transient
    val _id: String = "110"

    @JsonClass(generateAdapter = true)
    data class Sub(val title: String, val sub_b: Boolean)

    @JsonClass(generateAdapter = true)
    data class Sub2(val title: String = "b", val sub_b: String = "dd") {

    }
}

@JsonClass(generateAdapter = true)
data class BaseBean<T>(val code: Int, val msg: T)

@JsonClass(generateAdapter = true)
data class InfoData(val name: String = "")


//class MyBaseBeanAdapter: JsonAdapter<BaseBean<*>> (){
//    override fun fromJson(reader: JsonReader): BaseBean<*>? {
//        reader.beginObject()
//
//    }
//
//    override fun toJson(writer: JsonWriter, value: BaseBean<*>?) {
//
//    }
//}

class NullTypeAdapterFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        val delegate = moshi.nextAdapter<Any>(this, type, annotations)

        val intAdapter: JsonAdapter<Int> = moshi.adapter(Int::class.java, emptySet(), "code")

        return object : JsonAdapter<Any>() {
            override fun fromJson(reader: JsonReader): Any? {

                when (type) {
                    BaseBean::class.java -> {
                        println("-------------->>BaseBean ${type}  ${reader.peek() == JsonReader.Token.NULL}")
                        var code: Int? = null

                        reader.beginObject()
                        while (reader.hasNext()) {
                            when (reader.nextName()) {
                                "code" -> {
                                    code = intAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
                                        "code",
                                        "code",
                                        reader
                                    )
                                 }
                                "msg" -> {
                                    if (type is ParameterizedType) {
                                        moshi.adapter<Any>(type)
                                    }
                                    moshi.adapter<Any>(type)
                                }
                                else -> {
                                    // Unknown name, skip it.
                                    reader.skipName()
                                    reader.skipValue()
                                }
                            }
                        }
                        reader.endObject()
                    }

                    Int::class.java -> {
                        if (reader.peek() == JsonReader.Token.NULL) {
                            reader.nextNull<Int>()
                            return 0
                        }
                    }
                    String::class.java -> {
                        if (reader.peek() == JsonReader.Token.NULL) {
                            reader.nextNull<String>()
                            return ""
                        } else if (reader.peek() == JsonReader.Token.BOOLEAN) {
                            return reader.nextBoolean().toString()
                        }
                    }
                    else -> {
                        println("-------------->> ${type}  ${reader.peek() == JsonReader.Token.NULL}")
                        if (reader.peek() == JsonReader.Token.NULL) {
                            reader.skipValue()

                            return null
                        }
                    }
                }


                return delegate.fromJson(reader)
            }

            override fun toJson(writer: JsonWriter, value: Any?) {
                return delegate.toJson(writer, value)
            }
        }
    }
}