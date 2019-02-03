package com.malekkbh.me;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Genre extends ExpandableGroup<PlayList> {

    public Genre(String title, List<PlayList> items) {
        super(title, items);
    }
}