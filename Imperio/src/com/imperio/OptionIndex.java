package com.imperio;

public class OptionIndex {

    public final Character character;
    public final String name;

    public OptionIndex(String name, Character character)
            throws OptionException {
        if (name == null && character == null) {
            throw new OptionException(new NullPointerException(
                    "name and character cannot both be null"));
        }
        this.name = name;
        this.character = character;
    }

    public int compareTo(Character character) throws OptionException {
        return compareTo(new OptionIndex(null, character));
    }

    public int compareTo(OptionIndex optionIndex) {
        if (name != null) {
            if (optionIndex.name != null) {
                return name.compareTo(optionIndex.name);
            } else {
                return name
                        .compareTo(String.format("%c", optionIndex.character));
            }
        }
        if (optionIndex.character != null) {
            if (character < optionIndex.character) {
                return -1;
            } else if (character > optionIndex.character) {
                return 1;
            }
            return 0;
        }
        return String.format("%c", character).compareTo(optionIndex.name);
    }

    public int compareTo(String name) throws OptionException {
        return compareTo(new OptionIndex(name, null));
    }

    @Override
    public String toString() {
        if (name != null) {
            return name;
        }
        return String.format("%c", character);
    }

}
