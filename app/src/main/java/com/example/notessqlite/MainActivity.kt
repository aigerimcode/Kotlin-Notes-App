package com.example.notessqlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessqlite.databinding.ActivityMainBinding
import androidx.recyclerview.widget.GridLayoutManager
class MainActivity : AppCompatActivity() {

    //Binding
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NotesDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = NotesDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)

        //layout can be changed later, now it is linear
        //binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.notesRecyclerView.adapter = notesAdapter

        binding.notesRecyclerView.addItemDecoration(
            GridSpacingItemDecoration(
                spanCount = 2,
                spacing = resources.getDimensionPixelSize(
                    com.example.notessqlite.R.dimen.grid_spacing
                ),
                includeEdge = true
            )
        )

        binding.addButton.setOnClickListener{
            val intent = Intent(this, AddNoteActivity::class.java)
            startActivity(intent)
        }

    }
    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
}