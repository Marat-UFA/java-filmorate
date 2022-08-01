package ru.yandex.practicum.filmorate.comparison;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.Comparator;

public class TreeComparison implements Comparator {
    @Override
    public int compare(Object o1, Object o2) {
        Film film1 = (Film) o1;
        Film film2 = (Film) o2;

        if (film1.getUserID().size() == 0 && film1.getUserID().size() == 0){
            if(film1.getId() > film2.getId()){
                return 1;
            } else if (film1.getId() < film2.getId()){
                return -1;
            } else return 0;
        }
        if (film1.getUserID().size() == 0 && film2.getUserID().size() != 0){
            return 1;
        }
        if (film1.getUserID().size() != 0 && film2.getUserID().size() == 0){
            return -1;
        }

        if (film1.getUserID().size() > film2.getUserID().size()){
            return 1;
        } else if (film1.getUserID().size() < film2.getUserID().size()){
            return -1;
        } else return 0;
    }
}
