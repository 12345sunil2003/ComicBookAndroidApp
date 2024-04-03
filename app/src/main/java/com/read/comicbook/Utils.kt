package com.read.comicbook

import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowInsets
import android.view.WindowInsetsController
import com.read.comicbook.model.CategoryModel
import com.read.comicbook.model.ComicImageModel
import com.read.comicbook.model.PostInfoModel
import com.read.comicbook.model.UserModel

object Utils {

    fun hideStatusBar(window: Window) {
        // Check for Android version
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // For Android 11 (API level 30) and above
            window.setDecorFitsSystemWindows(false)
            val controller = window.insetsController
            controller?.let {
                it.systemBarsBehavior =
                    WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
                it.hide(WindowInsets.Type.statusBars())
            }
        } else {
            // For Android versions below 11
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
        }
    }

    fun getDummyUsers(): List<UserModel> {
        return listOf(
            UserModel(name = "Alice", img = "https://avatars.githubusercontent.com/u/1024025?v=4"),
            UserModel(name = "Bob", img = "https://avatars.githubusercontent.com/u/499550?v=4"),
            UserModel(name = "Charlie", img = "https://avatars.githubusercontent.com/u/810438?v=4"),
            UserModel(name = "David", img = "https://avatars.githubusercontent.com/u/8683378?v=4"),
            UserModel(name = "Eva", img = "https://avatars.githubusercontent.com/u/905434?v=4"),
            UserModel(name = "Frank", img = "https://avatars.githubusercontent.com/u/12994887?v=4"),
            UserModel(name = "Grace", img = "https://avatars.githubusercontent.com/u/5550850?v=4"),
            UserModel(name = "Henry", img = "https://avatars.githubusercontent.com/u/66577?v=4"),
            UserModel(name = "Ivy", img = "https://avatars.githubusercontent.com/u/170270?v=4"),
            UserModel(name = "Jack", img = "https://avatars.githubusercontent.com/u/241138?v=4")
        )
    }

    fun getDummyComics(): List<ComicImageModel>{
        return listOf(
            ComicImageModel(imgFiles = R.drawable.one_comic),
            ComicImageModel(imgFiles = R.drawable.two_comic),
            ComicImageModel(imgFiles = R.drawable.three_comic)
        )
    }
    fun getDummyComicMovies(): List<PostInfoModel> {
        return listOf(
            PostInfoModel(
                title = "The Avengers",
                desc = "Earth's mightiest heroes come together to fight a common enemy.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-8047413.jpg",
                category = "Action, Adventure",
                story = "The Avengers is a team of superheroes assembled by Nick Fury to save the world from powerful threats.",
                imgFiles =null,
                isFav = true,
                rating = "8.5",
                price = "$199",
                isPaid = true
            ),
            PostInfoModel(
                title = "Guardians of the Galaxy",
                desc = "A group of intergalactic misfits come together to form the Guardians of the Galaxy.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-4732086.jpg",
                category = "Sci-Fi, Adventure",
                story = "The Guardians of the Galaxy, led by Peter Quill, embark on a mission to protect a powerful orb from falling into the wrong hands.",
                imgFiles = null,
                isFav = false,
                rating = "8.0",
                price = null,
                isPaid = false
            ),
            PostInfoModel(
                title = "Thor: Ragnarok",
                desc = "Thor finds himself imprisoned on a distant planet and must fight in a gladiatorial contest to save Asgard from destruction.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-6297802.jpg",
                category = "Action, Comedy",
                story = "Thor: Ragnarok follows Thor's journey as he discovers the villainous Hela's plan to destroy Asgard and must find a way to stop her.",
                imgFiles = null,
                isFav = true,
                rating = "8.5",
                price = "$10",
                isPaid = true
            ),
            PostInfoModel(
                title = "Black Panther",
                desc = "T'Challa, the king of Wakanda, rises to the throne and faces challenges to his leadership.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-6905086.jpg",
                category = "Action, Drama",
                story = "Black Panther explores the challenges faced by T'Challa as he takes on the responsibilities of being the king and the Black Panther.",
                imgFiles = null,
                isFav = false,
                rating = "7.3",
                price = null,
                isPaid = false
            ),
            PostInfoModel(
                title = "Doctor Strange",
                desc = "A former neurosurgeon discovers the mystic arts after a car accident and becomes the Sorcerer Supreme.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-9386086.jpg",
                category = "Action, Adventure",
                story = "Doctor Strange follows the journey of Stephen Strange as he learns the mystical arts to protect the world from mystical threats.",
                imgFiles = null,
                isFav = true,
                rating = "7.5",
                price = "$17",
                isPaid = true
            ),
            PostInfoModel(
                title = "Ant-Man",
                desc = "A former thief is recruited to become Ant-Man and must help his mentor plan and execute a heist.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-5452071.jpg",
                category = "Action, Comedy",
                story = "Ant-Man features Scott Lang as he takes on the role of Ant-Man to help his mentor, Dr. Hank Pym, in a heist that will save the world.",
                imgFiles = null,
                isFav = false,
                rating = "7.3",
                price = null,
                isPaid = false
            ),
            PostInfoModel(
                title = "Iron Man",
                desc = "Tony Stark builds a powered suit of armor and becomes the armored superhero, Iron Man.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-9357490.jpg",
                category = "Action, Sci-Fi",
                story = "Iron Man follows Tony Stark's journey from an industrialist to a superhero as he creates a suit of armor to fight against threats.",
                imgFiles = null,
                isFav = true,
                rating = "7.9",
                price = "$11",
                isPaid = true
            ),
            PostInfoModel(
                title = "Captain America: The Winter Soldier",
                desc = "Captain America faces a mysterious assassin known as the Winter Soldier and uncovers a conspiracy within S.H.I.E.L.D.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-3683205.jpg",
                category = "Action, Thriller",
                story = "Captain America: The Winter Soldier explores Captain America's fight against a powerful assassin and the revelation of a hidden conspiracy.",
                imgFiles = null,
                isFav = false,
                rating = "7.7",
                price = null,
                isPaid = false
            ),
            PostInfoModel(
                title = "Iron Man 3",
                desc = "Tony Stark faces a new powerful enemy and must confront the demons of his past.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-1662326.jpg",
                category = "Action, Sci-Fi",
                story = "Iron Man 3 follows Tony Stark as he faces a formidable enemy known as the Mandarin and deals with the aftermath of the events in The Avengers.",
                imgFiles = null,
                isFav = true,
                rating = "7.2",
                price = "$23",
                isPaid = true
            ),
            PostInfoModel(
                title = "Captain Marvel",
                desc = "Vers, a Kree warrior, discovers her true identity and battles a galactic war between two alien races.",
                thumbnail = "https://s3.amazonaws.com/comicgeeks/comics/covers/medium-2578246.jpg",
                category = "Action, Sci-Fi",
                story = "Captain Marvel follows the journey of Carol Danvers as she discovers her true identity and becomes one of the universe's most powerful heroes.",
                imgFiles = null,
                isFav = false,
                rating = "6.9",
                price = null,
                isPaid = false
            )
        ).shuffled()
    }
    fun getDummyCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel(
                name = "Action",
                img = R.drawable.fire
            ),
            CategoryModel(
                name = "Romance",
                img = R.drawable.heart
            ),
            CategoryModel(
                name = "Drama",
                img = R.drawable.sneezing_face
            ),
            CategoryModel(
                name = "Comedy",
                img = R.drawable.rolling_on_the_floor_laughing
            ),
            CategoryModel(
                name = "Horror",
                img = R.drawable.face_screaming_in_fear
            )
        )
    }


}