package ru.omdroid.game.ChaosWord.LogicChangedFieldGame;

import ru.omdroid.game.ChaosWord.ManagerPositionMovement;

public class LogicControlPressedCurrentView {

    public boolean controlCurrentPressedTextView(float posX, float posY, ParamForChangingFieldGame paramChangingFieldGame){
        return (posX == (paramChangingFieldGame.getPositionXLastSelectedTV() + (ManagerPositionMovement.HEIGHT_SCREEN / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posY == paramChangingFieldGame.getPositionYLastSelectedTV()) ||
                (posX == (paramChangingFieldGame.getPositionXLastSelectedTV() - (ManagerPositionMovement.HEIGHT_SCREEN / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posY == paramChangingFieldGame.getPositionYLastSelectedTV()) ||
                (posY == (paramChangingFieldGame.getPositionYLastSelectedTV() + (ManagerPositionMovement.HEIGHT_SCREEN / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posX == paramChangingFieldGame.getPositionXLastSelectedTV()) ||
                (posY == (paramChangingFieldGame.getPositionYLastSelectedTV() - (ManagerPositionMovement.HEIGHT_SCREEN / ManagerPositionMovement.SIZE_GAME_FIELD - 20 + 2)) & posX == paramChangingFieldGame.getPositionXLastSelectedTV());
    }
}
