package me.arun.vcinch

import androidx.room.Room
import androidx.test.InstrumentationRegistry
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
open class CachedBufferooDaoTest {

//    private lateinit var bufferoosDatabase: BufferoosDatabase
//
//    @Before
//    fun initDb() {
//        bufferoosDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
//                BufferoosDatabase::class.java).build()
//    }
//
//    @After
//    fun closeDb() {
//        bufferoosDatabase.close()
//    }
//
//    @Test
//    fun insertBufferoosSavesData() {
//        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
//        bufferoosDatabase.cachedBufferooDao().insertBufferoo(cachedBufferoo)
//
//        val bufferoos = bufferoosDatabase.cachedBufferooDao().getBufferoos()
//        assert(bufferoos.isNotEmpty())
//    }
//
//    @Test
//    fun getBufferoosRetrievesData() {
//        val cachedBufferoos = BufferooFactory.makeCachedBufferooList(5)
//
//        cachedBufferoos.forEach {
//            bufferoosDatabase.cachedBufferooDao().insertBufferoo(it) }
//
//        val retrievedBufferoos = bufferoosDatabase.cachedBufferooDao().getBufferoos()
//        assert(retrievedBufferoos == cachedBufferoos.sortedWith(compareBy({ it.id }, { it.id })))
//    }
//
//    @Test
//    fun clearBufferoosClearsData() {
//        val cachedBufferoo = BufferooFactory.makeCachedBufferoo()
//        bufferoosDatabase.cachedBufferooDao().insertBufferoo(cachedBufferoo)
//
//        bufferoosDatabase.cachedBufferooDao().clearBufferoos()
//        assert(bufferoosDatabase.cachedBufferooDao().getBufferoos().isEmpty())
//    }

}