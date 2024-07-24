package com.vsga2024.noteapp.data.db;

import android.provider.BaseColumns;

public final class NoteReaderContract {
    private NoteReaderContract() {}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
