package com.example.healthapp

import android.app.Application
import com.example.healthapp.data.AppContainer
import com.example.healthapp.data.DefaultAppContainer

// Экземпляр контейнера, который используется другими классами, чтобы получать зависимости уровня приложения
class HealthApplication : Application() { // наследуем функционал класса Application
    lateinit var container: AppContainer // объявляем контейнер с отложенной инициализацией
    override fun onCreate() { // переопределяем функцию создания приложения
        super.onCreate() // вызываем ее
        container = DefaultAppContainer() // инициируем экземпляр контейнера данными из контейнера
    }
}
