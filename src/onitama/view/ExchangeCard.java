package onitama.view;

import onitama.model.moves.MoveCard;
import onitama.utils.ObservedSubject;
import onitama.utils.SubjectObserver;

public class ExchangeCard extends CardPanel implements SubjectObserver<MoveCard> {


    @Override
    public void update(ObservedSubject<MoveCard> sender, MoveCard message) {
        this.setCard(message);
    }
}
