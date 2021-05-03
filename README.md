# MovieBox

[![Kotlin Version](https://img.shields.io/badge/Kotlin-1.4.32-brightgreen)](https://kotlinlang.org) [![AGP](https://img.shields.io/badge/AGP-7.0.0--alpha14-orange)](https://developer.android.com/studio/releases/gradle-plugin) [![Gradle](https://img.shields.io/badge/Gradle-7.0-blue)](https://gradle.org) [![Codacy Badge](https://api.codacy.com/project/badge/Grade/2ce05b3df9f948f1988e48756d8b5901)](https://www.codacy.com/app/majorkik/MovieBox?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=majorkik/MovieBox&amp;utm_campaign=Badge_Grade)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0) 

## О проекте

MovieBox - pet-project для поиска, просмотра информации и отслеживания фильмов и сериалов. Цель
проекта:

- разработка собственного дизайна;
- реализовать приятный UI и UX;
- реализовать проект с современным стеком технологий и решений;
- реализация многомодульного проекта;
- комбинация написания проекта и изучение и закрепление новых подходов и решений (см. итоги что из
  этого получилось);

Статус проекта: `Временно на паузе`.

Есть идея реализации схожего по смыслу проекта, но с использованием Jetpack Compose, с продуманной
архитектурой и исправлением недостатков этого проекта.

## Технологии, паттерны и прочее, которые используются в проекте

- #### Tech-stack

  -   [100% Kotlin](https://kotlinlang.org/) + [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - выполнение фоновых операций
  -   Сеть
  
      -   [Retrofit](https://square.github.io/retrofit/) - выполнение сетевых вызовов
  
      -   [OkHttp](https://github.com/square/okhttp) - создание HTTP клиента
  -   [Coil](https://github.com/coil-kt/coil) - загрузка изображений
  -   [Jetpack](https://developer.android.com/jetpack/)
      -   [Lifecycle](https://developer.android.com/topic/libraries/architecture/lifecycle) - выполнение действие при изменении состояния жизненного цикла 
  
      -   [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - хранение и управление данными, связанными с UI, с учетом жизненного цикла
  -   [Klock](https://github.com/korlibs/klock) - мультиплатформенная библиотека для работы с датой и временем
  -   [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - управление и взаимодействие с элементами представления 
  -   [Dynamic Feature модули](https://developer.android.com/studio/projects/dynamic-delivery)
  -   Собственный дизайн
  
- Архитектура

  -   MVVM + MVI (только начал внедрять)

  -   [Android Architecture components](https://developer.android.com/topic/libraries/architecture)

  -   [Android KTX](https://developer.android.com/kotlin/ktx) - Jetpack Kotlin расширения

- Внедрение зависимостей

  -   [Koin](https://github.com/InsertKoinIO/koin) - управление зависимостей

- Статические анализаторы кода

  -   [Ktlint](https://github.com/pinterest/ktlint) - проверка и форматирование кода

- Gradle

  -   [Gradle Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html) и кастомные таски

- Логгеры

  -   [Timber](https://github.com/JakeWharton/timber) 

  -   [Pretty Logger](https://github.com/orhanobut/logger) - легковесный и мощный логгер для Android

## Архитектура

В проекте реализована модульная архитектура. Каждая группа экранов разбита на определенные `feature-модули`. Данный подход позволяет достигнуть:

- лучшую гибкость по сравнению с подходом, где проект разбит на 3 модуля (presentation, domain, data);
- разделить код на группы и разрабатывать его изолированно;
- построить граф зависимостей так, чтобы получать доступ к классам можно было только при явной зависимости;
- улучшает навигацию по коду;
- чуть более быстрая компиляция проекта;

Такой подход был подробно описан в статье [Multiple ways of defining Clean Architecture layers](https://proandroiddev.com/multiple-ways-of-defining-clean-architecture-layers-bbb70afa5d4a) автором [Igor Wojda](https://medium.com/@igorwojda). В данной статье автор сравнил разные подходы к разделению проекта на модули и выделил преимущества и недостатки каждого из них.



#### Граф зависимостей

Ниже представлен граф зависимостей между существующими модулями:

<img src="screenshots/architecture.jpg">

- `:feature_navigation` - экраны навигации и настроек приложения;

- `:feature_details` - все экраны, на которых отображаются детали элементов;

- `:feature_search` - поиск и подбор с фильтрами;

- `:feature_collections` - экраны с подборками (коллекциями);

- `:feature_auth` - все, что касается авторизации в приложении;

- `:lib_base` - базовые классы, общие методы, классы и ресурсы;

- `:BuildSrc` - настройки проекта, библиотеки и их версии.

> Обратите внимание, что модули реализованы как [dynamic-feature](https://developer.android.com/studio/projects/dynamic-delivery) модули, поэтому зависимости строятся наоборот. Тогда получается, что функциональные модули зависят от модуля приложения.



#### Структура функциональных модулей

Каждый функциональный модуль содержит собственный набор слоев из `Clean Architecture`.

<img src="screenshots/module_structure.jpg">

Структура модулей `app` и `lib_base` отличается от структуры функциональных модулей и зависит от классов, которые в ней находятся.



## Немного истории и выводы

#### История

Разработка проекта началась с идеи проекта, который бы мог упростить поиск фильмов и сериалов. Хотя
похожих проектов довольно много, на которых все практикуются, но такие проекты как правило очень
маленькие и с базовым функционалом, а я же не просто хотел попрактиковаться, я горел этой идей и
хотел реализовать более крупное приложение, со своим дизайном и дополнительным функционалом.

Все началось с попыток нарисовать какой-то минимальный набор экранов и параллельная разработка
начального функционала (список фильмов и детали фильма). Далее одновременно с набросками новых
экранов, приложение обрастало новым функционалом и не редко переписывался старый функционал или
перестраивался дизайн на каких-то экранах.

В итоге, в свободное время у меня стояло несколько задач:

- придумать и разрабатывать дизайн дизайн;

- разработка проекта (исправление багов и разработка нового функционала);

- исследование и поиск новых решений;

#### Что получилось в итоге

Проект пережил множество различных глобальных изменений:

- избавление от множества активити, приближение к single-activity;
- разбиение проекта на feature delivery модули, что забрало очень много времени и добавило новых
  проблем;
- попытки реализации анимации смахивания экрана (похожая логика на экранах в YouTube);
- экспериментирование с Navigation Components для сохранения логики смахивания экрана жестом;
- миграция с Glide на Coil;
- миграция с Dagger на Koin;
- миграция на Gradle Kotlin DSL;
- использование ViewBinding (как только появилась) вместо Kotlin Synthetic;
- множественные изменения в дизайне (различные версии см. ниже), попытки разбиения его на различные
  компоненты, отделение иконок, шрифтов и цветов (создание дизайн системы);
- попытки оптимизации различных мест в проекте;
- реализация MVVM + MVI подхода для обработки различных состояний экрана;
- и т.д.;

В итоге, я считаю, что разработка проекта была положительным опытом. У меня все таки получилось
реализовать свой дизайн почти в полном объеме, разбить проект на модули и сделать его более
масштабируемым, а также использовать современные технологии и решения.

Однако, в силу того, что проект подвергался частым *улучшениям* и иногда сказывался недостаток
времени, проект частично засорился, в нем есть места, которые могут быть улучшены или исправлены.

Ниже я опишу, что может быть улучшено или исправлено:

- построение архитектуры проекта на основе обычных модулей и разделение на core и feature модули;
- тестирование;
- использование функционала Coroutines и Flow на полную;
- построить зависимости для репозиториев на основе абстракций;
- оптимизация экран в xml, уменьшить вложенность;
- внедрить MVI и совместить с MVVM (как например, Roxy);

## Текущий интерфейс

<img src="screenshots/profile.jpg" width="20%" height="20%"> <img src="screenshots/main_movies.jpg" width="20%" height="20%"><img src="screenshots/main_tvs.jpg" width="20%" height="20%"> <img src="screenshots/movie_details.jpg" width="20%" height="20%"><img src="screenshots/movie_details_2.jpg" width="20%" height="20%"> <img src="screenshots/movie_details_add.jpg" width="20%" height="20%"><img src="screenshots/movie_details_watch.jpg" width="20%" height="20%"> <img src="screenshots/person_bio.jpg" width="20%" height="20%"><img src="screenshots/person_details.jpg" width="20%" height="20%"> <img src="screenshots/person_movies.jpg" width="20%" height="20%"><img src="screenshots/settings.jpg" width="20%" height="20%"> <img src="screenshots/discover_cards.jpg" width="20%" height="20%">
<img src="screenshots/discover_filters.jpg" width="20%" height="20%"> <img src="screenshots/discover_filters_2.jpg" width="20%" height="20%">



## Запуск проекта и участие в разработке

Есть несколько способов открыть проект:

##### Android Studio

1. `Android Studio` -> `File` -> `New` -> `From Version control` -> `Git`
2. Ввести `https://github.com/majorkik/MovieBox.git` в поле URL и нажать кнопку `Clone` 

##### Command-line + Android Studio

1. Запустить команду `git clone https://github.com/igorwojda/android-showcase.git` и клонировать проект к себе в директорию
2. Открыть `Android Studio` и выбрать `File | Open...`. Выбрать папку с клонированным проектом и нажать кнопку `Open`.



##### Первый запуск приложения

Для запуска проекта, необходимо указать в файле `local.properties` ряд ключей. Пример:

```properties
#Tmdb
keyTmdb="введите ключ"
keyTmdbv4="оставьте пустым" 

#Youtube
youTubeKey="введите ключ"

#Trakt
secretKeyTrakTV="оставьте пустым"
keyTrakTv="оставьте пустым"
```

> Ключ для TMDb можно взять на странице [TMDb API Introduction](https://developers.themoviedb.org/3).

> Ключ для Youtube можно взять на странице [Youtube API](https://developers.google.com/youtube/v3/getting-started).

## Полезные gradle таски

`/gradle packageDebugUniversalApk` - собирает apk файлы вместе с динамическими модулями

`/gradle ktlintDebugFormat` - анализ кода и его форматирование

`/gradle ktlintDebugCheck` - анализ кода и поиск мест, которые могут быть отформатированы

`/gradle dependencyUpdates` - отображает список библиотек и плагинов, которые используются в
проекте, их текущую версию и последнюю версию

## Различные версии дизайна

<img src="screenshots/old/old_1.jpg" width="20%" height="20%"> <img src="screenshots/old/old_2.jpg" width="20%" height="20%"> <img src="screenshots/old/old_3.jpg" width="20%" height="20%"> <img src="screenshots/old/old_4.jpg" width="20%" height="20%"> <img src="screenshots/old/old_5.jpg" width="20%" height="20%"> <img src="screenshots/old/old_6.jpg" width="20%" height="20%"> <img src="screenshots/old/old_7.jpg" width="20%" height="20%"> <img src="screenshots/old/main_page.jpg" width="20%" height="20%"> <img src="screenshots/old/movie_page.jpg" width="20%" height="20%"> <img src="screenshots/old/person_page.jpg" width="20%" height="20%"> <img src="screenshots/old/tv_page.jpg" width="20%" height="20%">

## Лицензия

Этот репозиторий находится под лицензией `GNU v3`. Подробную информаци вы можете найти [здесь](https://github.com/majorkik/MovieBox/blob/master/LICENSE.bat) или на [официальном сайте](https://www.gnu.org/licenses/gpl-3.0.ru.html).

