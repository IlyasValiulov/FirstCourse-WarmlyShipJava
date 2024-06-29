package DiffetentsDrawingDecks;

public enum NumberOfDecks {
    OneDeck(1),
    TwoDecks(2),
    ThreeDecks(3);
    private int numberofdecks;
    NumberOfDecks(int numberofdecks) {
        this.numberofdecks = numberofdecks;
    }
    public int getNumdecks() {return numberofdecks;}
}
