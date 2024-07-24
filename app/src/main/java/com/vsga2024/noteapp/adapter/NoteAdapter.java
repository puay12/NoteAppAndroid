package com.vsga2024.noteapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.vsga2024.noteapp.R;
import com.vsga2024.noteapp.data.model.Note;

import java.util.List;

public class NoteAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Note> notes;

    public NoteAdapter(Activity activity, List<Note> notes) {
        this.activity = activity;
        this.notes = notes;
    }


    @Override
    public int getCount() {
        return this.notes.size();
    }

    @Override
    public Object getItem(int position) {
        return this.notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parentView) {
        if (this.inflater == null) {
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView == null) {
            convertView = this.inflater.inflate(R.layout.item_note, null);
        }

        Note note = this.notes.get(position);
        TextView title = (TextView) convertView.findViewById(R.id.noteTitle);
        TextView content = (TextView) convertView.findViewById(R.id.noteContent);

        title.setText(note.getTitle());
        content.setText(note.getContent());

        return convertView;
    }
}
